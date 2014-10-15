<%@ page import="hmof.SecureProgram" %>
<% def year = new Date().getAt(Calendar.YEAR) %>

	


 <div class="span7">
        <section>
			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'productName', 'error-field')} required">
				<label for="productName" class="control-label col-sw-1"><g:message code="secureProgram.productName.label" default="Product Name" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField class="form-control" name="productName" required="" value="${secureProgramInstance?.productName}"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'registrationIsbn', 'error-field')} required ">
				<label for="registrationIsbn" class="control-label col-sw-1"><g:message code="secureProgram.registrationIsbn.label" default="Registration Isbn" /><span class="required-indicator">*</span></label>
				<div class="col-md-4 margin1" >
					<g:textField class="form-control" name="registrationIsbn" required="" value="${secureProgramInstance?.registrationIsbn}"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'onlineIsbn', 'error-field')} required">
				<label for="onlineIsbn" class="control-label col-sw-1"><g:message code="secureProgram.onlineIsbn.label" default="Online Isbn" /><span class="required-indicator">*</span></label>
				<div class="col-md-4 margin1" >
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
			<div class="control-group  fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'curriculumArea', 'error-field')} ">
	<label for="curriculumArea" class="control-label col-sw-1">
		<g:message code="secureProgram.curriculumArea.label" default="Curriculum Area" />
		
	</label>
	<div class="col-md-4 margin1">
	<g:select name="curriculumArea" class="form-control" from="${secureProgramInstance.constraints.curriculumArea.inList}" value="${secureProgramInstance?.curriculumArea}" valueMessagePrefix="secureProgram.curriculumArea" />
</div>
</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error-field')} ">
				<label for="copyright" class="control-label col-sw-1"><g:message code="secureProgram.copyright.label" default="Copyright" /></label>
				<div class="col-md-4 margin1">
					<%--<g:textField class="form-control" name="copyright" --%>
					<g:select class="form-control" name="copyright" from="${year-1..year+6}"
					value="${secureProgramInstance?.copyright}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'copyright', 'error-field')}</span>
				</div>
			</div>
			
			
			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForOnlineResource', 'error-field')} ">
				<label for="labelForOnlineResource" class="control-label col-sw-1"><g:message code="secureProgram.labelForOnlineResource.label" default="Label For Online Resource" /></label>
				<div class="col-md-5 margin1">
					<g:textField class="form-control" name="labelForOnlineResource" value="${secureProgramInstance?.labelForOnlineResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'labelForOnlineResource', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToResource', 'error-field')} ">
				<label for="pathToResource" class="control-label col-sw-1"><g:message code="secureProgram.pathToResource.label" default="Path To Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="pathToResource" value="${secureProgramInstance?.pathToResource}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToResource', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToCoverImage', 'error-field')} ">
				<label for="pathToCoverImage" class="control-label col-sw-1"><g:message code="secureProgram.pathToCoverImage.label" default="Path To Cover Image" /></label>
				<div class="controls">
					<g:textField class="form-control" name="pathToCoverImage" value="${secureProgramInstance?.pathToCoverImage}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'pathToCoverImage', 'error-field')}</span>
				</div>
			</div>
			

<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'essayGraderPrompts', 'error-field')} ">
	<label for="essayGraderPrompts" class="control-label col-sw-1">
		<g:message code="secureProgram.essayGraderPrompts.label" default="Essay Grader Prompts" />
		
	</label>
	<div class="col-md-4 margin1">
	<g:select name="essayGraderPrompts" class="form-control" from="${secureProgramInstance.constraints.essayGraderPrompts.inList}" value="${secureProgramInstance?.essayGraderPrompts}" valueMessagePrefix="secureProgram.essayGraderPrompts" />
