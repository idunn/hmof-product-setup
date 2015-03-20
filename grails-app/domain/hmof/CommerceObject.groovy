package hmof

import org.hibernate.envers.Audited

@Audited
class CommerceObject implements Comparable{
	
	static searchable = true
	String objectName
	String isbnNumber


	Date dateCreated
	Date lastUpdated

	String pathToCoverImage = "/nsmedia/images/bc/"
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
	Boolean isPremium=false
	String comments = (new Date().format('dd/MM/yy'))
	String userUpdatingCO


	ContentType contentType

	static belongsTo = SecureProgram
	int compareTo(obj) {
		objectName.compareTo(obj.objectName)
	}
	static mapping = {

		sort id: "desc"
	}

	static constraints = {

		objectName (blank: false, unique: true,maxSize:100)
		isbnNumber (blank: false,matches:/([0-9]{10}|[0-9]{13})$/,unique: true,maxSize:100)

		dateCreated ()
		lastUpdated ()

		pathToCoverImage (blank: false, nullable: true, matches:/^\/[\w\/\.]+(png|jpg|gif$)|(\/dummy)/,maxSize:200)
		teacherLabel (blank: false, nullable:true,maxSize:50)
		teacherUrl (shared: "globalUrl",maxSize:200)
		studentLabel (blank: false, nullable:true,maxSize:50)
		studentUrl (shared: "globalUrl",maxSize:200)
		objectType (inList: ['Other','DLO', 'eBook','myWriteSmart Activities', 'FYI', 'IWB', 'Notebook', 'Professional Development', 'Resources', 'Tab', 'ePlanner'])		
		objectReorderNumber (range: 1..12)
		gradeLevel (inList: ['K','1','2','3','4','5','6' ,'7' , '8', '9', '10', '11', '12', '6-8', '9-12', '6-12' ] )
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
