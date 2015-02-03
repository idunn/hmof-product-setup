package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import hmof.deploy.Job
import hmof.deploy.Promotion
import hmof.security.User
import hmof.security.Role
import hmof.security.UserRole
import grails.plugin.springsecurity.annotation.Secured
import org.apache.log4j.Logger

@Transactional(readOnly = true)
class SecureProgramController {

	def springSecurityService
	def deploymentService
	def utilityService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	/**
	 * Update the parents of the object being updated
	 * @param currentInstance
	 * @return
	 */
	@Transactional
	def updateParent(def currentInstance){

		def bundleInstances = Bundle.where{secureProgram{id==currentInstance.id}}.list()

		bundleInstances.each{
			it.properties = [lastUpdated: new Date()]
		}

		def programInstances = []

		if(!bundleInstances.isEmpty()){
			programInstances = Program.where{bundles{id in bundleInstances.id }}.list()
		}

		programInstances.each{
			it.properties = [lastUpdated: new Date()]
		}

	}


	/**
	 * Remove Standalone Associations before deleting the SecureProgram
	 * @param currentInstance
	 * @return
	 */
	@Transactional
	def removeAssociations(def currentInstance){

		def parentBundles = Bundle.where{secureProgram{id==currentInstance.id}}.list()

		log.info"Parent Bundles in association" + parentBundles

		parentBundles.each{

			def bundle = Bundle.get(it.id)
			bundle.removeFromSecureProgram(currentInstance)
		}

		def childCommerceObjects = []
		childCommerceObjects += currentInstance.commerceObjects
		childCommerceObjects.each{co -> currentInstance.removeFromCommerceObjects(co) }

	}

