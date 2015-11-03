<%@ page import="hmof.programxml.ProgramXML" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="kickstart">
		<g:set var="entityName" value="${message(code: 'programXML.label', default: 'ProgramXML')}" />
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
</style>
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
			<g:form url="[resource:programXMLInstance, action:'update']" method="PUT" >
				<g:hiddenField name="version" value="${programXMLInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
			<div class="span9">
<section>
			<div class="form-actions margin-top-medium">
					<g:actionSubmit class="save" formnovalidate action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
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
