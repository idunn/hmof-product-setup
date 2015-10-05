<%@ page import="hmof.security.User" %>
<%@ page import="hmof.deploy.EnvironmentGrp"%>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
<script>
	

	
	function env_display()
	{ 	
		
		var arr=[];
		$("input:checked").each(function() {			  
			  var str=$(this).val();
			  var role=str.substring(2); 	
			  arr.push(role);
			});

	
		
		$.ajax({
	    	url: "${createLink(controller: 'user', action: 'getEnvironments')}",
	        data: "role=" + arr,
	        type: 'POST',
	        async : false,
	        datatype: 'text',
	        success: function (data) {
	         
	        	if(!(jQuery.isEmptyObject(data))){  		        		
	        		
	            	            	var name=data.envName; 
	            	            	
	            	            	var ids=data.envIds;				            	            	
	            	            	var spdata=""; 
	            	            	spdata+="<select  name=\"environmentsIds\"  value=\"${userInstance?.environments?.collect{it.id}}\" multiple=\"multiple\"   class=\"many-to-many form-control\">"
	            	            		spdata+="<option value=\"\">No Access - This user may not make deployments</option>";
		            	            	for (var i = 0; i < data.count; i++) {
	            	            		
	            	            		spdata+="<option value="+ids[i]+" >"+name[i]+"</option>";
	            	            	}
	            	            	spdata+="</select>";				            	            	
	            	            	
	            	            	$("#result").html(spdata);
	            	            	

		        	}       			       
	        }
	    })


	}

	

	</script>

<div class="panel panel-info">
 <div class="panel-heading">
    <h3 class="panel-title"><b>User Details</b></h3>
  </div>
  <div class="panel-body" >
<div class="span8">


			<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
				<label for="username" class="control-label col-sw-1"><g:message code="user.username.label" default="Username" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField class="form-control" name="username" required="" value="${userInstance?.username}"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
				<label for="password" class="control-label col-sw-1"><g:message code="user.password.label" default="Password" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:passwordField class="form-control" name="password" value="${userInstance?.password}" />
					
				</div>
			</div>
			
			<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'role', 'error')} ">
				<label for="role" class="control-label col-sw-1"><g:message code="user.role.label" default="Role" /></label>
				<div class="controls">
					<g:each var="auth" in="${authorityList}">
						<div>
							<input type="checkbox"  name="${auth.authority}" class="ads_Checkbox" id="${auth.authority}" value="on'${auth.authority.encodeAsHTML()}'" ${userInstance.id && userInstance?.getAuthorities()?.contains(auth) ? 'checked=checked' : ''} onclick="env_display()"/>
							
							<%--onclick="return onCheckboxChanged('${auth.authority.encodeAsHTML()}');"<bs:checkBox name="${auth.authority}" id="${auth.authority}" value="on" ${userInstance.id && userInstance?.getAuthorities()?.contains(auth) ? 'checked=checked' : ''}  />
						<g:checkBox name="${auth.authority}" value="on" checked="${!userInstance.id ? 'false' : 'true'}" />	 --%>
							${auth.authority.encodeAsHTML()}
						</div>
					</g:each>
					
				</div>
			</div>
			
			<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'deployedEnvironment', 'error')} ">
				<label for="deployedEnvironment" class="control-label col-sw-1"><g:message code="user.deployedEnvironment.label" default="Environments" /></label>
				<div class="controls"><%--
					 <g:select name="environmentsIds" from="${tc.deploy.Environment.list()}" optionKey="id" value="userInstance?.environments?.collect{it.id}" multiple="multiple" size="4" class="many-to-many" />
					 There were issues setting the HasMany Environments list so we set them here instead 
					--%><%--
				
				<g:if test="${userInstance?.environments?.collect{it.name}}">
				
				<g:select name="environmentsIds" id="environmentsIds" from="${tc.deploy.Environment.list()}" optionKey="id" value="${userInstance?.environments?.collect{it.id}}" multiple="multiple" size="7" noSelection="['':'No Access - This user may not make deployments']" class="many-to-many  form-control" />	
					
					
				</g:if>
				<g:else>			
				
			--%><div id="result"></div>
			
			</div>
			</div>		
			
			<%--<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'deploymentEnvGroups', 'error')} ">
				<label for="deploymentEnvGroups" class="control-label col-sw-1"><g:message code="user.deploymentEnvGroups.label" default="Environment Groups" /></label>
				<div class="controls">	
					<g:select name="deploymentEnvGroups" from="${DeploymentEnvGroup?.values()}" optionKey="description" value="${userInstance?.deploymentEnvGroups?.collect{it.description} ?: 'Content'}" multiple="multiple" size="4" class="many-to-many  form-control" />
					
				</div>
			</div>
			
			--%>
			<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'email', 'error')} ">
				<label for="email" class="control-label col-sw-1"><g:message code="user.email.label" default="Email" /></label>
				<div class="controls">
					<g:textField class="form-control" name="email" required="" value="${userInstance?.email}"/>
				</div>
			</div>
			
			
			
			<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
				<label for="accountExpired" class="control-label col-sw-1"><g:message code="user.accountExpired.label" default="Account Expired" /></label>
				<div class="controls">
					
	<bs:checkBox name="accountExpired" value="${userInstance?.accountExpired}" />
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
				<label for="accountLocked" class="control-label col-sw-1"><g:message code="user.accountLocked.label" default="Account Locked" /></label>
				<div class="controls">
					
					<bs:checkBox name="accountLocked" value="${userInstance?.accountLocked}"  />
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
				<label for="enabled" class="control-label col-sw-1"><g:message code="user.enabled.label" default="Enabled" /></label>
				<div class="controls">
				
					<bs:checkBox name="enabled" value="${userInstance?.enabled}"  />
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
				<label for="passwordExpired" class="control-label col-sw-1"><g:message code="user.passwordExpired.label" default="Password Expired" /></label>
				<div class="controls">	
					
					<bs:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}"  />
				</div>					
			</div>
			</div>
			</div>
			</div>
			
		