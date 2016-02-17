package hmof
import grails.transaction.*

import org.apache.log4j.Logger
import org.tmatesoft.svn.core.SVNDirEntry
import org.tmatesoft.svn.core.SVNException
import org.tmatesoft.svn.core.SVNNodeKind
import org.tmatesoft.svn.core.SVNProperties
import org.tmatesoft.svn.core.SVNPropertyValue
import org.tmatesoft.svn.core.SVNURL
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager
import org.tmatesoft.svn.core.io.ISVNEditor
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
import org.tmatesoft.svn.core.wc2.SvnSetProperty
import org.tmatesoft.svn.core.wc2.SvnTarget
import org.tmatesoft.svn.core.wc2.SvnUpdate
import org.tmatesoft.svn.core.wc2.SvnSetProperty
import org.tmatesoft.svn.core.SVNDepth
import org.tmatesoft.svn.core.wc.SVNRevision
import org.tmatesoft.svn.core.io.SVNRepositoryFactory
import org.tmatesoft.svn.core.wc.SVNStatus
import grails.util.Holders
import hmof.programxml.ProgramXML
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.TrueFileFilter

/**
 * SubversionIntegrationService
 * A service class encapsulates the core business logic of a Grails application
 */
@Transactional
class SubversionIntegrationService {

	def compareDomainInstanceService

	/**
	 * Helper method to create an SVN Operation Factory	 
	 */
	def createSvnOperationFactory() {

		log.info "Creating an SVN Client"

		def username = Holders.config.svn.username
		def password = Holders.config.svn.password
		SvnOperationFactory svnOperationFactory = new SvnOperationFactory()

		def authenticationManager = new BasicAuthenticationManager(username, password)

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
	 * Commit Files to SVN
	 * @param localFilePath
	 * @param log
	 * @param programXMLInstance
	 * @param updateMDSISBN
	 * @return
	 */
	Boolean commitSvnContent( def localFilePath, Logger log, ProgramXML programXMLInstance, boolean updateMDSISBN ) {

		def svnClient = createSvnOperationFactory()
		String workingCopy = Holders.config.programXMLFolder


		try{


			def localCache =  new File(localFilePath)

			log.info "Updating programXML Working Copy: ${workingCopy}"

			File workingCopyPath = new File(workingCopy)

			// Run SVN Update ON Working Copy
			SvnUpdate update = svnClient.createUpdate()
			update.setSingleTarget(SvnTarget.fromFile(workingCopyPath))
			update.setRevision(SVNRevision.HEAD)

			update.run()

			log.info "Working Copy is Updated!"

			def isExistfile = doesFileExist(localFilePath)
			if(!isExistfile){
				log.info "Adding new programXML to SVN Working Copy"

				final SvnScheduleForAddition scheduleForAddition = svnClient.createScheduleForAddition()
				scheduleForAddition.setSingleTarget(SvnTarget.fromFile(localCache))
				scheduleForAddition.run()

			}

			log.info "SVN Commit Action"

			SvnCommit commit = svnClient.createCommit()
			commit.addTarget(SvnTarget.fromFile(localCache))
			commit.setCommitMessage("TT-1234: Adding Program XML to SVN using the HMOF Product Setup WebApp")
			commit.run()


			log.info "Program XML has been addded to the Working Copy"
			log.info "ProgramXML has been committed to the MDS Content Repository"


			if(updateMDSISBN)
				updateIsbnFileRevision(programXMLInstance,log,svnClient)


		}catch (Exception e)
		{
			log.error " SVN Error:  ${e}"

			return false

		}finally{
			cleanupSvnOperationFactory(svnClient)
		}

		return true
	}


	/**
	 * Check if the File exists in SVN
	 * @param targetPath
	 * @return
	 * @throws SVNException
	 */
	public static boolean doesFileExist(String targetPath) throws SVNException {

		def username = Holders.config.svn.username
		def password = Holders.config.svn.password

		SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded( Holders.config.svn.url))
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password)
		repository.setAuthenticationManager(authManager)

		Collection entries = repository.getDir( "", -1 , null , (Collection) null )
		Iterator iterator = entries.iterator( )
		def localCache =  new File(targetPath)
		String name=localCache.getName()
		while ( iterator.hasNext( ) ) {
			SVNDirEntry entry = ( SVNDirEntry ) iterator.next( )

			if(name.equals(entry.getName()))
			{
				println entry.getRevision()
				return true
			}
		}

