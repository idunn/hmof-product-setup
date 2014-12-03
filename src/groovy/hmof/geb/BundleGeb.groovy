package hmof.geb

import org.apache.log4j.Logger;

import geb.*
import groovy.util.logging.Log4j

@Log4j
class BundleGebWork extends Page {

	def initBaseUrl(def baseUrl,Logger log){
		log.info "Base Url: " + baseUrl
		url = baseUrl

	}

	static url

	static content = {

		manageBundlesLink(wait:true) { $("a", text: contains("Manage Existing eProduct Bundles"))}
		addNewBundleLink(wait:true) { $("a", text: contains("Add New eProduct Bundle"))}

		lookupIsbnField{$("input", name: "BndlISBN")}
		orderEntryType{$("select", name: "OrderEntryType")}
		bundleTitle {$("form").find("input", name: "BndlTitle")}

		lookupButton{$("form").find("input", name: "Lookup")}
		homeButton {$("input", value: "Home")}
		saveButton {$("form").find("input", name: "Save")}
		findButton(wait:33) {$("input", value: "Find").click()}

		// Relationship building
		addSecureProgram(wait:true) {$("form").find("input", value: "Add Secure Programs")}
		addTeacherIsbn(wait:true){$("form").find("input", name: "TeacherISBN")}

		// Platform Objects
		activityManager(wait:true, required:true) {$("input", type:"checkbox", value:"ACTIVITY_MGR")}
		classManager(wait:true, required:true) {$("input", type:"checkbox", value:"CLASS_MGR")}		
		includeDashboard (wait:30, required:true) {$("font", text: /Dashboard/).children()}
		includePlanner (wait:30, required:true) {$("font", text: /ePlanner/).children()}

		// Object used in some language arts
		studentEssay{$("input", type:"checkbox", value:"STUDENT_ESSAYS")}
		studentEssayUnits{$("input", name:"unitsSTUDENT_ESSAYS")}

		// Duration
		duration{$("select", name: "SubscrLen")}

		deleteRestricted(wait: 5, required:false){$("a", href: ~/^(?=.*\bDelete\b)(?=.*\bRestricted\b).*$/)}
		deleteUnrestricted(wait: 5, required:false){$("a", href: ~/^(?=.*\bDelete\b)(?=.*\bUnrestricted\b).*$/)}
		noBundleText(wait: 5, required:true){$("font", text: contains("No results found with provided search criteria"))}

		// Used in Asserts
		nonEmptyBundle(wait: 10, required:true){$("input", type:"checkbox", name: "BndlItemId")}
		secureProgramSelected (wait:30, required:true) {$("input", name: /TeacherISBN/).value()}

		// Modules
		globalModule { module GebModule }

	}

	void lookupIsbn(def enversInstanceToDeploy,Logger log){

		log.info "Looking up Bundle ISBN..."
		log.info "Bundle ISBN: " + enversInstanceToDeploy.isbn
		manageBundlesLink.click()
		lookupIsbnField.value(enversInstanceToDeploy.isbn)
		lookupButton.click()

		def bundleExist = deleteRestricted
		if(bundleExist){

			log.info "Restricted Bundle ISBN Exists."
			log.info "Deleting and Recreating Restricted Bundle..."

			withConfirm(true){deleteRestricted.click()}

		}

		def unrestrictedBundleExist = deleteUnrestricted
		if(unrestrictedBundleExist){
			log.info "UnRestricted Bundle ISBN Exists."
			log.info "Deleting and Recreating UnRestricted Bundle..."
			withConfirm(true){deleteUnrestricted.click()}

		}


		log.info"Creating New Bundle..."
		assert noBundleText

		homeButton.click()
		addNewBundleLink.click()
		lookupIsbnField.value(enversInstanceToDeploy.isbn)
		orderEntryType.value("All")
		bundleTitle.value(enversInstanceToDeploy.title)

		globalModule.addButton.click()

	}
	/**
	 * Add the SecurePrograms and CommerceObjects to the Bundle
	 * @param mapOfChildren
	 * @return
	 */
	def addBundleData(def mapOfChildren, def enversInstanceToDeploy,Logger log){

		
		mapOfChildren.each{
			log.info "${'*'.multiply(40)} Adding Bundle Data ${'*'.multiply(40)}\r\n"
			def secureProgramInstance = it.key

			addSecureProgram.click()
			
			assert title == "Add Entitlements"
			log.info "Adding Bundle Entitlements...\r\n"

			log.info"Adding Secure Program"
			log.info"Secure Program Registration Isbn: " + secureProgramInstance.registrationIsbn
			addTeacherIsbn.value(secureProgramInstance.registrationIsbn)
			
			findButton

			log.info"Testing that the Secure Program has been associated to the Bundle"			
			assert secureProgramSelected == secureProgramInstance.registrationIsbn
			log.info "Secure Program is correctly associated!"

			log.info"Adding Platform Commerce Objects..."
			
			log.info"Adding Activity Manager"
			activityManager.value(true)
			log.info"Adding Class Manager"
			classManager.value(true)
			
			if (secureProgramInstance.includeDashboardObject){
				log.info"Adding Dashboard"
				includeDashboard.value(true)
				//waitFor(50){$("font", text: /Dashboard/).children().value(true)}
				
			}
			
			if (secureProgramInstance.includeEplannerObject){
				log.info"Adding Planner"
				includePlanner.value(true)
				//waitFor(50){$("font", text: /ePlanner/).children().value(true)}
			}
			def commerceObjectMap = it.value
			commerceObjectMap.each{
				log.info"Adding Custom Commerce Objects..."
				def commerceObjectInstance = it
				String coName = commerceObjectInstance.objectName
				log.info"Adding Commerce Object ${coName}"
				log.info"Adding Commerce Object isbn:"+commerceObjectInstance.isbnNumber+"\r\n"
				waitFor(50) {$("font", text: /$coName/).children().value(true)}
			}


			def durationLength = getDuration(enversInstanceToDeploy.duration,log)
			duration.value(durationLength)


			log.info"Associations being added to the HMOF database"
			globalModule.addButton.click()

			log.info "Completed Adding Bundle Data"

		}
	}

	/**
	 * Simple check to confirm that Bundle is not empty
	 * @return
	 */
	def confirmBundle(Logger log){
		// make sure we are back in the bundle create page and that bundles have child content
		assert title == "Administration"
		log.info"In Bundle Create Page"
		
		assert nonEmptyBundle
		log.info "Bundle contains data!"

	}

	/**
	 * Helper method to return the String value of the Bundle duration
	 * Platform currently supports 1-Year or 6-Year
	 * @param durationLength
	 * @return
	 */
	def getDuration(def durationLength,Logger log){

		def x = durationLength
		switch (x) {
			case '1-Year':
				log.info"1-Year"
				durationLength = "365"
				break
			case '2-Year':
				log.info"2-Year"
				durationLength = "2190"
				break
			case '3-Year':
				log.info"3-Year"
				durationLength = "2190"
				break
			case '4-Year':
				log.info"4-Year"
				durationLength = "2190"
				break
			case '5-Year':
				log.info"5-Year"
				durationLength = "2190"
				break
			case '6-Year':
				log.info"6-Year"
				durationLength = "2190"
				break
			case '7-Year':
				log.info"7-Year"
				durationLength = "2190"
				break
			case '8-Year':
				log.info"8-Year"
				durationLength = "2190"
				break
			default:
				durationLength = "2190"
		}

		log.info "durationLength on Red Pages: ${durationLength.toInteger()/365} Year"

		durationLength

	}

}
