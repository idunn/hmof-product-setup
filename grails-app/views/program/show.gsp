
<%@ page import="hmof.Program"%>
<%@ page import="hmof.DeploymentService"%>
<!DOCTYPE html>
<html>

<head>
<meta name="layout" content="kickstart" />
<g:set var="entityName"
	value="${message(code: 'program.label', default: 'Program')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'search.css')}"
	type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'scroller.css')}"
	type="text/css">
        
        <script>

   	 function preview(bundleinstanceId,bundlename){
		 	
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
		    	url: "${createLink(controller: 'bundle', action: 'getDashboardSecureProgramChildren')}",
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
		            	            		if(coverimage[c]!=undefined)
		            	            		codata+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span style=\"color:#666;font-size:11px;font-weight:bold;\">"+teacherLabel[c]+"</span></div>";  
		            	            	}  		            	            	
		            	            	
		            	            	$("#result1").html(codata);
		            	              var codata2="";
		            	           
                               for (var c2= 7; c2 < 14; c2++) {		  		            	            	
	  		            	            	if(coverimage[c2]!=undefined)
                              	 codata2+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c2]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c2]+"</span></div>";  
		            	            	}  
		            	            
                                 $("#result2").html(codata2);


                             	var codata3="";
                              for (var c3= 14; c3 < 21; c3++) {		  		            	            	
	  		            	            	if(coverimage[c3]!=undefined)
                          	 codata3+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c3]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c3]+"</span></div>";  
		            	            	}  

                             $("#result3").html(codata3);
                            
                             var codata4="";
                             for (var c4= 21; c4 < 27; c4++) {		  		            	            	
	  		            	            	if(coverimage[c4]!=undefined)
                         	 codata4+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c4]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c4]+"</span></div>";  
		            	            	}  

                            $("#result4").html(codata4);

                            var codata5="";
                            for (var c5= 27; c5 < 35; c5++) {		  		            	            	
	  		            	            	if(coverimage[c5]!=undefined)
                        	 codata5+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c5]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c5]+"</span></div>";  
		            	            	} 
                            $("#result5").html(codata5);
                            var codata6="";
                            for (var c6= 35; c6 < 42; c6++) {		  		            	            	
	  		            	            	if(coverimage[c6]!=undefined)
                        	 codata6+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c6]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c6]+"</span></div>";  
		            	            	} 
                            $("#result6").html(codata6);
                            var codata7="";
                            for (var c7= 42; c7 < 49; c7++) {		  		            	            	
	  		            	            	if(coverimage[c7]!=undefined)
                        	 codata7+="<div class=\"coverimage1\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c7]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c7]+"</span></div>";  
		            	            	}  

                           $("#result7").html(codata7);

                                
		            	            	var navdata="";
		            	            	if(coverimage.length!=0 && coverimage.length>7)
		            	            	{
		            	            		navdata+="<a class=\"left carousel-control\" href=\"#myCarousel\" data-slide=\"prev\"><span class=\"glyphicon glyphicon-chevron-left\"></span></a><a class=\"right carousel-control\" href=\"#myCarousel\" data-slide=\"next\"><span class=\"glyphicon glyphicon-chevron-right\"></span></a>";
		            	            	}

		            	            	$("#navigation").html(navdata);

		            	            	
		            	            	

			        	}       			       
		        }
		    })
		    
		  		
}

        </script>
</head>

<body>
<style>
	@font-face {
font-family: 'Glyphicons Halflings';
src: url('../../fonts/glyphicons-halflings-regular.eot');
src: url('../../fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded- opentype'), 
     url('../../fonts/glyphicons-halflings-regular.woff') format('woff'), 
     url('../../fonts/glyphicons-halflings-regular.ttf') format('truetype'), 
     url('../../fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg');
} 
</style>
<g:set var="jobdetails" bean="deploymentService"/>
<g:set var="isbnWebService" bean="utilityService"/>
<div class="row">
	<div class="widget stacked ">
		<div class="widget-content">
	<section id="show-program" class="first">

	<div class="span12 form-horizontal">

