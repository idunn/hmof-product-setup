package hmof
import hmof.deploy.*


import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator

import grails.util.Holders
import groovy.sql.Sql
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.streamingmarkupsupport.BaseMarkupBuilder.Document
import hmof.programxml.ProgramXML
import hmof.security.*
import javax.xml.bind.Element
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import org.apache.commons.io.FileUtils
import java.sql.ResultSet
import org.codehaus.groovy.grails.web.util.WebUtils

class ProgramXmlService {

	static transactional = false
	def dataSource
	def deploymentService
	def commerceObjectService
	def utilityService
	def springSecurityService
	def subversionIntegrationService
	def bambooIntegrationService
	def compareDomainInstanceService
	Logger log = Logger.getLogger(JobService.class)

	/**
	 * Deploy or Promote Jobs in Pending or Pending_Repromote Status
	 * @return
	 */
	def executeJob(){


		def promotionProgramXMlJobInstance = Promotion.where{status == JobStatus.PendingProgramDeploy.getStatus() || status == JobStatus.PendingProgramRepromote.getStatus() || status == JobStatus.PendingProgramRetry.getStatus()}.list(max:1)
		def promotionProgramXMlJobNumber =  promotionProgramXMlJobInstance.jobNumber
		if(!promotionProgramXMlJobInstance.isEmpty())
		{
			def jobList = Job.where{jobNumber == promotionProgramXMlJobNumber}.list()
			Long promotionJobId =  promotionProgramXMlJobInstance.id[0]

			def promotionInstance = Promotion.get(promotionJobId)
			promotionInstance.discard()
			promotionInstance.lock()

			def statusStart = null

			statusStart = JobStatus.In_Progress.getStatus()

			promotionInstance.properties = [status: statusStart]
			promotionInstance.save(failOnError: true, flush:true)

			def processJobs = processJobs(jobList, promotionInstance)

			def statusFinish = null

			if (processJobs){
				statusFinish = JobStatus.Success.getStatus()
			} else {statusFinish = JobStatus.Failed.getStatus()}

			// return map
			def results = [status: statusFinish, promotionId:promotionProgramXMlJobInstance.id]
		}

	}
	/**
	 * Take the jobs and process them by pushing the content to the environment via Geb
	 * @param jobs
	 * @return
	 */
	Boolean processJobs(def jobs, def promotionInstance) {


		String programXMLIsbn=""
		Logger customerLog=null
		def commitJobs =false
		def commitJobs1 =false

		try{
			promotionInstance.refresh()
			// Get the environment URL
			def environmentInstance = Environment.where{id==promotionInstance.environmentsId}.get()
			def envId = environmentInstance.id
			def deploymentUrl = environmentInstance.url
			def deploymentBambooUrl = environmentInstance.bambooPlan1
			def envName = environmentInstance.name
			def user_Name = User.where{id == promotionInstance.userId}.username.get()
			String jiraId = promotionInstance.jiraId
			// Divide out the instances
			def programXML = jobs.findAll{it.contentTypeId == 5}
			def cacheLocation = Holders.config.cacheLocation

			def localURL= Holders.config.programXMLFolder
			def localTxtURL= Holders.config.programXMLTextFolder
			// used only to initialize logs
			if (!programXML.isEmpty() && envId==1 ){

				programXML.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					Long jobNumber = it.jobNumber

					def programXMLInstance = ProgramXML.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 5)

					def path=localURL+programXMLInstance.filename

					customerLog = initializeLogger(programXMLInstance.buid, cacheLocation,envId,5)
					customerLog=getLogHeader(customerLog, envId, jobNumber, user_Name, envName )


					def previousJob = deploymentService.getPreviousProgramXMLJob( instanceNumber, jobNumber, envId )
					def previousRevision
					boolean updateMDSISBN=false
					if (previousJob)
					{
						previousRevision=previousJob.revision

						def updatedValues = compareDomainInstanceService.compareEnversRevisions(programXMLInstance,revisionNumber,previousRevision)

						if( updatedValues.containsKey('title') ){
							updateMDSISBN=true
						}


					}
					def secureProgramsValues = compareDomainInstanceService.spEnversRevision(programXMLInstance,revisionNumber)
					def modifiedMDSIsbns =getModifiedMDSIsbns(programXMLInstance.id,secureProgramsValues.id,envId)
					
						
					commitJobs =subversionIntegrationService.commitSvnContent(path,customerLog,programXMLInstance,updateMDSISBN,modifiedMDSIsbns)
					
					if(commitJobs )
					{


						/*def respJson=bambooIntegrationService.bambooTrigger(localTextURL,jiraId,envId,customerLog,promotionInstance)
						 if(respJson.equals("Successful"))	{	*/	
						customerLog.info "${'*'.multiply(5)} Finished Deploying Program XML ${'*'.multiply(5)}\r\n"
						customerLog.info"${'*'.multiply(5)} Status ${'*'.multiply(5)}\r\n"
						customerLog.info("Job Status: Success\r\n")
						/*}else
						 {
						 customerLog.info"${'*'.multiply(5)} Status ${'*'.multiply(5)}\r\n"
						 customerLog.info("Job Status: Failed\r\n")
						 }*/


					}else
					{
						customerLog.info"${'*'.multiply(5)} Status ${'*'.multiply(5)}\r\n"
						customerLog.info("Job Status: Failed\r\n")
					}
				}
			} // end bundle log initializer
			else
			{
				programXML.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision

					Long jobNumber = it.jobNumber
					def programXMLInstance = ProgramXML.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 5)

