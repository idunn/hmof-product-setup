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

		//TODO
		manageBundlesLink(wait:true) { $("a", text: contains("Manage Existing eProduct Bundles"))}
		addNewBundleLink(wait:true) { $("a", text: contains("Add New eProduct Bundle"))}

		lookupIsbnField{$("input", name: "BndlISBN")}
		orderEntryType{$("select", name: "OrderEntryType")}
		bundleTitle {$("form").find("input", name: "BndlTitle")}

		lookupButton{$("form").find("input", name: "Lookup")}
		homeButton {$("input", value: "Home")}
		saveButton {$("form").find("input", name: "Save")}




		// Relationship building
		addSecureProgram(wait:true) {$("form").find("input", value: "Add Secure Programs")}
		addTeacherIsbn{$("form").find("input", name: "TeacherISBN")}

		// Platform Objects
		activityManager(wait:true) {$("input", type:"checkbox", value:"ACTIVITY_MGR")}
		classManager(wait:true) {$("input", type:"checkbox", value:"CLASS_MGR")}

		// object used in some language arts
		studentEssay{$("input", type:"checkbox", value:"STUDENT_ESSAYS")}
		studentEssayUnits{$("input", name:"unitsSTUDENT_ESSAYS")}

		//Duration
		duration{$("select", name: "SubscrLen")}

		// Modules
		globalModule { module GebModule }



	}

	void lookupIsbn(def enversInstanceToDeploy){

		log.info "Looking up Bundle ISBN..."

		manageBundlesLink.click()
		lookupIsbnField.value(enversInstanceToDeploy.isbn)
		lookupButton.click()

		// TODO
		def update = globalModule.updateLink
		if(update){

			log.info "Bundle ISBN Exists."

			waitFor(15) {globalModule.updateLink.click()}

			waitFor(15){globalModule.updateButtonName.click()}

			bundleTitle.value(enversInstanceToDeploy.title)

			saveButton.click()

		} else{

			log.info"Creating New Bundle..."

			homeButton.click()
			addNewBundleLink.click()
			lookupIsbnField.value(enversInstanceToDeploy.isbn)
			orderEntryType.value("All")
			bundleTitle.value(enversInstanceToDeploy.title)

			globalModule.addButton.click()

		}


	}
	/**
	 * Add the SecurePrograms and CommerceObjects to the Bundle
	 * @param mapOfChildren
	 * @return
	 */
	def addBundleData(def mapOfChildren){

		log.info "Adding Bundle Data..."

		addSecureProgram.click()

		mapOfChildren.each{
			def secureProgramInstance = it.key
			addTeacherIsbn.value(secureProgramInstance.registrationIsbn)			

			def commerceObjectMap = it.value
			commerceObjectMap.each{

				def commerceObjectInstance = it
				waitFor(100) {$("font", text: contains(commerceObjectInstance.objectName)).children().click()}				
			}			

		}
		
		// Add Platform Commerce Objects
		//activityManager.click()
		//classManager.click()

		duration.value("2190")

		globalModule.addButton.click()

	}

}
