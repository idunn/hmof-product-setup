
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
<div class="row" >
	<div class="widget stacked ">
		<div class="widget-content">
<section id="show-secureProgram" class="first"  >
<div class="span12 form-horizontal">

		
			<div
	class="control-group">
	<label for="productName" class="control-label col-sw-1"><g:message code="secureProgram.productName.label" default="Product Name" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "productName")}</div></div>
		
			<div
	class="control-group">
	<label for="registrationIsbn" class="control-label col-sw-1"><g:message code="secureProgram.registrationIsbn.label" default="Registration Isbn" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "registrationIsbn")}</div></div>
		
			<div
	class="control-group">
	<label for="onlineIsbn" class="control-label col-sw-1"><g:message code="secureProgram.onlineIsbn.label" default="Online Isbn" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "onlineIsbn")}</div></div>
		  <div
	class="control-group">
	<label for="curriculumArea" class="control-label col-sw-1"><g:message code="secureProgram.curriculumArea.label" default="Curriculum Area" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "curriculumArea")}</div></div>
			<div
	class="control-group">
	<label for="copyright" class="control-label col-sw-1"><g:message code="secureProgram.copyright.label" default="Copyright" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "copyright")}</div></div>
		
			<div
	class="control-group">
	<label for="labelForOnlineResource" class="control-label col-sw-1"><g:message code="secureProgram.labelForOnlineResource.label" default="Label For Online Resource" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "labelForOnlineResource")}</div></div>
		
			<div
	class="control-group">
	<label for="pathToResource" class="control-label col-sw-1"><g:message code="secureProgram.pathToResource.label" default="Path To Resource" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToResource")}</div></div>
			<div
	class="control-group">
	<label for="pathToCoverImage" class="control-label col-sw-1"><g:message code="secureProgram.pathToCoverImage.label" default="Path To Cover Image" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToCoverImage")}</div></div>
		
		
		<div
	class="control-group">
	<label for="essayGraderPrompts" class="control-label col-sw-1"><g:message code="secureProgram.essayGraderPrompts.label" default="Essay Grader Prompts" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "essayGraderPrompts")}</div></div>
		
		
		<div class="platformf">Platform Features</div><br>
		
		<div
	class="control-group">
	<label for="includeDashboardObject" class="control-label col-sw-1"><g:message code="secureProgram.includeDashboardObject.label" default="Include Dashboard Object" /></label>
				
				<div class="controls show-style"><g:formatBoolean boolean="${secureProgramInstance?.includeDashboardObject}" /></div></div>
		
			<div
	class="control-group">
	<label for="includeEplannerObject" class="control-label col-sw-1"><g:message code="secureProgram.includeEplannerObject.label" default="Include Eplanner Object" /></label>
				
				<div class="controls show-style"><g:formatBoolean boolean="${secureProgramInstance?.includeEplannerObject}" /></div></div>
			<div
	class="control-group">
	<label for="includeNotebookObject" class="control-label col-sw-1"><g:message code="secureProgram.includeNotebookObject.label" default="Include Notebook Object" /></label>
				
				<div class="controls show-style"><g:formatBoolean boolean="${secureProgramInstance?.includeNotebookObject}" /></div></div>
			
			
	
		  <div class="platformf">Security Words</div><br>
			<div
	class="control-group">
	<label for="securityWord" class="control-label col-sw-1"><g:message code="secureProgram.securityWord.label" default="Security Word" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "securityWord")}</div></div>
		
			<div
	class="control-group">
	<label for="securityWordLocation" class="control-label col-sw-1"><g:message code="secureProgram.securityWordLocation.label" default="Security Word Location" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "securityWordLocation")}</div></div>
		
			<div
	class="control-group">
	<label for="securityWordPage" class="control-label col-sw-1"><g:message code="secureProgram.securityWordPage.label" default="Security Word Page" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "securityWordPage")}</div></div>
		
						
			
    
