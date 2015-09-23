package hmof.deploy

/**
 * EnvironmentGrp
 * A domain class describes the data object and it's mapping to the database
 */
class EnvironmentGrp {
	 
	  String groupname

	  static hasMany = [environments:Environment]
	  
	  
    static	mapping = {
    }
    
	static	constraints = {
		groupname(blank: false, unique: true,maxSize:100)
		environments()
    }
	String toString(){
		"${groupname}"
	}
	
}
