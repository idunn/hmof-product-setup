<%@ page import="hmof.SecureProgram" %>



<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'productName', 'error')} required">
	<label for="productName">
		<g:message code="secureProgram.productName.label" default="Product Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="productName" required="" value="${secureProgramInstance?.productName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'registrationIsbn', 'error')} required">
	<label for="registrationIsbn">
		<g:message code="secureProgram.registrationIsbn.label" default="Registration Isbn" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="registrationIsbn" required="" value="${secureProgramInstance?.registrationIsbn}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'onlineIsbn', 'error')} required">
	<label for="onlineIsbn">
		<g:message code="secureProgram.onlineIsbn.label" default="Online Isbn" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="onlineIsbn" required="" value="${secureProgramInstance?.onlineIsbn}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error')} ">
	<label for="copyright">
		<g:message code="secureProgram.copyright.label" default="Copyright" />
		
	</label>
	<g:field name="copyright" type="number" value="${secureProgramInstance.copyright}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelLink', 'error')} ">
	<label for="labelLink">
		<g:message code="secureProgram.labelLink.label" default="Label Link" />
		
	</label>
	<g:textField name="labelLink" value="${secureProgramInstance?.labelLink}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToResource', 'error')} ">
	<label for="pathToResource">
		<g:message code="secureProgram.pathToResource.label" default="Path To Resource" />
		
	</label>
	<g:textField name="pathToResource" value="${secureProgramInstance?.pathToResource}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToCoverImage', 'error')} ">
	<label for="pathToCoverImage">
		<g:message code="secureProgram.pathToCoverImage.label" default="Path To Cover Image" />
		
	</label>
	<g:textField name="pathToCoverImage" value="${secureProgramInstance?.pathToCoverImage}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knewtonProduct', 'error')} ">
	<label for="knewtonProduct">
		<g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" />
		
	</label>
	<g:checkBox name="knewtonProduct" value="${secureProgramInstance?.knewtonProduct}" />

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'securityWord', 'error')} required">
	<label for="securityWord">
		<g:message code="secureProgram.securityWord.label" default="Security Word" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="securityWord" required="" value="${secureProgramInstance?.securityWord}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'securityWordLocation', 'error')} required">
	<label for="securityWordLocation">
		<g:message code="secureProgram.securityWordLocation.label" default="Security Word Location" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="securityWordLocation" required="" value="${secureProgramInstance?.securityWordLocation}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'securityWordPage', 'error')} required">
	<label for="securityWordPage">
		<g:message code="secureProgram.securityWordPage.label" default="Security Word Page" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="securityWordPage" required="" value="${secureProgramInstance?.securityWordPage}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'includeDashboardObject', 'error')} ">
	<label for="includeDashboardObject">
		<g:message code="secureProgram.includeDashboardObject.label" default="Include Dashboard Object" />
		
	</label>
	<g:checkBox name="includeDashboardObject" value="${secureProgramInstance?.includeDashboardObject}" />

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'includeEplannerObject', 'error')} ">
	<label for="includeEplannerObject">
		<g:message code="secureProgram.includeEplannerObject.label" default="Include Eplanner Object" />
		
	</label>
	<g:checkBox name="includeEplannerObject" value="${secureProgramInstance?.includeEplannerObject}" />

</div>

<div class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'comments', 'error')} ">
	<label for="comments">
		<g:message code="secureProgram.comments.label" default="Comments" />
		
	</label>
	<g:textArea name="comments" cols="40" rows="5" maxlength="255" value="${secureProgramInstance?.comments}"/>

</div>

