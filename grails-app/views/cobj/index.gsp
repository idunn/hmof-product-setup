
<%@ page import="hmof.Cobj" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cobj.label', default: 'Cobj')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-cobj" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-cobj" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
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
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
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
			<div class="pagination">
				<g:paginate total="${cobjInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
