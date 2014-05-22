<%@ page import="hmof.Program"%>

<div>

	
		<%--<g:each in="${programInstance?.bundles?}" var="b">
				<li><g:link controller="bundle" action="show" id="${b.id}">
						${b?.encodeAsHTML()}
					</g:link></li>
			</g:each>
			--%>
		<g:link controller="bundle" action="create"
				params="['program.id': programInstance?.id]">
				${message(code: 'default.add.label', args: [message(code: 'bundle.label', default: 'Bundle')])}
			</g:link>
	

	<span class="help-inline"> ${hasErrors(bean: programInstance, field: 'bundles', 'error')}
	</span>
</div>


