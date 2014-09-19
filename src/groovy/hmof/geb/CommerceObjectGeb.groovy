package hmof.geb
import geb.*
import groovy.util.logging.Log4j

@Log4j
class CommerceObjectWork extends Page {

	def initBaseUrl(def baseUrl){
		log.info "baseUrl" + baseUrl
		url = baseUrl

	}

	static url


	static content = {

		manageCommerceObject(wait:true) { $("a", text: contains("Manage Existing Commerce Object"))}
		isbnField {$("input", name: "ISBN")}
		searchButton{$("form").find("input", name: "search")}
		updateButton{$("input", value: "Update")}
		homeLink{$("a", text:"Home")}
		addLink{$("input", value: "Add")}

	}


	void lookupIsbn(def enversInstanceToDeploy){
		log.info "Looking up ISBN...."
		manageCommerceObject.click()
		isbnField.value enversInstanceToDeploy.isbn
		searchButton.click()

		log.info "Checking if CommerceObject Exists..."

		def update = $("a", href: contains("Update"))
		if(update){
			waitFor(15) {update.click()}

			log.info "Updating Commerce Object"
			addCommerceObjectData(enversInstanceToDeploy)
			updateButton.click()
		} else{
			homeLink.click()
			waitFor(15) {$("a", text: contains("Add New Commerce Object")).click()}

			log.info "Adding CommerceObject"
			addCommerceObjectData(enversInstanceToDeploy)
			addLink.click()

		}
	}

	/**
	 * Data to pass into RedPages
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def addCommerceObjectData(def enversInstanceToDeploy){

		String blank = ""

		log.info "Adding Commerce Object Form Envers Object..."

		waitFor(25) {$("input", name: "Name").value(enversInstanceToDeploy.objectName)}
		$("textarea", name: "Desc").value("Data entered using Product Setup Web Application")

		$("input", name: "DfltIcon").value(enversInstanceToDeploy.pathToCoverImage?: blank)

		$("input", type:"checkbox", name:"HubPage").value(true)

		//$("input", name: "ISBN").value(enversInstanceToDeploy.isbn)
		isbnField.value(enversInstanceToDeploy.isbn)

		$("input", type:"checkbox", name:"SProgDependent").value(true)

		$("input", name: "TeacherLabel").value(enversInstanceToDeploy.teacherLabel?: blank)
		$("input", name: "TeacherURL").value(enversInstanceToDeploy.teacherUrl?: blank)
		$("input", name: "StudentLabel").value(enversInstanceToDeploy.studentLabel?: blank)
		$("input", name: "StudentURL").value(enversInstanceToDeploy.studentUrl?: blank)

		$("select", name: "ObjectType").value(enversInstanceToDeploy.objectType)

		$("input", name: "rTypeOrder").value(enversInstanceToDeploy.objectReorderNumber)
		//$("select", name: "Subject").value("Reading") TODO set this from parent or leave none // TODO work on category and subject


		// Multiselect TODO allow user to select individual grades
		def grades = []
		if (!enversInstanceToDeploy.gradeLevel.contains("-")){
			grades = enversInstanceToDeploy.gradeLevel
		} else if(enversInstanceToDeploy.gradeLevel.equals("6-8")){
			grades = ["6","7","8"]
		}else if(enversInstanceToDeploy.gradeLevel.equals("9-12")){
			grades = ["9","10","11", "12"]
		} else {grades = ["6","7","8", "9","10","11", "12"]}

		$("select", name: "GradeLevel").value(grades)

	}

}








