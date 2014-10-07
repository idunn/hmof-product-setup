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
		println "222 mapOfChildren: " + mapOfChildren
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

		// get all Bundle that belong to the Program being deployed
		def allProgramBundle = Bundle.where{ program{ id==instanceId } }

		// get a unique listing of SecureProgram belonging to the Program being deployed
		Set uniqueSecureProgram = allProgramBundle.list().secureProgram.id.flatten()

		// get a unique listing of CommerceObject belonging to the Program being deployed
		Set uniqueCommerceObject = allProgramBundle.list().secureProgram.commerceObjects.id.flatten()

		// deployment logic for Content B
		def deployableSecureProgram = []
		// Needed for MySql database
		if(!uniqueSecureProgram.isEmpty()){
			deployableSecureProgram = SecureProgram.where{id in uniqueSecureProgram}.list()
		}

		// deployment logic for a Content C
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

		// deployment logic for Content B
		def deployableSecureProgram = []
		// Needed for MySql database
		if(!uniqueSecureProgram.isEmpty()){
			deployableSecureProgram = SecureProgram.where{id in uniqueSecureProgram}.list()
		}

		// get a unique listing of Content C belonging to the Content B being deployed
		Set uniqueCommerceObject = deployableSecureProgram.commerceObjects.id.flatten()

		// deployment logic for a Content C
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
		log.debug "Previous Environment: " +  previousEnvironment

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
			log.debug "Stop the promotion!"

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
		def promotionJobInstance = Promotion.where{status == "PENDING" }.list(max:1)
		def promotionJobNumber =  promotionJobInstance.jobNumber

		def jobList = Job.where{jobNumber == promotionJobNumber}.list()

		if(!promotionJobInstance.isEmpty()){

			def status1 = JobStatus.In_Progress.toString()

			Long promotionJobId =  promotionJobInstance.id[0]
			def promotionInstance = Promotion.get(promotionJobId)

			promotionInstance.properties = [status: status1]
			promotionInstance.save(failOnError: true, flush:true)

			// for PDC
			//def processJobs = jobService.processJobs2(jobList, promotionInstance)

			def processJobs = jobService.processJobs(jobList, promotionInstance)
			def status2 = null

			if (processJobs){

				status2 = JobStatus.Success.toString()

			} else {status2 = JobStatus.Failure.toString()}


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
