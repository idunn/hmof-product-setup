package hmof

import grails.transaction.Transactional
import hmof.programxml.ProgramXML
import grails.util.Holders
import hmof.deploy.*
import hmof.security.*
import hmof.*
@Transactional
class ProgramXMLService {

  	
	
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
