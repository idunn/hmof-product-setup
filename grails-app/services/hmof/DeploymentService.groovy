package hmof
import grails.transaction.Transactional;

/**
 * DeploymentService
 * A service class encapsulates the core business logic of a Grails application
 */
@Transactional
class DeploymentService {

	/**
	 * require a program ID and a deployment environment date-stamp
	 * @return
	 */
	def promoteProgram(params) {

		def bundleList = []

		def programId = params.id
		def programToQuery = Program.where{id==programId}
		log.info "Program to Query " +  programToQuery.get()
		def deploymentEnvironmentDateStamp = programToQuery.lastDeployed.get()
		log.info deploymentEnvironmentDateStamp


		def allBundlesFromProgram = Bundle.where{program{id==programId}}
		log.info "All bundles in the Program: " +  allBundlesFromProgram.isbn.list()

		// deployable bundles must have Secure Programs
		def bundlesWithSecurePrograms = allBundlesFromProgram.where{secureProgram{}}
		log.info "Bundles that have a SecureProgram: " + bundlesWithSecurePrograms.isbn.list()

		
		
		
		// get bundle details
		bundlesWithSecurePrograms.list().each{

			log.info "Bundle Title: " + it.title
			bundleList<< it.title
			log.info "Bundle ISBN: " + it.isbn
			bundleList<< it.isbn
			log.info "Secure Program ISBN: " + it.secureProgram.registrationIsbn
			bundleList<< it.secureProgram.registrationIsbn
			log.info "Commerce Object Name: " + it.commerceObjects.objectName
			bundleList<< it.commerceObjects.objectName

		}

		return bundleList


	}
}
