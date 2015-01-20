package hmof

//import grails.transaction.Transactional
import hmof.deploy.*
import hmof.security.User
import hmof.security.Role
import hmof.security.UserRole

/**
 * DeploymentService
 * A service class encapsulates the core business logic of a Grails application
 */
//@Transactional //TODO
class DeploymentService {

	static transactional = false

	// inject Spring Security
	def springSecurityService
	def jobService
	def sessionFactory


	/**
	 * Return true to detect if previous Job exists for this Program and User
	 * @param programInstanceNumber
	 * @param envId
	 * @return
	 */
	Boolean doesPreviousJobExist(def programInstanceNumber, def envId){

		def previousJobNumbers = Job.where{contentId==programInstanceNumber }.list().jobNumber

		def theJob = Promotion.where{jobNumber in previousJobNumbers && status=='Success' &&  environments{id == envId }}.list(max:1, sort:'jobNumber', order:'desc')

		Long theJobNumber = theJob.find{it.jobNumber}?.jobNumber

		// pass back an ArrayList of job instances of Bundles that belong to the previous successful Job
		def lastJob = Job.where{jobNumber == theJobNumber && contentTypeId == 2}.list()

		if (lastJob.isEmpty()){

			return false
		}

		return true
	}

	/**
	 * Compare the current Bundle job instances to the previous job instances and return the current bundles that are the same	 
	 * @param currentJobBundles
	 * @param previousJobBundles
	 * @return
	 */
	def compareJobs( def currentJobBundles, def previousJobBundles  ){

		def bundlesRemoved = []

		// double loop
		currentJobBundles.each{
			def currentJobInstance = it
			previousJobBundles.each{
				def previousJobInstance = it

				if(currentJobInstance.contentId == previousJobInstance.contentId && currentJobInstance.revision == previousJobInstance.revision){

					log.info "Bundle ID  ${currentJobInstance.contentId} is in  previous Job at the same revision: ${currentJobInstance.revision}"
					bundlesRemoved << currentJobInstance
				}
			}
		}

		println "bundles to be removed: " +  bundlesRemoved.contentId
		return bundlesRemoved
	}

	/**
	 * Return a List of Bundle job instances that were previously pushed for the same Program to the Users Environment
	 * @param programInstanceNumber
	 * @param currentJobNumber
	 * @return
	 */
	def getPreviousJob(def programInstanceNumber, def currentJobNumber, def envId){

		def previousJobNumbers = Job.where{contentId==programInstanceNumber && jobNumber < currentJobNumber }.list().jobNumber

		def theJob = Promotion.where{jobNumber in previousJobNumbers && status=='Success' &&  environments{id == envId }}.list(max:1, sort:'jobNumber', order:'desc')

		Long theJobNumber = theJob.find{it.jobNumber}?.jobNumber

		// pass back an ArrayList of job instances of Bundles that belong to the previous successful Job
		def lastJob = Job.where{jobNumber == theJobNumber && contentTypeId == 2}.list()
	}

	/**
	 * Get the bundles children as a map of String where the Key is the id of the SP and the value is the CO id
	 * @param instanceId
	 * @return
	 */
	def getChildrenMap(instanceId) {

		HashMap mapOfChildren = []

		def deployableBundle = Bundle.where{ id==instanceId && secureProgram{}}
		def secureProgramList = deployableBundle.list().secureProgram.id.flatten()

		secureProgramList.each{

			String secureProgramId = it
			println "secureProgramId: " + secureProgramId
			def sp = SecureProgram.where{id==secureProgramId}.get()
			def commerceObjectIds = sp.commerceObjects.id
			String coIds = commerceObjectIds.join(',')

			// add to map
			mapOfChildren<< [(secureProgramId):coIds]

		}

		log.info "deployableBundle: " + deployableBundle + "has Children Map: " + mapOfChildren
		mapOfChildren

	}

	/**
	 * get the children objects of a Program
	 * @param instanceId
	 * @return
	 */
	def getProgramChildren(instanceId) {

		// A deployable Bundle belongs to the deployable Program and must have a SecureProgram.
		def deployableBundle = Bundle.where{ program{ id==instanceId} && secureProgram{} }

		// get all Bundles that belong to the Program being deployed
		def allProgramBundle = Bundle.where{ program{ id==instanceId } }

		// get a unique listing of SecurePrograms belonging to the Program being deployed
		Set uniqueSecureProgram = allProgramBundle.list().secureProgram.id.flatten()

		// get a unique listing of CommerceObjects belonging to the Program being deployed
		Set uniqueCommerceObject = allProgramBundle.list().secureProgram.commerceObjects.id.flatten()

		// deployment logic for SP
		def deployableSecureProgram = []
		// Needed for MySql database
		if(!uniqueSecureProgram.isEmpty()){
			deployableSecureProgram = SecureProgram.where{id in uniqueSecureProgram}.list()
		}

		// deployment logic for a CO
		def deployableCommerceObject = []
		if(!uniqueCommerceObject.isEmpty()){
			deployableCommerceObject = CommerceObject.where{id in uniqueCommerceObject}.list()
		}

		// return list of deployable Bundle (Bundles), deployable SecureProgram (SP) and deployable CommerceObject (CO)
		def (bundleList, secureProgramList, commerceObjectList) = [deployableBundle.list(), deployableSecureProgram, deployableCommerceObject]

	}

