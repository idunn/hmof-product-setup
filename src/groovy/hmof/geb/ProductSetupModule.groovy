package hmof.geb

import geb.*

class GebModule extends Module {
	
	static content = {
		description { $("textarea", name: "Desc").value("Data entered using Product Setup Web Application") }
	}

}
