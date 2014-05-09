
<%@ page import="hmof.SecureProgram" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'secureProgram.label', default: 'SecureProgram')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-secureProgram" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-secureProgram" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list secureProgram">
			
				<g:if test="${secureProgramInstance?.productName}">
				<li class="fieldcontain">
					<span id="productName-label" class="property-label"><g:message code="secureProgram.productName.label" default="Product Name" /></span>
					
						<span class="property-value" aria-labelledby="productName-label"><g:fieldValue bean="${secureProgramInstance}" field="productName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.registrationIsbn}">
				<li class="fieldcontain">
					<span id="registrationIsbn-label" class="property-label"><g:message code="secureProgram.registrationIsbn.label" default="Registration Isbn" /></span>
					
						<span class="property-value" aria-labelledby="registrationIsbn-label"><g:fieldValue bean="${secureProgramInstance}" field="registrationIsbn"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.onlineIsbn}">
				<li class="fieldcontain">
					<span id="onlineIsbn-label" class="property-label"><g:message code="secureProgram.onlineIsbn.label" default="Online Isbn" /></span>
					
						<span class="property-value" aria-labelledby="onlineIsbn-label"><g:fieldValue bean="${secureProgramInstance}" field="onlineIsbn"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.copyright}">
				<li class="fieldcontain">
					<span id="copyright-label" class="property-label"><g:message code="secureProgram.copyright.label" default="Copyright" /></span>
					
						<span class="property-value" aria-labelledby="copyright-label"><g:fieldValue bean="${secureProgramInstance}" field="copyright"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.labelLink}">
				<li class="fieldcontain">
					<span id="labelLink-label" class="property-label"><g:message code="secureProgram.labelLink.label" default="Label Link" /></span>
					
						<span class="property-value" aria-labelledby="labelLink-label"><g:fieldValue bean="${secureProgramInstance}" field="labelLink"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.pathToResource}">
				<li class="fieldcontain">
					<span id="pathToResource-label" class="property-label"><g:message code="secureProgram.pathToResource.label" default="Path To Resource" /></span>
					
						<span class="property-value" aria-labelledby="pathToResource-label"><g:fieldValue bean="${secureProgramInstance}" field="pathToResource"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.pathToCoverImage}">
				<li class="fieldcontain">
					<span id="pathToCoverImage-label" class="property-label"><g:message code="secureProgram.pathToCoverImage.label" default="Path To Cover Image" /></span>
					
						<span class="property-value" aria-labelledby="pathToCoverImage-label"><g:fieldValue bean="${secureProgramInstance}" field="pathToCoverImage"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.knewtonProduct}">
				<li class="fieldcontain">
					<span id="knewtonProduct-label" class="property-label"><g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" /></span>
					
						<span class="property-value" aria-labelledby="knewtonProduct-label"><g:formatBoolean boolean="${secureProgramInstance?.knewtonProduct}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.securityWord}">
				<li class="fieldcontain">
					<span id="securityWord-label" class="property-label"><g:message code="secureProgram.securityWord.label" default="Security Word" /></span>
					
						<span class="property-value" aria-labelledby="securityWord-label"><g:fieldValue bean="${secureProgramInstance}" field="securityWord"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.securityWordLocation}">
				<li class="fieldcontain">
					<span id="securityWordLocation-label" class="property-label"><g:message code="secureProgram.securityWordLocation.label" default="Security Word Location" /></span>
					
						<span class="property-value" aria-labelledby="securityWordLocation-label"><g:fieldValue bean="${secureProgramInstance}" field="securityWordLocation"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.securityWordPage}">
				<li class="fieldcontain">
					<span id="securityWordPage-label" class="property-label"><g:message code="secureProgram.securityWordPage.label" default="Security Word Page" /></span>
					
						<span class="property-value" aria-labelledby="securityWordPage-label"><g:fieldValue bean="${secureProgramInstance}" field="securityWordPage"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.includeDashboardObject}">
				<li class="fieldcontain">
					<span id="includeDashboardObject-label" class="property-label"><g:message code="secureProgram.includeDashboardObject.label" default="Include Dashboard Object" /></span>
					
						<span class="property-value" aria-labelledby="includeDashboardObject-label"><g:formatBoolean boolean="${secureProgramInstance?.includeDashboardObject}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.includeEplannerObject}">
				<li class="fieldcontain">
					<span id="includeEplannerObject-label" class="property-label"><g:message code="secureProgram.includeEplannerObject.label" default="Include Eplanner Object" /></span>
					
						<span class="property-value" aria-labelledby="includeEplannerObject-label"><g:formatBoolean boolean="${secureProgramInstance?.includeEplannerObject}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.comments}">
				<li class="fieldcontain">
					<span id="comments-label" class="property-label"><g:message code="secureProgram.comments.label" default="Comments" /></span>
					
						<span class="property-value" aria-labelledby="comments-label"><g:fieldValue bean="${secureProgramInstance}" field="comments"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${secureProgramInstance?.commerceObjects}">
				<li class="fieldcontain">
					<span id="commerceObjects-label" class="property-label"><g:message code="secureProgram.commerceObjects.label" default="Commerce Objects" /></span>
					
						<g:each in="${secureProgramInstance.commerceObjects}" var="c">
						<span class="property-value" aria-labelledby="commerceObjects-label"><g:link controller="cobj" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:secureProgramInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${secureProgramInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
