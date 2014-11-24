<!-- Modal HTML -->
<div id="confirmbox" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">Confirmation</h4>
			</div>
			<div class="modal-body">
				<p id="confirmMessage">Any confirmation message?</p>
			</div>
			<div class="modal-footer">
				<button class="btn" id="confirmFalse">Cancel</button>
<sec:ifAnyGranted roles="ROLE_PM">
				<g:actionSubmit class="btn btn-primary" id="confirmTrue"
					value="Deploy" onClick="return true;" />
</sec:ifAnyGranted>

<sec:ifAnyGranted roles="ROLE_QA, ROLE_PROD">
	<g:actionSubmit class="btn btn-primary" id="confirmTrue" value="Promote" onClick="return true;"/>
</sec:ifAnyGranted>

			</div>
		</div>
	</div>
</div>