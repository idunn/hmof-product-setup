package hmof
import geb.*
import geb.driver.CachingDriverFactory
//import grails.transaction.Transactional

//@Transactional
class CommerceObjectService extends Page{

	def jobService
	static transactional = false

	static content = {

		manageSecureProgram(wait:true) { $("a", text: contains("Manage Existing Commerce Object"))}
		lookupIsbnField {$("input", name: "ISBN")}
		searchButton{$("form").find("input", name: "search")}

	}

	/**
	 * this is the geb driver
	 * @param baseUrl
	 * @param deployInstance
	 * @return
	 */
	def deployCommerceObject(def baseUrl, def deployInstance){
		
		log.info "In Service baseUrl" + baseUrl
		log.info "In Service deployInstance" + deployInstance

		def cachedDriver = CachingDriverFactory.clearCache()
		String url = baseUrl + "/hrw/ecom/admin_hub.jsp"

		log.info(url)

		Browser.drive {

			go url
			log.info "After Go URL ########################"
			lookupIsbn(deployInstance)


		}quit()
	}
	
	/**
	 * Look up ISBN
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def lookupIsbn(def enversInstanceToDeploy){
		
		println "test#####" + enversInstanceToDeploy
		$("a", text: contains("Manage Existing Commerce Object"))
		$("input", name: "ISBN").value enversInstanceToDeploy.isbn
		$("form").find("input", name: "search").click()
		
		//manageSecureProgram.click()
		
		println "test2#####"
		
		//lookupIsbnField.value enversInstanceToDeploy.isbn
		//searchButton.click()

		log.info "Testing if CommerceObject Exists..."

		def update = $("a", href: contains("Update"))
		if(update){
			waitFor(15) {update.click()}
			// Add object
			log.info "Updating CO"
			//addCommerceObjectData(enversInstanceToDeploy)
			//$("input", value: "Update").click()
		} else{
			$("a", text:"Home").click()
			waitFor(15) {$("a", text: contains("Add New Commerce Object")).click()}
			// Add object
			log.info "Adding CO"
			//addCommerceObjectData(enversInstanceToDeploy)
			//$("input", value: "Add").click()

		}
	}

}