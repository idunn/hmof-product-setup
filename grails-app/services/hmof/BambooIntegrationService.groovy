package hmof

import grails.transaction.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import grails.plugins.rest.client.RestBuilder
import grails.util.Holders
import org.apache.log4j.Logger
@Transactional
class BambooIntegrationService {


	/**
	 * get Bamboo credentials
	 * @return credentials encoded with Base64
	 */
	def getBambooCredentials(){

		// this is the test credentials for localhost
		def username =Holders.config.bamboo.username
		def password = Holders.config.bamboo.password

		println username
		println password
		def auth = 'Basic '+ "${username}:${password}".getBytes('iso-8859-1').encodeBase64()

	}


	/**
	 * Connect to Bamboo's Rest API to trigger build plans
	 * @return
	 */
	def bambooTrigger (def xmlTextPath, def jiraId,def deploymentBambooUrl,Logger log,def promotionInstance) {

		String Comment = "Proccesed by the Product Setup WebApp"
		String endPoint
       endPoint=deploymentBambooUrl+"?ExecuteAllStages=true&bamboo.variable.Comments="+jiraId+" : Promoted by CUSDEV&bamboo.variable.IdList="+xmlTextPath+"&bamboo.variable.jiraIssue="+jiraId
		
		
		
		log.info" Bamboo Url is ${endPoint}"
		log.info" JIRA is ${jiraId}"

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()

		def resp = new RestBuilder().post(endPoint) {

			headers['Authorization'] = getBambooCredentials()
			header "Accept", "application/json"

			body (form)
		}
			
		log.info "The HTTP Status of the response is: ${resp.status}"
	
		log.info "The Parsed build results Key is ${resp.json.buildResultKey}"
	
		def status
		
		for(int i=0;i<=10;i++)
		{			
			
			log.info "Fetching  Bamboo build plan ${resp.json.buildResultKey} status attempt :"+i
		status=bambooBuildResults(resp.json.buildResultKey)
		log.info "Bamboo build plan status :"+status
		if( status.equals("Unknown") || status.equals("null"))
		{
			if(i==1)
			Thread.sleep(500000)
			else
			Thread.sleep(200000)
			
		}else if(status.equals("Successful"))
		{				
			return status
		}else if( status.equals("Failed"))
		{
			
			log.info "Bamboo build plan ${resp.json.buildResultKey} got Failed .Please check the bamboo logs to rectify the errors"
			promotionInstance.properties = [bambooPlanNumber:resp.json.buildResultKey]
			return status
		}
		}
		return status
	}


	/**
	 * Get the result from the bamboo Job	 
	 * @param buildResultKey
	 * @return One of null, unknown, Successful or Failed
	 */
	def bambooBuildResults(def buildResultKey){

		String resultEndPoint = "${Holders.config.bamboo.resulturl+buildResultKey}"

		log.info "Bamboo build plan endPoint is ${resultEndPoint}"

		def resp = new RestBuilder().get(resultEndPoint) {

			headers['Authorization'] = getBambooCredentials()
			header "Accept", "application/json"

		}
		
		
		// parse the response for the State (either One of null, unknown, Successful or Failed)
		return resp.json.state

	}

}
