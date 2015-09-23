<%@ page import="hmof.deploy.EnvironmentGrp" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'environmentGrp.label', default: 'EnvironmentGrp')}" />
	<title><g:message code="default.edit.label" args="[entityName]" /></title>
	<style>
	@font-face {
font-family: 'Glyphicons Halflings';
src: url('../../fonts/glyphicons-halflings-regular.eot');
src: url('../../fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded- opentype'), 
     url('../../fonts/glyphicons-halflings-regular.woff') format('woff'), 
     url('../../fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('../../fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg');
} 
</head>

<body>

	<section id="edit-environmentGrp" class="first">

		<g:hasErrors bean="${environmentGrpInstance}">
		<div class="alert alert-danger">
			<g:renderErrors bean="${environmentGrpInstance}" as="list" />
		</div>
		</g:hasErrors>

		<g:form method="post" class="form-horizontal" role="form" >
			<g:hiddenField name="id" value="${environmentGrpInstance?.id}" />
			<g:hiddenField name="version" value="${environmentGrpInstance?.version}" />
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
