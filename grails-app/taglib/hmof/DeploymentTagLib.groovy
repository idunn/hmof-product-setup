package hmof

import hmof.deploy.*
import hmof.deploy.EnvironmentGrp
import groovy.sql.Sql
import java.sql.ResultSet
class DeploymentTagLib {
	def dataSource
	static namespace = "hmof"

	def ifGroupHasEnvironments = { attrs, body ->
		//log.info "Checking if the group has environments configured for it yet ${attrs.environmentGroup}"
		
		
		def sql = new Sql(dataSource)
		def environmentsOfGroup
		
		try{
			environmentsOfGroup = sql.rows('select * from Environment e left join Environment_grp group1 ON group1.id=?',[attrs.environmentGroup])
			}
		catch(Exception e){
			log.error("exception in ifGroupHasEnvironments method is: "+e.getMessage())
			log.error("exception in ifGroupHasEnvironments method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}
		
		//log.info"environmentsOfGroup ${environmentsOfGroup.size()}"
		if(!environmentsOfGroup || environmentsOfGroup.size() <= 0)
		{
			return false
		}else
		{
			out << body()
			return true
		}
	}
	/*
	 * List all the environments that are associated with the current group
	 * 
	 */
	def groupButton = { attrs, body ->
		def sql = new Sql(dataSource)
		def environmentsOfGroup
		
		try{
			environmentsOfGroup = sql.rows('select * from Environment e join Environment_grp group1  ON e.groups_id=group1.id and group1.id=?',[attrs.environmentGroup.id])
		
			}
		catch(Exception e){
			log.error("exception in groupButton method is: "+e.getMessage())
			log.error("exception in groupButton method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}
		//log.info"environmentsOfGroup ${environmentsOfGroup.size()}"
		
			def groupDescription = attrs.environmentGroup.groupname.replaceAll('_',' ')
			
			def buttonClass = attrs.i == 0 ? 'warning' :'default'
			
			out << "<input type='button' name='envGroupButton' class='btn btn-${buttonClass} ${attrs.environmentGroup.groupname}Button' value='${groupDescription} Environments' data-toggle='tooltip' data-placement='right' data-container='body' title='${environmentsOfGroup.name}'>"
			}
	
	
	/*
	 * List all the environments that are associated with the current group for search products
	 *
	 */
	def groupProductButton = { attrs, body ->
		def sql = new Sql(dataSource)
		def environmentsOfGroup
		
		try{
			environmentsOfGroup = sql.rows('select * from Environment e join Environment_grp group1  ON e.groups_id=group1.id and group1.id=?',[attrs.environmentGroup.id])
		
			}
		catch(Exception e){
			log.error("exception in groupButton method is: "+e.getMessage())
			log.error("exception in groupButton method is: "+e.getStackTrace())
		}
		finally{
			sql.close();
		}
		//log.info"environmentsOfGroup ${environmentsOfGroup.size()}"
		
			def groupDescription = attrs.environmentGroup.groupname.replaceAll('_',' ')
			
			def buttonClass = attrs.i == 0 ? 'warning' :'default'
			
			out << "<input type='button' name='envGroupProductButton'  class='btn btn-${buttonClass} ${attrs.environmentGroup.groupname}Button1' value='${groupDescription} Environments' data-toggle='tooltip' data-placement='right' data-container='body' title='${environmentsOfGroup.name}'>"
			}
	
		
}