</div>
</div>
</section></div>
<div class="span12" >
<div class="platformf">Platform Features</div><br>
				
		<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'includeDashboardObject', 'error-field')} ">
				<label for="includeDashboardObject" class="control-label col-sw-1"><g:message code="secureProgram.includeDashboardObject.label" default="Include Dashboard Object" /></label>
				<div class="controls">
					<bs:checkBox name="includeDashboardObject" value="${secureProgramInstance?.includeDashboardObject}" />
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'includeDashboardObject', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'includeEplannerObject', 'error-field')} ">
				<label for="includeEplannerObject" class="control-label col-sw-1"><g:message code="secureProgram.includeEplannerObject.label" default="Include Eplanner Object" /></label>
				<div class="controls">
					<bs:checkBox name="includeEplannerObject" value="${secureProgramInstance?.includeEplannerObject}" />
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'includeEplannerObject', 'error-field')}</span>
				</div>
			</div>
			
	</div>
			


 <div class="span12" >
  <div class="platformf">Security Words</div><br>
			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'securityWord', 'error-field')} required col-xs-4">
				<label for="securityWord" class="control-label col-sw-1"><g:message code="secureProgram.securityWord.label" default="Security Word" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField class="form-control" name="securityWord" required="" value="${secureProgramInstance?.securityWord}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'securityWord', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'securityWordLocation', 'error-field')} required col-xs-4">
				<label for="securityWordLocation" class="control-label col-sw-1"><g:message code="secureProgram.securityWordLocation.label" default="Security Word Location" /><span class="required-indicator">*</span></label>
				<div class="controls"> 
					<g:textField class="form-control" name="securityWordLocation" required="" value="${secureProgramInstance?.securityWordLocation}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'securityWordLocation', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'securityWordPage', 'error-field')} required col-xs-4">
				<label for="securityWordPage" class="control-label col-sw-1"><g:message code="secureProgram.securityWordPage.label" default="Security Word Page" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField class="form-control" name="securityWordPage" required="" value="${secureProgramInstance?.securityWordPage}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'securityWordPage', 'error-field')}</span>
				</div>
			</div>
</div>
<div class="span12">
       <section>
        
			<div id="accordion">
			
	<h3 class="col-sw-1">Additional Security Words</h3>
	<div id="accordion1">
	
	
	 <section>
<div class="${hasErrors(bean: secureProgramInstance, field: 'securityWord2', 'error-field')} col-xs-4">
	<label for="securityWord2" class="control-label col-sw-1">
		<g:message code="secureProgram.securityWord2.label" default="Security Word2" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="securityWord2" value="${secureProgramInstance?.securityWord2}"/>
</div>
</div>

<div class="${hasErrors(bean: secureProgramInstance, field: 'securityWordLocation2', 'error-field')} col-xs-4">
	<label for="securityWordLocation2" class="control-label col-sw-1">
		<g:message code="secureProgram.securityWordLocation2.label" default="Security Word Location2" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="securityWordLocation2" value="${secureProgramInstance?.securityWordLocation2}"/>
</div>
</div>

<div class="${hasErrors(bean: secureProgramInstance, field: 'securityWordPage2', 'error-field')} col-xs-4">
	<label for="securityWordPage2" class="control-label col-sw-1">
		<g:message code="secureProgram.securityWordPage2.label" default="Security Word Page2" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="securityWordPage2" value="${secureProgramInstance?.securityWordPage2}"/>
</div>
</div>

</section>


<section>
<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'securityWord3', 'error-field')} col-xs-4">
	<label for="securityWord3" class="control-label col-sw-1">
		<g:message code="secureProgram.securityWord3.label" default="Security Word3" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="securityWord3" value="${secureProgramInstance?.securityWord3}"/>
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'securityWordLocation3', 'error-field')} col-xs-4">
	<label for="securityWordLocation3" class="control-label col-sw-1">
		<g:message code="secureProgram.securityWordLocation3.label" default="Security Word Location3" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="securityWordLocation3" value="${secureProgramInstance?.securityWordLocation3}"/>
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'securityWordPage3', 'error-field')} col-xs-4">
	<label for="securityWordPage3" class="control-label col-sw-1">
		<g:message code="secureProgram.securityWordPage3.label" default="Security Word Page3" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="securityWordPage3" value="${secureProgramInstance?.securityWordPage3}"/>
</div>
</div>

</section>



</div>

<h3 class="col-sw-1">Links to Additional Resources</h3>
	<div id="accordion2">
	<span  class="col-sw-3">Teacher Additional Resources</span>
