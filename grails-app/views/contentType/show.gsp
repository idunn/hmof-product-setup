
<%@ page import="hmof.ContentType" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'contentType.label', default: 'ContentType')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-contentType" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contentType.contentId.label" default="Content Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contentTypeInstance, field: "contentId")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contentType.name.label" default="Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contentTypeInstance, field: "name")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