	/**
	 * Persist job details to the job and promotions tables
	 * @return
	 */
	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def deploy(){

			def instanceDetail = params.instanceDetail

		def instanceDetails = instanceDetail.split("/")
		def instanceId = instanceDetails[0]
		log.info("Secure Program  Detail: "+instanceId)
		def (commerceObject) = deploymentService.getSecureProgramChildren(instanceId)
		def childContent = commerceObject
		log.info("childContent: "+ childContent)
		def secureProgramInstance = SecureProgram.get(instanceId)
		log.info("Deploying secureProgramInstance: " + secureProgramInstance.productName)
		def deploymentJobNumber = deploymentService.getCurrentJobNumber()
		log.info("deploymentJobNumber: " + deploymentJobNumber)
		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()
		log.info("userId: " + userId)
		log.info("contentId: " + secureProgramInstance.id)
		log.info("contentTypeId: " + secureProgramInstance.contentType.contentId)
		// Create a map of job data to persist
		def job = [contentId: secureProgramInstance.id, revision: deploymentService.getCurrentEnversRevision(secureProgramInstance), contentTypeId: secureProgramInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

		// Add Program instance to Job
		Job j1 = new Job(job).save(failOnError:true)
		log.info("Successfully added Secure Program Instance to job: " + secureProgramInstance.productName)
		childContent.each{
			log.info("Adding child content to job content id"+it.id+",revision"+deploymentService.getCurrentEnversRevision(it)+",contentTypeId"+it.contentType.contentId+",jobNumber"+j1.getJobNumber())
			def content = [contentId: it.id, revision: deploymentService.getCurrentEnversRevision(it), contentTypeId: it.contentType.contentId, jobNumber: j1.getJobNumber(), user: userId]
			Job j2 = new Job(content).save(failOnError:true)
			log.info("Successfully added child content ID:"+it.id+" to job")
		}

		def envId = deploymentService.getUserEnvironmentInformation()
		log.info("Environment ID:"+envId)
		log.info("Job:"+ j1+",jobNumber: "+j1.getJobNumber()+",JobStatus:"+ JobStatus.Pending)
		def promote = [status: JobStatus.Pending, job: j1, jobNumber: j1.getJobNumber(), user: userId, environments: envId,smartDeploy:false]
		Promotion p1 = new Promotion(promote).save(failOnError:true)

		redirect(action: "list")

	}

	/**
	 * Promote content that has already been promoted to Dev OR QA
	 * @return
	 */
	@Transactional
	@Secured(['ROLE_QA', 'ROLE_PROD'])
	def promote(){

		final String none = "none"
		
		def instanceDetail = params.instanceToBePromoted
		def instanceDetails = instanceDetail.split("/")
		def instanceToBePromoted = instanceDetails[0]
		
		def secureProgramInstance = SecureProgram.get(instanceToBePromoted)
		log.info("Promoting secureProgramInstance : "+secureProgramInstance.productName)
		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = deploymentService.getUserEnvironmentInformation()
		log.info("User Id: "+userId+",envId:"+envId)
		def promotionInstance = deploymentService.getDeployedInstance(secureProgramInstance, envId)

		if(promotionInstance==null){

			flash.message = "Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment"
			log.info("Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment")
			redirect(action: "list")
			return
		}

		def jobInstance = Job.where{id == promotionInstance.jobId}.get()
		log.info("jobNumber:"+promotionInstance.getJobNumber())
		def promotionJobInstance = Promotion.where{jobNumber==promotionInstance.getJobNumber() && environments{id == envId}}.get()?:none

		if(promotionJobInstance==none){

			def promote = [status: JobStatus.Pending, job: jobInstance, jobNumber: promotionInstance.getJobNumber(), user: userId, environments: envId,smartDeploy:false]
			Promotion p2 = new Promotion(promote).save(failOnError:true, flush:true)
			
			log.info("Job saved successfully")
		} else if(promotionJobInstance.status == JobStatus.In_Progress.toString() || promotionJobInstance.status == JobStatus.Pending.toString()){

			flash.message = "Job cannot be re-promoted as it is ${promotionJobInstance.status}"
			log.info("Job cannot be re-promoted as it is ${promotionJobInstance.status}")
		}

		else{

			// If job has failed or is successful and user want to re-promote
			flash.message = "Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being re-promoted"
			log.info("Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being re-promoted")
			promotionJobInstance.properties = [status:JobStatus.Pending]

		}


		redirect(action: "list")

	}

	def index(Integer max) {redirect(action: "list", params: params)}

	def list(Integer max) {
		params.max = Math.min(max ?: 100, 200)
		log.info "Secure Program count:"+SecureProgram.count()
		respond SecureProgram.list(params), model:[secureProgramInstanceCount: SecureProgram.count()]
	}

	def show(SecureProgram secureProgramInstance) {
				
		def parentBundles = Bundle.where{secureProgram{id==secureProgramInstance.id}}.list()
		
		render(view:"show", model:[secureProgramInstance:secureProgramInstance, parentBundles:parentBundles])
	}
	
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def create() {
		respond new SecureProgram(params)
	}

	@Transactional
	def save() {
		def contentType = ContentType.where{id==3}.get()
		params.contentType = contentType
		def secureProgramInstance = new SecureProgram(params)
		if (secureProgramInstance == null) {
			log.info "Creating Secure Program Not Found"
			notFound()
			return
		}
		log.info "Started saving Secure Program :"+secureProgramInstance.productName
		if (secureProgramInstance.hasErrors()) {
			log.info "No Errors Found while creating Secure Program"
			respond secureProgramInstance.errors, view:'create'
			return
		}

		if (!secureProgramInstance.save(flush: true)) {
			render(view: "create", model: [secureProgramInstance: secureProgramInstance])
			return
		}
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [message(code: 'secureProgramInstance.label', default: 'SecureProgram'), secureProgramInstance.id])
				redirect secureProgramInstance
			}
			'*' { respond secureProgramInstance, [status: CREATED] }
		}
		log.info "Successfully saved Secure Program :"+secureProgramInstance.productName
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def edit(SecureProgram secureProgramInstance) {
		respond secureProgramInstance
	}

	@Transactional
	def update(SecureProgram secureProgramInstance) {
	
		if (secureProgramInstance == null) {
			log.info "updating Secure Program Not Found"
			notFound()
			return
		}
		log.info "Started updating Secure Program :"+secureProgramInstance.productName
		if (secureProgramInstance.hasErrors()) {
			log.info "No Errors Found while updating Secure Program"
			respond secureProgramInstance.errors, view:'edit'
			return
		}
	
		if(params.commerceObjects==null)
		{			
			secureProgramInstance.commerceObjects.clear()
		}
		  
		  

		secureProgramInstance.save flush:true
		
		// Update the timeStamp of all its parents so that the change is reflected in Envers
		updateParent(secureProgramInstance)

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'SecureProgram.label', default: 'SecureProgram'), secureProgramInstance.id])
				redirect secureProgramInstance
			}
			'*'{ respond secureProgramInstance, [status: OK] }
		}
		log.info "Successfully updated Secure Program: " + secureProgramInstance.productName
	}

	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def delete(SecureProgram secureProgramInstance) {

		if (secureProgramInstance == null) {
			log.info "Deleting Secure Program Not Found"
			notFound()
			return
		}

		log.info "Removing Parent/Child associations from the SP that is being deleted"
		removeAssociations(secureProgramInstance)

		log.info "Started deleting Secure Program: " + secureProgramInstance.productName
		secureProgramInstance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'SecureProgram.label', default: 'SecureProgram'), secureProgramInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
		log.info "Successfully deleted Secure Program: " + secureProgramInstance.productName
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'secureProgramInstance.label', default: 'SecureProgram'), params.id])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}


	/**
	 * Download the log file
	 * @return
	 */
	def download() {
		utilityService.getLogFile(params.logFile)
	}
}
