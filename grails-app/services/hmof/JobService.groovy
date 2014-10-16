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

					def commerceObjectInstance = CommerceObject.where{id==instanceNumber}.get()?: enversQueryService.getDeletedObject(instanceNumber, revisionNumber, 4)
					def enversInstanceToDeploy

					if (commerceObjectInstance instanceof hmof.CommerceObject){
						log.info"In normal deploy/promote for CO."
						enversInstanceToDeploy = commerceObjectInstance.findAtRevision(revisionNumber.toInteger())
					}

					else{
						log.warn"Promoting deleted Commerce Object from Envers"
						def commerceObjectMap = createCommerceObjectMap(commerceObjectInstance)
						enversInstanceToDeploy = new CommerceObject(commerceObjectMap)
					}

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

						log.warn"Promoting deleted Bundle from Envers"
						// Get the properties we are interested in
						enversInstanceToDeploy = new Bundle(isbn:bundleInstance.ISBN, title:bundleInstance.TITLE, duration:bundleInstance.DURATION, contentType:bundleInstance.CONTENT_TYPE_ID)
					}

					def childMap = [:]

					// Turn map of Strings into map of content child objects
					mapOfChildren.each{

						def secureProgramId = it.key
						def secureProgramRev = getRevisionNumber(secureProgramId, secureProgram)


						log.debug "secureProgram Id: " + secureProgramId

						def secureProgramInstance = SecureProgram.where{id==secureProgramId}.get()?: enversQueryService.getDeletedObject(secureProgramId, secureProgramRev, 3)
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

						log.info "Commerce Object Size: " +  commerceObjectIds.size()

						def listOfCommerceObjects = []

						commerceObjectIds.each{

							def commerceObjectId = it
							def commerceObjectRev = getRevisionNumber(commerceObjectId, commerceObject)

							def commerceObjectInstance = CommerceObject.where{id==commerceObjectId}.get()?: enversQueryService.getDeletedObject(commerceObjectId, commerceObjectRev, 4)
							def coEnversInstance

							if (commerceObjectInstance instanceof hmof.CommerceObject){
								coEnversInstance = commerceObjectInstance.findAtRevision(revisionNumber.toInteger())
							}

							else{
								log.warn"Promoting deleted Commerce Object for Bundle from Envers"
								def commerceObjectMap = createCommerceObjectMap(commerceObjectInstance)
								coEnversInstance = new CommerceObject(commerceObjectMap)
							}

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
	 * @param contentId
	 * @param jobList
	 * @return
	 */
	def getRevisionNumber(contentId, jobList){

		Long contentIdNumber = contentId.toLong()

		def revNumber = jobList.find{contentIdNumber in it.contentId}.revision
		log.info "Revision Number:  " +  revNumber

		revNumber
	}

	/**
	 * Return a new Secure Program Map from Envers to deal with scenarios where the content was deleted between deployment and promotion
	 * @param spEnversInstance
	 * @return
	 */
	def createSecureProgramMap(def sp){

		log.warn"Creating new Secure Program Map from Envers"
		// TODO update with new fields when Domain is updated - hopefully never!

		def secureProgramMap = [productName:sp.PRODUCT_NAME, registrationIsbn:sp.REGISTRATION_ISBN, onlineIsbn:sp.ONLINE_ISBN, copyright:sp.COPYRIGHT, labelForOnlineResource:sp.LABEL_FOR_ONLINE_RESOURCE,
			pathToResource:sp.PATH_TO_RESOURCE, pathToCoverImage:sp.PATH_TO_COVER_IMAGE, labelForTeacherAdditionalResource:sp.LABEL_FOR_TEACHER_ADDITIONAL_RESOURCE,
			pathToTeacherAdditionalResource:sp.PATH_TO_TEACHER_ADDITIONAL_RESOURCE,labelForStudentAdditionalResource:sp.LABEL_FOR_STUDENT_ADDITIONAL_RESOURCE,
			pathToStudentAdditionalResource:sp.PATH_TO_STUDENT_ADDITIONAL_RESOURCE,securityWord:sp.SECURITY_WORD, securityWordLocation:sp.SECURITY_WORD_LOCATION,
			securityWordPage:sp.SECURITY_WORD_PAGE, includeDashboardObject:sp.INCLUDE_DASHBOARD_OBJECT, includeEplannerObject:sp.INCLUDE_EPLANNER_OBJECT, knewtonProduct:sp.KNEWTON_PRODUCT,
			knowledgeGraphIdDev:sp.KNOWLEDGE_GRAPH_ID_DEV, knowledgeGraphIdQA:sp.KNOWLEDGE_GRAPH_IDQA, knowledgeGraphIdProd:sp.KNOWLEDGE_GRAPH_ID_PROD,
			knowledgeGraphWarmUpTimeLimit:sp.KNOWLEDGE_GRAPH_WARM_UP_TIME_LIMIT, knowledgeGraphEnrichmentTimeLimit:sp.KNOWLEDGE_GRAPH_ENRICHMENT_TIME_LIMIT,
			knowledgeGraphEnrichmentCbiTimeLimit:sp.KNOWLEDGE_GRAPH_ENRICHMENT_CBI_TIME_LIMIT,comments:sp.COMMENTS, curriculumArea:sp.CURRICULUM_AREA,
			essayGraderPrompts:sp.ESSAY_GRADER_PROMPTS, labelForTeacherAdditionalResource2:sp.LABEL_FOR_TEACHER_ADDITIONAL_RESOURCE2,
			pathToTeacherAdditionalResource2:sp.PATH_TO_TEACHER_ADDITIONAL_RESOURCE2, labelForStudentAdditionalResource2:sp.LABEL_FOR_STUDENT_ADDITIONAL_RESOURCE2,
			pathToStudentAdditionalResource2:sp.PATH_TO_STUDENT_ADDITIONAL_RESOURCE2,labelForTeacherAdditionalResource3:sp.LABEL_FOR_TEACHER_ADDITIONAL_RESOURCE3,
			pathToTeacherAdditionalResource3:sp.PATH_TO_TEACHER_ADDITIONAL_RESOURCE3,labelForStudentAdditionalResource3:sp.LABEL_FOR_STUDENT_ADDITIONAL_RESOURCE3,
			pathToStudentAdditionalResource3:sp.PATH_TO_STUDENT_ADDITIONAL_RESOURCE3,labelForTeacherAdditionalResource4:sp.LABEL_FOR_TEACHER_ADDITIONAL_RESOURCE4,
			pathToTeacherAdditionalResource4:sp.PATH_TO_TEACHER_ADDITIONAL_RESOURCE4,labelForStudentAdditionalResource4:sp.LABEL_FOR_STUDENT_ADDITIONAL_RESOURCE4,
			pathToStudentAdditionalResource4:sp.PATH_TO_STUDENT_ADDITIONAL_RESOURCE4,securityWord2:sp.SECURITY_WORD2, securityWordLocation2:sp.SECURITY_WORD_LOCATION2,
			securityWordPage2:sp.SECURITY_WORD_PAGE2,	 securityWord3:sp.SECURITY_WORD3,securityWordLocation3:sp.SECURITY_WORD_LOCATION3, securityWordPage3:sp.SECURITY_WORD_PAGE3,
			contentType:sp.CONTENT_TYPE_ID]

	}

	/**
	 * Return a new Commerce Object Map from Envers to deal with scenarios where the content was deleted between deployment and promotion
	 * @param co
	 * @return
	 */
	def createCommerceObjectMap(def co){

		log.warn"Creating new Commerce Object Map from Envers"
		// TODO update with new fields
		def commerceObjectMap = [objectName:co.OBJECT_NAME,isbnNumber:co.ISBN_NUMBER,pathToCoverImage:co.PATH_TO_COVER_IMAGE,teacherLabel:co.TEACHER_LABEL,
			teacherUrl:co.TEACHER_URL, studentLabel:co.STUDENT_LABEL, studentUrl:co.STUDENT_URL, objectType:co.OBJECT_TYPE, objectReorderNumber:co.OBJECT_REORDER_NUMBER,
			gradeLevel:co.GRADE_LEVEL, comments:co.COMMENTS,contentType:co.CONTENT_TYPE_ID]

	}

}
