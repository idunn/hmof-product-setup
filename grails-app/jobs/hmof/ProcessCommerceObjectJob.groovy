package hmof



class ProcessCommerceObjectJob {
    static triggers = {
      simple repeatInterval: 30 * 1000 // execute job once in 30 seconds
    }

    def execute() {
        // execute job
		println "Job Run!"
    }
}
