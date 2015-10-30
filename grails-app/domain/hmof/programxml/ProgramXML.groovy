package hmof.programxml

import org.hibernate.envers.Audited;

import hmof.*
@Audited
class ProgramXML {
    
	String title
	String buid
	String language
	String filename
	static hasMany = [secureProgram:SecureProgram]
	ContentType contentType
    static constraints = {
		title (nullable:false,blank: false)
		buid (nullable:false,blank: false,unique: true)
		language (inList: ["en_US","en_AS","en_AU","en_BE","en_BZ","en_BW","en_CA","en_GU","en_HK","en_IN","en_IE","en_JM","en_MT","en_MH","en_MU","en_NA","en_NZ","en_MP","en_PK","en_PH","en_SG","en_ZA","en_TT","en_UM","en_VI","en_GB","en_ZW"], nullable:false)
		filename(nullable:false,blank: false,unique: true)
	 }
}
