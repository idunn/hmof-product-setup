package hmof.geb
import org.apache.log4j.Logger;


import geb.*
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
//@Log4j
class CommerceObjectWork extends Page {
	static Logger log = Logger.getLogger(CommerceObjectWork.class);
	def initBaseUrl(def baseUrl,Logger log){
		log.info "Base Url: " + baseUrl
		url = baseUrl

	}

	static url


	static content = {

		manageCommerceObjectLink(wait:true) { $("a", text: contains("Manage Existing Commerce Object"))}
		addCommerceObjectLink(wait:true) {$("a", text: contains("Add New Commerce Object"))}
		isbnField {$("input", name: "ISBN")}
		searchButton{$("form").find("input", name: "search")}
		objectName{$("input", name: "Name")}
		pathToCoverImage{$("input", name: "DfltIcon")}
		hubPageSelect{$("input", type:"checkbox", name:"HubPage")}
		secureProgramDependent{$("input", type:"checkbox", name:"SProgDependent")}
		teacherLabel{$("input", name: "TeacherLabel")}
		teacherUrl{$("input", name: "TeacherURL")}
		studentLabel{$("input", name: "StudentLabel")}
		studentUrl{$("input", name: "StudentURL")}
		objectType{$("select", name: "ObjectType")}
		objectReorder{$("input", name: "rTypeOrder")}
		subject{$("select", name: "Subject")}
		category{$("select", name: contains("pmm"))}
		gradeLevel{$("select", name: "GradeLevel")}

		// Modules
		globalModule { module GebModule }

	}


	void lookupIsbn(def enversInstanceToDeploy,Logger log){
		log.info "Looking up ISBN...."
		manageCommerceObjectLink.click()
		log.info "ISBN Number : "+enversInstanceToDeploy.isbnNumber
		isbnField.value enversInstanceToDeploy.isbnNumber
		
		searchButton.click()

		log.info "Checking if CommerceObject Exists..."

		def update = globalModule.updateLink
		if(update){
			waitFor(25) {globalModule.updateLink.click()}

			log.info "Updating Existing Commerce Object"
			addCommerceObjectData(enversInstanceToDeploy,log)
			globalModule.updateButton.click()

		} else{
			globalModule.homeButton.click()
			addCommerceObjectLink.click()

			log.info "Adding New CommerceObject"
			addCommerceObjectData(enversInstanceToDeploy,log)
			globalModule.addButton.click()

		}
	}

	/**
	 * Pass Data into RedPages to create CommerceObject
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def addCommerceObjectData(def content,Logger log){

		log.info "Adding Commerce Object Data..."

		String blank = ""
		log.info "Object Name :"+content.objectName
		objectName.value(content.objectName)
		log.info "Comments:"+content.comments
		globalModule.description.value(content.comments?:"Data entered using the Product Setup Web Application")
		log.info "Path To Cover Image :"+content.pathToCoverImage
		pathToCoverImage.value(content.pathToCoverImage?: blank)
		log.info "Hub Page Selected : true"
		hubPageSelect.value(true)
		log.info "ISBN Field value :"+content.isbnNumber
		isbnField.value(content.isbnNumber)
	    log.info "Secure Program Dependent : true"
		secureProgramDependent.value(true)
		log.info "Teacher Label :"+content.teacherLabel
		teacherLabel.value(content.teacherLabel?: blank)
		log.info "Teacher URL :"+content.teacherUrl
		teacherUrl.value(content.teacherUrl?: blank)
		log.info "Student Label :"+content.studentLabel
		studentLabel.value(content.studentLabel?: blank)
		log.info "Student URL :"+content.studentUrl
		studentUrl.value(content.studentUrl?: blank)
		log.info "Object Type :"+content.objectType
		objectType.value(content.objectType)
		log.info "Object Reorder :"+content.objectReorderNumber
		objectReorder.value(content.objectReorderNumber)
		log.info "Subject :"+content.subject
		subject.value(content.subject?:"None")

		def categoryNumber = getCategory(content.category,log)
		category.value(categoryNumber)
		log.info "Grade Level :"+content.gradeLevel
		def grades = []
		if (!content.gradeLevel.contains("-")){
			grades = content.gradeLevel
		} else if(content.gradeLevel.equals("6-8")){
			grades = ["6","7","8"]
		}else if(content.gradeLevel.equals("9-12")){
			grades = ["9","10","11", "12"]
		} else {grades = ["6","7","8", "9","10","11", "12"]}

		gradeLevel.value(grades)

		log.info "Completed adding Commerce Object Data."

	}

	/**
	 * Helper method to return the String value of the Category	 
	 * @param category
	 * @return
	 */
	def getCategory(def category,Logger log){

		def cat = category
		switch (cat) {
			case 'Other':
				log.info "Other"
				category = '-1'
				break
			case 'Science & Health':
				log.info"Science & Health"
				category = '0'
				break
			case 'Social Studies':
				log.info"Social Studies"
				category = '1'
				break
			case 'Language Arts':
				log.info"Language Arts"
				category = '2'
				break
			case 'Mathematics':
				log.info"Mathematics"
				category = '3'
				break
			case 'World Languages':
				log.info"World Languages"
				category = '4'
				break
			default:
				category = '-1'
		}

		log.info" Category Number: " + category

		return category


	}

}








