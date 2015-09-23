package hmof.deploy

import hmof.security.*
/**
 * Environment
 * A domain class describes the data object and it's mapping to the database
 */
class Environment {

	String name
	int priorityOrder
	String url

	//Set<DeploymentEnvGroup> groups
	Role role
	static hasMany = [promotion: Promotion, users: User]	// tells GORM to associate other domain objects for a 1-n or n-m mapping
	static belongsTo =[groups: EnvironmentGrp]
	static mapping = {
	}

	static constraints = {
		name()
		priorityOrder()
		users()
		role()
		url(url: true)
		groups(blank: true, nullable: true)
	}

	public String toString() {
		return name
	}
}
