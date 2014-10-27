
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

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="bundle.isbn.label" default="Isbn" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: bundleInstance, field: "isbn")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="bundle.title.label" default="Title" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: bundleInstance, field: "title")}</td>
				
			</tr>
		<tr class="prop">
				<td valign="top" class="name"><g:message code="bundle.includePremiumCommerceObjects.label" default="Include Premium Commerce Objects" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: bundleInstance, field: "includePremiumCommerceObjects")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="bundle.duration.label" default="Duration" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: bundleInstance, field: "duration")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="bundle.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${bundleInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="bundle.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${bundleInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="bundle.program.label" default="Program" /></td>
				
				<td valign="top" class="value"><g:link controller="program" action="show" id="${bundleInstance?.program?.id}">${bundleInstance?.program?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="bundle.secureProgram.label" default="Secure Program" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${bundleInstance.secureProgram}" var="s">
						<li><g:link controller="secureProgram" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
		</tbody>
	</table>
</section>
</div>
</div>
</div>
</body>

</html>
