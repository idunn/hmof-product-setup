function toggle(box,theId) {
	//alert("inside myScript js toggle method");
		  var el;
		  if(document.getElementById) {
		    var row = document.getElementById(theId);
		    var f = row;
		    while(f.tagName.toLowerCase() != 'form') {
		      if(f.parentNode) f=f.parentNode;
		      else if(f.parentElement) f=f.parentElement; //IE4 support
		      if(f.tagName.toLowerCase() == 'body') {
		        alert('ERROR: looped to find <'+'body'+'>'); return;
		      }
		    }
		    for(var i=0;i<f.elements.length;i++) {
		      if(f.elements[i].tagName.toLowerCase() == 'input')
		        //the if condition above is to compensate for an NS6.0 bug.
		        if(f.elements[i].type.toLowerCase() == 'radio')
		          if(f.elements[i].name.toLowerCase().indexOf('rad')!=-1) {
		            el=f.elements[i];
		            while(el.tagName.toLowerCase() != 'tr') {
		              if(el.parentNode) el=el.parentNode;
		              else if(el.parentElement) el=el.parentElement; //IE4 support
		              if(el.tagName.toLowerCase() == 'form') {
		                alert('ERROR: looped to find <'+'form'+'>'); return;
		              }
		            }
		          if(box != el) { el.className = "off"; }
		          }
		    }
		    row.className = (box.checked)?"on":"off";
		  }
		}


	function deploy(){				
		if($("input:radio[name='rad']").is(":checked")){		
		var optionSelected = $('input[name=rad]:checked').val();				
		var instanceDetail = document.getElementById("instanceDetail");
		instanceDetail.value = optionSelected;		
		var deployProgram=confirm("Are you sure you want to deploy?");
		if(deployProgram){
			return true;
			}
		else{
			return false; 
			}		
		}
		else{
				alert("Please select content to deploy!");
				return false;
			}
	}


	function promote(){				
		if($("input:radio[name='rad']").is(":checked")){		
		var optionSelected = $('input[name=rad]:checked').val();				
		var instanceToBePromoted = document.getElementById("instanceToBePromoted");
		instanceToBePromoted.value = optionSelected;		
		var promoteProgram=confirm("Are you sure you want to promote?");
		if(promoteProgram){
			return true;
			}
		else{
			return false; 
			}		
		}
		else{
				alert("Please select content to promote!");
				return false;
			}
	}