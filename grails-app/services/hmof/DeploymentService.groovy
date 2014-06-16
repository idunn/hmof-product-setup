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
	 * Returns all the Bundles, Secure Programs and Commerce Objects that meet the criteria
	 * @return
	 */
	def promoteProgram(params) {
		
		// A deployable bundle has a SP and its datestamp for the environment is null or < lastupdated
		def deployableBundles = Bundle.where{
			program{ id==params.id}
			devEnvironment == null || devEnvironment < lastUpdated && secureProgram{}
		}

		// get all Bundles that belong to the Program being deployed that also have a SP
		def allProgramBundles = Bundle.where{ program{ id==params.id }
			secureProgram {}
		}
		
		// get a unique listing of Secure Programs belonging to the Program being deployed
		Set uniqueSp = allProgramBundles.list().secureProgram.id.flatten()
		
		// get a unique listing of Commerce Objects belonging to the Program being deployed
		Set uniqueCo = allProgramBundles.list().secureProgram.commerceObjects.id.flatten()
		
		// deployment logic for SP where datestamp for the environment is null or < lastupdated
		def deployableSp = SecureProgram.where{id in uniqueSp && devEnvironment == null || devEnvironment < lastUpdated }		

		// deployment logic for a Commerce Object where datestamp for the environment is null or < lastupdated
		def deployableCo = CommerceObject.where{id in uniqueCo && devEnvironment == null || devEnvironment < lastUpdated }
		 
		
		// return list of deployable bundles, deployable SP and deployable CO
		def (bundleList, secureProgramList, commerceObjectList) = [deployableBundles.list(), deployableSp.list(), deployableCo.list()]

	}
}
