<%@ page import="hmof.security.User"%>
<!doctype html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="kickstart" />
<g:set var="entityName"
	value="${message(code: 'user.label', default: 'User')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'search.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
<style>
@font-face {
	font-family: 'Glyphicons Halflings';
	src: url('../../fonts/glyphicons-halflings-regular.eot');
	src: url('../../fonts/glyphicons-halflings-regular.eot?#iefix')
		format('embedded- opentype'),
		url('../../fonts/glyphicons-halflings-regular.woff') format('woff'),
		url('../../fonts/glyphicons-halflings-regular.ttf') format('truetype'),
		url('../../fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular')
		format('svg');
}
</style>
</head>

<body>
	<div class="row">
		<div class="widget stacked ">
			<div class="widget-content">


				<section id="show-user" class="first">
					<div class="form-horizontal">


						<div class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">
									<b>User Details</b>
								</h3>
							</div>
							<div class="panel-body" >

								<div class="span8">


									<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.username.label" default="Username" /></label>

										<div class="controls show-style">
											${fieldValue(bean: userInstance, field: "username")}
										</div>
									</div>

									<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.email.label" default="Email" /></label>

										<div class="controls show-style">
											${fieldValue(bean: userInstance, field: "email")}
										</div>
									</div>

									<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.role.label" default="Role" /></label>

										<div class="controls show-style">
											<ul class="unstyled">
												<g:each in="${userInstance?.getAuthorities()}" var="a">
													<li>
														${a.authority.encodeAsHTML()}
													</li>
												</g:each>
											</ul>
										</div>
									</div>

									<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.environments.label" default="Environments" /></label>

										<div class="controls show-style">
											<ul>
												<g:each in="${userInstance.environments}" var="e">
													<li><sec:access controller='environment' action='show'>
															<g:link controller="environment" action="show"
																id="${e.id}">
																${e?.encodeAsHTML()}
															</g:link>
														</sec:access> <sec:noAccess controller='environment' action='show'>
															<!-- Show the environment details but no link as the user cannot access it anyway -->
															${e}
														</sec:noAccess></li>
												</g:each>
											</ul>
										</div>
									</div>


									<%--<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.deploymentEnvGroups.label"
												default="Environment Groups" /></label>

										<div class="controls show-style">
											<ul>
												<g:each in="${userInstance.deploymentEnvGroups}" var="eg">
													<li>
														${eg.description}
													</li>
												</g:each>
											</ul>
										</div>
									</div>

									--%><div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.accountExpired.label" default="Account Expired" /></label>

										<div class="controls show-style">
											<g:formatBoolean boolean="${userInstance?.accountExpired}" />
										</div>
									</div>

									<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.accountLocked.label" default="Account Locked" /></label>

										<div class="controls show-style">
											<g:formatBoolean boolean="${userInstance?.accountLocked}" />
										</div>
									</div>

									<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.enabled.label" default="Enabled" /></label>

										<div class="controls show-style">
											<g:formatBoolean boolean="${userInstance?.enabled}" />
										</div>
									</div>

									<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.passwordExpired.label" default="Password Expired" /></label>

										<div class="controls show-style">
											<g:formatBoolean boolean="${userInstance?.passwordExpired}" />
										</div>
									</div>
									<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.jobs.label" default="Jobs" /></label>

										<div class="controls show-style">
											<ul>
												<g:each in="${userInstance.jobs.sort { it.id }}" var="j">
													<li><g:link controller="job" action="show"
															id="${j.id}">
															${j?.encodeAsHTML()}
														</g:link></li>
												</g:each>
											</ul>
										</div>
									</div>

									<div class="control-group">
										<label for="name" class="control-label col-sw-1"><g:message
												code="user.promotions.label" default="Promotions" /></label>

										<div class="controls show-style">
											<ul>
												<g:each in="${userInstance.promotions.sort { it.id }}"
													var="d">
													<li><g:link controller="promotion" action="show"
															id="${d.id}">
															${d?.encodeAsHTML()}
														</g:link></li>
												</g:each>
											</ul>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</div>
	</div>
</body>

</html>
