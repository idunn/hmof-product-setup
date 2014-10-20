<li class="dropdown dropdown-btn">
	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
   		<i class="glyphicon glyphicon-info-sign"></i>
		<g:message code="default.info.label"/> <b class="caret"></b>
	</a>
	
	<ul class="dropdown-menu">
		<%-- Note: Links to pages without controller are redirected in conf/UrlMappings.groovy --%>
		<li class="">
		
			<a target="_blank" href="http://dubconf.hmhpub.com:8080/display/tool/User+Guide+-+Product+Setup+App"><i class="glyphicon glyphicon-book"></i>User Guide</a>
		</li>
		<li class="">
		
			<a target="_blank" href="http://dubconf.hmhpub.com:8080/display/tool/HMOF+Product+Setup+Best+Practices"><i class="glyphicon glyphicon-hand-right"></i>Best Practices</a>
		</li>
		<li class="">
		
			<a target="_blank" href="https://jira.hmhpub.com/secure/CreateIssueDetails!init.jspa?pid=30000535&issuetype=1&components=30020649&summary=I%20found%20a%20bug!&customfield_10044=10221&priority=4"><i class="glyphicon glyphicon-remove"></i>Log a bug</a>
		</li>
		<li class="">
			<a href="${createLink(uri: '/contact')}">
				<i class="glyphicon glyphicon-envelope"></i>
				<g:message code="default.contact.label"/>
			</a>
		</li>
	</ul>
</li>
