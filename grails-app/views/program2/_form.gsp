<%@ page import="hmof.deploy.Program2" %>



			<div class="${hasErrors(bean: program2Instance, field: 'name', 'error')} ">
				<label for="name" class="control-label"><g:message code="program2.name.label" default="Name" /></label>
				<div>
					<g:textField class="form-control" name="name" value="${program2Instance?.name}"/>
					<span class="help-inline">${hasErrors(bean: program2Instance, field: 'name', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: program2Instance, field: 'description', 'error')} ">
				<label for="description" class="control-label"><g:message code="program2.description.label" default="Description" /></label>
				<div>
					<g:textField class="form-control" name="description" value="${program2Instance?.description}"/>
					<span class="help-inline">${hasErrors(bean: program2Instance, field: 'description', 'error')}</span>
				</div>
			</div>

