
<%@ page import="hmof.deploy.EnvironmentGrp" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'environmentGrp.label', default: 'EnvironmentGrp')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
	<style>
	@font-face {
font-family: 'Glyphicons Halflings';
src: url('../../fonts/glyphicons-halflings-regular.eot');
src: url('../../fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded- opentype'), 
     url('../../fonts/glyphicons-halflings-regular.woff') format('woff'), 
     url('../../fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('../../fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg');
} 
</head>

<body>

<section id="show-environmentGrp" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="environmentGrp.groupname.label" default="Groupname" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: environmentGrpInstance, field: "groupname")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
