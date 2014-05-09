
<%@ page import="hmof.Bundle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bundle.label', default: 'Bundle')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-bundle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-bundle" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="isbn" title="${message(code: 'bundle.isbn.label', default: 'Isbn')}" />
					
						<g:sortableColumn property="title" title="${message(code: 'bundle.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="duration" title="${message(code: 'bundle.duration.label', default: 'Duration')}" />
					
						<th><g:message code="bundle.program.label" default="Program" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${bundleInstanceList}" status="i" var="bundleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${bundleInstance.id}">${fieldValue(bean: bundleInstance, field: "isbn")}</g:link></td>
					
						<td>${fieldValue(bean: bundleInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: bundleInstance, field: "duration")}</td>
					
						<td>${fieldValue(bean: bundleInstance, field: "program")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${bundleInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
