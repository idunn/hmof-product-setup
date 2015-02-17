
<%@ page import="hmof.Bundle" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'bundle.label', default: 'Bundle')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
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
<section id="show-bundle" class="first">


<div class="span9 form-horizontal">

<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="bundle.isbn.label" default="Isbn" /></label>
	<div class="controls show-style">
		${fieldValue(bean: bundleInstance, field: "isbn")}
	</div>
</div>


		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="bundle.title.label" default="Title" /></label>
				
				<div class="controls show-style">${fieldValue(bean: bundleInstance, field: "title")}</div>
				</div>
		<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="bundle.includePremiumCommerceObjects.label" default="Include Premium Commerce Objects" /></label>
				
				<div class="controls show-style">${fieldValue(bean: bundleInstance, field: "includePremiumCommerceObjects")}</div>
				</div>
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="bundle.duration.label" default="Duration" /></label>
				
				<div class="controls show-style">${fieldValue(bean: bundleInstance, field: "duration")}</div>
				</div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="bundle.dateCreated.label" default="Date Created" /></label>
				
				<div class="controls show-style"><g:formatDate date="${bundleInstance?.dateCreated}" /></div>
				</div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="bundle.lastUpdated.label" default="Last Updated" /></label>
				
				<div class="controls show-style"><g:formatDate date="${bundleInstance?.lastUpdated}" /></div>
				</div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="bundle.program.label" default="Program (Parent Object)" /></label>
				
				<div class="controls show-style"><g:link controller="program" action="show" id="${bundleInstance?.program?.id}">${bundleInstance?.program?.encodeAsHTML()}</g:link></div>
				</div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="bundle.secureProgram.label" default="Secure Program (Child Objects)" /></label>
				
				<div class="controls show-style" style="text-align: left;">
					<ul>
					<g:each in="${bundleInstance.secureProgram}" var="s">
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
