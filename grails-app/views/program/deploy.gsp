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

	<!-- Added new table -->
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
					${'Secure Programs'}
				</th>

				<th>
					${'Commerce Objects'}
				</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${deploymentDetailsList}" status="i" var="b">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
					<td>
						${b.isbn}
					</td>
					<td>
						${b.title}
					</td>
					<td>
						${b.secureProgram.registrationIsbn}
					</td>
					<td>
						${b.commerceObjects.objectName}
					</td>
				</tr>
			</g:each>

		</tbody>
	</table>
</body>
</html>