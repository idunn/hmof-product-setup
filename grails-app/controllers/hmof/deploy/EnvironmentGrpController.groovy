package hmof.deploy


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * EnvironmentGrpController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class EnvironmentGrpController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EnvironmentGrp.list(params), model:[environmentGrpInstanceCount: EnvironmentGrp.count()]
    }

	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EnvironmentGrp.list(params), model:[environmentGrpInstanceCount: EnvironmentGrp.count()]
    }

    def show(EnvironmentGrp environmentGrpInstance) {
        respond environmentGrpInstance
    }

    def create() {
        respond new EnvironmentGrp(params)
    }

    @Transactional
    def save(EnvironmentGrp environmentGrpInstance) {
        if (environmentGrpInstance == null) {
            notFound()
            return
        }

        if (environmentGrpInstance.hasErrors()) {
            respond environmentGrpInstance.errors, view:'create'
            return
        }

        environmentGrpInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'environmentGrpInstance.label', default: 'EnvironmentGrp'), environmentGrpInstance.id])
                redirect environmentGrpInstance
            }
            '*' { respond environmentGrpInstance, [status: CREATED] }
        }
    }

    def edit(EnvironmentGrp environmentGrpInstance) {
        respond environmentGrpInstance
    }

    @Transactional
    def update(EnvironmentGrp environmentGrpInstance) {
        if (environmentGrpInstance == null) {
            notFound()
            return
        }

        if (environmentGrpInstance.hasErrors()) {
            respond environmentGrpInstance.errors, view:'edit'
            return
        }

        environmentGrpInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'EnvironmentGrp.label', default: 'EnvironmentGrp'), environmentGrpInstance.id])
                redirect environmentGrpInstance
            }
            '*'{ respond environmentGrpInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(EnvironmentGrp environmentGrpInstance) {

        if (environmentGrpInstance == null) {
            notFound()
            return
        }

        environmentGrpInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'EnvironmentGrp.label', default: 'EnvironmentGrp'), environmentGrpInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'environmentGrpInstance.label', default: 'EnvironmentGrp'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
