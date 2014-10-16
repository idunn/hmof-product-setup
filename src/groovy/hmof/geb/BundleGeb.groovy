package hmof.geb

import geb.*
import groovy.util.logging.Log4j

@Log4j
class BundleGebWork extends Page {

	def initBaseUrl(def baseUrl){
		log.info "baseUrl" + baseUrl
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
		findButton(wait:true) {$("input", value: "Find")}


		// Relationship building
		addSecureProgram(wait:true) {$("form").find("input", value: "Add Secure Programs")}
		addTeacherIsbn(wait:true){$("form").find("input", name: "TeacherISBN")}

		// Platform Objects
		activityManager(wait:true) {$("input", type:"checkbox", value:"ACTIVITY_MGR")}
		classManager(wait:true) {$("input", type:"checkbox", value:"CLASS_MGR")}

		// object used in some language arts
		studentEssay{$("input", type:"checkbox", value:"STUDENT_ESSAYS")}
		studentEssayUnits{$("input", name:"unitsSTUDENT_ESSAYS")}

		//Duration
		duration{$("select", name: "SubscrLen")}

		deleteRestricted(required:false){$("a", href: ~/^(?=.*\bDelete\b)(?=.*\bRestricted\b).*$/)}
		deleteUnrestricted(required:false){$("a", href: ~/^(?=.*\bDelete\b)(?=.*\bUnrestricted\b).*$/)}
		noBundleText(wait: 5, required:true){$("font", text: contains("No results found with provided search criteria"))}

		// Modules
		globalModule { module GebModule }

	}

	void lookupIsbn(def enversInstanceToDeploy){

		log.info "Looking up Bundle ISBN..."

		manageBundlesLink.click()
		lookupIsbnField.value(enversInstanceToDeploy.isbn)
		lookupButton.click()

		def bundleExist = deleteRestricted
		if(bundleExist){

			log.info "Restricted Bundle ISBN Exists."
			log.info "Deleting and recreating Restricted Bundle..."

			withConfirm(true){deleteRestricted.click()}

		}

		def unrestrictedBundleExist = deleteUnrestricted
		if(unrestrictedBundleExist){
			log.info "UnRestricted Bundle ISBN Exists."
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
	def addBundleData(def mapOfChildren, def enversInstanceToDeploy){

		log.info "Adding Bundle Data..."

		mapOfChildren.each{

			// move click within the loop
			addSecureProgram.click()

			//TODO
			// Add Platform Commerce Objects
			activityManager.click()
			classManager.click()

			def secureProgramInstance = it.key
			addTeacherIsbn.value(secureProgramInstance.registrationIsbn)
			findButton.click()

			def commerceObjectMap = it.value
			commerceObjectMap.each{

				def commerceObjectInstance = it
				waitFor(200) {$("font", text: contains(commerceObjectInstance.objectName)).children().click()}
			}

			def durationLength = getDuration(enversInstanceToDeploy.duration)
			duration.value(durationLength)

			globalModule.addButton.click()

		}

	}

	/**
	 * Helper method to return the String value of the Bundle duration
	 * Platform currently supports 1-Year or 6-Year
	 * @param durationLength
	 * @return
	 */
	def getDuration(def durationLength){

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

		log.info" durationLength in Switch: " + durationLength

		durationLength


	}

}
