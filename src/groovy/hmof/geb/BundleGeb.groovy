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
		lookupIsbnField{$("input", name: "BndlISBN")}
		lookupButton{$("form").find("input", name: "Lookup")}
		homeButton {$("input", value: "Home")}

		bundleTitle {$("form").find("input", name: "BndlTitle")}
		saveButton {$("form").find("input", name: "Save")}

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
		// def update = $("a", href: contains("Update"))
		if(update){
			waitFor(15) {globalModule.updateLink.click()}

			//Update Title
			//waitFor(15) {$("form").find("input", name: "Update").click()}
			waitFor(15){globalModule.updateButtonName.click()}

			//$("form").find("input", name: "BndlTitle").value("Adding New eBundle - Custom Dev - 2014")
			bundleTitle.value(enversInstanceToDeploy.title)

			//$("form").find("input", name: "Save").click()
			saveButton.click()

			addBundleData(enversInstanceToDeploy)
			//$("input", value: "Add").click()
			globalModule.addButton.click()

		} else{

			log.info"In else..."

			/*$("a", text:"Home").click()
			 waitFor(15) {$("a", text: contains("Add New eProduct Bundle")).click()}
			 // Add bundle data
			 $("input", name: "BndlISBN").value(OnlineIsbn)
			 $("select", name: "OrderEntryType").value("All")
			 $("input", name: "BndlTitle").value("Adding New eBundle - Custom Dev - 2014")
			 $("input", value: "Add").click()
			 addBundleData(enversInstanceToDeploy)
			 $("input", value: "Add").click()*/

		}


	}

	def addBundleData(def enversInstanceToDeploy){

		log.info "Add Bundle Data... #########123"

	}

}
