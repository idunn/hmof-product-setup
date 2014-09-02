package hmof.deploy

import hmof.security.*
import java.util.Date

/**
 * Promotion
 * A domain class describes the data object and it's mapping to the database
 */
class Promotion {

	Date	dateCreated
	Date	lastUpdated
	String  status
	Long 	jobNumber

	static belongsTo	= [job: Job,user: User,environments: Environment]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

	static mapping = {

		sort dateCreated: "desc" // or "asc"
	}

	static constraints = {
	}

	public String toString() {
		return "${id}";
	}

}
