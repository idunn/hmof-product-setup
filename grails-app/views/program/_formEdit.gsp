<%@ page import="hmof.Program"%>



<div
	class="${hasErrors(bean: programInstance, field: 'name', 'error')} required">
	<label for="name" class="control-label"><g:message
			code="program.name.label" default="Name" /><span
		class="required-indicator">*</span></label>
	<div>
		<g:textField class="form-control" name="name"
			pattern="${programInstance.constraints.name.matches}" required=""
			value="${programInstance?.name}" />
		<span class="help-inline"> ${hasErrors(bean: programInstance, field: 'name', 'error')}
		</span>
	</div>
</div>

<div
	class="${hasErrors(bean: programInstance, field: 'discipline', 'error')} required">
	<label for="discipline" class="control-label"><g:message
			code="program.discipline.label" default="Discipline" /><span
		class="required-indicator">*</span></label>
	<div>
		<g:select class="form-control" name="discipline"
			from="${programInstance.constraints.discipline.inList}" required=""
			value="${programInstance?.discipline}"
			valueMessagePrefix="program.discipline" />
		<span class="help-inline"> ${hasErrors(bean: programInstance, field: 'discipline', 'error')}
		</span>
	</div>
</div>
<%-- Temp for testing --%>
<div
	class="${hasErrors(bean: programInstance, field: 'devEnvironment', 'error')} ">
	<label for="devEnvironment" class="control-label"><g:message
			code="program.devEnvironment.label" default="Dev Environment Promotion Date (For testing)" /></label>
	<div>
		<g:textField class="form-control" name="devEnvironment" required=""
			value="${secureProgramInstance?.devEnvironment}" />
		<span class="help-inline">
			${hasErrors(bean: secureProgramInstance, field: 'devEnvironment', 'error')}
		</span>
	</div>
</div>


