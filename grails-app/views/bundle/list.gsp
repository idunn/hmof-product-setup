<%@ page import="hmof.Bundle" %>
<%@ page import="hmof.DeploymentService"%>
<%@ page import="hmof.UtilityService"%>

<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'bundle.label', default: 'Bundle')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<script src="${resource(dir:'js',file:'myScript.js')}"></script>

<link rel="stylesheet" href="${resource(dir: 'css', file: 'scroller.css')}"
	type="text/css">
    
        <g:render template="/bundle/studentsliderjs"/>
        <g:render template="/bundle/teachersliderjs"/>
        
</head>

<body>

<sec:ifNotLoggedIn>
		Please <g:link controller='login' action='auth'><b>login</b></g:link> to deploy content
</sec:ifNotLoggedIn>


<section id="list-bundle" class="first">
<div>
<g:form>
	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="id" title="${message(code: 'program.id.label', default:'#')}" />
			
				<g:sortableColumn property="isbn" title="${message(code: 'bundle.isbn.label', default: 'Isbn')}" />
				
				<th>${'Current Revision'}</th>
			
				<g:sortableColumn property="title" title="${message(code: 'bundle.title.label', default: 'Title')}" />
			
				<g:sortableColumn property="duration" title="${message(code: 'bundle.duration.label', default: 'Duration')}" />
				<g:sortableColumn property="program" title="${message(code: 'bundle.program.label', default: 'Program')}" />
				<th >Preview</th>
												
				
				<g:render template="/_common/templates/headerEnv"/>
			
			</tr>
		</thead>
		<tbody>
		<g:set var="jobdetails" bean="deploymentService"/>
		<g:set var="userdetail" bean="utilityService"/>
		<g:each in="${bundleInstanceList}" status="i" var="bundleInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<% devLog = "${grails.util.Holders.config.cacheLocation}"+"/Bundles"+"/${bundleInstance.isbn}"+"/dev/log/"+"${bundleInstance.isbn}"+"-dev_log"+".log"
			   File devLogFile = new File(devLog)
			   qaLog = "${grails.util.Holders.config.cacheLocation}"+"/Bundles"+"/${bundleInstance.isbn}"+"/review/log/"+"${bundleInstance.isbn}"+"-review_log"+".log"
			   File qaLogFile = new File(qaLog)
			   prodLog = "${grails.util.Holders.config.cacheLocation}"+"/Bundles"+"/${bundleInstance.isbn}"+"/prod/log/"+"${bundleInstance.isbn}"+"-prod_log"+".log"
			   File prodLogFile = new File(prodLog) %>
				
				
					
				
				<td ><sec:ifAnyGranted roles="ROLE_PM, ROLE_QA, ROLE_PROD"><input type="radio" name="rad" id="rad${i}" value="${bundleInstance.id+"/"+jobdetails.getCurrentEnversRevision(bundleInstance)+"/"+jobdetails.getPromotionDetails(bundleInstance,jobdetails.getUserEnvironmentInformation())+"/false/false"}" onclick="toggle(this,'row${i}')"/><%-- Confirm dialog for Deploy/Promote  --%><g:render template="/_common/modals/confirmDialog"/></sec:ifAnyGranted>&nbsp;<g:link action="show" id="${bundleInstance.id}">${bundleInstance.id}</g:link></td>
			   
				<td><g:link action="show" id="${bundleInstance.id}">${fieldValue(bean: bundleInstance, field: "isbn")}</g:link> 
				
				
				<td>Revision: ${jobdetails.getCurrentEnversRevision(bundleInstance)}<br>Last updated by: ${userdetail.getLastUpdatedUserNameForBundle(jobdetails.getCurrentEnversRevision(bundleInstance))}</td>
			
				<td>${fieldValue(bean: bundleInstance, field: "title")}</td>
			
				<td>${fieldValue(bean: bundleInstance, field: "duration")}</td>
				<td>${fieldValue(bean: bundleInstance, field: "program")}</td>
									
				 <td  >
				<g:if test="${jobdetails.isDashboardSecureProgram(bundleInstance.id)==true}">	
			<ul style="margin: 0; padding: 13px;">
