package hmof

import hmh.poc.deploy.Promotion
import hmh.poc.deploy.Job



class ProcessCommerceObjectJob {

	def deploymentService

	static triggers = {
		//simple repeatInterval: 30 * 1000 // execute job once in 30 seconds
		simple repeatInterval: 120 * 1000 // execute job once in 120 seconds - testing
	}

	def execute() {
		def result = []
		// execute job
		println "Inside Job"
		result = deploymentService.executeJob()
		if(result !=null){
			println "Results of deployment/promotion: " + result
			deploymentService.updateStatus(result)
		}else{ println 'Nothing to deploy/promote'}
	}
}
