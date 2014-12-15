<li class="dropdown">
	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
   		<i class="glyphicon glyphicon-wrench"></i>
		<g:message code="default.admin.label"/><b class="caret"></b>
	</a>
	<ul class="dropdown-menu">
		<li class="">
			<a tabindex="-1" href="#"><b>Technical Admin</b></a>
		</li>
		<g:if env="development">
		<li class="">
			<a href="${createLink(uri: '/dbconsole')}">
				<i class="glyphicon glyphicon-dashboard"></i>
				<g:message code="default.dbconsole.label"/>
			</a>
		</li>
		</g:if>
		<li class="">
			<a href="${createLink(uri: '/systeminfo')}">
				<i class="glyphicon glyphicon-info-sign"></i>
				<g:message code="default.systeminfo.label"/>
			</a>
		</li>
			<sec:ifAnyGranted roles="ROLE_ADMIN">
		<li class="">
			<a href="${createLink(uri: '/user')}">
				<i class="glyphicon glyphicon-info-sign"></i>
				Manage Roles			</a>
		</li>
		<li class="">
			<a href="${createLink(uri: '/commerceObject/importCSV')}">
				<i class="glyphicon glyphicon-info-sign"></i>
				CSV Import
			</a>
		</li>
		</sec:ifAnyGranted>
	</ul>
</li>
