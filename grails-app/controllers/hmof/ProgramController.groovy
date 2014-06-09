package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * ProgramController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class ProgramController {

	// inject Service
	def deploymentService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	/**
	 * action to deploy content
	 * @return
	 */
	def deploy() {

		def deploymentDetails = deploymentService.promoteProgram(params)
		// TODO require variable to identify individual SP and CO that have been updated and pass this to the view via the model
		[deploymentDetailsList:deploymentDetails]
		
		/*if (deploymentDetails.size()==0) render "Nothing to Deploy"
		else
		{
			[deploymentDetailsList:deploymentDetails]
		}*/
	}

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond Program.list(params), model:[programInstanceCount: Program.count()]
	}

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
