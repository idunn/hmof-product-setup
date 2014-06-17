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

	<h4>Bundle to promote:</h4>
	<p>
		${bundleInstance.isbn}
	</p>
	<p>
		${bundleInstance.secureProgram.registrationIsbn}
	</p>

	<h4>Secure Programs to promote:</h4>
	<p>
		${secureProgramsList}
	</p>

	<h4>Commerce Objects to promote:</h4>
	<p>
		${commerceObjectsList}
	</p>

</body>
</html>