		return false
	}


	/**
	 * Take the files to be modified and set a property to force the revision change in SVN
	 * @param fileToModify
	 * @return
	 */
	def setSvnProperty(def fileToModify){

		//TODO consider try/catch

		log.info"File to be modified: ${fileToModify}"

		def svnClient = createSvnOperationFactory()

		SvnSetProperty changeProperty = svnClient.createSetProperty()
		changeProperty.setSingleTarget(SvnTarget.fromFile(fileToModify))
		changeProperty.setPropertyName("test")
		changeProperty.setPropertyValue(SVNPropertyValue.create("CUST_DEV"))

		changeProperty.run()

		cleanupSvnOperationFactory(svnClient)

	}

	/**
	 * Update Working Copy
	 * @param fileToUpdate
	 * @return
	 */
	def doSvnUpdate(def fileToUpdate){

		def svnClient = createSvnOperationFactory()

		SvnUpdate update = svnClient.createUpdate()
		update.setSingleTarget(SvnTarget.fromFile(fileToUpdate))
		update.setRevision(SVNRevision.HEAD)

		update.run()

		cleanupSvnOperationFactory(svnClient)

	}

	/**
	 * Commit the Working directory to SVN
	 * @override
	 * @return
	 */
	Boolean commitSvnContent(def workingCopy){

		def svnClient = createSvnOperationFactory()
		log.info "Commit Working Copy"

		SvnCommit commit = svnClient.createCommit()
		log.info "commiting File: ${workingCopy}"
		commit.addTarget(SvnTarget.fromFile(workingCopy))
		commit.setCommitMessage("TT-1234: Committing MDS_ISBN to SVN using the HMOF Product-Setup App")
		commit.run()

		cleanupSvnOperationFactory(svnClient)


	}


	/**
	 * Force an update to MDS ISBNs as part of a significant change to Program XML
	 * @param programXMLInstance
	 * @param log
	 * @param svnClient
	 * @return
	 * @throws SVNException
	 */
	//public static boolean updateIsbnFileRevision( ProgramXML programXMLInstance, Logger log, svnClient) throws SVNException {
	def updateIsbnFileRevision( ProgramXML programXMLInstance, Logger log, svnClient) throws SVNException {

		String workingCopy = Holders.config.programXMLISBNsFolder

		try{

			log.info "Updating MDS_ISBNs Working Copy: ${workingCopy}"
			File workingCopyPath = new File(workingCopy)
			doSvnUpdate(workingCopyPath)
			log.info "Working Copy is Updated!"

			def secureProgramList = []
			if(programXMLInstance.secureProgram){
				secureProgramList = SecureProgram.where{id in (programXMLInstance.secureProgram.id)}.list()
			}

			def mdsISBN = secureProgramList.onlineIsbn
			log.info"MDS ISBNs: ${mdsISBN}"

			def mdsIsbnFile = []
			mdsISBN.each{ isbn -> mdsIsbnFile<< "mds_resources_${isbn}.xml" }
			log.info "${mdsIsbnFile}"

			log.info("Getting all XML file...")
			String[] xmlExtensions = [ "xml" ]
			def listOfXmlFiles = FileUtils.listFiles(workingCopyPath, xmlExtensions, true)

			log.info "Searching for the MDS ISBNs that are required to be updated..."
			def mdsIsbnFilesFound = []

			mdsIsbnFile.each{

				def theFileToFind = it
				log.info "Looking for $theFileToFind"

				listOfXmlFiles.each{

					def theFilesToSearch = it

					if (theFileToFind == theFilesToSearch.getName()){

						log.info "found ${theFilesToSearch}"
						mdsIsbnFilesFound << theFilesToSearch

					}
				}
			}

			log.info "Found the following files ${mdsIsbnFilesFound}"

			// modify files found
			mdsIsbnFilesFound.each{

				def localFile = it
				// set property on file in working copy
				setSvnProperty(localFile)

			}

			// call commit
			log.info "Committing Change"
			commitSvnContent(workingCopyPath)
			log.info "Content committed!"

			return true

			log.info "MDS ISBN XMLs has been committed to the MDS Content Repository"
		}catch (Exception e)
		{
			log.error "Issue in updateIsbnFileRevision:  ${e}"

			return false

		}

		return false
	}

}
