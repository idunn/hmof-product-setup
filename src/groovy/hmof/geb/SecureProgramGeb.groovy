package hmof.geb

import geb.*
import groovy.util.logging.Log4j

@Log4j
class SecureProgramWork extends Page {

	def initBaseUrl(def baseUrl){
		log.info "baseUrl" + baseUrl
		url = baseUrl

	}

	static url

	static content = {

		manageSecureProgram(wait:true) { $("a", text: contains("Manage Existing Secure Program"))}
		lookupIsbnField {$("input", name: "TeacherISBN")}
		searchButton{$("form").find("input", name: "search")}
		updateButton(wait: true) {$("form").find("input", value: "Update")}

		programNameField (wait: true) {$("input", name: "ProgramName")}
		teacherIsbnField {$("input", name: "TeacherEditionISBN")}
		onlineIsbnField {$("input", name: "StudentEditionISBN")}
		curriculumAreasField{$("select", name: "CurriculumAreas")}
		copyrightYearField {$("select", name: "CopyrightYear")}

		// Teacher challenge words for self registration
		word1Field {$("input", name: "Word1")}
		word1LocationField {$("input", name: "Location1")}
		word1PageNumberField {$("input", name: "PageNum1")}

		updateButton{$("input", value: "Update")}

	}

	void lookupIsbn(def enversInstanceToDeploy){

		log.info "Looking up SecureProgram ISBN..."
		manageSecureProgram.click()
		lookupIsbnField.value enversInstanceToDeploy.isbn
		//searchButton.click()

		addSecureProgramData(enversInstanceToDeploy)

	}

	def addSecureProgramData(def enversInstanceToDeploy){

		log.info "Adding SecureProgram Data..."

	}

}
