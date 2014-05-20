<%@ page import="hmof.SecureProgram" %>



			<div class="${hasErrors(bean: secureProgramInstance, field: 'productName', 'error')} required">
				<label for="productName" class="control-label"><g:message code="secureProgram.productName.label" default="Product Name" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="productName" required="" value="${secureProgramInstance?.productName}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'productName', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'registrationIsbn', 'error')} required">
				<label for="registrationIsbn" class="control-label"><g:message code="secureProgram.registrationIsbn.label" default="Registration Isbn" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="registrationIsbn" required="" value="${secureProgramInstance?.registrationIsbn}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'registrationIsbn', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'onlineIsbn', 'error')} required">
				<label for="onlineIsbn" class="control-label"><g:message code="secureProgram.onlineIsbn.label" default="Online Isbn" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="onlineIsbn" required="" value="${secureProgramInstance?.onlineIsbn}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'onlineIsbn', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error')} ">
				<label for="copyright" class="control-label"><g:message code="secureProgram.copyright.label" default="Copyright" /></label>
				<div>
					<g:field class="form-control" name="copyright" type="number" value="${secureProgramInstance.copyright}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'labelLink', 'error')} ">
				<label for="labelLink" class="control-label"><g:message code="secureProgram.labelLink.label" default="Label Link" /></label>
				<div>
					<g:textField class="form-control" name="labelLink" value="${secureProgramInstance?.labelLink}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'labelLink', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'pathToResource', 'error')} ">
				<label for="pathToResource" class="control-label"><g:message code="secureProgram.pathToResource.label" default="Path To Resource" /></label>
				<div>
					<g:textField class="form-control" name="pathToResource" value="${secureProgramInstance?.pathToResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToResource', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'pathToCoverImage', 'error')} ">
				<label for="pathToCoverImage" class="control-label"><g:message code="secureProgram.pathToCoverImage.label" default="Path To Cover Image" /></label>
				<div>
					<g:textField class="form-control" name="pathToCoverImage" value="${secureProgramInstance?.pathToCoverImage}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToCoverImage', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knewtonProduct', 'error')} ">
				<label for="knewtonProduct" class="control-label"><g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" /></label>
				<div>
					<bs:checkBox name="knewtonProduct" value="${secureProgramInstance?.knewtonProduct}" />
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knewtonProduct', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'securityWord', 'error')} required">
				<label for="securityWord" class="control-label"><g:message code="secureProgram.securityWord.label" default="Security Word" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="securityWord" required="" value="${secureProgramInstance?.securityWord}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'securityWord', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'securityWordLocation', 'error')} required">
				<label for="securityWordLocation" class="control-label"><g:message code="secureProgram.securityWordLocation.label" default="Security Word Location" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="securityWordLocation" required="" value="${secureProgramInstance?.securityWordLocation}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'securityWordLocation', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'securityWordPage', 'error')} required">
				<label for="securityWordPage" class="control-label"><g:message code="secureProgram.securityWordPage.label" default="Security Word Page" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="securityWordPage" required="" value="${secureProgramInstance?.securityWordPage}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'securityWordPage', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'includeDashboardObject', 'error')} ">
				<label for="includeDashboardObject" class="control-label"><g:message code="secureProgram.includeDashboardObject.label" default="Include Dashboard Object" /></label>
				<div>
					<bs:checkBox name="includeDashboardObject" value="${secureProgramInstance?.includeDashboardObject}" />
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'includeDashboardObject', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'includeEplannerObject', 'error')} ">
				<label for="includeEplannerObject" class="control-label"><g:message code="secureProgram.includeEplannerObject.label" default="Include Eplanner Object" /></label>
				<div>
					<bs:checkBox name="includeEplannerObject" value="${secureProgramInstance?.includeEplannerObject}" />
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'includeEplannerObject', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'comments', 'error')} ">
				<label for="comments" class="control-label"><g:message code="secureProgram.comments.label" default="Comments" /></label>
				<div>
					<g:textArea class="form-control" name="comments" cols="40" rows="5" maxlength="255" value="${secureProgramInstance?.comments}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'comments', 'error')}</span>
				</div>
			</div>

