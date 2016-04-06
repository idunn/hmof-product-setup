<%@ page import="hmof.programxml.ProgramXML"%>
<script>function confirmbtn(){
	deployConfirm.confirmTrue.setAttribute('disabled','disabled');
	deployConfirm.submit();
	}</script>
<section id="create-deployment">

	<g:hasErrors bean="${programXMLInstance}">
		<div class="alert alert-error">
			<g:renderErrors bean="${programXMLInstance}" as="list" />
		</div>
	</g:hasErrors>

	<g:if test="${flash.message}">
	ProgramXMLPromote To Environment :${envName}<br><br>
		<div class="alert alert-info">
			${flash.message}
				<g:if test="${params.controller == 'programXML'}">
					
					${flash.message = null}
				</g:if> 
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
		</div>
	</g:if>
	<g:else>
   <g:if test="${envName && (!envName.contains('QA') && !envName.contains('Prod'))}">
	
	<sec:ifAnyGranted roles="ROLE_PM">
		<g:form controller="programXML" action="deploy" class="form-horizontal" name="deployConfirm">
   
	
   
			<fieldset class="form">
				
					ProgramXML Deploy To Environment :${envName}<br><br>
				
				   ${msg}
				  
				
				
				<div class="modal-footer">
					
					<input name="programId" id="programId" type="hidden" value="${programXMLInstance?.id}">
					<input name="depEnvId" id="depEnvId" type="hidden" value="${envId}">
					<button type="button" class="btn" data-dismiss="modal">Cancel</button>
					
				<sec:ifAnyGranted roles="ROLE_PM">
				<g:actionSubmit class="btn btn-primary" id="confirmTrue" value="Deploy" onClick="confirmbtn();" />
</sec:ifAnyGranted>

				</div>
			</fieldset>
	
		</g:form>
		</sec:ifAnyGranted>
		</g:if>
		<g:if test="${envName && (envName.contains('QA') || envName.contains('Production'))}">
		<sec:ifAnyGranted roles="ROLE_QA,ROLE_PROD">
		<g:form controller="programXML" action="promote" class="form-horizontal" name="deployConfirm">
		
		
			<fieldset class="form">
				
					ProgramXML Promote To Environment :${envName}<br><br>
					
				   ${msg}<br>
				Enter the JIRA Number :  <input name="jiraid" id="jiraid" type="text" value="">
				<div class="modal-footer">
					
					<input name="programId" id="programId" type="hidden" value="${programXMLInstance?.id}">
					<input name="depEnvId" id="depEnvId" type="hidden" value="${envId}">
					<button type="button" class="btn" data-dismiss="modal">Cancel</button>
					
	                <g:actionSubmit class="btn btn-primary" id="confirmTrue" value="Promote" onClick="confirmbtn();"/>

				</div>
			</fieldset>
			</g:form>
		</sec:ifAnyGranted>
		</g:if>
	</g:else>
	
	
	
</section>