
<%@ page import="hmof.programxml.ProgramXML" %>
<%@ page import="hmof.deploy.EnvironmentGrp"%>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'programXML.label', default: 'ProgramXML')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.min.css')}"
	type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'custom.css')}"
	type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
</head>

<body>
<sec:ifNotLoggedIn>
		Please <g:link controller='login' action='auth'><b>login</b></g:link> to deploy content
</sec:ifNotLoggedIn>
<section id="list-programXML" class="first">
	<g:set var="jobdetails" bean="deploymentService"/>
	<g:set var="userdetail" bean="utilityService"/>
<div class="panel panel-info">
 <div class="panel-heading" style="height:50px;" >
    <h3 class="panel-title"><b> &nbsp;</b></h3>
    <!-- Environment Group Control Buttons -->
				    	<div class="pull-right" style="margin-top:-20px;">
            				<input type="button" class="btn btn-default prev-slide" value="&lsaquo;">
            				
            				
            				<g:each in="${userdetail.getDeploymentEnvGroup()}" var="deploymentGroup" status="i">
            				
    							<hmof:ifGroupHasEnvironments environmentGroup="${deploymentGroup.id}">																																																									
									<hmof:groupButton environmentGroup="${deploymentGroup}" i="${i}"/>
								</hmof:ifGroupHasEnvironments>	
        					</g:each>
           					<input type="button" class="btn btn-default next-slide" value="&rsaquo;">
						</div>
  </div>
  <div class="panel-body" >
	
				
				<div class="widget-content">
						
				
					<div id="myCarousel" class="carousel" data-interval="false" >
   								<%-- Loop for each environment group, display each one on a different carousel tab --%>
    							<%-- Carousel items --%>
    							<div class="carousel-inner">
    							<!-- Loop for each environment group, display each one on a different carousel tab, make the first one (content) active by default  -->
    							<g:each in="${userdetail.getDeploymentEnvGroup()}" var="deploymentGroup">	
    							
    								<hmof:ifGroupHasEnvironments environmentGroup="${deploymentGroup.id}">
    									<div class="${deploymentGroup.groupname == 'Content'? 'active ' : ''}item" id="${deploymentGroup.groupname}">
	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="title" title="${message(code: 'programXML.title.label', default: 'Title')}" />
			
				<g:sortableColumn property="buid" title="${message(code: 'programXML.buid.label', default: 'Buid')}" />
			
				<g:sortableColumn property="language" title="${message(code: 'programXML.language.label', default: 'Language')}" />
			
				<g:sortableColumn property="filename" title="${message(code: 'programXML.filename.label', default: 'Filename')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${programXMLInstanceList}" status="i" var="programXMLInstance">
		
		
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${programXMLInstance.id}">${fieldValue(bean: programXMLInstance, field: "title")}</g:link></td>
			
				<td>${fieldValue(bean: programXMLInstance, field: "buid")}</td>
			
				<td>${fieldValue(bean: programXMLInstance, field: "language")}</td>
			
				<td>${fieldValue(bean: programXMLInstance, field: "filename")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	</div></hmof:ifGroupHasEnvironments>					
		 					
			 			</g:each>	
      				</div></div>
      				</div>
      				<!-- /.carousel -->
	<%-- Required to pass to JavaScript --%>
	
	<g:hiddenField name="programid"/>
	<g:hiddenField name="instanceDetail"/>
	<g:hiddenField name="instanceToBePromoted"/>
	<g:render template="/program/deploymentDialog"   />
		</div></div>
		
	<div>
		<bs:paginate total="${programXMLInstanceCount}" />
	</div>
</section>

</body>

</html>
