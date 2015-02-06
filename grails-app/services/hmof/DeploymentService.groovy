package hmof

import hmof.deploy.*
import hmof.security.User
import hmof.security.Role
import hmof.security.UserRole

/**
 * DeploymentService
 * A service class encapsulates the core business logic of a Grails application
 */
class DeploymentService {

	static transactional = false

	// inject Services
	def springSecurityService
	def jobService
	def sessionFactory


	/**
	 * Return Boolean to detect if previous Job exists for a particular Program and Current Users environment
	 * @param programInstanceNumber
	 * @param envId
	 * @return
	 */
	Boolean doesPreviousJobExist(def programInstanceNumber, def envId){


		// get all Job Numbers from the Job Table for this Program
		def previousJobNumbers = Job.where{contentId==programInstanceNumber && contentTypeId==1 }.list().jobNumber

		// needed for MySql
		if (!previousJobNumbers.isEmpty()){

			// If the environment has 1 or more promotion instance it has a previous Job
			def promotionList = Promotion.where{jobNumber in previousJobNumbers && status==JobStatus.Success &&  environments{id == envId }}.list()

			if(promotionList.size()>=1)	{ return true }

			return false
		}

		return false
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

		log.info "bundles to be removed: " +  bundlesRemoved.contentId
		return bundlesRemoved
	}

