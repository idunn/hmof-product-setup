<%@ page import="hmof.Cobj" %>



			<div class="${hasErrors(bean: cobjInstance, field: 'objectName', 'error')} required">
				<label for="objectName" class="control-label"><g:message code="cobj.objectName.label" default="Object Name" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="objectName" required="" value="${cobjInstance?.objectName}"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'objectName', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'isbn', 'error')} required">
				<label for="isbn" class="control-label"><g:message code="cobj.isbn.label" default="Isbn" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="isbn" required="" value="${cobjInstance?.isbn}"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'isbn', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'pathToCoverImage', 'error')} ">
				<label for="pathToCoverImage" class="control-label"><g:message code="cobj.pathToCoverImage.label" default="Path To Cover Image" /></label>
				<div>
					<g:textField class="form-control" name="pathToCoverImage" value="${cobjInstance?.pathToCoverImage}"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'pathToCoverImage', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'teacherLabel', 'error')} ">
				<label for="teacherLabel" class="control-label"><g:message code="cobj.teacherLabel.label" default="Teacher Label" /></label>
				<div>
					<g:textField class="form-control" name="teacherLabel" value="${cobjInstance?.teacherLabel}"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'teacherLabel', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'teacherUrl', 'error')} ">
				<label for="teacherUrl" class="control-label"><g:message code="cobj.teacherUrl.label" default="Teacher Url" /></label>
				<div>
					<g:textField class="form-control" name="teacherUrl" value="${cobjInstance?.teacherUrl}"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'teacherUrl', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'studentLabel', 'error')} ">
				<label for="studentLabel" class="control-label"><g:message code="cobj.studentLabel.label" default="Student Label" /></label>
				<div>
					<g:textField class="form-control" name="studentLabel" value="${cobjInstance?.studentLabel}"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'studentLabel', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'studentUrl', 'error')} ">
				<label for="studentUrl" class="control-label"><g:message code="cobj.studentUrl.label" default="Student Url" /></label>
				<div>
					<g:textField class="form-control" name="studentUrl" value="${cobjInstance?.studentUrl}"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'studentUrl', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'objectType', 'error')} ">
				<label for="objectType" class="control-label"><g:message code="cobj.objectType.label" default="Object Type" /></label>
				<div>
					<g:select class="form-control" name="objectType" from="${cobjInstance.constraints.objectType.inList}" value="${cobjInstance?.objectType}" valueMessagePrefix="cobj.objectType" noSelection="['': '']"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'objectType', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'objectReorderNumber', 'error')} ">
				<label for="objectReorderNumber" class="control-label"><g:message code="cobj.objectReorderNumber.label" default="Object Reorder Number" /></label>
				<div>
					<g:field class="form-control" name="objectReorderNumber" type="number" value="${cobjInstance.objectReorderNumber}"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'objectReorderNumber', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'gradeLevel', 'error')} ">
				<label for="gradeLevel" class="control-label"><g:message code="cobj.gradeLevel.label" default="Grade Level" /></label>
				<div>
					<g:select class="form-control" name="gradeLevel" from="${cobjInstance.constraints.gradeLevel.inList}" value="${fieldValue(bean: cobjInstance, field: 'gradeLevel')}" valueMessagePrefix="cobj.gradeLevel" noSelection="['': '']"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'gradeLevel', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: cobjInstance, field: 'comments', 'error')} ">
				<label for="comments" class="control-label"><g:message code="cobj.comments.label" default="Comments" /></label>
				<div>
					<g:textArea class="form-control" name="comments" cols="40" rows="5" maxlength="200" value="${cobjInstance?.comments}"/>
					<span class="help-inline">${hasErrors(bean: cobjInstance, field: 'comments', 'error')}</span>
				</div>
			</div>

