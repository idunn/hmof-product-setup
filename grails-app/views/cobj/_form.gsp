<%@ page import="hmof.Cobj" %>



<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'objectName', 'error')} required">
	<label for="objectName">
		<g:message code="cobj.objectName.label" default="Object Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="objectName" required="" value="${cobjInstance?.objectName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'isbn', 'error')} required">
	<label for="isbn">
		<g:message code="cobj.isbn.label" default="Isbn" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="isbn" required="" value="${cobjInstance?.isbn}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'pathToCoverImage', 'error')} ">
	<label for="pathToCoverImage">
		<g:message code="cobj.pathToCoverImage.label" default="Path To Cover Image" />
		
	</label>
	<g:textField name="pathToCoverImage" value="${cobjInstance?.pathToCoverImage}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'teacherLabel', 'error')} ">
	<label for="teacherLabel">
		<g:message code="cobj.teacherLabel.label" default="Teacher Label" />
		
	</label>
	<g:textField name="teacherLabel" value="${cobjInstance?.teacherLabel}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'teacherUrl', 'error')} ">
	<label for="teacherUrl">
		<g:message code="cobj.teacherUrl.label" default="Teacher Url" />
		
	</label>
	<g:textField name="teacherUrl" value="${cobjInstance?.teacherUrl}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'studentLabel', 'error')} ">
	<label for="studentLabel">
		<g:message code="cobj.studentLabel.label" default="Student Label" />
		
	</label>
	<g:textField name="studentLabel" value="${cobjInstance?.studentLabel}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'studentUrl', 'error')} ">
	<label for="studentUrl">
		<g:message code="cobj.studentUrl.label" default="Student Url" />
		
	</label>
	<g:textField name="studentUrl" value="${cobjInstance?.studentUrl}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'objectType', 'error')} ">
	<label for="objectType">
		<g:message code="cobj.objectType.label" default="Object Type" />
		
	</label>
	<g:select name="objectType" from="${cobjInstance.constraints.objectType.inList}" value="${cobjInstance?.objectType}" valueMessagePrefix="cobj.objectType" noSelection="['': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'objectReorderNumber', 'error')} ">
	<label for="objectReorderNumber">
		<g:message code="cobj.objectReorderNumber.label" default="Object Reorder Number" />
		
	</label>
	<g:field name="objectReorderNumber" type="number" value="${cobjInstance.objectReorderNumber}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'gradeLevel', 'error')} ">
	<label for="gradeLevel">
		<g:message code="cobj.gradeLevel.label" default="Grade Level" />
		
	</label>
	<g:select name="gradeLevel" from="${cobjInstance.constraints.gradeLevel.inList}" value="${fieldValue(bean: cobjInstance, field: 'gradeLevel')}" valueMessagePrefix="cobj.gradeLevel" noSelection="['': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cobjInstance, field: 'comments', 'error')} ">
	<label for="comments">
		<g:message code="cobj.comments.label" default="Comments" />
		
	</label>
	<g:textArea name="comments" cols="40" rows="5" maxlength="255" value="${cobjInstance?.comments}"/>

</div>

