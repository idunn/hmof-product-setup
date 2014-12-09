package hmof

import groovy.sql.Sql
import java.sql.ResultSet
import org.codehaus.groovy.grails.web.util.WebUtils

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

	/**
	 * Download the log file
	 * @param logFileLocation
	 * @return
	 */
	def getLogFile(def logFileLocation){

		def webUtils = WebUtils.retrieveGrailsWebRequest()
		def response = webUtils.getCurrentResponse()

		def logFile = logFileLocation
		log.debug("logFile is: " + logFile)
		File downloadFile = new File(logFile)
		log.debug("downloadFile is:" + downloadFile)
		try {
			if(downloadFile.exists()){

				OutputStream out = null
				response.setHeader("Content-Type", "application/octet-stream;")
				response.setHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName())
				FileInputStream fileInputStream = new FileInputStream(downloadFile)

				out = response.getOutputStream()
				int fileCharacter
				while((fileCharacter = fileInputStream.read())!= -1) {
					out.write(fileCharacter)
				}
				fileInputStream.close()
				out.flush()
				out.close()
			}
			else{
				log.debug "Log file not found"
			}
		}
		catch(Exception e){
			log.error("exception in download action is: "+e.getMessage())
			log.error("exception in download action is: "+e.getStackTrace().toString())
		}
	}


	// TODO
	def parseTextFile(File commerceObjectFile){
		
		def contentType = ContentType.where{id==4}.get()
		
		
		log.info"Parsing Text file in Service" + commerceObjectFile.getClass()

		commerceObjectFile.eachLine() { line ->
			def cols = line.split(",")

			CommerceObject dom = new CommerceObject(objectName:cols[0],comments:cols[1],pathToCoverImage:cols[2],isbnNumber:cols[3],
				teacherLabel:cols[4],teacherUrl:cols[5],studentLabel:cols[6],studentUrl:cols[7],category:'Language Arts', contentType:contentType,
				objectType:cols[9],objectReorderNumber:cols[10],subject:cols[11],gradeLevel:[12])			
			
			dom.save(failOnError:true)
		}
	}

}

