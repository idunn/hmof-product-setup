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
	 * get Bamboo User Credentials
	 * @return credentials encoded with Base64
	 */
	def getBambooCredentials(){

		// get credentials from the configuration file
		def username = Holders.config.bamboo.username
		def password = Holders.config.bamboo.password

		def auth = 'Basic '+ "${username}:${password}".getBytes('iso-8859-1').encodeBase64()

	}


	/**
	 * Connect to Bamboo's Rest API to trigger a new Job	 
	 * @param idList
	 * @param jiraId
	 * @param deploymentBambooUrl
	 * @param log
	 * @param promotionInstance
	 * @return Status of Promotion
	 */
	def bambooTrigger ( def idList, def jiraId, def deploymentBambooUrl, Logger log, def promotionInstance ) {

		String comment = "Proccesed by the Product-Setup WebApp"
		String endPoint = "${deploymentBambooUrl}?ExecuteAllStages=true&bamboo.variable.Comments=${jiraId}:${comment}&bamboo.variable.IdList=${idList}&bamboo.variable.jiraIssue=${jiraId}"

		log.info "Bamboo REST Url is: ${endPoint}"

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()

		def resp = new RestBuilder().post(endPoint) {

			headers['Authorization'] = getBambooCredentials()
			header "Accept", "application/json"

			body (form)
		}

		log.info "The HTTP response status is: ${resp.status}"

		def buildKey = "${resp.json.buildResultKey}"
		log.info "The plans build key is: ${buildKey}"


		def status


		// poll for a maximum 30 times
		for(int i=0;i<=30;i++){

			log.info "Checking Status of the Bamboo Plan: attempt: " + (i + 1)
			status = bambooBuildResults( buildKey )

			// initially wait for 5 minutes before checking
			if(i==0){
				log.info "Bamboo build plan status: " + status
				log.info "Waiting 5 minutes before checking the build status..."
				Thread.sleep(500000)
				status = bambooBuildResults( buildKey )

			}


			if( status.equals("Unknown") || status.equals(null))
			{
				log.info "Bamboo build plan status: " + status
				log.info "Waiting 3 minutes before rechecking the build status..."
				Thread.sleep(300000)


			}else if(status.equals("Successful"))
			{
				log.info "Bamboo build plan status: " + status
				log.info "${deploymentBambooUrl}-${buildKey}"
				return status

			}else if(status.equals("Failed"))
			{
				log.info "Bamboo build plan status: " + status
				log.info "Bamboo build plan ${buildKey} Failed! Please check the bamboo logs to help rectify the issue"
				log.info "${deploymentBambooUrl}-${buildKey}"
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

		log.info "Bamboo build plan endPoint is: ${resultEndPoint}"

		def resp = new RestBuilder().get(resultEndPoint) {

			headers['Authorization'] = getBambooCredentials()
			header "Accept", "application/json"

		}

		// parse the response for the State (either One of null, unknown, Successful or Failed)
		return resp.json.state

	}

}
