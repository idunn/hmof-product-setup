package hmof
import geb.*
//import grails.transaction.Transactional

//@Transactional
class CommerceObjectService{
	
	def jobService	
	static transactional = false	
	

	def loginToRedPages(def baseUrl){
		
		println baseUrl	
		//println commerceObjectInstance	

		Browser.drive{

			println "Starting Geb Automation"

			go baseUrl
			waitFor(60){$("form").find("input", name: "Login").value("jforare@harcourt.com")}
			$("form").find("input", name: "Password").value("11surf")			

			$("form").find("input", name: "login").click()			

			waitFor(25){$("a", text: contains("Ecommerce")).click()}

			println "Logged In."
			
			//to SecureProgramUpdate1
			//test

		}
	}	
	
}