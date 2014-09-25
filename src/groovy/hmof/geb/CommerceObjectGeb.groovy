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
		updateLink(required:false){$("a", href: contains("Update"))}			
		
		objectName(wait:true){$("input", name: "Name")}
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
		gradeLevel{$("select", name: "GradeLevel")}

		// Modules
		globalModule { module GebModule }

	}


	void lookupIsbn(def enversInstanceToDeploy){
		log.info "Looking up ISBN...."
		manageCommerceObjectLink.click()
		isbnField.value enversInstanceToDeploy.isbn
		searchButton.click()

		log.info "Checking if CommerceObject Exists..."
		
		def update = updateLink
		if(update){
			waitFor(25) {update.click()}
			
			log.info "Updating Commerce Object"
			addCommerceObjectData(enversInstanceToDeploy)			
			globalModule.updateButton.click()
			
		} else{			
			globalModule.homeButton.click()
			addCommerceObjectLink.click()

			log.info "Adding CommerceObject"
			addCommerceObjectData(enversInstanceToDeploy)			
			globalModule.addButton.click()

		}
	}

	/**
	 * Data to pass into RedPages to add CommerceObject information
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def addCommerceObjectData(def content){

		log.info "Adding Commerce Object Data From Envers Object..."

		String blank = ""

		objectName.value(content.objectName)

		globalModule.description

		pathToCoverImage.value(content.pathToCoverImage?: blank)

		hubPageSelect.value(true)

		isbnField.value(content.isbn)

		secureProgramDependent.value(true)

		teacherLabel.value(content.teacherLabel?: blank)
		teacherUrl.value(content.teacherUrl?: blank)
		studentLabel.value(content.studentLabel?: blank)
		studentUrl.value(content.studentUrl?: blank)

		objectType.value(content.objectType)
		objectReorder.value(content.objectReorderNumber)
		
		// TODO set this from parent object in view
		//subject.value("Reading") 

		def grades = []
		if (!content.gradeLevel.contains("-")){
			grades = content.gradeLevel
		} else if(content.gradeLevel.equals("6-8")){
			grades = ["6","7","8"]
		}else if(content.gradeLevel.equals("9-12")){
			grades = ["9","10","11", "12"]
		} else {grades = ["6","7","8", "9","10","11", "12"]}

		gradeLevel.value(grades)

	}

}








