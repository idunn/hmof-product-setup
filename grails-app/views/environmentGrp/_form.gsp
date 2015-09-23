<%@ page import="hmof.deploy.EnvironmentGrp" %>



			<div class="${hasErrors(bean: environmentGrpInstance, field: 'groupname', 'error')} ">
				<label for="groupname" class="control-label"><g:message code="environmentGrp.groupname.label" default="Groupname" /></label>
				<div>
					<g:textField class="form-control" name="groupname" value="${environmentGrpInstance?.groupname}"/>
					
				</div>
			</div>

