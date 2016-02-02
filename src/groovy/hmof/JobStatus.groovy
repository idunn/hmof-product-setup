package hmof

public enum JobStatus {
	Pending("Pending"),
	Pending_Repromote("Pending_Repromote"),
	Pending_Retry("Pending_Retry"),
	In_Progress("In_Progress"),
	Success("Success"),
	Failed("Failed"),
	Repromoting("Repromoting"),
	FailedbyAdmin("Failed - by Admin cleanup process"),
	Retrying("Retrying"),
	PendingProgramDeploy("PendingProgramDeploy"),
	PendingProgramRepromote("PendingProgramRepromote"),
	PendingProgramRetry("PendingProgramRetry"),
	
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