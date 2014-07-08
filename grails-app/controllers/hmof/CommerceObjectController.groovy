package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

/**
 * CommerceObjectController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class CommerceObjectController {

	def deploymentService
	def gebService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	@Transactional
	def deploy() {

		def (commerceObject) = deploymentService.promoteCommerceObject(params)

		if (commerceObject.size()==0) render "Nothing to Deploy"
		else
		{
			// Pass to Geb - possibly return true
			def deploymentJob = gebService.deployCommerceObject(commerceObject)		
			
			// pass control back to index
			redirect(action: 'index')
			//chain action: 'index'
			
			// send detached criteria to view
			//[commerceObjectInstance:commerceObject]
		}
	}

	def index(Integer max) {
		// This was the same as the list action
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond CommerceObject.list(params), model:[commerceObjectInstanceCount: CommerceObject.count()]
	}

	def show(CommerceObject commerceObjectInstance) {
		respond commerceObjectInstance
	}

	def create() {
		respond new CommerceObject(params)
	}

	@Transactional
	def save(CommerceObject commerceObjectInstance) {
		if (commerceObjectInstance == null) {
			notFound()
			return
		}

		if (commerceObjectInstance.hasErrors()) {
			respond commerceObjectInstance.errors, view:'create'
			return
		}

		commerceObjectInstance.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'commerceObjectInstance.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect commerceObjectInstance
			}
			'*' { respond commerceObjectInstance, [status: CREATED] }
		}
	}

	def edit(CommerceObject commerceObjectInstance) {
		respond commerceObjectInstance
	}

	@Transactional
	def update(CommerceObject commerceObjectInstance) {
		if (commerceObjectInstance == null) {
			notFound()
			return
		}

		if (commerceObjectInstance.hasErrors()) {
			respond commerceObjectInstance.errors, view:'edit'
			return
		}

		commerceObjectInstance.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'CommerceObject.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect commerceObjectInstance
			}
			'*'{ respond commerceObjectInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(CommerceObject commerceObjectInstance) {

		if (commerceObjectInstance == null) {
			notFound()
			return
		}

		commerceObjectInstance.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'CommerceObject.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'commerceObjectInstance.label', default: 'CommerceObject'), params.id])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
