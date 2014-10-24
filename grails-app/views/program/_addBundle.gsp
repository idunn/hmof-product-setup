<%@ page import="hmof.Program"%>

<div>		
		<g:link controller="bundle" action="create"
				params="['program.id': programInstance?.id]">
				${message(code: 'default.add.label', args: [message(code: 'bundle.label', default: 'Bundle')])}
			</g:link>
	

	<span class="help-inline"> ${hasErrors(bean: programInstance, field: 'bundles', 'error')}
	</span>
</div>


