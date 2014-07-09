package hmof.deploy


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * Program2Controller
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class Program2Controller {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Program2.list(params), model:[program2InstanceCount: Program2.count()]
    }

	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Program2.list(params), model:[program2InstanceCount: Program2.count()]
    }

    def show(Program2 program2Instance) {
        respond program2Instance
    }

    def create() {
        respond new Program2(params)
    }

    @Transactional
    def save(Program2 program2Instance) {
        if (program2Instance == null) {
            notFound()
            return
        }

        if (program2Instance.hasErrors()) {
            respond program2Instance.errors, view:'create'
            return
        }

        program2Instance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'program2Instance.label', default: 'Program2'), program2Instance.id])
                redirect program2Instance
            }
            '*' { respond program2Instance, [status: CREATED] }
        }
    }

    def edit(Program2 program2Instance) {
        respond program2Instance
    }

    @Transactional
    def update(Program2 program2Instance) {
        if (program2Instance == null) {
            notFound()
            return
        }

        if (program2Instance.hasErrors()) {
            respond program2Instance.errors, view:'edit'
            return
        }

        program2Instance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Program2.label', default: 'Program2'), program2Instance.id])
                redirect program2Instance
            }
            '*'{ respond program2Instance, [status: OK] }
        }
    }

    @Transactional
    def delete(Program2 program2Instance) {

        if (program2Instance == null) {
            notFound()
            return
        }

        program2Instance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Program2.label', default: 'Program2'), program2Instance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'program2Instance.label', default: 'Program2'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
