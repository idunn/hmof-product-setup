
<%@ page import="hmof.Program" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'program.label', default: 'Program')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	
	<style>
	.off {
	background-color: #FFFAF0;
	}
	.on {
 	 background-color: #00BFFF;
	}
	</style>
	
</head>

<body>

<sec:ifNotLoggedIn>
		Please <g:link controller='login' action='auth'><b>login</b></g:link> to view Programs.
</sec:ifNotLoggedIn>
	
<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_PM, ROLE_QA, ROLE_PROD">

<section id="list-program" class="first">

<div>
<g:form>

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
				<th>${''}</th>
			
				<g:sortableColumn property="name" title="${message(code: 'program.name.label', default: 'Name')}" />
				
				<th>${'Current Revision'}</th>
			
				<g:sortableColumn property="discipline" title="${message(code: 'program.discipline.label', default: 'Discipline')}" />
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'program.dateCreated.label', default: 'Date Created')}" />				
				
				<th>${'Dev'}</th>
				
				<th>${'QA'}</th>
				
				<th>${'Prod'}</th>
				
				<th>${'View'}</th>
			
			</tr>
		</thead>
		<tbody>
		
		<g:set var="jobdetails" bean="deploymentService"/>
		<g:each in="${programInstanceList}" status="i" var="programInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><input type="radio" name="rad" id="rad${i}" value="${programInstance.id}" onclick="toggle(this,'row${i}')"/>
				<g:link action="show" id="${programInstance.id}">${programInstance.id}</g:link> </td>
			
				<td><g:link action="show" id="${programInstance.id}">${fieldValue(bean: programInstance, field: "name")}</g:link></td>
				
				<td>${jobdetails.getCurrentEnversRevision(programInstance)}</td>
			
				<td>${fieldValue(bean: programInstance, field: "discipline")}</td>
			
				<td><g:formatDate date="${programInstance.dateCreated}" /></td>				
				
				<g:set var="jobdetail" value="${jobdetails.getPromotionDetails(programInstance,1)}" />
						
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
				
				<g:set var="jobdetailQa" value="${jobdetails.getPromotionDetails(programInstance,2)}" />
				
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
				
				<g:set var="jobdetailprod" value="${jobdetails.getPromotionDetails(programInstance,3)}" />
				
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
				
				<td><g:link action="show" id="${programInstance.id}">${'view'}</g:link></td>
			
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
		<bs:paginate total="${programInstanceCount}" />
	</div>
</section>
</sec:ifAnyGranted>
</body>

</html>
