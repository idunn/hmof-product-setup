package hmof.deploy
import hmof.security.User
import hmof.ContentType

/**
 * Job
 * A domain class describes the data object and it's mapping to the database
 */
class Job {

	Long jobNumber
	Long contentTypeId
	Long contentId
	Long revision

	Date dateCreated
	Date lastUpdated
	
	Map children

	static belongsTo	= [user: User]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
	static hasMany		= [promotion: Promotion]

	static mapping = {

		sort id: "desc" // or "asc"
	}

	static constraints = {

		jobNumber()
		contentTypeId()
		contentId()
		revision()
		children(blank: false, nullable:true)
	}

	public String toString() {
		return "${id}";
	}

}
