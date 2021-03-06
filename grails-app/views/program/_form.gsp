<%@ page import="hmof.Program"%>

<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
<div class="span7">
	<section>
<div
	class="control-group fieldcontain ${hasErrors(bean: programInstance, field: 'name', 'error')} required">
	<label for="name" class="control-label col-sw-1"><g:message
			code="program.name.label" default="Name" /><span
		class="required-indicator">*</span></label>
	<div class="controls">
		<g:textField class="form-control" name="name"
			pattern="${programInstance.constraints.name.matches}" required=""
			value="${programInstance?.name}" />
		<span class="help-inline">
			${hasErrors(bean: programInstance, field: 'name', 'error')}
		</span>
	</div>
</div>

<div
	class="control-group fieldcontain ${hasErrors(bean: programInstance, field: 'state', 'error')} required">
	<label for="state" class="control-label col-sw-1"><g:message
			code="program.state.label" default="State" /><span
		class="required-indicator">*</span></label>
	<div class="controls">
	
		<g:stateDropDown class="form-control" id="state" name="state" required="" value="${programInstance?.state}" valueMessagePrefix="program.state" />			
			
		<span class="help-inline"> ${hasErrors(bean: programInstance, field: 'state', 'error')}
		</span>
	</div>
</div>

<div
	class="control-group fieldcontain ${hasErrors(bean: programInstance, field: 'discipline', 'error')} required">
	<label for="discipline" class="control-label col-sw-1"><g:message
			code="program.discipline.label" default="Discipline" /><span
		class="required-indicator">*</span></label>
	<div class="controls">
		<g:select class="form-control" name="discipline"
			from="${programInstance.constraints.discipline.inList}" required=""
			value="${programInstance?.discipline}"
			valueMessagePrefix="program.discipline" />
		<span class="help-inline">
			${hasErrors(bean: programInstance, field: 'discipline', 'error')}
		</span>
	</div>
</div>

<div
	class="control-group fieldcontain ${hasErrors(bean: programInstance, field: 'bundles', 'error')} ">
	<label for="bundles" class="control-label col-sw-1"><g:message
			code="program.bundles.label" default="Bundles" /></label>
	<div class="controls">

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

		<span class="help-inline">
			${hasErrors(bean: programInstance, field: 'bundles', 'error')}
		</span>
	</div>
</div>
</section>
</div>