	/**
	 * Get the children objects of an individual Bundle (Bundle) which should have SecureProgram, and Content C
	 * @param instanceId
	 * @return
	 */
	def getBundleChildren(instanceId) {

		// deployable content A has a SecureProgram
		def deployableBundle = Bundle.where{ id==instanceId && secureProgram{}}

		// get a unique listing of Content B belonging to the deployable Content A
		Set uniqueSecureProgram = deployableBundle.list().secureProgram.id.flatten()

		// deployment logic for SP
		def deployableSecureProgram = []
		// Needed for MySql database
		if(!uniqueSecureProgram.isEmpty()){
			deployableSecureProgram = SecureProgram.where{id in uniqueSecureProgram}.list()
		}

		// get a unique listing of CO belonging to the SP being deployed
		Set uniqueCommerceObject = deployableSecureProgram.commerceObjects.id.flatten()

		// deployment logic for CO
		def deployableCommerceObject = []
		if(!uniqueCommerceObject.isEmpty()){
			deployableCommerceObject = CommerceObject.where{id in uniqueCommerceObject}.list()
		}

		// return Deployable SecureProgram and CommerceObject
		def (secureProgramList, commerceObjectList) = [deployableSecureProgram, deployableCommerceObject]

	}


	/**
	 * Get the children objects of an individual SecureProgram (Secure Program), and Content C
	 * @param instanceId
	 * @return
	 */
	def getSecureProgramChildren(instanceId) {

		def deployableSecureProgram = SecureProgram.where{id==instanceId}

		// get a unique listing of Content C belonging to the Content B being deployed
		Set uniqueCommerceObject = deployableSecureProgram.list().commerceObjects.id.flatten()

		def deployableCommerceObject = []
		if(!uniqueCommerceObject.isEmpty()){
			deployableCommerceObject = CommerceObject.where{id in uniqueCommerceObject}.list()
		}

		// return Deployable CommerceObject
		def (commerceObjectList) = [deployableCommerceObject]

	}


	/**
	 * get Job/Promotion details for the Instance being passed in based on its environment id
	 * @param instanceId
	 * @param environmentId
	 * @return
	 */
	def getPromotionDetails(instanceId, environmentId) {

		// All jobs for this Instance
		def jobDetails = Job.where{contentId == instanceId.id && contentTypeId == instanceId.contentTypeId}

		// Get Job Numbers and add to list
		def jobNumbers = jobDetails.jobNumber.list()

		// Iterate through the Job Numbers where the environment ID matches and return the latest promotion instance
		def promoInstanceList = []
		if(!jobNumbers.isEmpty()){
			promoInstanceList = Promotion.where{jobNumber in jobNumbers && environments{id==environmentId} }.list(max:1, sort:"dateCreated", order:"desc")
		}
		def job_Number = promoInstanceList.jobNumber
		def status_Type = promoInstanceList.status
		def user_Name = User.where{id == promoInstanceList.userId}.username.get()

		// get the revision number of the instance
		def revision_Number = Job.where{jobNumber == promoInstanceList.jobNumber && contentId == instanceId.id && contentTypeId == instanceId.contentTypeId}.revision.get()

		def (job, status, revision, user) = [job_Number, status_Type, revision_Number, user_Name].flatten()

	}

	/**
	 * Get the Environment associated with the User
	 * @return
	 */
	def getUserEnvironmentInformation(){

		// get role of user
		def principal = springSecurityService.principal
		def authorities = principal.authorities
		// get role id of user
		def roleId = Role.where{authority==authorities}.get()
		def envName = getEnvironmentName(roleId.id)
		def envId = Environment.where{name==envName}.get()

	}
	/**
	 * Get the Environment associated with the User
	 * @return
	 */
	def getUserEnvironmentIdInformation(){

		// get role of user
		def principal = springSecurityService.principal
		def authorities = principal.authorities
		// get role id of user
		def roleId = Role.where{authority==authorities}.get()
		def envName = getEnvironmentName(roleId.id)
		def envId = Environment.where{name==envName}.get()
		envId.id
	}
	/**
	 * Helper Method If Prod User then return 2 if QA user then return 1
	 * @param environmentId
	 * @return
	 */
	def getPreviousEnvironment(environmentId){

		def prevEnvironment
		if(environmentId==3L){prevEnvironment=2L}
		else if(environmentId==2L){prevEnvironment=1L}

		prevEnvironment

	}


