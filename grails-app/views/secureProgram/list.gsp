
<%@ page import="hmof.SecureProgram" %>
<%@ page import="hmof.DeploymentService"%>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'secureProgram.label', default: 'SecureProgram')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>

<sec:ifNotLoggedIn>
		Please <g:link controller='login' action='auth'><b>login</b></g:link> to deploy content
</sec:ifNotLoggedIn>

<section id="list-secureProgram" class="first">

<div>
<g:form>

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
				<g:sortableColumn property="id" title="${message(code: 'program.id.label', default:'#')}" />
			
				<g:sortableColumn property="productName" title="${message(code: 'secureProgram.productName.label', default: 'Product Name')}" />
				
				<th>${'Current Revision'}</th>
			
				<g:sortableColumn property="registrationIsbn" title="${message(code: 'secureProgram.registrationIsbn.label', default: 'Registration Isbn')}" />
			
				<g:sortableColumn property="onlineIsbn" title="${message(code: 'secureProgram.onlineIsbn.label', default: 'Online Isbn')}" />								
			
				<g:sortableColumn property="copyright" title="${message(code: 'secureProgram.copyright.label', default: 'Copyright')}" />
				
				<th>${'Dev'}</th>
				
				<th>${'QA'}</th>
				
				<th>${'Prod'}</th>
			
			</tr>
		</thead>
		<tbody>
		<g:set var="jobdetails" bean="deploymentService"/>
		<g:each in="${secureProgramInstanceList}" status="i" var="secureProgramInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<% devLog = "${grails.util.Holders.config.cacheLocation}"+"/Secure Programs"+"/${secureProgramInstance.registrationIsbn}"+"/dev/log/"+"${secureProgramInstance.registrationIsbn}"+"-dev_log"+".log"
			   File devLogFile = new File(devLog)
			   qaLog = "${grails.util.Holders.config.cacheLocation}"+"/Secure Programs"+"/${secureProgramInstance.registrationIsbn}"+"/review/log/"+"${secureProgramInstance.registrationIsbn}"+"-review_log"+".log"
			   File qaLogFile = new File(qaLog)
			   prodLog = "${grails.util.Holders.config.cacheLocation}"+"/Secure Programs"+"/${secureProgramInstance.registrationIsbn}"+"/prod/log/"+"${secureProgramInstance.registrationIsbn}"+"-prod_log"+".log"
			   File prodLogFile = new File(prodLog) %>
				
				
				<td><sec:ifAnyGranted roles="ROLE_PM, ROLE_QA, ROLE_PROD"><input type="radio" name="rad" id="rad${i}" value="${secureProgramInstance.id+"/"+jobdetails.getCurrentEnversRevision(secureProgramInstance)+"/"+jobdetails.getPromotionDetails(secureProgramInstance,jobdetails.getUserEnvironmentIdInformation())+"/false"}" onclick="toggle(this,'row${i}')"/>
				</sec:ifAnyGranted>
				<g:link action="show" id="${secureProgramInstance.id}">${secureProgramInstance.id}</g:link> </td>
			
				<td><g:link action="show" id="${secureProgramInstance.id}">${fieldValue(bean: secureProgramInstance, field: "productName")}</g:link></td>
				
				<td>${jobdetails.getCurrentEnversRevision(secureProgramInstance)}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "registrationIsbn")}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "onlineIsbn")}</td>						
			
				<td>${fieldValue(bean: secureProgramInstance, field: "copyright")}</td>
				
				<g:set var="jobdetail" value="${jobdetails.getPromotionDetails(secureProgramInstance,1)}" />
						
				<td>	<g:if test="${jobdetail[0]!=null && jobdetail[1]!=null && jobdetail[2]!=null && jobdetail[3]!=null}">				
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
														
				</td>
				
				<g:set var="jobdetailQa" value="${jobdetails.getPromotionDetails(secureProgramInstance,2)}" />
				
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
											
				</td>
				
				<g:set var="jobdetailprod" value="${jobdetails.getPromotionDetails(secureProgramInstance,3)}" />
				
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
											
				</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	
	<sec:ifAnyGranted roles="ROLE_PM">
	<g:actionSubmit style="color: #ffffff;background-color: #428bca;border-color: #357ebd;margin-right: 10px;margin-top: 10px;margin-bottom: 10px;" value="Deploy" onClick="return deploy()"/>
	</sec:ifAnyGranted>
	
	<sec:ifAnyGranted roles="ROLE_QA, ROLE_PROD">
	<g:actionSubmit style="color: #ffffff;background-color: #428bca;border-color: #357ebd;margin-right: 10px;margin-top: 10px;margin-bottom: 10px;" value="Promote" onClick="return promote()"/>
	</sec:ifAnyGranted>
	
	<%-- Required to pass to JavaScript --%>
	<g:hiddenField name="instanceDetail"/>
	<g:hiddenField name="instanceToBePromoted"/>
		<%-- Confirm dialog for Deploy/Promote  --%>
		<g:render template="/_common/modals/confirmDialog"/>
</g:form>
</div>
	<div>
		<bs:paginate total="${secureProgramInstanceCount}" />
	</div>
</section>

</body>

</html>
