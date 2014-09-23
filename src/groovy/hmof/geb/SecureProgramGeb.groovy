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
		addSecureProgramLink(wait:true) {$("a", text: contains("Add New Secure Program"))}
		lookupIsbnField {$("input", name: "TeacherISBN")}
		searchButton{$("form").find("input", name: "search")}
		updateButton(wait: true) {$("form").find("input", value: "Update")}

		programNameField (wait: true) {$("input", name: "ProgramName")}
		teacherIsbnField {$("input", name: "TeacherEditionISBN")}
		onlineIsbnField {$("input", name: "StudentEditionISBN")}
		curriculumAreasField{$("select", name: "CurriculumAreas")}
		copyrightYearField {$("select", name: "CopyrightYear")}
		
		// Other fields		
		comment {$("textarea", name: "Comments")}
		onlineResourcesLabel{$("input", name: "OnlineResourcesLabel")}
		onlineResourcesUrl{$("input", name: "OnlineResourcesURL")}
		bookCoverImage {$("input", name: "BookCoverImage")}
		
		addTeacherResLabel{$("input", name: "AddTeacherResLabel")}
		addTeacherResUrl{$("input", name: "AddTeacherResURL")}
		addTeacherResLabel2{$("input", name: "AddTeacherResLabel2")}
		addTeacherResUrl2{$("input", name: "AddTeacherResURL2")}
		addTeacherResLabel3{$("input", name: "AddTeacherResLabel3")}
		addTeacherResUrl3{$("input", name: "AddTeacherResURL3")}
		addTeacherResLabel4{$("input", name: "AddTeacherResLabel4")}
		addTeacherResUrl4{$("input", name: "AddTeacherResURL4")}
		
		addStudentResLabel{$("input", name: "AddStudentResLabel")}
		addStudentResUrl{$("input", name: "AddStudentResURL")}
		addStudentResLabel2{$("input", name: "AddStudentResLabel2")}
		addStudentResUrl2{$("input", name: "AddStudentResURL2")}
		addStudentResLabel3{$("input", name: "AddStudentResLabel3")}
		addStudentResUrl3{$("input", name: "AddStudentResURL3")}
		addStudentResLabel4{$("input", name: "AddStudentResLabel4")}
		addStudentResUrl4{$("input", name: "AddStudentResURL4")}

		// Teacher challenge words for self registration
		word1Field {$("input", name: "Word1")}
		word1LocationField {$("input", name: "Location1")}
		word1PageNumberField {$("input", name: "PageNum1")}
		
		word2Field {$("input", name: "Word2")}
		word2LocationField {$("input", name: "Location2")}
		word2PageNumberField {$("input", name: "PageNum2")}
		
		word3Field {$("input", name: "Word3")}
		word3LocationField {$("input", name: "Location3")}
		word3PageNumberField {$("input", name: "PageNum3")}
		
		// TODO test
		secureProgramAlreadyExistsText(wait: 10, required:false){$("font", text: contains("A secure program with the specified teacher edition ISBN already exists."))}

		//Input value add
		AddButton{$("input", value: "Add")}
		updateButton{$("input", value: "Update")}
		homeButton{$("a", text: contains("Home"))}

	}

	/**
	 * Workaround: Need to add then update
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def basicAdd(def enversInstanceToDeploy){

		log.info "Adding Basic..."

		addSecureProgramLink.click()
		programNameField.value (enversInstanceToDeploy.productName)
		teacherIsbnField.value (enversInstanceToDeploy.registrationIsbn)
		word1Field.value (enversInstanceToDeploy.securityWord)
		word1LocationField.value (enversInstanceToDeploy.securityWordLocation)
		word1PageNumberField.value (enversInstanceToDeploy.securityWordPage)
		AddButton.click()
		if(secureProgramAlreadyExistsText){
			log.info "Secure Program already exists..."
			homeButton.click()
		} else{log.info "Secure Program does not exist!"}



	}

	void lookupIsbn(def enversInstanceToDeploy){

		basicAdd (enversInstanceToDeploy)

		log.info "Updating SecureProgram ISBN..."
		manageSecureProgram.click()
		lookupIsbnField.value enversInstanceToDeploy.registrationIsbn
		searchButton.click()
		updateButton.click()

		addSecureProgramData(enversInstanceToDeploy)

	}

	def addSecureProgramData(def enversInstanceToDeploy){
		
		String blank = ""

		log.info "Adding SecureProgram Data..."
		programNameField.value (enversInstanceToDeploy.productName)
		teacherIsbnField.value (enversInstanceToDeploy.registrationIsbn)
		onlineIsbnField.value (enversInstanceToDeploy.onlineIsbn)
		// curriculumAreasField
		copyrightYearField.value (enversInstanceToDeploy.copyright)
		
		onlineResourcesLabel.value(enversInstanceToDeploy.labelForOnlineResource)
		onlineResourcesUrl.value(enversInstanceToDeploy.pathToResource)
		bookCoverImage.value(enversInstanceToDeploy.pathToCoverImage)
		
		addTeacherResLabel.value(enversInstanceToDeploy.labelForTeacherAdditionalResource?: blank)
		addTeacherResUrl.value(enversInstanceToDeploy.pathToTeacherAdditionalResource?: blank)
		
		addStudentResLabel.value(enversInstanceToDeploy.labelForStudentAdditionalResource?: blank)
		addStudentResUrl.value(enversInstanceToDeploy.pathToStudentAdditionalResource?: blank)
		
		
		word1Field.value(enversInstanceToDeploy.securityWord)
		word1LocationField.value(enversInstanceToDeploy.securityWordLocation)
		word1PageNumberField.value(enversInstanceToDeploy.securityWordPage)		
		
		updateButton.click()

	}

}
