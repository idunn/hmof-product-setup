package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * CobjController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class CobjController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cobj.list(params), model:[cobjInstanceCount: Cobj.count()]
    }

	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cobj.list(params), model:[cobjInstanceCount: Cobj.count()]
    }

    def show(Cobj cobjInstance) {
        respond cobjInstance
    }

    def create() {
        respond new Cobj(params)
    }

    @Transactional
    def save(Cobj cobjInstance) {
        if (cobjInstance == null) {
            notFound()
            return
        }

        if (cobjInstance.hasErrors()) {
            respond cobjInstance.errors, view:'create'
            return
        }

        cobjInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cobjInstance.label', default: 'Cobj'), cobjInstance.id])
                redirect cobjInstance
            }
            '*' { respond cobjInstance, [status: CREATED] }
        }
    }

    def edit(Cobj cobjInstance) {
        respond cobjInstance
    }

    @Transactional
    def update(Cobj cobjInstance) {
        if (cobjInstance == null) {
            notFound()
            return
        }

        if (cobjInstance.hasErrors()) {
            respond cobjInstance.errors, view:'edit'
            return
        }

        cobjInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Cobj.label', default: 'Cobj'), cobjInstance.id])
                redirect cobjInstance
            }
            '*'{ respond cobjInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Cobj cobjInstance) {

        if (cobjInstance == null) {
            notFound()
            return
        }

        cobjInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Cobj.label', default: 'Cobj'), cobjInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cobjInstance.label', default: 'Cobj'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
