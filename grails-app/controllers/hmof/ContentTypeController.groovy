package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * ContentTypeController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class ContentTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ContentType.list(params), model:[contentTypeInstanceCount: ContentType.count()]
    }

	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ContentType.list(params), model:[contentTypeInstanceCount: ContentType.count()]
    }

    def show(ContentType contentTypeInstance) {
        respond contentTypeInstance
    }

    def create() {
        respond new ContentType(params)
    }

    @Transactional
    def save(ContentType contentTypeInstance) {
        if (contentTypeInstance == null) {
            notFound()
            return
        }

        if (contentTypeInstance.hasErrors()) {
            respond contentTypeInstance.errors, view:'create'
            return
        }

        contentTypeInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contentTypeInstance.label', default: 'ContentType'), contentTypeInstance.id])
                redirect contentTypeInstance
            }
            '*' { respond contentTypeInstance, [status: CREATED] }
        }
    }

    def edit(ContentType contentTypeInstance) {
        respond contentTypeInstance
    }

    @Transactional
    def update(ContentType contentTypeInstance) {
        if (contentTypeInstance == null) {
            notFound()
            return
        }

        if (contentTypeInstance.hasErrors()) {
            respond contentTypeInstance.errors, view:'edit'
            return
        }

        contentTypeInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ContentType.label', default: 'ContentType'), contentTypeInstance.id])
                redirect contentTypeInstance
            }
            '*'{ respond contentTypeInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ContentType contentTypeInstance) {

        if (contentTypeInstance == null) {
            notFound()
            return
        }

        contentTypeInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ContentType.label', default: 'ContentType'), contentTypeInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contentTypeInstance.label', default: 'ContentType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
