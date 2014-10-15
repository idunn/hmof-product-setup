package hmof

import groovy.sql.Sql
import java.sql.ResultSet

import grails.transaction.Transactional

@Transactional
class EnversQueryService {
	
	def dataSource

	def getDeletedObject(instanceId, revision, contentType){

		log.info "instanceId: " +  instanceId
		log.info "revision: " +  revision
		log.info "contentType: " +  contentType
		
		def sql = new Sql(dataSource)

		def row = null 
		
		if (contentType==2){
			row = sql.firstRow( "select top 1 * from Bundle_Aud where id=? AND REVTYPE !=2 AND rev=?", [instanceId, revision] )
			
		}
		
		if (contentType==3){
			row = sql.firstRow( "select top 1 * from SECURE_PROGRAM_AUD where id=? AND REVTYPE !=2 AND rev=?", [instanceId, revision] )
			
		}

		log.info row

		sql.close()
		row

	}

}

