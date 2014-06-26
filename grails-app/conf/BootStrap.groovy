import hmof.Bundle
import hmof.CommerceObject
import hmof.Program
import hmof.SecureProgram
import hmof.security.Role
import hmof.security.User
import hmof.security.UserRole

class BootStrap {

	def init = { servletContext ->

		def p1 = new Program(name:'visualmath', discipline:'math').save()
		def p2 = new Program(name:'hmhcollections2016', discipline:'language_arts').save()

		def cob1 = new CommerceObject (objectName:'Math Learning Objects 2015', isbn:'5551234567891', objectType:'DLO', objectReorderNumber:2, gradeLevel:7, pathToCoverImage:'/nsmedia/bc/image1.jpg').save(failOnError:true)
		def cob2 = new CommerceObject (objectName:'Math calculator', isbn:'5551234567892', objectType:'DLO', objectReorderNumber:3, gradeLevel:8, pathToCoverImage:'/nsmedia/bc/image2.jpg').save(failOnError:true)
		def cob3 = new CommerceObject (objectName:'Math Professional Development Site', isbn:'5551234567893', objectType:'Other', objectReorderNumber:2, gradeLevel:8, pathToCoverImage:'/nsmedia/bc/image3.jpg').save(failOnError:true)
		def cob4 = new CommerceObject (objectName:'Math Learning Objects 2 2015', isbn:'5551234567894', objectType:'DLO', objectReorderNumber:2, gradeLevel:7, pathToCoverImage:'/nsmedia/bc/image4.jpg').save(failOnError:true)
		def cob5 = new CommerceObject (objectName:'Math Learning Objects 3 2015', isbn:'5551234567895', objectType:'DLO', objectReorderNumber:2, gradeLevel:8, pathToCoverImage:'/nsmedia/bc/image5.jpg').save(failOnError:true)
		def cob6 = new CommerceObject (objectName:'Math Learning Objects 4 2015', isbn:'5551234567897', objectType:'DLO', objectReorderNumber:4, gradeLevel:9, pathToCoverImage:'/nsmedia/bc/image7.jpg').save(failOnError:true)
		def cob7 = new CommerceObject (objectName:'Math Premium Tab 2015', isbn:'5551234567896', objectType:'DLO', objectReorderNumber:2, gradeLevel:8, pathToCoverImage:'/nsmedia/bc/image5.jpg').save(failOnError:true)


		def sp1 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 7', registrationIsbn:'0512345671',
		onlineIsbn:'9780123456781', copyright:2016, securityWord:'algebra', includeDashboardObject:true, includeEplannerObject:true).addToCommerceObjects(cob1).save(failOnError:true)
		def sp2 = new SecureProgram (productName:'Visual Math: Teacher Edition, Grade 8', registrationIsbn:'0512345672',
		onlineIsbn:'9780123456791', copyright:2016, securityWord:'teacher', includeDashboardObject:true, includeEplannerObject:true).addToCommerceObjects(cob1).addToCommerceObjects(cob7).save(failOnError:true)
		def sp3 = new SecureProgram (productName:'Visual Math: Advanced Algebra, Grade 8', registrationIsbn:'054423881X',
		onlineIsbn:'9780123456999', copyright:2016, securityWord:'reflection').save(failOnError:true)
		def sp4 = new SecureProgram (productName:'Visual Math: Geometry, Grade 8', registrationIsbn:'054423882X',
		onlineIsbn:'9780123456999', copyright:2016, securityWord:'equal').save(failOnError:true)



		def b1 = new Bundle(program:p1, isbn:'9780123456781', title:'Visual Math ete, Grade 7, 2016, 6Y', duration:'6-Year').addToSecureProgram(sp1).save(failOnError:true)
		def b2 = new Bundle(program:p1, isbn:'9780123456782', title:'Visual Math ete, Grade 7 Premium, 2016, 6Y', duration:'6-Year').addToSecureProgram(sp1).save(failOnError:true)
		def b3 = new Bundle(program:p1, isbn:'9780123456783', title:'Visual Math ete, Grade 7, 2016, 5Y', duration:'5-Year').addToSecureProgram(sp1).addToSecureProgram(sp2).save(failOnError:true)
		def b4 = new Bundle(program:p1, isbn:'9780123456784', title:'Visual Math ete, Grade 7, 2016, 3Y', duration:'3-Year').addToSecureProgram(sp3).save(failOnError:true)

		def b5 = new Bundle(program:p1, isbn:'9780123456791', title:'Visual Math ete Grade 8, 2016, 6Y', duration:'6-Year').addToSecureProgram(new SecureProgram(productName:'Visual Math: Mathematics 2',
		registrationIsbn:'0512349999',onlineIsbn:'9780123456791', copyright:2017)).addToSecureProgram(sp4).save(failOnError:true)

		def b6 = new Bundle(program:p1, isbn:'9780123456792', title:'Visual Math ete, Grade 9, 2016, 6Y', duration:'6-Year').save(failOnError:true)

		//Security
		def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
		def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)
		def devRole = Role.findByAuthority('ROLE_DEV') ?: new Role(authority: 'ROLE_DEV').save(failOnError: true)
		def qaRole = Role.findByAuthority('ROLE_QA') ?: new Role(authority: 'ROLE_QA').save(failOnError: true)
		def prodRole = Role.findByAuthority('ROLE_PROD') ?: new Role(authority: 'ROLE_PROD').save(failOnError: true)

		def user1 = User.findByUsername('ivan') ?: new User(username: 'ivan', password: 'pass').save(failOnError: true)
		if (!user1.authorities.contains(userRole)) {
			UserRole.create user1, userRole, true
		}

		def user2 = User.findByUsername('admin') ?: new User(username: 'admin', password: 'admin').save(failOnError: true)
		if (!user2.authorities.contains(userRole)) {
			UserRole.create user2, userRole, true
		}
		if (!user2.authorities.contains(adminRole)) {
			UserRole.create user2, adminRole, true
		}

		def devUser = User.findByUsername('dev') ?: new User(username: 'dev', password: 'dev').save(failOnError: true)
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
		}
		
		
		
		//assert User.count() == 2
		//assert Role.count() == 2
		//assert UserRole.count() == 3


	}
	def destroy = {
	}
}
