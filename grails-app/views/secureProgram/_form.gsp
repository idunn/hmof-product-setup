<%@ page import="hmof.SecureProgram" %>
<% def year = new Date().getAt(Calendar.YEAR) %>


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
					<%--<g:textField class="form-control" name="copyright" --%>
					<g:select class="form-control" name="copyright" from="${year-1..year+6}"
					value="${secureProgramInstance?.copyright}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'labelForOnlineResource', 'error')} ">
				<label for="labelForOnlineResource" class="control-label"><g:message code="secureProgram.labelForOnlineResource.label" default="Label For Online Resource" /></label>
				<div>
					<g:textField class="form-control" name="labelForOnlineResource" value="${secureProgramInstance?.labelForOnlineResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'labelForOnlineResource', 'error')}</span>
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

			<div class="${hasErrors(bean: secureProgramInstance, field: 'labelForTeacherAdditionalResource', 'error')} ">
				<label for="labelForTeacherAdditionalResource" class="control-label"><g:message code="secureProgram.labelForTeacherAdditionalResource.label" default="Label For Teacher Additional Resource" /></label>
				<div>
					<g:textField class="form-control" name="labelForTeacherAdditionalResource" value="${secureProgramInstance?.labelForTeacherAdditionalResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'labelForTeacherAdditionalResource', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'pathToTeacherAdditionalResource', 'error')} ">
				<label for="pathToTeacherAdditionalResource" class="control-label"><g:message code="secureProgram.pathToTeacherAdditionalResource.label" default="Path To Teacher Additional Resource" /></label>
				<div>
					<g:textField class="form-control" name="pathToTeacherAdditionalResource" value="${secureProgramInstance?.pathToTeacherAdditionalResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToTeacherAdditionalResource', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'labelForStudentAdditionalResource', 'error')} ">
				<label for="labelForStudentAdditionalResource" class="control-label"><g:message code="secureProgram.labelForStudentAdditionalResource.label" default="Label For Student Additional Resource" /></label>
				<div>
					<g:textField class="form-control" name="labelForStudentAdditionalResource" value="${secureProgramInstance?.labelForStudentAdditionalResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'labelForStudentAdditionalResource', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'pathToStudentAdditionalResource', 'error')} ">
				<label for="pathToStudentAdditionalResource" class="control-label"><g:message code="secureProgram.pathToStudentAdditionalResource.label" default="Path To Student Additional Resource" /></label>
				<div>
					<g:textField class="form-control" name="pathToStudentAdditionalResource" value="${secureProgramInstance?.pathToStudentAdditionalResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToStudentAdditionalResource', 'error')}</span>
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

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knewtonProduct', 'error')} ">
				<label for="knewtonProduct" class="control-label"><g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" /></label>
				<div>
					<bs:checkBox name="knewtonProduct" value="${secureProgramInstance?.knewtonProduct}" />
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knewtonProduct', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdDev', 'error')} ">
				<label for="knowledgeGraphIdDev" class="control-label"><g:message code="secureProgram.knowledgeGraphIdDev.label" default="Knowledge Graph Id Dev" /></label>
				<div>
					<g:textField class="form-control" name="knowledgeGraphIdDev" value="${secureProgramInstance?.knowledgeGraphIdDev}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdDev', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdQA', 'error')} ">
				<label for="knowledgeGraphIdQA" class="control-label"><g:message code="secureProgram.knowledgeGraphIdQA.label" default="Knowledge Graph Id QA" /></label>
				<div>
					<g:textField class="form-control" name="knowledgeGraphIdQA" value="${secureProgramInstance?.knowledgeGraphIdQA}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdQA', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdProd', 'error')} ">
				<label for="knowledgeGraphIdProd" class="control-label"><g:message code="secureProgram.knowledgeGraphIdProd.label" default="Knowledge Graph Id Prod" /></label>
				<div>
					<g:textField class="form-control" name="knowledgeGraphIdProd" value="${secureProgramInstance?.knowledgeGraphIdProd}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdProd', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphWarmUpTimeLimit', 'error')} ">
				<label for="knowledgeGraphWarmUpTimeLimit" class="control-label"><g:message code="secureProgram.knowledgeGraphWarmUpTimeLimit.label" default="Knowledge Graph Warm Up Time Limit" /></label>
				<div>
					<g:textField class="form-control" name="knowledgeGraphWarmUpTimeLimit" value="${secureProgramInstance?.knowledgeGraphWarmUpTimeLimit}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphWarmUpTimeLimit', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentTimeLimit', 'error')} ">
				<label for="knowledgeGraphEnrichmentTimeLimit" class="control-label"><g:message code="secureProgram.knowledgeGraphEnrichmentTimeLimit.label" default="Knowledge Graph Enrichment Time Limit" /></label>
				<div>
					<g:textField class="form-control" name="knowledgeGraphEnrichmentTimeLimit" value="${secureProgramInstance?.knowledgeGraphEnrichmentTimeLimit}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentTimeLimit', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentCbiTimeLimit', 'error')} ">
				<label for="knowledgeGraphEnrichmentCbiTimeLimit" class="control-label"><g:message code="secureProgram.knowledgeGraphEnrichmentCbiTimeLimit.label" default="Knowledge Graph Enrichment Cbi Time Limit" /></label>
				<div>
					<g:textField class="form-control" name="knowledgeGraphEnrichmentCbiTimeLimit" value="${secureProgramInstance?.knowledgeGraphEnrichmentCbiTimeLimit}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentCbiTimeLimit', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'comments', 'error')} ">
				<label for="comments" class="control-label"><g:message code="secureProgram.comments.label" default="Comments" /></label>
				<div>
					<g:textArea class="form-control" name="comments" cols="40" rows="5" maxlength="255" value="${secureProgramInstance?.comments}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'comments', 'error')}</span>
				</div>
			</div>

