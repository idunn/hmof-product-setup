package hmof

class ProgramXMLDeployJob {
	
	def concurrent = true

	def deploymentService
	def programXmlService
	static triggers = {
		simple startDelay: 60000, repeatInterval: 40 * 1000 // execute job once in 40 seconds		
		
	}

	def execute() {
		def result = []
		
		// execute job
		result= programXmlService.executeJob()
		if(result !=null){
			println "Results of deployment/promotion: " + result
			deploymentService.updateStatus(result)
		}
	}
}
