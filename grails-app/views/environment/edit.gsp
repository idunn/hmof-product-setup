<%@ page import="hmof.deploy.Environment" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'environment.label', default: 'Environment')}" />
	<title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>

<body>

<style>
		@font-face {
			font-family: 'Glyphicons Halflings';
			src: url('../../fonts/glyphicons-halflings-regular.eot');
			src: url('../../fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded- opentype'), 
     		url('../../fonts/glyphicons-halflings-regular.woff') format('woff'), 
    		 url('../../fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     		url('../../fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg');
		} 
	</style>

	<section id="edit-environment" class="first">

		<g:hasErrors bean="${environmentInstance}">
		<div class="alert alert-danger">
			<g:renderErrors bean="${environmentInstance}" as="list" />
		</div>
		</g:hasErrors>

		<g:form method="post" class="form-horizontal" role="form" >
			<g:hiddenField name="id" value="${environmentInstance?.id}" />
			<g:hiddenField name="version" value="${environmentInstance?.version}" />
			<g:hiddenField name="_method" value="PUT" />
			
			<g:render template="form"/>
			
			<div class="form-actions margin-top-medium">
				<g:actionSubmit class="btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset" /></button>
			</div>
		</g:form>

	</section>

</body>

</html>