<li><a href="#"  class="previewlink"  onclick="return teacherPreview(${bundleInstance.id},'${bundleInstance.title}');"  >Teacher</a></li><li><a href="#"  class="previewlink"  onclick="return studentPreview(${bundleInstance.id},'${bundleInstance.title}');"  >Student</a></li>
</ul>
				</g:if>
				</td>
				<g:set var="jobdetail" value="${jobdetails.getPromotionDetails(bundleInstance,1)}" />
						
				<td>	
				<g:if test="${jobdetail[0]!=null && jobdetail[1]!=null && jobdetail[2]!=null && jobdetail[3]!=null}">			
					Job: ${jobdetail[0]}  
				<br>
					Status: ${jobdetail[1]} 
				<br>
					Revision: ${jobdetail[2]} 
				<br>
					User: ${jobdetail[3]} 
				<br>	</g:if>
				<br>
											<g:if test="${devLogFile.exists()}">
												<a href='./download?logFile=<%=devLog%>'>Log File</a>
											</g:if>
											<g:else>
				 
				</g:else>									
				</td>
				
				<g:set var="jobdetailQa" value="${jobdetails.getPromotionDetails(bundleInstance,2)}" />
				
				<td><g:if test="${jobdetailQa[0]!=null && jobdetailQa[1]!=null && jobdetailQa[2]!=null && jobdetailQa[3]!=null}">		
				Job: ${jobdetailQa[0]}
				<br>
				Status: ${jobdetailQa[1]}
				<br>
				Revision: ${jobdetailQa[2]}
				<br>
				User: ${jobdetailQa[3]}
				<br></g:if>
				<br>
											<g:if test="${qaLogFile.exists()}">
												<a href='./download?logFile=<%=qaLog%>'>Log File</a>
											</g:if>
											<g:else>
				 
				</g:else>
				</td>
				
				<g:set var="jobdetailprod" value="${jobdetails.getPromotionDetails(bundleInstance,3)}" />
				
				<td><g:if test="${jobdetailprod[0]!=null && jobdetailprod[1]!=null && jobdetailprod[2]!=null && jobdetailprod[3]!=null}">
				Job: ${jobdetailprod[0]}
				<br>
				Status: ${jobdetailprod[1]}
				<br>
				Revision: ${jobdetailprod[2]}
				<br>
				User: ${jobdetailprod[3]}
				<br></g:if>
				<br>
											<g:if test="${prodLogFile.exists()}">
												<a href='./download?logFile=<%=prodLog%>'>Log File</a>
											</g:if>
											<g:else>
				
				</g:else>
				</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<g:if test="${jobdetails.getUserEnvironmentInformation()==1}">
	<sec:ifAnyGranted roles="ROLE_PM">
	<g:actionSubmit style="color: #ffffff;background-color: #428bca;border-color: #357ebd;margin-right: 10px;margin-top: 10px;margin-bottom: 10px;" value="Deploy" onClick="return deploy()"/>
	</sec:ifAnyGranted>
	</g:if>
	<g:if test="${jobdetails.getUserEnvironmentInformation()==2 || jobdetails.getUserEnvironmentInformation()==3}">
	<sec:ifAnyGranted roles="ROLE_QA, ROLE_PROD">
	<g:actionSubmit style="color: #ffffff;background-color: #428bca;border-color: #357ebd;margin-right: 10px;margin-top: 10px;margin-bottom: 10px;" value="Promote" onClick="return promote()"/>
	</sec:ifAnyGranted>
	</g:if>
	<%-- Required to pass to JavaScript --%>
	<g:hiddenField name="instanceDetail"/>
	<g:hiddenField name="instanceToBePromoted"/>
			<g:render template="/_common/modals/scrollDialog"   />
</g:form>
</div>
	<div>
		<bs:paginate total="${bundleInstanceCount}" />
	</div>
</section>

</body>

</html>
