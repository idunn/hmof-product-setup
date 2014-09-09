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

		def cert = Environment.findByName('Dev') ?: new Environment(name: 'Dev', priorityOrder: '0', url: "http://dev.com").save(failOnError: true)
		def qa = Environment.findByName('QA') ?: new Environment(name: 'QA',priorityOrder: '1', url: "http://qa.com").save(failOnError: true)
		def prod = Environment.findByName('Production') ?: new Environment(name: 'Production', priorityOrder: '2',  url: "http://prod.com" ).save(failOnError: true)

		ContentType ct1 = new ContentType(contentId:1, name:'Program').save(failOnError:true)
		ContentType ct2 = new ContentType(contentId:2, name:'Bundle').save(failOnError:true)
		ContentType ct3 = new ContentType(contentId:3, name:'SecureProgram').save(failOnError:true)
		ContentType ct4 = new ContentType(contentId:4, name:'CommerceObject').save(failOnError:true)



		Program.withTransaction{

			def p1 = new Program(name:'visualmath', discipline:'math', contentType:ct1).save(failOnError:true)
			def p2 = new Program(name:'hmhcollections2016', discipline:'language_arts', contentType:ct1).save(failOnError:true)
		}


		CommerceObject.withTransaction{

			def cob1 = new CommerceObject (objectName:'Math Learning Objects 2015', isbn:'5551234567891', objectType:'DLO', objectReorderNumber:2, gradeLevel:7, pathToCoverImage:'/nsmedia/bc/image1.jpg', contentType:ct4).save(failOnError:true)
			def cob2 = new CommerceObject (objectName:'Math calculator', isbn:'5551234567892', objectType:'DLO', objectReorderNumber:3, gradeLevel:8, pathToCoverImage:'/nsmedia/bc/image2.jpg', contentType:ct4).save(failOnError:true)
			def cob3 = new CommerceObject (objectName:'Math Professional Development Site', isbn:'5551234567893', objectType:'Other', objectReorderNumber:2, gradeLevel:8, pathToCoverImage:'/nsmedia/bc/image3.jpg', contentType:ct4).save(failOnError:true)
			def cob4 = new CommerceObject (objectName:'Math Learning Objects 2 2015', isbn:'5551234567894', objectType:'DLO', objectReorderNumber:2, gradeLevel:7, pathToCoverImage:'/nsmedia/bc/image4.jpg', contentType:ct4).save(failOnError:true)
			def cob5 = new CommerceObject (objectName:'Math Learning Objects 3 2015', isbn:'5551234567895', objectType:'DLO', objectReorderNumber:2, gradeLevel:8, pathToCoverImage:'/nsmedia/bc/image5.jpg', contentType:ct4).save(failOnError:true)
			def cob6 = new CommerceObject (objectName:'Math Learning Objects 4 2015', isbn:'5551234567897', objectType:'DLO', objectReorderNumber:4, gradeLevel:9, pathToCoverImage:'/nsmedia/bc/image7.jpg', contentType:ct4).save(failOnError:true)
			def cob7 = new CommerceObject (objectName:'Math Premium Tab 2015', isbn:'5551234567896', objectType:'DLO', objectReorderNumber:2, gradeLevel:8, pathToCoverImage:'/nsmedia/bc/image5.jpg', contentType:ct4).save(failOnError:true)
		}



		SecureProgram.withTransaction{

			def cob1 = CommerceObject.where{id==1}.get()
			def cob2 = CommerceObject.where{id==2}.get()

			def sp1 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 7', registrationIsbn:'0512345671',
			onlineIsbn:'9780123456781', copyright:2016, securityWord:'algebra', includeDashboardObject:true, includeEplannerObject:true, contentType:ct3).addToCommerceObjects(cob1).save(failOnError:true)
			def sp2 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 8', registrationIsbn:'0512345672',
			onlineIsbn:'9780123456791', copyright:2016, securityWord:'teacher', includeDashboardObject:true, includeEplannerObject:true, contentType:ct3).addToCommerceObjects(cob1).addToCommerceObjects(cob2).save(failOnError:true)
			def sp3 = new SecureProgram (productName:'Visual Math: Advanced Algebra, Grade 8', registrationIsbn:'054423881X',
			onlineIsbn:'9780123456999', copyright:2016, securityWord:'reflection', contentType:ct3).save(failOnError:true)
			def sp4 = new SecureProgram (productName:'Visual Math: Geometry, Grade 8', registrationIsbn:'054423882X',
			onlineIsbn:'9780123456999', copyright:2016, securityWord:'equal', contentType:ct3).save(failOnError:true)
		}


		Bundle.withTransaction{

			def p1 = Program.where{id==1}.get()
			def sp1 = SecureProgram.where{id==1}.get()
			def sp2 = SecureProgram.where{id==2}.get()
			def sp3 = SecureProgram.where{id==3}.get()
			def sp4 = SecureProgram.where{id==4}.get()

			def b1 = new Bundle(program:p1, isbn:'9780123456781', title:'Visual Math ete, Grade 7, 2016, 6Y', duration:'6-Year',contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
			def b2 = new Bundle(program:p1, isbn:'9780123456782', title:'Visual Math ete, Grade 7 Premium, 2016, 6Y', duration:'6-Year',contentType:ct2).addToSecureProgram(sp1).save(failOnError:true)
			def b3 = new Bundle(program:p1, isbn:'9780123456783', title:'Visual Math ete, Grade 7, 2016, 5Y', duration:'5-Year',contentType:ct2).addToSecureProgram(sp1).addToSecureProgram(sp2).save(failOnError:true)
			def b4 = new Bundle(program:p1, isbn:'9780123456784', title:'Visual Math ete, Grade 7, 2016, 3Y', duration:'3-Year',contentType:ct2).addToSecureProgram(sp3).save(failOnError:true)
			def b5 = new Bundle(program:p1, isbn:'9780123456791', title:'Visual Math ete Grade 8, 2016, 6Y', duration:'6-Year',contentType:ct2).addToSecureProgram(new SecureProgram(productName:'Visual Math: Mathematics 2',
			registrationIsbn:'0512349999',onlineIsbn:'9780123456791', copyright:2017,contentType:ct3)).addToSecureProgram(sp4).save(failOnError:true)
			def b6 = new Bundle(program:p1, isbn:'9780123456792', title:'Visual Math ete, Grade 9, 2016, 6Y', duration:'6-Year',contentType:ct2).save(failOnError:true)

		}


		// TODO review
		def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)

		def pmRole = Role.findByAuthority('ROLE_PM') ?: new Role(authority: 'ROLE_PM').save(failOnError: true)
		def qaRole = Role.findByAuthority('ROLE_QA') ?: new Role(authority: 'ROLE_QA').save(failOnError: true)
		def prodRole = Role.findByAuthority('ROLE_PROD') ?: new Role(authority: 'ROLE_PROD').save(failOnError: true)

		// Set admin user.
		def adminPerson = User.findByUsername('Admin') ?: new User(
				username: 'Admin',
				password: 'Admin',
				enabled: true).save(failOnError: true)

		if (!adminPerson.authorities.contains(adminRole)) {
			UserRole.create adminPerson, adminRole
		}


		// Set normal user.

		def normalPerson1 = User.findByUsername('PM') ?: new User(
				username: 'PM',
				password: 'PM',
				enabled: true).save(failOnError: true)

		if (!normalPerson1.authorities.contains(pmRole)) {
			UserRole.create normalPerson1, pmRole
		}

		def normalPerson2 = User.findByUsername('QA') ?: new User(
				username: 'QA',
				password: 'QA',
				enabled: true).save(failOnError: true)

		if (!normalPerson2.authorities.contains(qaRole)) {
			UserRole.create normalPerson2, qaRole
		}


		def normalPerson3 = User.findByUsername('Prod') ?: new User(
				username: 'Prod',
				password: 'Prod',
				enabled: true).save(failOnError: true)

		if (!normalPerson3.authorities.contains(prodRole)) {
			UserRole.create normalPerson3, prodRole
		}

		//Security


		/*def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
		 def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)
		 def devRole = Role.findByAuthority('ROLE_DEV') ?: new Role(authority: 'ROLE_DEV').save(failOnError: true)
		 def qaRole = Role.findByAuthority('ROLE_QA') ?: new Role(authority: 'ROLE_QA').save(failOnError: true)
		 def prodRole = Role.findByAuthority('ROLE_PROD') ?: new Role(authority: 'ROLE_PROD').save(failOnError: true)
		 def user1 = User.findByUsername('ivan') ?: new User(username: 'ivan', password: 'pass').save(failOnError: true)
		 if (!user1.authorities.contains(userRole)) {
		 UserRole.create user1, userRole, true
		 }
		 def user2 = User.findByUsername('admin') ?: new User(username: 'admin', enabled:true, password: 'admin').save(failOnError: true)
		 if (!user2.authorities.contains(userRole)) {
		 UserRole.create user2, userRole, true
		 }
		 if (!user2.authorities.contains(adminRole)) {
		 UserRole.create user2, adminRole, true
		 }
		 def devUser = User.findByUsername('dev') ?: new User(username: 'dev', enabled:true, password: 'dev').save(failOnError: true)
		 if (!devUser.authorities.contains(userRole)) {
		 UserRole.create devUser, userRole, true
		 }
		 if (!devUser.authorities.contains(devRole)) {
		 UserRole.create devUser, devRole, true
		 }
		 def qaUser = User.findByUsername('qa') ?: new User(username: 'qa', password: 'qa').save(failOnError: true)
		 if (!qaUser.authorities.contains(userRole)) {
		 UserRole.create qaUser, userRole, true
		 }
		 if (!qaUser.authorities.contains(qaRole)) {
		 UserRole.create qaUser, qaRole, true
		 }
		 def prodUser = User.findByUsername('prod') ?: new User(username: 'prod', password: 'prod').save(failOnError: true)
		 if (!prodUser.authorities.contains(userRole)) {
		 UserRole.create prodUser, userRole, true
		 }
		 if (!prodUser.authorities.contains(prodRole)) {
		 UserRole.create prodUser, prodRole, true
		 }*/


	}
	def destroy = {
	}
}
