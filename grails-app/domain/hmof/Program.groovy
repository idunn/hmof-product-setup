package hmof

class Program {

	String name
	String discipline

	static hasMany = [bundles:Bundle]

	static constraints = {

		name (matches: /[A-Za-z0-9]+/, nullable:true)
		discipline(inList: ['language_arts', 'math', 'world_languages', 'social_studies', 'Other'], nullable:true)
	}

	String toString(){
		"${name}"
	}
}