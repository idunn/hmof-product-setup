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
			value="${programInstance?.name}"  data-toggle="tooltip" data-placement="right" data-container="body" title="Please enter the Program Name"/>
		<span class="help-inline"> ${hasErrors(bean: programInstance, field: 'name', 'error')}
		</span>
	</div>
</div>

<div
	class="control-group fieldcontain ${hasErrors(bean: programInstance, field: 'state', 'error')} required">
	<label for="state" class="control-label col-sw-1"><g:message
			code="program.state.label" default="State" /><span
		class="required-indicator">*</span></label>
	<div class="controls">
	
		<g:stateDropDown class="form-control" id="state" name="state" required="" value="${programInstance?.state}" valueMessagePrefix="program.state"  data-toggle="tooltip" data-placement="right" data-container="body" title="Please enter the Program State"/>			
			
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
			valueMessagePrefix="program.discipline" data-toggle="tooltip" data-placement="right" data-container="body" title="Please enter the Program discipline" />
		<span class="help-inline"> ${hasErrors(bean: programInstance, field: 'discipline', 'error')}
		</span>
	</div>
</div>
</section>
</div>

<script>
   $(function () { $("[data-toggle='tooltip']").tooltip(); });
</script>