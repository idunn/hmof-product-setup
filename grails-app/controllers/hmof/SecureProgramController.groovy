package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import hmof.deploy.Job
import hmof.deploy.Promotion
import hmof.security.User
import hmof.security.Role
import hmof.security.UserRole

/**
 * SecureProgramController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class SecureProgramController {

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

		def bundleInstances = Bundle.where{secureProgram{id==currentInstance.id}}.list()

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
	def deploy(){

		def instanceId = params.instanceDetail
		
		def (commerceObject) = deploymentService.getSecureProgramChildren(instanceId)
		def childContent = commerceObject

		def secureProgramInstance = SecureProgram.get(instanceId)

		def deploymentJobNumber = deploymentService.getCurrentJobNumber()

		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()

		// Create a map of job data to persist
		def job = [contentId: secureProgramInstance.id, revision: deploymentService.getCurrentEnversRevision(secureProgramInstance), contentTypeId: secureProgramInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

		// Add Program instance to Job
		Job j1 = new Job(job).save(failOnError:true)

		childContent.each{

			def content = [contentId: it.id, revision: deploymentService.getCurrentEnversRevision(it), contentTypeId: it.contentType.contentId, jobNumber: j1.getJobNumber(), user: userId]
			Job j2 = new Job(content).save(failOnError:true)
		}

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
		
		def secureProgramInstance = SecureProgram.get(params.instanceToBePromoted)

		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = deploymentService.getUserEnvironmentInformation()

		def promotionInstance = deploymentService.getDeployedInstance(secureProgramInstance, envId)

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
		respond SecureProgram.list(params), model:[secureProgramInstanceCount: SecureProgram.count()]
	}

	def show(SecureProgram secureProgramInstance) {
		respond secureProgramInstance
	}

	def create() {
		respond new SecureProgram(params)
	}

	@Transactional
	def save() {

		def contentType = ContentType.where{id==3}.get()
		params.contentType = contentType
		def secureProgramInstance = new SecureProgram(params)

		if (secureProgramInstance == null) {
			notFound()
			return
		}

		if (secureProgramInstance.hasErrors()) {
			respond secureProgramInstance.errors, view:'create'
			return
		}

		secureProgramInstance.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'secureProgramInstance.label', default: 'SecureProgram'), secureProgramInstance.id])
				redirect secureProgramInstance
			}
			'*' { respond secureProgramInstance, [status: CREATED] }
		}
	}

	def edit(SecureProgram secureProgramInstance) {
		respond secureProgramInstance
	}

	@Transactional
	def update(SecureProgram secureProgramInstance) {
		if (secureProgramInstance == null) {
			notFound()
			return
		}

		if (secureProgramInstance.hasErrors()) {
			respond secureProgramInstance.errors, view:'edit'
			return
		}

		secureProgramInstance.save flush:true
		// Update the timeStamp of all its parents so that the change is reflected in Envers
		updateParent(secureProgramInstance)

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'SecureProgram.label', default: 'SecureProgram'), secureProgramInstance.id])
				redirect secureProgramInstance
			}
			'*'{ respond secureProgramInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(SecureProgram secureProgramInstance) {

		if (secureProgramInstance == null) {
			notFound()
			return
		}

		secureProgramInstance.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'SecureProgram.label', default: 'SecureProgram'), secureProgramInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'secureProgramInstance.label', default: 'SecureProgram'), params.id])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
