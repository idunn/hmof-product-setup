<%@ page import="hmof.SecureProgram" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'secureProgram.label', default: 'SecureProgram')}" />
	<title><g:message code="default.edit.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
</head>

<body>
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">

	<section id="edit-secureProgram" class="first">
     <div >
		<g:hasErrors bean="${secureProgramInstance}">
		<div class="alert alert-danger">
			<g:renderErrors bean="${secureProgramInstance}" as="list" />
		</div>
		</g:hasErrors>

		<g:form method="post" class="form-horizontal" role="form" >
			<g:hiddenField name="id" value="${secureProgramInstance?.id}" />
			<g:hiddenField name="version" value="${secureProgramInstance?.version}" />
			<g:hiddenField name="_method" value="PUT" />
			
			<g:render template="form"/>
			<div class="span9">
           <section>
			<div class="form-actions margin-top-medium">
				<g:actionSubmit class="btn btn-primary" formnovalidate action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset" /></button>
			</div>
			</section></div>
		</g:form>
        </div>
	</section>
</div>
</div>
</div>
</body>

</html>
