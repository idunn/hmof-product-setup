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
     
     //alert('myDepEnv: ' + myDepEnv + ' ,myJobId: '+ myJobId + ' myURL: ' + myURL)

     // load the URL and show modal on success
     $("#DeploymentModal .modal-body").load(myURL+ '?job.id=' + myJobId + '&env=' + myDepEnv, function() { 
         $("#DeploymentModal").modal("show"); 
     });
});

// Pass the id of the radio button selected from the list page
$(document).on("click", 'input[name="listGroup"]', function () {
    var mySelectedRadio = $(this).val();
    
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
 * bootstrap-select.js
 */

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
  
})(window.jQuery);