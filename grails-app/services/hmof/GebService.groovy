package hmof
import grails.transaction.Transactional

/**
 * GebService
 * A service class encapsulates the core business logic of a Grails application
 */
@Transactional(readOnly=false)
class GebService {
	
	// TODO understand transactional
	//static transactional = false

	// inject Spring Security
	def springSecurityService
	
	@Transactional(readOnly=false)
	def deployCommerceObject(commerceObjectToDeploy) {

		def commerceObjectId =  commerceObjectToDeploy.id
		
		// complete GEB work

		// Update database
		def co = CommerceObject.get(commerceObjectId)
		co.devEnvironment = new Date()		
		co.save(flush: true)
		
		// if success return true
		

	}
}
