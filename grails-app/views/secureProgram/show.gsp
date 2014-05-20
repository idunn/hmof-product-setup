
<%@ page import="hmof.SecureProgram" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'secureProgram.label', default: 'SecureProgram')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-secureProgram" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.productName.label" default="Product Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "productName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.registrationIsbn.label" default="Registration Isbn" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "registrationIsbn")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.onlineIsbn.label" default="Online Isbn" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "onlineIsbn")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.copyright.label" default="Copyright" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "copyright")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.labelLink.label" default="Label Link" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelLink")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToResource.label" default="Path To Resource" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToResource")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToCoverImage.label" default="Path To Cover Image" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToCoverImage")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secureProgramInstance?.knewtonProduct}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.securityWord.label" default="Security Word" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "securityWord")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.securityWordLocation.label" default="Security Word Location" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "securityWordLocation")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.securityWordPage.label" default="Security Word Page" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "securityWordPage")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.includeDashboardObject.label" default="Include Dashboard Object" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secureProgramInstance?.includeDashboardObject}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.includeEplannerObject.label" default="Include Eplanner Object" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secureProgramInstance?.includeEplannerObject}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.comments.label" default="Comments" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "comments")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${secureProgramInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${secureProgramInstance?.lastUpdated}" /></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
