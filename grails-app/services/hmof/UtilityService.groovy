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
	def springSecurityService
	def grailsApplication

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

	def parseTextFile(csvfile,contenttype){

		//def contentType = ContentType.where{id==contenttype}.get()
		def uploadFile
		List errorList = new ArrayList()
		List gradeList = new ArrayList()
		try {

			if(csvfile==null) {
				flash.message = "Import File cannot be empty"
				if(contenttype==4){
					redirect(controller:"CommerceObject",action: "list")
				}
				else{
					redirect(controller:"SecureProgram",action: "list")
				}
				return
			} else {
				def filename = csvfile.getOriginalFilename()

				String fullPath=grailsApplication.config.uploadFolder

				File upLoadedfileDir = new File(fullPath)
				if (!upLoadedfileDir.exists()) {
					upLoadedfileDir.mkdirs()
					log.info "Upload directory: ${upLoadedfileDir.getAbsolutePath()}"
				}

				uploadFile = new File(fullPath, filename)
				uploadFile.createNewFile() //if it doesn't already exist
				FileOutputStream fos = new FileOutputStream(uploadFile)
				fos.write(csvfile.getBytes())
				fos.close()

				log.info"Calling Service to import Commerce Objects"

			}



			log.info "Parsing Text file in Service"

			if(contenttype==4){
				def gradeLevel1 = ["6", "7", "8", "9", "10", "11", "12"]
				def gradeLevel2 = ["9", "10", "11", "12"]
				def gradeLevel3 = ["6", "7", "8"]

				uploadFile.eachCsvLine { tokens ->


					def list = (tokens[12].replaceAll(","," ")).tokenize()

					if(list.containsAll(gradeLevel1)){
						tokens[12]="6-12"
					}else if(list.containsAll(gradeLevel2)) {
						tokens[12]="9-12"
					}else if(list.containsAll(gradeLevel3)){
						tokens[12]="6-8"
					}else if(list.contains("K") ||list.contains("1") ||list.contains("2") ||list.contains("3") || list.contains("4") || list.contains("5")  ){
						tokens[12]="6"
					}


					CommerceObject dom=	new CommerceObject(objectName:tokens[0],comments:tokens[1],pathToCoverImage:tokens[2],isbnNumber:tokens[3].replaceAll('"',''),
					teacherLabel:tokens[4],teacherUrl:tokens[5],studentLabel:tokens[6],studentUrl:tokens[7],category:getCategory(tokens[8]), contentType:contenttype,
					objectType:tokens[9],objectReorderNumber:tokens[10],subject:tokens[11],gradeLevel:tokens[12],userUpdatingCO:springSecurityService?.currentUser?.username)


					if (!dom.save(flush: true)) {

						log.error "Failed to Save CommerceObject"
						errorList.add("<b>Object Name : " + tokens[0] + "</b>")
						dom.errors.allErrors.each {

							errorList.add(Holders.getGrailsApplication().mainContext.messageSource.getMessage(it, null))
						}
						errorList.add("<br>")
					}
				}
			}else{
				uploadFile.eachCsvLine { tokens ->

					


					def knewtonProductVal
					def secureWordStr1
					def secureWordStr2
					def secureWordStr3
					def secureWordLocStr1
					def secureWordLocStr2
					def secureWordLocStr3
					def secureWordPageNumStr1
					def secureWordPageNumStr2
					def secureWordPageNumStr3

					if(tokens[10]!=null && tokens[10]!="")
						knewtonProductVal=true
					else
						knewtonProductVal=false


					def secureWordStrs=tokens[30]

					if(secureWordStrs!=null){
						if(secureWordStrs.contains(',')){
							def secureWordStr=secureWordStrs.split(',')
							secureWordStr1=secureWordStr[0]
							if(secureWordStr[1]!=null)
								secureWordStr2=secureWordStr[1]
							if(secureWordStr[2]!=null)
								secureWordStr3=secureWordStr[2]
						}else
						{
							secureWordStr1=tokens[30]

						}
					}
					def secureWordLocStrs=tokens[31]
					if(secureWordLocStrs!=null){
						if(secureWordLocStrs.contains(',')){
							def secureWordLocStr=secureWordLocStrs.split(',')
							secureWordLocStr1=secureWordLocStr[0]
							if(secureWordLocStr[1]!=null)
								secureWordLocStr2=secureWordLocStr[1]
							if(secureWordLocStr[2]!=null)
								secureWordLocStr3=secureWordLocStr[2]
						}else{
							secureWordLocStr1=tokens[31]

						}


					}
					def secureWordPageNumStrs=tokens[32]
					if(secureWordPageNumStrs!=null){
						if(secureWordPageNumStrs.contains(',')){
							def secureWordPageNumStr=secureWordPageNumStrs.split(',')
							secureWordPageNumStr1=secureWordPageNumStr[0]
							if(secureWordPageNumStr[1]!=null)
								secureWordPageNumStr2=secureWordPageNumStr[1]
							if(secureWordPageNumStr[2]!=null)
								secureWordPageNumStr3=secureWordPageNumStr[2]
						}else{
							secureWordPageNumStr1=tokens[32]
						}


					}
def eGrades

					
					if(tokens[8]!=null && tokens[8]!="" ){
						eGrades==tokens[8]
					}else
				{
					eGrades="Not Required"
				}

					SecureProgram dom=new SecureProgram (productName:tokens[0].replaceAll('&#169;', '©'), registrationIsbn:tokens[1].replaceAll('"',''),comments:tokens[2],
					onlineIsbn:tokens[3].replaceAll('"',''),curriculumArea:tokens[4], copyright:tokens[5],labelForOnlineResource:tokens[6],pathToResource:tokens[7],essayGraderPrompts:eGrades, pathToCoverImage:tokens[9],
					knewtonProduct:knewtonProductVal,knowledgeGraphIdProd:tokens[10],knowledgeGraphWarmUpTimeLimit:tokens[11],knowledgeGraphEnrichmentTimeLimit:tokens[12],knowledgeGraphEnrichmentCbiTimeLimit:tokens[13],
					labelForTeacherAdditionalResource:tokens[14],pathToTeacherAdditionalResource:tokens[15],
					labelForTeacherAdditionalResource2:tokens[16],pathToTeacherAdditionalResource2:tokens[17],
					labelForTeacherAdditionalResource3:tokens[18],pathToTeacherAdditionalResource3:tokens[19],
					labelForTeacherAdditionalResource4:tokens[20],pathToTeacherAdditionalResource4:tokens[21],
					labelForStudentAdditionalResource:tokens[22],pathToStudentAdditionalResource:tokens[23],
					labelForStudentAdditionalResource2:tokens[24],pathToStudentAdditionalResource2:tokens[25],
					labelForStudentAdditionalResource3:tokens[26],pathToStudentAdditionalResource3:tokens[27],
					labelForStudentAdditionalResource4:tokens[28],pathToStudentAdditionalResource4:tokens[29],
					securityWord:secureWordStr1, securityWordLocation:secureWordLocStr1,securityWordPage:secureWordPageNumStr1,
					securityWord2:secureWordStr2, securityWordLocation2:secureWordLocStr2,securityWordPage2:secureWordPageNumStr2,
					securityWord3:secureWordStr3,securityWordLocation3:secureWordLocStr3,securityWordPage3:secureWordPageNumStr3,
					includeDashboardObject:true,includeEplannerObject:true,includeNotebookObject:false,
					userUpdatingSProgram:springSecurityService?.currentUser?.username,contentType:contenttype)

					if (!dom.save(flush: true)) {

						log.error "Failed to Save CommerceObject"
						errorList.add("<b>Program Name : " + tokens[0] + "</b>")
						dom.errors.allErrors.each {

							errorList.add(Holders.getGrailsApplication().mainContext.messageSource.getMessage(it, null))
						}
						errorList.add("<br>")
					}
				}

			}

			if(errorList?.empty)
			{
				uploadFile.delete()
			}
		}catch (IOException e) {
			flash.message = "There were errors when importing the Commerce Objects"
			log.error "There were errors when importing the Commerce Objects" + e
			if(contenttype==4){
				redirect(controller:"CommerceObject",action: "importCSV")
			}
			else{
				redirect(controller:"SecureProgram",action: "importCSV")
			}
			return
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
	/**
	 *
	 * @param program revision
	 * @return
	 */
	def getLastUpdatedUserName(def revision){
		def sql = new Sql(dataSource)
		def row
		def userName
		try{
			row = sql.rows("select user_updating_program from program_aud where rev=?",[revision])
			userName = row.get(0).get("user_updating_program")

		}
		catch(Exception e){
			log.error("exception in getLastUpdatedUserName method is: "+e.getMessage())
			log.error("exception in getLastUpdatedUserName method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}
		userName
	}
	/**
	 *
	 * @param CommerceObject revision
	 * @return
	 */
	def getLastUpdatedUserNameForCO(def revision){
		def sql = new Sql(dataSource)
		def row
		def userName
		try{

			row = sql.rows("select user_updatingco from commerce_object_aud where rev=?",[revision])
			userName = row.get(0).get("user_updatingco")

		}
		catch(Exception e){
			log.error("exception in getLastUpdatedUserNameForCO method is: "+e.getMessage())
			log.error("exception in getLastUpdatedUserNameForCO method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}
		userName
	}
	/**
	 *
	 * @param Secure Program revision
	 * @return
	 */
	def getLastUpdatedUserNameForSP(def revision){
		def sql = new Sql(dataSource)
		def row

		def userName
		try{

			row = sql.rows("select user_updatingsprogram from secure_program_aud where rev=?",[revision])
			userName = row.get(0).get("user_updatingsprogram")

		}
		catch(Exception e){
			log.error("exception in getLastUpdatedUserNameForSP method is: "+e.getMessage())
			log.error("exception in getLastUpdatedUserNameForSP method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}
		userName
	}
	/**
	 *
	 * @param Bundle revision
	 * @return
	 */
	def getLastUpdatedUserNameForBundle(def revision){
		def sql = new Sql(dataSource)
		def row

		def userName
		try{
			row = sql.rows("select user_updating_bundle from bundle_aud where rev=?",[revision])
			userName = row.get(0).get("user_updating_bundle")

		}
		catch(Exception e){
			log.error("exception in getLastUpdatedUserNameForBundle method is: "+e.getMessage())
			log.error("exception in getLastUpdatedUserNameForBundle method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}
		userName
	}

	/**
	 *
	 * @param Bundle revision
	 * @return
	 */
	def getJobsStuckInProgress(){
		def sql = new Sql(dataSource)
		def row

		try{

			row = sql.rows("SELECT id FROM promotion WHERE (hour(SYSDATE) - hour(date_created))>2  AND status = 'In_Progress'")
			log.info("row:"+row)
		}
		catch(Exception e){
			log.error("exception in getJobsStuckInProgress method is: "+e.getMessage())
			log.error("exception in getJobsStuckInProgress method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}
		row
	}
	/**
	 * Get ISBN product details
	 * @param isbnNumber
	 * @return sapResults
	 */
	def getIsbnRecords(List isbnNumber){
		def sapResultsMap = [:]
		try{
		
		def endpoint = 'http://eaicamel-prd.hmco.com/services/material/getMaterialDetailEx'
		def nameSpace = "http://www.hmco.com/EAI/OTS/MaterialNew"
		withSoap( serviceURL:endpoint,sslTrustAllCerts:true ) {
			def res = send {
				body {
					getMaterialDetailRequest(xmlns: nameSpace) {
						materialKeyList(xmlns: nameSpace) {
							isbnNumber.each{
								isbn(xmlns:nameSpace,it.trim())
							}
						}
					}
				}
			}
			getmaterialDetailResponse(res)
		}
	
	}catch(Exception e){
	log.error("exception in getIsbnRecord method is: "+e.getMessage())
	log.error("exception in getIsbnRecord method is: "+e.getStackTrace())
	sapResultsMap.put("error", e.getMessage())
	sapResultsMap
}
	
	}
	/**
	 * Get ISBN product details
	 * @param isbnNumber
	 * @return sapResults
	 */
	def getIsbnRecord(String isbnNumber){
		def sapResultsMap = [:]
		try{			
			
		def endpoint = 'http://eaicamel-prd.hmco.com/services/material/getMaterialDetailEx'
		def nameSpace = "http://www.hmco.com/EAI/OTS/MaterialNew"
		withSoap( serviceURL:endpoint,sslTrustAllCerts:true ) {
			def res = send {
				body {
					getMaterialDetailRequest(xmlns: nameSpace) {
						materialKeyList(xmlns: nameSpace) {							
								isbn(xmlns:nameSpace,isbnNumber.trim())							
						}
					}
				}
			}
			getmaterialDetailResponse(res)			
		}
				
		}catch(Exception e){
		log.error("exception in getIsbnRecord method is: "+e.getMessage())
		log.error("exception in getIsbnRecord method is: "+e.getStackTrace())
		sapResultsMap.put("error", e.getMessage())
		sapResultsMap
	}
	}
	
	/**
	 * Return the required data
	 * @param res
	 * @return
	 */
	def getmaterialDetailResponse(def res){
		def sapResultsMap = [:]
		try{
		res.materialDetailList.materialDetail.each{
		
			sapResultsMap.put( it.isbn13.text(),it.materialStatusDescription.text())
		}
	
		}catch(Exception e){
			log.error("exception in getmaterialDetailResponse method is: "+e.getMessage())
			log.error("exception in getmaterialDetailResponse method is: "+e.getStackTrace())
			sapResultsMap.put("error", e.getMessage())
		}

		sapResultsMap
	}



}

