package hmof

import java.util.Date
import org.hibernate.envers.Audited

@Audited
class SecureProgram {
	//static searchable = true
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
	Integer knowledgeGraphWarmUpTimeLimit
	Integer knowledgeGraphEnrichmentTimeLimit
	Integer knowledgeGraphEnrichmentCbiTimeLimit
	
	
	String comments = ("Autogenerated: " + new Date().format('dd/MM/yy'))
	//TT-3979 changes starts
	String curriculumArea
	String essayGraderPrompts
	String labelForTeacherAdditionalResource2
	String pathToTeacherAdditionalResource2
	String labelForStudentAdditionalResource2
	String pathToStudentAdditionalResource2
	
	String labelForTeacherAdditionalResource3
	String pathToTeacherAdditionalResource3
	String labelForStudentAdditionalResource3
	String pathToStudentAdditionalResource3
	
	String labelForTeacherAdditionalResource4
	String pathToTeacherAdditionalResource4
	String labelForStudentAdditionalResource4
	String pathToStudentAdditionalResource4
	
	String securityWord2
	String securityWordLocation2
	String securityWordPage2
	
	String securityWord3
	String securityWordLocation3
	String securityWordPage3
	//TT-3979 changes ends
	
	ContentType contentType

	static belongsTo = Bundle
	static hasMany = [commerceObjects:CommerceObject]
	static searchable = { commerceObjects component: true }
	static constraints = {

		productName (blank:false,nullable:false)
           
		registrationIsbn (blank: false,nullable:false, unique: true)
		onlineIsbn (blank: false,nullable:false)

		dateCreated ()
		lastUpdated ()
		
		
		copyright (blank: false, nullable:true)
		labelForOnlineResource (blank: false, nullable:true)
		pathToResource (blank: false, nullable:true)
		pathToCoverImage (blank: false, nullable:true)

		labelForTeacherAdditionalResource (nullable:true,validator: { val, obj ->
			if(val!=null)
			{
				println(val.indexOf("http://") == -1)
				
				println(val.charAt(0) != "/")
				if(val.indexOf("http://") == -1 && val.charAt(0) != "/")
				{
					return 'hmof.SecureProgram.labelForTeacherAdditionalResource.matches.invalid'
				}
				
			}
			}
)
		pathToTeacherAdditionalResource (nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		labelForStudentAdditionalResource (nullable:true)
		pathToStudentAdditionalResource (nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)

		securityWord (blank: false)
		securityWordLocation (blank: false)
		securityWordPage (blank: false)
		includeDashboardObject (nullable:true)
		includeEplannerObject (nullable:true)

		knewtonProduct (nullable:true)
		knowledgeGraphIdDev (nullable:true,validator: { val, obj ->
			if(obj.knewtonProduct!=null && obj.knewtonProduct)
			{

				if(null==val){

					return 'hmof.SecureProgram.knowledgeGraphIdDev.matches.invalid'
				}else{

					return true
				}
			}else
			{
				return true
			}
		},blank:false,matches: /([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})$/)
		knowledgeGraphIdQA (nullable:true,validator: { val, obj ->

			if(obj.knewtonProduct!=null && obj.knewtonProduct)
			{
				
				if(null==val){
					
					return 'hmof.SecureProgram.knowledgeGraphIdQA.matches.invalid'
				}else{

					return true
				}
			}else
			{
				return true
			}
		},blank:false,matches: /([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})$/)
		knowledgeGraphIdProd (nullable:true,validator: { val, obj ->

			if(obj.knewtonProduct!=null && obj.knewtonProduct)
			{				
				if(null==val){					
					return 'hmof.SecureProgram.knowledgeGraphIdProd.matches.invalid'
				}else{
					return true
				}
			}else
			{
				return true
			}
		},blank:false,matches: /([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})$/)
		knowledgeGraphWarmUpTimeLimit (nullable:true,validator: { val, obj ->

			if(obj.knewtonProduct!=null && obj.knewtonProduct)
			{				
				if(null==val){					
					return 'SecureProgram.knowledgeGraphWarmUpTimeLimit.range'
				}else{
					return true
				}
			}else
			{
				return true
			}
		},blank:false,range:5..60)
		knowledgeGraphEnrichmentTimeLimit (nullable:true,validator: { val, obj ->

			if(obj.knewtonProduct!=null && obj.knewtonProduct)
			{			
				if(null==val){					
					return 'SecureProgram.knowledgeGraphEnrichmentTimeLimit.range'
				}else{

					return true
				}
			}else
			{
				return true
			}
		},blank:false,range:5..60)
		knowledgeGraphEnrichmentCbiTimeLimit (nullable:true,validator: { val, obj ->

			if(obj.knewtonProduct!=null && obj.knewtonProduct)
			{
				if(null==val){
					return 'SecureProgram.knowledgeGraphEnrichmentCbiTimeLimit.range'
				}else{
					return true
				}
			}else
			{
				return true
			}
		},blank:false,range:5..60)

		comments (maxSize:255, nullable:true, widget: 'textarea')
		
		//TT-3979 changes starts
		curriculumArea (inList: ["Other", "Language Arts", "Mathematics", "Science and Health", "Social Studies", "World Languages"], nullable:false)
		
		essayGraderPrompts(inList: ["Not Required", "Middle School", "High School"], nullable:false)
		
		labelForTeacherAdditionalResource2(nullable:true)
		pathToTeacherAdditionalResource2(nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		labelForStudentAdditionalResource2(nullable:true)
		pathToStudentAdditionalResource2(nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		
		labelForTeacherAdditionalResource3(nullable:true)
		pathToTeacherAdditionalResource3(nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		labelForStudentAdditionalResource3(nullable:true)
		pathToStudentAdditionalResource3(nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		
		labelForTeacherAdditionalResource4(nullable:true)
		pathToTeacherAdditionalResource4(nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		labelForStudentAdditionalResource4(nullable:true)
		pathToStudentAdditionalResource4(nullable:true,matches:/^(http:\/\/|https:\/\/|\/)[\w\.\/\s]*/)
		
		securityWord2(nullable:true)
		securityWordLocation2(nullable:true)
		securityWordPage2(nullable:true)
		
		securityWord3(nullable:true)
		securityWordLocation3(nullable:true)
		securityWordPage3(nullable:true)
		//TT-3979 changes ends
		
	}

	String toString(){
		"${productName}"
	}
}
