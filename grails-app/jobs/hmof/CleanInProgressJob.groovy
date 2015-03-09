package hmof

import hmof.deploy.*
/**
 * This is a job for the Quartz scheduler.  It is called every 3 hours.
 * It checks how many of the jobs are in IN_PROGRES then change the status as FAILED.
 *
 */

class CleanInProgressJob {
		
	def concurrent = true
	def group = "scheduled"
	def deploymentService
	def UtilityService
	static triggers = {		
		cron name: 'cronTrigger1', cronExpression: "0 0 2 * * ?"	//  Schedule for everynight at 2:00am
		
	}

	def execute() {		
	def promotionJobInstanceList =UtilityService.getJobsStuckInProgress()
		if(null!=promotionJobInstanceList && !promotionJobInstanceList.isEmpty())
		{
			promotionJobInstanceList.each{
				def promotionJobStruckInstance=it
				Long promotionJobId =  promotionJobStruckInstance.id
				def promotionSInstance = Promotion.get(promotionJobId)
				promotionSInstance.properties = [status: JobStatus.FailedbyAdmin.getStatus()]
				promotionSInstance.save(failOnError: true, flush:true)
			}
		}
	}
	
}
