
<%@ page import="hmof.SecureProgram" %>
<!DOCTYPE html>
<html>
	<head>
<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'secureProgram.label', default: 'SecureProgram')}" />
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
				<td valign="top" class="name"><g:message code="secureProgram.curriculumArea.label" default="Curriculum Area" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "curriculumArea")}</td>
				
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
				<td valign="top" class="name"><g:message code="secureProgram.includeDashboardObject.label" default="Include Dashboard Object" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secureProgramInstance?.includeDashboardObject}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.includeEplannerObject.label" default="Include Eplanner Object" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secureProgramInstance?.includeEplannerObject}" /></td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.includeNotebookObject.label" default="Include Notebook Object" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secureProgramInstance?.includeNotebookObject}" /></td>
				
			</tr>
			
			
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.essayGraderPrompts.label" default="Essay Grader Prompts" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "essayGraderPrompts")}</td>
				
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
				<td valign="top" class="name"><g:message code="secureProgram.labelForTeacherAdditionalResource2.label" default="Label For Teacher Additional Resource2" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForTeacherAdditionalResource2")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToTeacherAdditionalResource2.label" default="Path To Teacher Additional Resource2" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToTeacherAdditionalResource2")}</td>
				
			</tr>
			
			
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.labelForTeacherAdditionalResource3.label" default="Label For Teacher Additional Resource3" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForTeacherAdditionalResource3")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToTeacherAdditionalResource3.label" default="Path To Teacher Additional Resource3" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToTeacherAdditionalResource3")}</td>
				
			</tr>
			
			
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.labelForTeacherAdditionalResource4.label" default="Label For Teacher Additional Resource4" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForTeacherAdditionalResource4")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToTeacherAdditionalResource4.label" default="Path To Teacher Additional Resource4" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToTeacherAdditionalResource4")}</td>
				
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
				<td valign="top" class="name"><g:message code="secureProgram.labelForStudentAdditionalResource.label" default="Label For Student Additional Resource" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForStudentAdditionalResource")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToStudentAdditionalResource.label" default="Path To Student Additional Resource" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToStudentAdditionalResource")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.labelForStudentAdditionalResource2.label" default="Label For Student Additional Resource2" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForStudentAdditionalResource2")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToStudentAdditionalResource2.label" default="Path To Student Additional Resource2" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToStudentAdditionalResource2")}</td>
				
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.labelForStudentAdditionalResource3.label" default="Label For Student Additional Resource3" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForStudentAdditionalResource3")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToStudentAdditionalResource3.label" default="Path To Student Additional Resource3" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToStudentAdditionalResource3")}</td>
				
			</tr>
			
				<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.labelForStudentAdditionalResource4.label" default="Label For Student Additional Resource4" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "labelForStudentAdditionalResource4")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.pathToStudentAdditionalResource4.label" default="Path To Student Additional Resource4" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "pathToStudentAdditionalResource4")}</td>
				
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
				<td valign="top" class="name"><g:message code="secureProgram.securityWord2.label" default="Security Word2" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "securityWord2")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.securityWordLocation2.label" default="Security Word Location2" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "securityWordLocation2")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.securityWordPage2.label" default="Security Word Page2" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "securityWordPage2")}</td>
				
			</tr>
		<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.securityWord3.label" default="Security Word3" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "securityWord3")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.securityWordLocation3.label" default="Security Word Location3" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "securityWordLocation3")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.securityWordPage3.label" default="Security Word Page3" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "securityWordPage3")}</td>
				
			</tr>
		
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${secureProgramInstance?.knewtonProduct}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphIdDev.label" default="Knowledge Graph ID Dev" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphIdDev")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphIdQA.label" default="Knowledge Graph ID QA" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphIdQA")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphIdProd.label" default="Knowledge Graph ID Prod" /></td>
				
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
				<td valign="top" class="name"><g:message code="secureProgram.knowledgeGraphEnrichmentCbiTimeLimit.label" default="Knowledge Graph Enrichment CBI Time Limit" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphEnrichmentCbiTimeLimit")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.comments.label" default="Comments" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: secureProgramInstance, field: "comments")}</td>
				
			</tr>
			
			<!-- Date Stamps here -->
			
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${secureProgramInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${secureProgramInstance?.lastUpdated}" /></td>
				
			</tr>
			
			<!-- Added Parent Object Links -->
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.bundles.label" default="Bundles (Parent Objects)" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${parentBundles}" var="b">
						<li><g:link controller="bundle" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="secureProgram.commerceObjects.label" default="Commerce Objects (Child Objects)" /></td>
				
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
</div>
</div>
</div>
</body>

			
				