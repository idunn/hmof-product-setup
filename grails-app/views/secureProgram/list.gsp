
<%@ page import="hmof.SecureProgram" %>
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
				<th>${''}</th>
			
				<g:sortableColumn property="productName" title="${message(code: 'secureProgram.productName.label', default: 'Product Name')}" />
				
				<th>${'Current Revision'}</th>
			
				<g:sortableColumn property="registrationIsbn" title="${message(code: 'secureProgram.registrationIsbn.label', default: 'Registration Isbn')}" />
			
				<g:sortableColumn property="onlineIsbn" title="${message(code: 'secureProgram.onlineIsbn.label', default: 'Online Isbn')}" />
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'secureProgram.dateCreated.label', default: 'Date Created')}" />				
			
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
			
				<td><input type="radio" name="rad" id="rad${i}" value="${secureProgramInstance.id}" onclick="toggle(this,'row${i}')"/>
				<g:link action="show" id="${secureProgramInstance.id}">${secureProgramInstance.id}</g:link> </td>
			
				<td><g:link action="show" id="${secureProgramInstance.id}">${fieldValue(bean: secureProgramInstance, field: "productName")}</g:link></td>
				
				<td>${jobdetails.getCurrentEnversRevision(secureProgramInstance)}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "registrationIsbn")}</td>
			
				<td>${fieldValue(bean: secureProgramInstance, field: "onlineIsbn")}</td>
			
				<td><g:formatDate date="${secureProgramInstance.dateCreated}" /></td>				
			
				<td>${fieldValue(bean: secureProgramInstance, field: "copyright")}</td>
				
				<g:set var="jobdetail" value="${jobdetails.getPromotionDetails(secureProgramInstance,1)}" />
						
				<td>				
					Job: ${jobdetail[0]}  
				<br>
					Status: ${jobdetail[1]} 
				<br>
					Revision: ${jobdetail[2]} 
				<br>
					User: ${jobdetail[3]} 
				<br>										
				</td>
				
				<g:set var="jobdetailQa" value="${jobdetails.getPromotionDetails(secureProgramInstance,2)}" />
				
				<td>
				Job: ${jobdetailQa[0]}
				<br>
				Status: ${jobdetailQa[1]}
				<br>
				Revision: ${jobdetailQa[2]}
				<br>
				User: ${jobdetailQa[3]}
				<br>
				</td>
				
				<g:set var="jobdetailprod" value="${jobdetails.getPromotionDetails(secureProgramInstance,3)}" />
				
				<td>
				Job: ${jobdetailprod[0]}
				<br>
				Status: ${jobdetailprod[1]}
				<br>
				Revision: ${jobdetailprod[2]}
				<br>
				User: ${jobdetailprod[3]}
				<br>
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
	
</g:form>
</div>
	<div>
		<bs:paginate total="${secureProgramInstanceCount}" />
	</div>
</section>

</body>

</html>
