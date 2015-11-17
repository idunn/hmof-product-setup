package hmof
import hmof.deploy.*


import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator

import grails.util.Holders
import groovy.xml.StreamingMarkupBuilder
import hmof.programxml.ProgramXML
import hmof.security.*
import org.apache.commons.io.FileUtils;


class ProgramXmlService {

	static transactional = false
	def deploymentService
	def commerceObjectService
	def utilityService
	def springSecurityService
	def subversionIntegrationService
	Logger log = Logger.getLogger(JobService.class)
	
	/**
	 * Deploy or Promote Jobs in Pending or Pending_Repromote Status
	 * @return
	 */
	def executeJob(){

		
		def promotionProgramXMlJobInstance = Promotion.where{status == JobStatus.PendingProgramDeploy.getStatus()  }.list(max:1)
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
		

		try{
			promotionInstance.refresh()
			// Get the environment URL
			def environmentInstance = Environment.where{id==promotionInstance.environmentsId}.get()
			def envId = environmentInstance.id
			def deploymentUrl = environmentInstance.url
			def envName = environmentInstance.name
			def user_Name = User.where{id == promotionInstance.userId}.username.get()
		
			// Divide out the instances
			def programXML = jobs.findAll{it.contentTypeId == 5}
			def cacheLocation = Holders.config.cacheLocation
						
			def localURL= Holders.config.programXMLFolder
			
			// used only to initialize logs
			if (!programXML.isEmpty() ){

				programXML.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision
					
					Long jobNumber = it.jobNumber
					def programXMLInstance = ProgramXML.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 5)
				
					def path=localURL+programXMLInstance.filename
					
					customerLog = initializeLogger(programXMLInstance.buid, cacheLocation,envId,5)
				    customerLog=getLogHeader(customerLog, envId, jobNumber, user_Name, envName )
					commitJobs =subversionIntegrationService.commitSvnContent(path,customerLog)
					if(commitJobs)
					{
						customerLog.info "${'*'.multiply(5)} Finished Deploying Program XML ${'*'.multiply(5)}\r\n"						
						customerLog.info"${'*'.multiply(5)} Status ${'*'.multiply(5)}\r\n"
						customerLog.info("Job Status: Success\r\n")
	
					}else
			    	{
						customerLog.info"${'*'.multiply(5)} Status ${'*'.multiply(5)}\r\n"
						customerLog.info("Job Status: Failed\r\n")
				    }
				}
			} // end bundle log initializer
			

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
	
	def generateProramXML(ProgramXML programXMLInstance)
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
			 

			 
			 
			 
			 def newSecurePrograms=[]
			 def oldSecurePrograms=[]
			 
			
			if(programXMLInstance.secureProgram){
				newSecurePrograms =SecureProgram.where{id in (programXMLInstance.secureProgram.id)}.list()
			   }
			
			
			 XmlParser parser = new XmlParser()
			 
			  def xmldata1 = parser.parse(new File(programsXMLLocation+programXMLInstance.filename))
			  
			   xmldata1.hsp_product.each{
				   oldSecurePrograms.add(it.product_isbn.text())
			   }
			   
		   def addISBNS=[]
			   newSecurePrograms.onlineIsbn.each{
				   if(!oldSecurePrograms.contains(it))
				   {
					   addISBNS.add(it)
				   }
				   
			   }
			  
			  
			   
			   def root = new XmlParser().parse( f1 )
			   
			   addISBNS.each{
			   def toadd = "<hsp_product><product_isbn lang='en_US'>"+it+"</product_isbn></hsp_product>"
			   
		def fragmentToAdd = new XmlParser().parseText( toadd )
	
	// Insert this new node at position 0 in the children of the first coreEntry node
	root.children().add( 0, fragmentToAdd )
	
			   }
			   def outxml ={ groovy.xml.XmlUtil.serialize( root )}
			
			   def stringWriter = new StringWriter()
			   def indentPrinter = new IndentPrinter(stringWriter, "", false)
			   def nodePrinter = new XmlNodePrinter(indentPrinter).print(root)
			   File outputFile = (new File(programsXMLLocation+programXMLInstance.filename))
			   outputFile.write(stringWriter.toString(), "UTF-8")
			   
					 
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
		 
	   hsp_product{product_isbn(lang:programXMLInstance.language,programXMLInstance.secureProgram[i].onlineIsbn)}
		 
	 }
		
	 }
  }
  
  

   
// rootNode.children().each { assert it.name() in ['one','two'] }

 

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
			
		}else if(envId==5){
			
				props.setProperty("log4j.appender.file.File",workingDir +"/ProgramXML/"+ programISBN + "/prod/log/"+programISBN+"-"+"prod_log.log")
			
		}else if(envId==3){			
				props.setProperty("log4j.appender.file.File",workingDir +"/ProgramXML/"+ programISBN + "/cert/log/"+programISBN+"-"+"cert_log.log")
			
		}else if(envId==4){
			
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

	

}
