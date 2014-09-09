
<%@ page import="hmof.Bundle" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'bundle.label', default: 'Bundle')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
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
				<th>${''}</th>
			
				<g:sortableColumn property="isbn" title="${message(code: 'bundle.isbn.label', default: 'Isbn')}" />
				
				<th>${'Current Revision'}</th>
			
				<g:sortableColumn property="title" title="${message(code: 'bundle.title.label', default: 'Title')}" />
			
				<g:sortableColumn property="duration" title="${message(code: 'bundle.duration.label', default: 'Duration')}" />
				
				<th><g:message code="bundle.program.label" default="Program" /></th>								
				
				<th>${'Dev'}</th>
				
				<th>${'QA'}</th>
				
				<th>${'Prod'}</th>
			
			</tr>
		</thead>
		<tbody>
		<g:set var="jobdetails" bean="deploymentService"/>
		<g:each in="${bundleInstanceList}" status="i" var="bundleInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><input type="radio" name="rad" id="rad${i}" value="${bundleInstance.id}" onclick="toggle(this,'row${i}')"/>
				<g:link action="show" id="${bundleInstance.id}">${bundleInstance.id}</g:link> </td>
			
				<td><g:link action="show" id="${bundleInstance.id}">${fieldValue(bean: bundleInstance, field: "isbn")}</g:link></td>
				
				<td>${jobdetails.getCurrentEnversRevision(bundleInstance)}</td>
			
				<td>${fieldValue(bean: bundleInstance, field: "title")}</td>
			
				<td>${fieldValue(bean: bundleInstance, field: "duration")}</td>
				
				<td>${fieldValue(bean: bundleInstance, field: "program")}</td>							
				
				<g:set var="jobdetail" value="${jobdetails.getPromotionDetails(bundleInstance,1)}" />
						
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
				
				<g:set var="jobdetailQa" value="${jobdetails.getPromotionDetails(bundleInstance,2)}" />
				
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
				
				<g:set var="jobdetailprod" value="${jobdetails.getPromotionDetails(bundleInstance,3)}" />
				
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
		<bs:paginate total="${bundleInstanceCount}" />
	</div>
</section>

</body>

</html>
