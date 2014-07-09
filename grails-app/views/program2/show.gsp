
<%@ page import="hmof.deploy.Program2" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'program2.label', default: 'Program2')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-program2" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="program2.name.label" default="Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: program2Instance, field: "name")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="program2.description.label" default="Description" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: program2Instance, field: "description")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
