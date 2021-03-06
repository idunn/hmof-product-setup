package hmof

import java.util.Date
import java.util.SortedSet;

import org.hibernate.envers.Audited

@Audited
class Bundle implements Comparable {

	String isbn
	String title
	String duration
	Date dateCreated
	Date lastUpdated
	Boolean includePremiumCommerceObjects=false
	String userUpdatingBundle
	ContentType contentType
	static belongsTo = [program:Program]
	SortedSet secureProgram
	static hasMany = [secureProgram:SecureProgram]
	static hasOne = [sap: Sap]
	static searchable = {
		secureProgram component: true

	}

	static mapping = {
		sort id: "desc"
	}
	
	int compareTo(obj) {
		title.compareTo(obj.title)
	}


	static constraints = {
		isbn (blank: false, matches:/([0-9]{13})$/,unique: true)
		title (blank: false,maxSize:125,matches:/^[A-Za-z0-9\/\s~,.<>;&#%\xA9^*!:\/\[\]\|{}()=_+-\.\-]+$/)
		includePremiumCommerceObjects(nullable: true)
		duration (inList: ["1-Year", "2-Year", "3-Year", "4-Year", "5-Year", "6-Year", "7-Year", "8-Year"], nullable:false)
		program()
		sap(nullable:true)
	}

	String toString(){
		"${isbn}: ${title}"
	}
}