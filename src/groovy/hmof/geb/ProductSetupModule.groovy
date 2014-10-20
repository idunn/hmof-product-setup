package hmof.geb

import geb.*

class GebModule extends Module {
	
	static content = {
		description { $("textarea", name: "Desc")}
		addButton{$("input", value: "Add")}
		//TODO test iContains
		homeButton{$("a", text: iContains("Home"))}
		updateButton{$("input", value: "Update")}
		updateButtonName{$("input", name: "Update")}
		updateLink(required:false){$("a", href: contains("Update"))}
	}

}
