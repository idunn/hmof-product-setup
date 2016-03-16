package hmof

class ProcessJob {
	
	def concurrent = true

	def deploymentService

	static triggers = {
		simple startDelay: 60000, repeatInterval: 30 * 1000 // execute job once in 30 seconds		
		
	}

	def execute() {
		def result = []
		
		// execute job
		result= deploymentService.executeJob()
		if(result !=null){
			println "Results of deployment/promotion: " + result
			deploymentService.updateStatus(result)
		}
	}
}
