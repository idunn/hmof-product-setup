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

		updateButton(wait: 2, required:false){$("input", value: "Update")}

		programNameField (wait: true) {$("input", name: "ProgramName")}
		teacherIsbnField {$("input", name: "TeacherEditionISBN")}
		onlineIsbnField {$("input", name: "StudentEditionISBN")}
		curriculumAreasField{$("select", name: "CurriculumAreas")}
		copyrightYearField {$("select", name: "CopyrightYear")}

		// Knewton KG
		knewtonEnabled{$("input", name: "Knewton_enabled")}
		knowledgeGraphId{$("input", name: "KnowledgeGraphId")}
		activeInterventionTime{$("input", name: "ActiveInterventionTime")}
		enrichmentTime{$("input", name: "EnrichmentTime")}
		interventionTime{$("input", name: "InterventionTime")}


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
		
		// Modules
		globalModule { module GebModule }

	}


	/**
	 * Check if SecureProgram Exists: if not update else add new SecureProgram
	 * @param enversInstanceToDeploy
	 */
	void lookupIsbn(def enversInstanceToDeploy){

		manageSecureProgram.click()
		lookupIsbnField.value(enversInstanceToDeploy.registrationIsbn)

		// Handle Alert box
		withAlert{searchButton.click()}

		def update = updateButton
		if(update){
			waitFor(15) {updateButton.click()}
			log.info "Secure Program already exists"
			log.info "Updating SecureProgram"
			addSecureProgramData(enversInstanceToDeploy)
			updateButton.click()

		}else{
			log.info "Secure Program does not exist"
			globalModule.homeButton.click()

			log.info "Adding new Secure Program"
			addSecureProgramLink.click()
			addSecureProgramData(enversInstanceToDeploy)

			globalModule.addButton.click()

		}
	}

	/**
	 * Full form data
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def addSecureProgramData(def enversInstanceToDeploy){

		String blank = ""

		log.info "Adding SecureProgram Data"
		programNameField.value(enversInstanceToDeploy.productName)
		teacherIsbnField.value(enversInstanceToDeploy.registrationIsbn)
		onlineIsbnField.value(enversInstanceToDeploy.onlineIsbn)

		// curriculumAreasField TODO

		copyrightYearField.value(enversInstanceToDeploy.copyright)

		comment.value(enversInstanceToDeploy.comments?: blank)

		onlineResourcesLabel.value(enversInstanceToDeploy.labelForOnlineResource?: blank)
		onlineResourcesUrl.value(enversInstanceToDeploy.pathToResource?: blank)
		bookCoverImage.value(enversInstanceToDeploy.pathToCoverImage?: blank)

		// essay grader prompts TODO

		log.info enversInstanceToDeploy.knewtonProduct
		knewtonEnabled.value(enversInstanceToDeploy.knewtonProduct)

		if(enversInstanceToDeploy.knewtonProduct){
			log.info "This is a Knewton Product..."

			if(url.contains("review-cert")){

				log.info "In Cert-Review..."
				knowledgeGraphId.value(enversInstanceToDeploy.knowledgeGraphIdDev)

			} else if (url.contains("support-review")){

				log.info "In Prod-Review..."
				knowledgeGraphId.value(enversInstanceToDeploy.knowledgeGraphIdQA)

			} else if (url.contains("support.hrw.com")){

				log.info "Production..."
				knowledgeGraphId.value(enversInstanceToDeploy.knowledgeGraphIdProd)
			}

			activeInterventionTime.value(enversInstanceToDeploy.knowledgeGraphWarmUpTimeLimit)
			enrichmentTime.value(enversInstanceToDeploy.knowledgeGraphEnrichmentTimeLimit)
			interventionTime.value(enversInstanceToDeploy.knowledgeGraphEnrichmentCbiTimeLimit)

		}

		addTeacherResLabel.value(enversInstanceToDeploy.labelForTeacherAdditionalResource?: blank)
		addTeacherResUrl.value(enversInstanceToDeploy.pathToTeacherAdditionalResource?: blank)

		addStudentResLabel.value(enversInstanceToDeploy.labelForStudentAdditionalResource?: blank)
		addStudentResUrl.value(enversInstanceToDeploy.pathToStudentAdditionalResource?: blank)


		word1Field.value(enversInstanceToDeploy.securityWord)
		word1LocationField.value(enversInstanceToDeploy.securityWordLocation)
		word1PageNumberField.value(enversInstanceToDeploy.securityWordPage)		

	}

}
