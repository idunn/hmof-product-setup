
<%@ page import="hmof.deploy.Promotion" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'promotion.label', default: 'Promotion')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-promotion" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="promotion.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${promotionInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="promotion.environments.label" default="Environments" /></td>
				
				<td valign="top" class="value"><g:link controller="environment" action="show" id="${promotionInstance?.environments?.id}">${promotionInstance?.environments?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="promotion.job.label" default="Job" /></td>
				
				<td valign="top" class="value"><g:link controller="job" action="show" id="${promotionInstance?.job?.id}">${promotionInstance?.job?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="promotion.jobNumber.label" default="Job Number" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: promotionInstance, field: "jobNumber")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="promotion.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${promotionInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="promotion.status.label" default="Status" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: promotionInstance, field: "status")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="promotion.user.label" default="User" /></td>
				
				<td valign="top" class="value"><g:link controller="user" action="show" id="${promotionInstance?.user?.id}">${promotionInstance?.user?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
