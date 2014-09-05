
<%@ page import="hmof.ContentType" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'contentType.label', default: 'ContentType')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>

<section id="list-contentType" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="contentId" title="${message(code: 'contentType.contentId.label', default: 'Content Id')}" />
			
				<g:sortableColumn property="name" title="${message(code: 'contentType.name.label', default: 'Name')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${contentTypeInstanceList}" status="i" var="contentTypeInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${contentTypeInstance.id}">${fieldValue(bean: contentTypeInstance, field: "contentId")}</g:link></td>
			
				<td>${fieldValue(bean: contentTypeInstance, field: "name")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${contentTypeInstanceCount}" />
	</div>
</section>

</body>

</html>
