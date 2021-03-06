package hmof

import java.util.Date;

import org.hibernate.envers.Audited;

@Audited
class Sap {

	String isbn
	String status
	String internalDescription
	String materialGroup
	String eGoodsIndicator
	//Date dateCreated
	//Date lastUpdated	
	
	static belongsTo	= [bundle:Bundle]
    static constraints = {
		status(blank: true,nullable:true)
		internalDescription(blank: true,nullable:true)
		materialGroup(blank: true,nullable:true)
		eGoodsIndicator(blank: true,nullable:true)
	
		bundle()
    }
}
