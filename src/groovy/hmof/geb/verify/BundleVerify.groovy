package hmof.geb.verify

import org.apache.log4j.Logger
import geb.*
import geb.Page
import hmof.*
import grails.util.Holders

/**
 * Class that Uses Geb to verify that the Bundles exist
 * @author Dunni
 *
 */
class BundleVerifyWork extends Page {

	def utilityService = Holders.grailsApplication.mainContext.getBean 'utilityService'

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

			Long bundleId = it.contentId
			Long revisionNumber = it.revision
			def bundleContent

			def bundleInstance = Bundle.where{id==bundleId}.get()?: utilityService.getDeletedObject(bundleId, revisionNumber, 2)

			if (bundleInstance instanceof hmof.Bundle){
				
				bundleContent = bundleInstance.findAtRevision(revisionNumber.toInteger())
			}
			else{				
				// Get the properties we are interested in
				bundleContent = new Bundle(isbn:bundleInstance.ISBN, title:bundleInstance.TITLE, duration:bundleInstance.DURATION, includePremiumCommerceObjects:bundleInstance.INCLUDE_PREMIUM_COMMERCE_OBJECTS, contentType:bundleInstance.CONTENT_TYPE_ID)
				log.warn"Retrieving deleted Bundle: ${bundleContent.isbn} from the Bundle Audit Table"
				}

			log.debug "Checking if ${bundleContent.isbn} exists on Red-Pages"
			manageBundlesLink.click()
			lookupIsbnField.value(bundleContent.isbn)
			lookupButton.click()

			if (noBundleText){
				log.error "Warning - Bundle ISBN: ${bundleContent.isbn} has been deleted manually from Red-Pages"
				bundlesToRedeploy << it
			}
			else{
				log.info "Success - Bundle ISBN: ${bundleContent.isbn} exists on Red-Pages"
			}

			homeButton.click()
		}

		log.info "Smart-Deploy Bundle verification has been completed."

		return bundlesToRedeploy
	}

}
