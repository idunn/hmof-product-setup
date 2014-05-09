
<%@ page import="hmof.Cobj" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cobj.label', default: 'Cobj')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-cobj" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-cobj" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list cobj">
			
				<g:if test="${cobjInstance?.objectName}">
				<li class="fieldcontain">
					<span id="objectName-label" class="property-label"><g:message code="cobj.objectName.label" default="Object Name" /></span>
					
						<span class="property-value" aria-labelledby="objectName-label"><g:fieldValue bean="${cobjInstance}" field="objectName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.isbn}">
				<li class="fieldcontain">
					<span id="isbn-label" class="property-label"><g:message code="cobj.isbn.label" default="Isbn" /></span>
					
						<span class="property-value" aria-labelledby="isbn-label"><g:fieldValue bean="${cobjInstance}" field="isbn"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.pathToCoverImage}">
				<li class="fieldcontain">
					<span id="pathToCoverImage-label" class="property-label"><g:message code="cobj.pathToCoverImage.label" default="Path To Cover Image" /></span>
					
						<span class="property-value" aria-labelledby="pathToCoverImage-label"><g:fieldValue bean="${cobjInstance}" field="pathToCoverImage"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.teacherLabel}">
				<li class="fieldcontain">
					<span id="teacherLabel-label" class="property-label"><g:message code="cobj.teacherLabel.label" default="Teacher Label" /></span>
					
						<span class="property-value" aria-labelledby="teacherLabel-label"><g:fieldValue bean="${cobjInstance}" field="teacherLabel"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.teacherUrl}">
				<li class="fieldcontain">
					<span id="teacherUrl-label" class="property-label"><g:message code="cobj.teacherUrl.label" default="Teacher Url" /></span>
					
						<span class="property-value" aria-labelledby="teacherUrl-label"><g:fieldValue bean="${cobjInstance}" field="teacherUrl"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.studentLabel}">
				<li class="fieldcontain">
					<span id="studentLabel-label" class="property-label"><g:message code="cobj.studentLabel.label" default="Student Label" /></span>
					
						<span class="property-value" aria-labelledby="studentLabel-label"><g:fieldValue bean="${cobjInstance}" field="studentLabel"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.studentUrl}">
				<li class="fieldcontain">
					<span id="studentUrl-label" class="property-label"><g:message code="cobj.studentUrl.label" default="Student Url" /></span>
					
						<span class="property-value" aria-labelledby="studentUrl-label"><g:fieldValue bean="${cobjInstance}" field="studentUrl"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.objectType}">
				<li class="fieldcontain">
					<span id="objectType-label" class="property-label"><g:message code="cobj.objectType.label" default="Object Type" /></span>
					
						<span class="property-value" aria-labelledby="objectType-label"><g:fieldValue bean="${cobjInstance}" field="objectType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.objectReorderNumber}">
				<li class="fieldcontain">
					<span id="objectReorderNumber-label" class="property-label"><g:message code="cobj.objectReorderNumber.label" default="Object Reorder Number" /></span>
					
						<span class="property-value" aria-labelledby="objectReorderNumber-label"><g:fieldValue bean="${cobjInstance}" field="objectReorderNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.gradeLevel}">
				<li class="fieldcontain">
					<span id="gradeLevel-label" class="property-label"><g:message code="cobj.gradeLevel.label" default="Grade Level" /></span>
					
						<span class="property-value" aria-labelledby="gradeLevel-label"><g:fieldValue bean="${cobjInstance}" field="gradeLevel"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cobjInstance?.comments}">
				<li class="fieldcontain">
					<span id="comments-label" class="property-label"><g:message code="cobj.comments.label" default="Comments" /></span>
					
						<span class="property-value" aria-labelledby="comments-label"><g:fieldValue bean="${cobjInstance}" field="comments"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:cobjInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${cobjInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
