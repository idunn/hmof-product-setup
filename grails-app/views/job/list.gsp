
<%@ page import="hmof.deploy.Job" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'job.label', default: 'Job')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>

<section id="list-job" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="jobNumber" title="${message(code: 'job.jobNumber.label', default: 'Job Number')}" />
			
				<g:sortableColumn property="contentTypeId" title="${message(code: 'job.contentTypeId.label', default: 'Content Type Id')}" />
			
				<g:sortableColumn property="contentId" title="${message(code: 'job.contentId.label', default: 'Content Id')}" />
			
				<g:sortableColumn property="revision" title="${message(code: 'job.revision.label', default: 'Revision')}" />
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'job.dateCreated.label', default: 'Date Created')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'job.lastUpdated.label', default: 'Last Updated')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${jobInstanceList}" status="i" var="jobInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${jobInstance.id}">${fieldValue(bean: jobInstance, field: "jobNumber")}</g:link></td>
			
				<td>${fieldValue(bean: jobInstance, field: "contentTypeId")}</td>
			
				<td>${fieldValue(bean: jobInstance, field: "contentId")}</td>
			
				<td>${fieldValue(bean: jobInstance, field: "revision")}</td>
			
				<td><g:formatDate date="${jobInstance.dateCreated}" /></td>
			
				<td><g:formatDate date="${jobInstance.lastUpdated}" /></td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${jobInstanceCount}" />
	</div>
</section>

</body>

</html>
