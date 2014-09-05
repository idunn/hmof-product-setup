package hmof.deploy


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * JobController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class JobController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Job.list(params), model:[jobInstanceCount: Job.count()]
    }

	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Job.list(params), model:[jobInstanceCount: Job.count()]
    }

    def show(Job jobInstance) {
        respond jobInstance
    }

    def create() {
        respond new Job(params)
    }

    @Transactional
    def save(Job jobInstance) {
        if (jobInstance == null) {
            notFound()
            return
        }

        if (jobInstance.hasErrors()) {
            respond jobInstance.errors, view:'create'
            return
        }

        jobInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'jobInstance.label', default: 'Job'), jobInstance.id])
                redirect jobInstance
            }
            '*' { respond jobInstance, [status: CREATED] }
        }
    }

    def edit(Job jobInstance) {
        respond jobInstance
    }

    @Transactional
    def update(Job jobInstance) {
        if (jobInstance == null) {
            notFound()
            return
        }

        if (jobInstance.hasErrors()) {
            respond jobInstance.errors, view:'edit'
            return
        }

        jobInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Job.label', default: 'Job'), jobInstance.id])
                redirect jobInstance
            }
            '*'{ respond jobInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Job jobInstance) {

        if (jobInstance == null) {
            notFound()
            return
        }

        jobInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Job.label', default: 'Job'), jobInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'jobInstance.label', default: 'Job'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
