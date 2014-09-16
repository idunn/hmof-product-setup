package hmof

import hmof.deploy.*
import geb.*

/**
 * JobService
 * A service class encapsulates the core business logic of a Grails application
 */
class JobService {

	static transactional = false //TODO
	def deploymentService
	def commerceObjectService

	/**
	 * Take the jobs and process them by pushing the content to the environment via Geb
	 * @param jobs
	 * @return
	 */
	Boolean processJobs(def jobs, def promotionInstance) {

		try{

			// Get the environment URL
			def deploymentUrl = Environment.where{id==promotionInstance.environmentsId}.url.get()


			// Divide out the instances
			def program = jobs.find{it.contentTypeId == 1}
			def bundle = jobs.findAll{it.contentTypeId == 2}
			def secureProgram = jobs.findAll{it.contentTypeId == 3}
			def commerceObject = jobs.findAll{it.contentTypeId == 4}

			// Deploy Content C
			if(!commerceObject.isEmpty()){

				commerceObject.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					def commerceObjectInstance = CommerceObject.where{id==instanceNumber}.get()
					def EnversInstanceToDeploy = commerceObjectInstance.findAtRevision(revisionNumber.toInteger())

					// TODO WORK on this
					//commerceObjectService.loginToRedPages( testUrl )
					
					// Pass data to Geb
					RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, EnversInstanceToDeploy)

				}
			}

			// Deploy Secure Program
			if(!secureProgram.isEmpty()){

				secureProgram.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					def secureProgramInstance = SecureProgram.where{id==instanceNumber}.get()
					def EnversInstanceToDeploy = secureProgramInstance.findAtRevision(revisionNumber.toInteger())

					// Pass data to Geb
					println "Mock deployment of Secure Program " + deploymentUrl
					println EnversInstanceToDeploy.productName
					println EnversInstanceToDeploy.registrationIsbn
					println "Finished Deploying Secure Program."

				}
			}

			// Deploy Bundle with its associations
			if (!bundle.isEmpty()){

				bundle.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					def bundleInstance = Bundle.where{id==instanceNumber}.get()
					def EnversInstanceToDeploy = bundleInstance.findAtRevision(revisionNumber.toInteger())

					// only one of these variables are used //TODO
					def (bundle_Children, content_C) = deploymentService.getBundleChildren(bundleInstance.id)

					// pass to Geb
					println "Mock deployment of Bundle to: " + deploymentUrl
					println EnversInstanceToDeploy.isbn
					println EnversInstanceToDeploy.title


					// Add Content SP to Bundle
					bundle_Children.each{

						println "Add Secure Program: " + it.productName + " " + it.registrationIsbn

						Long instanceId = it.id
						def secureProgramInstance = SecureProgram.where{id==instanceId}.get()
						def (secureProgram_Children) = deploymentService.getSecureProgramChildren(secureProgramInstance.id)

						// Add C to B indirectly A
						secureProgram_Children.each{

							println "Add Commerce Object: " + it.objectName + " " + it.isbn

						}
					}

					println "Finished Deploying Bundle."
				}
			}
		}
		catch(Exception e){

			println "Exception deploying content: " + e
			return false

		}


		return true
	}
}
