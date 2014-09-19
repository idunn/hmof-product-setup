package hmof.geb

import geb.*
import groovy.util.logging.Log4j

@Log4j
class BundleGebWork extends Page {

	def initBaseUrl(def baseUrl){
		log.info "baseUrl" + baseUrl
		url = baseUrl

	}

	static url

	static content = {

		//TODO
	}

	void lookupIsbn(def enversInstanceToDeploy){

		log.info "Looking up Bundle ISBN..."


	}

	def addCBundleData(def enversInstanceToDeploy){

		log.info "Add Bundle Data..."

	}

}
