package hmof.deploy

/**
 * Environment
 * A domain class describes the data object and it's mapping to the database
 */
class Environment {
	
    String name
	int priorityOrder
	String url
	

	static hasMany = [promotion: Promotion]	// tells GORM to associate other domain objects for a 1-n or n-m mapping 
	
    static mapping = {
    }
    
	static constraints = {
		name()
		priorityOrder()
		url(url: true)
    }
	
	public String toString() {
		return name
	}
}
