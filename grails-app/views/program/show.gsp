
<%@ page import="hmof.Program"%>
<%@ page import="hmof.DeploymentService"%>
<!DOCTYPE html>
<html>

<head>
<meta name="layout" content="kickstart" />
<g:set var="entityName"
	value="${message(code: 'program.label', default: 'Program')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'scroller.css')}"
	type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
        <g:render template="/program/studentsliderjs"/>
        <g:render template="/program/teachersliderjs"/>
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
<g:set var="jobdetails" bean="deploymentService"/>
<g:set var="isbnWebService" bean="utilityService"/>
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
	<section id="show-program" class="first">

	<div class="span12 form-horizontal">

<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message
							code="program.name.label" default="Name" /></label>
	<div class="controls show-style">
		${fieldValue(bean: programInstance, field: "name")}
	</div>
</div>

<div
	class="control-group">
	<label for="state" class="control-label col-sw-1"><g:message
							code="program.state.label" default="State" /></label>

					<div class="controls show-style">
		${fieldValue(bean: programInstance, field: "state")}
	</div>
</div>

		<div
	class="control-group">
	<label for="discipline" class="control-label col-sw-1"><g:message
							code="program.discipline.label" default="Discipline" /></label>

			<div class="controls show-style">
		${fieldValue(bean: programInstance, field: "discipline")}
	</div>
</div>				


		<div
	class="control-group">
	<label for="bundlecount" class="control-label col-sw-1"><g:message
							code="program.bundlecount.label" default="Bundle Count" /></label>

			<div class="controls show-style">
		${bundleCount}
	</div>
</div>	

		<div
	class="control-group">
	<label for="nondeployablebundles" class="control-label col-sw-1"><g:message
							code="program.bundlecount.label" default="Non Deployable Bundles" /></label>

			<div class="controls show-style">
			
		<g:if test="${unDeployableBundleMap}">
				 
					<ul>
					<g:each var="unDeployableBundleMap1" in="${unDeployableBundleMap}"> 
					
					<li><g:link controller="Bundle" action="show" id="${unDeployableBundleMap1.key}">${unDeployableBundleMap1.value}</g:link></li>      
                    </g:each>
                    </ul>
                    </g:if>
	</div>
</div>	
				
		<div
	class="control-group">
	<label for="bundles" class="control-label col-sw-1"><g:message
							code="program.bundles.label" default="Bundles" /></label>

					<div class="controls show-style">
						<!-- Add new Template --> <g:render template="addBundle" /> <!-- Added new table -->
						<g:render template="showBundles" />
					</div>
						
				<div
	class="control-group">
	<label for="dateCreated" class="control-label col-sw-1"><g:message
							code="program.dateCreated.label" default="Date Created" /></label>

					<div class="controls show-style"><g:formatDate
							date="${programInstance?.dateCreated}" />
							</div>
							</div>

				<div
	class="control-group">
	<label for="lastUpdated" class="control-label col-sw-1"><g:message
							code="program.lastUpdated.label" default="Last Updated" /></label>

					<div class="controls show-style"><g:formatDate
							date="${programInstance?.lastUpdated}" />
					</div>
					</div>

			</div>			
				
				
	
	</div>
	</section>
</div>
</div>
</div>
<g:render template="/_common/modals/scrollDialog"  />
</body>

</html>
