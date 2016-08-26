package hmof

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(CommerceObject)
class CommerceObjectSpec extends Specification {

	@spock.lang.Unroll("validate Url: #existingUrl should have returned #shouldBeValid")
	def "Validate URL value"(){

		expect: // expect can be the only block in the test but it is recommended to use given: block for better readability
		new CommerceObject(teacherUrl:existingUrl).validate(['teacherUrl'])==shouldBeValid
		
		and:
		
		new CommerceObject(studentUrl:existingUrl).validate(['studentUrl'])==shouldBeValid

		where:
		existingUrl 																																				|| shouldBeValid
		"javascript:var sswin = window.open('/SocialStudies/ss_2013/examview/hs_modern_worldhistory_fl.html ');" 													|| true
		"javascript:var sswin = window.open('/content/hmof/math/gomath/na/gr6/interactive_student_edition_9780544083059_/examview_installer/9780544065710.html');" 	|| true
		"/tabnav/spytop.jsp?isbn=8830418577" 																														|| true
		"/apps/alchemy/editors/display.jsp?cid=E1CAE83EFA365C58A63A2B6787934A3A" 																					|| true
		"/ss2/ss05/ppc_0030374693/nsmedia/liveink" || true
		"/sh/hh4/003070412x/teacher/osp/one_stop.pdf" || true
		"/hrw/actv/fl_reading/fl_reading_jump_page.html" || true
		"/sh/ev_downloads/hst_sc_k_o_05" || true
		"/hrw/actv/toACTV.jsp?class=147" || true
		"http://www.google.com" || true
		"/wl2/wl08/teacher/expresate/osp/level_1ab/expresate_level_1AB/tools/online_osp_tools.htm" || true
		"/HRWHoap/MWSTeacherDashboardLoader?isbn=9780544158122&grade=12" || true
		"/content/hmof/language_arts/hmhcollections/ga/gr6/dlo_landing_page_9780544398665_/Teacher_Landing_Page/index.html?tab=1" || true
		"http://www.hmhfyi.com" || true
		"http://hmcurrentevents.com" || true
		"/content" || true
		"/tabnav/controller.jsp" || true
		"/content/hmof/math/gomath/tn/gr8/student_resources_9780544814745_" || true
		"/HRWHoap/MWSTeacherDashboardLoader?isbn=9780544570832&grade=9" || true
		"http://my.hrw.com"|| false
		""|| true
		"/api/sso/v3/authorize?client_id=152ced50-1369-4b19-8b26-8f3d5d9bfd6a.hmhco.com&scope=openid&response_type=id_token%20token&nonce=yb2748f16ca49a89fac1&state=af718d8abd90fad0d986&platform=HMOF&redirect_uri=https://www.hmhco.com/assessment/%23/?on_logout=close﻿" || false // has BOM
		"/api/sso/v3/authorize?client_id=152ced50-1369-4b19-8b26-8f3d5d9bfd6a.hmhco.com&scope=openid&response_type=id_token%20token&nonce=yb2748f16ca49a89fac1&state=af718d8abd90fad0d986&platform=HMOF&redirect_uri=https://www.hmhco.com/assessment/%23/?on_logout=close" || true
		"/api/sso/v3/authorize?client_id=152ced50-1369-4b19-8b26-8f3d5d9bfd6a.hmhco.com&scope=openid&response_type=id_token%20token&nonce=yb2748f16ca49a89fac1&state=af718d8abd90fad0d986&platform=HMOF&redirect_uri=https://www.hmhco.com/assessment/%23/?on_logout=close1" || true
		
	}


}
