<%@ page import="hmof.deploy.Environment" %>



			<div class="${hasErrors(bean: environmentInstance, field: 'name', 'error')} ">
				<label for="name" class="control-label"><g:message code="environment.name.label" default="Name" /></label>
				<div>
					<g:textField class="form-control" name="name" value="${environmentInstance?.name}"/>
					<span class="help-inline">${hasErrors(bean: environmentInstance, field: 'name', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: environmentInstance, field: 'priorityOrder', 'error')} required">
				<label for="priorityOrder" class="control-label"><g:message code="environment.priorityOrder.label" default="Priority Order" /><span class="required-indicator">*</span></label>
				<div>
					<g:field class="form-control" name="priorityOrder" type="number" value="${environmentInstance.priorityOrder}" required=""/>
					<span class="help-inline">${hasErrors(bean: environmentInstance, field: 'priorityOrder', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: environmentInstance, field: 'url', 'error')} ">
				<label for="url" class="control-label"><g:message code="environment.url.label" default="Url" /></label>
				<div>
					<g:field class="form-control" type="url" name="url" value="${environmentInstance?.url}"/>
					<span class="help-inline">${hasErrors(bean: environmentInstance, field: 'url', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: environmentInstance, field: 'promotion', 'error')} ">
				<label for="promotion" class="control-label"><g:message code="environment.promotion.label" default="Promotion" /></label>
				<div>
					
<ul class="one-to-many">
<g:each in="${environmentInstance?.promotion?}" var="p">
    <li><g:link controller="promotion" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="promotion" action="create" params="['environment.id': environmentInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'promotion.label', default: 'Promotion')])}</g:link>
</li>
</ul>

					<span class="help-inline">${hasErrors(bean: environmentInstance, field: 'promotion', 'error')}</span>
				</div>
			</div>

