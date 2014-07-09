
<%@ page import="hmof.CommerceObject" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'commerceObject.label', default: 'CommerceObject')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>

<section id="list-commerceObject" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
				<g:sortableColumn property="id" title="${message(code: 'commerceObject.id.label', default: 'ID #')}" />
			
				<g:sortableColumn property="objectName" title="${message(code: 'commerceObject.objectName.label', default: 'Object Name')}" />
			
				<g:sortableColumn property="isbn" title="${message(code: 'commerceObject.isbn.label', default: 'Isbn')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'commerceObject.lastUpdated.label', default: 'Last Updated')}" />
				
			</tr>
		</thead>
		<tbody>		
		<g:each in="${commerceObjectInstanceList}" status="i" var="commerceObjectInstance">
			<%--<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">--%>
			<tr <g:if test='${i == 0}'>class="warning"</g:if>>
			<td><label class="radio control-label">
				<!--  Default the first one to selected -->
				<input type="radio" name="listGroup" id="listGroup" value="${commerceObjectInstance?.id}" <g:if test='${i == 0}'>checked='checked'</g:if> />
				<g:link action="show" id="${commerceObjectInstance.id}">${commerceObjectInstance?.id}</g:link></label></td>										
			
				<td><g:link action="show" id="${commerceObjectInstance.id}">${fieldValue(bean: commerceObjectInstance, field: "objectName")}</g:link></td>
			
				<td>${fieldValue(bean: commerceObjectInstance, field: "isbn")}</td>			
				
				<td><g:formatDate date="${commerceObjectInstance.lastUpdated}" /></td>
				
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