<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForTeacherAdditionalResource', 'error-field')} ">
				<label for="labelForTeacherAdditionalResource" class="control-label col-sw-1" ><g:message code="secureProgram.labelForTeacherAdditionalResource.label" default="Label For Teacher Additional Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="labelForTeacherAdditionalResource" value="${secureProgramInstance?.labelForTeacherAdditionalResource}" />
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToTeacherAdditionalResource', 'error-field')} ">
				<label for="pathToTeacherAdditionalResource" class="control-label col-sw-1"><g:message code="secureProgram.pathToTeacherAdditionalResource.label" default="Path To Teacher Additional Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="pathToTeacherAdditionalResource" value="${secureProgramInstance?.pathToTeacherAdditionalResource}" placeholder="must start with a forward slash / or http://"  />
			
				</div>
			</div>
<!-- 2T -->
<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Teach2" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Teacher Additional Resource2</span></a>
    </li>
  </ul> 
<div id="Teach2" class="collapse">
<br>
<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForTeacherAdditionalResource2', 'error-field')} ">
	<label for="labelForTeacherAdditionalResource2" class="control-label col-sw-1">
		<g:message code="secureProgram.labelForTeacherAdditionalResource2.label" default="Label For Teacher Additional Resource2" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="labelForTeacherAdditionalResource2" value="${secureProgramInstance?.labelForTeacherAdditionalResource2}" />
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToTeacherAdditionalResource2', 'error-field')} ">
	<label for="pathToTeacherAdditionalResource2" class="control-label col-sw-1">
		<g:message code="secureProgram.pathToTeacherAdditionalResource2.label" default="Path To Teacher Additional Resource2" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="pathToTeacherAdditionalResource2" value="${secureProgramInstance?.pathToTeacherAdditionalResource2}" placeholder="must start with a forward slash / or http://"/>
</div>
</div>
</div>


<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Teach3" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Teacher Additional Resource3</span></a>
    </li>
         </ul>   
<div id="Teach3" class="collapse">
<br>
<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForTeacherAdditionalResource3', 'error-field')} ">
	<label for="labelForTeacherAdditionalResource3" class="control-label col-sw-1">
		<g:message code="secureProgram.labelForTeacherAdditionalResource3.label" default="Label For Teacher Additional Resource3" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="labelForTeacherAdditionalResource3" value="${secureProgramInstance?.labelForTeacherAdditionalResource3}" />
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToTeacherAdditionalResource3', 'error-field')} " >
	<label for="pathToTeacherAdditionalResource3" class="control-label col-sw-1">
		<g:message code="secureProgram.pathToTeacherAdditionalResource3.label" default="Path To Teacher Additional Resource3" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="pathToTeacherAdditionalResource3" value="${secureProgramInstance?.pathToTeacherAdditionalResource3}" placeholder="must start with a forward slash / or http://"/>
</div>
</div>
</div>

<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Teach4" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Teacher Additional Resource4</span></a>
    </li>
         </ul> 
<div id="Teach4" class="collapse">
<div>
<br>
<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForTeacherAdditionalResource4', 'error-field')} ">
	<label for="labelForTeacherAdditionalResource4" class="control-label col-sw-1">
		<g:message code="secureProgram.labelForTeacherAdditionalResource4.label" default="Label For Teacher Additional Resource4" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="labelForTeacherAdditionalResource4" value="${secureProgramInstance?.labelForTeacherAdditionalResource4}" />
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToTeacherAdditionalResource4', 'error-field')} ">
	<label for="pathToTeacherAdditionalResource4" class="control-label col-sw-1">
		<g:message code="secureProgram.pathToTeacherAdditionalResource4.label" default="Path To Teacher Additional Resource4" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="pathToTeacherAdditionalResource4" value="${secureProgramInstance?.pathToTeacherAdditionalResource4}" placeholder="must start with a forward slash / or http://"/>
</div>
</div>
</div>
</div>

