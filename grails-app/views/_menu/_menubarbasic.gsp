<div class="">
	<ul class="nav nav-tabs" data-role="listview" data-split-icon="gear"
		data-filter="true">

		<g:each status="i" var="c"
			in="${grailsApplication.controllerClasses.sort { it.logicalPropertyName } }">
			
			<li
				class="controller${params.controller == c.logicalPropertyName ? " active" : ""}">
				<g:if
					test="${c.toString().contains('Bundle') || c.toString().contains('Secure') || c.toString().contains('Cobj') || c.toString().contains('Program') }">

					<g:link controller="${c.logicalPropertyName}" action="index">
						<g:message code="${c.logicalPropertyName}.label"
							default="${c.logicalPropertyName.capitalize()}" />
					</g:link>

				</g:if>
			</li>
		</g:each>
</div>
