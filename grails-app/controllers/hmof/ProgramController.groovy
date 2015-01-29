package hmof

import hmof.deploy.Environment
import hmof.deploy.Job
import hmof.deploy.Promotion
import hmof.security.User
import hmof.security.Role
import hmof.security.UserRole


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import org.apache.log4j.Logger;
/**
 * ProgramController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class ProgramController {

	def springSecurityService
	def deploymentService
	def utilityService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


	/**
	 * Persist job details to the job and promotions tables
	 * @return
	 */
	@Transactional
	def deploy(){

		def instanceDetail = params.instanceDetail

		def instanceDetails = instanceDetail.split("/")
		def instanceId = instanceDetails[0]

		log.info("Program Detail: "+instanceId)
		def (bundle, secureProgram, commerceObject) = deploymentService.getProgramChildren(instanceId)
		def childContent = bundle + secureProgram + commerceObject
		log.info("childContent: "+childContent)
		def programInstance = Program.get(instanceId)
		log.info("Deploying programInstance name: "+programInstance.name)
		def deploymentJobNumber = deploymentService.getCurrentJobNumber()
		log.info("deploymentJobNumber: "+deploymentJobNumber)
		def user = springSecurityService?.currentUser?.id
		def userId = User.where{id==user}.get()

		def doesPreviousJobExist1 =params.doesPreviousJobExist1

		boolean previousJobExist=false
		if(doesPreviousJobExist1!=null){
			previousJobExist=true
		}

		log.info("userId: "+userId)
		log.info("contentId: "+programInstance.id)
		log.info("contentTypeId: "+programInstance.contentType.contentId)
		// Create a map of job data to persist
		def job = [contentId: programInstance.id, revision: deploymentService.getCurrentEnversRevision(programInstance), contentTypeId: programInstance.contentType.contentId, jobNumber: deploymentJobNumber, user: userId]

		// Add Program instance to Job
		Job j1 = new Job(job).save(failOnError:true)
		log.info("Successfully added Program Instance to job : "+programInstance.name)
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
		log.info("Job:"+ j1+",jobNumber: "+j1.getJobNumber()+",JobStatus:"+ JobStatus.Pending)

		def promote = [status: JobStatus.Pending, job: j1, jobNumber: j1.getJobNumber(), user: userId, environments: envId,smartDeploy:previousJobExist]
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

		def programInstance = Program.get(instanceToBePromoted)
		log.info("Promoting programInstance name: "+programInstance.name)
		def userId = User.where{id==springSecurityService?.currentUser?.id}.get()

		def envId = deploymentService.getUserEnvironmentInformation()
		log.info("User Id: "+userId+",envId:"+envId)
		def promotionInstance = deploymentService.getDeployedInstance(programInstance, envId)

		if(promotionInstance==null){

			flash.message = "Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment"
			log.info("Job cannot be promoted as content has not been successfully deployed or promoted to a previous environment")
			redirect(action: "list")
			return
		}
		def doesPreviousJobExist1 =params.doesPreviousJobExist1

		boolean previousJobExist=false
		if(doesPreviousJobExist1!=null){
			previousJobExist=true
		}

		def jobInstance = Job.where{id == promotionInstance.jobId}.get()
		log.info("jobNumber:"+promotionInstance.getJobNumber())
		def promotionJobInstance = Promotion.where{jobNumber==promotionInstance.getJobNumber() && environments{id == envId.id}}.get()?:none

		if(promotionJobInstance==none){

			def promote = [status: JobStatus.Pending, job: jobInstance, jobNumber: promotionInstance.getJobNumber(), user: userId, environments: envId,smartDeploy:previousJobExist]
			Promotion p2 = new Promotion(promote).save(failOnError:true, flush:true)
			log.info("Job saved successfully")

		} else if(promotionJobInstance.status == JobStatus.In_Progress.toString() || promotionJobInstance.status == JobStatus.Pending.toString()){

			flash.message = "Job cannot be re-promoted as it is ${promotionJobInstance.status}"
			log.info("Job cannot be re-promoted as it is ${promotionJobInstance.status}")
		}

		else{

			// If job has failed or is successful and user want to re-promote
			flash.message = "Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being re-promoted"
			log.info("Job ${promotionJobInstance.jobNumber} that was in ${promotionJobInstance.status} status is being re-promoted")
			promotionJobInstance.properties = [status:JobStatus.Pending, smartDeploy:previousJobExist]

		}

		redirect(action: "list")

	}


	def index(Integer max) {redirect(action: "list", params: params)}

	def list(Integer max) {
		params.max = Math.min(max ?: 50, 100)
		log.info "Program count:"+Program.count()
		respond Program.list(params), model:[programInstanceCount: Program.count()]
	}

	def show(Program programInstance) {
		respond programInstance
	}
	@Secured(['ROLE_ADMIN'])
	def create() {
		respond new Program(params)
	}

	@Transactional
	def save() {

		def contentType = ContentType.where{id==1}.get()
		params.contentType = contentType
		def programInstance = new Program(params)

		if (programInstance == null) {
			log.info "Creating Program Not Found"
			notFound()
			return
		}
		log.info "Started saving Program :"+programInstance.name
		if (programInstance.hasErrors()) {
			log.info "No Errors Found while creating Program"
			respond programInstance.errors, view:'create'
			return
		}

		programInstance.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'programInstance.label', default: 'Program'), programInstance.id])
				redirect programInstance
			}
			'*' { respond programInstance, [status: CREATED] }
		}
		log.info "Successfully saved Program :"+programInstance.name
	}
	@Secured(['ROLE_ADMIN'])
	def edit(Program programInstance) {
		respond programInstance
	}

	@Transactional
	def update(Program programInstance) {
		if (programInstance == null) {
			log.info "Updating Program Not Found"
			notFound()
			return
		}
		log.info "Started updating Program :"+programInstance.name
		if (programInstance.hasErrors()) {
			log.info "No Errors Found while updating Program"
			respond programInstance.errors, view:'edit'
			return
		}

		programInstance.save flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'Program.label', default: 'Program'), programInstance.id])
				redirect programInstance
			}
			'*'{ respond programInstance, [status: OK] }
		}
		log.info "Successfully updated Program :"+programInstance.name
	}

	@Transactional
	@Secured(['ROLE_ADMIN'])
	def delete(Program programInstance) {

		if (programInstance == null) {
			log.info "Deleting Program Not Found"
			notFound()
			return
		}
		log.info "Started deleting Program :"+programInstance.name
		programInstance.delete flush:true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'Program.label', default: 'Program'), programInstance.id])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
		log.info "Successfully deleted Program :"+programInstance.name
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'programInstance.label', default: 'Program'), params.id])
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