<span  class="col-sw-3">Student Additional Resources</span>
			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForStudentAdditionalResource', 'error-field')} ">
				<label for="labelForStudentAdditionalResource" class="control-label col-sw-1"><g:message code="secureProgram.labelForStudentAdditionalResource.label" default="Label For Student Additional Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="labelForStudentAdditionalResource" value="${secureProgramInstance?.labelForStudentAdditionalResource}" />
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'labelForStudentAdditionalResource', 'error-field')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToStudentAdditionalResource', 'error-field')} ">
				<label for="pathToStudentAdditionalResource" class="control-label col-sw-1"><g:message code="secureProgram.pathToStudentAdditionalResource.label" default="Path To Student Additional Resource" /></label>
				<div class="controls">
					<g:textField class="form-control" name="pathToStudentAdditionalResource" value="${secureProgramInstance?.pathToStudentAdditionalResource}" placeholder="must start with a forward slash / or http://"/>
					
				</div>
			</div>
	<!-- 2S -->		
<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Student2" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Student Additional Resource2</span></a>
    </li>
  </ul> 
<div id="Student2" class="collapse">
<br>
<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForStudentAdditionalResource2', 'error-field')} ">
	<label for="labelForStudentAdditionalResource2" class="control-label col-sw-1">
		<g:message code="secureProgram.labelForStudentAdditionalResource2.label" default="Label For Student Additional Resource2" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="labelForStudentAdditionalResource2" value="${secureProgramInstance?.labelForStudentAdditionalResource2}" />
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToStudentAdditionalResource2', 'error-field')} ">
	<label for="pathToStudentAdditionalResource2" class="control-label col-sw-1">
		<g:message code="secureProgram.pathToStudentAdditionalResource2.label" default="Path To Student Additional Resource2" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="pathToStudentAdditionalResource2" value="${secureProgramInstance?.pathToStudentAdditionalResource2}" placeholder="must start with a forward slash / or http://"/>
</div>
</div>		
			</div>
	<!-- 3S -->		
<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Student3" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Student Additional Resource3</span></a>
    </li>
  </ul> 
<div id="Student3" class="collapse">
<br>
<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForStudentAdditionalResource3', 'error-field')} ">
	<label for="labelForStudentAdditionalResource3" class="control-label col-sw-1">
		<g:message code="secureProgram.labelForStudentAdditionalResource3.label" default="Label For Student Additional Resource3" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="labelForStudentAdditionalResource3" value="${secureProgramInstance?.labelForStudentAdditionalResource3}" />
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToStudentAdditionalResource3', 'error-field')} ">
	<label for="pathToStudentAdditionalResource3" class="control-label col-sw-1">
		<g:message code="secureProgram.pathToStudentAdditionalResource3.label" default="Path To Student Additional Resource3" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="pathToStudentAdditionalResource3" value="${secureProgramInstance?.pathToStudentAdditionalResource3}" placeholder="must start with a forward slash / or http://"/>
</div>
</div>	
</div>
<!-- 4S -->	
<ul id="Menu" class="nav nav-pills margin-top-small">
<li class="active">
       <a data-toggle="collapse" data-target="#Student4" ><i class="glyphicon glyphicon-minus"></i><span class="col-sw-1">Add Student Additional Resource4</span></a>
    </li>
  </ul> 
<div id="Student4" class="collapse">
<br>
<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'labelForStudentAdditionalResource4', 'error-field')} ">
	<label for="labelForStudentAdditionalResource4" class="control-label col-sw-1">
		<g:message code="secureProgram.labelForStudentAdditionalResource4.label" default="Label For Student Additional Resource4" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="labelForStudentAdditionalResource4" value="${secureProgramInstance?.labelForStudentAdditionalResource4}"/>
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'pathToStudentAdditionalResource4', 'error-field')} ">
	<label for="pathToStudentAdditionalResource4" class="control-label col-sw-1">
		<g:message code="secureProgram.pathToStudentAdditionalResource4.label" default="Path To Student Additional Resource4" />
		
	</label>
	<div class="controls">
	<g:textField class="form-control" name="pathToStudentAdditionalResource4" value="${secureProgramInstance?.pathToStudentAdditionalResource4}" placeholder="must start with a forward slash / or http://"/>
</div>
</div>	
	</div>		
			
