package hmof
import grails.transaction.*

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNDirEntry
import org.tmatesoft.svn.core.SVNException
import org.tmatesoft.svn.core.SVNNodeKind
import org.tmatesoft.svn.core.SVNURL
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager
import org.tmatesoft.svn.core.io.SVNRepository
import org.tmatesoft.svn.core.wc.SVNClientManager
import org.tmatesoft.svn.core.wc.SVNStatusType
import org.tmatesoft.svn.core.wc.SVNUpdateClient
import org.tmatesoft.svn.core.wc.SVNWCUtil
import org.tmatesoft.svn.core.wc2.SvnCheckout
import org.tmatesoft.svn.core.wc2.SvnCommit
import org.tmatesoft.svn.core.wc2.SvnExport
import org.tmatesoft.svn.core.wc2.SvnGetInfo
import org.tmatesoft.svn.core.wc2.SvnInfo
import org.tmatesoft.svn.core.wc2.SvnOperationFactory
import org.tmatesoft.svn.core.wc2.SvnScheduleForAddition
import org.tmatesoft.svn.core.wc2.SvnTarget
import org.tmatesoft.svn.core.wc2.SvnUpdate
import org.tmatesoft.svn.core.SVNDepth
import org.tmatesoft.svn.core.wc.SVNRevision
import org.tmatesoft.svn.core.io.SVNRepositoryFactory
import org.tmatesoft.svn.core.wc.SVNStatus;


/**
 * SubversionIntegrationService
 * A service class encapsulates the core business logic of a Grails application
 */
@Transactional
class SubversionIntegrationService {
	//Logger log = Logger.getLogger(SubversionIntegrationService.class)
	/**
	 * Helper method to create an SVN Operation Factory	 
	 */
	def createSvnOperationFactory() {

		log.info "Creating an SVN Client"
		//SVNURL url = SVNURL.parseURIEncoded( "http://172.17.1.17/svn/tck6content/data/content/tools/common/customdev/build/static/MDS/CERT-REVIEW/program/hmof/" );
		def username = "admin"
		def password = "admin"
		
		

		SvnOperationFactory svnOperationFactory = new SvnOperationFactory()
		ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager(username, password)
		//svnOperationFactory.createRepositoryCreate(SVNURL.parseURIDecoded(url))
		//svnOperationFactory.create();
		svnOperationFactory.setAuthenticationManager(authenticationManager)
		
		//final SVNRepository svnRepository = svnOperationFactory.getRepositoryPool().createRepository(url, true);
		
     return svnOperationFactory
	}

	/**
	 * Disposing context and repository pool if needed
	 * @param svnFactory
	 * @return
	 */
	def cleanupSvnOperationFactory(def svnFactory){
		log.info "SVN Cleanup: Disposing of context and repository pool"
		svnFactory.dispose()
	}
	/**
	 * Checkout SVN file to local cache
	 * @param productToExport
	 * @param localCacheLocation
	 * @return
	 */
	Boolean commitSvnContent( def localFilePath,Logger log ) {
	
		def svnClient = createSvnOperationFactory()


		try{

			def localCache =  new File(localFilePath)	
			 SVNURL svnRepositoryURL = SVNURL.parseURIEncoded("http://172.17.1.17/svn/tck6content/data/content/tools/common/customdev/build/static/MDS/CERT-REVIEW/program/hmof")
		
			
	/*		 SvnUpdate update = svnClient.createUpdate();
			//update.setSource(SvnTarget.fromURL(svnRepositoryURL))
			update.setSingleTarget(SvnTarget.fromURL(svnRepositoryURL))
		
			// Alternative SVNDepth.IMMEDIATES OR SVNDepth.FILES
			update.setDepth(SVNDepth.FILES)
			update.setRevision(SVNRevision.HEAD)		
			update.run()*/		
			 log.info "Updating program XML contained SVN Content"
			 SVNRepository repository = SVNRepositoryFactory.create(svnRepositoryURL);
			 File dstPath = new File("C:/ProductSetup-cache/ProgramXML/hmof"); 			 
			 SVNClientManager cm = SVNClientManager.newInstance();
			 SVNUpdateClient uc = cm.getUpdateClient();  	
		
		    	uc.doUpdate(dstPath, SVNRevision.UNDEFINED, SVNDepth.INFINITY, true, false)

			
			
			
			 log.info "Updated program XML to SVN Content"
			 log.info "Adding program XML to SVN Content"
			// SVNRevision.WORKING, SVNDepth.INFINITY,
			// println SVNRevision.getScheduleForAddition()
		     final SvnScheduleForAddition scheduleForAddition = svnClient.createScheduleForAddition();
            scheduleForAddition.setSingleTarget(SvnTarget.fromFile(localCache));
            scheduleForAddition.run()			
			log.info "Added program XML to SVN Content"
			log.info "Committing program XML to SVN Content"
			SvnCommit commit = svnClient.createCommit();
			commit.addTarget(SvnTarget.fromFile(localCache));
			commit.setCommitMessage("TT-1234: SVN commit through HMOF Product Setup app");
			commit.run()
			log.info "Committed program XML to SVN Content"
			

		}catch (Exception e)
		{
			log.error " SVN Error when Add and Commit to content:  ${e}"
			
			return false
		}finally{
			cleanupSvnOperationFactory(svnClient)
		}
    return true
	}
	/**
	 * Checkout SVN file to local cache	
	 * @param productToExport
	 * @param localCacheLocation
	 * @return
	 */
	void checkoutSvnContent( def productToExport, def localCacheLocation ){

		def svnClient = createSvnOperationFactory()

		try
		{

			log.info "Checking out SVN Content..."

			SvnCheckout checkout = svnClient.createCheckout()
			SVNURL svnRepositoryURL = SVNURL.parseURIEncoded(productToExport.svnUrl)

			def localCache =  new File(localCacheLocation)

			// WC directories are made automatically //localCache.mkdirs()

			checkout.setSource((SvnTarget.fromURL(svnRepositoryURL)))
			checkout.setSingleTarget(SvnTarget.fromFile(localCache))

			// Alternative SVNDepth.IMMEDIATES OR SVNDepth.FILES
			checkout.setDepth(SVNDepth.FILES)
			checkout.setRevision(SVNRevision.HEAD)

			checkout.run()


		}catch (Exception e)
		{
			log.error " SVN Error when checking out content:  ${e}"			
			productToExport.properties=[status: JobStatus.Failed.getStatus()]

		}finally{

			cleanupSvnOperationFactory(svnClient)

		}

	}




}