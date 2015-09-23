<%@ page import="hmof.security.User" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
	<%--

	
			function env_display(){

			
				var spdata=""; 
            	spdata+="<select  name=\"environmentsIds\"  value=\"${userInstance?.environments?.collect{it.id}}\" multiple=\"multiple\"   class=\"many-to-many form-control\">"
            		spdata+="<option value=\"\">No Access - This user may not make deployments</option>";

	            	if(document.getElementById("ROLE_PM").checked)
		            	{            		
            		spdata+="<option value='1'>Development / Cert_Review</option>";
            		spdata+="<option value='3'>Certification / Cert</option>";
            		spdata+="<option value='4'>Integration - Int</option>";
            		
		            	}
	            	if(document.getElementById("ROLE_QA").checked)
	            	{            		
        		   spdata+="<option value='2'>QA / Prod_Review</option>";      		
        		
	            	}  
	            	if(document.getElementById("ROLE_PROD").checked)
	            	{            		
        		  spdata+="<option value='5'>Production / Prod</option>";        		
        		
	            	}            	
            	spdata+="</select>";				            	            	

            
            	$("#result").html(spdata);

				
			   
			}
			</script>
	--%></head>

<body>
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
<section id="create-user" class="first">

	<g:hasErrors bean="${userInstance}">
	<div class="alert alert-error">
		<g:renderErrors bean="${userInstance}" as="list" />
	</div>
	</g:hasErrors>
	
	<g:form action="save" class="form-horizontal" >
		<fieldset class="form">
			<g:render template="form"/>
		</fieldset>
		<div class="span9">
<section>

<div id="result"></div>
		<div class="form-actions">
			<g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset" /></button>
		</div>
		</section>
		</div>
	</g:form>
	
</section>
		</div>
				<!-- /widget-content -->

			</div>
			<!-- /widget -->

		</div>
		<!-- /span8 -->
	
</body>

</html>
