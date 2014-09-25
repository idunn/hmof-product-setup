package hmof

import org.hibernate.envers.Audited

@Audited
class Program {
	//static searchable = true
	String name
	String discipline
	Date dateCreated
	Date lastUpdated
	
	ContentType contentType
	
	static hasMany = [bundles:Bundle]
	static searchable = {		
			 bundles component: true
				
	}
	static constraints = {

		name (matches: /[A-Za-z0-9]+/, nullable:false, unique:true, blank:false)
		discipline(inList: ['language_arts', 'math', 'world_languages', 'social_studies', 'Other'], nullable:false, blank:false)
		contentType ()
		dateCreated ()
		lastUpdated ()	
		bundles()
	}

	String toString(){
		"${name}"
	}
}