<%@ page import="hmof.deploy.Job" %>



			<div class="${hasErrors(bean: jobInstance, field: 'jobNumber', 'error')} required">
				<label for="jobNumber" class="control-label"><g:message code="job.jobNumber.label" default="Job Number" /><span class="required-indicator">*</span></label>
				<div>
					<g:field class="form-control" name="jobNumber" type="number" value="${jobInstance.jobNumber}" required=""/>
					<span class="help-inline">${hasErrors(bean: jobInstance, field: 'jobNumber', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: jobInstance, field: 'contentTypeId', 'error')} required">
				<label for="contentTypeId" class="control-label"><g:message code="job.contentTypeId.label" default="Content Type Id" /><span class="required-indicator">*</span></label>
				<div>
					<g:field class="form-control" name="contentTypeId" type="number" value="${jobInstance.contentTypeId}" required=""/>
					<span class="help-inline">${hasErrors(bean: jobInstance, field: 'contentTypeId', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: jobInstance, field: 'contentId', 'error')} required">
				<label for="contentId" class="control-label"><g:message code="job.contentId.label" default="Content Id" /><span class="required-indicator">*</span></label>
				<div>
					<g:field class="form-control" name="contentId" type="number" value="${jobInstance.contentId}" required=""/>
					<span class="help-inline">${hasErrors(bean: jobInstance, field: 'contentId', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: jobInstance, field: 'revision', 'error')} required">
				<label for="revision" class="control-label"><g:message code="job.revision.label" default="Revision" /><span class="required-indicator">*</span></label>
				<div>
					<g:field class="form-control" name="revision" type="number" value="${jobInstance.revision}" required=""/>
					<span class="help-inline">${hasErrors(bean: jobInstance, field: 'revision', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: jobInstance, field: 'promotion', 'error')} ">
				<label for="promotion" class="control-label"><g:message code="job.promotion.label" default="Promotion" /></label>
				<div>
					
<ul class="one-to-many">
<g:each in="${jobInstance?.promotion?}" var="p">
    <li><g:link controller="promotion" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="promotion" action="create" params="['job.id': jobInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'promotion.label', default: 'Promotion')])}</g:link>
</li>
</ul>

					<span class="help-inline">${hasErrors(bean: jobInstance, field: 'promotion', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: jobInstance, field: 'user', 'error')} required">
				<label for="user" class="control-label"><g:message code="job.user.label" default="User" /><span class="required-indicator">*</span></label>
				<div>
					<g:select class="form-control" id="user" name="user.id" from="${hmof.security.User.list()}" optionKey="id" required="" value="${jobInstance?.user?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: jobInstance, field: 'user', 'error')}</span>
				</div>
			</div>

