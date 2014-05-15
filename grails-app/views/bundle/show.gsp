
<%@ page import="hmof.Bundle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bundle.label', default: 'Bundle')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bundle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bundle" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bundle">
			
				<g:if test="${bundleInstance?.isbn}">
				<li class="fieldcontain">
					<span id="isbn-label" class="property-label"><g:message code="bundle.isbn.label" default="Isbn" /></span>
					
						<span class="property-value" aria-labelledby="isbn-label"><g:fieldValue bean="${bundleInstance}" field="isbn"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bundleInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="bundle.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${bundleInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bundleInstance?.duration}">
				<li class="fieldcontain">
					<span id="duration-label" class="property-label"><g:message code="bundle.duration.label" default="Duration" /></span>
					
						<span class="property-value" aria-labelledby="duration-label"><g:fieldValue bean="${bundleInstance}" field="duration"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bundleInstance?.commerceObjects}">
				<li class="fieldcontain">
					<span id="commerceObjects-label" class="property-label"><g:message code="bundle.commerceObjects.label" default="Commerce Objects" /></span>
					
						<g:each in="${bundleInstance.commerceObjects}" var="c">
						<span class="property-value" aria-labelledby="commerceObjects-label"><g:link controller="cobj" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${bundleInstance?.program}">
				<li class="fieldcontain">
					<span id="program-label" class="property-label"><g:message code="bundle.program.label" default="Program" /></span>
					
						<span class="property-value" aria-labelledby="program-label"><g:link controller="program" action="show" id="${bundleInstance?.program?.id}">${bundleInstance?.program?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${bundleInstance?.secureProgram}">
				<li class="fieldcontain">
					<span id="secureProgram-label" class="property-label"><g:message code="bundle.secureProgram.label" default="Secure Program" /></span>
					
						<g:each in="${bundleInstance.secureProgram}" var="s">
						<span class="property-value" aria-labelledby="secureProgram-label"><g:link controller="secureProgram" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:bundleInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${bundleInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
