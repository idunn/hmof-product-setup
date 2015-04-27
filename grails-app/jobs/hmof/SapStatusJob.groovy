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
			String isbnId = it.key		
			def sapInstance = Sap.where{isbn==isbnId}.get()
			def bundleInstance = Bundle.where{isbn==isbnId}.get()
			if(bundleInstance!=null && sapInstance!=null){
			
			bundleInstance.sap.status = it.value			
			bundleInstance.save(flush: true)
			}else
		   {
			   bundleInstance.sap = new Sap(isbn: isbnId,bundle: bundleInstance, status:it.value)
			   bundleInstance.save(flush: true)
	    	}
		}	
		}
	}else
{
	logger.error("SAP service Not Reachable")
}

	}
}
