<%@ page import="hmof.deploy.Promotion" %>



			<div class="${hasErrors(bean: promotionInstance, field: 'environments', 'error')} required">
				<label for="environments" class="control-label"><g:message code="promotion.environments.label" default="Environments" /><span class="required-indicator">*</span></label>
				<div>
					<g:select class="form-control" id="environments" name="environments.id" from="${hmof.deploy.Environment.list()}" optionKey="id" required="" value="${promotionInstance?.environments?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: promotionInstance, field: 'environments', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: promotionInstance, field: 'job', 'error')} required">
				<label for="job" class="control-label"><g:message code="promotion.job.label" default="Job" /><span class="required-indicator">*</span></label>
				<div>
					<g:select class="form-control" id="job" name="job.id" from="${hmof.deploy.Job.list()}" optionKey="id" required="" value="${promotionInstance?.job?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: promotionInstance, field: 'job', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: promotionInstance, field: 'jobNumber', 'error')} required">
				<label for="jobNumber" class="control-label"><g:message code="promotion.jobNumber.label" default="Job Number" /><span class="required-indicator">*</span></label>
				<div>
					<g:field class="form-control" name="jobNumber" type="number" value="${promotionInstance.jobNumber}" required=""/>
					<span class="help-inline">${hasErrors(bean: promotionInstance, field: 'jobNumber', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: promotionInstance, field: 'status', 'error')} ">
				<label for="status" class="control-label"><g:message code="promotion.status.label" default="Status" /></label>
				<div>
					<g:textField class="form-control" name="status" value="${promotionInstance?.status}"/>
					<span class="help-inline">${hasErrors(bean: promotionInstance, field: 'status', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: promotionInstance, field: 'user', 'error')} required">
				<label for="user" class="control-label"><g:message code="promotion.user.label" default="User" /><span class="required-indicator">*</span></label>
				<div>
					<g:select class="form-control" id="user" name="user.id" from="${hmof.security.User.list()}" optionKey="id" required="" value="${promotionInstance?.user?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: promotionInstance, field: 'user', 'error')}</span>
				</div>
			</div>

