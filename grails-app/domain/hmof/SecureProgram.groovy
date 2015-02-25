package hmof

import java.util.Date
import java.util.SortedSet

import org.hibernate.envers.Audited

@Audited
class SecureProgram implements Comparable {

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

	String securityWord
	Integer securityWordLocation = 1
	String securityWordPage = "5"

	Boolean includeDashboardObject = true
	Boolean includeEplannerObject = true
	Boolean includeNotebookObject= false
	Boolean knewtonProduct
	String knowledgeGraphIdDev
	String knowledgeGraphIdQA
	String knowledgeGraphIdProd
	Integer knowledgeGraphWarmUpTimeLimit
	Integer knowledgeGraphEnrichmentTimeLimit
	Integer knowledgeGraphEnrichmentCbiTimeLimit


	String comments = (new Date().format('dd/MM/yy'))	
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
	Integer securityWordLocation2
	String securityWordPage2

	String securityWord3
	Integer securityWordLocation3
	String securityWordPage3	
	String userUpdatingSProgram
	ContentType contentType

	static belongsTo = Bundle
	int compareTo(obj) {
		productName.compareTo(obj.productName)
	}
	SortedSet commerceObjects
	static hasMany = [commerceObjects:CommerceObject]
	static searchable = { commerceObjects component: true }

	static mapping = {

		sort id: "desc"
		
	}

	static constraints = {

		productName (blank:false,nullable:false,maxSize:125)

		registrationIsbn (blank: false,nullable:false,matches:/(97(8|9))[0-9]{10}|\d{9}(\d|X)$/, unique: true)
		onlineIsbn (blank: false,nullable:false)

		dateCreated ()
		lastUpdated ()


		copyright (blank: false, nullable:true)
		labelForOnlineResource (blank: false, nullable:true,maxSize:100)		
		pathToResource (shared: "globalUrl",maxSize:200)				
		pathToCoverImage (blank: false, nullable: true, matches:/^\/[\w\/\.]+(png|jpg|gif$)|(\/dummy)/,maxSize:200)
		

		labelForTeacherAdditionalResource (nullable:true,maxSize:100)
		pathToTeacherAdditionalResource (shared: "globalUrl",maxSize:200)
		labelForStudentAdditionalResource (nullable:true,maxSize:100)
		pathToStudentAdditionalResource (shared: "globalUrl",maxSize:200)

		securityWord (blank: false, nullable:false,maxSize:25)
		securityWordLocation (blank: false,range:1..10)
		securityWordPage (blank: false,maxSize:20)
		includeDashboardObject (nullable:true)
		includeEplannerObject (nullable:true)
		includeNotebookObject (nullable:true)
		
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

		labelForTeacherAdditionalResource2(nullable:true,maxSize:100)
		pathToTeacherAdditionalResource2 (shared: "globalUrl",maxSize:200)
		labelForStudentAdditionalResource2(nullable:true,maxSize:100)
		pathToStudentAdditionalResource2 (shared: "globalUrl",maxSize:200)

		labelForTeacherAdditionalResource3(nullable:true,maxSize:100)
		pathToTeacherAdditionalResource3(shared: "globalUrl",maxSize:200)
		labelForStudentAdditionalResource3(nullable:true,maxSize:100)
		pathToStudentAdditionalResource3(shared: "globalUrl",maxSize:200)

		labelForTeacherAdditionalResource4(nullable:true,maxSize:100)
		pathToTeacherAdditionalResource4(shared: "globalUrl",maxSize:200)
		labelForStudentAdditionalResource4(nullable:true,maxSize:100)
		pathToStudentAdditionalResource4(shared: "globalUrl",maxSize:200)

		securityWord2(nullable:true,maxSize:25)
		securityWordLocation2(nullable:true,range:1..10)
		securityWordPage2(nullable:true,maxSize:20)

		securityWord3(nullable:true,maxSize:25)
		securityWordLocation3(nullable:true,range:1..10)
		securityWordPage3(nullable:true,maxSize:20)
		//TT-3979 changes ends

	}

	String toString(){
		"${registrationIsbn} : ${productName} : (${copyright})"
	}
}
