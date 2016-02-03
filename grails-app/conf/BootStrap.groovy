import hmof.Bundle
import hmof.CommerceObject
import hmof.ContentType
import hmof.Program
import hmof.SecureProgram
import hmof.programxml.ProgramXML
import hmof.deploy.Environment
import hmof.deploy.EnvironmentGrp
import hmof.deploy.Job
import hmof.deploy.Promotion
import hmof.security.Role
import hmof.security.User
import hmof.security.UserRole

class BootStrap {

	def init = { servletContext ->


		// test if data exists by looking for a Program on startup
		if(Program.where{}.list().isEmpty()){

			//Security Order matters but shouldn't there is a bug where a User cannot be in more than one group

			def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
			def pmRole = Role.findByAuthority('ROLE_PM') ?: new Role(authority: 'ROLE_PM').save(failOnError: true)
			def qaRole = Role.findByAuthority('ROLE_QA') ?: new Role(authority: 'ROLE_QA').save(failOnError: true)
			def prodRole = Role.findByAuthority('ROLE_PROD') ?: new Role(authority: 'ROLE_PROD').save(failOnError: true)
			def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)

			// Admin users
			def adminUser = User.findByUsername('Admin') ?: new User(username: 'Admin', password: 'spr1ngt1me',	email: 'DublinTPDCustomDevelopment@hmhco.com').save(failOnError: true)
			if (!adminUser.authorities.contains(adminRole)) {UserRole.create adminUser, adminRole}

			def adminUser2 = User.findByUsername('noconnor') ?: new User(username: 'noconnor',password: 'Hs63hej',email: 'niall.oconnor@hmhco.com').save(failOnError: true)
			if (!adminUser2.authorities.contains(adminRole)) {UserRole.create adminUser2, adminRole}

			def adminUser3 = User.findByUsername('arollapati') ?: new User(username: 'arollapati',password: 'Ji9ahhia',email: 'aparna.r2@cognizant.com').save(failOnError: true)
			if (!adminUser3.authorities.contains(adminRole)) {UserRole.create adminUser3, adminRole}




			// Dev Users
			def devUser = User.findByUsername('PM') ?: new User(username: 'PM',	password: 'spr1ngt1me').save(failOnError: true)
			if (!devUser.authorities.contains(pmRole)) {UserRole.create devUser, pmRole	}

			def devUser2 = User.findByUsername('tosullivan') ?: new User(username: 'tosullivan',password: 'bYAw2C',	email: 'terri.osullivan@hmhco.com').save(failOnError: true)
			if (!devUser2.authorities.contains(pmRole)) {UserRole.create devUser2, pmRole}

			def devUser3 = User.findByUsername('jgriffin') ?: new User(	username: 'jgriffin',password: 'jooF7Xoh',email: 'jennifer.griffin@hmhco.com').save(failOnError: true)
			if (!devUser3.authorities.contains(pmRole)) {UserRole.create devUser3, pmRole}

			def devUser4 = User.findByUsername('raswal') ?: new User(username: 'raswal',password: 'ysit8d3M',email: 'ritesh.aswal@niit.com').save(failOnError: true)
			if (!devUser4.authorities.contains(pmRole)) {UserRole.create devUser4, pmRole}

			def devUser5 = User.findByUsername('ashishy') ?: new User(username: 'ashishy',password: 'Z4egh7',email: 'ashish.yadav@niit.com').save(failOnError: true)
			if (!devUser5.authorities.contains(pmRole)) {UserRole.create devUser5, pmRole}

			def devUser6 = User.findByUsername('apadavil') ?: new User(username: 'apadavil',password: 'Ri57hjj',email: 'amrita.padavil@niit.com').save(failOnError: true)
			if (!devUser6.authorities.contains(pmRole)) {UserRole.create devUser6, pmRole}

			def devUser7 = User.findByUsername('rkanwar') ?: new User(username: 'rkanwar',password: 'qSfg44t',email: 'rohit.9.kanwar@niit.com').save(failOnError: true)
			if (!devUser7.authorities.contains(pmRole)) {UserRole.create devUser7, pmRole}

			def devUser8 = User.findByUsername('gpraveen') ?: new User(username: 'gpraveen',password: 'CaaXoh7',email: 'praveen.9.gaur@niit.com').save(failOnError: true)
			if (!devUser8.authorities.contains(pmRole)) {UserRole.create devUser8, pmRole}

			def devUser9 = User.findByUsername('idunn') ?: new User(username: 'idunn',password: 'EebeL7ae',email: 'ivan.dunn@hmhco.com').save(failOnError: true)
			if (!devUser9.authorities.contains(pmRole)) {UserRole.create devUser9, pmRole}

			def devUser10 = User.findByUsername('terryi') ?: new User(username: 'terryi',password: 'os5tkyH',email: 'ivan.terry@hmhco.com').save(failOnError: true)
			if (!devUser10.authorities.contains(pmRole)) {UserRole.create devUser10, pmRole}

			def devUser11 = User.findByUsername('gfay') ?: new User(username: 'gfay',password: '8QKcC3',email: 'geoff.fay@hmhco.com').save(failOnError: true)
			if (!devUser11.authorities.contains(pmRole)) {UserRole.create devUser11, pmRole}

			def devUser12 = User.findByUsername('cflanagan') ?: new User(username: 'cflanagan',password: '8mheXk',email: 'ciara.flanagan@hmhco.com').save(failOnError: true)
			if (!devUser12.authorities.contains(pmRole)) {UserRole.create devUser12, pmRole}

			def devUser13 = User.findByUsername('stuartb') ?: new User(username: 'stuartb',password: 'GHb4hg5',email: 'stuart.byrne@hmhco.com').save(failOnError: true)
			if (!devUser13.authorities.contains(pmRole)) {UserRole.create devUser13, pmRole}

			def devUser14 = User.findByUsername('bquinn') ?: new User(username: 'bquinn',password: 'qued3aFu',email: 'brian.quinn@hmhco.com').save(failOnError: true)
			if (!devUser14.authorities.contains(pmRole)) {UserRole.create devUser14, pmRole}


			// QA Users
			def qaUser = User.findByUsername('QA') ?: new User(	username: 'QA',	password: 'spr1ngt1me').save(failOnError: true)
			if (!qaUser.authorities.contains(qaRole)) {	UserRole.create qaUser, qaRole}

			def qaUser2 = User.findByUsername('RE-prodrev') ?: new User(username: 'RE-prodrev',password: 'N11tRelease04',email: 'release.engineering@hmhco.com').save(failOnError: true)
			if (!qaUser2.authorities.contains(qaRole)) {UserRole.create qaUser2, qaRole}

			// Production Users
			def prodUser = User.findByUsername('Prod') ?: new User(	username: 'Prod',password: 'spr1ngt1me').save(failOnError: true)
			if (!prodUser.authorities.contains(prodRole)) {	UserRole.create prodUser, prodRole}

			def prodUser2 = User.findByUsername('dpant') ?: new User(username: 'dpant',password: 'hHbBM7iq',email: 'deepak.pant@niit.com').save(failOnError: true)
			if (!prodUser2.authorities.contains(prodRole)) {UserRole.create prodUser2, prodRole}

			def prodUser3 = User.findByUsername('pkumar1') ?: new User(username: 'pkumar1',password: 'M0MLWf1e',email: 'prabhash.kumar@niit.com').save(failOnError: true)
			if (!prodUser3.authorities.contains(prodRole)) {UserRole.create prodUser3, prodRole}

			def prodUser4 = User.findByUsername('rabhinav') ?: new User(username: 'rabhinav',password: 'rahN2oh',email: 'abhinav.rai@niit.com').save(failOnError: true)
			if (!prodUser4.authorities.contains(prodRole)) {UserRole.create prodUser4, prodRole}

			def prodUser5 = User.findByUsername('parminders') ?: new User(username: 'parminders',password: 'uayouch',email: 'parminder.singh@niit.com').save(failOnError: true)
			if (!prodUser5.authorities.contains(prodRole)) {UserRole.create prodUser5, prodRole}

			def prodUser6 = User.findByUsername('nnandanan') ?: new User(username: 'nnandanan',password: '7ZQ6jo8q',email: 'nisha.nandanan@niit.com').save(failOnError: true)
			if (!prodUser6.authorities.contains(prodRole)) {UserRole.create prodUser6, prodRole}

			def prodUser7 = User.findByUsername('msaraswat') ?: new User(username: 'msaraswat',password: 'kS74TgwZ',email: 'madhup.saraswat@niit.com').save(failOnError: true)
			if (!prodUser7.authorities.contains(prodRole)) {UserRole.create prodUser7, prodRole}

			def prodUser8 = User.findByUsername('ogallen') ?: new User(username: 'ogallen',password: 'Shae7Shu',email: 'oran.gallen@hmhco.com').save(failOnError: true)
			if (!prodUser8.authorities.contains(prodRole)) {UserRole.create prodUser8, prodRole}

			ContentType ct1 = new ContentType(contentId:1, name:'Program').save(failOnError:true)
			ContentType ct2 = new ContentType(contentId:2, name:'Bundle').save(failOnError:true)
			ContentType ct3 = new ContentType(contentId:3, name:'SecureProgram').save(failOnError:true)
			ContentType ct4 = new ContentType(contentId:4, name:'CommerceObject').save(failOnError:true)
			//ContentType ct5 = new ContentType(contentId:5, name:'ProgramXML').save(failOnError:true)

			EnvironmentGrp.withTransaction{
				def eg1 = new EnvironmentGrp(groupname:'Content').save(failOnError:true)
				def eg2 = new EnvironmentGrp(groupname:'Integration').save(failOnError:true)
				def eg3 = new EnvironmentGrp(groupname:'Platform').save(failOnError:true)
			}

			Environment.withTransaction{
				def eg1 = EnvironmentGrp.where{id==1}.get()
				def eg2 = EnvironmentGrp.where{id==2}.get()
				def eg3 = EnvironmentGrp.where{id==3}.get()
				def r2 = Role.where{id==2}.get()
				def r3 = Role.where{id==3}.get()
				def r4 = Role.where{id==4}.get()


				def certReview = Environment.findByName('Development / Cert_Review') ?: new Environment(groups:eg1,name: 'Development / Cert_Review', role:r2,priorityOrder: '0', url: "http://support-review-cert.hrw.com",bambooPlan1: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-PLACEHOLDER",bambooPlan2: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-PLACEHOLDER").save(failOnError: true)
				def qa = Environment.findByName('QA / Prod_Review') ?: new Environment(groups:eg1,name: 'QA / Prod_Review',role:r3,priorityOrder: '1', url: "http://support-review.hrw.com",bambooPlan1: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-RCRTPRCRM",bambooPlan2: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-RPRUPF").save(failOnError: true)
				def prod = Environment.findByName('Production / Prod') ?: new Environment(groups:eg1,name: 'Production / Prod',role:r4, priorityOrder: '2',  url: "http://support.hrw.com",bambooPlan1: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-RELEASEMDS",bambooPlan2: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-RPUPF" ).save(failOnError: true)
				def Cert = new Environment(groups:eg3,name:'Certification / Cert', priority_order:3,role:r2, url:'http://support-cert.hrw.com',bambooPlan1: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-PLACEHOLDER",bambooPlan2: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-PLACEHOLDER").save(failOnError:true)
				def Int = new Environment(groups:eg2,name:'Integration / Int', priority_order:1,role:r2, url:'http://support-test.hrw.com',bambooPlan1: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-PLACEHOLDER",bambooPlan2: "http://dubv-engbam05.dubeng.local:8085/rest/api/latest/queue/MDS-PLACEHOLDER").save(failOnError:true)
			}


			Program.withTransaction{

				def p1 = new Program(name:'visualmath', state:'FL', discipline:'math',userUpdatingProgram:'Dev', contentType:ct1).save(failOnError:true)
				def p2 = new Program(name:'hmhcollections2017', state:'NA', discipline:'language_arts',userUpdatingProgram:'Dev', contentType:ct1).save(failOnError:true)
				def p3 = new Program(name:'hmhcollections2017', state:'CA', discipline:'language_arts',userUpdatingProgram:'Dev', contentType:ct1).save(failOnError:true)
				def p4 = new Program(name:'visualmathNY', state:'NY', discipline:'language_arts',userUpdatingProgram:'Dev', contentType:ct1).save(failOnError:true)
			}


			CommerceObject.withTransaction{

				def cob1 = new CommerceObject (objectName:'Visual Math Guided Tour', isbnNumber:'5551234567891', objectType:'DLO', objectReorderNumber:1, gradeLevel:7, pathToCoverImage:'/nsmedia/images/bc/start.png',teacherLabel:'Collections Guided Tour', teacherUrl:'/content',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob2 = new CommerceObject (objectName:'Visual Math Student eBook', isbnNumber:'5551234567892', objectType:'DLO', objectReorderNumber:3, gradeLevel:7, pathToCoverImage:'/nsmedia/images/bc/student_ebook.png',teacherLabel:'Student eBook',teacherUrl:'/content',studentLabel:'Student eBook', studentUrl:'/content',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob3 = new CommerceObject (objectName:'Visual Math Teacher eBook', isbnNumber:'5551234567893', objectType:'DLO', objectReorderNumber:2, gradeLevel:7, pathToCoverImage:'/nsmedia/images/bc/teacher_ebook.png',teacherLabel:'Teacher eBook',teacherUrl:'/content',category:'Mathematics',subject:'Math', isPremium:false, userUpdatingCO:'Dev',contentType:ct4).save(failOnError:true)
				def cob4 = new CommerceObject (objectName:'Visual Math Program Assessment', isbnNumber:'5551234567894', objectType:'DLO', objectReorderNumber:5, gradeLevel:7, pathToCoverImage:'/nsmedia/images/bc/assessments.png',teacherLabel:'Program Assessments',teacherUrl:'/content',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob5 = new CommerceObject (objectName:'Visual Math FYI', isbnNumber:'5551234567895', objectType:'DLO', objectReorderNumber:7, gradeLevel:6, pathToCoverImage:'/nsmedia/images/icons/fyi_icon_purplex2.png',teacherLabel:'FYI Site',teacherUrl:'/content',studentLabel:'FYI Site', studentUrl:'/content',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob6 = new CommerceObject (objectName:'Visual Math Teacher Resources', isbnNumber:'5551234567897', objectType:'DLO', objectReorderNumber:4, gradeLevel:7, pathToCoverImage:'/nsmedia/images/bc/teacher_resources.png',teacherLabel:'Teacher Resources',teacherUrl:'/content',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob7 = new CommerceObject (objectName:'Visual Math myWriteSmart', isbnNumber:'5551234567896', objectType:'DLO', objectReorderNumber:8, gradeLevel:7, pathToCoverImage:'/nsmedia/images/bc/mywritesmart.png',teacherLabel:'myWriteSmart',teacherUrl:'/content',category:'Mathematics',subject:'Math', isPremium:true,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob8 = new CommerceObject (objectName:'Visual Math Professional Development', isbnNumber:'5551234567898', objectType:'DLO', objectReorderNumber:7, gradeLevel:7, pathToCoverImage:'/nsmedia/images/icons/fyi_icon_pinkx2.png',teacherLabel:'Professional Development for Visual Math',teacherUrl:'/content',studentLabel:'Professional Development for Visual Math', studentUrl:'/content',category:'Mathematics',subject:'Math', isPremium:true,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
			}



			SecureProgram.withTransaction{

				def cob1 = CommerceObject.where{id==1}.get()
				def cob2 = CommerceObject.where{id==2}.get()
				def cob3 = CommerceObject.where{id==3}.get()
				def cob4 = CommerceObject.where{id==4}.get()
				def cob5 = CommerceObject.where{id==5}.get()
				def cob6 = CommerceObject.where{id==6}.get()
				def cob7 = CommerceObject.where{id==7}.get()
				def cob8 = CommerceObject.where{id==8}.get()
				def sp1 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 7 | 7Y', registrationIsbn:'0512345671',
				onlineIsbn:'9780123456781', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456781.jpg', securityWord:'algebra', includeDashboardObject:true, includeEplannerObject:false,includeNotebookObject:false,knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054b',curriculumArea:'Mathematics',essayGraderPrompts:'Middle School',userUpdatingSProgram:'Dev',language:'en-us', contentType:ct3).addToCommerceObjects(cob1).addToCommerceObjects(cob7).addToCommerceObjects(cob2).addToCommerceObjects(cob3).addToCommerceObjects(cob4).addToCommerceObjects(cob5).addToCommerceObjects(cob6).addToCommerceObjects(cob8).save(failOnError:true)
				def sp2 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 8', registrationIsbn:'0512345672',
				onlineIsbn:'9780123456791', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456791.jpg', securityWord:'teacher', includeDashboardObject:false, includeEplannerObject:true,includeNotebookObject:false,knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054c',curriculumArea:'Mathematics',essayGraderPrompts:'Middle School',userUpdatingSProgram:'Dev',language:'en-us', contentType:ct3).addToCommerceObjects(cob1).addToCommerceObjects(cob2).save(failOnError:true)
				def sp3 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 9', registrationIsbn:'054423881X',
				onlineIsbn:'9780123456792', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456792.jpg', securityWord:'reflection', includeDashboardObject:true, includeEplannerObject:true,includeNotebookObject:false, knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054d',curriculumArea:'Mathematics',essayGraderPrompts:'High School',userUpdatingSProgram:'Dev',language:'en-us', contentType:ct3).addToCommerceObjects(cob3).save(failOnError:true)
				def sp4 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 10', registrationIsbn:'054423882X',
				onlineIsbn:'9780123456793', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456793.jpg', securityWord:'equal', includeDashboardObject:false, includeEplannerObject:false,includeNotebookObject:false, knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054e',curriculumArea:'Mathematics',essayGraderPrompts:'High School',userUpdatingSProgram:'Dev',language:'en-us', contentType:ct3).addToCommerceObjects(cob4).save(failOnError:true)
				def sp5 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 11', registrationIsbn:'054423883X',
				onlineIsbn:'9780123456794', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456794.jpg', securityWord:'multiply', includeDashboardObject:true, includeEplannerObject:true,includeNotebookObject:false, knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054f',curriculumArea:'Mathematics',essayGraderPrompts:'High School',userUpdatingSProgram:'Dev',language:'en-us', contentType:ct3).addToCommerceObjects(cob5).save(failOnError:true)
				def sp6 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 12', registrationIsbn:'054423884X',
				onlineIsbn:'9780123456795', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456795.jpg', securityWord:'essay', includeDashboardObject:true, includeEplannerObject:true,includeNotebookObject:false, knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756055b',curriculumArea:'Mathematics',essayGraderPrompts:'High School',userUpdatingSProgram:'Dev',language:'en-us', contentType:ct3).addToCommerceObjects(cob6).addToCommerceObjects(cob7).save(failOnError:true)
				def sp7 = new SecureProgram (productName:'Visual Math: NY, Teacher Edition, Grade 7', registrationIsbn:'0512345673',
				onlineIsbn:'9780123456781', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456781.jpg', securityWord:'algebra', includeDashboardObject:true, includeEplannerObject:false,includeNotebookObject:false,knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054b',curriculumArea:'Mathematics',essayGraderPrompts:'Middle School',userUpdatingSProgram:'Dev',language:'en-us', contentType:ct3).addToCommerceObjects(cob1).addToCommerceObjects(cob7).addToCommerceObjects(cob2).addToCommerceObjects(cob3).addToCommerceObjects(cob4).addToCommerceObjects(cob5).addToCommerceObjects(cob6).addToCommerceObjects(cob8).save(failOnError:true)

			}


			Bundle.withTransaction{

				def p1 = Program.where{id==1}.get()
				def p4 = Program.where{id==4}.get()


				def sp1 = SecureProgram.where{id==1}.get()
				def sp2 = SecureProgram.where{id==2}.get()
				def sp3 = SecureProgram.where{id==3}.get()
				def sp4 = SecureProgram.where{id==4}.get()
				def sp5 = SecureProgram.where{id==5}.get()
				def sp6 = SecureProgram.where{id==6}.get()
				def sp7 = SecureProgram.where{id==7}.get()

				def b1 = new Bundle(program:p1, isbn:'9780123456781', title:'Visual Math ete, Grade 7, 2016, 6Y', duration:'6-Year', includePremiumCommerceObjects:false,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				def b2 = new Bundle(program:p1, isbn:'9780123456782', title:'Visual Math ete, Grade 7 Premium, 2016, 6Y', duration:'6-Year', includePremiumCommerceObjects:true,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				def b3 = new Bundle(program:p1, isbn:'9780123456783', title:'Visual Math ete, Grade 7, 2016, 1Y', duration:'1-Year', includePremiumCommerceObjects:false,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				def b4 = new Bundle(program:p1, isbn:'9780123456784', title:'Visual Math ete, Grade 7 Premium, 2016, 1Y', duration:'1-Year', includePremiumCommerceObjects:true,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				def b5 = new Bundle(program:p1, isbn:'9780123456791', title:'Visual Math ete Grade 8, 2016, 6Y', duration:'6-Year',  includePremiumCommerceObjects:false,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp2).save(failOnError:true)
				def b6 = new Bundle(program:p1, isbn:'9780123456792', title:'Visual Math ete, Grade 9, 2016, 6Y', duration:'6-Year', includePremiumCommerceObjects:false,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
				def b7 = new Bundle(program:p1, isbn:'9780123456793', title:'Visual Math ete, Grade 10, 2016, 6Y', duration:'6-Year', includePremiumCommerceObjects:false,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp4).save(failOnError:true)
				def b8 = new Bundle(program:p1, isbn:'9780123456794', title:'Visual Math ete, Grade 11, 2016, 6Y', duration:'6-Year', includePremiumCommerceObjects:false,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp5).save(failOnError:true)
				def b9 = new Bundle(program:p1, isbn:'9780123456795', title:'Visual Math ete, Grade 12, 2016, 6Y', duration:'6-Year', includePremiumCommerceObjects:false,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp6).save(failOnError:true)
				def b10 = new Bundle(program:p1, isbn:'9780123456796', title:'Visual Math ete, Grade 12 Premium, 2016, 6Y', duration:'6-Year', includePremiumCommerceObjects:true,userUpdatingBundle:'Dev', contentType:ct2).addToSecureProgram(sp6).save(failOnError:true)



				def b11 = new Bundle(program:p4, isbn:'9780123456760',title:'Visual Math NY, ete Grade 7, 2016, 1Y',duration:'1-Year',includePremiumCommerceObjects:false,userUpdatingBundle:'Dev',contentType:ct2).addToSecureProgram(sp7).save(failOnError:true)
				def b12 = new Bundle(program:p4, isbn:'9780123456761',title:'Visual Math NY, ete, Grade 7, 2016, 2Y',duration:'2-Year',includePremiumCommerceObjects:false,userUpdatingBundle:'Dev',contentType:ct2).addToSecureProgram(sp7).save(failOnError:true)

				/*def b13 = new Bundle(program:p1, isbn:'9780123456762',title:'Visual Math load Testing, Grade 7, 2016, 3Y',duration:'3-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				 def b14 = new Bundle(program:p1, isbn:'9780123456763',title:'Visual Math load Testing, Grade 7, 2016, 4Y',duration:'4-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				 def b15 = new Bundle(program:p1, isbn:'9780123456764',title:'Visual Math load Testing, Grade 7, 2016, 5Y',duration:'5-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				 def b16 = new Bundle(program:p1, isbn:'9780123456765',title:'Visual Math load Testing, Grade 7, 2016, 6Y',duration:'6-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				 def b17 = new Bundle(program:p1, isbn:'9780123456766',title:'Visual Math load Testing, Grade 7, 2016, 7Y',duration:'7-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				 def b18 = new Bundle(program:p1, isbn:'9780123456767',title:'Visual Math load Testing, Grade 7, 2016, 8Y',duration:'8-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				 def b19 = new Bundle(program:p1, isbn:'9780123456768',title:'Visual Math load Testing, Grade 8, 2016, 1Y',duration:'1-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp2).save(failOnError:true)
				 def b20 = new Bundle(program:p1, isbn:'9780123456769',title:'Visual Math load Testing, Grade 8, 2016, 2Y',duration:'2-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp2).save(failOnError:true)
				 def b21 = new Bundle(program:p1, isbn:'9780123456770',title:'Visual Math load Testing2, Grade 9, 2016, 1Y',duration:'1-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
				 def b22 = new Bundle(program:p1, isbn:'9780123456771',title:'Visual Math load Testing2, Grade 9, 2016, 2Y',duration:'2-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
				 def b23 = new Bundle(program:p1, isbn:'9780123456772',title:'Visual Math load Testing2, Grade 9, 2016, 3Y',duration:'3-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
				 def b24 = new Bundle(program:p1, isbn:'9780123456773',title:'Visual Math load Testing2, Grade 9, 2016, 4Y',duration:'4-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
				 def b25 = new Bundle(program:p1, isbn:'9780123456774',title:'Visual Math load Testing2, Grade 9, 2016, 5Y',duration:'5-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
				 def b26 = new Bundle(program:p1, isbn:'9780123456775',title:'Visual Math load Testing2, Grade 9, 2016, 6Y',duration:'6-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
				 def b27 = new Bundle(program:p1, isbn:'9780123456776',title:'Visual Math load Testing2, Grade 9, 2016, 7Y',duration:'7-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
				 def b28 = new Bundle(program:p1, isbn:'9780123456777',title:'Visual Math load Testing2, Grade 9, 2016, 8Y',duration:'8-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
				 def b29 = new Bundle(program:p1, isbn:'9780123456778',title:'Visual Math load Testing2, Grade 10, 2016, 1Y',duration:'1-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp4).save(failOnError:true)
				 def b30 = new Bundle(program:p1, isbn:'9780123456779',title:'Visual Math load Testing2, Grade 10, 2016, 2Y',duration:'2-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp4).save(failOnError:true)
				 */
			}


		} // end if



		includeProgramXML()



	}


	def destroy = {}




	/**
	 * Include ProgramXML if it does not exist
	 * @return
	 */
	def includeProgramXML(){

		if(ProgramXML.where{}.list().isEmpty()){

			ContentType ct5 = new ContentType(contentId:5, name:'ProgramXML').save(failOnError:true)

			ProgramXML.withTransaction{

				def p1xml = new ProgramXML(title:'TX High School Science',buid:'SCIENCE_HSSSCIENCE2014',filename:'Tx_HighSchoolScience.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p2xml = new ProgramXML(title:'TX Go Math! 2015',buid:'MATH_HMHTXNA2015',filename:'Texas_Math_2015_G6_8.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p3xml = new ProgramXML(title:'TX High School Math',buid:'MATH_HMHTXHSM2016',filename:'Texas_HSM_2016.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p4xml = new ProgramXML(title:'Social Studies Texas 2016',buid:'SCIENCE_HSSSCIENCE2014XXXX',filename:'SocialStudies_US_History.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p5xml = new ProgramXML(title:'NL Go Math! 2014',buid:'MATH_HMHMXNA2013',filename:'National_Math_2014.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p6xml = new ProgramXML(title:'High School Integrated Math',buid:'MATH_HMHNAINTHSM2015',filename:'National_INT_HSM_2015.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p7xml = new ProgramXML(title:'High School Math',buid:'MATH_HMHNAAGAHSM2015',filename:'National_AGA_HSM_2015.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)


				def p8xml = new ProgramXML(title:'CA Go Math! 2015',buid:'MATH_HMHCALMATH2015',filename:'California_Math_2015_G6_12.xml ',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p9xml = new ProgramXML(title:'CA High School Integrated Math',buid:'MATH_HMHCAINTHSM2015',filename:'California_INT_HSM_2015.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p10xml = new ProgramXML(title:'CA High School Math',buid:'MATH_HMHCAAGAHSM2015',filename:'California_AGA_HSM_2015.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)

				def p11xml = new ProgramXML(title:'Visual Math 2017',buid:'VISUALMATH2017',filename:'hmof_program_visualMath.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)

				def p12xml = new ProgramXML(title:'HMH Collections',buid:'HMHCOLLECTIONS_SAMPLE_LP',filename:'hsp_program_HMHCollections.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p13xml = new ProgramXML(title:'Escalate English NA 2017',buid:'NA_Escalate_English_2017',filename:'hsp_program_READING_HSPESCALATENA2017.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p14xml = new ProgramXML(title:'Escalate English 2017',buid:'CA_Escalate_English_2017',filename:'hsp_program_READING_HSPESCALATECA2017.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p15xml = new ProgramXML(title:'Collections 2017',buid:'HMHCOLLECTIONS_2017_NA',filename:'hsp_program_HMHCollections_2017_NA.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)
				def p16xml = new ProgramXML(title:'CA Collections 2017',buid:'HMHCOLLECTIONS_2017_CA',filename:'hsp_program_HMHCollections_2017_CA.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)

				def p17xml = new ProgramXML(title:'Family Engagement NA 2017',buid:'HMH_FE_2017_NA',filename:'hsp_program_HMH_FE_2017_NA.xml',userUpdatingProgramXML:'Dev', contentType:ct5).save(failOnError:true)


				// Associate Jobs
				def job1 = new Job(jobNumber:435,contentTypeId:5,contentId:p1xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo1 = new Promotion (status: "Success", job: job1, jobNumber: job1.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo1b = new Promotion (status: "Success", job: job1, jobNumber: job1.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo1c = new Promotion (status: "Success", job: job1, jobNumber: job1.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job2 = new Job(jobNumber:436,contentTypeId:5,contentId:p2xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo2 = new Promotion (status: "Success", job: job2, jobNumber: job2.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo2b = new Promotion (status: "Success", job: job2, jobNumber: job2.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo2c = new Promotion (status: "Success", job: job2, jobNumber: job2.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job3 = new Job(jobNumber:437,contentTypeId:5,contentId:p3xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo3 = new Promotion (status: "Success", job: job3, jobNumber: job3.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo3b = new Promotion (status: "Success", job: job3, jobNumber: job3.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo3c = new Promotion (status: "Success", job: job3, jobNumber: job3.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job4 = new Job(jobNumber:438,contentTypeId:5,contentId:p4xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo4 = new Promotion (status: "Success", job: job4, jobNumber: job4.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo4b = new Promotion (status: "Success", job: job4, jobNumber: job4.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo4c = new Promotion (status: "Success", job: job4, jobNumber: job4.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job5 = new Job(jobNumber:439,contentTypeId:5,contentId:p5xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo5 = new Promotion (status: "Success", job: job4, jobNumber: job5.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo5b = new Promotion (status: "Success", job: job4, jobNumber: job5.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo5c = new Promotion (status: "Success", job: job4, jobNumber: job5.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job6 = new Job(jobNumber:440,contentTypeId:5,contentId:p6xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo6 = new Promotion (status: "Success", job: job4, jobNumber: job6.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo6b = new Promotion (status: "Success", job: job4, jobNumber: job6.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo6c = new Promotion (status: "Success", job: job4, jobNumber: job6.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job7 = new Job(jobNumber:441,contentTypeId:5,contentId:p7xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo7 = new Promotion (status: "Success", job: job4, jobNumber: job7.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo7b = new Promotion (status: "Success", job: job4, jobNumber: job7.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo7c = new Promotion (status: "Success", job: job4, jobNumber: job7.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job8 = new Job(jobNumber:442,contentTypeId:5,contentId:p8xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo8 = new Promotion (status: "Success", job: job4, jobNumber: job8.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo8b = new Promotion (status: "Success", job: job4, jobNumber: job8.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo8c = new Promotion (status: "Success", job: job4, jobNumber: job8.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job9 = new Job(jobNumber:443,contentTypeId:5,contentId:p9xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo9 = new Promotion (status: "Success", job: job9, jobNumber: job9.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo9b = new Promotion (status: "Success", job: job9, jobNumber: job9.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo9c = new Promotion (status: "Success", job: job9, jobNumber: job9.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job10 = new Job(jobNumber:444,contentTypeId:5,contentId:p10xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo10 = new Promotion (status: "Success", job: job10, jobNumber: job10.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo10b = new Promotion (status: "Success", job: job10, jobNumber: job10.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo10c = new Promotion (status: "Success", job: job10, jobNumber: job10.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				
				// TEST PROGRAM
				def job11 = new Job(jobNumber:445,contentTypeId:5,contentId:p11xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo11 = new Promotion (status: "Success", job: job11, jobNumber: job11.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
								
				def job12 = new Job(jobNumber:446,contentTypeId:5,contentId:p12xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo12 = new Promotion (status: "Success", job: job12, jobNumber: job12.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo12b = new Promotion (status: "Success", job: job12, jobNumber: job12.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo12c = new Promotion (status: "Success", job: job12, jobNumber: job12.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job13 = new Job(jobNumber:447,contentTypeId:5,contentId:p13xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo13 = new Promotion (status: "Success", job: job13, jobNumber: job13.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo13b = new Promotion (status: "Success", job: job13, jobNumber: job13.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo13c = new Promotion (status: "Success", job: job13, jobNumber: job13.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job14 = new Job(jobNumber:448,contentTypeId:5,contentId:p14xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo14 = new Promotion (status: "Success", job: job14, jobNumber: job14.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo14b = new Promotion (status: "Success", job: job14, jobNumber: job14.getJobNumber(), user: 1, environments: 2 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				def promo14c = new Promotion (status: "Success", job: job14, jobNumber: job14.getJobNumber(), user: 1, environments: 3 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job15 = new Job(jobNumber:449,contentTypeId:5,contentId:p15xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo15 = new Promotion (status: "Success", job: job15, jobNumber: job15.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)

				def job16 = new Job(jobNumber:450,contentTypeId:5,contentId:p16xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo16 = new Promotion (status: "Success", job: job16, jobNumber: job16.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)
				
				def job17 = new Job(jobNumber:451,contentTypeId:5,contentId:p17xml.id,revision:2506,user: 1).save(failOnError:true)
				def promo17 = new Promotion (status: "Success", job: job17, jobNumber: job17.getJobNumber(), user: 1, environments: 1 ,smartDeploy:false,jira_id:"").save(failOnError:true)



			}
		}
	}
}
