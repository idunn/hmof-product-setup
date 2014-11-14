<%@ page import="hmof.Bundle"%>

<div class="span7">
	<section>

		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'isbn', 'error-field')} required">
			<label for="isbn" class="control-label col-sw-1"><g:message
					code="bundle.isbn.label" default="Isbn" /><span
				class="required-indicator">*</span></label>
			<div class="controls">
				<g:textField class="form-control" name="isbn" required="" maxlength="13"
					value="${bundleInstance?.isbn}" data-toggle="tooltip" data-placement="right" data-container="body" title="A 13-digit component ISBN which is usually the Teacher Edition Online ISBN" />
				
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'title', 'error-field')} required">
			<label for="title" class="control-label col-sw-1"><g:message
					code="bundle.title.label" default="Title" /><span
				class="required-indicator">*</span></label>
			<div class="controls">
				<g:textField class="form-control" name="title" required=""
					value="${bundleInstance?.title}" data-toggle="tooltip" data-placement="right" data-container="body" title="Title which is not visible to the Customer but should be descriptive" />
				
			</div>
		</div>
		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'includePremiumCommerceObjects', 'error-field')} ">
			<label for="includePremiumCommerceObjects" class="control-label col-sw-1">
				<g:message code="commerceObject.includePremiumCommerceObjects.label"
					default="Include Premium Commerce Objects" />

			</label>
			<div class="controls">
			<g:checkBox name="includePremiumCommerceObjects"
				value="${bundleInstance?.includePremiumCommerceObjects}" data-toggle="tooltip" data-placement="right" data-container="body" title="Allows Premium Commerce Objects to be associated with the Bundle" />
				</div>
		</div>
		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'duration', 'error-field')} ">
			<label for="duration" class="control-label col-sw-1"><g:message
					code="bundle.duration.label" default="Duration" /></label>
			<div class="controls col-sw-4" >
				<g:select class="form-control" name="duration"
					from="${bundleInstance.constraints.duration.inList}"
					value="${bundleInstance?.duration}"
					valueMessagePrefix="bundle.duration" noSelection="['': '']" data-toggle="tooltip" data-placement="right" data-container="body" title="Entitlement duration of the ISBN"  />
				
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'program', 'error-field')} required">
			<label for="program" class="control-label col-sw-1"><g:message
					code="bundle.program.label" default="Program" /><span
				class="required-indicator">*</span></label>
			<div class="controls">
				<g:select class="form-control" id="program" name="program.id"
					from="${hmof.Program.list()}" optionKey="id" required=""
					value="${bundleInstance?.program?.id}"   />
				
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: bundleInstance, field: 'secureProgram', 'error-field')} ">
			<label for="secureProgram" class="control-label col-sw-1"><g:message
					code="bundle.secureProgram.label" default="Secure Program" /></label>
			<div class="controls">
				<g:select class="form-control" name="secureProgram"
					from="${hmof.SecureProgram.list()}" noSelection="['':'-None-']"
					multiple="multiple" optionKey="id" size="10"
					value="${bundleInstance?.secureProgram*.id}" data-toggle="tooltip" data-placement="right" data-container="body" title="A Bundle can only be deployed when it is associated with a Secure Program"   />
				
			</div>
		</div>
	</section>
</div>

<script>
   $(function () { $("[data-toggle='tooltip']").tooltip(); });
</script>