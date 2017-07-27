package hmof.geb


import geb.*
import org.apache.log4j.LogManager
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator
import grails.util.Holders

class CommerceObjectWork extends Page {
	def utilityService = Holders.grailsApplication.mainContext.getBean 'utilityService'

	def initBaseUrl(def baseUrl, Logger log){
		log.info "Base Url: " + baseUrl
		url = baseUrl
	}

	static url

	static content = {

		manageCommerceObjectLink {waitFor(30){ $("a", text: contains("Manage Existing Commerce Object"))}}
		addCommerceObjectLink {waitFor(30){$("a", text: contains("Add New Commerce Object"))}}
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
		category{$("select", name: contains("ModuleCategory"))}
		gradeLevel{$("select", name: "GradeLevel")}

		// Modules
		globalModule { module GebModule }

	}


	void lookupIsbn(def contentInstance, Logger log){
		log.debug "Looking up ISBN...."

		manageCommerceObjectLink.click()
		log.info "Looking up ISBN Number: ${contentInstance.isbnNumber}"
		isbnField.value contentInstance.isbnNumber

		searchButton.click()

		log.info "Checking if CommerceObject Exists..."

		def update = globalModule.updateLink
		if(update){
			waitFor(25) {globalModule.updateLink.click()}

			log.info "Updating Existing Commerce Object"
			addCommerceObjectData(contentInstance, log)
			globalModule.updateButton.click()

		} else{
			globalModule.homeButton.click()
			addCommerceObjectLink.click()

			log.info "Adding New Commerce Object"
			addCommerceObjectData(contentInstance, log)
			globalModule.addButton.click()

		}
	}

	/**
	 * Pass Data into RedPages to create CommerceObject
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def addCommerceObjectData(def content, Logger log){

		log.info "Adding Commerce Object Data..."

		String blank = ""
		log.info "Product Name: " + content.objectName
		objectName.value(content.objectName)

		globalModule.description.value(content.comments?:"Data entered using the Product Setup Web Application")

		log.debug "Path To Cover Image: "  + content.pathToCoverImage
		pathToCoverImage.value(content.pathToCoverImage?: blank)

		hubPageSelect.value(true)
		log.debug "ISBN Field value: "+content.isbnNumber
		isbnField.value(content.isbnNumber)

		secureProgramDependent.value(true)

		teacherLabel.value(content.teacherLabel?: blank)

		teacherUrl.value(content.teacherUrl?: blank)

		studentLabel.value(content.studentLabel?: blank)

		studentUrl.value(content.studentUrl?: blank)

		objectType.value(content.objectType)
		log.info("objectReorderNumber: "+content.objectReorderNumber)
		if(content.objectReorderNumber=="Hidden")
		 objectReorder.value("100")
		 else
		 objectReorder.value(content.objectReorderNumber)
		    
		

		subject.value(content.subject?:"None")

		def categoryNumber = utilityService.getCategory(content.category)
		if (categoryNumber == "Other") categoryNumber = "-1"
		log.debug "category Number: " + categoryNumber
		category.value(categoryNumber)
		log.debug "Grade Level: " + content.gradeLevel
		def grades = []

        if(content.gradeLevel.equals("K") || content.gradeLevel.equals("1") || content.gradeLevel.equals("2") ||content.gradeLevel.equals("3") || content.gradeLevel.equals("4") || content.gradeLevel.equals("5")){
		    grades = ["6"]
	    }else if (!content.gradeLevel.contains("-")){
			grades = content.gradeLevel
		} else if(content.gradeLevel.equals("6-8")){
			grades = ["6","7","8"]
		}else if(content.gradeLevel.equals("9-12")){
			grades = ["9","10","11", "12"]
		} else {
		grades = ["6","7","8", "9","10","11", "12"]
		}

		gradeLevel.value(grades)

		log.info"Completed adding Commerce Object Data"
	}
}