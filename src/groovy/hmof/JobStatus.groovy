package hmof

public enum JobStatus {
	Pending("Pending"),
	In_Progress("In_Progress"),
	Success("Success"),
	Failed("Failed"),
	Failure("Failure") // TODO remove

	String status

	JobStatus(String status) {
		this.status = status
	}

	static JobStatus byId(String id) {
		values().find { it.status == id }
	}
}