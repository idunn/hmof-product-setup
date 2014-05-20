
<%@ page import="hmof.Program" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'program.label', default: 'Program')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-program" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="program.name.label" default="Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: programInstance, field: "name")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="program.discipline.label" default="Discipline" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: programInstance, field: "discipline")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="program.bundles.label" default="Bundles" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${programInstance.bundles}" var="b">
						<li><g:link controller="bundle" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="program.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${programInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="program.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${programInstance?.lastUpdated}" /></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
