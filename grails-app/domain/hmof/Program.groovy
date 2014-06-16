package hmof

class Program {

	String name
	String discipline
	Date dateCreated
	Date lastUpdated
	Date devEnvironment
	Date qaEnvironment
	Date prodEnvironment
  

	static hasMany = [bundles:Bundle]

	static constraints = {

		name (matches: /[A-Za-z0-9]+/, nullable:false, unique:true, blank:false)
		discipline(inList: ['language_arts', 'math', 'world_languages', 'social_studies', 'Other'], nullable:false, blank:false)
		dateCreated ()
		lastUpdated ()
		devEnvironment (nullable:true)
		qaEnvironment (nullable:true)
		prodEnvironment (nullable:true)
	}

	String toString(){
		"${name}"
	}
}