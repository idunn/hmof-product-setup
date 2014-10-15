package hmof

import hmof.deploy.*
import hmof.geb.RedPagesDriver
import geb.*

/**
 * JobService
 * Take the jobs and process them by pushing the content to the environment via Geb
 */
class JobService {

	static transactional = false
	def deploymentService
	def commerceObjectService
	def enversQueryService

	/**
	 * Take the jobs and process them by pushing the content to the environment via Geb
	 * @param jobs
	 * @return
	 */
	Boolean processJobs(def jobs, def promotionInstance) {

		try{

			// Get the environment URL
			def deploymentUrl = Environment.where{id==promotionInstance.environmentsId}.url.get()

			log.info "The deployment Url is: " + deploymentUrl

			// Divide out the instances
			def program = jobs.find{it.contentTypeId == 1}
			def bundle = jobs.findAll{it.contentTypeId == 2}
			def secureProgram = jobs.findAll{it.contentTypeId == 3}
			def commerceObject = jobs.findAll{it.contentTypeId == 4}

			// Deploy Commerce Object
			if(!commerceObject.isEmpty()){

				commerceObject.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					def commerceObjectInstance = CommerceObject.where{id==instanceNumber}.get()
					def enversInstanceToDeploy = commerceObjectInstance.findAtRevision(revisionNumber.toInteger())

					// Pass data to Geb
					RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, enversInstanceToDeploy)

				}
			}

			// Deploy Secure Program
			if(!secureProgram.isEmpty()){

				secureProgram.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					def secureProgramInstance = SecureProgram.where{id==instanceNumber}.get()?: enversQueryService.getDeletedObject(instanceNumber, revisionNumber, 3)
					def enversInstanceToDeploy

					if (secureProgramInstance instanceof hmof.SecureProgram){
						enversInstanceToDeploy = secureProgramInstance.findAtRevision(revisionNumber.toInteger())
					}

					// TODO 1
					else{
						log.warn"Promoting deleted Secure Program from Envers"
						def secureProgramMap = createSecureProgramMap(secureProgramInstance)
						enversInstanceToDeploy = new SecureProgram(secureProgramMap)
					}

					// Pass data to Geb
					RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, enversInstanceToDeploy)

				}
			}

			// Deploy Bundle with its child associations
			if (!bundle.isEmpty()){

				bundle.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision
					def mapOfChildren = it.children
					def enversInstanceToDeploy

					log.info "Map Of Children: " + mapOfChildren


					// If instance has been deleted return a GroovyRowResult object from the Envers Audit table
					def bundleInstance = Bundle.where{id==instanceNumber}.get()?: enversQueryService.getDeletedObject(instanceNumber, revisionNumber, 2)

					if (bundleInstance instanceof hmof.Bundle){

						enversInstanceToDeploy = bundleInstance.findAtRevision(revisionNumber.toInteger())
					}
					else{

						log.info"In Groovy SQL"
						// Get the properties we are interested in
						enversInstanceToDeploy = new Bundle(isbn:bundleInstance.ISBN, title:bundleInstance.TITLE, duration:bundleInstance.DURATION, contentType:bundleInstance.CONTENT_TYPE_ID)
					}

					def childMap = [:]

					// Turn map of Strings into map of child objects
					mapOfChildren.each{

						def secureProgramId = it.key
						def secureProgramRev = getRevisionNumber(secureProgramId, secureProgram)

						//TODO 2
						log.debug "secureProgram Id: " + secureProgramId

						def secureProgramInstance = SecureProgram.where{id==secureProgramId}.get()?: enversQueryService.getDeletedObject(instanceNumber, secureProgramRev, 3)
						def spEnversInstance
						if (secureProgramInstance instanceof hmof.SecureProgram){

							spEnversInstance = secureProgramInstance.findAtRevision(revisionNumber.toInteger())
						}

						else{
							log.warn"Promoting deleted Secure Program for Bundle from Envers"							
							def secureProgramMap = createSecureProgramMap(secureProgramInstance)							
							spEnversInstance = new SecureProgram(secureProgramMap)
						}

						def commerceObjectValue = it.value
						List commerceObjectIds = []

						if (commerceObjectValue.contains(",")){
							commerceObjectIds = commerceObjectValue.split(',')
						}else { commerceObjectIds = commerceObjectValue.toList() }

						log.info "CO Size: " +  commerceObjectIds.size()

						def listOfCommerceObjects = []

						commerceObjectIds.each{

							def idValue = it
							def commerceObjectInstance = CommerceObject.where{id==idValue}.get()
							def coEnversInstance = commerceObjectInstance.findAtRevision(revisionNumber.toInteger())
							listOfCommerceObjects << coEnversInstance
						}

						childMap << [(spEnversInstance):listOfCommerceObjects]
						log.info "child Map of Objects sent to Geb: " + childMap

					}

					// Pass data to Geb
					RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, enversInstanceToDeploy, childMap)

					log.info "Finished Deploying Bundle."
				}
			}
		}
		catch(Exception e){

			log.error "Exception deploying content: " + e
			return false

		}


		return true
	}

	/**
	 * Helper method to return the value of the content's revision
	 * @param spId
	 * @param jobList
	 * @return
	 */
	def getRevisionNumber(spId, jobList){

		Long secureProgramId = spId.toLong()

		def revNumber = jobList.find{secureProgramId in it.contentId}.revision
		log.info "Revision Number:  " +  revNumber

		revNumber
	}

	/**
	 * Return a new Secure Program Map from Envers
	 * @param spEnversInstance
	 * @return
	 */
	def createSecureProgramMap(def sp){

		log.debug"Creating new Secure Program Map from Envers"

		def secureProgramMap = [productName:sp.PRODUCT_NAME, registrationIsbn:sp.REGISTRATION_ISBN, onlineIsbn:sp.ONLINE_ISBN, copyright:sp.COPYRIGHT, securityWord:sp.SECURITY_WORD,
			securityWordLocation: sp.SECURITY_WORD_LOCATION, securityWordPage:sp.SECURITY_WORD_PAGE, includeDashboardObject:sp.INCLUDE_DASHBOARD_OBJECT,
			includeEplannerObject:sp.INCLUDE_EPLANNER_OBJECT, knewtonProduct:sp.KNEWTON_PRODUCT, contentType:sp.CONTENT_TYPE_ID]		

	}

}
