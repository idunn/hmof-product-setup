package hmof

class ProcessJob {
	
	def concurrent = false

	def deploymentService

	static triggers = {
		simple repeatInterval: 30 * 1000 // execute job once in 30 seconds		
		
	}

	def execute() {
		def result = []
		// execute job
		result = deploymentService.executeJob()
		if(result !=null){
			println "Results of deployment/promotion: " + result
			deploymentService.updateStatus(result)
		}
	}
}
