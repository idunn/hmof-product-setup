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
		
		var latestRevision = optionSelected.split("/")[1];
		var environmentRevision = optionSelected.split("/")[2];
		environmentRevision = environmentRevision.split(",")[2];
		var doesPJobExists = optionSelected.split("/")[3];

		if (environmentRevision != undefined) {
			environmentRevision = environmentRevision.trim();
		}
		
	 if (latestRevision == environmentRevision  ) {	
	
		 if(doesPJobExists=="true"){
		getConfirm("A job with the same revision already exists on the environment. <br><br> Execute as a Smart Deployment  <input type=\"checkbox\" name=\"doesPreviousJobExist1\" value=\"true\" checked/>  <a class=\"my-tool-tip\" data-toggle=\"tooltip\" data-placement=\"left\" data-container=\"body\" title=\"A Smart Deployment will only deploy bundles which have not previously been deployed. Choosing this option will improve the deployment time of your job. This option is recommended.\"><i class=\"glyphicon glyphicon-info-sign\"></i></a><br><br> Do you want to proceed? ",function(result) {
			   // Do something with result...

		});		
		return false;	
		 }else if(doesPJobExists=="false")
			 {
			 
			 getConfirm("A job with the same revision already exists on the environment, Do you want to proceed? ",function(result) {
				   // Do something with result...

			});
			return false;	
			 
			 }
			
		} else if(latestRevision != environmentRevision && doesPJobExists=="true")
		{	
			getConfirm("Execute as a Smart Deployment   <input type=\"checkbox\" name=\"doesPreviousJobExist1\" value=\"true\" checked/> <a class=\"my-tool-tip\" data-toggle=\"tooltip\" data-placement=\"left\" data-container=\"body\" title=\"A Smart Deployment will only deploy bundles which have not previously been deployed. Choosing this option will improve the deployment time of your job. This option is recommended.\"><i class=\"glyphicon glyphicon-info-sign\"></i></a> <br><br>Are you sure you want to deploy?",function(result) {
				   // Do something with result...

			});		
			return false;	
			
		}
		
		else {	
					getConfirm("Are you sure you want to deploy? ",function(result) {
						   // Do something with result...

					});
					return false;
					
	
		}
		}
		else{
				alert("Please select content to deploy!");
				return false;
			}
	}
	
	function getConfirm(confirmMessage,callback){
		
	    confirmMessage = confirmMessage || '';
	    
	    $('#confirmbox').modal({show:true,
	                            backdrop:false,
	                            keyboard: false,
	    });

	    $('#confirmMessage').html(confirmMessage);
	    $('#confirmFalse').click(function(){
	        $('#confirmbox').modal('hide');
	        if (callback) callback(false);

	    });
	    $('#confirmTrue').click(function(){
	        $('#confirmbox').modal('hide');
	        if (callback) callback(true);
	    });
	}  

	function promote(){				
		if($("input:radio[name='rad']").is(":checked")){		
		var optionSelected = $('input[name=rad]:checked').val();				
		var instanceToBePromoted = document.getElementById("instanceToBePromoted");
		instanceToBePromoted.value = optionSelected;
		
		var latestRevision = optionSelected.split("/")[1];
		var environmentRevision = optionSelected.split("/")[2];
		environmentRevision = environmentRevision.split(",")[2];
		var doesPJobExists = optionSelected.split("/")[3];
	
		if (environmentRevision != undefined) {
			environmentRevision = environmentRevision.trim();
		}
		if (latestRevision == environmentRevision) {
			if(doesPJobExists=="true"){
			getConfirm('A job with the same revision already exists on the environment. <br><br>Execute as a Smart Deployment  <input type=\"checkbox\" name=\"doesPreviousJobExist1\" value=\"true\" checked/> <a class=\"my-tool-tip\" data-toggle=\"tooltip\" data-placement=\"left\" data-container=\"body\" title=\"A Smart Deployment will only deploy bundles which have not previously been deployed. Choosing this option will improve the deployment time of your job. This option is recommended.\"><i class=\"glyphicon glyphicon-info-sign\"></i></a><br><br> Do you want to proceed?',function(result) {
				   // Do something with result...

			});
			return false;		
			}else
				{
				getConfirm('A job with the same revision already exists on the environment, Do you want to proceed? ',function(result) {
					   // Do something with result...

				});
				return false;	
				
				}
		}else if(latestRevision != environmentRevision && doesPJobExists=="true")
		{			
				
				getConfirm('A job with the same revision already exists on the environment. <br><br>Execute as a Smart Deployment <input type=\"checkbox\" name=\"doesPreviousJobExist1\" value=\"true\" checked/> <a class=\"my-tool-tip\" data-toggle=\"tooltip\" data-placement=\"left\" data-container=\"body\" title=\"A Smart Deployment will only deploy bundles which have not previously been deployed. Choosing this option will improve the deployment time of your job. This option is recommended.\"><i class=\"glyphicon glyphicon-info-sign\"></i></a><br><br>Are you sure you want to promote?',function(result) {
					   // Do something with result...

				});
				return false;		
								
			
		}else {
		
			getConfirm("Are you sure you want to promote? ",function(result) {
				   // Do something with result...

			});
			return false;
		
		}
		}
		else{
				alert("Please select content to promote!");
				return false;
			}
	}
	$("a.my-tool-tip").tooltip();
	function pageonload()
	{		
		
		var selectIds = $('#Teach2,#Teach3,#Teach4,#Student2,#Student3,#Student4');
		$(function ($) {
		    selectIds.on('show.bs.collapse hidden.bs.collapse', function () {
		        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
		    })
		});

		var knewtonProd = document.getElementById("knewtonProduct").checked;
		
		 if(knewtonProd){		    
			    $("#collapseThree").show();			
				
			    $("#demo").show();
				$("#demo").toggleClass('collapse');
				
				}
		 
		 if(!knewtonProd)
			{			
			 
			 $("#demo").hide();		
			}
		 var  lar =document.getElementById("lar").value;
			
			if(knewtonProd && lar=="true"){		    			
			    $("#collapseTwo").show();
			    $("#collapseThree").show();			
			 	
			    $("#demo").show();
				$('#demo').toggleClass('collapse');
				$('#Teach2').toggleClass('collapse');
				$('#Teach3').toggleClass('collapse');
				$('#Teach4').toggleClass('collapse');
				$('#Student2').toggleClass('collapse');
				$('#Student3').toggleClass('collapse');
				$('#Student4').toggleClass('collapse');
				
				}

			
			
			 if(lar=="true" && !knewtonProd)
				{				 
					 			
					    $("#collapseTwo").show();
					 		
					    $('#Teach2').toggleClass('collapse');
						$('#Teach3').toggleClass('collapse');
						$('#Teach4').toggleClass('collapse');
						$('#Student2').toggleClass('collapse');
						$('#Student3').toggleClass('collapse');
						$('#Student4').toggleClass('collapse');
					
				 $("#demo").hide();		
				}
			

		
	}