<div class="bs-example">
    <div class="panel-group accordion-caret" id="accordion">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a class="accordion-toggle collapsed acstyle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">&nbsp;Additional Security Words</a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse">
                <div class="panel-body">
     
	
	 <section>
			
		<div
	class="col-sw-5">
	<label for="securityWord2" class="control-label col-sw-1"><g:message code="secureProgram.securityWord2.label" default="Security Word" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "securityWord2")}</div></div>
		
			<div
	class="col-sw-7">
	<label for="securityWordLocation2" class="control-label col-sw-1"><g:message code="secureProgram.securityWordLocation2.label" default="Security Word Location" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "securityWordLocation2")}</div></div>
		
			
			
			<div
	class="col-sw-5">
	<label for="securityWordPage2" class="control-label col-sw-1"><g:message code="secureProgram.securityWordPage2.label" default="Security Word Page" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "securityWordPage2")}</div></div>
		</section>
			<section>
		<div
	class="col-sw-5">
	<label for="securityWord3" class="control-label col-sw-1"><g:message code="secureProgram.securityWord3.label" default="Security Word" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "securityWord3")}</div></div>
		
			<div
	class="col-sw-7">
	<label for="securityWordLocation3" class="control-label col-sw-1"><g:message code="secureProgram.securityWordLocation3.label" default="Security Word Location" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "securityWordLocation3")}</div></div>
					
			
			<div
	class="col-sw-5">
	<label for="securityWordPage3" class="control-label col-sw-1"><g:message code="secureProgram.securityWordPage3.label" default="Security Word Page" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "securityWordPage3")}</div></div>
		
		</section>
		
		</div></div></div>
 <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" class="accordion-toggle collapsed" data-parent="#accordion" href="#collapseTwo">&nbsp;Links to Additional Resources</a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse">
                <div class="panel-body">               

	<span  class="col-sw-3">Teacher Additional Resources</span>
				
			<div
	class="control-group">
	<label for="labelForTeacherAdditionalResource" class="control-label col-sw-1"><g:message code="secureProgram.labelForTeacherAdditionalResource.label" default="Label For Teacher Additional Resource" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "labelForTeacherAdditionalResource")}</div></div>
		
			<div
	class="control-group">
	<label for="pathToTeacherAdditionalResource" class="control-label col-sw-1"><g:message code="secureProgram.pathToTeacherAdditionalResource.label" default="Path To Teacher Additional Resource" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToTeacherAdditionalResource")}</div></div>
	
<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Teach2" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Teacher Additional Resource2</span></a>
    </li>
  </ul> 
<div id="Teach2" class="collapse">
<br>
				<div
	class="control-group">
	<label for="labelForTeacherAdditionalResource2" class="control-label col-sw-1"><g:message code="secureProgram.labelForTeacherAdditionalResource2.label" default="Label For Teacher Additional Resource2" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "labelForTeacherAdditionalResource2")}</div></div>
			<div
	class="control-group">
	<label for="pathToTeacherAdditionalResource2" class="control-label col-sw-1"><g:message code="secureProgram.pathToTeacherAdditionalResource2.label" default="Path To Teacher Additional Resource2" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToTeacherAdditionalResource2")}</div></div>
		</div>	
	
	<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Teach3" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Teacher Additional Resource3</span></a>
    </li>
         </ul>   
<div id="Teach3" class="collapse">
<br>		
			<div
	class="control-group">
	<label for="labelForTeacherAdditionalResource3" class="control-label col-sw-1"><g:message code="secureProgram.labelForTeacherAdditionalResource3.label" default="Label For Teacher Additional Resource3" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "labelForTeacherAdditionalResource3")}</div></div>
			<div
	class="control-group">
	<label for="pathToTeacherAdditionalResource3" class="control-label col-sw-1"><g:message code="secureProgram.pathToTeacherAdditionalResource3.label" default="Path To Teacher Additional Resource3" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToTeacherAdditionalResource3")}</div></div>
			
			</div>
<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Teach4" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Teacher Additional Resource4</span></a>
    </li>
         </ul> 
<div id="Teach4" class="collapse">
<div>
<br>
			<div
	class="control-group">
	<label for="labelForTeacherAdditionalResource4" class="control-label col-sw-1"><g:message code="secureProgram.labelForTeacherAdditionalResource4.label" default="Label For Teacher Additional Resource4" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "labelForTeacherAdditionalResource4")}</div></div>
			<div
	class="control-group">
	<label for="pathToTeacherAdditionalResource4" class="control-label col-sw-1"><g:message code="secureProgram.pathToTeacherAdditionalResource4.label" default="Path To Teacher Additional Resource4" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToTeacherAdditionalResource4")}</div></div>
		</div>
		</div>	
			
		<span  class="col-sw-3">Student Additional Resources</span>	
			<div
	class="control-group">
	<label for="labelForStudentAdditionalResource" class="control-label col-sw-1"><g:message code="secureProgram.labelForStudentAdditionalResource.label" default="Label For Student Additional Resource" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "labelForStudentAdditionalResource")}</div></div>
		
			<div
	class="control-group">
	<label for="pathToStudentAdditionalResource" class="control-label col-sw-1"><g:message code="secureProgram.pathToStudentAdditionalResource.label" default="Path To Student Additional Resource" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToStudentAdditionalResource")}</div></div>
			
<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Student2" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Student Additional Resource2</span></a>
    </li>
  </ul> 
<div id="Student2" class="collapse">
<br>
			
			<div
	class="control-group">
	<label for="labelForStudentAdditionalResource2" class="control-label col-sw-1"><g:message code="secureProgram.labelForStudentAdditionalResource2.label" default="Label For Student Additional Resource2" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "labelForStudentAdditionalResource2")}</div></div>
		
			<div
	class="control-group">
	<label for="pathToStudentAdditionalResource2" class="control-label col-sw-1"><g:message code="secureProgram.pathToStudentAdditionalResource2.label" default="Path To Student Additional Resource2" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToStudentAdditionalResource2")}</div></div>
		</div>
		
			<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Student3" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Student Additional Resource3</span></a>
    </li>
         </ul>   
