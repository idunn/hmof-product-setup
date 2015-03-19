package hmof

import java.util.Date;

class Sap {

	String isbn
	String status
	Date dateCreated
	Date lastUpdated
	static belongsTo	= [bundle:Bundle]
    static constraints = {
		status(blank: true,nullable:true)
		bundle()
    }
}
