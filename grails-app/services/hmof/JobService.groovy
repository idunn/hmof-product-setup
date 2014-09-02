package hmof

import hmof.deploy.*

/**
 * JobService
 * A service class encapsulates the core business logic of a Grails application
 */
class JobService {

	//static transactional = false
	def deploymentService

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
			def commernceObject = jobs.findAll{it.contentTypeId == 4}

			// Deploy Content C
			if(!commernceObject.isEmpty()){

				commernceObject.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					def commernceObjectInstance = CommernceObject.where{id==instanceNumber}.get()
					def EnversInstanceToDeploy = commernceObjectInstance.findAtRevision(revisionNumber.toInteger())

					// Pass data to Geb
					println "Mock deployment of Content C to: " + deploymentUrl
					println EnversInstanceToDeploy.name
					println EnversInstanceToDeploy.description
					println "Finished Deploying Content C."

				}
			}

			// Deploy Content B
			if(!secureProgram.isEmpty()){

				secureProgram.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					def secureProgramInstance = SecureProgram.where{id==instanceNumber}.get()
					def EnversInstanceToDeploy = secureProgramInstance.findAtRevision(revisionNumber.toInteger())

					// Pass data to Geb
					println "Mock deployment of Content B to " + deploymentUrl
					println EnversInstanceToDeploy.teacherUrl
					println EnversInstanceToDeploy.isbn
					println "Finished Deploying Content B."

				}
			}

			// Deploy Content A with its associations
			if (!bundle.isEmpty()){

				bundle.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					def bundleInstance = Bundle.where{id==instanceNumber}.get()
					def EnversInstanceToDeploy = bundleInstance.findAtRevision(revisionNumber.toInteger())

					// only one of these variables are used //TODO
					def (content_A_Children, content_C) = deploymentService.getBundleChildren(BundleInstance.id)

					// pass to Geb
					println "Mock deployment of Content A to: " + deploymentUrl
					println EnversInstanceToDeploy.isbn
					println EnversInstanceToDeploy.name

					println "############################4"
					// Add Content B to A
					content_A_Children.each{

						println "Add Content B: " + it.name

						Long instanceId = it.id
						def secureProgramInstance = SecureProgram.where{id==instanceId}.get()
						def (content_B_Children) = deploymentService.getSecureProgramChildren(secureProgramInstance.id)

						// Add C to B indirectly A
						content_B_Children.each{

							println "Add Content C: " + it.name

						}
					}

					println "Finished Deploying Content A."
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
