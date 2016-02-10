package hmof

import grails.transaction.Transactional

@Transactional
class CompareDomainInstanceService {



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
	def compareEnversRevisions(def domainInstance, def currentRevision, def previousRevision){

		
		def currentRev = domainInstance.findAtRevision(currentRevision)
		def previousRev = domainInstance.findAtRevision(previousRevision)
		

		def origMap = toMap(currentRev)
		def prevMap = toMap(previousRev)


		def difMap = origMap - prevMap


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
