<%@ page import="hmof.Bundle"%>

<section id="create-deployment">
	<g:hasErrors bean="${bundleInstance}">
		<div class="alert alert-error">
			<g:renderErrors bean="${bundleInstance}" as="list" />
		</div>
	</g:hasErrors>

	<g:if test="${flash.message}">
	Promote To Environment :${envName}<br><br>
		<div class="alert alert-info">
			${flash.message}
				<g:if test="${params.controller == 'bundle'}">
					
					${flash.message = null}
				</g:if> 
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
		</div>
	</g:if>
	<g:else>
   <g:if test="${envName  && (!envName.contains('QA') && !envName.contains('Production'))}">
	
	<sec:ifAnyGranted roles="ROLE_PM">
		<g:form controller="bundle" action="deploy" class="form-horizontal">
   
	
   
			<fieldset class="form">
				
					Deploy To Environment :${envName}<br><br>
					
				   ${msg}
				  
				
				<div class="modal-footer">
					
					<input name="programId" id="programId" type="hidden" value="${bundleInstance?.id}">
					<input name="depEnvId" id="depEnvId" type="hidden" value="${envId}">
					<button type="button" class="btn" data-dismiss="modal">Cancel</button>
					
				<sec:ifAnyGranted roles="ROLE_PM">
				<g:actionSubmit class="btn btn-primary" id="confirmTrue"
					value="Deploy" onClick="return true;" />
</sec:ifAnyGranted>

				</div>
			</fieldset>
	
		</g:form>
		</sec:ifAnyGranted>
		</g:if>
		<g:if test="${envName && (envName.contains('QA') || envName.contains('Production'))}">
		<sec:ifAnyGranted roles="ROLE_QA,ROLE_PROD">
		<g:form controller="bundle" action="promote" class="form-horizontal">
		
		
			<fieldset class="form">
				
					Promote To Environment :${envName}<br><br>
					
				
				   ${msg}
				  
				<div class="modal-footer">
					
					<input name="programId" id="programId" type="hidden" value="${bundleInstance?.id}">
					<input name="depEnvId" id="depEnvId" type="hidden" value="${envId}">
					<button type="button" class="btn" data-dismiss="modal">Cancel</button>
					
	<g:actionSubmit class="btn btn-primary" id="confirmTrue" value="Promote" onClick="return true;"/>

				</div>
			</fieldset>
			</g:form>
		</sec:ifAnyGranted>
		</g:if>
	</g:else>
</section>