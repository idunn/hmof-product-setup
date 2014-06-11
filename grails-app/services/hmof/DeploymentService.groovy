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

		//def programInstance = Program.where{ id==params.id }
		//log.info "Program to Deploy: " +  programInstance.get()

		//def allBundlesBelongingToProgram = Bundle.where{ program{ id==params.id } }
		//log.info "All bundles in the Program: " +  allBundlesBelongingToProgram.isbn.list()

		//def distinctSp = SecureProgram.where {}.projections { distinct 'id' }

		// A deployable bundle has a SP and its datestamp for environments is null or < lastupdated
		def deployableBundles = Bundle.where{
			program{ id==params.id}
			devEnvironment == null || devEnvironment < lastUpdated && secureProgram{}
		}
		
		
		// A SP may be changed outside of a bundle the bundle is not marked for promotion but its SP is
		def alldeployableSecureProgramsBelongingToProgram = Bundle.where{ program{ id==params.id }
			secureProgram
			{
				// add date criteria here
				// NOTE, this object is not currently being returned
				// something similar could be done for Commerce Objects
			}

		}

		def (results, secureProgramList) = [deployableBundles.list(), alldeployableSecureProgramsBelongingToProgram.list()] 

	}
}
