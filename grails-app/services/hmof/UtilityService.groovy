package hmof

import groovy.sql.Sql
import java.sql.ResultSet

import grails.transaction.Transactional

@Transactional
class UtilityService {
	
	def dataSource

	def getDeletedObject(instanceId, revision, contentType){

		log.info "Deleted Objects instanceId: " +  instanceId
		log.info "Deleted Objects revision: " +  revision
		log.info "Deleted Objects contentType: " +  contentType
		
		def sql = new Sql(dataSource)

		def row = null 
		
		if (contentType==2){
			row = sql.firstRow( "select * from Bundle_Aud where id=? AND REVTYPE !=2 AND rev=? Order By LAST_UPDATED desc LIMIT 1", [instanceId, revision] )
			
		}
		
		if (contentType==3){
			row = sql.firstRow( "select * from SECURE_PROGRAM_AUD where id=? AND REVTYPE !=2 AND rev=? Order By LAST_UPDATED desc LIMIT 1", [instanceId, revision] )
			
		}
		
		if (contentType==4){
			row = sql.firstRow( "select * from COMMERCE_OBJECT_AUD where id=? AND REVTYPE !=2 AND rev=? Order By LAST_UPDATED desc LIMIT 1", [instanceId, revision] )
			
		}		
		

		sql.close()
		row

	}

}

