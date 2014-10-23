package hmof

import java.util.Date

import org.hibernate.envers.Audited

@Audited
class Bundle {
	
	String isbn
	String title
	String duration
	Date dateCreated
	Date lastUpdated
	Boolean includePremiumCommerceObjects
	ContentType contentType
	static belongsTo = [program:Program]
	static hasMany = [secureProgram:SecureProgram]
	static searchable = {
		secureProgram component: true

	}

	static mapping = {

		sort id: "desc"
	}
	static constraints = {

		isbn (blank: false, matches:/([0-9]{13})$/,unique: true)
		title (blank: false)
		includePremiumCommerceObjects(nullable: true)
		duration (inList: ["1-Year", "2-Year", "3-Year", "4-Year", "5-Year", "6-Year", "7-Year", "8-Year"], nullable:true)
		program()
	}

	String toString(){
		"${isbn}: ${title}"
	}
}