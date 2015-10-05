
<%@ page import="hmof.deploy.EnvironmentGrp" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'environmentGrp.label', default: 'EnvironmentGrp')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
</head>

<body>

<section id="list-environmentGrp" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="groupname" title="${message(code: 'environmentGrp.groupname.label', default: 'Groupname')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${environmentGrpInstanceList}" status="i" var="environmentGrpInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${environmentGrpInstance.id}">${fieldValue(bean: environmentGrpInstance, field: "groupname")}</g:link></td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${environmentGrpInstanceCount}" />
	</div>
</section>

</body>

</html>
