package hmof.search

import org.compass.core.engine.SearchEngineQueryParseException

/**
 * Basic web interface for Grails Searchable Plugin 
 *
 * @author Maurice Nicholson
 */
class SearchController {
    def searchableService

    /**
     * Index page with search form and results
     */
    def search = {
		params.suggestQuery = false
	
        if (!params.q?.trim()) {
            return [:]
        }
        try {			
			
	        return [ searchResult: searchableService.search(params.q,params)]
			
          
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }
    }

    /**
     * Perform a bulk index of every searchable object in the database
     */
    def indexAll = {
        Thread.start {
            searchableService.index()
        }
        render("bulk index started in a background thread")
    }

    /**
     * Perform a bulk index of every searchable object in the database
     */
    def unindexAll = {
        searchableService.unindex()
        render("unindexAll done")
    }
}

