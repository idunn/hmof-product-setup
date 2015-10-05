package hmof.security

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import hmof.deploy.*

/**
 * UserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class UserController {

	def springSecurityService
	def utilityService
	//  Allows us retrieve configuration information from config.xml
	def grailsApplication
	
	def userService
	
     static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 25, 100)	
		
		
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }
	
	//  This is so that we can display a ; delimited list of user's email addresses that can be used for email notification alerts
	def listForEmail() {
		
		def allActiveUsers = User.findAllWhere(enabled: true, accountExpired: false, accountLocked:false)
		[view:"listForEmail", userInstanceList: allActiveUsers, userInstanceTotal: allActiveUsers.size()]
	}
	
	def lockoutAllUsers() {
		//  false to disable.  true to enable
		userService.disableOrEnableAllUsers(false)
		userService.expireSessions()
		redirect(action: "list", params: params)
	}
	
	def removeLockoutOnAllUsers() {
		//  false to disable.  true to enable
		userService.disableOrEnableAllUsers(true)
		userService.expireSessions()
		redirect(action: "list", params: params)
	}

    def create() {
		//  Trim the username
		if(params.username)
			params.username = params.username.trim()
		
		//  Trim the password
		if(params.password)
			params.password = params.password.trim()
			def allEnvironments=Environment.list()
        [userInstance: new User(params), authorityList: Role.list(params),allEnvironments:allEnvironments]
    }

    def save() {
		//  Trim the username
		if(params.username)
			params.username = params.username.trim()
		
		//  Trim the password
		if(params.password)
			params.password = params.password.trim()
			
        def userInstance = new User(params)
		if (!userInstance.save(flush: true)) {
			render(view: "create", model: [userInstance: userInstance])
			return
		}
		
		addRoles(userInstance, params)
		
/*		//getEnvironments
		def envId
		List<String> arr = new ArrayList<String>()
		for (String key in params.keySet()) {
		
			if(key.contains('ROLE') && 'on' == params.get(key)){
			log.debug key
			
			arr.add(key);
			
			}
		}
		

if(arr.contains("ROLE_PM")){			
envId=Environment.where{name in ['Development / Cert_Review','Certification / Cert','Int - Int']}.list()
}else if(arr.contains("ROLE_QA"))
{
envId=Environment.where{name=="QA / Prod_Review"}.get()
}
else if(arr.contains("ROLE_PROD"))
{
envId=Environment.where{name=="Production / Prod"}.get()
}
	*/
		
		//  There is an issue here that the current list of environments that the user is entitled to are not being cleared so we clear them down here first and set them correctly
		if(params.environmentsIds)
			userInstance.setEnvironmentsIds(params.environmentsIds)
		else
			userInstance.setEnvironmentsIds('')
		
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance, authorityList: Role.list(params)])
            return
        }
	
		
		flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

	protected void addRoles(userInstance, params) {

		// Clear the user's current roles
		UserRole.removeAll(userInstance)

		for (String key in params.keySet()) {	
		
			if(key.contains('ROLE') && params.get(key).contains('on')){
			
				//  Note this needs to use the RoleConstants classes
				UserRole.create userInstance, Role.findByAuthority(key), true
			}	
		}
	}
	protected void deleteRoles(userInstance) {
		
				// Clear the user's current roles
				 UserRole.removeAll(userInstance)
                 utilityService.deleteUserEnvironmentAsso(userInstance)
					
						
				
			}
    def show() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit() {

        def userInstance = User.get(params.id)
		
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance, authorityList: Role.list(params)]
    }

    def update() {
		log.debug "Updating a user"
		
		//  Trim the username
		if(params.username)
			params.username = params.username.trim()
		
		//  Trim the password
		if(params.password)
			params.password = params.password.trim()
		
        def userInstance = User.get(params.id)

        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }
		addRoles(userInstance, params)
		userInstance.refresh()
	
		/*//getEnvironments
		def envId
		List<String> arr = new ArrayList<String>()
		for (String key in params.keySet()) {
		
			if(key.contains('ROLE') && 'on' == params.get(key)){
			log.debug key
			
			arr.add(key);
			
			}
		}
		

		if(arr.contains("ROLE_PM")){
			
envId=Environment.where{name in ['Development / Cert_Review','Certification / Cert','Int - Int']}.list()

}else if(arr.contains("ROLE_QA"))
{
envId=Environment.where{name=="QA / Prod_Review"}.get()
}
else if(arr.contains("ROLE_PROD"))
{
envId=Environment.where{name=="Production / Prod"}.get()
}*/
	
		//log.debug params.environmentsIds
		//  There is an issue here that the current list of environments that the user is entitled to are not being cleared so we clear them down here first and set them correctly
		if(params.environmentsIds)
			userInstance.setEnvironmentsIds(params.environmentsIds)
		else 
			userInstance.setEnvironmentsIds('')
	
		if (!userInstance.save(flush: true)) {
			render(view: "edit", model: [userInstance: userInstance])
			return
		}
		
		userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }
	
		
	
		flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def delete() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        try {
			deleteRoles(userInstance)
            userInstance.delete(flush: true)

			flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	
	
	
	/**
	 * Handles a user search.  The input could be user number, username or email.  A search by email could find multiple users, if it does the list view is rendered otherwise the show view is rendered.
	 *
	 */
	def search() {
		
		//  Try for a specific Id
		def userInstance

		if(params.query && params.query.isNumber())
			userInstance = User.get(params.query)
			
		//  Trim the query
		params.query = params.query.toString().trim()
		
		def userInstanceList
		if(params.query && !userInstance)
		{
			//  None found so try to find by email or username	(Ilike instead of Like so its case insensitive)		
			userInstanceList = User.findAllByEmailIlikeOrUsernameIlike(params.query, params.query)
		}
		
		//  Show specific list view if we find more than one result
		if(userInstanceList && userInstanceList.size() > 1)
		{
			
			//  Paginate
			int offset = 0
			int max = 10
			if(params.offset && params.max)
			{
				//  Paginate
				offset = params.offset
				max = params.max
			}
				
			userInstanceList = userInstanceList.drop(offset).take(max)
			
			render(view: "list", model: [userInstanceList: userInstanceList, userInstanceTotal: userInstanceList.size()])
			return
		}
		
		//  Show the default list view if we don't find any results
		if (!userInstance && !userInstanceList) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.query])
			redirect(action: "list")
			return
		}
		
		render(view: "show", model: [userInstance: userInstance ?: userInstanceList.first()])
	}
	
	
	def getEnvironments(){
		def roleArray = []
		roleArray=params.role
		List<String> authList = Arrays.asList(roleArray.split(","));

	def roleList = []
authList.each {
	
	def str=it.toString().replace(",","").replaceAll("'", "")
	
	roleList.add(Role.where{authority in str}.get())
	
	}
	
def grantedEnvironments=[]		
grantedEnvironments=Environment.where{role in roleList}.list()

	
		def jsonCODetails
		if(grantedEnvironments.size()>0){
			jsonCODetails = [envIds:grantedEnvironments.id,envName:grantedEnvironments.name,count:grantedEnvironments.size()]
			log.debug "jsonCODetails:"+jsonCODetails
		}else{
			jsonCODetails = [:]
		}

		render jsonCODetails as JSON
		
	}
	
	
	def getEditEnvironments(){
		def role=params.role
log.debug role
def splitatr=role.split(",")
log.debug splitatr
/*def grantedEnvironments=[]

if(role.contains("ROLE_PM")){
		
grantedEnvironments=Environment.where{name in ['Development / Cert_Review','Certification / Cert','Int - Int']}.list()

}else if(role.contains("ROLE_QA"))
{

grantedEnvironments=Environment.where{name in ['QA / Prod_Review']}.list()
}
else if(role.contains("ROLE_PROD"))
{
grantedEnvironments=Environment.where{name in ['Production / Prod']}.list()
}
	

	def jsonCODetails
	if(grantedEnvironments.size()>0){
		jsonCODetails = [envIds:grantedEnvironments.id,envName:grantedEnvironments.name,count:grantedEnvironments.size()]
		log.debug "jsonCODetails:"+jsonCODetails
	}else{
		jsonCODetails = [:]
	}

	render jsonCODetails as JSON*/
	
}
}
