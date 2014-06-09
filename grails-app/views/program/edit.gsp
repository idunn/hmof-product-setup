<%@ page import="hmof.Program" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'program.label', default: 'Program')}" />
	<title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>

<body>

	<section id="edit-program" class="first">

		<g:hasErrors bean="${programInstance}">
		<div class="alert alert-danger">
			<g:renderErrors bean="${programInstance}" as="list" />
		</div>
		</g:hasErrors>

		<g:form method="post" class="form-horizontal" role="form" >
			<g:hiddenField name="id" value="${programInstance?.id}" />
			<g:hiddenField name="version" value="${programInstance?.version}" />
			<g:hiddenField name="_method" value="PUT" />
			
			<g:render template="formEdit"/>
			
			<div class="form-actions margin-top-medium">
				<g:actionSubmit class="btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset" /></button>
			</div>
		</g:form>

	</section>

</body>

</html>
