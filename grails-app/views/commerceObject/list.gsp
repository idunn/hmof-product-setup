
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
			
				<g:sortableColumn property="objectName" title="${message(code: 'commerceObject.objectName.label', default: 'Object Name')}" />
			
				<g:sortableColumn property="isbn" title="${message(code: 'commerceObject.isbn.label', default: 'Isbn')}" />
			
				<g:sortableColumn property="pathToCoverImage" title="${message(code: 'commerceObject.pathToCoverImage.label', default: 'Path To Cover Image')}" />
			
				<g:sortableColumn property="teacherLabel" title="${message(code: 'commerceObject.teacherLabel.label', default: 'Teacher Label')}" />
			
				<g:sortableColumn property="teacherUrl" title="${message(code: 'commerceObject.teacherUrl.label', default: 'Teacher Url')}" />
			
				<g:sortableColumn property="studentLabel" title="${message(code: 'commerceObject.studentLabel.label', default: 'Student Label')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${commerceObjectInstanceList}" status="i" var="commerceObjectInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${commerceObjectInstance.id}">${fieldValue(bean: commerceObjectInstance, field: "objectName")}</g:link></td>
			
				<td>${fieldValue(bean: commerceObjectInstance, field: "isbn")}</td>
			
				<td>${fieldValue(bean: commerceObjectInstance, field: "pathToCoverImage")}</td>
			
				<td>${fieldValue(bean: commerceObjectInstance, field: "teacherLabel")}</td>
			
				<td>${fieldValue(bean: commerceObjectInstance, field: "teacherUrl")}</td>
			
				<td>${fieldValue(bean: commerceObjectInstance, field: "studentLabel")}</td>
			
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
