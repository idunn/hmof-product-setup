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

	/**
	 * Promote an individual Bundle
	 * @param params
	 * @return
	 */
	def promoteBundle(params) {

		// A deployable bundle has a SP and its datestamp for the environment is null or < lastupdated
		def deployableBundle = Bundle.where{
			id==params.id && devEnvironment == null || devEnvironment < lastUpdated && secureProgram{}
		}

		// get a listing of Secure Programs belonging to the Bundle being deployed
		Set uniqueSp = deployableBundle.list().secureProgram.id.flatten()
		def deployableSp = SecureProgram.where{id in uniqueSp && devEnvironment == null || devEnvironment < lastUpdated }

		// get a unique listing of Commerce Objects belonging to the Secure Program being deployed
		Set uniqueCo = deployableSp.list().commerceObjects.id.flatten()
		def deployableCo = CommerceObject.where{id in uniqueCo && devEnvironment == null || devEnvironment < lastUpdated }

		// return Deployable Bundle, SP and CO
		def (bundleInstance, secureProgramList, commerceObjectList) = [deployableBundle.list(), deployableSp.list(), deployableCo.list()]

	}

	/**
	 * Promote SP
	 * @param params
	 * @return
	 */
	def promoteSecureProgram(params) {

		def secureProgram = SecureProgram.where{id==params.id}

		// SP and its datestamp for the environment is null or < lastupdated
		def deployableSp = secureProgram.where{devEnvironment == null || devEnvironment < lastUpdated}

		def commerceObjects = secureProgram.list().commerceObjects.id.flatten()

		def deployableCo = CommerceObject.where{id in commerceObjects
			//&& devEnvironment == null || devEnvironment < lastUpdated
		}

		// return Deployable Bundle, SP and CO
		def (secureProgramInstance, commerceObjectList) = [deployableSp.list(), deployableCo.list()]

	}


	/**
	 * Promote CO
	 * @param params
	 * @return
	 */
	def promoteCommerceObject(params) {

		def commerceObject = CommerceObject.where{id==params.id}

		// SP and its datestamp for the environment is null or < lastupdated
		def deployableCo = commerceObject.where{
			// TODO add dev environment
			devEnvironment == null || devEnvironment < lastUpdated
		}

		// return Deployable CO
		def (commerceObjectInstance) = [deployableCo.list()]

	}


	def promoteCommerceObjectTest(params) {

		def deploymentEnv = "dev"
		def commerceObject = CommerceObject.where{id==params.id}
		def deployableCommerceObject

		if(deploymentEnv == "dev"){
			println "dev"

			// SP and its datestamp for the environment is null or < lastupdated
			deployableCommerceObject = commerceObject.where{
				devEnvironment == null || devEnvironment < lastUpdated
			}
		}
		else if (deploymentEnv == "qa"){
			println "qa"
			deployableCommerceObject = commerceObject.where{
				devEnvironment != null && devEnvironment >= lastUpdated
				//&& qaEnvironment == null || < lastUpdated
			}
		}
		else if (deploymentEnv == "prod"){
			println "prod"
			devEnvironment != null && devEnvironment >= lastUpdated
			//&& qaEnvironment != null && qaEnvironment >= lastUpdated
			//&& prodEnvironment == null || < lastUpdated

		}

		def result = deployableCommerceObject.list()

	}


}
