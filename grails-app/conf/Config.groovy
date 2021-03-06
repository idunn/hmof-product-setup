// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
	all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
	atom:          'application/atom+xml',
	css:           'text/css',
	csv:           'text/csv',
	form:          'application/x-www-form-urlencoded',
	html:          ['text/html','application/xhtml+xml'],
	js:            'text/javascript',
	json:          ['application/json', 'text/json'],
	multipartForm: 'multipart/form-data',
	rss:           'application/rss+xml',
	text:          'text/plain',
	hal:           ['application/hal+json','application/hal+xml'],
	xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000
def logDirectory = '.'
// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.resources.adhoc.includes = ['/images/**', '/css/**', '/js/**', '/plugins/**']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
	views {
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
			codecs {
				expression = 'html' // escapes values inside ${}
				scriptlet = 'html' // escapes output from scriptlets in GSPs
				taglib = 'none' // escapes output from taglibs
				staticparts = 'none' // escapes output from static template parts
			}
		}
		// escapes all not-encoded output at final stage of outputting
		// filteringCodecForContentType.'text/html' = 'html'
	}
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
	development {
		grails.logging.jul.usebridge = true
	}
	production {
		grails.logging.jul.usebridge = false
		// TODO: grails.serverURL = "http://www.changeme.com"
	}
}

// log4j configuration
environments {
	development {

		cacheLocation="target/ProductSetup-cache"
		uploadFolder="target/ProductSetup-cache/import"
		//programXMLFolder="C:/ProductSetup-cache/ProgramXML/hmof/"
		programXMLFolder="C:/productSetup-cache/ProgramXML/MDS_Program/"			
		programXMLISBNsFolder="C:/ProductSetup-cache/ProgramXML/MDS_ISBNS/"
		
		javamelody.'storage-directory' = 'target/ProductSetup-cache/PS_Monitoring/PS_javamelody'
		// log4j configuration
		log4j = {

			appenders {
				console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')

				rollingFile name:'file', file: logDirectory + '/target/ProductSetup.log', threshold: org.apache.log4j.Level.INFO, maxFileSize:"1MB", maxBackupIndex: 10, 'append':true
			}

			root {
				error 'stdout', 'file'
				additivity = true
			}
			debug   'hmof.geb'
			error   'org.codehaus.groovy.grails.web.servlet',        // controllers
					'org.codehaus.groovy.grails.web.pages',          // GSP
					'org.codehaus.groovy.grails.web.sitemesh',       // layouts
					'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
					'org.codehaus.groovy.grails.web.mapping',        // URL mapping
					'org.codehaus.groovy.grails.commons',            // core / classloading
					'org.codehaus.groovy.grails.plugins',            // plugins
					'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
					'org.springframework',
					'org.hibernate',
					'net.sf.ehcache.hibernate'


			debug   "grails.app.controllers.hmof",
					"grails.app.services.hmof" ,
					"grails.app.domain.hmof"

			info    "grails.app.controllers.hmof",
					"grails.app.services.hmof" ,
					"grails.app.domain.hmof"
		}
	}

	production{

		cacheLocation="E:/ProductSetup-cache"
		uploadFolder="E:/ProductSetup-cache/import"
		//programXMLFolder="E:/ProductSetup-cache/ProgramXML/MDS_Program/hmof/"
		//programXMLFolder="C:/ProductSetup-cache/ProgramXML/hmof/"
		programXMLFolder="E:/productSetup-cache/ProgramXML/MDS_Program/"
		programXMLISBNsFolder="E:/ProductSetup-cache/ProgramXML/MDS_ISBNS/"
	
		javamelody.'storage-directory' = 'E:/ProductSetup-cache/PS_Monitoring/PS_javamelody'
		// log4j configuration
		log4j = {

			appenders {
				rollingFile name:'file', file: logDirectory + '/logs/ProductSetupLogs/ProductSetup.log', threshold: org.apache.log4j.Level.INFO, maxFileSize:"1MB", maxBackupIndex: 10, 'append':true
			}
			debug   'hmof.geb'
			error   'org.codehaus.groovy.grails.web.servlet',        // controllers
					'org.codehaus.groovy.grails.web.pages',          // GSP
					'org.codehaus.groovy.grails.web.sitemesh',       // layouts
					'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
					'org.codehaus.groovy.grails.web.mapping',        // URL mapping
					'org.codehaus.groovy.grails.commons',            // core / classloading
					'org.codehaus.groovy.grails.plugins',            // plugins
					'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
					'org.springframework',
					'org.hibernate',
					'net.sf.ehcache.hibernate'

			warn    'grails.app.service'
			warn    'grails.app.controller'
			info    'grails.app.controllers.hmof',
					'grails.app.services.hmof',
					'grails.app.domain.hmof'

			root {
				error 'file'
				additivity = true
			}
		}
	}
}


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'hmof.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'hmof.security.UserRole'
grails.plugin.springsecurity.authority.className = 'hmof.security.Role'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/**':                            ['permitAll'],
	'/index':                         ['permitAll'],
	'/index.gsp':                     ['permitAll'],
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll'],
	'/user/**':                       ['ROLE_ADMIN'],
	'/quartz/**':                     ['ROLE_ADMIN'],
	'/secureProgram/importCSV/**':    ['ROLE_ADMIN'],
	'/commerceObject/importCSV/**':   ['ROLE_ADMIN'],
	'/monitoring/**':                 ['ROLE_ADMIN'],
	'/environment/**':                ['ROLE_ADMIN'],
	'/environmentGrp/**':             ['ROLE_ADMIN'],
	'/dbconsole/**':                  ['ROLE_ADMIN']
	 
]

// Application Specific security settings

grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.securityConfigType = "Annotation"
//grails.gorm.default.constraints = {	globalUrl(blank: false, nullable: true, matches:/^(http:\/\/|https:\/\/|\/|javascript:)[\w:\.\/\s';"()=?&-]+/) }
// relaxed URL input match
grails.gorm.default.constraints = {	globalUrl(blank: false, nullable: true, matches:/^(http:\/\/|https:\/\/|\/|javascript:)[\w\W]+/) }


//Bamboo Accounts
bamboo.username = 'customdev'
bamboo.password = 'cust0mD3v!'
bamboo.test.username = 'admin'
bamboo.test.password = 'admin'
bamboo.hosturl="http://dubv-engbam05.dubeng.local:8085"
bamboo.resulturl="http://dubv-engbam05.dubeng.local:8085/rest/api/latest/result/"
bamboo.browse.plan="http://dubv-engbam05.dubeng.local:8085/browse/"





//ProgramXMl Accounts
svn.username='cust_dev_app'
svn.password='JDv9cpp'

svn.url ="http://dubsvn.hmco.com/svn/MDS_Content/trunk/MDS/CERT-REVIEW/program"
svn.isbnsurl ="http://dubsvn.hmco.com/svn/MDS_Content/trunk/MDS/CERT-REVIEW/metadata"