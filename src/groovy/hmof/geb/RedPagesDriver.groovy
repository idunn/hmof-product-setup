package hmof.geb
import geb.*
import geb.driver.CachingDriverFactory
import groovy.util.logging.Log4j

@Log4j
class RedPagesDriver  {
	
	RedPagesDriver(def url, def enversInstanceToDeploy){

		def cachedDriver = CachingDriverFactory.clearCache()
		log.info "cachedDriver" + cachedDriver
		driveBrowser(url, enversInstanceToDeploy)
	}


	def driveBrowser(def url, def enversInstanceToDeploy){
		log.info "url: " + url
		log.info "EnversInstanceToDeploy " + enversInstanceToDeploy + " " + enversInstanceToDeploy.contentTypeId
		
		// This url allows us to bypass the login
		String skipLoginUrl = url + "/hrw/ecom/admin_hub.jsp"

		Browser.drive{

			if(enversInstanceToDeploy.contentTypeId==4){
				
				log.info "Starting Geb Automation for CommerceObject"

				HmofRedPagesLogin rpl = new HmofRedPagesLogin()
				rpl.initBaseUrl(skipLoginUrl)

				to HmofRedPagesLogin				
				//login "jforare@harcourt.com", "11surf"				
				skipLogin()				
				lookupIsbn (enversInstanceToDeploy)

				log.info "Completed Geb Automation of CommerceObject"

			} else{ log.error "Not a CommerceObject" }

		}.quit() // quit is important in a multi-threaded application

	}

}
