
<%@ page import="hmof.Cobj" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'cobj.label', default: 'Cobj')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-cobj" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.objectName.label" default="Object Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "objectName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.isbn.label" default="Isbn" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "isbn")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.pathToCoverImage.label" default="Path To Cover Image" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "pathToCoverImage")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.teacherLabel.label" default="Teacher Label" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "teacherLabel")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.teacherUrl.label" default="Teacher Url" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "teacherUrl")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.studentLabel.label" default="Student Label" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "studentLabel")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.studentUrl.label" default="Student Url" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "studentUrl")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.objectType.label" default="Object Type" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "objectType")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.objectReorderNumber.label" default="Object Reorder Number" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "objectReorderNumber")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.gradeLevel.label" default="Grade Level" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "gradeLevel")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.comments.label" default="Comments" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: cobjInstance, field: "comments")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${cobjInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="cobj.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${cobjInstance?.lastUpdated}" /></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
