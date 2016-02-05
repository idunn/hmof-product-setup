package hmof
import grails.transaction.*

import org.apache.log4j.Logger
import org.tmatesoft.svn.core.SVNDirEntry
import org.tmatesoft.svn.core.SVNException
import org.tmatesoft.svn.core.SVNNodeKind
import org.tmatesoft.svn.core.SVNURL
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager
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
import org.tmatesoft.svn.core.wc.SVNStatus
import grails.util.Holders


/**
 * SubversionIntegrationService
 * A service class encapsulates the core business logic of a Grails application
 */
@Transactional
class SubversionIntegrationService {
	/**
	 * Helper method to create an SVN Operation Factory
	 */

	/**
	 * Helper method to create an SVN Operation Factory	 
	 */
	def createSvnOperationFactory() {

		log.info "Creating an SVN Client"
		
		def username = Holders.config.svn.username
		def password = Holders.config.svn.password
		SvnOperationFactory svnOperationFactory = new SvnOperationFactory()

		//ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager(username, password)
		
		def authenticationManager = new BasicAuthenticationManager(username, password)
		println authenticationManager.getClass()

		svnOperationFactory.setAuthenticationManager(authenticationManager)

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
	Boolean commitSvnContent( def localFilePath, Logger log ) {

		def svnClient = createSvnOperationFactory()
		String workingCopy = Holders.config.programXMLFolder


		try{


			def localCache =  new File(localFilePath)

			log.info "Updating programXML Working Copy"

			File workingCopyPath = new File(workingCopy)

			// Run SVN Update ON Working Copy
			SvnUpdate update = svnClient.createUpdate()
			update.setSingleTarget(SvnTarget.fromFile(workingCopyPath))
			update.setRevision(SVNRevision.HEAD);

			update.run()

			log.info "Working Copy is Updated!"

			def isExistfile = doesFileExist(localFilePath)
			if(!isExistfile){
				log.info "Adding new programXML to SVN Working Copy"

				final SvnScheduleForAddition scheduleForAddition = svnClient.createScheduleForAddition();
				scheduleForAddition.setSingleTarget(SvnTarget.fromFile(localCache));
				scheduleForAddition.run()
				log.info "SVN Add Action"
			}

			log.info "SVN Commit Action"

			SvnCommit commit = svnClient.createCommit()
			commit.addTarget(SvnTarget.fromFile(localCache))
			commit.setCommitMessage("TT-1234: Adding Program XML to SVN using the HMOF Product Setup App");
			commit.run()
			log.info "ProgramXML has been committed to the MDS Content Repository"




		}catch (Exception e)
		{
			log.error " SVN Error:  ${e}"

			return false

		}finally{
			cleanupSvnOperationFactory(svnClient)
		}

		return true
	}


	public static boolean doesFileExist(String targetPath) throws SVNException {
		def username = Holders.config.svn.username
		def password = Holders.config.svn.password
		SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded("http://dubsvn.hmco.com/svn/MDS_Content/trunk/MDS/CERT-REVIEW/program"));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
		repository.setAuthenticationManager(authManager);

		Collection entries = repository.getDir( "", -1 , null , (Collection) null );
		Iterator iterator = entries.iterator( );
		def localCache =  new File(targetPath)
		String name=localCache.getName()
		while ( iterator.hasNext( ) ) {
			SVNDirEntry entry = ( SVNDirEntry ) iterator.next( );
			if(name.equals(entry.getName()))
			{
				return true;
			}
		}

		return false;
	}


}
