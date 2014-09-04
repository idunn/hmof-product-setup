package hmof

import hmof.deploy.Environment
import hmof.deploy.Job
import hmof.deploy.Promotion
import hmof.security.User
import hmof.security.Role
import hmof.security.UserRole


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * ProgramController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class ProgramController {

	def springSecurityService
	def deploymentService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


	/**
	 * Persist job details to the job and promotions tables
	 * @return
	 */
	def deploy(){		
		
		
		def instanceId = params.instanceDetail				
		
		def (bundle, secureProgram, commerceObject) = deploymentService.getProgramChildren(instanceId)
		def childContent = bundle + secureProgram + commerceObject

		def programInstance = Program.get(instanceId)

		def deploymentJobNumber = deploymentService.getCurrentJobNumber()		
	
		def user = springSecurityService?.currentUser?.id		
		def userId = User.where{id==user}.get()		

		// Create a map of job data to persist
		def job = [contentId: programInstance.id, revision: deploymentService.getCurrentEnversRevision(programInstance), contentTypeId: programInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

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
	def promote(){

		def programInstance = Program.get(params.instanceToBePromoted)

		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = deploymentService.getUserEnvironmentInformation()

		def promotionInstance = deploymentService.getDeployedInstance(programInstance, envId)

		if(promotionInstance==null){

			flash.message = "Can't Promote as Content not deployed OR Promoted to an earlier environment"
			redirect(action: "list")
			return
		}

		def jobInstance = Job.where{id == promotionInstance.jobId}.get()

		def promotion = Promotion.where{jobNumber==promotionInstance.getJobNumber() && environments{id == envId.id}}.list()

		// If instance already exists on QA OR Prod then stop TODO check
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
		respond Program.list(params), model:[programInstanceCount: Program.count()]
	}

	def show(Program programInstance) {
		respond programInstance
	}

	def create() {
		respond new Program(params)
	}

	@Transactional
	def save(Program programInstance) {
		if (programInstance == null) {
			notFound()
			return
		}

		if (programInstance.hasErrors()) {
			respond programInstance.errors, view:'create'
			return
		}
		
		//TODO ADD CONTENT TYPE check if this Works!!
		def contentType = ContentType.where{id==1}.get()
		programInstance.contentType = contentType
		programInstance.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'programInstance.label', default: 'Program'), programInstance.id])
				redirect programInstance
			}
			'*' { respond programInstance, [status: CREATED] }
		}
	}

	def edit(Program programInstance) {
		respond programInstance
	}

	@Transactional
	def update(Program programInstance) {
		if (programInstance == null) {
			notFound()
			return
		}

		if (programInstance.hasErrors()) {
			respond programInstance.errors, view:'edit'
			return
		}
		
		// TODO check
		def contentType = ContentType.where{id==1}.get()
		programInstance.contentType = contentType
		
		programInstance.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'Program.label', default: 'Program'), programInstance.id])
				redirect programInstance
			}
			'*'{ respond programInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(Program programInstance) {

		if (programInstance == null) {
			notFound()
			return
		}

		programInstance.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'Program.label', default: 'Program'), programInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'programInstance.label', default: 'Program'), params.id])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