	/**
	 * Return a List of Bundle job instances that were previously pushed for the same Program to the Users Environment
	 * Will only be called if doesPreviousJobExist() method returns true
	 * @param programInstanceNumber
	 * @param currentJobNumber
	 * @return
	 */
	def getPreviousJob( def programInstanceNumber, def currentJobNumber, def envId ){

		def lastJob = []

		def previousJobNumbers = Job.where{contentId==programInstanceNumber && contentTypeId==1 && jobNumber <= currentJobNumber}.list().jobNumber
		log.info "previous Job Numbers: ${previousJobNumbers}"

		// required for mySql
		if (!previousJobNumbers.isEmpty()){

			def theJob = Promotion.where{jobNumber in previousJobNumbers && (status==JobStatus.Success || status==JobStatus.Repromoting) &&  environments{id == envId }}.list(max:1, sort:'jobNumber', order:'desc')

			Long theJobNumber = theJob.find{it.jobNumber}?.jobNumber

			println "Current Job is: ${currentJobNumber} and is being compared to ${theJobNumber}"
			log.info"Current Job is: ${currentJobNumber} and is being compared to ${theJobNumber}"

			// pass back an ArrayList of job instances of Bundles that belong to the previous successful Job
			return lastJob = Job.where{jobNumber == theJobNumber && contentTypeId == 2}.list()
		}

		return lastJob
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
			log.debug "secureProgramId: " + secureProgramId
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
	 * Return true if the lower environment contains the same Job Number or Revision
	 * @param instanceId
	 * @param environmentId
	 * @return
	 */
	def isLowerEnvironmentEqual(instanceId, environmentId) {
		// return zero if current environment is Dev/1
		def lowerEnvironment = getPreviousEnvironment(environmentId)?:0	
		def currentRevision = getCurrentEnversRevision(instanceId)
		
		// All job numbers for this Content Instance
		def jobNumbers = Job.where{contentId == instanceId.id && contentTypeId == instanceId.contentTypeId}.jobNumber.list()
		

		def promoInstanceUserEnvironment = []
		def promoInstanceLowerEnvironment = []

		if(!jobNumbers.isEmpty()){
			// Iterate through the Job Numbers where the environment ID matches and return the latest promotion instance
			promoInstanceUserEnvironment = Promotion.where{jobNumber in jobNumbers && environments{id==environmentId} }.list(max:1, sort:"dateCreated", order:"desc")
			promoInstanceLowerEnvironment = Promotion.where{jobNumber in jobNumbers && environments{id==lowerEnvironment} }.list(max:1, sort:"dateCreated", order:"desc")

			// Job Number for Users Environment
			def currentJobNumber = promoInstanceUserEnvironment.jobNumber[0]
			
			def lowerJobNumber = promoInstanceLowerEnvironment.jobNumber[0]?:0
			

			if(currentJobNumber == lowerJobNumber )	{ return true }
		}

		// If lower Environment is 0 compare Environment to Working revision
		if (lowerEnvironment == 0){

			def revisionNumber = Job.where{jobNumber == promoInstanceUserEnvironment.jobNumber && contentId == instanceId.id && contentTypeId == instanceId.contentTypeId}.revision.get()
			if (currentRevision == revisionNumber) {
				
				return true
			}
		}

		return false
		

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

		def principal = springSecurityService.principal
		def authorities = principal.authorities


		def authStr = authorities.toString().replace("[","").replace("]","")
		List<String> authList = Arrays.asList(authStr.split(", "));

		def roleList = []
		// get role id of user
		roleList = Role.where{authority in authList}.list()
		List<String> arr = new ArrayList<String>();
		for (Role temp : roleList) {
			def envName = getEnvironmentName(temp.id)
			if(envName!=null){
				arr.add(envName);
			}

		}
		if(arr.contains("Dev")){
			def envId = Environment.where{name=="Dev"}.get()
			envId.id
		}else if(arr.contains("QA")){
			def envId = Environment.where{name=="QA"}.get()
			envId.id
		}else if(arr.contains("Production")){
			def envId = Environment.where{name=="Production"}.get()
			envId.id
		}
	}
	/**
	 * Get the Environment associated with the User
	 * @return
	 */
	@Deprecated
	def getUserEnvironmentIdInformation(){

		// get role of user
		def principal = springSecurityService.principal
		def authorities = principal.authorities


		def authStr = authorities.toString().replace("[","")
		def authStr1 = authStr.replace("]","")
		def authStr2 = authStr1.replace(", ",",")


		String[] authString = authStr2.split(",")
		List<String> authList = Arrays.asList(authString);

		def roleList = []
		// get role id of user
		roleList = Role.where{authority in authList}.list()
		List<String> arr = new ArrayList<String>();
		for (Role temp : roleList) {
			def envName = getEnvironmentName(temp.id)
			if(envName!=null){
				arr.add(envName);
			}

		}
		if(arr.contains("Dev")){
			def envId = Environment.where{name=="Dev"}.get()
			envId.id
		}else if(arr.contains("QA")){
			def envId = Environment.where{name=="QA"}.get()
			envId.id
		}else if(arr.contains("Production")){
			def envId = Environment.where{name=="Production"}.get()
			envId.id
		}

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

		def environmentToCheck = getPreviousEnvironment(environment)
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
	 * Helper method that returns the current revision number
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
	 * Deploy or Promote Jobs in Pending or Pending_Repromote Status	
	 * @return
	 */
	def executeJob(){

		// get first instance in pending status
		def promotionJobInstance = Promotion.where{status == JobStatus.Pending || status == JobStatus.Pending_Repromote }.list(max:1)
		def promotionJobNumber =  promotionJobInstance.jobNumber

		if(!promotionJobInstance.isEmpty()){

			def jobList = Job.where{jobNumber == promotionJobNumber}.list()
			Long promotionJobId =  promotionJobInstance.id[0]

			/*def promotionJobStatus = Promotion.findByStatus(JobStatus.Pending)			
			 //  Locking the job.  The lock will be released after the DeploymentProcessorService saves the bundle with a status of InProgress.
			 //  This should prevent any other threads picking up the deployment anyway
			 promotionJobStatus.discard()
			 promotionJobStatus = Promotion.lock(promotionJobId)*/		

			def promotionInstance = Promotion.get(promotionJobId)
			promotionInstance.discard()
			promotionInstance.lock()

			def statusStart = null

			if (promotionInstance.status==JobStatus.Pending_Repromote.toString()){
				statusStart = JobStatus.Repromoting
			}else { statusStart = JobStatus.In_Progress	}

			promotionInstance.properties = [status: statusStart]
			promotionInstance.save(failOnError: true, flush:true)

			def processJobs = jobService.processJobs(jobList, promotionInstance)

			def statusFinish = null

			if (processJobs){
				statusFinish = JobStatus.Success
			} else {statusFinish = JobStatus.Failed}

			// return map
			def results = [status: statusFinish, promotionId:promotionJobInstance.id]
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
