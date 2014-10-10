<%@ page import="hmof.SecureProgram" %>
<% def year = new Date().getAt(Calendar.YEAR) %>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">

 <div class="span5">
        <section>
			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'productName', 'error-field')} required">
				<label for="productName" class="control-label"><g:message code="secureProgram.productName.label" default="Product Name" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField class="form-control" name="productName" required="" value="${secureProgramInstance?.productName}"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'registrationIsbn', 'error-field')} required">
				<label for="registrationIsbn" class="control-label"><g:message code="secureProgram.registrationIsbn.label" default="Registration Isbn" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField class="form-control" name="registrationIsbn" required="" value="${secureProgramInstance?.registrationIsbn}"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'onlineIsbn', 'error-field')} required">
				<label for="onlineIsbn" class="control-label"><g:message code="secureProgram.onlineIsbn.label" default="Online Isbn" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField class="form-control" name="onlineIsbn" required="" value="${secureProgramInstance?.onlineIsbn}"/>
					
				</div>
			</div>

			<%--<div class="${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error-field')} ">
				<label for="copyright" class="control-label"><g:message code="secureProgram.copyright.label" default="Copyright" /></label>
				<div>
					<g:textField class="form-control" name="copyright" value="${secureProgramInstance?.copyright}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error-field')}</span>
				</div>
			</div>--%>
			
			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error-field')} ">
				<label for="copyright" class="control-label"><g:message code="secureProgram.copyright.label" default="Copyright" /></label>
				<div class="controls">
					<%--<g:textField class="form-control" name="copyright" --%>
					<g:select class="form-control" name="copyright" from="${year-1..year+6}"
					value="${secureProgramInstance?.copyright}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error-field')}</span>
				</div>
			</div>
			
			
			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForOnlineResource', 'error-field')} ">
				<label for="labelForOnlineResource" class="control-label"><g:message code="secureProgram.labelForOnlineResource.label" default="Label For Online Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="labelForOnlineResource" value="${secureProgramInstance?.labelForOnlineResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'labelForOnlineResource', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToResource', 'error-field')} ">
				<label for="pathToResource" class="control-label"><g:message code="secureProgram.pathToResource.label" default="Path To Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="pathToResource" value="${secureProgramInstance?.pathToResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToResource', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToCoverImage', 'error-field')} ">
				<label for="pathToCoverImage" class="control-label"><g:message code="secureProgram.pathToCoverImage.label" default="Path To Cover Image" /></label>
				<div class="controls">
					<g:textField class="form-control" name="pathToCoverImage" value="${secureProgramInstance?.pathToCoverImage}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToCoverImage', 'error-field')}</span>
				</div>
			</div>
</section></div>
 <div class="span5">
        <section>


			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForTeacherAdditionalResource', 'error-field')} ">
				<label for="labelForTeacherAdditionalResource" class="control-label"><g:message code="secureProgram.labelForTeacherAdditionalResource.label" default="Label For Teacher Additional Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="labelForTeacherAdditionalResource" value="${secureProgramInstance?.labelForTeacherAdditionalResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'labelForTeacherAdditionalResource', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToTeacherAdditionalResource', 'error-field')} ">
				<label for="pathToTeacherAdditionalResource" class="control-label"><g:message code="secureProgram.pathToTeacherAdditionalResource.label" default="Path To Teacher Additional Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="pathToTeacherAdditionalResource" value="${secureProgramInstance?.pathToTeacherAdditionalResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToTeacherAdditionalResource', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForStudentAdditionalResource', 'error-field')} ">
				<label for="labelForStudentAdditionalResource" class="control-label"><g:message code="secureProgram.labelForStudentAdditionalResource.label" default="Label For Student Additional Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="labelForStudentAdditionalResource" value="${secureProgramInstance?.labelForStudentAdditionalResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'labelForStudentAdditionalResource', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToStudentAdditionalResource', 'error-field')} ">
				<label for="pathToStudentAdditionalResource" class="control-label"><g:message code="secureProgram.pathToStudentAdditionalResource.label" default="Path To Student Additional Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="pathToStudentAdditionalResource" value="${secureProgramInstance?.pathToStudentAdditionalResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToStudentAdditionalResource', 'error-field')}</span>
				</div>
			</div>

</section></div>
 <div class="span12">
  
			<div class="${hasErrors(bean: secureProgramInstance, field: 'securityWord', 'error-field')} required col-xs-4">
				<label for="securityWord" class="control-label"><g:message code="secureProgram.securityWord.label" default="Security Word" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField class="form-control" name="securityWord" required="" value="${secureProgramInstance?.securityWord}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'securityWord', 'error-field')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'securityWordLocation', 'error-field')} required col-xs-4">
				<label for="securityWordLocation" class="control-label"><g:message code="secureProgram.securityWordLocation.label" default="Security Word Location" /><span class="required-indicator">*</span></label>
				<div class="controls"> 
					<g:textField class="form-control" name="securityWordLocation" required="" value="${secureProgramInstance?.securityWordLocation}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'securityWordLocation', 'error-field')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: secureProgramInstance, field: 'securityWordPage', 'error-field')} required col-xs-4">
				<label for="securityWordPage" class="control-label"><g:message code="secureProgram.securityWordPage.label" default="Security Word Page" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField class="form-control" name="securityWordPage" required="" value="${secureProgramInstance?.securityWordPage}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'securityWordPage', 'error-field')}</span>
				</div>
			</div>
</div>
<div class="span12">
       <section>
        
			<div id="accordion">
	<h3>Commerce Objects</h3>
	<div id="accordion1">
<div class="${hasErrors(bean: secureProgramInstance, field: 'commerceObjects', 'error-field')} ">
				
				<div>
					<g:select class="form-control" name="commerceObjects" 
					from="${hmof.CommerceObject.list()}" noSelection="['':'-None-']" 
					multiple="multiple" optionKey="id" size="10" 
					value="${secureProgramInstance?.commerceObjects*.id}" class="many-to-many"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'commerceObjects', 'error-field')}</span>
				</div>
			</div>
