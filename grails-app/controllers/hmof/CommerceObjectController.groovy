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

/**
 * CommerceObjectController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class CommerceObjectController {

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

		def secureProgramInstances = SecureProgram.where{commerceObjects{id==currentInstance.id}}.list()

		secureProgramInstances.each{
			it.properties = [lastUpdated: new Date(),userUpdatingSProgram: springSecurityService?.currentUser?.username]
		}

		def bundleInstances = []
		// Needed for MySql database
		if(!secureProgramInstances.isEmpty()){
			bundleInstances = Bundle.where{secureProgram{id in secureProgramInstances.id}}.list()
		}

		bundleInstances.each{
			it.properties = [lastUpdated: new Date(),userUpdatingBundle: springSecurityService?.currentUser?.username]
		}

		def programInstances = []
		// Needed for MySql database
		if(!bundleInstances.isEmpty()){
			programInstances = Program.where{bundles{id in bundleInstances.id }}.list()
		}

		programInstances.each{
			it.properties = [lastUpdated: new Date(),userUpdatingProgram: springSecurityService?.currentUser?.username]
		}

	}

	/**
	 * Remove Standalone Parent Associations before deleting the Commerce Object
	 * @param currentInstance
	 * @return
	 */
	@Transactional
	def removeAssociations(def currentInstance){

		def parentSecureProgram = SecureProgram.where{commerceObjects{id==currentInstance.id}}.list()
		log.info"Parent Secure Programs in association" + parentSecureProgram

		parentSecureProgram.each{

			def secureProgram = SecureProgram.get(it.id)
			secureProgram.removeFromCommerceObjects(currentInstance)
		}
	}
	@Secured(['ROLE_ADMIN'])	
	def importCSV(Integer max) {render(view:"importCSV")}
	/**
	 * Pass in a comma separated file and update the database with new CO instances
	 * @return
	 */
	
	@Transactional
	def importFile(){

		log.info"Importing File"
		def csvfile = request.getFile('CSVfiledata')
		
		try {
			if(csvfile==null) {
				
				flash.message = "File cannot be empty"
				redirect(action: "list")
				return
			} else {
				def filename = csvfile.getOriginalFilename()
				def fullPath=grailsApplication.config.uploadFolder
				
				File upLoadedfileDir = new File(fullPath);
				if (!upLoadedfileDir.exists()) {					
					upLoadedfileDir.mkdir()						
				}
				
				def upLoadedfile= new File(fullPath, filename)
			   	upLoadedfile.createNewFile() //if it doesn't already exist
				FileOutputStream fos = new FileOutputStream(upLoadedfile);
				fos.write(csvfile.getBytes());
				fos.close();
				log.info"Calling Service"
				List parseFileAndPersistData = utilityService.parseTextFile(upLoadedfile)
				if (parseFileAndPersistData?.empty) {
					upLoadedfile.delete()						
					redirect(action: "list")
					return
				}else
				{
					render view:'importCSV', model:[parseFileAndPersistData:parseFileAndPersistData]
					return
				}

			}
		} catch (IOException e) {
			flash.message = "File cannot be empty / File have errors"
			redirect(action: "importCSV")
			return
		}
		log.info"Completed import"

		redirect(action: "list")

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

		log.info("Commerce Object  Detail: "+instanceId)
		def commerceObjectInstance = CommerceObject.get(instanceId)
		log.info("Deploying commerceObjectInstance : "+commerceObjectInstance.objectName)
		def deploymentJobNumber = deploymentService.getCurrentJobNumber()
		log.info("deploymentJobNumber: "+deploymentJobNumber)
		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()
		log.info("userId: "+userId)
		log.info("contentId: "+commerceObjectInstance.id)
		log.info("contentTypeId: "+commerceObjectInstance.contentType.contentId)
		// Create a map of job data to persist
		def job = [contentId: commerceObjectInstance.id, revision: deploymentService.getCurrentEnversRevision(commerceObjectInstance), contentTypeId: commerceObjectInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

		// Add Program instance to Job
		Job j1 = new Job(job).save(failOnError:true)
		log.info("Successfully added Commerce Object Instance to job : "+commerceObjectInstance.objectName)
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

		def commerceObjectInstance = CommerceObject.get(instanceToBePromoted)
		log.info("Promoting commerceObjectInstance : "+commerceObjectInstance.objectName)
		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = deploymentService.getUserEnvironmentInformation()
		log.info("User Id: "+userId+",envId:"+envId)
		def promotionInstance = deploymentService.getDeployedInstance(commerceObjectInstance, envId)

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

		} else if(promotionJobInstance.status == JobStatus.In_Progress.toString() || promotionJobInstance.status == JobStatus.Pending.toString() || 
		promotionJobInstance.status == JobStatus.Pending_Repromote.toString() || promotionJobInstance.status == JobStatus.Repromoting.toString()){

			flash.message = "Job cannot be re-promoted as it is ${promotionJobInstance.status}"
			log.info("Job cannot be re-promoted as it is ${promotionJobInstance.status}")
		}

		else{

			// If job has failed or is successful and user want to re-promote
			flash.message = "Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being re-promoted"
			log.info("Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being re-promoted")
			promotionJobInstance.properties = [status:JobStatus.Pending_Repromote]

		}

		redirect(action: "list")

	}

	def index(Integer max) {redirect(action: "list", params: params)}

	def list(Integer max) {
		params.max = Math.min(max ?: 100, 200)
		log.info "Commerce Object count:"+CommerceObject.count()
		respond CommerceObject.list(params), model:[commerceObjectInstanceCount: CommerceObject.count()]
	}

	def show(CommerceObject commerceObjectInstance)
	{
		def parentSecureProgram = SecureProgram.where{commerceObjects{id==commerceObjectInstance.id}}.list()
		render(view:"show", model:[commerceObjectInstance:commerceObjectInstance, parentSecureProgram:parentSecureProgram ])
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def create() {

		Long lNumber =5570000001
		def lisbnNumber = CommerceObject.executeQuery("select max(isbnNumber) from CommerceObject where isbnNumber like '%557%' and LENGTH(isbnNumber) = 10")
		String strISBN= lisbnNumber[0]
		if(strISBN!=null){
			lNumber = Long.valueOf(strISBN)
			if(lNumber>=5570000001)
				lNumber++
		}
		params.isbnNumber=lNumber

		respond new CommerceObject(params)
	}

	@Transactional
	def save() {

		def contentType = ContentType.where{id==4}.get()
		params.userUpdatingCO = springSecurityService?.currentUser?.username
		params.contentType = contentType
		def commerceObjectInstance = new CommerceObject(params)

		if (commerceObjectInstance == null) {
			log.info "Creating Commerce Object Not Found"
			notFound()
			return
		}
		log.info "Started saving Commerce Object :"+commerceObjectInstance.objectName
		if (commerceObjectInstance.hasErrors()) {
			log.info "No Errors Found while creating Commerce Object"
			respond commerceObjectInstance.errors, view:'create'
			return
		}

		//commerceObjectInstance.save flush:true
		if (!commerceObjectInstance.save(flush: true)) {
			render(view: "create", model: [commerceObjectInstance: commerceObjectInstance])
			return
		}
		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'commerceObjectInstance.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect commerceObjectInstance
			}
			'*' { respond commerceObjectInstance, [status: CREATED] }
		}
		log.info "Successfully saved Commerce Object :"+commerceObjectInstance.objectName
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def edit(CommerceObject commerceObjectInstance) {
		respond commerceObjectInstance
	}

	@Transactional
	def update(CommerceObject commerceObjectInstance) {
		if (commerceObjectInstance == null) {
			log.info "Updating Commerce Object Not Found"
			notFound()
			return
		}
		log.info "Started updating Commerce Object :"+commerceObjectInstance.objectName
		if (commerceObjectInstance.hasErrors()) {
			log.info "No Errors Found while updating Commerce Object"
			respond commerceObjectInstance.errors, view:'edit'
			return
		}
		commerceObjectInstance.userUpdatingCO = springSecurityService?.currentUser?.username
		commerceObjectInstance.save flush:true
		// update the timeStamp of all its parents so that the change is reflected in Envers
		updateParent(commerceObjectInstance)

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'CommerceObject.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect commerceObjectInstance
			}
			'*'{ respond commerceObjectInstance, [status: OK] }
		}
		log.info "Successfully updated Commerce Object :"+commerceObjectInstance.objectName
	}

	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def delete(CommerceObject commerceObjectInstance) {

		if (commerceObjectInstance == null) {
			log.info "Deleting Commerce Object Not Found"
			notFound()
			return
		}

		log.info "Removing Parent associations from the CO that is being deleted"
		removeAssociations(commerceObjectInstance)

		log.info "Started deleting Commerce Object :"+commerceObjectInstance.objectName
		commerceObjectInstance.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'CommerceObject.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
		log.info "Successfully deleted Commerce Object :"+commerceObjectInstance.objectName
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'commerceObjectInstance.label', default: 'CommerceObject'), params.id])
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
