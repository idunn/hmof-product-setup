<%@ page import="hmof.security.User" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

	<body>	
		<div class="row">
			<div class="span12">
				<div class="widget stacked widget-table action-table">
					<div class="widget-header">
						<i class="icon-th-list"></i>
						<h3>Users Email List</h3>
					</div><!-- /widget-header -->
		
					<div class="widget-content">
						<section id="list-user" class="first">
							<table class="table table-bordered">
								<thead>
									<tr>
										<td>									
											This is a ; delimited listing of all active user's email addresses. It is intended to be used as a mailing list for CDM email alerts.  Use Ctrl-A and Ctrl-C on the textarea to select and copy the full list of email addresses to paste into an email.
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>								
											<textarea rows="20" cols="100" autofocus="autofocus" readonly="readonly"><g:each in="${userInstanceList}" status="i" var="userInstance">${userInstance.email};</g:each></textarea> 
										</td>
									</tr>
								</tbody>
							</table>
						</section>
					</div><!-- /widget-content -->
				</div><!-- /widget -->	
			</div>
		</div>
	</body>
</html>