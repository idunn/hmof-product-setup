package hmof

import java.util.List;
import hmof.deploy.*
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import grails.transaction.Transactional
@Transactional(readOnly = true)
class SapStatusJob {
	def concurrent = true
	def group = "scheduled"	
	def UtilityService
	private  static Log logger = LogFactory.getLog(SapStatusJob.class);
	static triggers = {
		cron name: 'cronTrigger2', cronExpression: "0 0 1 * * ?"	//  Schedule for everynight at 2:00am
		
	}

	@Transactional
	def execute() {
		def bundleListISBN = Bundle.list().isbn
		InetAddress address = InetAddress.getByName("172.17.101.75")
		boolean reachable = address.isReachable(5000)
		
	if(reachable){
		logger.info("SAP service Reachable")
		def sapResultsMap= UtilityService.getIsbnRecords(bundleListISBN)	

	
		if(sapResultsMap!=null && !sapResultsMap.isEmpty()){
		sapResultsMap.each {			
			String isbnId = it.isbn13	
			
			def bundleInstance = Bundle.where{isbn==isbnId}.get()
			def sapInstance = Sap.where{bundle.id==bundleInstance.id}.get()
			
					
			
			if(sapInstance!=null ){
				
			bundleInstance.sap.isbn = bundleInstance.isbn
            bundleInstance.sap.internalDescription = it.internalDescription
				bundleInstance.sap.materialGroup = it.materialGroup
				bundleInstance.sap.eGoodsIndicator = it.eGoodsIndicator
			bundleInstance.sap.status = it.status			
			bundleInstance.save(flush: true,failOnError:true)
			}else
		   { 
  			 
			 bundleInstance.sap = new Sap(isbn: isbnId,internalDescription:it.internalDescription,materialGroup:it.materialGroup,eGoodsIndicator:it.eGoodsIndicator,bundle: bundleInstance, status:it.status)	
			  bundleInstance.save(flush: true,failOnError:true)
              
	    	}
		}	
		}
	}else
{
	logger.error("SAP service Not Reachable")
}

	}
}
