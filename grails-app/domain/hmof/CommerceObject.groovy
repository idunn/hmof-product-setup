package hmof

import org.hibernate.envers.Audited

@Audited
class CommerceObject {
	static searchable = true
	String objectName
	String isbnNumber
	
	
	Date dateCreated
	Date lastUpdated	
	
	String pathToCoverImage
	String teacherLabel
	String teacherUrl
	String studentLabel
	String studentUrl
	String category
	String objectType
	Integer objectReorderNumber = 1
	String subject
	String gradeLevel
	Boolean tabNavTab
	Boolean isPremium
	String comments = ("Autogenerated: " + new Date().format('dd/MM/yy'))
	
		
	
	ContentType contentType
			
	static belongsTo = SecureProgram

	static constraints = {

		objectName (blank: false, unique: true)
		isbnNumber (blank: false,matches:/([0-9]{10}|[0-9]{13})$/,unique: true)
		
		dateCreated ()
		lastUpdated ()	
		
		pathToCoverImage (blank: false, nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		teacherLabel (blank: false, nullable:true)
		teacherUrl (blank: false, nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		studentLabel (blank: false, nullable:true)
		studentUrl (blank: false, nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		objectType (inList: ['Other','DLO', 'eBook','myWriteSmart Activities', 'FYI', 'IWB', 'Notebook', 'Professional Development', 'Tab'])
		objectReorderNumber ( validator: {return it > 0 &&  (String.valueOf(it).length()) < 3 }, blank: false, nullable:true)
		gradeLevel (inList: ['6' ,'7' , '8', '9', '10', '11', '12', '6-8', '9-12', '6-12' ] )
		comments (maxSize:200, nullable:true, widget: 'textarea')
		
		category (inList: ['Other','Science & Health','Social Studies','Language Arts','Mathematics','World Languages'])
		subject (inList: ['None','Reading','Math','Social Studies','Science','Art','Health','All'])
		tabNavTab(nullable:true)
		isPremium(nullable:true)
	}

	String toString(){
		"${objectName}"
	}
}
