package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * SecureProgramController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class SecureProgramController {

	// inject Service
	def deploymentService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def deploy() {

		def (secureProgram, commerceObjects) = deploymentService.promoteSecureProgram(params)

		if (secureProgram.size() + commerceObjects.size()==0) render "Nothing to Deploy"
		else
		{
			[secureProgramInstance:secureProgram, commerceObjectsList:commerceObjects]
		}
	}

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond SecureProgram.list(params), model:[secureProgramInstanceCount: SecureProgram.count()]
	}

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
	def save(SecureProgram secureProgramInstance) {
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
