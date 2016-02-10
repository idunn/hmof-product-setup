package hmof.programxml


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import hmof.deploy.*
import hmof.security.*
import hmof.*
import grails.plugin.springsecurity.annotation.Secured
@Transactional(readOnly = true)
class ProgramXMLController {
	def springSecurityService
	def deploymentService
	def utilityService
	def programXmlService
	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	/*def index(Integer max) {
	 params.max = Math.min(max ?: 10, 100)
	 respond ProgramXML.list(params), model:[programXMLInstanceCount: ProgramXML.count()]
	 }*/
	def index(Integer max) {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 25, 50)

		respond ProgramXML.list(params), model:[programXMLInstanceCount: ProgramXML.count()]
	}

	def show(ProgramXML programXMLInstance) {
		respond programXMLInstance
	}

	def create() {

		def secureProgramsXML = utilityService.getProgramXMLSecurePrograms()

		//def securePrograms1 =SecureProgram.where{id in (secureProgramsXML.id)}.list().unique{ it.onlineIsbn }
		def securePrograms =SecureProgram.where{id in (secureProgramsXML.id)}
		respond new ProgramXML(params), model:[securePrograms:securePrograms]
	}

	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def save() {
		def contentType = ContentType.where{id==5}.get()
		params.userUpdatingProgramXML = springSecurityService?.currentUser?.username
		params.contentType = contentType
		def programXMLInstance = new ProgramXML(params)
		def secureProgramsAll = SecureProgram.where{includeDashboardObject==true}.list()
		if (programXMLInstance == null) {
			notFound()
			return
		}

		if (programXMLInstance.hasErrors()) {
			respond programXMLInstance.errors, view:'create',model:[securePrograms:secureProgramsAll]
			return
		}
		
		def secureProgramsXML =params.list('secureProgram')*.toLong()
		def errorMessage=isSPOnlineIsbnUnique(secureProgramsXML,programXMLInstance)
		
		

		if(errorMessage && errorMessage!=null){
			programXMLInstance.discard()
			flash.message = message(code: errorMessage, default: errorMessage)
			redirect(action: "create",params: [id: programXMLInstance.id])
			return
		}


		programXMLInstance.save (failOnError:true,flush:true)

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'programXMLInstance.label', default: 'ProgramXML'),
					programXMLInstance.id
				])
				redirect programXMLInstance
			}
			'*' { respond programXMLInstance,[status: CREATED] }
		}
	}
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def edit(ProgramXML programXMLInstance) {

		def securePrograms =[]
		def securePrograms1=[]
		def secureProgramsList=[]
		def secureProgramsList1=[]
		def secureProgramsXML = utilityService.getProgramXMLSecurePrograms()

		if(programXMLInstance.secureProgram){
			securePrograms1 =SecureProgram.where{id in (programXMLInstance.secureProgram.id)}.list()
		}
		securePrograms1.addAll(secureProgramsXML)


		def title=programXMLInstance.title
		securePrograms =SecureProgram.where{id in (securePrograms1.id)}

		respond programXMLInstance, model:[securePrograms:securePrograms,title:title]
	}



	@Transactional
	def update(ProgramXML programXMLInstance) {
		if (programXMLInstance == null) {
			notFound()
			return
		}

		if (programXMLInstance.hasErrors()) {

			respond programXMLInstance.errors, view:'edit',model:[securePrograms:securePrograms,title:title]
			return
		}
		def secureProgramsXML =params.list('secureProgram')*.toLong()
		def errorMessage=isSPOnlineIsbnUnique(secureProgramsXML,programXMLInstance)

		if(errorMessage && errorMessage!=null){
			programXMLInstance.discard()
			flash.message = message(code: errorMessage, default: errorMessage)
			redirect(action: "edit",params: [id: programXMLInstance.id])
			return
		}
		//Unique Online Isbn code ends here

		List<SecureProgram> secureProgList=[]
		if(params.secureProgram==null)
		{
			programXMLInstance.secureProgram.clear()
		}else{

			for (SecureProgram modelListdata : programXMLInstance.secureProgram) {
				for (String prevListdata : params.list('secureProgram')*.toLong()) {

					if(modelListdata.id==Integer.parseInt(prevListdata))	{
						secureProgList.add(modelListdata)
					}
				}
			}
			programXMLInstance.secureProgram=new TreeSet<SecureProgram>(secureProgList)
		}

		programXMLInstance.userUpdatingProgramXML = springSecurityService?.currentUser?.username
		programXMLInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [
					message(code: 'ProgramXML.label', default: 'ProgramXML'),
					programXMLInstance.id
				])
				redirect programXMLInstance
			}
			'*'{ respond programXMLInstance, [status: OK] }
		}
	}
	/**
	 * Remove Children before deleting the SecureProgram as the Children are also Standalone
	 * @param currentInstance
	 * @return
	 */
	@Transactional
	def removeAssociations(def currentInstance){

		def programXMLToDelete = ProgramXML.where{id==currentInstance.id}.get()
		def child = []
		child += programXMLToDelete.secureProgram

		child.each{sp -> programXMLToDelete.removeFromSecureProgram(sp) }

	}
	@Transactional
	@Secured(['ROLE_PM', 'ROLE_ADMIN'])
	def delete(ProgramXML programXMLInstance) {

		if (programXMLInstance == null) {
			notFound()
			return
		}
		removeAssociations(programXMLInstance)
		programXMLInstance.delete flush:true



		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'ProgramXML.label', default: 'ProgramXML'),
					programXMLInstance.id
				])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [
					message(code: 'programXMLInstance.label', default: 'ProgramXML'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
	@Transactional
	def confirm() {
		def msg

		def programXMLInstance = ProgramXML.get(params.job.id)
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
		def latestRevision=	deploymentService.getCurrentEnversRevision(programXMLInstance)
		environmentRevision=deploymentService.getPromotionDetails(programXMLInstance,envId)
		environmentRevision=environmentRevision[2]
		def envName=Environment.where{id==envId}.name.get()
		if (environmentRevision != null) {
			environmentRevision = environmentRevision
		}

		def doesPJobExists=deploymentService.doesPreviousJobExist(programXMLInstance.id,envId)
		def lowEnvRevision=deploymentService.isLowerEnvironmentEqual(programXMLInstance,envId)
		if (latestRevision == environmentRevision) {
			msg="A job with the same revision already exists on the environment, Do you want to proceed?"
		}
		else if(latestRevision != environmentRevision)
		{
			msg='Are you sure you want to deploy?'
		}
		if(envId.equals("2") || envId.equals("3")){
			def promotionInstance = deploymentService.getDeployedInstance(programXMLInstance,envId)

			if(promotionInstance==null){
				flash.message = message(code: 'promote.no.environments', default: 'Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment')
				log.info("Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment")
				//redirect(action: "list")
				//return
			}
		}
		//  Only set this if everything has passed inspection.  If it hasn't we want to make sure this bad deployment instance can never be used
		if(!flash.message)
		{
			flash.programXMLInstance = programXMLInstance
		}



		render(view: "_confirm", model: [programXMLInstance:programXMLInstance,msg:msg,envName:envName,envId:envId])
	}
	/**
	 * Persist job details to the job and promotions tables
	 * @return
	 */
	@Transactional
	def deploy(){

		def instanceId = params.programId

		//def instanceDetails = instanceDetail.split("/")
		//def instanceId = instanceDetails[0]

		log.info("ProgramXML Detail: "+instanceId)
		def programXMLInstance = ProgramXML.where{id==instanceId}.get()
		log.debug("Deploying programInstance name: "+programXMLInstance.title)
		def (secureProgram) = deploymentService.getProgramXmlChildren(instanceId)
		def childContent = secureProgram
		log.info("childContent: "+childContent)
		if(childContent.isEmpty()){
			flash.message = "The '${programXMLInstance.title}' ProgramXml cannot be deployed as it does not have a Secure Program "
			log.info("The '${programXMLInstance.title}' ProgramXml cannot be deployed as it does not have a Secure Program ")
			redirect(action: "list")
			return
		}


		def deploymentJobNumber = deploymentService.getCurrentJobNumber()
		log.info("deploymentJobNumber: "+deploymentJobNumber)
		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()

		/*//def doesPreviousJobExist1 =params.doesPreviousJobExist1
		 boolean previousJobExist=false
		 if(doesPreviousJobExist1!=null){
		 previousJobExist=true
		 }
		 */
		log.info("userId: "+userId)
		log.info("contentId: "+programXMLInstance.id)


		//Generate XML
		def isXMLGenerated=programXmlService.generateProramXML(programXMLInstance)

		//end
		if(isXMLGenerated){
			//log.debug("contentTypeId: "+programXMLInstance.contentType.contentId)
			// Create a map of job data to persist
			def job = [contentId: programXMLInstance.id, revision: deploymentService.getCurrentEnversRevision(programXMLInstance), contentTypeId: programXMLInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

			// Add Program instance to Job
			Job j1 = new Job(job).save(failOnError:true)
			log.info("Successfully added Program Instance to job : "+programXMLInstance.title)

			//def envId = deploymentService.getUserEnvironmentInformation()
			def envId = params.depEnvId
			log.info("Environment ID:"+envId)
			log.info("Job:"+ j1+",jobNumber: "+j1.getJobNumber()+",JobStatus:"+ JobStatus.PendingProgramDeploy.getStatus())

			def promote = [status: JobStatus.PendingProgramDeploy.getStatus(), job: j1, jobNumber: j1.getJobNumber(), user: userId, environments: envId,smartDeploy:false,jira_id:""]
			Promotion p1 = new Promotion(promote).save(failOnError:true)
		}
		redirect(action: "list")

	}
	/**
	 * Promote content that has already been promoted to Dev OR QA
	 * @return
	 */
	@Transactional
	@Secured(['ROLE_QA', 'ROLE_PROD'])
	def promote(){
		log.info("Promoting programXMLInstance title: ")
		final String none = "none"

		//def instanceDetail = params.instanceToBePromoted
		//def instanceDetails = instanceDetail.split("/")
		def instanceToBePromoted =params.programId
		def jiraId =params.jiraid
		if(jiraId==null || jiraId=="")
		{
			jiraId="TT:1234"
		}
		def programXMLInstance = ProgramXML.get(instanceToBePromoted)
		log.info("Promoting programXMLInstance title: "+programXMLInstance.title)
		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId =params.depEnvId
		log.info("User Id: "+userId+",envId:"+envId)
		def promotionInstance = deploymentService.getDeployedInstance(programXMLInstance, envId)

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

			def promote = [status: JobStatus.PendingProgramDeploy.getStatus(), job: jobInstance, jobNumber: promotionInstance.getJobNumber(), user: userId, environments: envId,smartDeploy:false,jiraId:jiraId]
			Promotion p2 = new Promotion(promote).save(failOnError:true, flush:true)
			log.info("Job saved successfully")

		} else if(promotionJobInstance.status == JobStatus.In_Progress.getStatus().toString() || promotionJobInstance.status == JobStatus.PendingProgramDeploy.getStatus().toString() ||
		promotionJobInstance.status == JobStatus.PendingProgramRepromote.getStatus().toString() || promotionJobInstance.status == JobStatus.Repromoting.getStatus().toString() ||
		promotionJobInstance.status == JobStatus.PendingProgramRetry.getStatus().toString() || promotionJobInstance.status == JobStatus.Retrying.getStatus().toString()){

			flash.message = "Job cannot be re-promoted as it is ${promotionJobInstance.status}"
			log.info("Job cannot be re-promoted as it is ${promotionJobInstance.status}")
		}

		else if(promotionJobInstance.status == JobStatus.Failed.getStatus().toString() || promotionJobInstance.status == JobStatus.FailedbyAdmin.getStatus().toString()){

			// If job has failed and the user want to retry
			flash.message = "Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being Retried"
			log.info("Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being Retried")
			promotionJobInstance.properties = [status:JobStatus.PendingProgramRetry.getStatus(),jiraId:jiraId]

		}

		else{

			// If job was previously successful and user want to re-promote
			flash.message = "Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being Re-promoted"
			log.info("Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being Re-promoted")
			promotionJobInstance.properties = [status:JobStatus.PendingProgramRepromote.getStatus(),jiraId:jiraId]

		}

		redirect(action: "list")

	}
	/**
	 * Download the log file
	 * @return
	 */
	def download() {
		utilityService.getLogFile(params.logFile)
	}
	
	
	
	
	def isSPOnlineIsbnUnique(secureProgramsXML,programXMLInstance)
	{
		
				def securePrograms =[]
				def securePrograms1=[]
				def secureProgramsList=[]
				def uniqueIsbn=false
				StringBuffer sb=new StringBuffer()
				def errorMessage
							
				if(programXMLInstance.secureProgram){
					securePrograms1 =SecureProgram.where{id in (programXMLInstance.secureProgram.id)}.list()
				}
						
				def secureProgramXmls =[]
		
				if(securePrograms1 && secureProgramsXML){
		
					secureProgramXmls = SecureProgram.where{id in securePrograms1.id}.list()
					def allSecureProgramXmls =SecureProgram.where{id in secureProgramsXML}.list()
		
					allSecureProgramXmls.each{
		
						def spid= it
						def spid1= it.id
		
						def onlineIsbn=it.onlineIsbn
						def registerIsbn=it.registrationIsbn
						secureProgramXmls.each{
							if(spid1!=it.id && it.onlineIsbn.equals(onlineIsbn))
							{
								errorMessage="Secure Program ("+registerIsbn+") and Secure Program ("+it.registrationIsbn+") share the same online isbn ("+onlineIsbn+").This is not allowed please remove one of the secure program from this program xml."
								sb.append(errorMessage+"\n")
								uniqueIsbn=true
							}
						}
		
					}
		
					if(!uniqueIsbn){
						def programXmlids = utilityService.getProgramXMLISecurePrograms()
						if(programXmlids){
							secureProgramXmls = SecureProgram.where{id in programXmlids.secure_program_id}.list()
						}
		
						allSecureProgramXmls.each{
							def spid= it
							def spid1= it.id
		
							def onlineIsbn=it.onlineIsbn
							def registerIsbn=it.registrationIsbn		
							secureProgramXmls.each{
								if(spid1!=it.id && it.onlineIsbn.equals(onlineIsbn))
								{  //errorMessage="Secure Program ("+registerIsbn+") and Secure Program ("+it.registrationIsbn+") share the same online isbn ("+onlineIsbn+").This not allowed please remove one of the secure program from this program xml."
									def programXMLId1=utilityService.getProgramXMLID(it.id)
									def programXML1=ProgramXML.where{id in programXMLId1.programxml_secure_program_id}.get()
									if(programXML1)
									{
										errorMessage="Secure Program  ("+it.registrationIsbn+")  in ' "+programXML1.filename+"' and Secure Program  ("+registerIsbn+") in '"+programXMLInstance.filename+"' share the same online isbn ("+onlineIsbn+").This is not allowed."
									}
									
		
									sb.append(errorMessage+"\n")
									uniqueIsbn=true
									
								}
							}
		
						}
					}				
					
				}else if(secureProgramsXML && !uniqueIsbn)
				{
					
					def programXmlids = utilityService.getProgramXMLISecurePrograms()
					if(programXmlids){
						secureProgramXmls = SecureProgram.where{id in programXmlids.secure_program_id}.list()
					}
					def allSecureProgramXmls =SecureProgram.where{id in secureProgramsXML}.list()
					allSecureProgramXmls.each{
						def spid= it
						def spid1= it.id		
						def onlineIsbn=it.onlineIsbn
						def registerIsbn=it.registrationIsbn
						secureProgramXmls.each{
							if(spid1!=it.id && it.onlineIsbn.equals(onlineIsbn))
							{ 
								errorMessage="Secure Program ("+registerIsbn+") and Secure Program ("+it.registrationIsbn+") share the same online isbn ("+onlineIsbn+").This is not allowed please remove one of the secure program from this program xml."
							     sb.append(errorMessage+"\n")
								 uniqueIsbn=true
							}
						}
		
					}		
		
				}				
				
			errorMessage 				
				
	}

	
}
