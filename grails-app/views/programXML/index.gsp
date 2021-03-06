
<%@ page import="hmof.programxml.ProgramXML" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="kickstart">
		<g:set var="entityName" value="${message(code: 'programXML.label', default: 'ProgramXML')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-programXML" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-programXML" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'programXML.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="buid" title="${message(code: 'programXML.buid.label', default: 'Buid')}" />
					
						<g:sortableColumn property="language" title="${message(code: 'programXML.language.label', default: 'Language')}" />
					
						<g:sortableColumn property="filename" title="${message(code: 'programXML.filename.label', default: 'Filename')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${programXMLInstanceList}" status="i" var="programXMLInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${programXMLInstance.id}">${fieldValue(bean: programXMLInstance, field: "title")}</g:link></td>
					
						<td>${fieldValue(bean: programXMLInstance, field: "buid")}</td>
					
						<td>${fieldValue(bean: programXMLInstance, field: "language")}</td>
					
						<td>${fieldValue(bean: programXMLInstance, field: "filename")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${programXMLInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
