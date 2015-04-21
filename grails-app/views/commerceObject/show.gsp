
<%@ page import="hmof.CommerceObject" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'commerceObject.label', default: 'CommerceObject')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
</head>

<body>
<style>
	@font-face {
font-family: 'Glyphicons Halflings';
src: url('../../fonts/glyphicons-halflings-regular.eot');
src: url('../../fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded- opentype'), 
     url('../../fonts/glyphicons-halflings-regular.woff') format('woff'), 
     url('../../fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('../../fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg');
} 
</style>
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
<section id="show-commerceObject" class="first">

	<div class="span7 form-horizontal">

	<section>
<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.objectName.label" default="Object Name" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "objectName")}</div></div>
				
				<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.isbnNumber.label" default="Isbn" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "isbnNumber")}</div></div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.pathToCoverImage.label" default="Path To Cover Image" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "pathToCoverImage")}</div></div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.teacherLabel.label" default="Teacher Label" /></label>
				
				<div class="controls show-style wordwrap">${fieldValue(bean: commerceObjectInstance, field: "teacherLabel")}</div></div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.teacherUrl.label" default="Teacher Url" /></label>
				
				<div class="controls show-style wordwrap" >${fieldValue(bean: commerceObjectInstance, field: "teacherUrl")}</div></div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.studentLabel.label" default="Student Label" /></label>
				
				<div class="controls show-style wordwrap">${fieldValue(bean: commerceObjectInstance, field: "studentLabel")}</div></div>
		
		<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.studentUrl.label" default="Student Url" /></label>
				
				<div class="controls show-style wordwrap">${fieldValue(bean: commerceObjectInstance, field: "studentUrl")}</div></div>
		
		</section>
		</div>
		<div class="span5 form-horizontal">
	<section>
		
		<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.category.label" default="Category" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "category")}</div></div>
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.objectType.label" default="Object Type" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "objectType")}</div></div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.objectReorderNumber.label" default="Resource Order" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "objectReorderNumber")}</div></div>
		<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.subject.label" default="Subject" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "subject")}</div></div>
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.gradeLevel.label" default="Grade Level" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "gradeLevel")}</div></div>
		
		<%--
		<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.tabNavTab.label" default="TabNavTab" /></td>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "tabNavTab")}</td>
				
		</tr>
		--%>
		<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.isPremium.label" default="Is Premium" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "isPremium")}</div></div>
			
			</section>
		</div>
<div class="span9 form-horizontal">
	<section>	
			
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.comments.label" default="Comments" /></label>
				
				<div class="controls show-style">${fieldValue(bean: commerceObjectInstance, field: "comments")}</div></div>
			
			<!-- Date Stamps -->
			
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.dateCreated.label" default="Date Created" /></label>
				
				<div class="controls show-style"><g:formatDate date="${commerceObjectInstance?.dateCreated}" /></div></div>
		
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.lastUpdated.label" default="Last Updated" /></label>
				
				<div class="controls show-style"><g:formatDate date="${commerceObjectInstance?.lastUpdated}" /></div></div>
			
			<!-- Added Parent Object Links -->
			<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message code="commerceObject.secureprograms.label" default="Secure Programs (Parent Objects)" /></label>
				
				<div class="controls show-style" style="text-align: left;" >
					<ul>
					<g:each in="${parentSecureProgram}" var="s">
						<li><g:link controller="secureProgram" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</div></div>
			</section>
		</div>
</section>
</div>
</div>
</div>
</body>

</html>
