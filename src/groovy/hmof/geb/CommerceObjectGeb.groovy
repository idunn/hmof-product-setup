package hmof.geb
import geb.*
import groovy.util.logging.Log4j

@Log4j
class CommerceObjectWork extends Page {

	def initBaseUrl(def baseUrl){
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


	void lookupIsbn(def enversInstanceToDeploy){
		log.info "Looking up ISBN...."
		manageCommerceObjectLink.click()
		isbnField.value enversInstanceToDeploy.isbnNumber
		searchButton.click()

		log.info "Checking if CommerceObject Exists..."

		def update = globalModule.updateLink
		if(update){
			waitFor(25) {globalModule.updateLink.click()}

			log.info "Updating Existing Commerce Object"
			addCommerceObjectData(enversInstanceToDeploy)
			globalModule.updateButton.click()

		} else{
			globalModule.homeButton.click()
			addCommerceObjectLink.click()

			log.info "Adding New CommerceObject"
			addCommerceObjectData(enversInstanceToDeploy)
			globalModule.addButton.click()

		}
	}

	/**
	 * Pass Data into RedPages to create CommerceObject
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def addCommerceObjectData(def content){

		log.info "Adding Commerce Object Data..."

		String blank = ""

		objectName.value(content.objectName)

		globalModule.description.value(content.comments?:"Data entered using the Product Setup Web Application")

		pathToCoverImage.value(content.pathToCoverImage?: blank)

		hubPageSelect.value(true)

		isbnField.value(content.isbnNumber)

		secureProgramDependent.value(true)

		teacherLabel.value(content.teacherLabel?: blank)
		teacherUrl.value(content.teacherUrl?: blank)
		studentLabel.value(content.studentLabel?: blank)
		studentUrl.value(content.studentUrl?: blank)

		objectType.value(content.objectType)
		objectReorder.value(content.objectReorderNumber)

		subject.value(content.subject?:"None")

		def categoryNumber = getCategory(content.category)
		category.value(categoryNumber)

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
	def getCategory(def category){

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








