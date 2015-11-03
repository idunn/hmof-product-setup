package hmof

import org.apache.log4j.Logger;

import grails.transaction.Transactional
import hmof.programxml.ProgramXML
import grails.util.Holders
import hmof.deploy.*
import hmof.security.*
import hmof.*


import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator


@Transactional
class ProgramXMLService {

	static transactional = false
	def deploymentService
	def commerceObjectService
	def utilityService
	def springSecurityService


	Logger log = Logger.getLogger(JobService.class)

	/**
	 * Take the jobs and process them by pushing the content to the environment via Geb
	 * @param jobs
	 * @return
	 */
	Boolean processJobs(def jobs, def promotionInstance) {
		
		
		String programXMLIsbn=""
		Logger customerLog=null

		def bundlesToRemove = []

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
			// used only to initialize logs
			if (!programXML.isEmpty() ){

				programXML.each{

					Long instanceNumber = it.contentId
					Long revisionNumber = it.revision
					
					Long jobNumber = it.jobNumber
					def programXMLInstance = programXML.where{id==instanceNumber}.get()?: utilityService.getDeletedObject(instanceNumber, revisionNumber, 5)
					programXMLIsbn=programXMLInstance.isbn

					//customerLog = initializeLogger(programXMLIsbn, cacheLocation,envId,2)
				//	customerLog=getLogHeader(customerLog, envId, jobNumber, user_Name, envName )
				}
			} // end bundle log initializer
			

			log.debug "cacheLocation" + cacheLocation
			log.debug "The deployment Url is: " + deploymentUrl

	
			} 
		   catch(InterruptedException  e){

			log.error "Exception deploying content: " + e
			return false

		}
		catch(Exception e){

			log.error "Exception deploying content: " + e
			return false

		}

		return true
	}
	
	def generateProramXML(ProgramXML programXMLInstance)
	{
		def builder = new groovy.xml.StreamingMarkupBuilder()
		builder.encoding = 'UTF-8'
		StringBuilder sb = new StringBuilder();
		ArrayList<String> items = new ArrayList<String>()
		
     try{
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
  def programsXMLLocation = Holders.config.programXMLFolder
			
  File f = new File(programsXMLLocation);
  f.mkdir();
 

  items.add(programsXMLLocation+"/"+programXMLInstance.filename)
  def writer = new FileWriter(programsXMLLocation+"/"+programXMLInstance.filename)
  writer << builder.bind(xml)
  writer.close()
   }catch(Exception ex){
	  ex.getMessage()
	  return false
  }

   return true
   
	}
}
