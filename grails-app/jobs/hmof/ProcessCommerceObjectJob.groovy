package hmof



class ProcessCommerceObjectJob {
    static triggers = {
      //simple repeatInterval: 30 * 1000 // execute job once in 30 seconds
		simple repeatInterval: 120 * 1000 // execute job once in 120 seconds - testing
    }

    def execute() {
        // execute job
		println "Quartz Job running!" + new Date()
    }
}
