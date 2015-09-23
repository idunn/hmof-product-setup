//  This is needed to prevent 304 responses in Internet Explorer.  Without this, a second request to the same deployment create and environment will not hit the server
$.ajaxSetup ({
    // Disable caching of AJAX responses
    cache: false
});
// Pass the environment selected into the deployment creation dialog
$(document).on("click", ".open-NewDeploymentDialog", function () {
     var myDepEnv = $(this).data('env');
     var myJobId = $(this).data('jobid');
     var myURL = $(this).data('url');
     var optionSelected1 = $('input[name=listGroup]:checked').val();
     //alert('myDepEnv: ' + myDepEnv + ' ,myJobId: '+ myJobId + ' myURL: ' + myURL)

     $("#confirmbox .modal-body").load(myURL+ '?env=' +myDepEnv+ '&job.id=' + myJobId, function() { 
      	 
     	$("#confirmbox").modal("show"); 
        
     });
});

//Initialise to the first one on the page
var mySelectedRadio = $("#Content :input[ name=listGroup]:checked").val();
// Pass the id of the radio button selected from the list page
$(document).on("click", 'input[name="listGroup"]', function () {

    mySelectedRadio = $(this).val();

    //  Remove highlight from the previously selected row
    $(this).closest('table').find('tr.warning').removeClass("warning");
    //  Highlight the new row 
    $(this).closest('tr').toggleClass("warning");
    
    //  Update all of the URLS that need to know which was selected, we have marked them with a class of update-URL
    $("a.update-URL").each(function() {
    var _href = $(this).attr("href");
	    
    //  We have defaulted the value of the id or we may have already clicked a radio button so trim the url back to the action/controller
    var n = _href.indexOf('?');    
    _href = _href.substring(0, n != -1 ? n : _href.length);

    $(this).attr("href", _href + '?id=' + mySelectedRadio);
    });
    
    // Update all of the jobids that are passed through to create a deployment, this only applies for Jobs
    $("a.open-NewDeploymentDialog").each(function() {
        $(this).data('jobid', mySelectedRadio);
        });
});
/*
 * Handle the environment group buttons that switch between the tabs of the environment carousel
 */
$(function() {

	var totalEnvGroups = ($(":input[name='envGroupButton']").length - 1)

    // Cycles to the previous environment group
    $(".prev-slide").click(function(){
    	$("#myCarousel").carousel('prev');
	   
		//  Remove warning highlight from the previously tab
		var currentGroupInput = $(this).closest('div').find('input.btn-warning');
		currentGroupInput.removeClass("btn-warning");
		//  Highlight the newly selected carousel tab
		var prevIndex = ($(":input[name='envGroupButton']").index(currentGroupInput) - 1);
		  
		//  Only 3 group types at the moment - this will need to be changed so it counts how many there are and isn't hardcoded
		if(prevIndex < 0)
			prevIndex = totalEnvGroups;
		$(":input[name='envGroupButton']:eq(" + (prevIndex) + ")").toggleClass("btn-warning");
		
		//  Select the correct radio button for the current tab
		//  First clear all of the others
		
		$("#Content :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 
		$("#Integration :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false)
		$("#Platform :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 

		if(prevIndex == 0)
			$("#Content :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked',true);
		else if(prevIndex == 1)
			$("#Integration :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked',true);
		else if(prevIndex == 2)
			$("#Platform :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked',true);
		
    });
    // Cycles to the next environment group
    $(".next-slide").click(function(){
    	$("#myCarousel").carousel('next');
	   
		//  Remove warning highlight from the previously tab
		var currentGroupInput = $(this).closest('div').find('input.btn-warning');
		currentGroupInput.removeClass("btn-warning");
		//  Highlight the newly selected carousel tab
		var nextIndex = ($(":input[name='envGroupButton']").index(currentGroupInput) + 1);
		//  Only 3 group types at the moment - this will need to be changed so it counts how many there are and isn't hardcoded
		if(nextIndex > totalEnvGroups)
			nextIndex = 0;
		$(":input[name='envGroupButton']:eq(" + (nextIndex) + ")").toggleClass("btn-warning");
		
		//  Select the correct radio button for the current tab
		//  First clear all of the others
		
		$("#Content :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 
		$("#Integration :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false)
		$("#Platform :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 
	
		
		if(nextIndex == 0)
			$("#Content :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked',true);
		else if(nextIndex == 1)
			$("#Integration :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked',true);
		else if(nextIndex == 2)
			$("#Platform :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked',true);
		
		
    });
    // Cycles the carousel to a particular environment group
    $(".ContentButton").click(function(){
    	$("#myCarousel").carousel(0);
    	
		//  Remove warning highlight from the previously tab
		$(this).closest('div').find('input.btn-warning').removeClass("btn-warning");
		//  Highlight the newly selected carousel tab
		$(this).closest('div').find('input.ContentButton').toggleClass("btn-warning"); 
		
		//  Select the correct radio button for the current tab
		//  First clear all of the others
		
		$("#Content :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 
		$("#Integration :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false)
		$("#Platform :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 

		$("#Content :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked',true);
		
	});
    $(".PlatformButton").click(function(){
	    $("#myCarousel").carousel(2);
	  
		//  Remove warning highlight from the previously tab
		$(this).closest('div').find('input.btn-warning').removeClass("btn-warning");
		//  Highlight the newly selected carousel tab
		$(this).closest('div').find('input.PlatformButton').toggleClass("btn-warning");
		
		//  Select the correct radio button for the current tab
		//  First clear all of the others
		
		$("#Content :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 
		$("#Integration :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false)
		$("#Platform :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 

		$("#Integration :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked',true);
	    
    });
    $(".IntegrationButton").click(function(){
    	$("#myCarousel").carousel(1);
    	
		//  Remove warning highlight from the previously tab
		$(this).closest('div').find('input.btn-warning').removeClass("btn-warning");
		//  Highlight the newly selected carousel tab
		$(this).closest('div').find('input.IntegrationButton').toggleClass("btn-warning");
		//  Select the correct radio button for the current tab
		//  First clear all of the others
		
		$("#Content :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 
		$("#Integration :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false)
		$("#Platform :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked', false) 

		$("#Platform :input[name=listGroup][value=" + mySelectedRadio + "]").prop('checked',true);         
	});
	
    $('.carousel').each(function(){
        $(this).carousel({
            interval: false
        });
    });
    
  });

/*
 * bootstrap-select.js
 

(function($) {

  'use strict'; // jshint ;_;

  $.fn.dropSelect = function(option) {
    return this.each(function() {

      var $this = $(this);
      var display = $this.find('.dropdown-display');        // display span
      var field = $this.find('input.dropdown-field');       // hidden input
      var options = $this.find('ul.dropdown-menu > li > a');// select options

      // when the hidden field is updated, update dropdown-toggle
      var onFieldChange = function(event) {
        var val = $(this).val();
        var displayText = options.filter("[data-value='" + val + "']").html();
        display.html(displayText);
      };

      // when an option is clicked, update the hidden field
      var onOptionClick = function(event) {
        // stop click from causing page refresh
        event.preventDefault();
        field.val($(this).attr('data-value'));
        field.change();
      };

      field.change(onFieldChange);
      options.click(onOptionClick);

    });
  };

  // invoke on every div element with 'data-select=true'
  $(function() {
    $('div[data-select=true]').dropSelect();
  });
  
  //  Change the direction of the chevron on an accordian toggle
$('.accordion').on('show hide', function (n) {
    $(n.target).siblings('.accordion-heading').find('.accordion-toggle i').toggleClass('icon-chevron-up icon-chevron-down');
});
  
})(window.jQuery);*/