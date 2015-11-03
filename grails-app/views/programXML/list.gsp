
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
			<g:sortableColumn property="id" title="${message(code: 'programXML.id.label', default:'#')}" class="widget-header2" style="color:#bbb" />
				<g:sortableColumn property="title" title="${message(code: 'programXML.title.label', default: 'Title')}" class="widget-header2" style="color:#bbb"/><%--
			
				<g:sortableColumn property="buid" title="${message(code: 'programXML.buid.label', default: 'Buid')}" />
			
				<g:sortableColumn property="language" title="${message(code: 'programXML.language.label', default: 'Language')}" />
			
				--%><th class="widget-header2" style="color:#bbb">${'Current Revision'}</th><g:sortableColumn property="filename" title="${message(code: 'programXML.filename.label', default: 'Filename')}" class="widget-header2" style="color:#bbb" />
			<g:each in="${userdetail.getAllEnvironments()}" var="deploymentEnv">	
					<%-- Change which environments are shown based on the user groups and the environments configured for this type --%>
							
								<g:if test="${deploymentGroup.id==deploymentEnv?.groups?.id}">
						
								<th class="widget-header2" style="color:#bbb">
											${deploymentEnv.name}
								</th>
								
						</g:if>
				</g:each>
			</tr>
		</thead>
		<tbody>
		<g:each in="${programXMLInstanceList}" status="i" var="programXMLInstance">
		
		
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<% devLog = "${grails.util.Holders.config.cacheLocation}"+"/ProgramXML"+"/${programXMLInstance.buid}"+"/dev/log/"+"${programXMLInstance.buid}"+"-dev_log"+".log"
			   File devLogFile = new File(devLog)
			   qaLog = "${grails.util.Holders.config.cacheLocation}"+"/ProgramXML"+"/${programXMLInstance.buid}"+"/review/log/"+"${programXMLInstance.buid}"+"-review_log"+".log"
			   File qaLogFile = new File(qaLog)
			   prodLog = "${grails.util.Holders.config.cacheLocation}"+"/ProgramXML"+"/${programXMLInstance.buid}"+"/prod/log/"+"${programXMLInstance.buid}"+"-prod_log"+".log"
			   File prodLogFile = new File(prodLog)
			   
			   certLog = "${grails.util.Holders.config.cacheLocation}"+"/ProgramXML"+"/${programXMLInstance.buid}"+"/cert/log/"+"${programXMLInstance.buid}"+"-cert_log"+".log"
			   File certLogFile = new File(certLog)
			   intLog = "${grails.util.Holders.config.cacheLocation}"+"/ProgramXML"+"/${programXMLInstance.buid}"+"/int/log/"+"${programXMLInstance.buid}"+"-int_log"+".log"
			   File intLogFile = new File(intLog)
			   
			   
			    %>
				
				 <td><sec:ifAnyGranted roles="ROLE_PM, ROLE_QA, ROLE_PROD"><input type="radio" name="listGroup" id="listGroup" value="${programXMLInstance.id}" <g:if test='${(deploymentGroup.groupname == 'Content') && i == 0}'>checked='checked'</g:if> /></sec:ifAnyGranted><g:link action="show" id="${programXMLInstance.id}">${fieldValue(bean: programXMLInstance, field: "id")}	</g:link></td>
				
				<td><g:link action="show" id="${programXMLInstance.id}">${fieldValue(bean: programXMLInstance, field: "title")}</g:link></td>
				<td>Revision: ${jobdetails.getCurrentEnversRevision(programXMLInstance)}</td><%--
				
				<td>${fieldValue(bean: programXMLInstance, field: "buid")}</td>
			
				<td>${fieldValue(bean: programXMLInstance, field: "language")}</td>--%>
			
				<td>${fieldValue(bean: programXMLInstance, field: "filename")}</td>
				<%-- Output one cell for each environment --%>
													<g:each in="${userdetail.getAllEnvironments()}" var="deploymentEnv">
														<!-- Change which environments are shown based on the user groups and the environments configured for this type -->
														<g:if test="${deploymentGroup.id==deploymentEnv?.groups?.id}">				
														<g:set var="jobdetail" value="${jobdetails.getPromotionDetails(programXMLInstance,deploymentEnv.id)}" />
															<td>
																<ul class="unstyled">
														<li><g:if test="${jobdetail[0]!=null && jobdetail[1]!=null && jobdetail[2]!=null && jobdetail[3]!=null}">			
					<span class="label label-info" style="font-size:12px;padding-bottom:2px;">Job: ${jobdetail[0]}  </span>
				<br>
					<g:if test="${jobdetail[1]=="Success"}">
					<span class="label label-success"  style="font-size:12px;">Status:&nbsp;${jobdetail[1]}</span>
					</g:if>
					<g:if test="${jobdetail[1]=="Failure" || jobdetail[1].toString().contains("Failed")}">
					<span class="label label-danger" style="font-size:12px;">Status:&nbsp;${jobdetail[1]}</span>
					</g:if>
					<g:if test="${jobdetail[1]!="Success" && jobdetail[1]!="Failure" && !jobdetail[1].toString().contains("Failed")}">
					<span class="label label-warning" style="font-size:12px;">Status:&nbsp;${jobdetail[1]}</span>
					</g:if>
				<br>
					Revision: ${jobdetail[2]} 
				<br>
					User: ${jobdetail[3]} 
				<br>	
				
				                            <g:if test="${deploymentEnv.id==1 && devLogFile.exists()}">
												<a href='./download?logFile=<%=devLog%>'>Log File</a>
											</g:if>
										<g:if test="${deploymentEnv.id==2 && qaLogFile.exists()}">
												<a href='./download?logFile=<%=qaLog%>'>Log File</a>
											</g:if>
												<g:if test="${deploymentEnv.id==3 && prodLogFile.exists()}">
												<a href='./download?logFile=<%=prodLog%>'>Log File</a>
											</g:if>
												<g:if test="${deploymentEnv.id==4 && certLogFile.exists()}">
												<a href='./download?logFile=<%=certLog%>'>Log File</a>
											</g:if>
												<g:if test="${deploymentEnv.id==5 && intLogFile.exists()}">
												<a href='./download?logFile=<%=intLog%>'>Log File</a>
											</g:if>		
											
											
											
				</g:if>
											</li>
					
								
																</ul>
															</td>
														</g:if>
													</g:each>
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
