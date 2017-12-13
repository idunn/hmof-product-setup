/**
 * 
 */
$(document).ready(function() {
	var navdataleft="";
	var navdataright="";
	$('#myCarouselslide').carousel({	
		
		interval: false
	})   

	// Cycles to the previous item
      //listener for after slide
$('#myCarouselslide').on('slid.bs.carousel', function () {
   //Each slide has a .item class to it, you can get the total number of slides like this
    var totalItems = $('.item,.active').length;
var showRightArrow=false;

//find current slide number
//var currentIndex = $('div.active').index() + 1;
var carouselData = $(this).data('bs.carousel');
var currentIndex1 = carouselData.getActiveIndex();
var currentIndex=currentIndex1+1;
var tot=parseInt(document.getElementById("coverimagelen").value);

if(currentIndex==1 && tot <=7) {
	showRightArrow=false;  
    navdataleft+="<a class=\"left carousel-control\" href=\"#javascript:void(0);\" data-slide=\"prev\"><span class=\"glyphicon glyphicon-chevron-left\"></span></a>";
    navdataright+="<a class=\"right carousel-control\" href=\"#javascript:void(0);\" data-slide=\"next\"><span class=\"glyphicon glyphicon-chevron-right\"></span></a>";
	navdata="";
	   $("#navigation").html(navdata); 
 } 

else{
	
for(var i = 0; i <= tot; i ++) {
  
   if(( i >7 || i >14 || i >21) && currentIndex==1  ) {
	   showRightArrow=false;  
       navdataleft+="<a class=\"left carousel-control\" href=\"#javascript:void(0);\" data-slide=\"prev\"><span class=\"glyphicon glyphicon-chevron-left\"></span></a>";
   	   navdataright+="<a class=\"right carousel-control\" href=\"#myCarouselslide\" data-slide=\"next\"><span class=\"glyphicon glyphicon-chevron-right\"></span></a>";
   	   navdata=navdataleft+navdataright;
   	   $("#navigation").html(navdata); 
    }    
    else if((( i >7 && i <=14) && currentIndex==2) || (( i >14 && i <=21) && currentIndex==3) || ((i >21 && i <=28) && currentIndex==4 ) || (( i >28 && i <=35) && currentIndex==5 ) || (( i >35 && i <=42) && currentIndex==6 ) || (( i >42 && i <=49) && currentIndex==7 ) ) { 
    	showRightArrow=true;     
        
     }    
    else
	   {
    	showRightArrow=false;  
  	   navdataleft+="<a class=\"left carousel-control\" href=\"#myCarouselslide\" data-slide=\"prev\"><span class=\"glyphicon glyphicon-chevron-left\"></span></a>";
  	   navdataright+="<a class=\"right carousel-control\" href=\"#myCarouselslide\" data-slide=\"next\"><span class=\"glyphicon glyphicon-chevron-right\"></span></a>";
  	   navdata=navdataleft+navdataright;
  	   $("#navigation").html(navdata);
  	   }
}

if(showRightArrow){
	navdataleft+="<a class=\"left carousel-control\" href=\"#myCarouselslide\" data-slide=\"prev\"><span class=\"glyphicon glyphicon-chevron-left\"></span></a>";
	   navdataright+="<a class=\"right carousel-control\" href=\"javascript:void(0);\" data-slide=\"next\"><span class=\"glyphicon glyphicon-chevron-right\"></span></a>";
	   navdata=navdataleft+navdataright;
	   $("#navigation").html(navdata);
	
}


}  
  if(totalItems == currentIndex){
        $('.carousel2').carousel({
          interval: false
    })
  }

})
    
});
