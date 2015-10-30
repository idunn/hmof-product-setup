<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="kickstart">
		<g:set var="entityName" value="${message(code: 'programXML.label', default: 'ProgramXML')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
	</head>
	<body>
	<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
	<section id="create-program" class="first">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${programXMLInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${programXMLInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" class="form-horizontal" role="form" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<div class="span9">
<section>
			<div class="form-actions margin-top-medium">
					<g:submitButton name="create" formnovalidate class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</div>
			</section>
			</div>
			</g:form>
		
		</section>
</div>
</div>
</div>
	</body>
</html>
