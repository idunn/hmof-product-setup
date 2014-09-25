<g:form controller="search" action="search" method="post" class="navbar-form navbar-left" >
	<div class="form-group fieldcontain text-center">
		<input name="q" type="text" class="form-control nav-search" placeholder="${message(code: 'search.navbar.placeholder', default: 'Search ...')}" value="${params.q}">
	<input type="hidden"  name="max" value="${params.max ?: 5}"   />
	</div>
</g:form>
