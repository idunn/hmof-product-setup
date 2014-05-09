package hmof

import spock.lang.Specification

abstract class ConstraintUnitSpec extends Specification {

	void validateConstraints(obj, field, error, val) {
		
		println "Value being tested: " + val
		println "Property to test: " + field
		println "Constraint being tested: " + error

		def validated = obj.validate()

		println "Is Valid: " + validated

		if (validated){
			println "No Issues with this property: " + field
		}
		else{
			assert error == obj.errors[field]
			println "Constraint issue with property: " + field
		}
	}
}
