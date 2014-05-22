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

<%--<div
	class="${hasErrors(bean: programInstance, field: 'bundles', 'error')} ">
	<label for="bundles" class="control-label"><g:message
			code="program.bundles.label" default="Bundles" /></label>
	<div>

		<ul class="one-to-many">
			<g:each in="${programInstance?.bundles?}" var="b">
				<li><g:link controller="bundle" action="show" id="${b.id}">
						${b?.encodeAsHTML()}
					</g:link></li>
			</g:each>
			<li class="add"><g:link controller="bundle" action="create"
					params="['program.id': programInstance?.id]">
					${message(code: 'default.add.label', args: [message(code: 'bundle.label', default: 'Bundle')])}
				</g:link></li>
		</ul>

		<span class="help-inline"> ${hasErrors(bean: programInstance, field: 'bundles', 'error')}
		</span>
	</div>
</div>

--%>