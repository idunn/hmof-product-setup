package hmof

class StatesTagLib {
	//static defaultEncodeAs = 'html'
	//static encodeAsForTags = [tagName: 'raw']


	def stateDropDown = { attrs ->
		def stateList = [
			AL:"Alabama",
			AK:"Alaska",
			AZ:"Arizona",
			AR:"Arkansas",
			CA:"California",
			CO:"Colorado",
			CT:"Connecticut",
			DC:"District of Columbia",
			DE:"Delaware",
			FL:"Florida",
			GA:"Georgia",
			HI:"Hawaii",
			ID:"Idaho",
			IL:"Illinois",
			IN:"Indiana",
			IA:"Iowa",
			KS:"Kansas",
			KY:"Kentucky",
			LA:"Louisiana",
			ME:"Maine",
			MD:"Maryland",
			MA:"Massachusetts",
			MI:"Michigan",
			MN:"Minnesota",
			MS:"Mississippi",
			MO:"Missouri",
			MT:"Montana",
			NE:"Nebraska",
			NV:"Nevada",
			NH:"New Hampshire",
			NJ:"New Jersey",
			NM:"New Mexico",
			NY:"New York",
			NC:"North Carolina",
			ND:"North Dakota",
			OH:"Ohio",
			OK:"Oklahoma",
			OR:"Oregon",
			PA:"Pennsylvania",
			RI:"Rhode Island",
			SC:"South Carolina",
			SD:"South Dakota",
			TN:"Tennessee",
			TX:"Texas",
			UT:"Utah",
			VT:"Vermont",
			VA:"Virginia",
			WA:"Washington",
			WI:"Wisconsin",
			WV:"West Virginia",
			WY:"Wyoming"]
		out << "<select name='${attrs.name}' id='${attrs.id}'>"
		out << "<option value=''>Select...</option>"
		stateList.each {
			out << "<option value='${it.key}'"
			if(attrs.selectedValue == it.key) {
				out << " selected='selected'"
			}
			out << ">${it.value}</option>"
		}
		out << "</select>"
	}



	/**
	 * Test TagLib
	 */
	def repeat = { attrs, body ->
		// retrieve the 'times' attribute and convert it to an int
		int n = attrs.int('times')

		// render the body 'n' times, passing a 1 based
		// counter into the body each time
		n?.times { counter ->
			out << body(counter + 1)
		}
	}




}