<div id="Student3" class="collapse">
<br>	
	<div
	class="control-group">
	<label for="labelForStudentAdditionalResource3" class="control-label col-sw-1"><g:message code="secureProgram.labelForStudentAdditionalResource3.label" default="Label For Student Additional Resource3" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "labelForStudentAdditionalResource3")}</div></div>
		
			<div
	class="control-group">
	<label for="pathToStudentAdditionalResource3" class="control-label col-sw-1"><g:message code="secureProgram.pathToStudentAdditionalResource3.label" default="Path To Student Additional Resource3" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToStudentAdditionalResource3")}</div></div>
			
			</div>
			
	<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Student4" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Student Additional Resource4</span></a>
    </li>
         </ul> 
<div id="Student4" class="collapse">
<div>
<br>		
			
				<div
	class="control-group">
	<label for="labelForStudentAdditionalResource4" class="control-label col-sw-1"><g:message code="secureProgram.labelForStudentAdditionalResource4.label" default="Label For Student Additional Resource4" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "labelForStudentAdditionalResource4")}</div></div>
		
			<div
	class="control-group">
	<label for="pathToStudentAdditionalResource4" class="control-label col-sw-1"><g:message code="secureProgram.pathToStudentAdditionalResource4.label" default="Path To Student Additional Resource4" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "pathToStudentAdditionalResource4")}</div></div>
			
	</div>		
			
</div></div></div>
	
		<div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a class="accordion-toggle collapsed"  data-toggle="collapse" data-parent="#accordion" href="#collapseThree">&nbsp;Knewton</a>
                </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse">
                <div class="panel-body">
			<div
	class="control-group">
	<label for="knewtonProduct" class="control-label col-sw-1"><g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" /></label>
				
				<div class="controls show-style"><g:formatBoolean boolean="${secureProgramInstance?.knewtonProduct}" /></div></div>

			<div
	class="control-group">
	<label for="knowledgeGraphIdDev" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphIdDev.label" default="Knowledge Graph ID Dev" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphIdDev")}</div></div>
			<div
	class="control-group">
	<label for="knowledgeGraphIdQA" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphIdQA.label" default="Knowledge Graph ID QA" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphIdQA")}</div></div>
		
			<div
	class="control-group">
	<label for="knowledgeGraphIdProd" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphIdProd.label" default="Knowledge Graph ID Prod" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphIdProd")}</div></div>
		
			<div
	class="control-group">
	<label for="knowledgeGraphWarmUpTimeLimit" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphWarmUpTimeLimit.label" default="Knowledge Graph Warm Up Time Limit" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphWarmUpTimeLimit")}</div></div>
		
			<div class="control-group">
	<label for="knowledgeGraphEnrichmentTimeLimit" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphEnrichmentTimeLimit.label" default="Knowledge Graph Enrichment Time Limit" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphEnrichmentTimeLimit")}</div></div>
		
			<div
	class="control-group">
	<label for="knowledgeGraphEnrichmentCbiTimeLimit" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphEnrichmentCbiTimeLimit.label" default="Knowledge Graph Enrichment CBI Time Limit" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "knowledgeGraphEnrichmentCbiTimeLimit")}
			</div>
			</div>
		</div>
</div>
</div>


		</div></div>
		<br>
		<div class="platformf">Comments</div><br>
			<div
	class="control-group">
	<label for="comments" class="control-label col-sw-1"><g:message code="secureProgram.comments.label" default="Comments" /></label>
				
				<div class="controls show-style">${fieldValue(bean: secureProgramInstance, field: "comments")}</div></div>
			
			<!-- Date Stamps here -->
			
			<div
	class="control-group">
	<label for="dateCreated" class="control-label col-sw-1"><g:message code="secureProgram.dateCreated.label" default="Date Created" /></label>
				
				<div class="controls show-style"><g:formatDate date="${secureProgramInstance?.dateCreated}" /></div></div>
		
			<div
	class="control-group">
	<label for="lastUpdated" class="control-label col-sw-1"><g:message code="secureProgram.lastUpdated.label" default="Last Updated" /></label>
				
				<div class="controls show-style"><g:formatDate date="${secureProgramInstance?.lastUpdated}" />
				</div>
				</div>
			<div class="platformf">Bundles</div><br>
			<!-- Added Parent Object Links -->
			<div
	class="control-group">
	<label for="bundles" class="control-label col-sw-1"><g:message code="secureProgram.bundles.label" default="Bundles (Parent Objects)" /></label>
				
				<div class="controls show-style" style="text-align: left;">
					<ul>
					<g:each in="${parentBundles}" var="b">
						<li><g:link controller="bundle" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</div></div>
		<div class="platformf">Commerce Objects</div><br>
			<div
	class="control-group">
	<label for="commerceObjects" class="control-label col-sw-1"><g:message code="secureProgram.commerceObjects.label" default="Commerce Objects (Child Objects)" /></label>
				
			<div class="controls show-style" style="text-align: left;">	
					<ul>
					<g:each in="${secureProgramInstance.commerceObjects}" var="c">
						<li><g:link controller="commerceObject" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</div></div>					
			</div>
				
		
	</section>
</div>
</div>
</div>
<script>

	window.onload = pageonload;

</script>
</body>

</html>
				