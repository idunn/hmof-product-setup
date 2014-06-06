package hmof

class Bundle {

	String isbn
	String title
	String duration
	Date dateCreated
	Date lastUpdated
	Date devEnvironment
  

	static belongsTo = [program:Program]
	static hasMany = [secureProgram:SecureProgram, commerceObjects:CommerceObject]	

	static constraints = {

		isbn blank: false, unique: true
		title blank: false
		duration (inList: ["1-Year", "2-Year", "3-Year", "4-Year", "5-Year", "6-Year", "7-Year", "8-Year"], nullable:true)
		devEnvironment (nullable:true)
	}

	String toString(){
		"${isbn}: ${title}"
	}
}