</div>


<h3 class="col-sw-1">Comments</h3>
	<div id="accordion3">
	   <div class="${hasErrors(bean: secureProgramInstance, field: 'comments', 'error-field')} ">
				
				<div>
					<g:textArea class="form-control" name="comments" cols="80" rows="5" maxlength="255" value="${secureProgramInstance?.comments}"/>
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'comments', 'error-field')}</span>
				</div>
			</div>
	</div>

		<h3 class="col-sw-1">Knewton</h3>
	<div id="accordion4">
	

			<div class="${hasErrors(bean: secureProgramInstance, field: 'knewtonProduct', 'error-field')} ">
				<label for="knewtonProduct" class="control-label col-sw-1"><g:message code="secureProgram.knewtonProduct.label" default="Knewton Product" /></label>
				<div class="controls">				
					
					<g:checkBox name="knewtonProduct"  value="${secureProgramInstance?.knewtonProduct}" data-toggle="collapse" data-target="#demo" onchange="pageonload()" />
					
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'knewtonProduct', 'error-field')}</span>
				</div>
			</div>
<div id="demo" class="collapse">
<div class="span6">
   
			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdDev', 'error-field')}">
				<label for="knowledgeGraphIdDev" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphIdDev.label" default="Knowledge Graph Id Dev" /></label>
				<div  class="controls">
					<g:textField class="form-control" name="knowledgeGraphIdDev" pattern="${secureProgramInstance.constraints.knowledgeGraphIdDev.matches}"  value="${secureProgramInstance?.knowledgeGraphIdDev}"  />
					</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdQA', 'error-field')} ">
				<label for="knowledgeGraphIdQA" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphIdQA.label" default="Knowledge Graph Id QA" /></label>
				<div  class="controls">
					<g:textField class="form-control" name="knowledgeGraphIdQA" pattern="${secureProgramInstance.constraints.knowledgeGraphIdQA.matches}"  value="${secureProgramInstance?.knowledgeGraphIdQA}"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphIdProd', 'error-field')} ">
				<label for="knowledgeGraphIdProd" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphIdProd.label" default="Knowledge Graph Id Prod" /></label>
				<div  class="controls">
					<g:textField class="form-control" name="knowledgeGraphIdProd" pattern="${secureProgramInstance.constraints.knowledgeGraphIdProd.matches}" value="${secureProgramInstance?.knowledgeGraphIdProd}"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphWarmUpTimeLimit', 'error-field')} ">
				<label for="knowledgeGraphWarmUpTimeLimit" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphWarmUpTimeLimit.label" default="Knowledge Graph Warm Up Time Limit" /></label>
				<div  class="controls"> 
					<g:select name="knowledgeGraphWarmUpTimeLimit" from="${5..60}" class="form-control" value="${fieldValue(bean: secureProgramInstance, field: 'knowledgeGraphWarmUpTimeLimit')}" noSelection="['': '']"/>
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentTimeLimit', 'error-field')} ">
				<label for="knowledgeGraphEnrichmentTimeLimit" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphEnrichmentTimeLimit.label" default="Knowledge Graph Enrichment Time Limit" /></label>
				<div  class="controls">
				<g:select name="knowledgeGraphEnrichmentTimeLimit" from="${5..60}" class="form-control" value="${fieldValue(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentTimeLimit')}" noSelection="['': '']"/>
					
					
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentCbiTimeLimit', 'error-field')} ">
				<label for="knowledgeGraphEnrichmentCbiTimeLimit" class="control-label col-sw-1"><g:message code="secureProgram.knowledgeGraphEnrichmentCbiTimeLimit.label" default="Knowledge Graph Enrichment Cbi Time Limit" /></label>
				<div  class="controls">
					<g:select name="knowledgeGraphEnrichmentCbiTimeLimit" from="${5..60}" class="form-control" value="${fieldValue(bean: secureProgramInstance, field: 'knowledgeGraphEnrichmentCbiTimeLimit')}" noSelection="['': '']"/>
					
				</div>
			</div>
			</div>
</div>
</div>

		</div></section></div>
		
		
