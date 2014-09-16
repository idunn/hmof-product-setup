package hmof
import geb.*
import geb.driver.CachingDriverFactory


class RedPagesDriver  {


	RedPagesDriver(def url, def EnversInstanceToDeploy){
		
		def cachedDriver = CachingDriverFactory.clearCache()
		println "cachedDriver" + cachedDriver
		init(url, EnversInstanceToDeploy)
	}


	def init(def url, def EnversInstanceToDeploy){		
		println "url: " + url
		println "EnversInstanceToDeploy " + EnversInstanceToDeploy

		RedPagesLogin rpl = new RedPagesLogin()
		rpl.init(url)


		Browser.drive{
			println "Starting Geb Automation"

			to RedPagesLogin
			login "jforare@harcourt.com", "11surf"			
			lookupIsbn (EnversInstanceToDeploy)			

			println "Completed Geb Automation"
		}.quit() // quit is important in a multi-threaded application

	}

	static main(args) {

		RedPagesDriver test = new RedPagesDriver("http://support-review.hrw.com")
	}
}
