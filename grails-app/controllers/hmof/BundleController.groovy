package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * BundleController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class BundleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Bundle.list(params), model:[bundleInstanceCount: Bundle.count()]
    }

	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Bundle.list(params), model:[bundleInstanceCount: Bundle.count()]
    }

    def show(Bundle bundleInstance) {
        respond bundleInstance
    }

    def create() {
        respond new Bundle(params)
    }

    @Transactional
    def save(Bundle bundleInstance) {
        if (bundleInstance == null) {
            notFound()
            return
        }

        if (bundleInstance.hasErrors()) {
            respond bundleInstance.errors, view:'create'
            return
        }

        bundleInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bundleInstance.label', default: 'Bundle'), bundleInstance.id])
                redirect bundleInstance
            }
            '*' { respond bundleInstance, [status: CREATED] }
        }
    }

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

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Bundle.label', default: 'Bundle'), bundleInstance.id])
                redirect bundleInstance
            }
            '*'{ respond bundleInstance, [status: OK] }
        }
    }

    @Transactional
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