<div class="span12">
       <section>
       <div class="platformf">Commerce Objects</div><br>
<div class="control-group fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'commerceObjects', 'error-field')} ">
				<label for="commerceObjects" class="control-label col-sw-1"><g:message code="secureProgram.commerceObjects.label" default="Commerce Objects" /></label>
				<div class="controls">
					<g:select class="form-control" name="commerceObjects" 
					from="${hmof.CommerceObject.list()}" noSelection="['':'-None-']" 
					multiple="multiple" optionKey="id" size="10"  
					value="${secureProgramInstance?.commerceObjects*.id}" class="many-to-many" />
					<span class="help-inline">${hasErrors(bean: secureProgramInstance, field: 'commerceObjects', 'error-field')}</span>
				</div>
</div>
		</section></div>
<g:eachError bean="${secureProgramInstance}" field="pathToTeacherAdditionalResource">
   <input type="hidden" id="lar" name="lar" value="true"/>
</g:eachError>
<g:eachError bean="${secureProgramInstance}" field="pathToTeacherAdditionalResource2">
   <input type="hidden" id="lar" name="lar" value="true"/>
</g:eachError>
<g:eachError bean="${secureProgramInstance}" field="pathToTeacherAdditionalResource3">
   <input type="hidden" id="lar" name="lar" value="true"/>
</g:eachError>
<g:eachError bean="${secureProgramInstance}" field="pathToTeacherAdditionalResource4">
   <input type="hidden" id="lar" name="lar" value="true"/>
</g:eachError>
<g:eachError bean="${secureProgramInstance}" field="pathToStudentAdditionalResource">
   <input type="hidden" id="lar" name="lar" value="true"/>
</g:eachError>
<g:eachError bean="${secureProgramInstance}" field="pathToStudentAdditionalResource2">
   <input type="hidden" id="lar" name="lar" value="true"/>
</g:eachError>
<g:eachError bean="${secureProgramInstance}" field="pathToStudentAdditionalResource3">
   <input type="hidden" id="lar" name="lar" value="true"/>
</g:eachError>
<g:eachError bean="${secureProgramInstance}" field="pathToStudentAdditionalResource4">
   <input type="hidden" id="lar" name="lar" value="true"/>
</g:eachError>

<script>

$( "#accordion" ).accordion({ heightStyle: "content",autoHeight: false, clearStyle: true});

function pageonload()
{	
	var selectIds = $('#Teach2,#Teach3,#Teach4,#Student2,#Student3,#Student4');
	$(function ($) {
	    selectIds.on('show.bs.collapse hidden.bs.collapse', function () {
	        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
	    })
	});
	var knewtonProd = document.getElementById("knewtonProduct").checked;
	var lar =document.getElementById("lar").value;
	alert(lar);
	if(knewtonProd && lar){
	    $("#accordion1").hide();			
	    $("#accordion2").show();
	    $("#accordion3").hide();			
	    $("#accordion4").show();	
	    $("#demo").show();
		$('#demo').toggleClass('collapse');
		$('#Teach2').toggleClass('collapse');
		$('#Teach3').toggleClass('collapse');
		$('#Teach4').toggleClass('collapse');
		$('#Student2').toggleClass('collapse');
		$('#Student3').toggleClass('collapse');
		$('#Student4').toggleClass('collapse');
		
		}else if(knewtonProd && !lar){
    $("#accordion1").hide();			
    $("#accordion2").hide();
    $("#accordion3").hide();			
    $("#accordion4").show();	
    $("#demo").show();
	$('#demo').toggleClass('collapse');
	
	}else if(lar && !knewtonProd)
		{				 
			  $("#accordion1").hide();			
			    $("#accordion2").show();
			    $("#accordion3").hide();			
			    $("#accordion4").hide();	
			    $('#Teach2').toggleClass('collapse');
				$('#Teach3').toggleClass('collapse');
				$('#Teach4').toggleClass('collapse');
				$('#Student2').toggleClass('collapse');
				$('#Student3').toggleClass('collapse');
				$('#Student4').toggleClass('collapse');
			
		 $("#demo").hide();		
		}


	
	}

window.onload = pageonload;
</script>
