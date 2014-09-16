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
		eComerceHome (to: CommerceObjectWork, wait:true){$("a", text: contains("Ecommerce"))}

	}


	void login(String username, String password){
		usernameField.value username
		passwordField.value password
		loginButton.click()
		eComerceHome.click()
	}

}

class CommerceObjectWork extends Page {


	static content = {

		manageSecureProgram(wait:true) { $("a", text: contains("Manage Existing Commerce Object"))}
		lookupIsbnField {$("input", name: "ISBN")}
		searchButton{$("form").find("input", name: "search")}

	}

	void lookupIsbn(def enversInstanceToDeploy){
		manageSecureProgram.click()
		lookupIsbnField.value enversInstanceToDeploy.isbn
		searchButton.click()

		println "Testing if CommerceObject Exists..."

		def update = $("a", href: contains("Update"))
		if(update){
			waitFor(15) {update.click()}
			// Add object
			println "Updating CO"
			addCommerceObjectData(enversInstanceToDeploy)
			$("input", value: "Update").click()
		} else{
			$("a", text:"Home").click()
			waitFor(15) {$("a", text: contains("Add New Commerce Object")).click()}
			// Add object
			println "Adding CO"
			addCommerceObjectData(enversInstanceToDeploy)
			$("input", value: "Add").click()

		}
	}


	def addCommerceObjectData(def enversInstanceToDeploy){

		String blank = ""

		println "Adding Commerce Object Form Data..."

		waitFor(25) {$("input", name: "Name").value(enversInstanceToDeploy.objectName)}
		$("textarea", name: "Desc").value("Data entered using Product Setup Web Application")

		$("input", name: "DfltIcon").value(enversInstanceToDeploy.pathToCoverImage?: blank)

		$("input", type:"checkbox", name:"HubPage").value(true)
		
		$("input", name: "ISBN").value(enversInstanceToDeploy.isbn)

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








