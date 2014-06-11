<%@ page import="hmof.CommerceObject" %>



			<div class="${hasErrors(bean: commerceObjectInstance, field: 'objectName', 'error')} required">
				<label for="objectName" class="control-label"><g:message code="commerceObject.objectName.label" default="Object Name" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="objectName" required="" value="${commerceObjectInstance?.objectName}"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'objectName', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'isbn', 'error')} required">
				<label for="isbn" class="control-label"><g:message code="commerceObject.isbn.label" default="Isbn" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="isbn" required="" value="${commerceObjectInstance?.isbn}"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'isbn', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'pathToCoverImage', 'error')} ">
				<label for="pathToCoverImage" class="control-label"><g:message code="commerceObject.pathToCoverImage.label" default="Path To Cover Image" /></label>
				<div>
					<g:textField class="form-control" name="pathToCoverImage" value="${commerceObjectInstance?.pathToCoverImage}"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'pathToCoverImage', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'teacherLabel', 'error')} ">
				<label for="teacherLabel" class="control-label"><g:message code="commerceObject.teacherLabel.label" default="Teacher Label" /></label>
				<div>
					<g:textField class="form-control" name="teacherLabel" value="${commerceObjectInstance?.teacherLabel}"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'teacherLabel', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'teacherUrl', 'error')} ">
				<label for="teacherUrl" class="control-label"><g:message code="commerceObject.teacherUrl.label" default="Teacher Url" /></label>
				<div>
					<g:textField class="form-control" name="teacherUrl" value="${commerceObjectInstance?.teacherUrl}"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'teacherUrl', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'studentLabel', 'error')} ">
				<label for="studentLabel" class="control-label"><g:message code="commerceObject.studentLabel.label" default="Student Label" /></label>
				<div>
					<g:textField class="form-control" name="studentLabel" value="${commerceObjectInstance?.studentLabel}"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'studentLabel', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'studentUrl', 'error')} ">
				<label for="studentUrl" class="control-label"><g:message code="commerceObject.studentUrl.label" default="Student Url" /></label>
				<div>
					<g:textField class="form-control" name="studentUrl" value="${commerceObjectInstance?.studentUrl}"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'studentUrl', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'objectType', 'error')} ">
				<label for="objectType" class="control-label"><g:message code="commerceObject.objectType.label" default="Object Type" /></label>
				<div>
					<g:select class="form-control" name="objectType" from="${commerceObjectInstance.constraints.objectType.inList}" value="${commerceObjectInstance?.objectType}" valueMessagePrefix="commerceObject.objectType" noSelection="['': '']"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'objectType', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'objectReorderNumber', 'error')} ">
				<label for="objectReorderNumber" class="control-label"><g:message code="commerceObject.objectReorderNumber.label" default="Object Reorder Number" /></label>
				<div>
					<g:field class="form-control" name="objectReorderNumber" type="number" value="${commerceObjectInstance.objectReorderNumber}"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'objectReorderNumber', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'gradeLevel', 'error')} ">
				<label for="gradeLevel" class="control-label"><g:message code="commerceObject.gradeLevel.label" default="Grade Level" /></label>
				<div>
					<g:select class="form-control" name="gradeLevel" from="${commerceObjectInstance.constraints.gradeLevel.inList}" value="${commerceObjectInstance?.gradeLevel}" valueMessagePrefix="commerceObject.gradeLevel" noSelection="['': '']"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'gradeLevel', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: commerceObjectInstance, field: 'comments', 'error')} ">
				<label for="comments" class="control-label"><g:message code="commerceObject.comments.label" default="Comments" /></label>
				<div>
					<g:textArea class="form-control" name="comments" cols="40" rows="5" maxlength="200" value="${commerceObjectInstance?.comments}"/>
					<span class="help-inline">${hasErrors(bean: commerceObjectInstance, field: 'comments', 'error')}</span>
				</div>
			</div>

