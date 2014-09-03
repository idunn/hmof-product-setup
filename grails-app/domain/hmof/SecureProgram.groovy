package hmof

import java.util.Date
import org.hibernate.envers.Audited

@Audited
class SecureProgram {

	String productName
	String registrationIsbn
	String onlineIsbn
	String copyright
	String labelForOnlineResource
	String pathToResource
	String pathToCoverImage

	Date dateCreated
	Date lastUpdated

	String labelForTeacherAdditionalResource
	String pathToTeacherAdditionalResource
	String labelForStudentAdditionalResource
	String pathToStudentAdditionalResource

	String securityWord = "changeme"
	String securityWordLocation = "1"
	String securityWordPage = "5"

	Boolean includeDashboardObject
	Boolean includeEplannerObject

	Boolean knewtonProduct
	String knowledgeGraphIdDev
	String knowledgeGraphIdQA
	String knowledgeGraphIdProd
	String knowledgeGraphWarmUpTimeLimit
	String knowledgeGraphEnrichmentTimeLimit
	String knowledgeGraphEnrichmentCbiTimeLimit

	String comments = ("Autogenerated: " + new Date().format('dd/MM/yy'))

	static belongsTo = Bundle
	static hasMany = [commerceObjects:CommerceObject]

	static constraints = {

		productName (blank: false)
		registrationIsbn (blank: false, unique: true)
		onlineIsbn (blank: false)

		dateCreated ()
		lastUpdated ()

		copyright (blank: false, nullable:true)
		labelForOnlineResource (blank: false, nullable:true)
		pathToResource (blank: false, nullable:true)
		pathToCoverImage (blank: false, nullable:true)

		labelForTeacherAdditionalResource (nullable:true)
		pathToTeacherAdditionalResource (nullable:true)
		labelForStudentAdditionalResource (nullable:true)
		pathToStudentAdditionalResource (nullable:true)

		securityWord (blank: false)
		securityWordLocation (blank: false)
		securityWordPage (blank: false)
		includeDashboardObject (nullable:true)
		includeEplannerObject (nullable:true)

		knewtonProduct (nullable:true)
		knowledgeGraphIdDev (nullable:true)
		knowledgeGraphIdQA (nullable:true)
		knowledgeGraphIdProd (nullable:true)
		knowledgeGraphWarmUpTimeLimit (nullable:true)
		knowledgeGraphEnrichmentTimeLimit (nullable:true)
		knowledgeGraphEnrichmentCbiTimeLimit (nullable:true)

		comments (maxSize:255, nullable:true, widget: 'textarea')
	}

	String toString(){
		"${productName}"
	}
}
