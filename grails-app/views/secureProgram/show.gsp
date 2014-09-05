
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
				<td valign="top" class="name"><g:message code="secureProgram.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${secureProgramInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${secureProgramInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.copyright.label" default="Copyright" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "copyright")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.labelForOnlineResource.label" default="Label For Online Resource" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForOnlineResource")}</td>
				
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
				<td valign="top" class="name"><g:message code="secureProgram.labelForTeacherAdditionalResource.label" default="Label For Teacher Additional Resource" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForTeacherAdditionalResource")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToTeacherAdditionalResource.label" default="Path To Teacher Additional Resource" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToTeacherAdditionalResource")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.labelForStudentAdditionalResource.label" default="Label For Student Additional Resource" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForStudentAdditionalResource")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToStudentAdditionalResource.label" default="Path To Student Additional Resource" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToStudentAdditionalResource")}</td>
				
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
				<td valign="top" class="name"><g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secureProgramInstance?.knewtonProduct}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphIdDev.label" default="Knowledge Graph Id Dev" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphIdDev")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphIdQA.label" default="Knowledge Graph Id QA" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphIdQA")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphIdProd.label" default="Knowledge Graph Id Prod" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphIdProd")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphWarmUpTimeLimit.label" default="Knowledge Graph Warm Up Time Limit" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphWarmUpTimeLimit")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphEnrichmentTimeLimit.label" default="Knowledge Graph Enrichment Time Limit" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphEnrichmentTimeLimit")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphEnrichmentCbiTimeLimit.label" default="Knowledge Graph Enrichment Cbi Time Limit" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphEnrichmentCbiTimeLimit")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.comments.label" default="Comments" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "comments")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.commerceObjects.label" default="Commerce Objects" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${secureProgramInstance.commerceObjects}" var="c">
						<li><g:link controller="commerceObject" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
