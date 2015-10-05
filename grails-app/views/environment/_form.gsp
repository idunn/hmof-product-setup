<%@ page import="hmof.deploy.Environment" %>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">


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
<%--

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

--%>
           <div class="${hasErrors(bean: environmentInstance, field: 'role', 'error')} ">
				<label for="roels" class="control-label"><g:message code="environment.role.label" default="Role" /></label>
				<div>
					<g:select class="form-control" name="role" from="${hmof.security.Role.list()}" optionKey="id"  value="${environmentInstance?.role?.id}" />
					<span class="help-inline">${hasErrors(bean: environmentInstance, field: 'role', 'error')}</span>
				</div>
			</div>
			
			<div class="${hasErrors(bean: environmentInstance, field: 'groups', 'error')} ">
				<label for="groups" class="control-label"><g:message code="environment.groups.label" default="Groups" /></label>
				<div>
					<g:select class="form-control" id="groups" name="groups.id" from="${hmof.deploy.EnvironmentGrp.list()}"  optionKey="id" value="${environmentInstance?.groups?.id}"  />
					<span class="help-inline">${hasErrors(bean: environmentInstance, field: 'groups', 'error')}</span>
				</div>
			</div>

			<%--
		<div class="${hasErrors(bean: environmentInstance, field: 'enabled', 'error')} ">
				<label for="enabled" class="control-label"><g:message code="environment.enabled.label" default="Enabled" /></label>
				<div>
					<bs:checkBox name="enabled" value="${environmentInstance?.enabled}" />
					<span class="help-inline">${hasErrors(bean: environmentInstance, field: 'enabled', 'error')}</span>
				</div>
			</div>

--%>