
<%@ page import="hmof.Bundle" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'bundle.label', default: 'Bundle')}" />
	<title><g:message code="default.index.label" args="[entityName]" /></title>
</head>

<body>

<section id="index-bundle" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="isbn" title="${message(code: 'bundle.isbn.label', default: 'Isbn')}" />
			
				<g:sortableColumn property="title" title="${message(code: 'bundle.title.label', default: 'Title')}" />
			
				<g:sortableColumn property="duration" title="${message(code: 'bundle.duration.label', default: 'Duration')}" />
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'bundle.dateCreated.label', default: 'Date Created')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'bundle.lastUpdated.label', default: 'Last Updated')}" />
			
				<th><g:message code="bundle.program.label" default="Program" /></th>
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${bundleInstanceList}" status="i" var="bundleInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${bundleInstance.id}">${fieldValue(bean: bundleInstance, field: "isbn")}</g:link></td>
			
				<td>${fieldValue(bean: bundleInstance, field: "title")}</td>
			
				<td>${fieldValue(bean: bundleInstance, field: "duration")}</td>
			
				<td><g:formatDate date="${bundleInstance.dateCreated}" /></td>
			
				<td><g:formatDate date="${bundleInstance.lastUpdated}" /></td>
			
				<td>${fieldValue(bean: bundleInstance, field: "program")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${bundleInstanceCount}" />
	</div>
</section>

</body>

</html>
