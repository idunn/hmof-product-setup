package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import hmof.deploy.Job
import hmof.deploy.Promotion
import hmof.security.User
import hmof.security.Role
import hmof.security.UserRole

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
	 * Persist job details to the job and promotions tables
	 * @return
	 */
	@Transactional
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

		def promote = [status: "PENDING", job: j1, jobNumber: j1.getJobNumber(), user: userId, environments: envId]
		Promotion p1 = new Promotion(promote).save(failOnError:true)

		redirect(action: "list")

	}

	/**
	 * Promote content that has already been promoted to Dev OR QA
	 * @return
	 */
	@Transactional
	def promote(){

		// TODO is this the same as instanceId
		def commerceObjectInstance = CommerceObject.get(params.instanceToBePromoted)

		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = deploymentService.getUserEnvironmentInformation()

		def promotionInstance = deploymentService.getDeployedInstance(commerceObjectInstance, envId)

		if(promotionInstance==null){

			flash.message = "Can't Promote as Content not deployed OR Promoted to an earlier environment"
			redirect(action: "list")
			return
		}

		def jobInstance = Job.where{id == promotionInstance.jobId}.get()

		def promotion = Promotion.where{jobNumber==promotionInstance.getJobNumber() && environments{id == envId.id}}.list()

		if(promotion.isEmpty()){

			def promote = [status: "PENDING", job: jobInstance, jobNumber: promotionInstance.getJobNumber(), user: userId, environments: envId]
			Promotion p2 = new Promotion(promote).save(failOnError:true, flush:true)

		} else{

			flash.message = "Job Already Promoted or In-Progress"

		}

		redirect(action: "list")

	}

	def index(Integer max) {redirect(action: "list", params: params)}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond CommerceObject.list(params), model:[commerceObjectInstanceCount: CommerceObject.count()]
	}

	def show(CommerceObject commerceObjectInstance) {
		respond commerceObjectInstance
	}

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

		commerceObjectInstance.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'commerceObjectInstance.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect commerceObjectInstance
			}
			'*' { respond commerceObjectInstance, [status: CREATED] }
		}
	}

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

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'CommerceObject.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect commerceObjectInstance
			}
			'*'{ respond commerceObjectInstance, [status: OK] }
		}
	}

	@Transactional
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
