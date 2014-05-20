
<%@ page import="hmof.Cobj" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'cobj.label', default: 'Cobj')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>

<section id="list-cobj" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="objectName" title="${message(code: 'cobj.objectName.label', default: 'Object Name')}" />
			
				<g:sortableColumn property="isbn" title="${message(code: 'cobj.isbn.label', default: 'Isbn')}" />
			
				<g:sortableColumn property="pathToCoverImage" title="${message(code: 'cobj.pathToCoverImage.label', default: 'Path To Cover Image')}" />
			
				<g:sortableColumn property="teacherLabel" title="${message(code: 'cobj.teacherLabel.label', default: 'Teacher Label')}" />
			
				<g:sortableColumn property="teacherUrl" title="${message(code: 'cobj.teacherUrl.label', default: 'Teacher Url')}" />
			
				<g:sortableColumn property="studentLabel" title="${message(code: 'cobj.studentLabel.label', default: 'Student Label')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${cobjInstanceList}" status="i" var="cobjInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${cobjInstance.id}">${fieldValue(bean: cobjInstance, field: "objectName")}</g:link></td>
			
				<td>${fieldValue(bean: cobjInstance, field: "isbn")}</td>
			
				<td>${fieldValue(bean: cobjInstance, field: "pathToCoverImage")}</td>
			
				<td>${fieldValue(bean: cobjInstance, field: "teacherLabel")}</td>
			
				<td>${fieldValue(bean: cobjInstance, field: "teacherUrl")}</td>
			
				<td>${fieldValue(bean: cobjInstance, field: "studentLabel")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${cobjInstanceCount}" />
	</div>
</section>

</body>

</html>
