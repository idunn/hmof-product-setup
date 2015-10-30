
<%@ page import="hmof.programxml.ProgramXML" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="kickstart">
		<g:set var="entityName" value="${message(code: 'programXML.label', default: 'ProgramXML')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
			<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
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
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
<section id="show-commerceObject" class="first">

	<div class="span9 form-horizontal">

	
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="programXML.title.label" default="Title" /></label>
				
				<div class="controls show-style"><g:fieldValue bean="${programXMLInstance}" field="title"/></div></div>
			
				<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="programXML.buid.label" default="Buid" /></label>
				
				<div class="controls show-style"><g:fieldValue bean="${programXMLInstance}" field="buid"/></div></div>
				<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="programXML.language.label" default="Language" /></label>
				
				<div class="controls show-style"><g:fieldValue bean="${programXMLInstance}" field="language"/></div></div>
			
				<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="programXML.filename.label" default="Filename" /></label>
				
				<div class="controls show-style"><g:fieldValue bean="${programXMLInstance}" field="filename"/></div></div>
			
		
				<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="bundle.secureProgram.label" default="Secure Programs" /></label>
				
				<div class="controls show-style" style="text-align: left;">
					<ul>
					<g:each in="${programXMLInstance.secureProgram}" var="s">
						<li><g:link controller="secureProgram" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</div>
				</div>
		</div>
		</section>
</div>
</div>
</div>
	</body>
</html>
