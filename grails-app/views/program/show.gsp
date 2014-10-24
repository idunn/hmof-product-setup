
<%@ page import="hmof.Program"%>
<!DOCTYPE html>
<html>

<head>
<meta name="layout" content="kickstart" />
<g:set var="entityName"
	value="${message(code: 'program.label', default: 'Program')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
</head>

<body>
<style>
	@font-face {
font-family: 'Glyphicons Halflings';
src: url('../../fonts/glyphicons-halflings-regular.eot');
src: url('../../fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded- opentype'), 
     url('../../fonts/glyphicons-halflings-regular.woff') format('woff'), 
     url('../../fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('../../fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg');
} 
</style>
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
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
							code="program.state.label" default="State" /></td>

					<td valign="top" class="value">
						${fieldValue(bean: programInstance, field: "state")}
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

			</tbody>
		</table>
	</section>
</div>
</div>
</div>
</body>

</html>
