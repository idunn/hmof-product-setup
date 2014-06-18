
<%@ page import="hmof.CommerceObject" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'commerceObject.label', default: 'CommerceObject')}" />
	<title><g:message code="default.index.label" args="[entityName]" /></title>
</head>

<body>

<section id="index-commerceObject" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="objectName" title="${message(code: 'commerceObject.objectName.label', default: 'Object Name')}" />
			
				<g:sortableColumn property="isbn" title="${message(code: 'commerceObject.isbn.label', default: 'Isbn')}" />
				
				<g:sortableColumn property="lastUpdated" title="${message(code: 'commerceObject.lastUpdated.label', default: 'Last Updated')}" />
			
				<g:sortableColumn property="devEnvironment" title="${message(code: 'commerceObject.devEnvironment.label', default: 'Dev Environment')}" />
				
				<g:sortableColumn property="qaEnvironment" title="${message(code: 'commerceObject.qaEnvironment.label', default: 'QA Environment')}" />
				
				<g:sortableColumn property="prodEnvironment" title="${message(code: 'commerceObject.prodEnvironment.label', default: 'Prod Environment')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${commerceObjectInstanceList}" status="i" var="commerceObjectInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${commerceObjectInstance.id}">${fieldValue(bean: commerceObjectInstance, field: "objectName")}</g:link></td>
			
				<td>${fieldValue(bean: commerceObjectInstance, field: "isbn")}</td>
			
				<td><g:formatDate date="${commerceObjectInstance.lastUpdated}" /></td>
				
				<td><g:formatDate date="${commerceObjectInstance.devEnvironment}" /></td>
				
				<td><g:formatDate date="${commerceObjectInstance.qaEnvironment}" /></td>
				
				<td><g:formatDate date="${commerceObjectInstance.prodEnvironment}" /></td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${commerceObjectInstanceCount}" />
	</div>
</section>

</body>

</html>
