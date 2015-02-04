package hmof.deploy

import hmof.security.*
import java.util.Date

/**
 * Promotion
 * A domain class describes the data object and it's mapping to the database
 */
class Promotion {

	String  status
	Long 	jobNumber
    Boolean smartDeploy
	Date	dateCreated
	Date	lastUpdated
	

	static belongsTo	= [job: Job,user: User,environments: Environment]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

	static mapping = {

		sort dateCreated: "desc" // or "asc"
	}

	static constraints = {
		
		smartDeploy nullable:false
	}

	public String toString() {
		return "${id}";
	}

}
