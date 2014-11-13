
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
		Please <g:link controller='login' action='auth'><b>login</b></g:link> to deploy content
</sec:ifNotLoggedIn>
	
<section id="list-program" class="first">

<div>
<g:form>

	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>				
				<g:sortableColumn property="id" title="${message(code: 'program.id.label', default:'#')}" />
			
				<g:sortableColumn property="name" title="${message(code: 'program.name.label', default: 'Name')}" />
				
				<th>${'Current Revision'}</th>
				
				<g:sortableColumn property="state" title="${message(code: 'program.state.label', default: 'State')}" />
			
				<g:sortableColumn property="discipline" title="${message(code: 'program.discipline.label', default: 'Discipline')}" />								
				
				<th>${'Dev'}</th>
				
				<th>${'QA'}</th>
				
				<th>${'Prod'}</th>				
			
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
				
				<td>${fieldValue(bean: programInstance, field: "state")}</td>	
			
				<td>${fieldValue(bean: programInstance, field: "discipline")}</td>							
				
				<g:set var="jobdetail" value="${jobdetails.getPromotionDetails(programInstance,1)}" />
						
				<td>	<g:if test="${jobdetail[0]!=null && jobdetail[1]!=null && jobdetail[2]!=null && jobdetail[3]!=null}">				
					Job: ${jobdetail[0]}  
				<br>
					Status: ${jobdetail[1]} 
				<br>
					Revision: ${jobdetail[2]} 
				<br>
					User: ${jobdetail[3]} 
				<br>	</g:if>									
				</td>
				
				<g:set var="jobdetailQa" value="${jobdetails.getPromotionDetails(programInstance,2)}" />
				
				<td><g:if test="${jobdetailQa[0]!=null && jobdetailQa[1]!=null && jobdetailQa[2]!=null && jobdetailQa[3]!=null}">
				Job: ${jobdetailQa[0]}
				<br>
				Status: ${jobdetailQa[1]}
				<br>
				Revision: ${jobdetailQa[2]}
				<br>
				User: ${jobdetailQa[3]}
				<br></g:if>
				</td>
				
				<g:set var="jobdetailprod" value="${jobdetails.getPromotionDetails(programInstance,3)}" />
				
				<td><g:if test="${jobdetailprod[0]!=null && jobdetailprod[1]!=null && jobdetailprod[2]!=null && jobdetailprod[3]!=null}">
				Job: ${jobdetailprod[0]}
				<br>
				Status: ${jobdetailprod[1]}
				<br>
				Revision: ${jobdetailprod[2]}
				<br>
				User: ${jobdetailprod[3]}
				<br></g:if>
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
		<bs:paginate total="${programInstanceCount}" />
	</div>
</section>
</body>

</html>
