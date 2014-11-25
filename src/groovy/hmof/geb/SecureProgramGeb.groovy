package hmof.geb

import geb.*
import groovy.util.logging.Log4j
import org.apache.log4j.Logger;

class SecureProgramWork extends Page {

	def initBaseUrl(def baseUrl,Logger log){
		log.info "Base Url: " + baseUrl
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
		essayGraderPrompts {$("select", name: "EssayGraderPrompts")}

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
	void lookupIsbn(def enversInstanceToDeploy,Logger log){

		log.info "Looking up ISBN...."

		manageSecureProgram.click()
		lookupIsbnField.value(enversInstanceToDeploy.registrationIsbn)

		log.info "ISBN Number: " + enversInstanceToDeploy.registrationIsbn
		log.info "Checking if SecureProgram Exists..."

		// Handle Alert box
		withAlert{searchButton.click()}

		def update = updateButton
		if(update){
			waitFor(15) {updateButton.click()}
			log.info "Secure Program already exists"
			log.info "Updating SecureProgram"
			addSecureProgramData(enversInstanceToDeploy,log)
			updateButton.click()

		}else{
			log.info "Secure Program does not exist"
			globalModule.homeButton.click()

			log.info "Adding new Secure Program"
			addSecureProgramLink.click()
			addSecureProgramData(enversInstanceToDeploy,log)

			globalModule.addButton.click()

		}
	}

	/**
	 * Full form data
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def addSecureProgramData(def content,Logger log){

		String blank = ""

		log.info "Adding SecureProgram Data"

		log.info "Product Name: "+content.productName
		programNameField.value(content.productName)
		log.info "Registration Isbn: "+content.registrationIsbn
		teacherIsbnField.value(content.registrationIsbn)
		log.info "Online Isbn: "+content.onlineIsbn
		onlineIsbnField.value(content.onlineIsbn)
		log.info "Curriculum Area: "+content.curriculumArea
		curriculumAreasField.value(content.curriculumArea?: "Other")
		log.info "Copyright: "+content.copyright
		copyrightYearField.value(content.copyright)
		log.info "Comments: "+content.comments
		comment.value(content.comments?: blank)
		log.info "Label For Online Resource: "+content.labelForOnlineResource
		onlineResourcesLabel.value(content.labelForOnlineResource?: blank)
		log.info "Path To Resource: "+content.pathToResource
		onlineResourcesUrl.value(content.pathToResource?: blank)
		log.info "Path To Cover Image: "+content.pathToCoverImage
		bookCoverImage.value(content.pathToCoverImage?: blank)
		log.info "Essay Grader Prompts: "+content.essayGraderPrompts
		essayGraderPrompts.value(content.essayGraderPrompts?: "Not Required")

		log.info "Is Knewton Product: "+content.knewtonProduct
		knewtonEnabled.value(content.knewtonProduct)

		if(content.knewtonProduct){
			log.info "This is a Knewton Product..."

			if(url.contains("review-cert")){

				log.info "KG deployment to Cert-Review..."
				log.info "Knowledge Graph Id in DEV: "+content.knowledgeGraphIdDev
				knowledgeGraphId.value(content.knowledgeGraphIdDev)

			} else if (url.contains("support-review")){

				log.info "KG deployment to Prod-Review..."
				log.info "Knowledge Graph Id in QA: "+content.knowledgeGraphIdQA
				knowledgeGraphId.value(content.knowledgeGraphIdQA)

			} else if (url.contains("support.hrw.com")){

				log.info "KG deployment to Production..."
				log.info "Knowledge Graph Id in PROD: "+content.knowledgeGraphIdProd
				knowledgeGraphId.value(content.knowledgeGraphIdProd)
			}
			log.info "Knowledge Graph WarmUp Time Limit: "+content.knowledgeGraphWarmUpTimeLimit
			activeInterventionTime.value(content.knowledgeGraphWarmUpTimeLimit)
			log.info "Knowledge Graph Enrichment Time Limit: "+content.knowledgeGraphEnrichmentTimeLimit
			enrichmentTime.value(content.knowledgeGraphEnrichmentTimeLimit)
			log.info "Knowledge Graph EnrichmentCbi Time Limit: "+content.knowledgeGraphEnrichmentCbiTimeLimit
			interventionTime.value(content.knowledgeGraphEnrichmentCbiTimeLimit)

		}
		log.info "Label For Teacher Additional Resource: "+content.labelForTeacherAdditionalResource
		// Additional links
		addTeacherResLabel.value(content.labelForTeacherAdditionalResource?: blank)
		log.info "Path To Teacher Additional Resource: "+content.pathToTeacherAdditionalResource
		addTeacherResUrl.value(content.pathToTeacherAdditionalResource?: blank)

		log.info "Label For Teacher Additional Resource2: "+content.labelForTeacherAdditionalResource2
		addTeacherResLabel2.value(content.labelForTeacherAdditionalResource2?: blank)
		log.info "Path To Teacher Additional Resource2: "+content.pathToTeacherAdditionalResource2
		addTeacherResUrl2.value(content.pathToTeacherAdditionalResource2?: blank)

		log.info "Label For Teacher Additional Resource3: "+content.labelForTeacherAdditionalResource3
		addTeacherResLabel3.value(content.labelForTeacherAdditionalResource3?: blank)
		log.info "Path To Teacher Additional Resource3: "+content.pathToTeacherAdditionalResource3
		addTeacherResUrl3.value(content.pathToTeacherAdditionalResource3?: blank)

		log.info "Label For Teacher Additional Resource4: "+content.labelForTeacherAdditionalResource4
		addTeacherResLabel4.value(content.labelForTeacherAdditionalResource4?: blank)
		log.info "Path To Teacher Additional Resource4: "+content.pathToTeacherAdditionalResource4
		addTeacherResUrl4.value(content.pathToTeacherAdditionalResource4?: blank)

		log.info "Label For Student Additional Resource: "+content.labelForStudentAdditionalResource
		addStudentResLabel.value(content.labelForStudentAdditionalResource?: blank)
		log.info "Path To Student Additional Resource: "+content.pathToStudentAdditionalResource
		addStudentResUrl.value(content.pathToStudentAdditionalResource?: blank)

		log.info "Label For Student Additional Resource2: "+content.labelForStudentAdditionalResource2
		addStudentResLabel2.value(content.labelForStudentAdditionalResource2?: blank)
		log.info "Path To Student Additional Resource2: "+content.pathToStudentAdditionalResource2
		addStudentResUrl2.value(content.pathToStudentAdditionalResource2?: blank)

		log.info "Label For Student Additional Resource3: "+content.labelForStudentAdditionalResource3
		addStudentResLabel3.value(content.labelForStudentAdditionalResource3?: blank)
		log.info "Path To Student Additional Resource3: "+content.pathToStudentAdditionalResource3
		addStudentResUrl3.value(content.pathToStudentAdditionalResource3?: blank)

		log.info "Label For Student Additional Resource4: "+content.labelForStudentAdditionalResource4
		addStudentResLabel4.value(content.labelForStudentAdditionalResource4?: blank)
		log.info "Path To Student Additional Resource4: "+content.pathToStudentAdditionalResource4
		addStudentResUrl4.value(content.pathToStudentAdditionalResource4?: blank)

		log.info "Security Word: "+content.securityWord
		// registration words
		word1Field.value(content.securityWord)
		log.info "Security Word Location: "+content.securityWordLocation
		word1LocationField.value(content.securityWordLocation)
		log.info "Security Word Page: "+content.securityWordPage
		word1PageNumberField.value(content.securityWordPage)


		log.info "Security Word2: "+content.securityWord2
		word2Field.value(content.securityWord2?: blank)
		log.info "Security Word Location2: "+content.securityWordLocation2
		word2LocationField.value(content.securityWordLocation2?: blank)
		log.info "Security Word Page3: "+content.securityWordPage2
		word2PageNumberField.value(content.securityWordPage2?: blank)



		log.info "Security Word3: "+content.securityWord3
		word3Field.value(content.securityWord3?: blank)
		log.info "Security Word Location3: "+content.securityWordLocation3
		word3LocationField.value(content.securityWordLocation3?: blank)
		log.info "Security Word Page3: "+content.securityWordPage3
		word3PageNumberField.value(content.securityWordPage3?: blank)
		
		log.info"Completed adding Secure Program Data"

	}

}
