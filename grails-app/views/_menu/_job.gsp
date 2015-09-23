<%@ page import="hmof.DeploymentService"%>
<%@ page import="hmof.UtilityService"%>

		<g:set var="userdetails" bean="utilityService"/>
<g:if test="${(params.controller == 'program' && params.action !='confirm') }">

	<g:if test="${programInstance || programInstanceList}">
		<g:set var="currId" value="${programInstance?.id ?: programInstanceList?.first()?.id}"/>
		
			<%-- NB jquery data seems to be case sensitive!  data-jobId doesn't work! --%>
			<li class="dropdown"><a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-edit"></i> <span><g:message code="default.submenu.job.promote" default="Promote to Environment" /></span> <b class="caret"></b></a>
				<ul class="dropdown-menu">	
					<!--   The class is just for the JQuery to differentiate it from other hrefs.  See custom.js -->
					<g:each in="${userdetails.getUserEnvironmentInfo()}" status="i" var="deploymentEnv">
				
						<%-- Only show if the current user has permission to deploy to this environment type --%>
							<li>
							
							<a href="#DeploymentModal" role="button" data-env="${deploymentEnv?.id}"  data-url="${createLink(controller:"program",action: 'confirm')}" data-jobid="${currId}" class="open-NewDeploymentDialog" >
							<g:message code="default.submenu.product.deploy" default="${deploymentEnv?.name}" />
							</a>
							</li>
						
					</g:each>
				</ul>
			</li>	
		
			
	</g:if>
</g:if>
<g:if test="${(params.controller == 'bundle' && params.action !='confirm') }">

	<g:if test="${bundleInstance || bundleInstanceList}">
		<g:set var="currbundleId" value="${bundleInstance?.id ?: bundleInstanceList?.first()?.id}"/>
		
		
			<%-- NB jquery data seems to be case sensitive!  data-jobId doesn't work! --%>
			<li class="dropdown"><a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-edit"></i> <span><g:message code="default.submenu.job.promote" default="Promote to Environment" /></span> <b class="caret"></b></a>
				<ul class="dropdown-menu">	
					<!--   The class is just for the JQuery to differentiate it from other hrefs.  See custom.js -->
					<g:each in="${userdetails.getUserEnvironmentInfo()}" status="i" var="deploymentEnv">
						
						<%-- Only show if the current user has permission to deploy to this environment type --%>
							<li>
							<a href="#DeploymentModal" role="button" data-env="${deploymentEnv?.id}"  data-url="${createLink(controller:"bundle",action: 'confirm')}" data-jobid="${currbundleId}" class="open-NewDeploymentDialog" >
							<g:message code="default.submenu.bundle.deploy" default="${deploymentEnv?.name}" />
							</a>
							</li>
						
					</g:each>
				</ul>
			</li>	
		
			
	</g:if>
</g:if>
<g:if test="${(params.controller == 'commerceObject' && params.action !='confirm') }">

	<g:if test="${commerceObjectInstance || commerceObjectInstanceList}">
		<g:set var="currId" value="${commerceObjectInstance?.id ?: commerceObjectInstanceList?.first()?.id}"/>
		
		
			<%-- NB jquery data seems to be case sensitive!  data-jobId doesn't work! --%>
			<li class="dropdown"><a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-edit"></i> <span><g:message code="default.submenu.job.promote" default="Promote to Environment" /></span> <b class="caret"></b></a>
				<ul class="dropdown-menu">	
					<!--   The class is just for the JQuery to differentiate it from other hrefs.  See custom.js -->
					<g:each in="${userdetails.getUserEnvironmentInfo()}" status="i" var="deploymentEnv">
						
						<%-- Only show if the current user has permission to deploy to this environment type --%>
							<li>
							<a href="#DeploymentModal" role="button" data-env="${deploymentEnv?.id}"  data-url="${createLink(controller:"commerceObject",action: 'confirm')}" data-jobid="${currId}" class="open-NewDeploymentDialog" >
							<g:message code="default.submenu.commerceObject.deploy" default="${deploymentEnv?.name}" />
							</a>
							</li>
						
					</g:each>
				</ul>
			</li>	
		
			
	</g:if>
</g:if>
<g:if test="${(params.controller == 'secureProgram' && params.action !='confirm') }">

	<g:if test="${secureProgramInstance || secureProgramInstanceList}">
		<g:set var="currId" value="${secureProgramInstance?.id ?: secureProgramInstanceList?.first()?.id}"/>
		
		
			<%-- NB jquery data seems to be case sensitive!  data-jobId doesn't work! --%>
			<li class="dropdown"><a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-edit"></i> <span><g:message code="default.submenu.job.promote" default="Promote to Environment" /></span> <b class="caret"></b></a>
				<ul class="dropdown-menu">	
					<!--   The class is just for the JQuery to differentiate it from other hrefs.  See custom.js -->
					<g:each in="${userdetails.getUserEnvironmentInfo()}" status="i" var="deploymentEnv">
						
						<%-- Only show if the current user has permission to deploy to this environment type --%>
							<li>
							<a href="#DeploymentModal" role="button" data-env="${deploymentEnv?.id}"  data-url="${createLink(controller:"secureProgram",action: 'confirm')}" data-jobid="${currId}" class="open-NewDeploymentDialog" >
							<g:message code="default.submenu.secureProgram.deploy" default="${deploymentEnv?.name}" />
							</a>
							</li>
						
					</g:each>
				</ul>
			</li>	
		
			
	</g:if>
</g:if>