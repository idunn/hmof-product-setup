
<%@ page import="hmof.deploy.Environment" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'environment.label', default: 'Environment')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
</head>

<body>

<section id="list-environment" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="name" title="${message(code: 'environment.name.label', default: 'Name')}" />
			
				<g:sortableColumn property="priorityOrder" title="${message(code: 'environment.priorityOrder.label', default: 'Priority Order')}" />
			
				<g:sortableColumn property="url" title="${message(code: 'environment.url.label', default: 'Url')}" />
			    <g:sortableColumn property="url" title="${message(code: 'environment.role.label', default: 'Role')}" />
				<th><g:message code="environment.groups.label" default="Groups" /></th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${environmentInstanceList}" status="i" var="environmentInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${environmentInstance.id}">${fieldValue(bean: environmentInstance, field: "name")}</g:link></td>
			
				<td>${fieldValue(bean: environmentInstance, field: "priorityOrder")}</td>
			
				<td>${fieldValue(bean: environmentInstance, field: "url")}</td>
			  <td>${fieldValue(bean: environmentInstance, field: "role")}</td>
				<td>${fieldValue(bean: environmentInstance, field: "groups")}</td>
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${environmentInstanceCount}" />
	</div>
</section>

</body>

</html>
