
<%@ page import="hmof.deploy.Job" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'job.label', default: 'Job')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-job" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="job.jobNumber.label" default="Job Number" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: jobInstance, field: "jobNumber")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="job.contentTypeId.label" default="Content Type Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: jobInstance, field: "contentTypeId")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="job.contentId.label" default="Content Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: jobInstance, field: "contentId")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="job.revision.label" default="Revision" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: jobInstance, field: "revision")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="job.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${jobInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="job.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${jobInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="job.promotion.label" default="Promotion" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${jobInstance.promotion}" var="p">
						<li><g:link controller="promotion" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="job.user.label" default="User" /></td>
				
				<td valign="top" class="value"><g:link controller="user" action="show" id="${jobInstance?.user?.id}">${jobInstance?.user?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