					def path=localURL+programXMLInstance.filename
					def pathStr=programXMLInstance.filename.replace(".xml","")
					def localTextURL=pathStr+".txt"
					customerLog = initializeLogger(programXMLInstance.buid, cacheLocation,envId,5)
					customerLog=getLogHeader(customerLog, envId, jobNumber, user_Name, envName )


					def previousJob = deploymentService.getPreviousProgramXMLJob( instanceNumber, jobNumber, envId )
					def previousRevision
					boolean updateMDSISBN=false
					if (previousJob)
					{
						previousRevision=previousJob.revision

						def updatedValues = compareDomainInstanceService.compareEnversRevisions(programXMLInstance,revisionNumber,previousRevision)

						if( updatedValues.containsKey('title')  ){
							updateMDSISBN=true
						}

					}
					def secureProgramsValues = compareDomainInstanceService.spEnversRevision(programXMLInstance,revisionNumber)
					def modifiedMDSIsbns =getModifiedMDSIsbns(programXMLInstance.id,secureProgramsValues.id,envId)

					
					def respJson=bambooIntegrationService.bambooTrigger(jiraId,deploymentBambooUrl,customerLog,promotionInstance,programXMLInstance,updateMDSISBN,modifiedMDSIsbns)

					if(respJson.equals("Successful"))
					{
						commitJobs=true
						customerLog.info "${'*'.multiply(5)} Finished Deploying Program XML ${'*'.multiply(5)}\r\n"
						customerLog.info"${'*'.multiply(5)} Status ${'*'.multiply(5)}\r\n"
						customerLog.info("Job Status: Success\r\n")

					}else
					{

						customerLog.info"${'*'.multiply(5)} Status ${'*'.multiply(5)}\r\n"
						customerLog.info("Job Status: Failed\r\n")
					}

				}
			}

			log.debug "cacheLocation" + cacheLocation

		}
		catch(InterruptedException  e){

			log.error "Exception deploying content: " + e
			return false

		}
		catch(Exception e){

			log.error "Exception deploying content: " + e
			return false

		}

		return commitJobs
	}

	def generateProgramXML(ProgramXML programXMLInstance)
	{
		def builder = new groovy.xml.StreamingMarkupBuilder()
		builder.encoding = 'UTF-8'
		StringBuilder sb = new StringBuilder();
		ArrayList<String> items = new ArrayList<String>()

		try{

			def programsXMLLocation = Holders.config.programXMLFolder

			File f = new File(programsXMLLocation);
			f.mkdir();
			File f1 = new File(programsXMLLocation+programXMLInstance.filename);

			if(f1.exists()) {
				subversionIntegrationService.doSvnUpdate(f1)


				def newSecurePrograms=[]
				def oldSecurePrograms=[]
				def oldSecurePrograms1=[]
				def oldSecurePrograms2=[]
				def editSecurePrograms=[]


				if(programXMLInstance.secureProgram){
					newSecurePrograms =SecureProgram.where{id in (programXMLInstance.secureProgram.id)}.list()
				}


				XmlParser parser = new XmlParser()

				def xmldata1 = parser.parse(new File(programsXMLLocation+programXMLInstance.filename))


				def root = new XmlParser().parse( f1 )
				xmldata1.hsp_product.each{
					oldSecurePrograms.add(it.product_isbn.text())
					oldSecurePrograms1.add(it.product_isbn.text())
				}

				def newonlineIsbn=newSecurePrograms.onlineIsbn
				oldSecurePrograms.removeAll(newonlineIsbn);
				//	newonlineIsbn.removeAll(oldSecurePrograms1);


				def secprogramIds= getProgramXMLAudSecurePrograms(programXMLInstance.id)

				if(secprogramIds){
					oldSecurePrograms2 =SecureProgram.where{id in (secprogramIds.secure_program_id)}.list()
				}


				def oldProgramInstanceIsbns=oldSecurePrograms2.onlineIsbn
				if(!root['@title'].equals(programXMLInstance.title))
					root['@title']=programXMLInstance.title

				if(!root['@buid'].equals(programXMLInstance.buid))
					root['@buid']=programXMLInstance.buid


				def parent;
				if(newonlineIsbn){
					editSecurePrograms =SecureProgram.where{onlineIsbn in (newonlineIsbn)}.list()

					newonlineIsbn.each{
						def misbn=it
						def nodeToDel = root.hsp_product.find{ it.product_isbn.text()==misbn}

						if(nodeToDel)
						{
							parent = nodeToDel.parent()
							parent.remove(nodeToDel)
						}
					}

					editSecurePrograms.each{

						def knewton="";
						if(null!=it.knewtonProduct && it.knewtonProduct){

							knewton="<KnowledgeGraph><warm_up_time_limit>"+it.knowledgeGraphWarmUpTimeLimit+"</warm_up_time_limit><intervention_time_limit>"+it.knowledgeGraphEnrichmentTimeLimit+"</intervention_time_limit><enrichment_time_limit>"+it.knowledgeGraphEnrichmentCbiTimeLimit+"</enrichment_time_limit><environmentKGIds><certReviewKG>"+it.knowledgeGraphIdDev+"</certReviewKG><prodReviewKG>"+it.knowledgeGraphIdQA+"</prodReviewKG><prodKG>"+it.knowledgeGraphIdProd+"</prodKG></environmentKGIds></KnowledgeGraph>"
						}

						def toadd = "<hsp_product><product_isbn lang='"+it.language+"'>"+it.onlineIsbn+"</product_isbn>"+knewton+"</hsp_product>"
						def fragmentToAdd = new XmlParser().parseText( toadd )
						root.children().add( 0, fragmentToAdd )

					}

				}

				oldSecurePrograms.each{

					String isbn= it
					oldProgramInstanceIsbns.each{

						if(isbn.equals(it)){

							def nodeToDel = root.hsp_product.find{ it.product_isbn.text()==isbn}
							parent = nodeToDel.parent()
							parent.remove(nodeToDel)
						}
					}

				}

				StringWriter stringWriter = new StringWriter()
				XmlNodePrinter nodePrinter = new XmlNodePrinter(new PrintWriter(stringWriter))
				nodePrinter.setPreserveWhitespace(true)
				nodePrinter.print(root)

				def nodePrinter1 =stringWriter.toString()

				def prologAndXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ nodePrinter1

				FileUtils.writeStringToFile(f1, prologAndXml, "UTF-8")


			}
			else{

				def xml = {
					mkp.xmlDeclaration()
					hsp_program(
							buid:programXMLInstance.buid,
							mastery_level:'75',
							title:programXMLInstance.title,
							xmlns:'http://xml.thinkcentral.com/pub/xml/hsp/program',
							'xmlns:xsi':'http://www.w3.org/2001/XMLSchema-instance',
							'xsi:schemaLocation':'http://xml.thinkcentral.com/pub/xml/hsp/program http://xml.thinkcentral.com/pub/xml2_0/hsp_program.xsd'
							) {

								for(int i=0;i<programXMLInstance.secureProgram.size();i++)
								{

									hsp_product{

										product_isbn(lang:programXMLInstance.secureProgram[i].language,programXMLInstance.secureProgram[i].onlineIsbn)
										if(null!=programXMLInstance.secureProgram[i].knewtonProduct && programXMLInstance.secureProgram[i].knewtonProduct)
										{
											KnowledgeGraph{
												warm_up_time_limit(programXMLInstance.secureProgram[i].knowledgeGraphWarmUpTimeLimit)
												indtervention_time_limit(programXMLInstance.secureProgram[i].knowledgeGraphEnrichmentTimeLimit)
												enrichment_time_limit(programXMLInstance.secureProgram[i].knowledgeGraphEnrichmentCbiTimeLimit)
												environmentKGIds{
													certReviewKG(programXMLInstance.secureProgram[i].knowledgeGraphIdDev)
													prodReviewKG(programXMLInstance.secureProgram[i].knowledgeGraphIdQA)
													prodKG(programXMLInstance.secureProgram[i].knowledgeGraphIdProd)
												}
											}
										}
									}

								}

							}
				}
				items.add(programsXMLLocation+"/"+programXMLInstance.filename)
				def writer = new FileWriter(programsXMLLocation+"/"+programXMLInstance.filename)
				writer << builder.bind(xml)
				writer.close()


			}


		}catch(Exception ex){
			ex.getMessage()
			return false
		}

		return true

	}

	/**
	 * Helper method to create Log file Header
	 */
	Logger getLogHeader(Logger customerLogs, def envId, def jobNumber, def user_Name, envName ){
		if(envId==1 || envId==3 || envId==4){
			customerLogs.info"${'*'.multiply(5)} Job Creation ${'*'.multiply(5)}\r\n"
			customerLogs.info("Job " + jobNumber+" was created by user " + user_Name + " for Environment "+envName+"\r\n")
		} else if(envId==2 || envId==5){
			customerLogs.info"${'*'.multiply(5)} Job Promotion ${'*'.multiply(5)}\r\n"
			customerLogs.info("Job "+jobNumber + " was promoted by user " + user_Name + " for Environment "+envName+"\r\n")
		}
		if(customerLogs==null) customerLogs = log
		return customerLogs
	}

	/**
	 * Helper method to return the value of the content's revision
	 * @param contentId
	 * @param jobList
	 * @return
	 */
	def getRevisionNumber(contentId, jobList){
		Long contentIdNumber = contentId.toLong()
		def revNumber = jobList.find{contentIdNumber in it.contentId}.revision
		log.debug "Revision Number:  " +  revNumber
		revNumber
	}

	/**
	 * Initialize logger for each thread
	 * @param programName
	 * @param cacheLocation
	 * @param envId
	 */
	Logger initializeLogger(String programISBN,String cacheLocation, def envId,def contentType) {
		final String workingDir = cacheLocation


		Logger log1 = Logger.getLogger("Thread" + programISBN+"-"+envId)
		Properties props=new Properties()
		props.setProperty("log4j.appender.file","org.apache.log4j.RollingFileAppender")
		props.setProperty("log4j.appender.file.maxFileSize","100MB")
		props.setProperty("log4j.appender.file.maxBackupIndex","100")
		if(envId==1){
			props.setProperty("log4j.appender.file.File",workingDir +"/ProgramXML/"+ programISBN + "/dev/log/"+programISBN+"-"+"dev_log.log")
		}else if(envId==2){

			props.setProperty("log4j.appender.file.File",workingDir +"/ProgramXML/"+ programISBN + "/review/log/"+programISBN+"-"+"review_log.log")

		}else if(envId==3){

			props.setProperty("log4j.appender.file.File",workingDir +"/ProgramXML/"+ programISBN + "/prod/log/"+programISBN+"-"+"prod_log.log")

		}else if(envId==4){
			props.setProperty("log4j.appender.file.File",workingDir +"/ProgramXML/"+ programISBN + "/cert/log/"+programISBN+"-"+"cert_log.log")

		}else if(envId==5){

			props.setProperty("log4j.appender.file.File",workingDir +"/ProgramXML/"+ programISBN + "/Int/log/"+programISBN+"-"+"int_log.log")

		}
		props.setProperty("log4j.appender.file.threshold","info")
		props.setProperty("log4j.appender.file.Append","false")
		props.setProperty("log4j.appender.file.layout","org.apache.log4j.PatternLayout")
		props.setProperty("log4j.appender.file.layout.ConversionPattern","%d - %m%n")
		props.setProperty("log4j.logger."+ "Thread" + programISBN+"-"+envId,"INFO, file")
		PropertyConfigurator.configure(props)
		return log1
	}
	/**
	 * Get the Environment associated with the User
	 * @return
	 */
	def getProgramXMLAudSecurePrograms(def programXmlId){
		def sql = new Sql(dataSource)
		def row = null
		try{
			row = sql.rows( "select sp.secure_program_id  from programxml_secure_program_aud sp where sp.programxml_secure_program_id=?",[programXmlId])


		}catch(Exception e){
			log.error("exception in getProgramXMLSecurePrograms method is: "+e.getMessage())
			log.error("exception in getProgramXMLSecurePrograms method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}

		row

	}



	/**
	 * get ISBNs that have been deleted from another Program XML and associated to this Program XML and return a list of ISBNs that need to be modified
	 * TODO we dont need spId or jobNumber or revisionNumberBeingDeployed
	 * @param instanceId
	 * @param spId
	 * @param revisionNumber
	 * @param envId
	 * @param jobNumber
	 * @return
	 */
	def getModifiedMDSIsbns(def instanceId, def spId, def envId){

		def sql = new Sql(dataSource)
		boolean updateMdsIsbn = false

		// getting a comma separated list of SP IDs
		StringBuffer sb=new StringBuffer()
		spId.each{
			sb.append("'"+it+"',")
		}

		sb.deleteCharAt(sb.length()-1)

		def isbnListToModify = []


		try{
			// Have any of the SP instances been deleted from another Program XML
			//Example: Select * from programxml_secure_program_aud where programxml_secure_program_id <> 24 and secure_program_id IN ('17', '198', '199') and revtype = 2
			def listOfDeletedSecurePrograms = sql.rows("Select * from programxml_secure_program_aud where programxml_secure_program_id <> "+instanceId+" and secure_program_id in ("+sb.toString()+") and revtype =2")

			println "List of deleted SP ${ listOfDeletedSecurePrograms }"

			// if list is not empty
			if(! listOfDeletedSecurePrograms.isEmpty())
			{
				listOfDeletedSecurePrograms.each{

					def revsionOfDeletedSecureProgram = it.rev
					def secureprogramId = it.secure_program_id
					def onlineIsbnToModify = SecureProgram.where{id==secureprogramId}.get()?.onlineIsbn

					// get Job instances for the current Program XML being deployed or promoted
					def jobs = Job.where{contentTypeId==5 && contentId==instanceId}.list()
					
					// get all jobs not including the current job which is in progress
					def previousJobs = jobs.take(jobs.size() - 1)

					// do any jobs have a higher or equal revision than SP ISBN under test
					def jobsMeetingRevisionCriteria =  previousJobs.revision.find{it >= revsionOfDeletedSecureProgram }


					if (jobsMeetingRevisionCriteria){

						println "Checking if any jobs had a successful Promotion to the current environment"
					
						def checkJobWasSuccessful = jobs.promotion.find{it.status[0] == "Success" && it.environmentsId[0] == envId}
						
						if (checkJobWasSuccessful){
							println "Job was previously successful so DO NOT Modify ISBN: ${onlineIsbnToModify}"
						}else{
							println "Job was not deployed successfully so modify ISBN: ${onlineIsbnToModify}"
							isbnListToModify << onlineIsbnToModify
						}

					}else {
						println "Revision Criteria was not met so modify ISBN: : ${onlineIsbnToModify}"
						isbnListToModify << onlineIsbnToModify

					}
				}
			}

			// final list of ISBNs to modify removing any potential null values
			isbnListToModify -= null
			println "ISBNs to Modify" +  isbnListToModify

		}
		catch(Exception e){
			log.error("exception in getProgramXMLID method is: "+e.getMessage())
			log.error("exception in getProgramXMLID method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}

		isbnListToModify
	}




	/**
	 *
	 * @param instanceId
	 * @return
	 *//*
	 def getSecureProgramXMLID(def instanceId,def spId,def revisionNumber,def envId,def jobNumber){
	 def sql = new Sql(dataSource)
	 def isDeletedSP
	 def maxJobNumber
	 def row
	 def currentProgramXmlRev
	 def currentPXPRev
	 boolean updateMdsIsbn=false
	 StringBuffer sb=new StringBuffer()
	 spId.each{
	 sb.append("'"+it+"',")
	 }
	 sb.deleteCharAt(sb.length()-1)
	 try{
	 isDeletedSP = sql.rows("Select * from programxml_secure_program_aud where programxml_secure_program_id <> "+instanceId+" and secure_program_id in ("+sb.toString()+") and revtype =2")
	 if(isDeletedSP!=null)
	 { 
	 spId.each{
	 currentProgramXmlRev = sql.rows("Select * from programxml_secure_program_aud where programxml_secure_program_id = "+instanceId+" and secure_program_id in ("+it.toString()+") and rev<="+revisionNumber+" and revtype!=2")
	 if(!currentProgramXmlRev.isEmpty())
	 {
	 currentProgramXmlRev.each	{
	 maxJobNumber= sql.rows("SELECT * FROM job WHERE content_id ="+instanceId+" and content_type_id = 5 and revision="+it.rev)
	 if(!maxJobNumber.isEmpty())
	 {					
	 maxJobNumber.each{								
	 if(maxJobNumber.size()==1 && it.job_number==jobNumber )	
	 {
	 updateMdsIsbn=true
	 }
	 if(envId==1 )								
	 row= sql.rows("SELECT * FROM promotion where job_number ="+it.job_number+" and environments_id="+envId+" and status='Failed'")		
	 if(envId!=1)
	 row= sql.rows("SELECT * FROM promotion where job_number ="+it.job_number+" and environments_id="+envId+" and status='Failed'")
	 if(!row.isEmpty())									
	 updateMdsIsbn=true
	 }
	 }						
	 }
	 }else{
	 updateMdsIsbn=true
	 }
	 }//spid
	 }
	 }
	 catch(Exception e){
	 log.error("exception in getProgramXMLID method is: "+e.getMessage())
	 log.error("exception in getProgramXMLID method is: "+e.getStackTrace())
	 }
	 finally{
	 sql.close();
	 }
	 updateMdsIsbn
	 }*/
}
