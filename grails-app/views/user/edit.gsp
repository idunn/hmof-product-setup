<%@ page import="hmof.security.User" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
	<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
	<style>

	@font-face {
font-family: 'Glyphicons Halflings';
src: url('../../fonts/glyphicons-halflings-regular.eot');
src: url('../../fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded- opentype'), 
     url('../../fonts/glyphicons-halflings-regular.woff') format('woff'), 
     url('../../fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('../../fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg');
} 

.loader {
	position: fixed;
	left: 400px;
	top:300px;
	width: 22%;
	height: 40%;
	z-index: 9999;
	background: url('../../images/page-loader.gif') 20% 20% no-repeat rgb(249,249,249);
}
</style>
<script>
$(window).load(function() {
	$(".loader").fadeOut("2000");
})
window.onload = pageload;
var environmentsIds ="${userInstance?.environments?.collect{it.id}}";
var environments = "${userInstance?.environments?.collect{it.name}}";
			function pageload(){
				
				var spdata=""; 

            	spdata+="<select  name=\"environmentsIds\"  id=\"environmentsIds\"  value=\"\" multiple=\"multiple\"   class=\"many-to-many form-control\">"
            	spdata+="<option value=\"\">No Access - This user may not make deployments</option>";

                 var envIdsStr1= environmentsIds.replace("["," ").replace("]"," "); 
                 var envStr1=environments.replace("[","").replace("]",""); 
        
              	  var envIdsStr=envIdsStr1.split(",");
            		var envStr=envStr1.split(",");
            		
            		for (var i = 0; i <envStr.length; i++) {            			
	            		spdata+="<option value="+envIdsStr[i]+" selected=selected>"+envStr[i]+"</option>";
	            	}
	                       	
            	spdata+="</select>";				            	            	

$("#result").html(spdata);   
			}

			
			</script>
</head>

<body>
<div class="loader"></div>
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
<section id="edit-user" class="first">

	<g:hasErrors bean="${userInstance}">
	<div class="alert alert-error">
		<g:renderErrors bean="${userInstance}" as="list" />
	</div>
	</g:hasErrors>

	<g:form method="post" class="form-horizontal" >
		<g:hiddenField name="id" value="${userInstance?.id}" />
		<g:hiddenField name="version" value="${userInstance?.version}" />
		<fieldset class="form">
			<g:render template="form"/>
		</fieldset>
		<div class="span9">
         <section>
		<div class="form-actions">
			<g:actionSubmit class="btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
			<%-- <g:actionSubmit class="btn btn-danger" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--%>
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
		
</body>

</html>
