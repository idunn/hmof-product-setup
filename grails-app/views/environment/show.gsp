
<%@ page import="hmof.deploy.Environment" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'environment.label', default: 'Environment')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
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

<section id="show-environment" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environment.name.label" default="Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: environmentInstance, field: "name")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environment.priorityOrder.label" default="Priority Order" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: environmentInstance, field: "priorityOrder")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environment.url.label" default="Url" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: environmentInstance, field: "url")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environment.bambooPlan1.label" default="bambooPlan1" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: environmentInstance, field: "bambooPlan1")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environment.bambooPlan2.label" default="bambooPlan2" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: environmentInstance, field: "bambooPlan2")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environment.promotion.label" default="Promotion" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${environmentInstance.promotion}" var="p">
						<li><g:link controller="promotion" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environment.users.label" default="Users" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${environmentInstance.users}" var="u">
						<li><g:link controller="user" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environment.roles.label" default="Roles" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					${environmentInstance.role}
				</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environment.groups.label" default="Groups" /></td>
				
				<td valign="top" class="value"><g:link controller="environmentGrp" action="show" id="${environmentInstance?.groups?.id}">${environmentInstance?.groups?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
