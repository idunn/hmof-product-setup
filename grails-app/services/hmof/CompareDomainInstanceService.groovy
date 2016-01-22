package hmof

import grails.transaction.Transactional

@Transactional
class CompareDomainInstanceService {



	/**
	 * get a difference of the Current persisted object and the object being updated 
	 * @return
	 */
	def getDiffMap(def currentObject) {

		List allRevisions = currentObject.retrieveRevisions()
		// Get SECOND last revision for testing
		def enversObject = currentObject.findAtRevision(allRevisions[-1])


		def origMap = toMap(enversObject)
		def newMap = toMap(currentObject)


		def difMap = newMap - origMap
		
		log.info difMap
		
		return difMap

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
