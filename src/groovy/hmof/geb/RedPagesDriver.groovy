package hmof.geb
import geb.*
import geb.driver.CachingDriverFactory
import org.apache.log4j.Logger


class RedPagesDriver  {
	
	private static Logger log = Logger.getLogger(RedPagesDriver.class)


	RedPagesDriver(def url, def EnversInstanceToDeploy){

		def cachedDriver = CachingDriverFactory.clearCache()
		log.info "cachedDriver" + cachedDriver
		init(url, EnversInstanceToDeploy)
	}


	def init(def url, def EnversInstanceToDeploy){
		log.info "url: " + url
		log.info "EnversInstanceToDeploy " + EnversInstanceToDeploy

		HmofRedPagesLogin rpl = new HmofRedPagesLogin()
		rpl.init(url)


		Browser.drive{
			log.info "Starting Geb Automation"

			to HmofRedPagesLogin
			login "jforare@harcourt.com", "11surf"
			lookupIsbn (EnversInstanceToDeploy)

			log.info "Completed Geb Automation"
		}.quit() // quit is important in a multi-threaded application

	}

}
