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


	RedPagesDriver(def url, def enversInstanceToDeploy, def mapOfChildren){

		def cachedDriver = CachingDriverFactory.clearCache()
		log.info "cachedDriver" + cachedDriver
		driveBrowser(url, enversInstanceToDeploy, mapOfChildren)
	}


	def driveBrowser(def url, def enversInstanceToDeploy, def mapOfChildren = null){
		log.info "Deployment url: " + url
		log.info "EnversInstanceToDeploy " + enversInstanceToDeploy + " " + enversInstanceToDeploy.contentTypeId

		// This url allows us to bypass the login
		String skipLoginUrl = url + "/hrw/ecom/admin_hub.jsp"

		Browser.drive{

			if(enversInstanceToDeploy.contentTypeId==4){

				log.info "Starting Geb Automation for CommerceObject"

				CommerceObjectWork cow = new CommerceObjectWork()
				cow.initBaseUrl(skipLoginUrl)
				to CommerceObjectWork
				lookupIsbn (enversInstanceToDeploy)

				log.info "Completed Geb Automation of CommerceObject"

			} else if (enversInstanceToDeploy.contentTypeId==3){

				log.info "Starting Geb Automation for SecureProgram"

				SecureProgramWork spw = new SecureProgramWork()
				spw.initBaseUrl(skipLoginUrl)
				to SecureProgramWork
				lookupIsbn (enversInstanceToDeploy)

			}else if (enversInstanceToDeploy.contentTypeId==2){

				log.info "Starting Geb Automation for Bundle"

				BundleGebWork bundle = new BundleGebWork()
				bundle.initBaseUrl(skipLoginUrl)

				to BundleGebWork

				lookupIsbn (enversInstanceToDeploy)
				addBundleData (mapOfChildren, enversInstanceToDeploy)

				log.info "Completed Geb Automation of Bundle"


			}else{ log.error "Content Type not supported!"

			}

		}.quit() // quit is important in a multi-threaded application

	}

}
