package hmof


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import hmof.deploy.Job
import hmof.deploy.Promotion
import hmof.security.User
import hmof.security.Role
import hmof.security.UserRole
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.log4j.Logger
import java.util.Properties
/**
 * BundleController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class BundleController {

	def springSecurityService
	def deploymentService
	def utilityService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


	/**
	 * Update Parent when a change is made to a child
	 * @param currentInstance
	 * @return
	 */
	@Transactional
	def updateParent(def currentInstance){

		def bundle = Bundle.where{id==currentInstance.id}.get()
		// get parent of current Instance
		def ProgramToUpdate = bundle.program
		ProgramToUpdate.properties = [lastUpdated: new Date(),userUpdatingProgram: springSecurityService?.currentUser?.username]

	}

	/**
	 * Remove Children before deleting the Bundle as the Children are also Standalone
	 * @param currentInstance
	 * @return
	 */
	@Transactional
	def removeAssociations(def currentInstance){

		def bundleToDelete = Bundle.where{id==currentInstance.id}.get()
		def child = []
		child += bundleToDelete.secureProgram

		child.each{sp -> bundleToDelete.removeFromSecureProgram(sp) }

	}

	/**
	 * Persist job details to the job and promotions tables
	 * @return
	 */
	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def deploy(){

		def instanceDetail = params.instanceDetail

		def instanceDetails = instanceDetail.split("/")
		def instanceId = instanceDetails[0]
		log.info("Bundle  Detail: "+instanceId)
		def (secureProgram, commerceObject) = deploymentService.getBundleChildren(instanceId)
		def childContent = secureProgram + commerceObject
		log.info("childContent: "+childContent)
		def bundleInstance = Bundle.get(instanceId)
		log.info("Deploying bundleInstance ISBN: "+bundleInstance.isbn)
		if(childContent.isEmpty()){

			flash.message = "The '${bundleInstance.title}' Bundle cannot be deployed as it does not have a Secure Program "
			log.info("The '${bundleInstance.title}' Bundle cannot be deployed as it does not have a Secure Program ")
			redirect(action: "list")
			return

		}

		def deploymentJobNumber = deploymentService.getCurrentJobNumber()
		log.info("deploymentJobNumber: "+deploymentJobNumber)
		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()
		log.info("userId: "+userId)
		log.info("contentId: "+bundleInstance.id)
		log.info("contentTypeId: "+bundleInstance.contentType.contentId)
		// Create a map of job data to persist
		def job = [contentId: bundleInstance.id, revision: deploymentService.getCurrentEnversRevision(bundleInstance), contentTypeId: bundleInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

		// Add Program instance to Job
		Job j1 = new Job(job).save(failOnError:true)
		log.info("Successfully added Bundle Instance to job : "+bundleInstance.isbn)
		childContent.each{
			log.info("Adding child content to job content id"+it.id+",revision"+deploymentService.getCurrentEnversRevision(it)+",contentTypeId"+it.contentType.contentId+",jobNumber"+j1.getJobNumber())
			def content = [contentId: it.id, revision: deploymentService.getCurrentEnversRevision(it), contentTypeId: it.contentType.contentId, jobNumber: j1.getJobNumber(), user: userId]
			Job j2 = new Job(content).save(failOnError:true)
			log.info("Successfully added child content ID:"+it.id+" to job")
		}

		// Add child relationship to each bundle
		def jobToUpdate = Job.where{jobNumber == deploymentJobNumber && contentTypeId==2 }.list()

		jobToUpdate.each{

			def bundleJobInstance = it
			def childMap = deploymentService.getChildrenMap(bundleJobInstance.contentId)
			bundleJobInstance.children = childMap

		}


		def envId = deploymentService.getUserEnvironmentInformation()
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

		def instanceDetail = params.instanceToBePromoted
		def instanceDetails = instanceDetail.split("/")
		def instanceToBePromoted = instanceDetails[0]

		def bundleInstance = Bundle.get(instanceToBePromoted)
		log.info("Promoting bundleInstance isbn: "+bundleInstance.isbn)
		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = deploymentService.getUserEnvironmentInformation()
		log.info("User Id: "+userId+",envId:"+envId)
		def promotionInstance = deploymentService.getDeployedInstance(bundleInstance, envId)

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
		promotionJobInstance.status == JobStatus.Pending_Repromote.getStatus().toString() || promotionJobInstance.status == JobStatus.Repromoting.getStatus().toString()){

			flash.message = "Job cannot be re-promoted as it is ${promotionJobInstance.status}"
			log.info("Job cannot be re-promoted as it is ${promotionJobInstance.status}")
		}

		else{

			// If job has failed or is successful and user want to re-promote
			flash.message = "Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being re-promoted"
			log.info("Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being re-promoted")
			promotionJobInstance.properties = [status:JobStatus.Pending_Repromote.getStatus()]

		}

		redirect(action: "list")

	}

	def index(Integer max) {redirect(action: "list", params: params)}

	def list(Integer max) {
		params.max = Math.min(max ?: 100, 200)
		log.debug "Bundle count:" + Bundle.count()
		respond Bundle.list(params), model:[bundleInstanceCount: Bundle.count()]
	}

	def show(Bundle bundleInstance) {
		
		def sapResultsList=Sap.list()
		
		 render(view:"show", model:[bundleInstance:bundleInstance,sapResultsList:sapResultsList])
			
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def create() {
		respond new Bundle(params)
	}

	@Transactional
	def save() {

		def contentType = ContentType.where{id==2}.get()
		params.userUpdatingBundle = springSecurityService?.currentUser?.username
		params.contentType = contentType
		def bundleInstance = new Bundle(params)
		
		
		if (bundleInstance == null) {
			log.info "Creating Bundle Not Found"
			notFound()
			return
		}
		log.info "Started saving Bundle ISBN:"+bundleInstance.isbn
		if (bundleInstance.hasErrors()) {
			log.info "No Errors Found while creating Bundle"
			respond bundleInstance.errors, view:'create'
			return
		}
			
		
	InetAddress address = InetAddress.getByName("172.17.101.75");
	boolean reachable = address.isReachable(5000);
	if(reachable){
		
		log.info("SAP Service Reachable")
		def sapResultsMap= utilityService.getIsbnRecord(bundleInstance.isbn)
		if(sapResultsMap!=null && !sapResultsMap.isEmpty()){
			sapResultsMap.each {
				String sapIsbn=it.key
			bundleInstance.sap = new Sap(isbn: sapIsbn,bundle: bundleInstance, status:it.value,dateCreated:new Date())
				
			 }
		 }else
	 {
		 bundleInstance.sap = new Sap(isbn: bundleInstance.isbn,bundle: bundleInstance, status:"",dateCreated:new Date())
	 }
		
	}else
	{
		bundleInstance.sap = new Sap(isbn: bundleInstance.isbn,bundle: bundleInstance, status:"",dateCreated:new Date())	
	   log.error("SAP Service not Reachable")
	}
		
		
		
		if (!bundleInstance.save(flush: true)) {
			render(view: "create", model: [bundleInstance: bundleInstance])
			return
		}

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'bundleInstance.label', default: 'Bundle'), bundleInstance.id])
				redirect bundleInstance
			}
			'*' { respond bundleInstance, [status: CREATED] }
		}
		log.info "Successfully saved Bundle ISBN:"+bundleInstance.isbn
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def edit(Bundle bundleInstance) {
		respond bundleInstance
	}

	@Transactional
	def update(Bundle bundleInstance) {
		if (bundleInstance == null) {
			log.info "Updating Bundle Not Found"
			notFound()
			return
		}
		log.info "Started updating Bundle ISBN:"+bundleInstance.isbn
		if (bundleInstance.hasErrors()) {
			log.info "No Errors Found while updating Bundle"
			respond bundleInstance.errors, view:'edit'
			return
		}
		List<SecureProgram> secureProgList=[]
		if(params.secureProgram==null)
		{
			bundleInstance.secureProgram.clear()
		}else{			
		
		for (SecureProgram modelListdata : bundleInstance.secureProgram) {
			for (String prevListdata : params.list('secureProgram')*.toLong()) {
						
			if(modelListdata.id==Integer.parseInt(prevListdata))	{				
				secureProgList.add(modelListdata)
				}
			}
		}
		bundleInstance.secureProgram=new TreeSet<SecureProgram>(secureProgList)
		}
		bundleInstance.userUpdatingBundle = springSecurityService?.currentUser?.username
		bundleInstance.save flush:true

		// update the timeStamp of its parent so that the change is reflected in Envers
		updateParent(bundleInstance)

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'Bundle.label', default: 'Bundle'), bundleInstance.id])
				redirect bundleInstance
			}
			'*'{ respond bundleInstance, [status: OK] }
		}
		log.info "Successfully updated Bundle ISBN:"+bundleInstance.isbn
	}

	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def delete(Bundle bundleInstance) {

		if (bundleInstance == null) {
			log.info "Deleting Bundle Not Found"
			notFound()
			return
		}

		log.info "Removing Secure Program associations from the Bundle that is being deleted"
		removeAssociations(bundleInstance)

		log.info "Started deleting Bundle ISBN: " + bundleInstance.isbn
		bundleInstance.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'Bundle.label', default: 'Bundle'), bundleInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
		log.info "Successfully deleted Bundle ISBN: " + bundleInstance.isbn
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'bundleInstance.label', default: 'Bundle'), params.id])
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
		log.debug "Downloading log for Bundle deployment"
		utilityService.getLogFile(params.logFile)
	}
	/**
	 * Get the children objects of an individual Bundle (Bundle) which should have SecureProgram, and Content C
	 * @param instanceId
	 * @return
	 */
	def getDashboardBundleChildren() {
		def instanceId=params.instanceId
	
		// deployable content A has a SecureProgram
		def deployableBundle = Bundle.where{ id==instanceId && secureProgram{}}

		// get a unique listing of Content B belonging to the deployable Content A
		Set uniqueSecureProgram = deployableBundle.list().secureProgram.id.flatten()

		// deployment logic for SP
		def deployableSecureProgram = []
		// Needed for MySql database
		if(!uniqueSecureProgram.isEmpty()){
			deployableSecureProgram = SecureProgram.where{id in uniqueSecureProgram && includeDashboardObject==true}.list(max:1)
		}

		
		//deployableSecureProgram
		def jsonCODetails
		if(deployableSecureProgram.size()>0){
		jsonCODetails = [name:deployableSecureProgram.productName,count:deployableSecureProgram.size()]
		log.debug "jsonCODetails:"+jsonCODetails
		}else{
		jsonCODetails = [:]
		}
	
		render jsonCODetails as JSON
	}
	
	/**
	 * Get the children objects of an individual Bundle (Bundle) which should have SecureProgram, and Content C
	 * @param instanceId
	 * @return
	 */
	def getDashboardSecureProgramChildren() {
		
		def instanceId=params.instanceId
		// deployable content A has a SecureProgram
		def deployableBundle = Bundle.where{ id==instanceId && secureProgram{}}

		// get a unique listing of Content B belonging to the deployable Content A
		Set uniqueSecureProgram = deployableBundle.list().secureProgram.id.flatten()

		// deployment logic for SP
		def deployableSecureProgram = []
		// Needed for MySql database
		if(!uniqueSecureProgram.isEmpty()){
			deployableSecureProgram = SecureProgram.where{id in uniqueSecureProgram && includeDashboardObject==true}.list()
		}

		// get a unique listing of CO belonging to the SP being deployed
		Set uniqueCommerceObject = deployableSecureProgram.commerceObjects.id.flatten()

		// deployment logic for CO
		def deployableCommerceObject = []
		if(!uniqueCommerceObject.isEmpty()){
			deployableCommerceObject = CommerceObject.where{id in uniqueCommerceObject}.list(sort:'objectReorderNumber', order:'asc')
		}
		deployableCommerceObject
		def jsonCODetails
		if(deployableCommerceObject.size()>0){
		jsonCODetails = [coverimage:deployableCommerceObject.pathToCoverImage,teacherLabel:deployableCommerceObject.teacherLabel,count:deployableCommerceObject.size(),ordernum:deployableCommerceObject.objectReorderNumber]
		log.debug "jsonCODetails:"+jsonCODetails
		}else{
		jsonCODetails = [:]
		}
		
		render jsonCODetails as JSON
		
	}
}
