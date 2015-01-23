package hmof

import hmof.deploy.*
import hmof.geb.RedPagesDriver
import geb.*

import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator

import grails.util.Holders
import hmof.security.User


/**
 * JobService
 * Take the jobs and process them by pushing the content to the environment via Geb
 */
class JobService{

	static transactional = false
	def deploymentService
	def commerceObjectService
	def utilityService
	def springSecurityService

	
	Logger log = Logger.getLogger(JobService.class)

	/**
	 * Take the jobs and process them by pushing the content to the environment via Geb
	 * @param jobs
	 * @return
	 */
	Boolean processJobs(def jobs, def promotionInstance) {
		String cObjectName=""
		String spProductName=""
		String bundleName=""
		def isbn=""
		def secureIsbn=""
		def bundleIsbn=""
		String programName=""
		Logger new_log=null

		def bundlesToRemove = []

		try{
			promotionInstance.refresh()
			// Get the environment URL
			def environmentInstance = Environment.where{id==promotionInstance.environmentsId}.get()
			def envId = environmentInstance.id
			def deploymentUrl = environmentInstance.url
			def envName = environmentInstance.name
			def user_Name = User.where{id == promotionInstance.userId}.username.get()

			// Divide out the instances
			def program = jobs.findAll{it.contentTypeId == 1}
			def bundle = jobs.findAll{it.contentTypeId == 2}
			def secureProgram = jobs.findAll{it.contentTypeId == 3}
			def commerceObject = jobs.findAll{it.contentTypeId == 4}

			def cacheLocation = Holders.config.cacheLocation

			log.debug "cacheLocation" + cacheLocation
			log.debug "The deployment Url is: " + deploymentUrl

			// used in external logs
			if (!program.isEmpty()){

				program.each{
					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					Long jobNumber = it.jobNumber
					// If instance has been deleted return a GroovyRowResult object from the Envers Audit table
					def programInstance = Program.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 1)

					programName = programInstance.toString()
def smartDeploy=promotionInstance.smartDeploy
println smartDeploy
					// checking if this Program has been pushed before to the environment TODO
					println "#############################################1"
					println "instanceNumber" + instanceNumber
					println "jobNumber" + jobNumber
					println "programName" + programName
					println "envirnment Id" + envId

							
					
					def previousJob = deploymentService.getPreviousJob( instanceNumber, jobNumber, envId )

					if (!previousJob.isEmpty() && smartDeploy){

						println "A previous Job Exists"
						println "The User will be given an opporunity to do a smart deployment..."
						// TODO give Users a modal window to confirm the difference


						// TODO compare the bundles in this job to the previous jobs bundles
						bundlesToRemove = deploymentService.compareJobs( bundle, previousJob )
						println "Bundle IDs to remove: " + bundlesToRemove.contentId + "at revision: " + bundlesToRemove.revision

						// if User wants to be smart - uncomment for testing
						bundle = bundle - bundlesToRemove

					}

					println "#############################################2"

					new_log = initializeLogger(programName, cacheLocation,envId,1)
					if(envId==1){
						new_log.info"${'*'.multiply(40)} Job Creation ${'*'.multiply(40)}\r\n"
						new_log.info("Job "+jobNumber+" was created by user "+user_Name+" for Environment "+envName+"\r\n")
						new_log.info("TEST1 checking if a previous Job exists and returning its job instances" + previousJob)
						new_log.info "TEST2 Bundles to Remove " + bundlesToRemove
						new_log.info "TEST3 Bundle IDs in current Job " + bundlesToRemove
					}else if(envId==2 || envId==3){
						new_log.info"${'*'.multiply(40)} Job Promotion ${'*'.multiply(40)}\r\n"
						new_log.info("Job "+jobNumber+" was promoted by user "+user_Name+" for Environment "+envName+"\r\n")

					}
					if(new_log==null) new_log = log
				}
			}

			if (program.isEmpty() && bundle.isEmpty() && !secureProgram.isEmpty()){

				secureProgram.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision
					Long jobNumber = it.jobNumber
					def secureProgramInstance = SecureProgram.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 3)

					secureIsbn = secureProgramInstance.registrationIsbn

					new_log = initializeLogger(secureIsbn, cacheLocation,envId,3)
					if(envId==1){
						new_log.info"${'*'.multiply(40)} Job Creation ${'*'.multiply(40)}\r\n"
						new_log.info("Job "+jobNumber+" was created by user "+user_Name+" for Environment "+envName+"\r\n")
						//log.info("Job "+idCreatedOrPromoted+" was created with ID="+idCreatedOrPromoted+" by user \n")
					}else if(envId==2 || envId==3){
						new_log.info"${'*'.multiply(40)} Job Promotion ${'*'.multiply(40)}\r\n"
						new_log.info("Job "+jobNumber+" was promoted by user "+user_Name+" for Environment "+envName+"\r\n")

					}
					if(new_log==null) new_log = log
				}
			}

			// Deploy Commerce Object
			if(!commerceObject.isEmpty()){

				commerceObject.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision
					Long jobNumber = it.jobNumber
					def commerceObjectInstance = CommerceObject.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 4)

					def enversInstanceToDeploy

					if (commerceObjectInstance instanceof hmof.CommerceObject){
						log.debug "In normal deploy/promote for CO."
						enversInstanceToDeploy = commerceObjectInstance.findAtRevision(revisionNumber.toInteger())
						cObjectName=enversInstanceToDeploy.toString()
						isbn=commerceObjectInstance.isbnNumber

					}

					else{
						log.warn"Promoting deleted Commerce Object from Envers"
						def commerceObjectMap = createCommerceObjectMap(commerceObjectInstance)
						enversInstanceToDeploy = new CommerceObject(commerceObjectMap)
						isbn=commerceObjectInstance.isbnNumber
					}

					if (program.isEmpty() && bundle.isEmpty() && secureProgram.isEmpty()){
						new_log = initializeLogger(isbn,cacheLocation,envId,4)
						if(envId==1){
							new_log.info"${'*'.multiply(40)} Job Creation ${'*'.multiply(40)}\r\n"
							new_log.info("Job "+jobNumber+" was created by user "+user_Name+" for Environment "+envName+"\r\n")
						}else if(envId==2 || envId==3){
							new_log.info"${'*'.multiply(40)} Job Promotion ${'*'.multiply(40)}\r\n"
							new_log.info("Job "+jobNumber+" was promoted by user "+user_Name+" for Environment "+envName+"\r\n")

						}
					}
					if(new_log==null) new_log = log
					// Pass data to Geb
					RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, enversInstanceToDeploy,new_log)
					new_log.info "${'*'.multiply(40)} Finished Deploying Commerce Object ${'*'.multiply(40)}\r\n"
					if(rpd && program.isEmpty() && bundle.isEmpty() && secureProgram.isEmpty()){
						new_log.info"${'*'.multiply(40)} Status ${'*'.multiply(40)}\r\n"
						new_log.debug("promotionId:"+promotionInstance.id)
						new_log.info("Job Status: Success\r\n")

					}
				}
			}

			// Deploy Secure Program
			if(!secureProgram.isEmpty()){

				secureProgram.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision
					Long jobNumber = it.jobNumber
					def secureProgramInstance = SecureProgram.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 3)
					def enversInstanceToDeploy

					if (secureProgramInstance instanceof hmof.SecureProgram){
						enversInstanceToDeploy = secureProgramInstance.findAtRevision(revisionNumber.toInteger())
						spProductName=enversInstanceToDeploy.toString()

						secureIsbn=secureProgramInstance.registrationIsbn
					}

					else{
						log.warn"Promoting deleted Secure Program from Envers"
						def secureProgramMap = createSecureProgramMap(secureProgramInstance)
						enversInstanceToDeploy = new SecureProgram(secureProgramMap)
						secureIsbn=secureProgramInstance.registrationIsbn
					}

					// Pass data to Geb
					RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, enversInstanceToDeploy,new_log)
					new_log.info "${'*'.multiply(40)} Finished Deploying Secure Program ${'*'.multiply(40)}\r\n"
					if(rpd && program.isEmpty() && bundle.isEmpty()){

						new_log.info"${'*'.multiply(40)} Status ${'*'.multiply(40)}\r\n"
						new_log.debug("promotionId:"+promotionInstance.id)
						new_log.info("Job Status: Success\r\n")

					}
				}
			}


			// Deploy Bundle with its child associations
			if (!bundle.isEmpty()){

				bundle.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision
					def mapOfChildren = it.children
					Long jobNumber = it.jobNumber
					def benversInstanceToDeploy
					def childMap = [:]


					new_log.info"${'*'.multiply(40)} Bundles and Associations ${'*'.multiply(40)}\r\n"
					log.debug "Map Of Children: " + mapOfChildren


					// If instance has been deleted return a GroovyRowResult object from the Envers Audit table
					def bundleInstance = Bundle.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 2)

					if (bundleInstance instanceof hmof.Bundle){

						benversInstanceToDeploy = bundleInstance.findAtRevision(revisionNumber.toInteger())
						bundleName=benversInstanceToDeploy.toString()
						bundleIsbn=bundleInstance.isbn
					}
					else{

						log.warn"Promoting deleted Bundle from Envers"
						// Get the properties we are interested in
						benversInstanceToDeploy = new Bundle(isbn:bundleInstance.ISBN, title:bundleInstance.TITLE, duration:bundleInstance.DURATION, includePremiumCommerceObjects:bundleInstance.INCLUDE_PREMIUM_COMMERCE_OBJECTS, contentType:bundleInstance.CONTENT_TYPE_ID)
						bundleIsbn=bundleInstance.isbn
					}

					if(program.isEmpty() && !bundle.isEmpty()){
						new_log = initializeLogger(bundleIsbn, cacheLocation,envId,2)
						if(envId==1){
							new_log.info"${'*'.multiply(40)} Job Creation ${'*'.multiply(40)}\r\n"
							new_log.info("Job "+jobNumber+" was created by user "+user_Name+" for Environment "+envName+"\r\n")
						}else if(envId==2 || envId==3){
							new_log.info"${'*'.multiply(40)} Job Promotion ${'*'.multiply(40)}\r\n"
							new_log.info("Job "+jobNumber+" was promoted by user "+user_Name+" for Environment "+envName+"\r\n")

						}
						if(new_log==null) new_log = log
					}

					Boolean includePremium = benversInstanceToDeploy.includePremiumCommerceObjects
					new_log.info "Bundle is Premium: $includePremium"

					// Turn map of Strings into map of content child objects
					mapOfChildren.each{

						def secureProgramId = it.key
						def secureProgramRev = getRevisionNumber(secureProgramId, secureProgram)


						log.debug "secureProgram Id: " + secureProgramId

						def secureProgramInstance= SecureProgram.where{id==secureProgramId}.get()?: utilityService.getDeletedObject(secureProgramId, secureProgramRev, 3)
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

						// Commerce Object Values example "6,8,12" or "12"
						if (commerceObjectValue.contains(",")){
							commerceObjectIds = commerceObjectValue.split(',')
						}else {	commerceObjectIds << commerceObjectValue }

						log.info "Commerce Object IDs " + commerceObjectIds
						new_log.info "Total Number of Custom Commerce Objects: " +  commerceObjectIds.size()

						def listOfCommerceObjects = []

						commerceObjectIds.each{

							def commerceObjectId = it
							def commerceObjectRev = getRevisionNumber(commerceObjectId, commerceObject)

							def commerceObjectInstance = CommerceObject.where{id==commerceObjectId}.get()?: utilityService.getDeletedObject(commerceObjectId, commerceObjectRev, 4)
							def coEnversInstance

							if (commerceObjectInstance instanceof hmof.CommerceObject){
								coEnversInstance = commerceObjectInstance.findAtRevision(revisionNumber.toInteger())
							}

							else{
								log.warn"Promoting deleted Commerce Object for Bundle from Envers"
								def commerceObjectMap = createCommerceObjectMap(commerceObjectInstance)
								coEnversInstance = new CommerceObject(commerceObjectMap)
							}

							// Handle Premium Commerce Objects
							if (!coEnversInstance.isPremium || coEnversInstance.isPremium && includePremium){

								listOfCommerceObjects << coEnversInstance

							}
						}

						childMap << [(spEnversInstance):listOfCommerceObjects]
						new_log.info "child Map of Objects being sent to Geb: " + childMap

					}

					// Pass data to Geb
					RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, benversInstanceToDeploy, childMap,new_log)

					new_log.info "${'*'.multiply(40)} Finished Deploying Bundle ${'*'.multiply(40)}\r\n"
					new_log.info"${'*'.multiply(40)} Status ${'*'.multiply(40)}\r\n"
					log.debug("promotionId:"+promotionInstance.id)
					new_log.info("Job Status: Success\r\n")
				}
			}
		}
		catch(InterruptedException  e){

			log.error "Exception deploying content: " + e
			return false

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
		log.debug "Revision Number:  " +  revNumber

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
			gradeLevel:co.GRADE_LEVEL, comments:co.COMMENTS, isPremium:co.IS_PREMIUM,contentType:co.CONTENT_TYPE_ID]

	}
	/**
	 * Initialize logger for each thread
	 * @param programName
	 * @param cacheLocation
	 * @param envId
	 */
	Logger initializeLogger(String programISBN,String cacheLocation, def envId,def contentType) {
		final String workingDir = cacheLocation
		Logger log1 = Logger.getLogger("Thread" + programISBN)
		Properties props=new Properties()
		props.setProperty("log4j.appender.file","org.apache.log4j.RollingFileAppender")
		props.setProperty("log4j.appender.file.maxFileSize","100MB")
		props.setProperty("log4j.appender.file.maxBackupIndex","100")
		if(envId==1){
			if(contentType==1){
				props.setProperty("log4j.appender.file.File",workingDir +"/Programs/"+ programISBN + "/dev/log/"+programISBN+"-"+"dev_log.log")
			}else if(contentType==2){
				props.setProperty("log4j.appender.file.File",workingDir +"/Bundles/"+ programISBN + "/dev/log/"+programISBN+"-"+"dev_log.log")
			}else if(contentType==3){
				props.setProperty("log4j.appender.file.File",workingDir +"/Secure Programs/"+ programISBN + "/dev/log/"+programISBN+"-"+"dev_log.log")
			}else if(contentType==4){
				props.setProperty("log4j.appender.file.File",workingDir +"/Commerce Objects/"+ programISBN + "/dev/log/"+programISBN+"-"+"dev_log.log")
			}
		}else if(envId==2){
			if(contentType==1){
				props.setProperty("log4j.appender.file.File",workingDir +"/Programs/"+ programISBN + "/review/log/"+programISBN+"-"+"review_log.log")
			}else if(contentType==2){
				props.setProperty("log4j.appender.file.File",workingDir +"/Bundles/"+ programISBN + "/review/log/"+programISBN+"-"+"review_log.log")
			}else if(contentType==3){
				props.setProperty("log4j.appender.file.File",workingDir +"/Secure Programs/"+ programISBN + "/review/log/"+programISBN+"-"+"review_log.log")
			}else if(contentType==4){
				props.setProperty("log4j.appender.file.File",workingDir +"/Commerce Objects/"+ programISBN + "/review/log/"+programISBN+"-"+"review_log.log")
			}

		}else if(envId==3){
			if(contentType==1){
				props.setProperty("log4j.appender.file.File",workingDir +"/Programs/"+ programISBN + "/prod/log/"+programISBN+"-"+"prod_log.log")
			}else if(contentType==2){
				props.setProperty("log4j.appender.file.File",workingDir +"/Bundles/"+ programISBN + "/prod/log/"+programISBN+"-"+"prod_log.log")
			}else if(contentType==3){
				props.setProperty("log4j.appender.file.File",workingDir +"/Secure Programs/"+ programISBN + "/prod/log/"+programISBN+"-"+"prod_log.log")
			}else if(contentType==4){
				props.setProperty("log4j.appender.file.File",workingDir +"/Commerce Objects/"+ programISBN + "/prod/log/"+programISBN+"-"+"prod_log.log")
			}
		}
		props.setProperty("log4j.appender.file.threshold","info")
		props.setProperty("log4j.appender.file.Append","false")
		props.setProperty("log4j.appender.file.layout","org.apache.log4j.PatternLayout")
		props.setProperty("log4j.appender.file.layout.ConversionPattern","%d - %m%n")
		props.setProperty("log4j.logger."+ "Thread" + programISBN,"INFO, file")
		PropertyConfigurator.configure(props)
		return log1
	}
}

