<%@ page import="hmof.Bundle"%>

<div class="span7">
	<section>

		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'isbn', 'error')} required">
			<label for="isbn" class="control-label col-sw-1"><g:message
					code="bundle.isbn.label" default="Isbn" /><span
				class="required-indicator">*</span></label>
			<div class="controls">
				<g:textField class="form-control" name="isbn" required=""
					value="${bundleInstance?.isbn}" />
				<span class="help-inline">
					${hasErrors(bean: bundleInstance, field: 'isbn', 'error')}
				</span>
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'title', 'error')} required">
			<label for="title" class="control-label col-sw-1"><g:message
					code="bundle.title.label" default="Title" /><span
				class="required-indicator">*</span></label>
			<div class="controls">
				<g:textField class="form-control" name="title" required=""
					value="${bundleInstance?.title}" />
				<span class="help-inline">
					${hasErrors(bean: bundleInstance, field: 'title', 'error')}
				</span>
			</div>
		</div>
		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'includePremiumCommerceObjects', 'error')} ">
			<label for="includePremiumCommerceObjects" class="control-label col-sw-1">
				<g:message code="commerceObject.includePremiumCommerceObjects.label"
					default="Include Premium CommerceObjects" />

			</label>
			<div class="controls">
			<bs:checkBox name="includePremiumCommerceObjects"
				value="${bundleInstance?.includePremiumCommerceObjects}" />
				</div>
		</div>
		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'duration', 'error')} ">
			<label for="duration" class="control-label col-sw-1"><g:message
					code="bundle.duration.label" default="Duration" /></label>
			<div class="controls col-sw-4" >
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
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'program', 'error')} required">
			<label for="program" class="control-label col-sw-1"><g:message
					code="bundle.program.label" default="Program" /><span
				class="required-indicator">*</span></label>
			<div class="controls">
				<g:select class="form-control" id="program" name="program.id"
					from="${hmof.Program.list()}" optionKey="id" required=""
					value="${bundleInstance?.program?.id}" />
				<span class="help-inline">
					${hasErrors(bean: bundleInstance, field: 'program', 'error')}
				</span>
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'secureProgram', 'error')} ">
			<label for="secureProgram" class="control-label col-sw-1"><g:message
					code="bundle.secureProgram.label" default="Secure Program" /></label>
			<div class="controls">
				<g:select class="form-control" name="secureProgram"
					from="${hmof.SecureProgram.list()}" noSelection="['':'-None-']"
					multiple="multiple" optionKey="id" size="10"
					value="${bundleInstance?.secureProgram*.id}"  />
				<span class="help-inline">
					${hasErrors(bean: bundleInstance, field: 'secureProgram', 'error')}
				</span>
			</div>
		</div>
	</section>
</div>