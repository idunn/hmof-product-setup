<%@ page import="hmof.deploy.EnvironmentGrp" %>

<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">

			<div class="${hasErrors(bean: environmentGrpInstance, field: 'groupname', 'error')} ">
				<label for="groupname" class="control-label"><g:message code="environmentGrp.groupname.label" default="Groupname" /></label>
				<div>
					<g:textField class="form-control" name="groupname" value="${environmentGrpInstance?.groupname}"/>
					
				</div>
			</div>

