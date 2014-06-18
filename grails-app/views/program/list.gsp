
<%@ page import="hmof.Program"%>
<!DOCTYPE html>
<html>

<head>
<meta name="layout" content="kickstart" />
<g:set var="entityName"
	value="${message(code: 'program.label', default: 'Program')}" />
<title><g:message code="default.index.label" args="[entityName]" /></title>
</head>

<body>

	<section id="index-program" class="first">

		<table class="table table-bordered margin-top-medium">
			<thead>
				<tr>

					<g:sortableColumn property="name"
						title="${message(code: 'program.name.label', default: 'Name')}" />

					<g:sortableColumn property="discipline"
						title="${message(code: 'program.discipline.label', default: 'Discipline')}" />

					<g:sortableColumn property="lastUpdated"
						title="${message(code: 'program.lastUpdated.label', default: 'Last Updated')}" />

					<g:sortableColumn property="devEnvironment"
						title="${message(code: 'program.devEnvironment.label', default: 'Dev Environment')}" />

					<g:sortableColumn property="qaEnvironment"
						title="${message(code: 'program.qaEnvironment.label', default: 'QA Environment')}" />

					<g:sortableColumn property="prodEnvironment"
						title="${message(code: 'program.prodEnvironment.label', default: 'Prod Environment')}" />


					<th>
						${'View'}
					</th>

				</tr>
			</thead>
			<tbody>
				<g:each in="${programInstanceList}" status="i" var="programInstance">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

						<td><g:link action="show" id="${programInstance.id}">
								${fieldValue(bean: programInstance, field: "name")}
							</g:link></td>

						<td>
							${fieldValue(bean: programInstance, field: "discipline")}
						</td>

						<td><g:formatDate date="${programInstance.lastUpdated}" /></td>

						<td><g:formatDate date="${programInstance.devEnvironment}" /></td>

						<td><g:formatDate date="${programInstance.qaEnvironment}" /></td>
						
						<td><g:formatDate date="${programInstance.prodEnvironment}" /></td>

						<td><g:link action="show" id="${programInstance.id}">
								${'view'}
							</g:link></td>

					</tr>
				</g:each>
			</tbody>
		</table>
		<div>
			<bs:paginate total="${programInstanceCount}" />
		</div>
	</section>

</body>

</html>
