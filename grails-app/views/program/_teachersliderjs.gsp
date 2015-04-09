        <script>

   	 function teacherPreview(bundleinstanceId,bundlename){
     var URL="${createLink(controller: 'bundle', action: 'getDashboardSecureProgramChildren')}";
	
			$.ajax({
		    	url: "${createLink(controller: 'bundle', action: 'getDashboardBundleChildren')}",
		        data: "instanceId=" + bundleinstanceId,
		        type: 'POST',
		        async : false,
		        datatype: 'text',
		        success: function (data) {
		        	$('#scrollModal').modal({show:true,
	                   backdrop:false,
	                   keyboard: false,
					});    
		        	if(!(jQuery.isEmptyObject(data))){  		        		
		        		
		            	            	var name=data.name; 
		            	            
		            	            	 if(name[0].indexOf("|")>-1){
	  		            	            	 sname=name[0].split("|"); 		            	            	
	  		            	            	 
		  		            	            }else
			  		            	            {
		  		            	            	sname=name;
			  		            	            }
		            	            	   var spdata="";	
		            	            	 spdata+="<select class=\"form-control spname\" name=\"select\" id=\"select\" disabled >"
		            	            	for (var i = 0; i < data.count; i++) {
		            	            		spdata+="<option>"+sname[0]+"</option>";
		            	            	}
		            	            	spdata+="</select>";
		            	            	$("#bundleName").html(bundlename);
		            	            	
		            	            	$("#result").html(spdata);
		            	            	

			        	}       			       
		        }
		    })

	$.ajax({
		    	url: URL,
		        data: "instanceId=" + bundleinstanceId,
		        type: 'POST',
		        async : false,
		        datatype: 'text',
		        success: function (data) {
		        	   
		        	if(!(jQuery.isEmptyObject(data))){  		        		
		            	            	 
		            	            	var coverimage=data.coverimage;  
		            	            	var teacherLabel=data.teacherLabel;  
		            	            	var ordernum=data.ordernum; 
		            	            	   var codata="";	
		            	            	
		            	            	for (var c = 0; c < 7; c++) {	
			            	            		
		            	            		 if(teacherLabel[c]==null){
		            	                    	   teacherLabel[c]="";
		            	                       }
		            	            		 if(coverimage[c]!=undefined) 		
			            	            	codata+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span style=\"color:#666;font-size:11px;font-weight:bold;\">"+teacherLabel[c]+"</span></div>";  
				            	            		

			            	            	}  		            	            	
		            	            	
		            	            	$("#result1").html(codata);
		            	              var codata2="";
		            	           
                               for (var c2= 7; c2 < 14; c2++) {		  		            	            	
                            		 if(teacherLabel[c2]==null){
          	                    	   teacherLabel[c2]="";
          	                           }
                            		 if(coverimage[c2]!=undefined) 		
	            	            	codata2+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c2]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span style=\"color:#666;font-size:11px;font-weight:bold;\">"+teacherLabel[c2]+"</span></div>";  
		            	            		

			            	            	}  
		            	            
                                 $("#result2").html(codata2);


                             	var codata3="";
                              for (var c3= 14; c3 < 21; c3++) {		  		            	            	
                            		 if(teacherLabel[c3]==null){
          	                    	   teacherLabel[c3]="";
          	                       }
                            		 if(coverimage[c3]!=undefined) 		
	            	            	codata3+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c3]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span style=\"color:#666;font-size:11px;font-weight:bold;\">"+teacherLabel[c3]+"</span></div>";  
		            	            		
		            	            	}  

                             $("#result3").html(codata3);
                            
                             var codata4="";
                             for (var c4= 21; c4 < 28; c4++) {		  		            	            	
                            	 if(teacherLabel[c4]==null){
        	                    	   teacherLabel[c4]="";
        	                       }
                            	 if(coverimage[c4]!=undefined) 		
	            	            	codata4+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c4]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span style=\"color:#666;font-size:11px;font-weight:bold;\">"+teacherLabel[c4]+"</span></div>";  
		            	            		
		            	            	}  

                            $("#result4").html(codata4);

                            var codata5="";
                            for (var c5= 27; c5 < 35; c5++) {		  		            	            	
                            	if(teacherLabel[c5]==null){
       	                    	   teacherLabel[c5]="";
       	                       }
                            	 if(coverimage[c5]!=undefined) 		
	            	            	codata5+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c5]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span style=\"color:#666;font-size:11px;font-weight:bold;\">"+teacherLabel[c5]+"</span></div>";  
		            	            		
		            	            	} 
                            $("#result5").html(codata5);
                            var codata6="";
                            for (var c6= 35; c6 < 42; c6++) {		  		            	            	
                            	if(teacherLabel[c6]==null){
       	                    	   teacherLabel[c6]="";
       	                       }
                            	 if(coverimage[c6]!=undefined) 		
	            	            	codata6+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c6]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span style=\"color:#666;font-size:11px;font-weight:bold;\">"+teacherLabel[c6]+"</span></div>";  
		            	            		
		            	            	} 
                            $("#result6").html(codata6);
                            var codata7="";
                            for (var c7= 42; c7 < 49; c7++) {		  		            	            	
                            	if(teacherLabel[c7]==null){
       	                    	   teacherLabel[c7]="";
       	                       }
                            	 if(coverimage[c7]!=undefined) 		 
	            	            	codata7+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c7]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span style=\"color:#666;font-size:11px;font-weight:bold;\">"+teacherLabel[c7]+"</span></div>";  
		            	            		
		            	            	}  

                           $("#result7").html(codata7);

                                var navdata="";
		            	            	var navdataleft1="";
		            	            	var navdataleft2="";
		            	            	var navdataright="";
		            	            	if(coverimage.length!=0 )
		            	            	{
		            	            		
			            	            	if(coverimage[0]!=undefined && coverimage[1]!=undefined && coverimage[2]!=undefined && coverimage[3]!=undefined && coverimage[4]!=undefined && coverimage[5]!=undefined && coverimage[6]!=undefined && coverimage[7]!=undefined){
		            	            		navdataleft1+="<a class=\"left carousel-control\" href=\"javascript:void(0);\" data-slide=\"prev\"><span class=\"glyphicon glyphicon-chevron-left\"></span></a>";
		            	            		navdataleft2+="";
			            	            	}
			            	            	else if(coverimage.length>7)
			            	            	{
			            	            		navdataleft1+="";
			            	            		navdataleft2+="<a class=\"left carousel-control\" href=\"#myCarousel\" data-slide=\"prev\"><span class=\"glyphicon glyphicon-chevron-left\"></span></a>";
				            	            	}


			            	            	}
		            	            	if(coverimage.length!=0 && coverimage.length>7)
		            	            	{
		            	            	navdataright+="<a class=\"right carousel-control\" href=\"#myCarousel\"  id=\"right\" data-slide=\"next\"><span class=\"glyphicon glyphicon-chevron-right\"></span></a>";
		            	            	}
		            	            	navdata=navdataleft1+navdataleft2+navdataright;

		            	            	$("#navigation").html(navdata);
		            	            
		            	            	$("#coverimagelen").val(coverimage.length);
		            	            	

			        	}       			       
		        }
		    })
		    
		  		
}
   
   	function closePreview()
   	{
   		self.location.reload();
    }

        </script>