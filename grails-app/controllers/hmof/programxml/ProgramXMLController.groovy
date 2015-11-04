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
		//def securePrograms = SecureProgram.where{includeDashboardObject==true}.list()		
		def secureProgramsXML = utilityService.getProgramXMLSecurePrograms()
		
		 def securePrograms =SecureProgram.where{id in (secureProgramsXML.id)}
		respond new ProgramXML(params), model:[securePrograms:securePrograms]
    }

    @Transactional
	@Secured(['ROLE_PM','ROLE_ADMIN'])
    def save() {
		def contentType = ContentType.where{id==5}.get()
	//	params.userUpdatingProgram = springSecurityService?.currentUser?.username
		params.contentType = contentType
		def programXMLInstance = new ProgramXML(params)
		def securePrograms = SecureProgram.where{includeDashboardObject==true}.list()
        if (programXMLInstance == null) {
            notFound()
            return
        }
		
        if (programXMLInstance.hasErrors()) {
            respond programXMLInstance.errors, view:'create',model:[securePrograms:securePrograms]
            return
        }
		
		     
        programXMLInstance.save (failOnError:true,flush:true)
		
		
		
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'programXMLInstance.label', default: 'ProgramXML'), programXMLInstance.id])
                redirect programXMLInstance
            }
            '*' { respond programXMLInstance,[status: CREATED] }
        }
    }
	@Secured(['ROLE_PM','ROLE_ADMIN'])
    def edit(ProgramXML programXMLInstance) {	
      def secureProgramsXML = utilityService.getProgramXMLSecurePrograms()	
	 def securePrograms1 =SecureProgram.where{id in (programXMLInstance.secureProgram.id)}.list()
	 securePrograms1.addAll(secureProgramsXML)
	  def securePrograms =SecureProgram.where{id in (securePrograms1.id)}
	
	  respond programXMLInstance, model:[securePrograms:securePrograms]
    }

    @Transactional
    def update(ProgramXML programXMLInstance) {
        if (programXMLInstance == null) {
            notFound()
            return
        }

        if (programXMLInstance.hasErrors()) {
            respond programXMLInstance.errors, view:'edit'
            return
        }
		
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
        programXMLInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ProgramXML.label', default: 'ProgramXML'), programXMLInstance.id])
                redirect programXMLInstance
            }
            '*'{ respond programXMLInstance, [status: OK] }
        }
    }

    @Transactional
	@Secured(['ROLE_PM','ROLE_ADMIN'])
    def delete(ProgramXML programXMLInstance) {

        if (programXMLInstance == null) {
            notFound()
            return
        }

        programXMLInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ProgramXML.label', default: 'ProgramXML'), programXMLInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'programXMLInstance.label', default: 'ProgramXML'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	@Transactional
	def confirm() {
		def msg
		println params.job.id
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
				
		def programXMLInstance = ProgramXML.get(instanceId)
		log.debug("Deploying programInstance name: "+programXMLInstance.title)
		def deploymentJobNumber = deploymentService.getCurrentJobNumber()
		log.debug("deploymentJobNumber: "+deploymentJobNumber)
		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()

		/*//def doesPreviousJobExist1 =params.doesPreviousJobExist1

		boolean previousJobExist=false
		if(doesPreviousJobExist1!=null){
			previousJobExist=true
		}
		*/
		log.debug("userId: "+userId)
		log.debug("contentId: "+programXMLInstance.id)
		
		
		//Generate XML
		def isXMLGenerated=programXmlService.generateProramXML(programXMLInstance)
		
		//end
		if(isXMLGenerated){
		//log.debug("contentTypeId: "+programXMLInstance.contentType.contentId)
		// Create a map of job data to persist
		def job = [contentId: programXMLInstance.id, revision: deploymentService.getCurrentEnversRevision(programXMLInstance), contentTypeId: programXMLInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

		// Add Program instance to Job
		Job j1 = new Job(job).save(failOnError:true)
		log.debug("Successfully added Program Instance to job : "+programXMLInstance.title)
	
		//def envId = deploymentService.getUserEnvironmentInformation()
		def envId = params.depEnvId
		log.info("Environment ID:"+envId)
		log.info("Job:"+ j1+",jobNumber: "+j1.getJobNumber()+",JobStatus:"+ JobStatus.PendingProgramDeploy.getStatus())

		def promote = [status: JobStatus.PendingProgramDeploy.getStatus(), job: j1, jobNumber: j1.getJobNumber(), user: userId, environments: envId,smartDeploy:false]
		Promotion p1 = new Promotion(promote).save(failOnError:true)
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

}
