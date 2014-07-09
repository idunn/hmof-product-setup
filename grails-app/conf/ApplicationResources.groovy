modules = {
    application {
		dependsOn 'jquery'
		resource url:'js/custom.js' // Add to every page - file taken from CDM
        resource url:'js/application.js' // default		
    }
	
	//list {resource url:'js/plugins/faq/faq.css'}
}