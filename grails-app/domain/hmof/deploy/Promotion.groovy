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
	String  jiraId
	String  bambooPlanNumber
	Date	dateCreated
	Date	lastUpdated
	

	static belongsTo	= [job: Job,environments: Environment]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
	static hasOne		= [user: User]
	static mapping = {

		sort dateCreated: "desc" // or "asc"
	}

	static constraints = {
		
		smartDeploy nullable:false
		jiraId nullable:true
		bambooPlanNumber(nullable:true,blank:true)
	}

	public String toString() {
		return "${id}";
	}

}
