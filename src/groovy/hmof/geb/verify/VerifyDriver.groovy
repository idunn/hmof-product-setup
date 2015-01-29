package hmof.geb.verify

import geb.*
import geb.driver.CachingDriverFactory

import org.apache.log4j.Logger

class VerifyDriver {


	VerifyDriver(def url, def objectsToVerify, Logger log){

		def cachedDriver = CachingDriverFactory.clearCache()
		log.debug "cachedDriver :" + cachedDriver + "\r\n"		
		driveBrowser(url, objectsToVerify, log)
	}


    /**
     * Start an instance of PhantomJs and verify the Bundles are on Red-Pages
     * @param url
     * @param objectsToVerify
     * @param log
     * @return
     */
	def driveBrowser(def url, def objectsToVerify, Logger log){

		String directUrl = url + "/hrw/ecom/admin_hub.jsp"		

		try{
			Browser.drive{				

				BundleVerifyWork bvw = new BundleVerifyWork()
				bvw.initBaseUrl(directUrl, log)				
				to BundleVerifyWork
				lookupIsbn (objectsToVerify, log)

			}.quit() // quit is important in a multi-threaded application

		}catch(Exception e){

			def cachedDriver = CachingDriverFactory.clearCacheAndQuitDriver()
			log.error "Exception in Geb: " + e
			throw e
		}
	}
}
