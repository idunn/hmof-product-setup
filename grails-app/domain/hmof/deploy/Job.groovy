package hmof.deploy
import hmof.security.User
import hmof.ContentType

/**
 * Job
 * A domain class describes the data object and it's mapping to the database
 */
class Job {
	
	Date dateCreated	
	Date lastUpdated	
		
	Long jobNumber
	Long contentTypeId
	Long contentId
	Long revision
	
	
	// TODO this should be reviewed
	//ContentType contentType
	
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
    }
		
	public String toString() {
		return "${id}";
	}
	
}
