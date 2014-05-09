package hmof

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Program)
class ProgramSpec extends ConstraintUnitSpec {

	/*def setup() {
		//mock a person with some data (put unique violations in here so they can be tested, the others aren't needed)
		mockForConstraintsTests(Program, [new Program(name: 'failme', discipline:'math')])
	}*/

	@Unroll("program #field is #error using #val")

	def "test program constraints"() {
		when:
		def obj = new Program("$field": val)
				
		then:
		validateConstraints(obj, field, error, val)

		where:
		error                  | field         | val
		'matches'              | 'name'        | 'TestProgram'
		'matches'              | 'name'        | 'TestProgram2016'		
		'inList'               | 'discipline'  | 'math'
		'inList'               | 'discipline'  | 'language_arts'
		'inList'               | 'discipline'  | 'world_languages'
		'inList'               | 'discipline'  | 'social_studies'
		'inList'               | 'discipline'  | 'Other'
		

	}

}
