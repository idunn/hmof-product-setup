<%@ page import="hmof.CommerceObject"%>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">

<div class="span7">
	<section>
		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'objectName', 'error-field')} required">
			<label for="objectName" class="control-label col-sw-1"><g:message
					code="commerceObject.objectName.label" default="Object Name" /><span
				class="required-indicator">*</span></label>
			<div class="controls">
				<g:textField class="form-control" name="objectName" required=""
					value="${commerceObjectInstance?.objectName}" data-toggle="tooltip" data-placement="right" data-container="body" title="Title must be unique and should be prefixed with a Program Name" />

			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'isbnNumber', 'error-field')} required">
			<label for="isbnNumber" class="control-label col-sw-1"><g:message
					code="commerceObject.isbnNumber.label" default="Isbn" /><span
				class="required-indicator">*</span></label>
			<div class="controls">
				<g:textField class="form-control" maxlength="13" name="isbnNumber" required="" 
					value="${commerceObjectInstance?.isbnNumber}" data-toggle="tooltip" data-placement="right" data-container="body" title="A unique identifier or ISBN. This auto-generated number can be used if you don't have an ISBN" />

			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'pathToCoverImage', 'error-field')} ">
			<label for="pathToCoverImage" class="control-label col-sw-1"><g:message
					code="commerceObject.pathToCoverImage.label"
					default="Path To Cover Image" /></label>
			<div class="controls">
				<g:textField class="form-control" name="pathToCoverImage"
					value="${commerceObjectInstance?.pathToCoverImage}" data-toggle="tooltip" data-placement="right" data-container="body" title="Path to the icon Image located in: /nsmedia/images/bc/" />
				<span class="help-inline">
					${hasErrors(bean: commerceObjectInstance, field: 'pathToCoverImage', 'error-field')}
				</span>
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'teacherLabel', 'error-field')} ">
			<label for="teacherLabel" class="control-label col-sw-1"><g:message
					code="commerceObject.teacherLabel.label" default="Teacher Label" /></label>
			<div class="controls">
				<g:textField class="form-control" name="teacherLabel"
					value="${commerceObjectInstance?.teacherLabel}" data-toggle="tooltip" data-placement="right" data-container="body" title="Label for Teacher Url Link, which is visible to the Customer" />
				<span class="help-inline">
					${hasErrors(bean: commerceObjectInstance, field: 'teacherLabel', 'error-field')}
				</span>
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'teacherUrl', 'error-field')} ">
			<label for="teacherUrl" class="control-label col-sw-1"><g:message
					code="commerceObject.teacherUrl.label" default="Teacher Url" /></label>
			<div class="controls">
				<g:textField class="form-control" name="teacherUrl"
					value="${commerceObjectInstance?.teacherUrl}"   data-toggle="tooltip" data-placement="right" data-container="body" title="Path to Teacher Only Content" />
				<span class="help-inline">
					${hasErrors(bean: commerceObjectInstance, field: 'teacherUrl', 'error-field')}
				</span>
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'studentLabel', 'error-field')} ">
			<label for="studentLabel" class="control-label col-sw-1"><g:message
					code="commerceObject.studentLabel.label" default="Student Label" /></label>
			<div class="controls">
				<g:textField class="form-control" name="studentLabel"
					value="${commerceObjectInstance?.studentLabel}" data-toggle="tooltip" data-placement="right" data-container="body" title="Label for Student Url Link, which is visible to the Customer" />
				<span class="help-inline">
					${hasErrors(bean: commerceObjectInstance, field: 'studentLabel', 'error-field')}
				</span>
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'studentUrl', 'error-field')} ">
			<label for="studentUrl" class="control-label col-sw-1"><g:message
					code="commerceObject.studentUrl.label" default="Student Url" /></label>
			<div class="controls">
				<g:textField class="form-control" name="studentUrl"
					value="${commerceObjectInstance?.studentUrl}"  data-toggle="tooltip" data-placement="right" data-container="body" title="Path to Student Content" />
				<span class="help-inline">
					${hasErrors(bean: commerceObjectInstance, field: 'studentUrl', 'error-field')}
				</span>
			</div>
		</div>
	</section>
