<%@ page import="hmof.Bundle"%>
<!DOCTYPE html>
<html>

<head>
<meta name="layout" content="kickstart" />
<g:set var="entityName"
	value="${message(code: 'bundle.label', default: 'Deployable Bundle')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>

	<h4>SecureProgram to promote:</h4>
	<p>
		${secureProgramInstance}
	</p>
	<h4>CommerceObject to promote:</h4>
	<p>
		${commerceObjectsList}
	</p>


</body>
</html>