package hmof

import hmof.deploy.*
import hmof.geb.RedPagesDriver
import hmof.geb.verify.VerifyDriver
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
		def isbn=""
		def secureIsbn=""
		def bundleIsbn=""
		String programName=""
		Logger customerLog=null

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

			// used in external logs and smart-deploy
			if (!program.isEmpty()){

				program.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					Long jobNumber = it.jobNumber
					// If instance has been deleted return a GroovyRowResult object from the Envers Audit table
					def programInstance = Program.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 1)

					programName = programInstance.toString()
					def smartDeploy=promotionInstance.smartDeploy

					customerLog = initializeLogger(programName, cacheLocation,envId,1)
					customerLog=getLogHeader(customerLog, envId, jobNumber, user_Name, envName )

					def previousJob = deploymentService.getPreviousJob( instanceNumber, jobNumber, envId )


					if (!previousJob.isEmpty() && smartDeploy){

						bundlesToRemove = deploymentService.compareJobs( bundle, previousJob )
						customerLog.info "User selected '${smartDeploy}' for Smart-Deployment for the '${programName}' Program"
						customerLog.info "There are a total of: ${bundle.size()} Bundles in this Job"
						customerLog.info "There are a total of: ${bundlesToRemove.size()} Bundles that will not be redeployed as part of this Smart-Deployment \r\n"
						customerLog.debug "The following Bundles do not need to be redeployed: ${bundlesToRemove.contentId} at revision ${bundlesToRemove.revision}"


						// removes bundles from current job
						bundle = bundle - bundlesToRemove
					}
				}
			} //end Program

			// used only to initialize logs
			if (program.isEmpty() && !bundle.isEmpty()){

				bundle.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision
					def mapOfChildren = it.children
					Long jobNumber = it.jobNumber
					def bundleInstance = Bundle.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 2)
					bundleIsbn=bundleInstance.isbn

					customerLog = initializeLogger(bundleIsbn, cacheLocation,envId,2)
					customerLog=getLogHeader(customerLog, envId, jobNumber, user_Name, envName )
				}
			} // end bundle log initializer

			// used only to initialize SP logs
			if (program.isEmpty() && bundle.isEmpty() && !secureProgram.isEmpty()){

				secureProgram.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision
					Long jobNumber = it.jobNumber
					def secureProgramInstance = SecureProgram.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 3)

					secureIsbn = secureProgramInstance.registrationIsbn

					customerLog = initializeLogger(secureIsbn, cacheLocation,envId,3)
					customerLog=getLogHeader(customerLog, envId, jobNumber, user_Name, envName )
				}
			}// end SP log initializer

			// Deployment Logic
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
						customerLog = initializeLogger(isbn,cacheLocation,envId,4)
						customerLog=getLogHeader(customerLog, envId, jobNumber, user_Name, envName )
					}

					// Pass data to Geb
					RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, enversInstanceToDeploy,customerLog)
					customerLog.info "${'*'.multiply(40)} Finished Deploying Commerce Object ${'*'.multiply(40)}\r\n"
					if(rpd && program.isEmpty() && bundle.isEmpty() && secureProgram.isEmpty()){
						customerLog.info"${'*'.multiply(40)} Status ${'*'.multiply(40)}\r\n"
						customerLog.info("Job Status: Success\r\n")

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
					RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, enversInstanceToDeploy,customerLog)
					customerLog.info "${'*'.multiply(40)} Finished Deploying Secure Program ${'*'.multiply(40)}\r\n"
					if(rpd && program.isEmpty() && bundle.isEmpty()){

						customerLog.info"${'*'.multiply(40)} Status ${'*'.multiply(40)}\r\n"
						customerLog.debug("promotionId:"+promotionInstance.id)
						customerLog.info("Job Status: Success\r\n")

					}
				}
			}


			// Deploy Bundle with its child associations
			if (!bundle.isEmpty()){

				deployBundles(bundle, customerLog, secureProgram, commerceObject, deploymentUrl )
			}

			// Verify that the Bundles we did not deploy are still on Red-Pages
			if(!bundlesToRemove.isEmpty()){

				customerLog.info "${'*'.multiply(30)} Smart-Deploy Bundle Verification ${'*'.multiply(30)}\r\n"

				// Send in jobs
				def verifyObjects = new VerifyDriver(deploymentUrl, bundlesToRemove, customerLog)

				def redeployBundles = verifyObjects.bundlesToRedeploy

				if (!redeployBundles.isEmpty()){

					customerLog.info "Manually Deleted Bundles are being recreated in Red-Pages..."

					deployBundles(redeployBundles, customerLog, secureProgram, commerceObject, deploymentUrl)

				}

				customerLog.info "Job Complete!"
			} // end Smart-Deploy Verification
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
	 * Helper Method to deploy the Bundles via Geb Browser 
	 */
	def deployBundles(def bundleList, Logger customerLog, def secureProgram, def commerceObject, def deploymentUrl){

		bundleList.each{			

			Long instanceNumber = it.contentId
			Long revisionNumber = it.revision
			def mapOfChildren = it.children
			Long jobNumber = it.jobNumber
			def bundleContent
			def childMap = [:]

			customerLog.info"${'*'.multiply(30)} Bundles and Associations ${'*'.multiply(30)}\r\n"
			log.debug "Map Of Children: " + mapOfChildren


			// If instance has been deleted return a GroovyRowResult object from the Envers Audit table
			def bundleInstance = Bundle.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 2)

			if (bundleInstance instanceof hmof.Bundle){

				bundleContent = bundleInstance.findAtRevision(revisionNumber.toInteger())				
			}
			else{

				log.warn"Promoting deleted Bundle from Envers"
				// Get the properties we are interested in
				bundleContent = new Bundle(isbn:bundleInstance.ISBN, title:bundleInstance.TITLE, duration:bundleInstance.DURATION, includePremiumCommerceObjects:bundleInstance.INCLUDE_PREMIUM_COMMERCE_OBJECTS, contentType:bundleInstance.CONTENT_TYPE_ID)
				
			}

			Boolean includePremium = bundleContent.includePremiumCommerceObjects
			customerLog.info "Bundle is Premium: $includePremium"

			// Turn map of Strings into map of content child objects
			mapOfChildren.each{

				def secureProgramId = it.key
				def secureProgramRev = getRevisionNumber(secureProgramId, secureProgram)

				log.debug "secureProgram Id: " + secureProgramId

				def secureProgramInstance = SecureProgram.where{id==secureProgramId}.get()?: utilityService.getDeletedObject(secureProgramId, secureProgramRev, 3)
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
				}else {
					commerceObjectIds << commerceObjectValue
				}

				log.info "Commerce Object IDs " + commerceObjectIds
				customerLog.info "Total Number of Custom Commerce Objects: " +  commerceObjectIds.size()

				def listOfCommerceObjects = []

				customerLog.info commerceObjectValue!=""
				if(commerceObjectValue!=""){
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
				}
				
				childMap << [(spEnversInstance):listOfCommerceObjects]
				customerLog.info "child Map of Objects being sent to Geb: " + childMap
			}
			
			// Pass data to Geb
			RedPagesDriver rpd = new RedPagesDriver(deploymentUrl, bundleContent, childMap,customerLog)

			customerLog.info "${'*'.multiply(30)} Finished Deploying Bundle ${'*'.multiply(30)}\r\n"
			customerLog.info"${'*'.multiply(30)} Status ${'*'.multiply(30)}\r\n"			
			customerLog.info("Job Status: Success\r\n")
		}
	}


	/**
	 * Helper method to create Log file Header
	 */
	Logger getLogHeader(Logger customerLogs, def envId, def jobNumber, def user_Name, envName ){

		if(envId==1){
			customerLogs.info"${'*'.multiply(40)} Job Creation ${'*'.multiply(40)}\r\n"
			customerLogs.info("Job " + jobNumber+" was created by user " + user_Name + " for Environment "+envName+"\r\n")
		} else if(envId==2 || envId==3){
			customerLogs.info"${'*'.multiply(40)} Job Promotion ${'*'.multiply(40)}\r\n"
			customerLogs.info("Job "+jobNumber + " was promoted by user " + user_Name + " for Environment "+envName+"\r\n")
		}
		if(customerLogs==null) customerLogs = log
		return customerLogs
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

