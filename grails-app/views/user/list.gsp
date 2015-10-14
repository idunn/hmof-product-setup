
<%@ page import="hmof.security.User" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
</head>

<body>
	
<div class="row">

		<div class="span12">

			<div class="widget stacked widget-table action-table">

						<div class="widget-content">
<section id="list-user" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}" class="widget-header2"/>
			
				<%-- <g:sortableColumn property="password" title="${message(code: 'user.password.label', default: 'Password')}" />--%>
				
				<g:sortableColumn property="environments" title="${message(code: 'user.environments.label', default: 'Environments')}" class="widget-header2" />
				
				<%--<g:sortableColumn property="deploymentEnvGroups" title="${message(code: 'user.environmentGroups.label', default: 'Environment Groups')}" class="widget-header2" />
				
				--%><g:sortableColumn property="email" title="${message(code: 'user.email.label', default: 'Email')}" class="widget-header2"/>
				
				<th class="widget-header2" style="color:#bbb">${message(code: 'user.role.label', default: 'Role')}</th>
				
				<g:sortableColumn property="accountExpired" title="${message(code: 'user.accountExpired.label', default: 'Account Expired')}" class="widget-header2"/>
			
				<g:sortableColumn property="accountLocked" title="${message(code: 'user.accountLocked.label', default: 'Account Locked')}" class="widget-header2" />
			
				<g:sortableColumn property="enabled" title="${message(code: 'user.enabled.label', default: 'Account Enabled')}" class="widget-header2" />
			
				<g:sortableColumn property="passwordExpired" title="${message(code: 'user.passwordExpired.label', default: 'Password Expired')}" class="widget-header2" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${userInstanceList}" status="i" var="userInstance">
			<tr <g:if test='${i == 0}'>class="warning"</g:if>>
			
				<td><label class="radio control-label">
				
				<g:link action="show" id="${userInstance.id}">${fieldValue(bean: userInstance, field: "username")}</g:link></label>
				</td>
			
				<%--<td>${fieldValue(bean: userInstance, field: "password")}</td>--%>
				
				<td>
					<ul>
						<g:each in="${userInstance.environments}" var="e">
							<li><g:link controller="environment" action="show" id="${e.id}">${e}</g:link></li>
						</g:each>
					</ul>
				</td>
				<%--<td>
					<ul>
						<g:each in="${userInstance.deploymentEnvGroups}" var="eg">
							<li>${eg.description}</li>
						</g:each>
					</ul>
				</td>

				--%><td>${fieldValue(bean: userInstance, field: "email")}</td>
				<td>								
					<ul class="unstyled">
						<g:each in="${userInstance?.getAuthorities()}" var="a">					
							<li>
								${a.authority.encodeAsHTML()}
							</li>					
						</g:each>
					</ul>
				</td>
				<td>
				
				<g:formatBoolean boolean="${userInstance.accountExpired}" /></td>
			
				<td><g:formatBoolean boolean="${userInstance.accountLocked}" /></td>
			
				<td><g:formatBoolean boolean="${userInstance.enabled}" /></td>
			
				<td><g:formatBoolean boolean="${userInstance.passwordExpired}" /></td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
</section>
	</div>
			<!-- /widget-content -->
			</div>
			<!-- /widget -->
			
			
	<div>
		<bs:paginate total="${userInstanceTotal}" />
	</div>
		</div>
	</div>
</body>

</html>
