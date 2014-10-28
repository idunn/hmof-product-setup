package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import hmof.deploy.Job
import hmof.deploy.Promotion
import hmof.security.User
import hmof.security.Role
import hmof.security.UserRole
import grails.plugin.springsecurity.annotation.Secured
/**
 * BundleController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class BundleController {

	def springSecurityService
	def deploymentService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


	/*
	 * 
	 */
	@Transactional
	def updateParent(def currentInstance){

		def bundle = Bundle.where{id==currentInstance.id}.get()
		// get parent of current Instance
		def ProgramToUpdate = bundle.program
		ProgramToUpdate.properties = [lastUpdated: new Date()]

	}

	/**
	 * Persist job details to the job and promotions tables
	 * @return
	 */
	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def deploy(){

		def instanceId = params.instanceDetail

		def (secureProgram, commerceObject) = deploymentService.getBundleChildren(instanceId)
		def childContent = secureProgram + commerceObject

		def bundleInstance = Bundle.get(instanceId)

		def deploymentJobNumber = deploymentService.getCurrentJobNumber()

		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()

		// Create a map of job data to persist
		def job = [contentId: bundleInstance.id, revision: deploymentService.getCurrentEnversRevision(bundleInstance), contentTypeId: bundleInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

		// Add Program instance to Job
		Job j1 = new Job(job).save(failOnError:true)

		childContent.each{

			def content = [contentId: it.id, revision: deploymentService.getCurrentEnversRevision(it), contentTypeId: it.contentType.contentId, jobNumber: j1.getJobNumber(), user: userId]
			Job j2 = new Job(content).save(failOnError:true)
		}

		// Add child relationship to each bundle
		def jobToUpdate = Job.where{jobNumber == deploymentJobNumber && contentTypeId==2 }.list()

		jobToUpdate.each{

			def bundleJobInstance = it
			def childMap = deploymentService.getChildrenMap(bundleJobInstance.contentId)
			bundleJobInstance.children = childMap

		}


		def envId = deploymentService.getUserEnvironmentInformation()

		def promote = [status: JobStatus.Pending, job: j1, jobNumber: j1.getJobNumber(), user: userId, environments: envId]
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

		def bundleInstance = Bundle.get(params.instanceToBePromoted)

		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = deploymentService.getUserEnvironmentInformation()

		def promotionInstance = deploymentService.getDeployedInstance(bundleInstance, envId)

		if(promotionInstance==null){

			flash.message = "Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment"
			redirect(action: "list")
			return
		}

		def jobInstance = Job.where{id == promotionInstance.jobId}.get()

		def promotionJobInstance = Promotion.where{jobNumber==promotionInstance.getJobNumber() && environments{id == envId.id}}.get()?:none

		if(promotionJobInstance==none){

			def promote = [status: JobStatus.Pending, job: jobInstance, jobNumber: promotionInstance.getJobNumber(), user: userId, environments: envId]
			Promotion p2 = new Promotion(promote).save(failOnError:true, flush:true)

		} else if(promotionJobInstance.status == JobStatus.In_Progress.toString() || promotionJobInstance.status == JobStatus.Pending.toString()){

			flash.message = "Job cannot be re-promoted as it is ${promotionJobInstance.status}"
		}

		else{

			// If job has failed or is successful and user want to re-promote			
			flash.message = "Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being re-promoted"
			promotionJobInstance.properties = [status:JobStatus.Pending]

		}

		redirect(action: "list")

	}

	def index(Integer max) {redirect(action: "list", params: params)}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond Bundle.list(params), model:[bundleInstanceCount: Bundle.count()]
	}

	def show(Bundle bundleInstance) {
		respond bundleInstance
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def create() {
		respond new Bundle(params)
	}

	@Transactional
	def save() {

		def contentType = ContentType.where{id==2}.get()
		params.contentType = contentType
		def bundleInstance = new Bundle(params)

		if (bundleInstance == null) {
			notFound()
			return
		}

		if (bundleInstance.hasErrors()) {
			respond bundleInstance.errors, view:'create'
			return
		}

		//bundleInstance.save flush:true
		if (!bundleInstance.save(flush: true)) {
			render(view: "create", model: [bundleInstance: bundleInstance])
			return
		}

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'bundleInstance.label', default: 'Bundle'), bundleInstance.id])
				redirect bundleInstance
			}
			'*' { respond bundleInstance, [status: CREATED] }
		}
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def edit(Bundle bundleInstance) {
		respond bundleInstance
	}

	@Transactional
	def update(Bundle bundleInstance) {
		if (bundleInstance == null) {
			notFound()
			return
		}

		if (bundleInstance.hasErrors()) {
			respond bundleInstance.errors, view:'edit'
			return
		}

		bundleInstance.save flush:true

		// update the timeStamp of its parent so that the change is reflected in Envers
		updateParent(bundleInstance)

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'Bundle.label', default: 'Bundle'), bundleInstance.id])
				redirect bundleInstance
			}
			'*'{ respond bundleInstance, [status: OK] }
		}
	}

	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def delete(Bundle bundleInstance) {

		if (bundleInstance == null) {
			notFound()
			return
		}

		bundleInstance.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'Bundle.label', default: 'Bundle'), bundleInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'bundleInstance.label', default: 'Bundle'), params.id])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
