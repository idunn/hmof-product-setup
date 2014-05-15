package hmof

class Bundle {

	String isbn
	String title
	String duration

	static belongsTo = [program:Program]
	static hasMany = [secureProgram:SecureProgram, commerceObjects:Cobj]	

	static constraints = {

		isbn blank: false, unique: true
		title blank: false
		duration (inList: ["1-Year", "3-Year", "5-Year", "6-Year", "8-Year"], nullable:true)
	}

	String toString(){
		"${isbn}: ${title}"
	}
}