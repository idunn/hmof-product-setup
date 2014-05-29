
<%@ page import="hmof.SecureProgram" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'secureProgram.label', default: 'SecureProgram')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>

<section id="list-secureProgram" class="first">

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="productName" title="${message(code: 'secureProgram.productName.label', default: 'Product Name')}" />
			
				<g:sortableColumn property="registrationIsbn" title="${message(code: 'secureProgram.registrationIsbn.label', default: 'Registration Isbn')}" />
			
				<g:sortableColumn property="onlineIsbn" title="${message(code: 'secureProgram.onlineIsbn.label', default: 'Online Isbn')}" />
			
				<g:sortableColumn property="copyright" title="${message(code: 'secureProgram.copyright.label', default: 'Copyright')}" />
			
				<g:sortableColumn property="labelForOnlineResource" title="${message(code: 'secureProgram.labelForOnlineResource.label', default: 'Label For Online Resource')}" />
			
				<g:sortableColumn property="pathToResource" title="${message(code: 'secureProgram.pathToResource.label', default: 'Path To Resource')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${secureProgramInstanceList}" status="i" var="secureProgramInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${secureProgramInstance.id}">${fieldValue(bean: secureProgramInstance, field: "productName")}</g:link></td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "registrationIsbn")}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "onlineIsbn")}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "copyright")}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "labelForOnlineResource")}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "pathToResource")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div>
		<bs:paginate total="${secureProgramInstanceCount}" />
	</div>
</section>

</body>

</html>
