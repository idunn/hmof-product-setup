package hmof
import geb.*

class RedPagesLogin extends Page {	
	

	def init(def baseUrl){
		url = baseUrl
		
	}

	static url
	static content = {		

		usernameField(wait:true){$("form").find("input", name: "Login")}
		passwordField {$("form").find("input", name: "Password")}
		loginButton {$("form").find("input", name: "login")}
		eComerceHome (to: SecureProgramUpdate, wait:true){$("a", text: contains("Ecommerce"))}

	}


	void login(String username, String password){
		usernameField.value username
		passwordField.value password
		loginButton.click()
		eComerceHome.click()		
	}

}

class SecureProgramUpdate extends Page {


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

	void lookupIsbn(String isbn){
		manageSecureProgram.click()
		lookupIsbnField.value isbn
		searchButton.click()
		updateButton.click()

	}
	
	/**
	 * Update Secure Program Form
	 * @param spList
	 */
	void UpdateSecureProgramForm(def spList){		
		
		log.info spList
		
		programNameField.value spList[0]
		teacherIsbnField.value spList[1]
		onlineIsbnField.value spList[2]
		curriculumAreasField.value spList[3]
		copyrightYearField.value spList[4]
		
		word1Field.value "Learning"
		word1LocationField.value ("T4")
		word1PageNumberField.value ("2")
		
		updateButton.click()
		
	}

}








