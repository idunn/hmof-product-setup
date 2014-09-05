package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * SecureProgramController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class SecureProgramController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

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
