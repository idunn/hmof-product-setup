package hmof

import groovy.sql.Sql
import java.sql.ResultSet
import org.codehaus.groovy.grails.web.util.WebUtils
import grails.util.Holders
import grails.converters.JSON
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
			row = sql.firstRow( "select * from Bundle_Aud where id=? AND REVTYPE !=2 AND rev=? Order By LAST_UPDATED desc LIMIT 1", [instanceId, revision])
		}

		if (contentType==3){
			row = sql.firstRow( "select * from SECURE_PROGRAM_AUD where id=? AND REVTYPE !=2 AND rev=? Order By LAST_UPDATED desc LIMIT 1", [instanceId, revision])
		}

		if (contentType==4){
			row = sql.firstRow( "select * from COMMERCE_OBJECT_AUD where id=? AND REVTYPE !=2 AND rev=? Order By LAST_UPDATED desc LIMIT 1", [instanceId, revision])
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


	/**
	 * Parse the text file and persist the Commerce Object Data
	 * @param commerceObjectFile
	 * @return
	 */
	def parseTextFile(File commerceObjectFile){

		def contentType = ContentType.where{id==4}.get()
		List errorList = new ArrayList();
		List gradeList = new ArrayList();
		log.info"Parsing Text file in Service" + commerceObjectFile.getClass()
		def gradeLevel1 = ["6", "7", "8", "9", "10", "11", "12"]
		def gradeLevel2 = ["9", "10", "11", "12"]
		def gradeLevel3 = ["6", "7", "8"]

		commerceObjectFile.eachCsvLine { tokens ->

			 
			def list = (tokens[12].replaceAll(","," ")).tokenize()


			if(list.containsAll(gradeLevel1)){
				tokens[12]="6-12"
			}else if(list.containsAll(gradeLevel2)) {
				tokens[12]="9-12"
			}else if(list.containsAll(gradeLevel3)){
				tokens[12]="6-8"
			}
		
			
			CommerceObject dom=	new CommerceObject(objectName:tokens[0],comments:tokens[1],pathToCoverImage:tokens[2],isbnNumber:tokens[3].replaceAll('"',''),
			teacherLabel:tokens[4],teacherUrl:tokens[5],studentLabel:tokens[6],studentUrl:tokens[7],category:getCategory(tokens[8]), contentType:contentType,
			objectType:tokens[9],objectReorderNumber:tokens[10],subject:tokens[11],gradeLevel:tokens[12])

			if (!dom.save(flush: true)) {

				log.error "Failed to Save CommerceObject"
				errorList.add("<b>Object Name : "+tokens[0]+"</b>")
				dom.errors.allErrors.each {

					errorList.add(Holders.getGrailsApplication().mainContext.messageSource.getMessage(it, null))
				}
				errorList.add("<br>")
			}
		}

		return errorList
	}

	/**
	 * Helper method to return the String value of the Category
	 * @param category
	 * @return
	 */
	def getCategory(def category){

		switch (category) {
			case 'Other':
				category = '-1'
				break
			case '-1':
				category = "Other"
				break
			case 'Science & Health':
				category = '0'
				break
			case '0':
				category = 'Science & Health'
				break
			case 'Social Studies':
				category = '1'
				break
			case '1':
				category = 'Social Studies'
				break
			case 'Language Arts':
				category = '2'
				break
			case '2':
				category = 'Language Arts'
				break
			case 'Mathematics':
				category = '3'
				break
			case '3':
				category = 'Mathematics'
				break
			case 'World Languages':
				category = '4'
				break
			case '4':
				category = 'World Languages'
				break
			default:
				category = 'Other'
		}

		return category
	}
}

