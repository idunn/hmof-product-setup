package hmof
import geb.*


class RedPagesDriver  {


	static main(args) {

		def secureProgramData = ["Product Setup Ivan2 - Grades 6-9", "9241234567891", "9241234567892", "Language Arts", "2016"]

		RedPagesLogin rpl = new RedPagesLogin()
		rpl.init("http://support-review-cert.hrw.com")

		Browser.drive{
			println "Starting Geb Automation"

			to RedPagesLogin
			login "jforare@harcourt.com", "11surf"

			// Manage Existing ISBN
			lookupIsbn secureProgramData[1]
			UpdateSecureProgramForm(secureProgramData)

			println "Completed Geb Automation"

		}

	}

}
