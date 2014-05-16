<%@ page import="hmof.Program" %>



<div class="fieldcontain ${hasErrors(bean: programInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="program.name.label" default="Name" />
		
	</label>
	<g:textField name="name" pattern="${programInstance.constraints.name.matches}" value="${programInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: programInstance, field: 'discipline', 'error')} ">
	<label for="discipline">
		<g:message code="program.discipline.label" default="Discipline" />
		
	</label>
	<g:select name="discipline" from="${programInstance.constraints.discipline.inList}" value="${programInstance?.discipline}" valueMessagePrefix="program.discipline" noSelection="['': '']"/>

</div>

<%--<div class="fieldcontain ${hasErrors(bean: programInstance, field: 'bundles', 'error')} ">
	<label for="bundles">
		<g:message code="program.bundles.label" default="Bundles" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${programInstance?.bundles?}" var="b">
    <li><g:link controller="bundle" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="bundle" action="create" params="['program.id': programInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'bundle.label', default: 'Bundle')])}</g:link>
</li>
</ul>


</div>

--%>