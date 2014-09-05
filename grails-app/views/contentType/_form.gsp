<%@ page import="hmof.ContentType" %>



			<div class="${hasErrors(bean: contentTypeInstance, field: 'contentId', 'error')} required">
				<label for="contentId" class="control-label"><g:message code="contentType.contentId.label" default="Content Id" /><span class="required-indicator">*</span></label>
				<div>
					<g:field class="form-control" name="contentId" type="number" value="${contentTypeInstance.contentId}" required=""/>
					<span class="help-inline">${hasErrors(bean: contentTypeInstance, field: 'contentId', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: contentTypeInstance, field: 'name', 'error')} ">
				<label for="name" class="control-label"><g:message code="contentType.name.label" default="Name" /></label>
				<div>
					<g:textField class="form-control" name="name" value="${contentTypeInstance?.name}"/>
					<span class="help-inline">${hasErrors(bean: contentTypeInstance, field: 'name', 'error')}</span>
				</div>
			</div>

