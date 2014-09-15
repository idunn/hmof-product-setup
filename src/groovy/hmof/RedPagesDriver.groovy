package hmof
import geb.*


class RedPagesDriver  {


	RedPagesDriver(def url){

		init(url)
	}


	def init(def url){

		println "url: " + url

		RedPagesLogin rpl = new RedPagesLogin()
		rpl.init(url)

		try{

			Browser.drive{
				println "Starting Geb Automation"

				to RedPagesLogin
				login "jforare@harcourt.com", "11surf"

				// Manage Existing ISBN
				//lookupIsbn secureProgramData[1]
				//UpdateSecureProgramForm(secureProgramData)

				println "Completed Geb Automation"
			}


		}catch(Exception e){

			println "Exception " + e
		}

	}
	
	static main(args) {
		
		RedPagesDriver test = new RedPagesDriver("http://support-review.hrw.com")
	}
}
