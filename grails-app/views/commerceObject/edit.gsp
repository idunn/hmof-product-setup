<%@ page import="hmof.CommerceObject" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'commerceObject.label', default: 'CommerceObject')}" />
	<title><g:message code="default.edit.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
</head>

<body>
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
	<section id="edit-commerceObject" class="first">

		<g:hasErrors bean="${commerceObjectInstance}">
		<div class="alert alert-danger">
			<g:renderErrors bean="${commerceObjectInstance}" as="list" />
		</div>
		</g:hasErrors>

		<g:form method="post" class="form-horizontal" role="form" >
			<g:hiddenField name="id" value="${commerceObjectInstance?.id}" />
			<g:hiddenField name="version" value="${commerceObjectInstance?.version}" />
			<g:hiddenField name="_method" value="PUT" />
			
			<g:render template="form"/>
			<div class="span9">
<section>
			<div class="form-actions margin-top-medium">
				<g:actionSubmit class="btn btn-primary" formnovalidate action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset" /></button>
			</div>
			</section></div>
		</g:form>

	</section>
</div></div></div>
</body>

</html>
