<%@ page import="hmof.DeploymentService"%>
<%@ page import="hmof.UtilityService"%>

		<g:set var="userdetails" bean="utilityService"/>
<sec:ifAnyGranted roles="ROLE_PM,ROLE_ADMIN">
	<li class="dropdown"><a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-edit"></i> <span><g:message code="default.submenu.job.promote" default="Create" /></span> <b class="caret"></b></a>
				<ul class="dropdown-menu">					
						<sec:ifAnyGranted roles="ROLE_ADMIN">
				<li>
				<a href="${createLink(controller:"program",action: 'create')}">Program</a>
				</li>	
				
				</sec:ifAnyGranted>
				<li>
				<a href="${createLink(controller:"bundle",action: 'create')}">Bundle</a>
				</li>
				<li>
				<a href="${createLink(controller:"secureProgram",action: 'create')}">Secure Program</a>
				</li>
				<li>
				<a href="${createLink(controller:"commerceObject",action: 'create')}">Commerce Object</a>
				</li>
				<li>
				<a href="${createLink(controller:"programXML",action: 'create')}">ProgramXML</a>
				</li>
				
				</ul>
				
				</li>
	</sec:ifAnyGranted>
