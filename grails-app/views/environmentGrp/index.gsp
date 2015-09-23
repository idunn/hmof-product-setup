
<%@ page import="hmof.deploy.EnvironmentGrp" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'environmentGrp.label', default: 'EnvironmentGrp')}" />
	<title><g:message code="default.index.label" args="[entityName]" /></title>
</head>

<body>

<section id="index-environmentGrp" class="first">

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
