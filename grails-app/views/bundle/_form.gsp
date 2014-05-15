<%@ page import="hmof.Bundle" %>



<div class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'isbn', 'error')} required">
	<label for="isbn">
		<g:message code="bundle.isbn.label" default="Isbn" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="isbn" required="" value="${bundleInstance?.isbn}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="bundle.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${bundleInstance?.title}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'duration', 'error')} ">
	<label for="duration">
		<g:message code="bundle.duration.label" default="Duration" />
		
	</label>
	<g:select name="duration" from="${bundleInstance.constraints.duration.inList}" value="${bundleInstance?.duration}" valueMessagePrefix="bundle.duration" noSelection="['': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'commerceObjects', 'error')} ">
	<label for="commerceObjects">
		<g:message code="bundle.commerceObjects.label" default="Commerce Objects" />
		
	</label>
	<g:select name="commerceObjects" from="${hmof.Cobj.list()}" multiple="multiple" optionKey="id" size="5" value="${bundleInstance?.commerceObjects*.id}" class="many-to-many"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'program', 'error')} required">
	<label for="program">
		<g:message code="bundle.program.label" default="Program" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="program" name="program.id" from="${hmof.Program.list()}" optionKey="id" required="" value="${bundleInstance?.program?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'secureProgram', 'error')} ">
	<label for="secureProgram">
		<g:message code="bundle.secureProgram.label" default="Secure Program" />
		
	</label>
	<g:select name="secureProgram" from="${hmof.SecureProgram.list()}" multiple="multiple" optionKey="id" size="5" value="${bundleInstance?.secureProgram*.id}" class="many-to-many"/>

</div>