</div>
<h3>Comments</h3>
	<div id="accordion2">
	   <div class="${hasErrors(bean: secureProgramInstance, field: 'comments', 'error-field')} ">
				
				<div>
					<g:textArea class="form-control" name="comments" cols="80" rows="5" maxlength="255" value="${secureProgramInstance?.comments}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'comments', 'error-field')}</span>
				</div>
			</div>
	</div>

		<h3>Knewton</h3>
	<div id="accordion3">
	

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knewtonProduct', 'error-field')} ">
				<label for="knewtonProduct" class="control-label"><g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" /></label>
				<div class="controls">				
					
					<g:checkBox name="knewtonProduct"  value="${secureProgramInstance?.knewtonProduct}" data-toggle="collapse" data-target="#demo" onchange="pageonload()" />
					
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knewtonProduct', 'error-field')}</span>
				</div>
			</div>
<div id="demo" class="collapse">
<div class="span6">
   
			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdDev', 'error-field')}">
				<label for="knowledgeGraphIdDev" class="control-label"><g:message code="secureProgram.knowledgeGraphIdDev.label" default="Knowledge Graph Id Dev" /></label>
				<div  class="controls">
					<g:textField class="form-control" name="knowledgeGraphIdDev" pattern="${secureProgramInstance.constraints.knowledgeGraphIdDev.matches}"  value="${secureProgramInstance?.knowledgeGraphIdDev}"  />
					</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdQA', 'error-field')} ">
				<label for="knowledgeGraphIdQA" class="control-label"><g:message code="secureProgram.knowledgeGraphIdQA.label" default="Knowledge Graph Id QA" /></label>
				<div  class="controls">
					<g:textField class="form-control" name="knowledgeGraphIdQA" pattern="${secureProgramInstance.constraints.knowledgeGraphIdQA.matches}"  value="${secureProgramInstance?.knowledgeGraphIdQA}"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdProd', 'error-field')} ">
				<label for="knowledgeGraphIdProd" class="control-label"><g:message code="secureProgram.knowledgeGraphIdProd.label" default="Knowledge Graph Id Prod" /></label>
				<div  class="controls">
					<g:textField class="form-control" name="knowledgeGraphIdProd" pattern="${secureProgramInstance.constraints.knowledgeGraphIdProd.matches}" value="${secureProgramInstance?.knowledgeGraphIdProd}"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphWarmUpTimeLimit', 'error-field')} ">
				<label for="knowledgeGraphWarmUpTimeLimit" class="control-label"><g:message code="secureProgram.knowledgeGraphWarmUpTimeLimit.label" default="Knowledge Graph Warm Up Time Limit" /></label>
				<div  class="controls"> 
					<g:select name="knowledgeGraphWarmUpTimeLimit" from="${5..60}" class="form-control" value="${fieldValue(bean: secureProgramInstance, field: 'knowledgeGraphWarmUpTimeLimit')}" noSelection="['': '']"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentTimeLimit', 'error-field')} ">
				<label for="knowledgeGraphEnrichmentTimeLimit" class="control-label"><g:message code="secureProgram.knowledgeGraphEnrichmentTimeLimit.label" default="Knowledge Graph Enrichment Time Limit" /></label>
				<div  class="controls">
				<g:select name="knowledgeGraphEnrichmentTimeLimit" from="${5..60}" class="form-control" value="${fieldValue(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentTimeLimit')}" noSelection="['': '']"/>
					
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentCbiTimeLimit', 'error-field')} ">
				<label for="knowledgeGraphEnrichmentCbiTimeLimit" class="control-label"><g:message code="secureProgram.knowledgeGraphEnrichmentCbiTimeLimit.label" default="Knowledge Graph Enrichment Cbi Time Limit" /></label>
				<div  class="controls">
					<g:select name="knowledgeGraphEnrichmentCbiTimeLimit" from="${5..60}" class="form-control" value="${fieldValue(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentCbiTimeLimit')}" noSelection="['': '']"/>
					
				</div>
			</div>
			</div>
</div>
</div>

		</div></section></div>
<script>

$( "#accordion" ).accordion();


function pageonload()
{	
	
	var knewtonProd = document.getElementById("knewtonProduct").checked;
		
    if(knewtonProd){
    $("#accordion1").hide();			
    $("#accordion2").hide();			
    $("#accordion3").show();	
    $("#demo").show();
	$('#demo').toggleClass('collapse');
	
	}else
		{ $("#demo").hide();		

		}
	}
window.onload = pageonload;
</script>
