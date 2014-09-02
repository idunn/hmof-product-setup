package hmof

class Program {

	String name
	String discipline
	Date dateCreated
	Date lastUpdated
	static hasMany = [bundles:Bundle]

	static constraints = {

		name (matches: /[A-Za-z0-9]+/, nullable:false, unique:true, blank:false)
		discipline(inList: ['language_arts', 'math', 'world_languages', 'social_studies', 'Other'], nullable:false, blank:false)
		dateCreated ()
		lastUpdated ()		
	}

	String toString(){
		"${name}"
	}
}