package hmof.geb
import geb.*
import geb.driver.CachingDriverFactory

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

class RedPagesDriver  {

	RedPagesDriver(def url, def enversInstanceToDeploy,Logger log){

		def cachedDriver = CachingDriverFactory.clearCache()
		log.debug "******************************************************************************"

	log.info "cachedDriver :" + cachedDriver+"\r\n"
		driveBrowser(url, enversInstanceToDeploy,log)
	}

	RedPagesDriver(def url, def enversInstanceToDeploy, def mapOfChildren,Logger log){

		def cachedDriver = CachingDriverFactory.clearCache()
		log.debug "******************************************************************************"
		log.info "cachedDriver :" + cachedDriver+"\r\n"
		driveBrowser(url, enversInstanceToDeploy, mapOfChildren,log)
	}

	/**
	 * Interact with the various Geb Classes
	 * @param url
	 * @param enversInstanceToDeploy
	 * @return
	 */
	def driveBrowser(def url, def enversInstanceToDeploy, def mapOfChildren = null,Logger log){
		
		
		log.debug "Deployment url: " + url
		log.debug "EnversInstanceToDeploy " + enversInstanceToDeploy + " " + enversInstanceToDeploy.contentTypeId+"\r\n"

		// This url allows us to bypass the login
		String bypassLogin = url + "/hrw/ecom/admin_hub.jsp"
		try{

			Browser.drive{

				if(enversInstanceToDeploy.contentTypeId==4){

					log.info "Starting Geb Automation for CommerceObject\r\n"

					CommerceObjectWork cow = new CommerceObjectWork()
					cow.initBaseUrl(bypassLogin,log)
					to CommerceObjectWork
					lookupIsbn (enversInstanceToDeploy,log)

					log.info "Completed Geb Automation of CommerceObject\r\n"

				} else if (enversInstanceToDeploy.contentTypeId==3){

					log.info "Starting Geb Automation for SecureProgram\r\n"

					SecureProgramWork spw = new SecureProgramWork()
					spw.initBaseUrl(bypassLogin,log)
					to SecureProgramWork
					lookupIsbn (enversInstanceToDeploy,log)
					log.info "Completed Geb Automation of CommerceObject\r\n"
				}else if (enversInstanceToDeploy.contentTypeId==2){

					log.info "Starting Geb Automation for Bundle\r\n"

					BundleGebWork bundle = new BundleGebWork()
					bundle.initBaseUrl(bypassLogin,log)

					to BundleGebWork

					lookupIsbn (enversInstanceToDeploy,log)
					addBundleData (mapOfChildren, enversInstanceToDeploy,log)					
					
					log.info"asserting bundle contains content"
					confirmBundle(log)

					log.info "Completed Geb Automation of Bundle\r\n"


				}else{ log.error "Content Type not supported!"

				}

			}.quit() // quit is important in a multi-threaded application
		}catch(Exception e){
			def cachedDriver = CachingDriverFactory.clearCacheAndQuitDriver()
			log.error"Exception in Geb: " + e
			throw e

		}finally{

			log.info"###################################################################\r\n"
			

		}
	}
}
