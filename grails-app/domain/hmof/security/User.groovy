package hmof.security

import hmof.deploy.*
class User {

	transient springSecurityService

	String username
	String password
	String email
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	static hasMany = [ jobs: Job, promotions: Promotion,environments: Environment, deploymentEnvGroups: EnvironmentGrp]

	static transients = ['springSecurityService']
	static belongsTo = [Environment]
	static constraints = {
		username blank: false, unique: true
		password blank: false
		email nullable: true, blank:false, unique: true, email: true
	}

	static mapping = {
		password column: '`password`'
	}
	def setEnvironmentsIds(ids) {
		
				// Removing old selected environments
				def oldEnvironmentsIds = getEnvironmentsIds()
				oldEnvironmentsIds.each() {
					removeFromEnvironments(Environment.get(it)).save()
				}
				//  This line is absolutely essential.  Removing environments will work without it but the email will show all environments anyway
				if(environments)
					environments.clear()
				// If 'ids' is a String, then the user have chosen
				// The ids list could be empty as we may want to prevent a user having access to any environment
				// only one environment
				if(ids && ids instanceof String) {
					addToEnvironments(Environment.get(ids)).save()
				} else if(ids){
					ids.each() {
						if(Environment.get(it))
							addToEnvironments(Environment.get(it)).save()
					}
				}
			}
			
			def getEnvironmentsIds() {
				this.environments.collect{it.id}
			}
	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
	String toString(){
		username
	}
}
