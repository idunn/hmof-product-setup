package hmof

import java.util.Date;

import org.hibernate.envers.Audited;

@Audited
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
