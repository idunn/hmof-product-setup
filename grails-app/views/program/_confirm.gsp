<%@ page import="hmof.Program"%>
<script>function confirmbtn(){
	deployConfirm.confirmTrue.setAttribute('disabled','disabled');
	deployConfirm.submit();
	}</script>
<section id="create-deployment">
	<g:hasErrors bean="${programInstance}">
		<div class="alert alert-error">
			<g:renderErrors bean="${programInstance}" as="list" />
		</div>
	</g:hasErrors>

	<g:if test="${flash.message}">
	Promote To Environment :${envName}<br><br>
		<div class="alert alert-info">
			${flash.message}
				<g:if test="${params.controller == 'program'}">
					
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
		<g:form controller="program" action="deploy" class="form-horizontal" name="deployConfirm">
   
	
   
			<fieldset class="form">
				
					Deploy To Environment :${envName}<br><br>
					<g:if test="${msg == 'deployMessage1'}">
					A job with the same revision already exists on the environment. <br><br> Execute as a Smart Deployment  <input type="checkbox" name="doesPreviousJobExist1" value="true" checked/>  <a class="my-tool-tip" data-toggle="tooltip" data-placement="left" data-container="body" title="A Smart Deployment will only deploy:\n  -   bundles which have not previously been deployed\n  -   bundles which have been modified since the last successful deployment \nChoosing this option will improve the deployment time of your job.\n This option is recommended."><i class="glyphicon glyphicon-info-sign"></i></a><br><br> Do you want to proceed? 
				   </g:if>
				   <g:elseif test="${msg == 'deployMessage2'}">
				   Execute as a Smart Deployment   <input type=checkbox name=doesPreviousJobExist1 value=true checked/> <a class=my-tool-tip data-toggle=tooltip data-placement=left data-container=body title=A Smart Deployment will only deploy:\n  -   bundles which have not previously been deployed\n  -   bundles which have been modified since the last successful deployment \nChoosing this option will improve the deployment time of your job.\n This option is recommended.><i class=glyphicon glyphicon-info-sign></i></a> <br><br>Are you sure you want to deploy?
				   </g:elseif>
				   <g:else>
				   ${msg}
				   </g:else>
				
				
				<div class="modal-footer">
					
					<input name="programId" id="programId" type="hidden" value="${programInstance?.id}">
					<input name="depEnvId" id="depEnvId" type="hidden" value="${envId}">
					<button type="button" class="btn" data-dismiss="modal">Cancel</button>
					
				<sec:ifAnyGranted roles="ROLE_PM">
				<g:actionSubmit class="btn btn-primary" id="confirmTrue"
					value="Deploy" onClick="confirmbtn();" />
        </sec:ifAnyGranted>

				</div>
			</fieldset>
	
		</g:form>
		</sec:ifAnyGranted>
		</g:if>
		<g:if test="${envName && (envName.contains('QA') || envName.contains('Production'))}">
		<sec:ifAnyGranted roles="ROLE_QA,ROLE_PROD">
		<g:form controller="program" action="promote" class="form-horizontal" name="deployConfirm">
		
		
			<fieldset class="form">
				
					Promote To Environment :${envName}<br><br>
					
					<g:if test="${msg == 'promoteMessage1'}">
					A job with the same revision already exists on the environment. <br><br> Execute as a Smart Deployment  <input type="checkbox" name="doesPreviousJobExist1" value="true" checked/>  <a class="my-tool-tip" data-toggle="tooltip" data-placement="left" data-container="body" title="A Smart Deployment will only deploy:\n  -   bundles which have not previously been deployed\n  -   bundles which have been modified since the last successful deployment \nChoosing this option will improve the deployment time of your job.\n This option is recommended."><i class="glyphicon glyphicon-info-sign"></i></a><br><br> Do you want to proceed? 
				   </g:if>
				   <g:elseif test="${msg == 'promoteMessage2'}">
				   Execute as a Smart Deployment   <input type=checkbox name=doesPreviousJobExist1 value=true checked/> <a class=my-tool-tip data-toggle=tooltip data-placement=left data-container=body title=A Smart Deployment will only deploy:\n  -   bundles which have not previously been deployed\n  -   bundles which have been modified since the last successful deployment \nChoosing this option will improve the deployment time of your job.\n This option is recommended.><i class=glyphicon glyphicon-info-sign></i></a> <br><br>Are you sure you want to promote?
				   </g:elseif>
				   <g:else>
				   ${msg}
				   </g:else>
				<div class="modal-footer">
					
					<input name="programId" id="programId" type="hidden" value="${programInstance?.id}">
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