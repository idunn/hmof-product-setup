
<%@ page import="hmof.SecureProgram" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'secureProgram.label', default: 'SecureProgram')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-secureProgram" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-secureProgram" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="productName" title="${message(code: 'secureProgram.productName.label', default: 'Product Name')}" />
					
						<g:sortableColumn property="registrationIsbn" title="${message(code: 'secureProgram.registrationIsbn.label', default: 'Registration Isbn')}" />
					
						<g:sortableColumn property="onlineIsbn" title="${message(code: 'secureProgram.onlineIsbn.label', default: 'Online Isbn')}" />
					
						<g:sortableColumn property="copyright" title="${message(code: 'secureProgram.copyright.label', default: 'Copyright')}" />
					
						<g:sortableColumn property="labelLink" title="${message(code: 'secureProgram.labelLink.label', default: 'Label Link')}" />
					
						<g:sortableColumn property="pathToResource" title="${message(code: 'secureProgram.pathToResource.label', default: 'Path To Resource')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${secureProgramInstanceList}" status="i" var="secureProgramInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${secureProgramInstance.id}">${fieldValue(bean: secureProgramInstance, field: "productName")}</g:link></td>
					
						<td>${fieldValue(bean: secureProgramInstance, field: "registrationIsbn")}</td>
					
						<td>${fieldValue(bean: secureProgramInstance, field: "onlineIsbn")}</td>
					
						<td>${fieldValue(bean: secureProgramInstance, field: "copyright")}</td>
					
						<td>${fieldValue(bean: secureProgramInstance, field: "labelLink")}</td>
					
						<td>${fieldValue(bean: secureProgramInstance, field: "pathToResource")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${secureProgramInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
