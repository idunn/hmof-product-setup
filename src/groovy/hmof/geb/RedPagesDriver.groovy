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

	/**
	 * Interact with the various Geb Classes
	 * @param url
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def driveBrowser(def url, def enversInstanceToDeploy, def mapOfChildren = null){
		log.debug "Deployment url: " + url
		log.debug "EnversInstanceToDeploy " + enversInstanceToDeploy + " " + enversInstanceToDeploy.contentTypeId

		// This url allows us to bypass the login
		String bypassLogin = url + "/hrw/ecom/admin_hub.jsp"
		try{

			Browser.drive{

				if(enversInstanceToDeploy.contentTypeId==4){

					log.info "Starting Geb Automation for CommerceObject"

					CommerceObjectWork cow = new CommerceObjectWork()
					cow.initBaseUrl(bypassLogin)
					to CommerceObjectWork
					lookupIsbn (enversInstanceToDeploy)

					log.info "Completed Geb Automation of CommerceObject"

				} else if (enversInstanceToDeploy.contentTypeId==3){

					log.info "Starting Geb Automation for SecureProgram"

					SecureProgramWork spw = new SecureProgramWork()
					spw.initBaseUrl(bypassLogin)
					to SecureProgramWork
					lookupIsbn (enversInstanceToDeploy)

				}else if (enversInstanceToDeploy.contentTypeId==2){

					log.info "Starting Geb Automation for Bundle"

					BundleGebWork bundle = new BundleGebWork()
					bundle.initBaseUrl(bypassLogin)

					to BundleGebWork

					lookupIsbn (enversInstanceToDeploy)
					addBundleData (mapOfChildren, enversInstanceToDeploy)

					log.info "Completed Geb Automation of Bundle"


				}else{ log.error "Content Type not supported!"

				}

			}.quit() // quit is important in a multi-threaded application
		}catch(Exception e){
			def cachedDriver = CachingDriverFactory.clearCacheAndQuitDriver()
			log.error"Exception in Geb: " + e
			throw e

		}finally{

			log.info"###################################################################"
			

		}
	}
}
