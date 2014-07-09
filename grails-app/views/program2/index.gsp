
<%@ page import="hmof.deploy.Program2" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'program2.label', default: 'Program2')}" />
	<title><g:message code="default.index.label" args="[entityName]" /></title>
</head>

<body>

<section id="index-program2" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="name" title="${message(code: 'program2.name.label', default: 'Name')}" />
			
				<g:sortableColumn property="description" title="${message(code: 'program2.description.label', default: 'Description')}" />
			
				<th><g:message code="program2.job.label" default="Job" /></th>
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${program2InstanceList}" status="i" var="program2Instance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${program2Instance.id}">${fieldValue(bean: program2Instance, field: "name")}</g:link></td>
			
				<td>${fieldValue(bean: program2Instance, field: "description")}</td>
			
				<td>${fieldValue(bean: program2Instance, field: "job")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${program2InstanceCount}" />
	</div>
</section>

</body>

</html>
