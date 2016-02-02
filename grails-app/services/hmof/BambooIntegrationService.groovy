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

		//def prodReviewUrl=Holders.config.bamboo.prod.review.hosturl
		//def productionUrl=Holders.config.bamboo.production.hosturl
		// Basic test Bamboo plan running locally if this does not work test the server is up via the browser http://172.17.4.106:8085
		//String endPoint = "http://localhost:8085/rest/api/latest/queue/CUS-TR?productSetup&ExecuteAllStages=true&bamboo.variable.comment=Promote&bamboo.variable.ISBNs="+xmlTextPath+"&Promote&bamboo.variable.JIRA=TT-1234"
		//String endPoint = "http://172.17.3.201:8085/rest/api/latest/queue/CUS-TR?ExecuteAllStages=true";
		String endPoint
	/*	if(envId==1)
		 endPoint = "http://localhost:8085/rest/api/latest/queue/CUS-TR?ExecuteAllStages=true&bamboo.variable.Comments='promoto to Cert-review'&bamboo.variable.programList="+xmlTextPath+"&bamboo.variable.JIRA="+jiraId
        else if(envId==2)
		endPoint = prodReviewUrl+"?ExecuteAllStages=true&bamboo.variable.Comments=Promoto to Prod-Review by CUSDEV&bamboo.variable.programList="+xmlTextPath+"&bamboo.variable.jiraIssue="+jiraId
		else if(envId==3)
		endPoint = productionUrl+"?ExecuteAllStages=true&bamboo.variable.Comments='Promoto to Prodction by CUSDEV'&bamboo.variable.programList="+xmlTextPath+"&bamboo.variable.jiraIssue="+jiraId
		*/
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
	//String resultEndPoint = "http://172.17.3.22:8085/rest/api/latest/result/CUS-TR-44"
		log.info "Bamboo build plan endPoint is ${resultEndPoint}"

		def resp = new RestBuilder().get(resultEndPoint) {

			headers['Authorization'] = getBambooCredentials()
			header "Accept", "application/json"

		}
		
		
		
		// parse the response for the State (either One of null, unknown, Successful or Failed)
		return resp.json.state

	}

}
