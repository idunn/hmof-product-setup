

<script>
$(document).ready(function() {
	$('#myCarousel').carousel({	
		interval: false
	})
    
    $('#myCarousel').on('slid.bs.carousel', function() {
    	//alert("slid");
	});
    
    
});

</script>
<div id="scrollModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="DeleteModalLabel" aria-hidden="true" >
	<div class="modal-dialog" style="width:65%">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>					
					<h4 style="color:#666;font-size:14px;font-weight:bold;">Preview : <span id="bundleName"></span></h4>
					<div style="background-color:#DEE7F2;height:35px;">
					<span style="color:#305DA6;font-family: 'Lato',sans-serif; font-size: 26px;line-height: 1;">Resources</span>
						<div align="right">
						
						<div id="result"></div>
						</div>
				</div>
			
		
			</div>
			<div class="modal-body">
			
			
        <div class="container">  
		<div class="col-md-9">    
		<div style="padding-left:10px;">   
		 <div>     <div>    <div>     <div>    <div>   
		<div id="myCarousel" class="carousel slide">   
		<!-- Carousel items -->   
		<div class="carousel-inner">   
		  
		<div class="item active">         
		<div class="row">   
		
     	<div id="result1"></div>
		</div>    
		<!--/row-->   
		</div> 
		  
		<!--/item--> 
		
		               
		<div class="item">          
		<div class="row">                        
		<div id="result2"></div>
     	
		 
		</div>    
		</div>  
		                    <!--/row-->     
 							
		</div>                    <!--/item--> 
            		<div class="item">          
		<div class="row">                        
		<div id="result3"></div>
     	
		 
		</div>    
		</div>  
		                    <!--/row-->     
 							
		</div>                    <!--/item-->   
		        		<div class="item">          
		<div class="row">                        
		<div id="result4"></div>
     	
		 
		</div>    
		</div>  
		                    <!--/row-->     
 							
		</div>                    <!--/item-->   
		<div class="item">          
		<div class="row">                        
		<div id="result5"></div>
     	
		 
		</div>    
		</div>  
		                    <!--/row-->     
 							
		</div>                    <!--/item-->   
		<div class="item">          
		<div class="row">                        
		<div id="result6"></div>
     	
		 
		</div>    
		</div>  
		                    <!--/row-->     
 							
		</div>                    <!--/item-->   
		<div class="item">          
		<div class="row">                        
		<div id="result7"></div>
     	
		 
		</div>    
		</div>  
		                    <!--/row-->     
 							
		</div>                    <!--/item-->   
	<!--/row-->            
	</div>               
	<!--/item-->         
	</div>        
	<!--/carousel-inner-->
	<div id="navigation"></div>
	</div>       
	<!--/myCarousel-->  
	</div>    
    <!--/well--> 
	</div>
	
	
        </div>
			
		</div>
	</div>
