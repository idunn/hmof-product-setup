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


	}
	
	
	void lookupIsbn(def bundleList, Logger log){
		
		
		log.info "About to Click"
		manageBundlesLink.click()
		
		log.info bundleList
		
		log.info "Verified!"
	}

}
