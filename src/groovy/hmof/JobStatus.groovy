package hmof

public enum JobStatus {
	Pending("Pending"),
	Pending_Repromote("Pending_Repromote"),
	In_Progress("In_Progress"),
	Success("Success"),
	Failed("Failed"),
	Repromoting("Repromoting"),
	FailedbyAdmin("Failed - by Admin cleanup process"),
	//Failure("Failure") // TODO remove

	String status
	
	JobStatus(String status) {
	   this.status = status
	}
	String getStatus() {
	   return this.status
	}
		
	static JobStatus byId(String id) {
		values().find { it.status == id }
	}
}