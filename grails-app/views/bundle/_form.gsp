<%@ page import="hmof.Bundle"%>



<div
	class="${hasErrors(bean: bundleInstance, field: 'isbn', 'error')} required">
	<label for="isbn" class="control-label"><g:message
			code="bundle.isbn.label" default="Isbn" /><span
		class="required-indicator">*</span></label>
	<div>
		<g:textField class="form-control" name="isbn" required=""
			value="${bundleInstance?.isbn}" />
		<span class="help-inline">
			${hasErrors(bean: bundleInstance, field: 'isbn', 'error')}
		</span>
	</div>
</div>

<div
	class="${hasErrors(bean: bundleInstance, field: 'title', 'error')} required">
	<label for="title" class="control-label"><g:message
			code="bundle.title.label" default="Title" /><span
		class="required-indicator">*</span></label>
	<div>
		<g:textField class="form-control" name="title" required=""
			value="${bundleInstance?.title}" />
		<span class="help-inline">
			${hasErrors(bean: bundleInstance, field: 'title', 'error')}
		</span>
	</div>
</div>

<div
	class="${hasErrors(bean: bundleInstance, field: 'duration', 'error')} ">
	<label for="duration" class="control-label"><g:message
			code="bundle.duration.label" default="Duration" /></label>
	<div>
		<g:select class="form-control" name="duration"
			from="${bundleInstance.constraints.duration.inList}"
			value="${bundleInstance?.duration}"
			valueMessagePrefix="bundle.duration" noSelection="['': '']" />
		<span class="help-inline">
			${hasErrors(bean: bundleInstance, field: 'duration', 'error')}
		</span>
	</div>
</div>

<div
	class="${hasErrors(bean: bundleInstance, field: 'secureProgram', 'error')} ">
	<label for="secureProgram" class="control-label"><g:message
			code="bundle.secureProgram.label" default="Secure Program" /></label>
	<div>
		<g:select class="form-control" name="secureProgram"
			from="${hmof.SecureProgram.list()}" 
			noSelection="['':'-None-']"
			multiple="multiple"
			optionKey="id" size="10" value="${bundleInstance?.secureProgram*.id}"
			class="many-to-many" />
		<span class="help-inline">
			${hasErrors(bean: bundleInstance, field: 'secureProgram', 'error')}
		</span>
	</div>
</div>

<div
	class="${hasErrors(bean: bundleInstance, field: 'program', 'error')} required">
	<label for="program" class="control-label"><g:message
			code="bundle.program.label" default="Program" /><span
		class="required-indicator">*</span></label>
	<div>
		<g:select class="form-control" id="program" name="program.id"
			from="${hmof.Program.list()}" optionKey="id" required=""
			value="${bundleInstance?.program?.id}" class="many-to-one" />
		<span class="help-inline">
			${hasErrors(bean: bundleInstance, field: 'program', 'error')}
		</span>
	</div>
</div>

<div
	class="${hasErrors(bean: bundleInstance, field: 'commerceObjects', 'error')} ">
	<label for="commerceObjects" class="control-label"><g:message
			code="bundle.commerceObjects.label" default="Commerce Objects" /></label>
	<div>
		<g:select class="form-control" name="commerceObjects"
			from="${hmof.CommerceObject.list()}" 
			noSelection="['':'-None-']"
			multiple="multiple" optionKey="id"
			size="10" value="${bundleInstance?.commerceObjects*.id}"
			class="many-to-many" />
		<span class="help-inline">
			${hasErrors(bean: bundleInstance, field: 'commerceObjects', 'error')}
		</span>
	</div>
</div>



