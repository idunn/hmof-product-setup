package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import hmof.deploy.*

import hmof.security.*

import grails.plugin.springsecurity.annotation.Secured
import org.apache.log4j.Logger

/**
 * CommerceObjectController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class CommerceObjectController {

	def springSecurityService
	def deploymentService
	def utilityService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	/**
	 * Update the Parent Program on an Edit and Bundles on a delete
	 * @param currentInstance
	 * @return
	 */
	@Transactional
	def updateParent(def currentInstance){

		log.info "Executing action $actionName"

		def secureProgramInstances = SecureProgram.where{commerceObjects{id==currentInstance.id}}.list()

		def bundleInstances = []
		// Needed for MySql database
		if(!secureProgramInstances.isEmpty()){
			bundleInstances = Bundle.where{secureProgram{id in secureProgramInstances.id}}.list()
		}

		def programInstances = []
		// Needed for MySql database
		if(!bundleInstances.isEmpty()){
			programInstances = Program.where{bundles{id in bundleInstances.id }}.list()
		}

		// update Program instances if they are associated with CO
		programInstances.each{
			it.properties = [lastUpdated: new Date(),userUpdatingProgram: springSecurityService?.currentUser?.username]
		}


		// only update Bundles on a delete action - envers will detect that the SP has been modified
		if (actionName == "delete"){

			bundleInstances.each{
				it.properties = [lastUpdated: new Date(),userUpdatingBundle: springSecurityService?.currentUser?.username]
			}
		}
	}

	/**
	 * Remove Standalone Parent Associations before deleting the Commerce Object
	 * @param currentInstance
	 * @return
	 */
	@Transactional
	def removeAssociations(def currentInstance){

		def parentSecureProgram = SecureProgram.where{commerceObjects{id==currentInstance.id}}.list()
		log.info"Parent Secure Programs in association" + parentSecureProgram

		parentSecureProgram.each{

			def secureProgram = SecureProgram.get(it.id)
			secureProgram.removeFromCommerceObjects(currentInstance)
		}
	}

	@Secured(['ROLE_ADMIN'])
	def importCSV(Integer max) {render(view:"importCSV")}

	/**
	 * Pass in a comma separated file and update the database with new CO instances
	 * @return
	 */
	@Transactional
	def importFile(){

		log.info"Importing File..."
		def csvfile = request.getFile('CSVfiledata')

		try {


			List parseFileAndPersistData = utilityService.parseTextFile(csvfile,4)
			if (parseFileAndPersistData?.empty) {

				redirect(action: "list")
				return
			}else
			{
				render view:'importCSV', model:[parseFileAndPersistData:parseFileAndPersistData]
				return
			}

		}
		catch (IOException e) {
			flash.message = "There were errors when importing the Commerce Objects"
			log.error "There were errors when importing the Commerce Objects" + e
			redirect(action: "importCSV")
			return
		}
		log.info"Completed import"

		redirect(action: "list")

	}
	@Transactional
	def confirm() {
		def msg
		def commerceObjectInstance = CommerceObject.get(params.job.id)
		//  Check the user is enabled first
		//  If not we force them to log in again as we are probably trying to lock them out
		def currentUser = springSecurityService.currentUser
		if(!currentUser.enabled)
		{
			//  Redirect to login page
			return redirect (controller: 'login', action: 'full', params: params)

		}
		def environmentRevision
		//  Make sure its clean to start with
		log.debug "Flushing the flash memory"
		flash.clear()
		//Required since Grails 2.3.4 upgrade http://stackoverflow.com/questions/20359203/grails-seems-unable-to-bind-params-to-properties-if-it-contains-a-key-name-as-th
		params.remove('_')
		//  Test that the current user has permission to create a deployment on the chosen environment
		def environments = springSecurityService.currentUser.environments
		if(!environments)
		{
			flash.message = message(code: 'deployment.create.no.environments', default: 'Sorry, you are not authorised to promote to any environment.')
		}
		def envId=params.env
		def latestRevision=	deploymentService.getCurrentEnversRevision(commerceObjectInstance)
		environmentRevision=deploymentService.getPromotionDetails(commerceObjectInstance,envId)
		environmentRevision=environmentRevision[2]
		def envName=Environment.where{id==envId}.name.get()
		if (environmentRevision != null) {
			environmentRevision = environmentRevision
		}



		def lowEnvRevision=deploymentService.isLowerEnvironmentEqual(commerceObjectInstance,envId)

		if (latestRevision == environmentRevision && (!envId.equals("2") && !envId.equals("3") )) {

			msg="A job with the same revision already exists on the environment, Do you want to proceed?"

		}else if (latestRevision == environmentRevision && (envId.equals("2") || envId.equals("3") )) {

			msg="A job with the same revision already exists on the environment, Do you want to proceed?"


		}

		else if(latestRevision != environmentRevision && (!envId.equals("2") && !envId.equals("3") ))
		{
			msg='Are you sure you want to deploy?'

		}else if(latestRevision != environmentRevision && (envId.equals("2") || envId.equals("3")) )
		{
			msg='Are you sure you want to promote?'

		}

		if(envId.equals("2") || envId.equals("3")){
			def promotionInstance = deploymentService.getDeployedInstance(commerceObjectInstance,envId)


			if(promotionInstance==null){
				flash.message = message(code: 'promote.no.environments', default: 'Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment')
				log.info("Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment")
			}
		}

		//  Only set this if everything has passed inspection.  If it hasn't we want to make sure this deployment instance can never be used
		if(!flash.message)
		{
			flash.commerceObjectInstance = commerceObjectInstance
		}

		render(view: "_confirm", model: [commerceObjectInstance:commerceObjectInstance,msg:msg,envName:envName,envId:envId])
	}

	/**
	 * Persist job details to the job and promotions tables
	 * @return
	 */
	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def deploy(){

		def instanceId = params.programId

		log.info("Commerce Object  Detail: "+instanceId)
		def commerceObjectInstance = CommerceObject.get(instanceId)
		log.info("Deploying commerceObjectInstance : "+commerceObjectInstance.objectName)
		def deploymentJobNumber = deploymentService.getCurrentJobNumber()
		log.info("deploymentJobNumber: "+deploymentJobNumber)
		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()
		log.info("userId: "+userId)
		log.info("contentId: "+commerceObjectInstance.id)
		log.info("contentTypeId: "+commerceObjectInstance.contentType.contentId)
		// Create a map of job data to persist
		def job = [contentId: commerceObjectInstance.id, revision: deploymentService.getCurrentEnversRevision(commerceObjectInstance), contentTypeId: commerceObjectInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

		// Add Program instance to Job
		Job j1 = new Job(job).save(failOnError:true)
		log.info("Successfully added Commerce Object Instance to job : "+commerceObjectInstance.objectName)
		def envId = params.depEnvId
		log.info("Environment ID:"+envId)
		log.info("Job:"+ j1+",jobNumber: "+j1.getJobNumber()+",JobStatus:"+ JobStatus.Pending.getStatus())
		def promote = [status: JobStatus.Pending.getStatus(), job: j1, jobNumber: j1.getJobNumber(), user: userId, environments: envId,smartDeploy:false]
		Promotion p1 = new Promotion(promote).save(failOnError:true)

		redirect(action: "list")

	}

	/**
	 * Promote content that has already been promoted to Dev OR QA
	 * @return
	 */
	@Transactional
	@Secured(['ROLE_QA', 'ROLE_PROD'])
	def promote(){

		final String none = "none"

		def instanceToBePromoted = params.programId

		def commerceObjectInstance = CommerceObject.get(instanceToBePromoted)
		log.info("Promoting commerceObjectInstance : "+commerceObjectInstance.objectName)
		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = params.depEnvId
		log.info("User Id: "+userId+",envId:"+envId)
		def promotionInstance = deploymentService.getDeployedInstance(commerceObjectInstance, envId)

		if(promotionInstance==null){
			flash.message = "Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment"
			log.info("Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment")
			redirect(action: "list")
			return
		}

		def jobInstance = Job.where{id == promotionInstance.jobId}.get()
		log.info("jobNumber:"+promotionInstance.getJobNumber())
		def promotionJobInstance = Promotion.where{jobNumber==promotionInstance.getJobNumber() && environments{id == envId}}.get()?:none

		if(promotionJobInstance==none){

			def promote = [status: JobStatus.Pending.getStatus(), job: jobInstance, jobNumber: promotionInstance.getJobNumber(), user: userId, environments: envId,smartDeploy:false]
			Promotion p2 = new Promotion(promote).save(failOnError:true, flush:true)
			log.info("Job saved successfully")

		} else if(promotionJobInstance.status == JobStatus.In_Progress.getStatus().toString() || promotionJobInstance.status == JobStatus.Pending.getStatus().toString() ||
		promotionJobInstance.status == JobStatus.Pending_Repromote.getStatus().toString() || promotionJobInstance.status == JobStatus.Repromoting.getStatus().toString() ||
		promotionJobInstance.status == JobStatus.Pending_Retry.getStatus().toString() || promotionJobInstance.status == JobStatus.Retrying.getStatus().toString()){

			flash.message = "Job cannot be re-promoted as it is ${promotionJobInstance.status}"
			log.info("Job cannot be re-promoted as it is ${promotionJobInstance.status}")
		}

		else if(promotionJobInstance.status == JobStatus.Failed.getStatus().toString() || promotionJobInstance.status == JobStatus.FailedbyAdmin.getStatus().toString()){

			// If job has failed and the user want to retry
			flash.message = "Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being Retried"
			log.info("Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being Retried")
			promotionJobInstance.properties = [status:JobStatus.Pending_Retry.getStatus()]

		}

		else{

			// If job was previously successful and user want to re-promote
			flash.message = "Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being Re-promoted"
			log.info("Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being Re-promoted")
			promotionJobInstance.properties = [status:JobStatus.Pending_Repromote.getStatus()]

		}


		redirect(action: "list")

	}

	def index(Integer max) {redirect(action: "list", params: params)}

	def list(Integer max) {
		params.max = Math.min(max ?: 25, 50)
		log.debug "Commerce Object count:" + CommerceObject.count()
		respond CommerceObject.list(params), model:[commerceObjectInstanceCount: CommerceObject.count()]
	}

	def show(CommerceObject commerceObjectInstance)
	{
		def parentSecureProgram = SecureProgram.where{commerceObjects{id==commerceObjectInstance.id}}.list()
		render(view:"show", model:[commerceObjectInstance:commerceObjectInstance, parentSecureProgram:parentSecureProgram ])
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def create() {

		Long lNumber =5570000001
		def lisbnNumber = CommerceObject.executeQuery("select max(isbnNumber) from CommerceObject where isbnNumber like '%557%' and LENGTH(isbnNumber) = 10")
		String strISBN= lisbnNumber[0]
		if(strISBN!=null){
			lNumber = Long.valueOf(strISBN)
			if(lNumber>=5570000001)
				lNumber++
		}
		params.isbnNumber=lNumber

		respond new CommerceObject(params)
	}

	@Transactional
	def save() {

		def contentType = ContentType.where{id==4}.get()
		params.userUpdatingCO = springSecurityService?.currentUser?.username
		params.contentType = contentType
		def commerceObjectInstance = new CommerceObject(params)

		if (commerceObjectInstance == null) {
			log.info "Creating Commerce Object Not Found"
			notFound()
			return
		}
		log.info "Started saving Commerce Object :"+commerceObjectInstance.objectName
		if (commerceObjectInstance.hasErrors()) {
			log.info "No Errors Found while creating Commerce Object"
			respond commerceObjectInstance.errors, view:'create'
			return
		}

		//commerceObjectInstance.save flush:true
		if (!commerceObjectInstance.save(flush: true)) {
			render(view: "create", model: [commerceObjectInstance: commerceObjectInstance])
			return
		}
		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'commerceObjectInstance.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect commerceObjectInstance
			}
			'*' { respond commerceObjectInstance, [status: CREATED] }
		}
		log.info "Successfully saved Commerce Object :"+commerceObjectInstance.objectName
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def edit(CommerceObject commerceObjectInstance) {
		respond commerceObjectInstance
	}

	@Transactional
	def update(CommerceObject commerceObjectInstance) {
		if (commerceObjectInstance == null) {
			log.info "Updating Commerce Object Not Found"
			notFound()
			return
		}
		log.info "Started updating Commerce Object :"+commerceObjectInstance.objectName
		if (commerceObjectInstance.hasErrors()) {
			log.info "No Errors Found while updating Commerce Object"
			respond commerceObjectInstance.errors, view:'edit'
			return
		}
		commerceObjectInstance.userUpdatingCO = springSecurityService?.currentUser?.username
		commerceObjectInstance.save flush:true
		// update the timeStamp of all its parents so that the change is reflected in Envers
		updateParent(commerceObjectInstance)

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'CommerceObject.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect commerceObjectInstance
			}
			'*'{ respond commerceObjectInstance, [status: OK] }
		}
		log.info "Successfully updated Commerce Object :"+commerceObjectInstance.objectName
	}

	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def delete(CommerceObject commerceObjectInstance) {

		if (commerceObjectInstance == null) {
			log.info "Deleting Commerce Object Not Found"
			notFound()
			return
		}

		log.info "Updating the revisions of parent objects"
		updateParent(commerceObjectInstance)

		log.info "Removing Parent associations from the CO that is being deleted"
		removeAssociations(commerceObjectInstance)

		log.info "Started deleting Commerce Object :"+commerceObjectInstance.objectName
		commerceObjectInstance.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'CommerceObject.label', default: 'CommerceObject'), commerceObjectInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
		log.info "Successfully deleted Commerce Object :"+commerceObjectInstance.objectName
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'commerceObjectInstance.label', default: 'CommerceObject'), params.id])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}

	/**
	 * Download the log file
	 * @return
	 */
	def download() {
		utilityService.getLogFile(params.logFile)
	}

}
