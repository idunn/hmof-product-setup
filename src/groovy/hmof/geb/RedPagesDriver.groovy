package hmof.geb
import geb.*
import geb.driver.CachingDriverFactory
import org.apache.log4j.Logger


class RedPagesDriver  {

	private static Logger log = Logger.getLogger(RedPagesDriver.class)


	RedPagesDriver(def url, def enversInstanceToDeploy){

		def cachedDriver = CachingDriverFactory.clearCache()
		log.info "cachedDriver" + cachedDriver
		init(url, enversInstanceToDeploy)
	}


	def init(def url, def enversInstanceToDeploy){
		log.info "url: " + url
		log.info "EnversInstanceToDeploy " + enversInstanceToDeploy + " " + enversInstanceToDeploy.contentTypeId

		Browser.drive{

			if(enversInstanceToDeploy.contentTypeId==4){

				println "In CommerceObject##########"
				log.info "Starting Geb Automation for CommerceObject"

				HmofRedPagesLogin rpl = new HmofRedPagesLogin()
				rpl.init(url)

				to HmofRedPagesLogin
				login "jforare@harcourt.com", "11surf"
				lookupIsbn (enversInstanceToDeploy)

				log.info "Completed Geb Automation of CommerceObject"

			} else{println "Not a CommerceObject"}

		}.quit() // quit is important in a multi-threaded application

	}

}
