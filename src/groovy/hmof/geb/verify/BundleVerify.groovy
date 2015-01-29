package hmof.geb.verify

import org.apache.log4j.Logger
import geb.*
import geb.Page

/**
 * Class that Uses Geb to verify that the Bundles exist
 * @author Dunni
 *
 */
class BundleVerifyWork extends Page {

	def initBaseUrl(def baseUrl, Logger log){
		log.info "Base Url: " + baseUrl
		url = baseUrl
	}

	static url

	static content = {

		manageBundlesLink(wait:true) { $("a", text: contains("Manage Existing eProduct Bundles"))}
		lookupIsbnField{$("input", name: "BndlISBN")}
		lookupButton{$("form").find("input", name: "Lookup")}
		noBundleText(wait: 5, required:false){$("font", text: contains("No results found with provided search criteria"))}
		homeButton {$("input", value: "Home")}
	}


	void lookupIsbn(def bundleList, Logger log){

		bundleList.each{ bundleIsbn ->
			log.debug "Checking if ${bundleIsbn} exists on Red-Pages"
			manageBundlesLink.click()
			lookupIsbnField.value(bundleIsbn)
			lookupButton.click()

			if (noBundleText){
				log.error "Error - Bundle ISBN: ${bundleIsbn} has been deleted manually from Red-Pages"
			}
			else{
				log.info "Success - Bundle ISBN: ${bundleIsbn} exists on Red-Pages"
			}

			homeButton.click()
		}

		log.info "Smart-Deploy Bundle verification has been completed."
	}

}
