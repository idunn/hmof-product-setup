<div class="">
	<ul class="nav nav-tabs" data-role="listview" data-split-icon="gear" data-filter="true">


       <li	class="controller${params.controller == 'program' ? " active" : ""}">
			<g:link controller="Program" action="index">
						<g:message code="Program.Controller.label"/>
			</g:link>
		</li>
       <li	class="controller${params.controller == 'bundle' ? " active" : ""}">				
            <g:link controller="Bundle" action="index">
					<g:message code="Bundle.Controller.label"/>	
			</g:link>				
		</li>
		
		  <li	class="controller${params.controller == 'secureProgram' ? " active" : ""}">				
            <g:link controller="SecureProgram" action="index">
						<g:message code="SecureProgram.Controller.label"/>	
			</g:link>				
		</li>
		<li	class="controller${params.controller == 'commerceObject' ? " active" : ""}">				
            <g:link controller="CommerceObject" action="index">
						<g:message code="CommerceObject.Controller.label"/>	
			</g:link>				
		</li>
		<li	class="controller${params.controller == 'programXML' ? " active" : ""}">
			<g:link controller="ProgramXML" action="index">
						<g:message code="ProgramXML.Controller.label"/>
			</g:link>
		</li>
		</ul>
</div>
