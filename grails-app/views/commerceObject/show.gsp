
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
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
<section id="show-commerceObject" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.objectName.label" default="Object Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "objectName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.isbnNumber.label" default="Isbn" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "isbnNumber")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${commerceObjectInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${commerceObjectInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.pathToCoverImage.label" default="Path To Cover Image" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "pathToCoverImage")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.teacherLabel.label" default="Teacher Label" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "teacherLabel")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.teacherUrl.label" default="Teacher Url" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "teacherUrl")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.studentLabel.label" default="Student Label" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "studentLabel")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.studentUrl.label" default="Student Url" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "studentUrl")}</td>
				
			</tr>
		<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.category.label" default="Category" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "category")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.objectType.label" default="Object Type" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "objectType")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.objectReorderNumber.label" default="Object Reorder Number" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "objectReorderNumber")}</td>
				
			</tr>
		<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.subject.label" default="Subject" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "subject")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.gradeLevel.label" default="Grade Level" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "gradeLevel")}</td>
				
			</tr>
		<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.objectType.label" default="Object Type" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "objectType")}</td>
				
			</tr>
			<%--<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.tabNavTab.label" default="TabNavTab" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "tabNavTab")}</td>
				
			</tr>
		--%><tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.isPremium.label" default="Is Premium" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "isPremium")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="commerceObject.comments.label" default="Comments" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: commerceObjectInstance, field: "comments")}</td>
				
			</tr>
		</tbody>
	</table>
</section>
</div>
</div>
</div>
</body>

</html>
