package hmof

class ProgramXMLDeployJob {
	
	def concurrent = true

	def deploymentService
	def programXmlService
	static triggers = {
		simple repeatInterval: 60 * 1000 // execute job once in 60 seconds		
		
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
