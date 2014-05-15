<%@ page import="hmof.Bundle"%>



<div
	class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'isbn', 'error')} required">
	<label for="isbn"> <g:message code="bundle.isbn.label"
			default="Isbn" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="isbn" required="" value="${bundleInstance?.isbn}" />

</div>

<div
	class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'title', 'error')} required">
	<label for="title"> <g:message code="bundle.title.label"
			default="Title" /> <span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${bundleInstance?.title}" />

</div>

<div
	class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'duration', 'error')} ">
	<label for="duration"> <g:message code="bundle.duration.label"
			default="Duration" />

	</label>
	<g:select name="duration"
		from="${bundleInstance.constraints.duration.inList}"
		value="${bundleInstance?.duration}"
		valueMessagePrefix="bundle.duration" noSelection="['': '']" />

</div>

<div
	class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'commerceObjects', 'error')} ">
	<label for="commerceObjects"> <g:message
			code="bundle.commerceObjects.label" default="Commerce Objects" />

	</label>
	<g:select name="commerceObjects" from="${hmof.Cobj.list()}"
		multiple="multiple" optionKey="id" size="5"
		value="${bundleInstance?.commerceObjects*.id}" class="many-to-many" />

</div>

<%-- Add new Cobj --%>

<div
	class="fieldcontain ${hasErrors(bean: secureProgramInstance, field: 'commerceObjects', 'error')} ">
	<label for="commerceObjects"> <g:message
			code="secureProgram.commerceObjects.label"
			default="New Commerce Object" />

	</label>

	<ul class="one-to-many">
		<%--<g:each in="${secureProgramInstance?.commerceObjects?}" var="c">
			<li><g:link controller="cobj" action="show" id="${c.id}">
					${c?.encodeAsHTML()}
				</g:link></li>
		</g:each>
		--%>
		<li class="add"><g:link controller="cobj" action="create"
				params="['secureProgram.id': secureProgramInstance?.id]">
				${message(code: 'default.add.label', args: [message(code: 'cobj.label', default: 'New Commerce Object')])}
			</g:link></li>
	</ul>


</div>

<div
	class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'program', 'error')} required">
	<label for="program"> <g:message code="bundle.program.label"
			default="Program" /> <span class="required-indicator">*</span>
	</label>
	<g:select id="program" name="program.id" from="${hmof.Program.list()}"
		optionKey="id" required="" value="${bundleInstance?.program?.id}"
		class="many-to-one" />

</div>

<div
	class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'secureProgram', 'error')} ">
	<label for="secureProgram"> <g:message
			code="bundle.secureProgram.label" default="Secure Program" />

	</label>
	<g:select name="secureProgram" from="${hmof.SecureProgram.list()}"
		multiple="multiple" optionKey="id" size="5"
		value="${bundleInstance?.secureProgram*.id}" class="many-to-many" />

</div>

<%--Added to add SP not listed --%>

<div
	class="fieldcontain ${hasErrors(bean: bundleInstance, field: 'secureProgram', 'error')} ">
	<label for="secureProgram"> <g:message
			code="bundle.secureProgram.label" default="New Secure Program" />

	</label>

	<ul class="one-to-many">
		<%--<g:each in="${bundleInstance?.secureProgram?}" var="s">
			<li><g:link controller="secureProgram" action="show"
					id="${s.id}">
					${s?.encodeAsHTML()}
				</g:link></li>
		</g:each>
		--%>
		<li class="add"><g:link controller="secureProgram"
				action="create" params="['bundle.id': bundleInstance?.id]">
				${message(code: 'default.add.label', args: [message(code: 'secureProgram.label', default: 'New Secure Program')])}
			</g:link></li>
	</ul>


</div>

