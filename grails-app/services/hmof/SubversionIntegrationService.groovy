package hmof
import grails.transaction.*

import org.tmatesoft.svn.core.SVNDirEntry
import org.tmatesoft.svn.core.SVNException
import org.tmatesoft.svn.core.SVNNodeKind
import org.tmatesoft.svn.core.SVNURL
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager
import org.tmatesoft.svn.core.io.SVNRepository
import org.tmatesoft.svn.core.wc.SVNWCUtil
import org.tmatesoft.svn.core.wc2.SvnCheckout
import org.tmatesoft.svn.core.wc2.SvnExport
import org.tmatesoft.svn.core.wc2.SvnGetInfo
import org.tmatesoft.svn.core.wc2.SvnInfo
import org.tmatesoft.svn.core.wc2.SvnOperationFactory
import org.tmatesoft.svn.core.wc2.SvnTarget
import org.tmatesoft.svn.core.SVNDepth
import org.tmatesoft.svn.core.wc.SVNRevision
import org.tmatesoft.svn.core.io.SVNRepositoryFactory

/**
 * SubversionIntegrationService
 * A service class encapsulates the core business logic of a Grails application
 */
@Transactional
class SubversionIntegrationService {

	/**
	 * Helper method to create an SVN Operation Factory	 
	 */
	def createSvnOperationFactory() {

		log.info "Creating an SVN Client"

		def username = "admin"
		def password = "admin"
		SvnOperationFactory svnOperationFactory = new SvnOperationFactory()
		ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager(username, password)
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
			productToExport.properties=[status: JobStatus.FAILED]

		}finally{

			cleanupSvnOperationFactory(svnClient)

		}

	}

	/**
	 * Export SVN file to local cache
	 * @param productToExport
	 * @param localCacheLocation
	 * @return
	 */
	def exportSvnContent( def productToExport, localCacheLocation ){

		log.info "Exporting SVN Content..."
		def svnClient =createSvnOperationFactory()		
		log.info "local cache loaction: ${localCacheLocation}"

		try
		{
			SvnExport export = svnClient.createExport()
			SVNURL svnRepositoryURL = SVNURL.parseURIEncoded(productToExport.svnUrl)


			def localCache =  new File(localCacheLocation)
			// make local directories
			localCache.mkdirs()

			export.setSource((SvnTarget.fromURL(svnRepositoryURL)))
			export.setSingleTarget(SvnTarget.fromFile(localCache))

			// Alternative SVNDepth.IMMEDIATES OR SVNDepth.FILES
			export.setDepth(SVNDepth.FILES)
			export.setRevision(SVNRevision.HEAD)
			
			export.setForce(true)
			export.run()

		}
		catch (Exception e)
		{
			log.error " SVN Error when checking out content:  ${e}"			
			productToExport.properties=[status: JobStatus.FAILED]

		}finally{

			cleanupSvnOperationFactory(svnClient)

		}

	}


	/**
	 * Helper method to get the SVN revision number and check if SVN is available
	 * @param svnUrl
	 * @return
	 */
	def getSvnInfo(def productToExport){

		Boolean available = false
		Long svnRevision
		def svnClient = createSvnOperationFactory()

		try{

			SVNURL svnRepositoryURL = SVNURL.parseURIEncoded(productToExport.svnUrl)

			SvnGetInfo getInfo = svnClient.createGetInfo()
			getInfo.setSingleTarget(SvnTarget.fromURL(svnRepositoryURL))
			SvnInfo info = getInfo.run()

			available = true
			svnRevision = info.getLastChangedRevision()

			log.info "LastChangedRevision: " + svnRevision

		}catch(SVNException e){

			log.error "SVN Issue: ${e}"
			productToExport.properties=[status: JobStatus.FAILED]			

		}finally{

			cleanupSvnOperationFactory(svnClient)

		}

		def (Long revision, Boolean svnAvailable) = [svnRevision, available]

	}
}
