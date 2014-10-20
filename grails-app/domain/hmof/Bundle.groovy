package hmof

import java.util.Date

import org.hibernate.envers.Audited

@Audited
class Bundle {
	//static searchable = true
	String isbn
	String title
	String duration
	Date dateCreated
	Date lastUpdated
	Boolean includePremiumCommerceObjects
	ContentType contentType
	//Program program
	static belongsTo = [program:Program]
	static hasMany = [secureProgram:SecureProgram]
	static searchable = {
		secureProgram component: true
		   
   }
	static constraints = {

		isbn (blank: false, unique: true)
		title (blank: false)
		includePremiumCommerceObjects(nullable: true)
		duration (inList: ["1-Year", "2-Year", "3-Year", "4-Year", "5-Year", "6-Year", "7-Year", "8-Year"], nullable:true)
		program()
		}

	String toString(){
		"${isbn}: ${title}"
	}
}