
<%@ page import="hmof.deploy.Promotion" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'promotion.label', default: 'Promotion')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>

<section id="list-promotion" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'promotion.dateCreated.label', default: 'Date Created')}" />
			
				<th><g:message code="promotion.environments.label" default="Environments" /></th>
			
				<th><g:message code="promotion.job.label" default="Job" /></th>
			
				<g:sortableColumn property="jobNumber" title="${message(code: 'promotion.jobNumber.label', default: 'Job Number')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'promotion.lastUpdated.label', default: 'Last Updated')}" />
			
				<g:sortableColumn property="status" title="${message(code: 'promotion.status.label', default: 'Status')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${promotionInstanceList}" status="i" var="promotionInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${promotionInstance.id}">${fieldValue(bean: promotionInstance, field: "dateCreated")}</g:link></td>
			
				<td>${fieldValue(bean: promotionInstance, field: "environments")}</td>
			
				<td>${fieldValue(bean: promotionInstance, field: "job")}</td>
			
				<td>${fieldValue(bean: promotionInstance, field: "jobNumber")}</td>
			
				<td><g:formatDate date="${promotionInstance.lastUpdated}" /></td>
			
				<td>${fieldValue(bean: promotionInstance, field: "status")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${promotionInstanceCount}" />
	</div>
</section>

</body>

</html>
