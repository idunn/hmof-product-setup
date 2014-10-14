package hmof

import groovy.sql.Sql
import java.sql.ResultSet

import grails.transaction.Transactional

@Transactional
class EnversQueryService {
	
	def dataSource

	def getDeletedBundle(instanceId, revision){

		log.info "instanceId: " +  instanceId
		log.info "revision: " +  revision		
		
		def sql = new Sql(dataSource)

		def row = sql.firstRow( "select top 1 * from Bundle_Aud where id=? AND REVTYPE !=2 AND rev=?", [instanceId, revision] )

		log.info row	


		sql.close()
		row

	}

}

