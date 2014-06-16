
<%@ page import="hmof.Program"%>
<!DOCTYPE html>
<html>

<head>
<meta name="layout" content="kickstart" />
<g:set var="entityName"
	value="${message(code: 'program.label', default: 'Program')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

	<section id="show-program" class="first">

		<table class="table">
			<tbody>

				<tr class="prop">
					<td valign="top" class="name"><g:message
							code="program.name.label" default="Name" /></td>

					<td valign="top" class="value">
						${fieldValue(bean: programInstance, field: "name")}
					</td>

				</tr>

				<tr class="prop">
					<td valign="top" class="name"><g:message
							code="program.discipline.label" default="Discipline" /></td>

					<td valign="top" class="value">
						${fieldValue(bean: programInstance, field: "discipline")}
					</td>

				</tr>

				<tr class="prop">
					<td valign="top" class="name"><g:message
							code="program.bundles.label" default="Bundles" /></td>

					<td valign="top" style="text-align: left;" class="value">
						<!-- Add new Template --> <g:render template="addBundle" /> <!-- Added new table -->
						<table class="table table-bordered margin-top-medium">
							<thead>
								<tr>
									<th>
										${'List'}
									</th>
									<th>
										${'View'}
									</th>
									<th>
										${'Edit'}
									</th>
								</tr>
							</thead>
							<tbody>
								<g:each in="${programInstance.bundles}" status="i" var="b">

									<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
										<td><g:link controller="bundle" action="show"
												id="${b.id}">
												${b?.encodeAsHTML()}
											</g:link></td>
										<td><g:link controller="bundle" action="show"
												id="${b.id}">
												${"View"}
											</g:link></td>
										<td><g:link controller="bundle" action="edit"
												id="${b.id}">
												${"Edit"}
											</g:link></td>
									</tr>
								</g:each>

							</tbody>
						</table>
					</td>

				</tr>
				<tr class="prop">
					<td valign="top" class="name"><g:message
							code="program.dateCreated.label" default="Date Created" /></td>

					<td valign="top" class="value"><g:formatDate
							date="${programInstance?.dateCreated}" /></td>

				</tr>

				<tr class="prop">
					<td valign="top" class="name"><g:message
							code="program.lastUpdated.label" default="Last Updated" /></td>

					<td valign="top" class="value"><g:formatDate
							date="${programInstance?.lastUpdated}" /></td>

				</tr>

				<tr class="prop">
					<td valign="top" class="name"><g:message
							code="program.devEnvironment.label" default="Dev Environment" /></td>

					<td valign="top" class="value"><g:formatDate
							date="${programInstance?.devEnvironment}" /></td>

				</tr>

				<tr class="prop">
					<td valign="top" class="name"><g:message
							code="program.devEnvironment.label" default="QA Environment" /></td>

					<td valign="top" class="value"><g:formatDate
							date="${programInstance?.qaEnvironment}" /></td>

				</tr>

			</tbody>
		</table>
	</section>

</body>

</html>