</div>
<div class="span5">
	<section>
		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'category', 'error-field')} ">
			<label for="category" class="control-label col-sw-1"> <g:message
					code="commerceObject.category.label" default="Category" />

			</label>
			<div class="controls">
				<g:select name="category" class="form-control"
					from="${commerceObjectInstance.constraints.category.inList}"
					value="${commerceObjectInstance?.category}"
					valueMessagePrefix="commerceObject.category" />
			</div>
		</div>


		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'objectType', 'error-field')} ">
			<label for="objectType" class="control-label col-sw-1"><g:message
					code="commerceObject.objectType.label" default="Object Type" /></label>
			<div class="controls">
				<g:select class="form-control" name="objectType"
					from="${commerceObjectInstance.constraints.objectType.inList}"
					value="${commerceObjectInstance?.objectType}"
					valueMessagePrefix="commerceObject.objectType" data-toggle="tooltip" data-placement="right" data-container="body" title="Groups resources on the resource gadget" />
				<span class="help-inline">
					${hasErrors(bean: commerceObjectInstance, field: 'objectType', 'error-field')}
				</span>
			</div>
		</div>

		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'objectReorderNumber', 'error-field')} ">
			<label for="objectReorderNumber" class="control-label col-sw-1"><g:message
					code="commerceObject.objectReorderNumber.label"
					default="Resource Order" /></label>
					
					<div  class="controls"> 
					<g:select name="objectReorderNumber" from="${commerceObjectInstance.constraints.objectReorderNumber.inList}" class="form-control" value="${fieldValue(bean: commerceObjectInstance, field: 'objectReorderNumber')}" data-toggle="tooltip" data-placement="right" data-container="body" title="Determines the content display sequence on the Dashboard Resource Gadget.'Hidden' ordered resources are not displayed in HMOF Dashboard." />
					
				</div>
			</div>
		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'subject', 'error-field')} ">
			<label for="subject" class="control-label"> <g:message
					code="commerceObject.subject.label" default="Subject" />

			</label>
			<div class="controls">
				<g:select name="subject" class="form-control"
					from="${commerceObjectInstance.constraints.subject.inList}"
					value="${commerceObjectInstance?.subject}"
					valueMessagePrefix="commerceObject.subject" />
			</div>
		</div>
		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'gradeLevel', 'error-field')} ">
			<label for="gradeLevel" class="control-label col-sw-1"><g:message
					code="commerceObject.gradeLevel.label" default="Grade Level" /></label>
			<div class="controls">
				<g:select class="form-control" name="gradeLevel"
					from="${commerceObjectInstance.constraints.gradeLevel.inList}"
					value="${commerceObjectInstance?.gradeLevel}"
					valueMessagePrefix="commerceObject.gradeLevel" data-toggle="tooltip" data-placement="right" data-container="body" title="Middle School = 6-8 and High School = 9-12" />
				<span class="help-inline">
					${hasErrors(bean: commerceObjectInstance, field: 'gradeLevel', 'error-field')}
				</span>
			</div>
		</div>
		<%--<div class="fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'tabNavTab', 'error-field')} ">
	<label for="tabNavTab" class="control-label">
		<g:message code="commerceObject.tabNavTab.label" default="Tab Nav Tab" />
		
	</label>	
<bs:checkBox name="tabNavTab" value="${commerceObjectInstance?.tabNavTab}" />
</div>
--%>
		<div
			class="control-group fieldcontain  ${hasErrors(bean: commerceObjectInstance, field: 'isPremium', 'error-field')} ">
			<label for="isPremium" class="control-label col-sw-1"> <g:message
					code="commerceObject.isPremium.label" default="Is Premium" />

			</label>
			<div class="controls">
			<bs:checkBox name="isPremium" value="${commerceObjectInstance?.isPremium}" data-toggle="tooltip" data-placement="right" data-container="body" title="Viewable to Customers who buy Premium Bundles" />
				<%--<g:checkBox name="isPremium"
					value="${commerceObjectInstance?.isPremium}" data-toggle="tooltip" data-placement="right" data-container="body" title="Viewable to Customers who buy Premium Bundles"/>
			--%></div>
		</div>

	</section>
</div>
<div class="span9">
	<section>
		<div
			class="control-group fieldcontain ${hasErrors(bean: commerceObjectInstance, field: 'comments', 'error-field')} ">
			<label for="comments" class="control-label col-sw-1"><g:message
					code="commerceObject.comments.label" default="Comments" /></label>
			<div class="controls">
				<g:textArea class="form-control" name="comments" cols="40" rows="5"
					maxlength="200" value="${commerceObjectInstance?.comments}" />
				<span class="help-inline">
					${hasErrors(bean: commerceObjectInstance, field: 'comments', 'error-field')}
				</span>
			</div>
		</div>



	</section>
</div>

<script>
   $(function () { $("[data-toggle='tooltip']").tooltip(); });
</script>