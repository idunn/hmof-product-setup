import hmof.Bundle
import hmof.CommerceObject
import hmof.ContentType
import hmof.Program
import hmof.SecureProgram
import hmof.deploy.Environment
import hmof.security.Role
import hmof.security.User
import hmof.security.UserRole

class BootStrap {

	def init = { servletContext ->


		// test if data exists by looking for a Program on startup
		if(Program.where{}.list().isEmpty()){


			def cert = Environment.findByName('Dev') ?: new Environment(name: 'Dev', priorityOrder: '0', url: "http://support-review-cert.hrw.com").save(failOnError: true)
			def qa = Environment.findByName('QA') ?: new Environment(name: 'QA',priorityOrder: '1', url: "http://support-review.hrw.com").save(failOnError: true)
			def prod = Environment.findByName('Production') ?: new Environment(name: 'Production', priorityOrder: '2',  url: "http://support.hrw.com" ).save(failOnError: true)

			ContentType ct1 = new ContentType(contentId:1, name:'Program').save(failOnError:true)
			ContentType ct2 = new ContentType(contentId:2, name:'Bundle').save(failOnError:true)
			ContentType ct3 = new ContentType(contentId:3, name:'SecureProgram').save(failOnError:true)
			ContentType ct4 = new ContentType(contentId:4, name:'CommerceObject').save(failOnError:true)



			Program.withTransaction{

				def p1 = new Program(name:'visualmath', state:'FL', discipline:'math',userUpdatingProgram:'Dev', contentType:ct1).save(failOnError:true)
				def p2 = new Program(name:'hmhcollections2017', state:'NA', discipline:'language_arts',userUpdatingProgram:'Dev', contentType:ct1).save(failOnError:true)
				def p3 = new Program(name:'hmhcollections2017', state:'CA', discipline:'language_arts',userUpdatingProgram:'Dev', contentType:ct1).save(failOnError:true)
			}


			CommerceObject.withTransaction{

				def cob1 = new CommerceObject (objectName:'Visual Math Learning Objects 2015', isbnNumber:'5551234567891', objectType:'DLO', objectReorderNumber:1, gradeLevel:7, pathToCoverImage:'/nsmedia/images/bc/start.png',teacherLabel:'Collections Guided Tour',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob2 = new CommerceObject (objectName:'Visual Math calculator', isbnNumber:'5551234567892', objectType:'DLO', objectReorderNumber:1, gradeLevel:2, pathToCoverImage:'/nsmedia/images/bc/student_ebook.png',teacherLabel:'Student eBook',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob3 = new CommerceObject (objectName:'Visual Math Professional Development Site 2015', isbnNumber:'5551234567893', objectType:'Other', objectReorderNumber:2, gradeLevel:8, pathToCoverImage:'/nsmedia/images/bc/teacher_ebook.png',teacherLabel:'Teacher eBook',category:'Mathematics',subject:'Math', isPremium:false, userUpdatingCO:'Dev',contentType:ct4).save(failOnError:true)
				def cob4 = new CommerceObject (objectName:'Visual Math Learning Objects 2 2015', isbnNumber:'5551234567894', objectType:'DLO', objectReorderNumber:2, gradeLevel:7, pathToCoverImage:'/nsmedia/images/bc/assessments.png',teacherLabel:'Program Assessments',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob5 = new CommerceObject (objectName:'Visual Math Learning Objects 3 2015', isbnNumber:'5551234567895', objectType:'DLO', objectReorderNumber:4, gradeLevel:8, pathToCoverImage:'/nsmedia/images/icons/fyi_icon_purplex2.png',teacherLabel:'FYI Site',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob6 = new CommerceObject (objectName:'Visual Math Learning Objects 4 2015', isbnNumber:'5551234567897', objectType:'DLO', objectReorderNumber:5, gradeLevel:9, pathToCoverImage:'/nsmedia/images/bc/teacher_resources.png',teacherLabel:'Teacher Resources',category:'Mathematics',subject:'Math', isPremium:false,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob7 = new CommerceObject (objectName:'Visual Math Premium Object 2015', isbnNumber:'5551234567896', objectType:'DLO', objectReorderNumber:6, gradeLevel:8, pathToCoverImage:'/nsmedia/images/bc/mywritesmart.png',teacherLabel:'myWriteSmart',category:'Mathematics',subject:'Math', isPremium:true,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
				def cob8 = new CommerceObject (objectName:'Visual Math Premium Object 2016', isbnNumber:'5551234567898', objectType:'DLO', objectReorderNumber:1, gradeLevel:8, pathToCoverImage:'/nsmedia/images/icons/fyi_icon_pinkx2.png',teacherLabel:'Professional Development for Language Arts',category:'Mathematics',subject:'Math', isPremium:true,userUpdatingCO:'Dev', contentType:ct4).save(failOnError:true)
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
				onlineIsbn:'9780123456781', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456781.jpg', securityWord:'algebra', includeDashboardObject:true, includeEplannerObject:false,includeNotebookObject:false,knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054b',curriculumArea:'Mathematics',essayGraderPrompts:'Middle School',userUpdatingSProgram:'Dev', contentType:ct3).addToCommerceObjects(cob1).addToCommerceObjects(cob7).addToCommerceObjects(cob2).addToCommerceObjects(cob3).addToCommerceObjects(cob4).addToCommerceObjects(cob5).addToCommerceObjects(cob6).addToCommerceObjects(cob8).save(failOnError:true)
				def sp2 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 8', registrationIsbn:'0512345672',
				onlineIsbn:'9780123456791', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456791.jpg', securityWord:'teacher', includeDashboardObject:false, includeEplannerObject:true,includeNotebookObject:false,knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054c',curriculumArea:'Mathematics',essayGraderPrompts:'Middle School',userUpdatingSProgram:'Dev', contentType:ct3).addToCommerceObjects(cob1).addToCommerceObjects(cob2).save(failOnError:true)
				def sp3 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 9', registrationIsbn:'054423881X',
				onlineIsbn:'9780123456792', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456792.jpg', securityWord:'reflection', includeDashboardObject:true, includeEplannerObject:true,includeNotebookObject:false, knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054d',curriculumArea:'Mathematics',essayGraderPrompts:'High School',userUpdatingSProgram:'Dev', contentType:ct3).addToCommerceObjects(cob3).save(failOnError:true)
				def sp4 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 10', registrationIsbn:'054423882X',
				onlineIsbn:'9780123456793', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456793.jpg', securityWord:'equal', includeDashboardObject:false, includeEplannerObject:false,includeNotebookObject:false, knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054e',curriculumArea:'Mathematics',essayGraderPrompts:'High School',userUpdatingSProgram:'Dev', contentType:ct3).addToCommerceObjects(cob4).save(failOnError:true)
				def sp5 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 11', registrationIsbn:'054423883X',
				onlineIsbn:'9780123456794', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456794.jpg', securityWord:'multiply', includeDashboardObject:true, includeEplannerObject:true,includeNotebookObject:false, knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756054f',curriculumArea:'Mathematics',essayGraderPrompts:'High School',userUpdatingSProgram:'Dev', contentType:ct3).addToCommerceObjects(cob5).save(failOnError:true)
				def sp6 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 12', registrationIsbn:'054423884X',
				onlineIsbn:'9780123456795', copyright:2016, pathToCoverImage:'/nsmedia/bc/9780123456795.jpg', securityWord:'essay', includeDashboardObject:true, includeEplannerObject:true,includeNotebookObject:false, knowledgeGraphIdDev:'513429ef-7ad2-422d-bd21-e60ae756055b',curriculumArea:'Mathematics',essayGraderPrompts:'High School',userUpdatingSProgram:'Dev', contentType:ct3).addToCommerceObjects(cob6).addToCommerceObjects(cob7).save(failOnError:true)

			}


			Bundle.withTransaction{

				def p1 = Program.where{id==1}.get()
				def sp1 = SecureProgram.where{id==1}.get()
				def sp2 = SecureProgram.where{id==2}.get()
				def sp3 = SecureProgram.where{id==3}.get()
				def sp4 = SecureProgram.where{id==4}.get()
				def sp5 = SecureProgram.where{id==5}.get()
				def sp6 = SecureProgram.where{id==6}.get()

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

				// Load testing

				/*def b11 = new Bundle(program:p1, isbn:'9780123456760',title:'Visual Math load Testing, Grade 7, 2016, 1Y',duration:'1-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				 def b12 = new Bundle(program:p1, isbn:'9780123456761',title:'Visual Math load Testing, Grade 7, 2016, 2Y',duration:'2-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
				 def b13 = new Bundle(program:p1, isbn:'9780123456762',title:'Visual Math load Testing, Grade 7, 2016, 3Y',duration:'3-Year',includePremiumCommerceObjects:false,contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
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

		} // end if


	}
	def destroy = {
	}
}
