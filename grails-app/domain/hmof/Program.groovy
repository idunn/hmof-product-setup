package hmof

import org.hibernate.envers.Audited

@Audited
class Program {

	String name
	String discipline
	String state
	Date dateCreated
	Date lastUpdated
	String userUpdatingProgram
	
	ContentType contentType

	static hasMany = [bundles:Bundle]
	static searchable = {
		bundles component: true

	}

	static mapping = {

		sort id: "desc"
	}

	static constraints = {

		name (matches: /[A-Za-z0-9_]+/, nullable:false, unique:['state'], blank:false)		
		discipline(inList: ['language_arts', 'math', 'world_languages', 'social_studies', 'science', 'Other'], nullable:false, blank:false)
		state(nullable:false)
		contentType ()
		dateCreated ()
		lastUpdated ()
		bundles()
	}

	String toString(){
		"${name}_${state}"
	}
}