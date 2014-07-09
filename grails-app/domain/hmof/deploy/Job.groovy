package hmof.deploy
import hmof.security.*
/**
 * Job
 * A domain class describes the data object and it's mapping to the database
 */
class Job {

	/* Default (injected) attributes of GORM */
	//	Long	id
	//	Long	version

	/* Automatic timestamping of GORM */
	//	Date	dateCreated
	//	Date	lastUpdated

	static	belongsTo	= [user: User]	
	static	hasOne	= Program2 // no back-reference
	static	hasMany		= [promotions: JobPromotion]
		
	//	static	mappedBy	= []	// specifies which property should be used in a mapping



	static	mapping = {
	}

	static	constraints = {
	}

	/*
	 * Methods of the Domain Class
	 */
	//	@Override	// Override toString for a nicer / more descriptive UI
	//	public String toString() {
	//		return "${name}";
	//	}
}
