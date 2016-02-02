package hmof.programxml

import hmof.*

import java.util.Date;
import java.util.SortedSet;

import org.hibernate.envers.Audited

@Audited
class ProgramXML implements Comparable{

	String title
	String buid
	String language
	String filename
	String userUpdatingProgramXML


	Date dateCreated
	Date lastUpdated

	SortedSet secureProgram
	static hasMany = [secureProgram:SecureProgram]
	ContentType contentType

	static mapping = {
		sort id: "desc"
	}
	int compareTo(obj) {
		title.compareTo(obj.title)
	}
	static constraints = {
		title (nullable:false,blank: false)

		dateCreated ()
		lastUpdated ()
		buid (validator: { val, obj ->
			if(val==null)
			{

				return 'hmof.programxml.ProgramXML.buid.nullable'

			}else
			{
				return true
			}

		},nullable:false,blank: false,unique: true)
		language (inList: ["en-us", "es-mx", "fr", "Multi"], nullable:false)
		filename(validator: { val, obj ->
			if(val==null)
			{

				return 'hmof.programxml.ProgramXML.filename.nullable'

			}else
			{
				return true
			}
		},matches:/^\S+\.\S+$/,nullable:false,blank: false,unique: true)
	}
	
	
	String toString(){
		
		return id
	}

}
