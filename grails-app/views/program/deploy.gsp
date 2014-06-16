<%@ page import="hmof.Program"%>
<!DOCTYPE html>
<html>

<head>
<meta name="layout" content="kickstart" />
<g:set var="entityName"
	value="${message(code: 'program.label', default: 'Deployable Bundles')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>

	<h4>Bundles to promote:</h4>
	<p>
		${bundlesList.isbn}
	</p>

	<h4>Secure Programs to promote:</h4>
	<p>
		${secureProgramsList}
	</p>

	<h4>Commerce Objects to promote:</h4>
	<p>
		${commerceObjectsList}
	</p>

	<!-- Added new table -->
	
	<%--<h4>Bundles to promote in Table View:</h4>
	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
				<th>
					${'Bundle Title'}
				</th>
				<th>
					${'Bundle ISBN'}
				</th>
				<th>
					${'Bundle Duration'}
				</th>
				<th>
					${'Secure Programs'}
				</th>

				<th>
					${'Commerce Objects'}
				</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${bundlesList}" status="i" var="b">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
					<td>
						${b.isbn}
					</td>
					<td>
						${b.title}
					</td>
					<td>
						${b.duration}
					</td>
					<td>
						${b.secureProgram.registrationIsbn}
					</td>
					<td>
						${b.secureProgram.commerceObjects.objectName}
					</td>
				</tr>
			</g:each>

		</tbody>
	</table>

--%></body>
</html>