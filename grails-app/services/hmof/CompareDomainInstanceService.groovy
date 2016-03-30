package hmof

import grails.transaction.Transactional

@Transactional
class CompareDomainInstanceService {


	def deploymentService
	def utilityService
	/**
	 * get a difference of the Current persisted object and the object being updated 
	 * @return
	 */
	def getDiffMap(def currentObject) {


		//currentObject.properties.each{println it}

		List allRevisions = currentObject.retrieveRevisions()
		// Get Envers Object that has been previously persisted


		def enversObject = currentObject.findAtRevision(allRevisions[-1])

		def origMap = toMap(enversObject)
		def newMap = toMap(currentObject)

		def difMap = newMap - origMap

		log.info "The Dif Map is: $difMap"

		return difMap

	}

	/**
	 * Given a domain object and 2 revisions return a map of differences
	 * @param domainInstance
	 * @param currentRevision
	 * @param previousRevision
	 * @return
	 */
	def compareEnversRevisions(def programXMLInstance,def revisionNumber,def previousRevision){
		
		int previousRevision1 = (int) previousRevision;
		int revisionNumber1 = (int) revisionNumber;	

		def currentRev = programXMLInstance.findAtRevision(revisionNumber1)
		def previousRev = programXMLInstance.findAtRevision(previousRevision1)


		def origMap = toMap(currentRev)
		def prevMap = toMap(previousRev)


		def difMap = origMap - prevMap
		log.info "The Dif Map is: $difMap"
		return difMap
	}

	/**
	 * Given a domain object and 2 revisions return a map of differences
	 * @param domainInstance
	 * @param currentRevision
	 * @param previousRevision
	 * @return
	 */
	def spEnversRevision(def programXMLInstance,def revisionNumber){
		
		int revisionNumber1 = (int) revisionNumber;

		def currentRev = programXMLInstance.findAtRevision(revisionNumber1)
		
		def currentSecurePrograms  = currentRev.secureProgram


		
		log.info "The currentSecurePrograms are: $currentSecurePrograms "
		return currentSecurePrograms 
	}
	/**
	 * convert Domain object to Map
	 * @param object
	 * @return
	 */
	def Map toMap(object) { return object?.properties.findAll{ (it.key != 'class') }.collectEntries {
			it.value == null || it.value instanceof Serializable ? [it.key, it.value] : [it.key, toMap(it.value)]
		}
	}
}
