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
 * CommerceObjectController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class CommerceObjectController {

	def springSecurityService
	def deploymentService

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
			it.properties = [lastUpdated: new Date()]
		}

		def bundleInstances = Bundle.where{secureProgram{id in secureProgramInstances.id}}.list()

		bundleInstances.each{
			it.properties = [lastUpdated: new Date()]
		}

		def programInstances = Program.where{bundles{id in bundleInstances.id }}.list()
		programInstances.each{
			it.properties = [lastUpdated: new Date()]
		}

	}

	/**
	 * Persist job details to the job and promotions tables
	 * @return
	 */
	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def deploy(){

		def instanceId = params.instanceDetail

		def commerceObjectInstance = CommerceObject.get(instanceId)

		def deploymentJobNumber = deploymentService.getCurrentJobNumber()

		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()

		// Create a map of job data to persist
		def job = [contentId: commerceObjectInstance.id, revision: deploymentService.getCurrentEnversRevision(commerceObjectInstance), contentTypeId: commerceObjectInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

		// Add Program instance to Job
		Job j1 = new Job(job).save(failOnError:true)

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

		def commerceObjectInstance = CommerceObject.get(params.instanceToBePromoted)

		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = deploymentService.getUserEnvironmentInformation()

		def promotionInstance = deploymentService.getDeployedInstance(commerceObjectInstance, envId)

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
		params.max = Math.min(max ?: 100, 200)
		respond CommerceObject.list(params), model:[commerceObjectInstanceCount: CommerceObject.count()]
	}

	def show(CommerceObject commerceObjectInstance) {
		respond commerceObjectInstance
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def create() {
		respond new CommerceObject(params)
	}

	@Transactional
	def save() {

		def contentType = ContentType.where{id==4}.get()
		params.contentType = contentType
		def commerceObjectInstance = new CommerceObject(params)

		if (commerceObjectInstance == null) {
			notFound()
			return
		}

		if (commerceObjectInstance.hasErrors()) {
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
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def edit(CommerceObject commerceObjectInstance) {
		respond commerceObjectInstance
	}

	@Transactional
	def update(CommerceObject commerceObjectInstance) {
		if (commerceObjectInstance == null) {
			notFound()
			return
		}

		if (commerceObjectInstance.hasErrors()) {
			respond commerceObjectInstance.errors, view:'edit'
			return
		}

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
	}

	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def delete(CommerceObject commerceObjectInstance) {

		if (commerceObjectInstance == null) {
			notFound()
			return
		}

		commerceObjectInstance.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'CommerceObject.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
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
}
