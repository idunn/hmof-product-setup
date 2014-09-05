package hmof.deploy


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * PromotionController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class PromotionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Promotion.list(params), model:[promotionInstanceCount: Promotion.count()]
    }

	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Promotion.list(params), model:[promotionInstanceCount: Promotion.count()]
    }

    def show(Promotion promotionInstance) {
        respond promotionInstance
    }

    def create() {
        respond new Promotion(params)
    }

    @Transactional
    def save(Promotion promotionInstance) {
        if (promotionInstance == null) {
            notFound()
            return
        }

        if (promotionInstance.hasErrors()) {
            respond promotionInstance.errors, view:'create'
            return
        }

        promotionInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'promotionInstance.label', default: 'Promotion'), promotionInstance.id])
                redirect promotionInstance
            }
            '*' { respond promotionInstance, [status: CREATED] }
        }
    }

    def edit(Promotion promotionInstance) {
        respond promotionInstance
    }

    @Transactional
    def update(Promotion promotionInstance) {
        if (promotionInstance == null) {
            notFound()
            return
        }

        if (promotionInstance.hasErrors()) {
            respond promotionInstance.errors, view:'edit'
            return
        }

        promotionInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Promotion.label', default: 'Promotion'), promotionInstance.id])
                redirect promotionInstance
            }
            '*'{ respond promotionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Promotion promotionInstance) {

        if (promotionInstance == null) {
            notFound()
            return
        }

        promotionInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Promotion.label', default: 'Promotion'), promotionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'promotionInstance.label', default: 'Promotion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
