package hmof
import grails.transaction.Transactional;

/**
 * DeploymentService
 * A service class encapsulates the core business logic of a Grails application
 */
@Transactional
class DeploymentService {

	/**
	 * require a program ID and a deployment environment
	 * @return
	 */
	def promoteProgram(params) {

		def bundleList = []
		def targetEnvironment = 'devEnvironment'

		def programInstance = Program.where{ id==params.id }
		log.info "Program to Deploy: " +  programInstance.get()


		def allBundlesBelongingToProgram = Bundle.where{ program{ id==params.id } }
		log.info "All bundles in the Program: " +  allBundlesBelongingToProgram.isbn.list()

		/* Deployable bundles must have Secure Programs and meet datestamp requirements
		 where the environment datestamp (eds) is null or eds is < Bundle instance ds*/
		def bundlesWithSecurePrograms = allBundlesBelongingToProgram.where{

			devEnvironment == null || devEnvironment < lastUpdated && secureProgram{}

		}

		log.info "Bundles with SecurePrograms: " + bundlesWithSecurePrograms.isbn.list()


		// get bundles that meet the criteria
		bundlesWithSecurePrograms.list().each{

			//bundleList<< it.title
			bundleList<< it.isbn
			//bundleList<< it.secureProgram.registrationIsbn
			//bundleList<< it.commerceObjects.objectName

		}

		return bundleList
	}
}