<div
	class="control-group">
	<label for="name" class="control-label col-sw-1"><g:message
							code="program.name.label" default="Name" /></label>
	<div class="controls show-style">
		${fieldValue(bean: programInstance, field: "name")}
	</div>
</div>

<div
	class="control-group">
	<label for="state" class="control-label col-sw-1"><g:message
							code="program.state.label" default="State" /></label>

					<div class="controls show-style">
		${fieldValue(bean: programInstance, field: "state")}
	</div>
</div>

		<div
	class="control-group">
	<label for="discipline" class="control-label col-sw-1"><g:message
							code="program.discipline.label" default="Discipline" /></label>

			<div class="controls show-style">
		${fieldValue(bean: programInstance, field: "discipline")}
	</div>
</div>				


		<div
	class="control-group">
	<label for="bundlecount" class="control-label col-sw-1"><g:message
							code="program.bundlecount.label" default="Bundle Count" /></label>

			<div class="controls show-style">
		${bundleCount}
	</div>
</div>	

		<div
	class="control-group">
	<label for="nondeployablebundles" class="control-label col-sw-1"><g:message
							code="program.bundlecount.label" default="Non Deployable Bundles" /></label>

			<div class="controls show-style">
			
		<g:if test="${unDeployableBundleMap}">
				 
					<ul>
					<g:each var="unDeployableBundleMap1" in="${unDeployableBundleMap}"> 
					
					<li><g:link controller="Bundle" action="show" id="${unDeployableBundleMap1.key}">${unDeployableBundleMap1.value}</g:link></li>      
                    </g:each>
                    </ul>
                    </g:if>
	</div>
</div>	
				
		<div
	class="control-group">
	<label for="bundles" class="control-label col-sw-1"><g:message
							code="program.bundles.label" default="Bundles" /></label>

					<div class="controls show-style">
						<!-- Add new Template --> <g:render template="addBundle" /> <!-- Added new table -->
						<table class="table table-bordered margin-top-medium">
							<thead>
								<tr>
									<th>
										${'List'}
									</th>
									<th>
										${'Preview'}
									</th>
									<th>
										${'Edit'}
									</th>
									<th>
										${'SAP Status'}
									</th>
								</tr>
							</thead>
							<tbody>
								<g:each in="${programInstance.bundles}" status="i" var="b">
							       
									<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
										<td><g:link controller="bundle" action="show"
												id="${b.id}">
												${b?.encodeAsHTML()}
											</g:link> </td>
										<td><g:if test="${jobdetails.isDashboardSecureProgram(b.id)==true}">	
				<a href="#scrollModal" class="previewlink" onclick="return preview(${b.id},'${b.title}');" >Preview</a>

					<g:render template="/_common/modals/scrollDialog"   />



				</g:if></td>
										<td><g:link controller="bundle" action="edit"
												id="${b.id}">
												${"Edit"}
											</g:link></td>
											
											<td>
											<g:each in="${sapResultsList}" status="j" var="c">										
											<g:if test="${c.isbn!=null &&  (c.isbn).equals(b.isbn) }"> 											
											${c.status}
										     </g:if>
											</g:each>
											</td>
											
									</tr>
									
								</g:each>

							</tbody>
						</table>
					</div>
						
				<div
	class="control-group">
	<label for="dateCreated" class="control-label col-sw-1"><g:message
							code="program.dateCreated.label" default="Date Created" /></label>

					<div class="controls show-style"><g:formatDate
							date="${programInstance?.dateCreated}" />
							</div>
							</div>

				<div
	class="control-group">
	<label for="lastUpdated" class="control-label col-sw-1"><g:message
							code="program.lastUpdated.label" default="Last Updated" /></label>

					<div class="controls show-style"><g:formatDate
							date="${programInstance?.lastUpdated}" />
					</div>
					</div>

			</div>			
				
				
	
	</div>
	</section>
</div>
</div>
</div>
<g:render template="/_common/modals/scrollDialog"  />
</body>

</html>
