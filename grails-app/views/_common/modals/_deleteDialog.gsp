<!-- 
This is the standard dialog that initiates the delete action.
-->

<!-- Modal dialog -->
<div id="DeleteModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="DeleteModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3 id="DeleteModalLabel"><g:message code="default.button.delete.confirm.title" args="[entityName]" default="Delete Item"/></h3>
			</div>
			<div class="modal-body">
				<p><g:message code="default.button.delete.confirm.message" args="[entityName]" default="Do you really want to delete this item?"/>
				
				<g:if test="${entityName == "Bundle"}">
					<g:if test="${bundleInstance.secureProgram}">
						<p><g:message code="default.bundle.children" default="Secure Programs will not be deleted:"/></p>						
						<g:each in="${bundleInstance.secureProgram}" status="i" var="bundleChildren">
							<ul><li>${bundleChildren}</li></ul>
						</g:each>
					</g:if>
				</g:if>
				
				<g:if test="${entityName == "SecureProgram"}">					
					<g:if test="${parentBundles}">
						<p><g:message code="default.secureProgram.parent" default="This Secure Program is being used by the following Bundles that will not be deleted:"/></p>						
						<g:each in="${parentBundles}" status="i" var="parentBundlesList">
							<ul><li>${parentBundlesList}</li></ul>
						</g:each>
					</g:if>
					<br />
					<g:if test="${secureProgramInstance.commerceObjects}">
						<p><g:message code="default.secureProgram.children" default="This Secure program has the following Commerce Objects that will not be deleted"/></p>						
						<g:each in="${secureProgramInstance.commerceObjects}" status="i" var="secureProgramChildren">
							<ul><li>${secureProgramChildren}</li></ul>
						</g:each>
					</g:if>
				</g:if>
				
				<g:if test="${entityName == "CommerceObject"}">
					<g:if test="${parentSecureProgram}">
						<p><g:message code="default.commerceObject.parent" default="This Commerce Object is being used by the following Secure Programs that will not be deleted:"/></p>						
						<g:each in="${parentSecureProgram}" status="i" var="parentSecureProgramList">
							<ul><li>${parentSecureProgramList}</li></ul>
						</g:each>
					</g:if>
				</g:if>
				
				</p>
			</div>
			<div class="modal-footer">
				<g:form>
					<button class="btn" data-dismiss="modal" aria-hidden="true"><g:message code="default.button.cancel.label" default="Cancel"/></button>
					<g:hiddenField name="id" value="${item ? item.id : params.id}" />
					<g:hiddenField name="_method" value="DELETE" />
					<span class="button"><g:actionSubmit class="btn btn-danger" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"/></span>
				</g:form>
			</div>
		</div>
	</div>
</div>