package hmof.geb.verify

import org.apache.log4j.Logger
import geb.*
import geb.Page
import hmof.*

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

	/**
	 * Return the list of Bundles that have been manually deleted
	 * @param bundleList
	 * @param log
	 * @return
	 */
	def lookupIsbn(def bundleList, Logger log){

		def bundlesToRedeploy = []

		bundleList.each{

			def bundleId = it.contentId
			// TODO what if we deleted locally
			def bundleInstance = Bundle.where{id==bundleId}.get()

			log.debug "Checking if ${bundleInstance.isbn} exists on Red-Pages"
			manageBundlesLink.click()
			lookupIsbnField.value(bundleInstance.isbn)
			lookupButton.click()

			if (noBundleText){
				log.error "Error - Bundle ISBN: ${bundleInstance.isbn} has been deleted manually from Red-Pages"
				bundlesToRedeploy << it
			}
			else{
				log.info "Success - Bundle ISBN: ${bundleInstance.isbn} exists on Red-Pages"
			}

			homeButton.click()
		}

		log.info "Smart-Deploy Bundle verification has been completed."

		return bundlesToRedeploy
	}

}