	/**
	 * Return the Promotion Instance on Dev or QA based on the Users environment permission	
	 * @param instanceId
	 * @param environment
	 * @return
	 */
	def getDeployedInstance(instanceId, environment) {

		def environmentToCheck = getPreviousEnvironment(environment.id)
		def previousEnvironment = Environment.where{id==environmentToCheck}.get()
		log.info "Previous Environment: " +  previousEnvironment

		def jobDetails = Job.where{contentId == instanceId.id && contentTypeId == instanceId.contentTypeId}

		// Get Job Numbers and add to list
		def jobNumbers = jobDetails.jobNumber.list()

		log.debug "All job Numbers for Instance " + jobNumbers

		// Iterate through the Job Numbers where the environment ID matches and return the latest promotion instance
		def promoInstanceList = []
		if(!jobNumbers.isEmpty()){
			promoInstanceList = Promotion.where{jobNumber in jobNumbers && environments{id==previousEnvironment.id} }.list(max:1, sort:"dateCreated", order:"desc")
		}
		// Type Long
		Long InstanceNumber = Promotion.where{id==promoInstanceList.id}.id.get()
		log.debug "The Instance Number: " +  InstanceNumber

		// Get the Instance that was deployed to the previous environment
		def promoInstance = Promotion.where{id==InstanceNumber}.get()

		if(promoInstance == null || promoInstance.status !=JobStatus.Success.toString()){
			log.warn "Preventing the promotion as previous promotion was not successful!"

		}else{

			promoInstance
		}

	}


	/**
	 * Get the environment name
	 * @return
	 */
	def getEnvironmentName(Long roleId){
		def envName

		if(roleId == 2){
			envName = "Dev"
		} else if(roleId == 3){
			envName = "QA"
		} else if(roleId == 4){
			envName = "Production"
		}
		return envName
	}

	/**
	 * 
	 * @param deployableInstance
	 * @return
	 */
	def getCurrentEnversRevision(def deployableInstance){

		// TODO put measures in to check for null
		List allRevisions = deployableInstance.retrieveRevisions()
		def currentRevision = allRevisions[-1]
	}

	/**
	 * Get the current Job Number
	 * @return
	 */
	def getCurrentJobNumber(){

		def currentJobNumber = Job.createCriteria().get {
			projections { max "jobNumber" } } as Long

		if (currentJobNumber == null){currentJobNumber = 1}
		else{currentJobNumber += 1}

		currentJobNumber
	}

	/**
	 * Deploy or Promote Jobs in Pending Status	
	 * @return
	 */
	def executeJob(){

		// get first instance in pending status
		def promotionJobInstance = Promotion.where{status == JobStatus.Pending }.list(max:1)
		def promotionJobNumber =  promotionJobInstance.jobNumber

		def jobList = Job.where{jobNumber == promotionJobNumber}.list()

		if(!promotionJobInstance.isEmpty()){
			Long promotionJobId =  promotionJobInstance.id[0]
			def promotionJobStatus = Promotion.findByStatus(JobStatus.Pending)

			//  Locking the job.  The lock will be released after the DeploymentProcessorService saves the bundle with a status of InProgress.
			//  This should prevent any other threads picking up the deployment anyway
			promotionJobStatus.discard()
			promotionJobStatus = Promotion.lock(promotionJobId)


			def status1 = JobStatus.In_Progress


			def promotionInstance = Promotion.get(promotionJobId)

			promotionInstance.properties = [status: status1]
			promotionInstance.save(failOnError: true, flush:true)

			def processJobs = jobService.processJobs(jobList, promotionInstance)


			def status2 = null

			if (processJobs){

				status2 = JobStatus.Success

			} else {status2 = JobStatus.Failed}


			// return map
			def results = [status: status2, promotionId:promotionJobInstance.id]
		}

	}

	/**
	 * Update the Promotion instance
	 * @param jobIdList
	 * @return
	 */
	def updateStatus(def promotionUpdate){

		log.info "Promotion Instance to Update: " +  promotionUpdate

		def promotionInstance = Promotion.where{id == promotionUpdate.promotionId}.get()
		promotionInstance.properties = [status:promotionUpdate.status]

	}


}
