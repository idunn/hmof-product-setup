package hmof.geb

import geb.*
import org.apache.log4j.Logger

class SecureProgramWork extends Page {

	def initBaseUrl(def baseUrl, Logger log){
		log.info "Base Url: " + baseUrl
		url = baseUrl
	}

	static url

	static content = {

		manageSecureProgram {waitFor(30){ $("a", text: contains("Manage Existing Secure Program"))}}
		addSecureProgramLink {waitFor(30){$("a", text: contains("Add New Secure Program"))}}
		lookupIsbnField {$("input", name: "TeacherISBN")}
		searchButton{$("form").find("input", name: "search")}

		updateButton(wait: 2, required:false){$("input", value: "Update")}

		programNameField {waitFor(30) {$("input", name: "ProgramName")}}
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
	void lookupIsbn(def contentInstance, Logger log){

		log.debug "Looking up ISBN...."

		manageSecureProgram.click()
		lookupIsbnField.value(contentInstance.registrationIsbn)

		log.info "Looking up ISBN Number: ${contentInstance.registrationIsbn}"
		log.info "Checking if Secure Program Exists..."

		// Handle Alert box
		withAlert{searchButton.click()}

		def update = updateButton
		if(update){
			waitFor(15) {updateButton.click()}
			log.info "Updating Existing Secure Program"
			addSecureProgramData(contentInstance, log)
			updateButton.click()

		}else{
			globalModule.homeButton.click()

			log.info "Adding New Secure Program"
			addSecureProgramLink.click()
			addSecureProgramData(contentInstance, log)

			globalModule.addButton.click()

		}
	}

	/**
	 * Full form data
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def addSecureProgramData(def content, Logger log){

		String blank = ""

		log.info "Adding Secure Program Data..."

		log.info "Product Name: " + content.productName
		programNameField.value(content.productName)
		log.debug "Registration Isbn: " + content.registrationIsbn
		teacherIsbnField.value(content.registrationIsbn)
		log.debug "Online Isbn: " + content.onlineIsbn
		onlineIsbnField.value(content.onlineIsbn)

		curriculumAreasField.value(content.curriculumArea?: "Other")
		log.debug "Copyright: " + content.copyright
		copyrightYearField.value(content.copyright)

		comment.value(content.comments?: blank)

		onlineResourcesLabel.value(content.labelForOnlineResource?: blank)

		onlineResourcesUrl.value(content.pathToResource?: blank)

		bookCoverImage.value(content.pathToCoverImage?: blank)

		essayGraderPrompts.value(content.essayGraderPrompts?: "Not Required")

		log.debug "Knewton Product: " + content.knewtonProduct
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

		// Additional links
		addTeacherResLabel.value(content.labelForTeacherAdditionalResource?: blank)

		addTeacherResUrl.value(content.pathToTeacherAdditionalResource?: blank)

		addTeacherResLabel2.value(content.labelForTeacherAdditionalResource2?: blank)

		addTeacherResUrl2.value(content.pathToTeacherAdditionalResource2?: blank)

		addTeacherResLabel3.value(content.labelForTeacherAdditionalResource3?: blank)

		addTeacherResUrl3.value(content.pathToTeacherAdditionalResource3?: blank)

		addTeacherResLabel4.value(content.labelForTeacherAdditionalResource4?: blank)

		addTeacherResUrl4.value(content.pathToTeacherAdditionalResource4?: blank)

		addStudentResLabel.value(content.labelForStudentAdditionalResource?: blank)

		addStudentResUrl.value(content.pathToStudentAdditionalResource?: blank)

		addStudentResLabel2.value(content.labelForStudentAdditionalResource2?: blank)

		addStudentResUrl2.value(content.pathToStudentAdditionalResource2?: blank)

		addStudentResLabel3.value(content.labelForStudentAdditionalResource3?: blank)

		addStudentResUrl3.value(content.pathToStudentAdditionalResource3?: blank)

		addStudentResLabel4.value(content.labelForStudentAdditionalResource4?: blank)

		addStudentResUrl4.value(content.pathToStudentAdditionalResource4?: blank)

		log.debug "Security Word: " + content.securityWord
		// registration words
		word1Field.value(content.securityWord)
		log.debug "Security Word Location: " + content.securityWordLocation
		word1LocationField.value(content.securityWordLocation)
		log.debug "Security Word Page: " + content.securityWordPage
		word1PageNumberField.value(content.securityWordPage)

		word2Field.value(content.securityWord2?: blank)

		word2LocationField.value(content.securityWordLocation2?: blank)

		word2PageNumberField.value(content.securityWordPage2?: blank)

		word3Field.value(content.securityWord3?: blank)

		word3LocationField.value(content.securityWordLocation3?: blank)

		word3PageNumberField.value(content.securityWordPage3?: blank)

		log.info "Completed adding Secure Program Data"

	}

}
