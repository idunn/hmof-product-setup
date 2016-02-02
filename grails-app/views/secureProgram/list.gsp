
<%@ page import="hmof.SecureProgram" %>
<%@ page import="hmof.DeploymentService"%>
<%@ page import="hmof.UtilityService"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'secureProgram.label', default: 'SecureProgram')}" />
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

<section id="list-secureProgram" class="first">

<g:form>
	<g:set var="jobdetails" bean="deploymentService"/>
	<g:set var="userdetail" bean="utilityService"/>
<div class="panel panel-info">
 <div class="panel-heading" style="height:50px;" >
    <h3 class="panel-title"><b>&nbsp;</b></h3>
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
	<g:if test="${flash.message}">
			<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
				
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
				<g:sortableColumn property="id" title="${message(code: 'program.id.label', default:'#')}" class="widget-header2" style="color:#bbb"/>
			
				<g:sortableColumn property="productName" title="${message(code: 'secureProgram.productName.label', default: 'Product Name')}" class="widget-header2" style="color:#bbb"/>
				
				<th class="widget-header2" style="color:#bbb">${'Current Revision'}</th>
			
				<g:sortableColumn property="registrationIsbn" title="${message(code: 'secureProgram.registrationIsbn.label', default: 'Registration Isbn')}" class="widget-header2" style="color:#bbb"/>
			
				<g:sortableColumn property="onlineIsbn" title="${message(code: 'secureProgram.onlineIsbn.label', default: 'Online Isbn')}" class="widget-header2" style="color:#bbb"/>								
			
				<g:sortableColumn property="copyright" title="${message(code: 'secureProgram.copyright.label', default: 'Copyright')}" class="widget-header2" style="color:#bbb"/>
				
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
		<g:set var="jobdetails" bean="deploymentService"/>
		<g:set var="userdetail" bean="utilityService"/>
		<g:each in="${secureProgramInstanceList}" status="i" var="secureProgramInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<% 
		    
			   devLog = "${grails.util.Holders.config.cacheLocation}"+"/Secure Programs"+"/${secureProgramInstance.registrationIsbn}"+"/dev/log/"+"${secureProgramInstance.registrationIsbn}"+"-dev_log"+".log"
			   File devLogFile = new File(devLog)
			   qaLog = "${grails.util.Holders.config.cacheLocation}"+"/Secure Programs"+"/${secureProgramInstance.registrationIsbn}"+"/review/log/"+"${secureProgramInstance.registrationIsbn}"+"-review_log"+".log"
			   File qaLogFile = new File(qaLog)
			   prodLog = "${grails.util.Holders.config.cacheLocation}"+"/Secure Programs"+"/${secureProgramInstance.registrationIsbn}"+"/prod/log/"+"${secureProgramInstance.registrationIsbn}"+"-prod_log"+".log"
			   File prodLogFile = new File(prodLog)
			   certLog = "${grails.util.Holders.config.cacheLocation}"+"/Secure Programs"+"/${secureProgramInstance.registrationIsbn}"+"/cert/log/"+"${secureProgramInstance.registrationIsbn}"+"-cert_log"+".log"
			   File certLogFile = new File(certLog)
			   intLog = "${grails.util.Holders.config.cacheLocation}"+"/Secure Programs"+"/${secureProgramInstance.registrationIsbn}"+"/int/log/"+"${secureProgramInstance.registrationIsbn}"+"-int_log"+".log"
			   File intLogFile = new File(intLog)
			   
			   
			    %>
				
				
				<%--<td><sec:ifAnyGranted roles="ROLE_PM, ROLE_QA, ROLE_PROD"><input type="radio" name="rad" id="rad${i}" value="${secureProgramInstance.id+"/"+jobdetails.getCurrentEnversRevision(secureProgramInstance)+"/"+jobdetails.getPromotionDetails(secureProgramInstance,jobdetails.getUserEnvironmentInformation())+"/false/false"}" onclick="toggle(this,'row${i}')"/>
				</sec:ifAnyGranted>
				<g:link action="show" id="${secureProgramInstance.id}">${secureProgramInstance.id}</g:link> </td>
			
				--%>
				
				 <td><sec:ifAnyGranted roles="ROLE_PM, ROLE_QA, ROLE_PROD"><input type="radio" name="listGroup" id="listGroup" value="${secureProgramInstance.id}" <g:if test='${(deploymentGroup.groupname == 'Content') && i == 0}'>checked='checked'</g:if> /></sec:ifAnyGranted><g:link action="show" id="${secureProgramInstance.id}">${fieldValue(bean: secureProgramInstance, field: "id")}	</g:link></td>
				
				<td><g:link action="show" id="${secureProgramInstance.id}">${fieldValue(bean: secureProgramInstance, field: "productName")}</g:link></td>
				
				<td>Revision: ${jobdetails.getCurrentEnversRevision(secureProgramInstance)}<br>Last updated by: ${userdetail.getLastUpdatedUserNameForSP(jobdetails.getCurrentEnversRevision(secureProgramInstance))}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "registrationIsbn")}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "onlineIsbn")}</td>						
			
				<td>${fieldValue(bean: secureProgramInstance, field: "copyright")}</td>
				<%-- Output one cell for each environment --%>
													<g:each in="${userdetail.getAllEnvironments()}" var="deploymentEnv">
														<!-- Change which environments are shown based on the user groups and the environments configured for this type -->
														<g:if test="${deploymentGroup.id==deploymentEnv?.groups?.id}">				
														<g:set var="jobdetail" value="${jobdetails.getPromotionDetails(secureProgramInstance,deploymentEnv.id)}" />
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
	<g:render template="/secureProgram/deploymentDialog"   />
		</div></div>
</g:form>

	<div>
		<bs:paginate total="${secureProgramInstanceCount}" />
	</div>
</section>

</body>

</html>
