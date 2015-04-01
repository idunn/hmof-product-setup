
<%@ page import="hmof.Bundle" %>
<%@ page import="hmof.DeploymentService"%>
<%@ page import="hmof.UtilityService"%>

<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'bundle.label', default: 'Bundle')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<script src="${resource(dir:'js',file:'myScript.js')}"></script>

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
		            	            		codata+="<div class=\"coverimage\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span style=\"color:#666;font-size:11px;font-weight:bold;\">"+teacherLabel[c]+"</span></div>";  
		            	            	}  		            	            	
		            	            	
		            	            	$("#result1").html(codata);
		            	              var codata2="";
		            	           
                               for (var c2= 7; c2 < 14; c2++) {		  		            	            	
	  		            	            	if(coverimage[c2]!=undefined)
                              	 codata2+="<div class=\"coverimage\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c2]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c2]+"</span></div>";  
		            	            	}  
		            	            
                                 $("#result2").html(codata2);


                             	var codata3="";
                              for (var c3= 14; c3 < 21; c3++) {		  		            	            	
	  		            	            	if(coverimage[c3]!=undefined)
                          	 codata3+="<div class=\"coverimage\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c3]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c3]+"</span></div>";  
		            	            	}  

                             $("#result3").html(codata3);
                            
                             var codata4="";
                             for (var c4= 21; c4 < 27; c4++) {		  		            	            	
	  		            	            	if(coverimage[c4]!=undefined)
                         	 codata4+="<div class=\"coverimage\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c4]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c4]+"</span></div>";  
		            	            	}  

                            $("#result4").html(codata4);

                            var codata5="";
                            for (var c5= 27; c5 < 35; c5++) {		  		            	            	
	  		            	            	if(coverimage[c5]!=undefined)
                        	 codata5+="<div class=\"coverimage\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c5]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c5]+"</span></div>";  
		            	            	} 
                            $("#result5").html(codata5);
                            var codata6="";
                            for (var c6= 35; c6 < 42; c6++) {		  		            	            	
	  		            	            	if(coverimage[c6]!=undefined)
                        	 codata6+="<div class=\"coverimage\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c6]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c6]+"</span></div>";  
		            	            	} 
                            $("#result6").html(codata6);
                            var codata7="";
                            for (var c7= 42; c7 < 49; c7++) {		  		            	            	
	  		            	            	if(coverimage[c7]!=undefined)
                        	 codata7+="<div class=\"coverimage\"><a href=\"#x\"><img src=\"https://my-review-cert.hrw.com"+coverimage[c7]+"\" alt=\"Image\" class=\"img-responsive\" ></a><span class=\"teacherLable\">"+teacherLabel[c7]+"</span></div>";  
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

<sec:ifNotLoggedIn>
		Please <g:link controller='login' action='auth'><b>login</b></g:link> to deploy content
</sec:ifNotLoggedIn>


<section id="list-bundle" class="first">
<div>
<g:form>
	<table class="table table-bordered margin-top-medium">
		<thead>
			<tr>
			
				<g:sortableColumn property="id" title="${message(code: 'program.id.label', default:'#')}" />
			<th>Preview</th>
				<g:sortableColumn property="isbn" title="${message(code: 'bundle.isbn.label', default: 'Isbn')}" />
				
				<th>${'Current Revision'}</th>
			
				<g:sortableColumn property="title" title="${message(code: 'bundle.title.label', default: 'Title')}" />
			
				<g:sortableColumn property="duration" title="${message(code: 'bundle.duration.label', default: 'Duration')}" />
				<g:sortableColumn property="program" title="${message(code: 'bundle.program.label', default: 'Program')}" />
				
												
				
				<g:render template="/_common/templates/headerEnv"/>
			
			</tr>
		</thead>
		<tbody>
		<g:set var="jobdetails" bean="deploymentService"/>
		<g:set var="userdetail" bean="utilityService"/>
		<g:each in="${bundleInstanceList}" status="i" var="bundleInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<% devLog = "${grails.util.Holders.config.cacheLocation}"+"/Bundles"+"/${bundleInstance.isbn}"+"/dev/log/"+"${bundleInstance.isbn}"+"-dev_log"+".log"
			   File devLogFile = new File(devLog)
			   qaLog = "${grails.util.Holders.config.cacheLocation}"+"/Bundles"+"/${bundleInstance.isbn}"+"/review/log/"+"${bundleInstance.isbn}"+"-review_log"+".log"
			   File qaLogFile = new File(qaLog)
			   prodLog = "${grails.util.Holders.config.cacheLocation}"+"/Bundles"+"/${bundleInstance.isbn}"+"/prod/log/"+"${bundleInstance.isbn}"+"-prod_log"+".log"
			   File prodLogFile = new File(prodLog) %>
				
				
					
				
				<td><sec:ifAnyGranted roles="ROLE_PM, ROLE_QA, ROLE_PROD"><input type="radio" name="rad" id="rad${i}" value="${bundleInstance.id+"/"+jobdetails.getCurrentEnversRevision(bundleInstance)+"/"+jobdetails.getPromotionDetails(bundleInstance,jobdetails.getUserEnvironmentInformation())+"/false/false"}" onclick="toggle(this,'row${i}')"/>
				<%-- Confirm dialog for Deploy/Promote  --%>
		<g:render template="/_common/modals/confirmDialog"/>
				
				</sec:ifAnyGranted>
				<g:link action="show" id="${bundleInstance.id}">${bundleInstance.id}</g:link> </td>
			    <td>
				<g:if test="${jobdetails.isDashboardSecureProgram(bundleInstance.id)==true}">	
				<a href="#scrollModal" class="previewlink" onclick="return preview(${bundleInstance.id},'${bundleInstance.title}');" >Preview</a>

					<g:render template="/_common/modals/scrollDialog"   />



				</g:if>
				</td>
				<td><g:link action="show" id="${bundleInstance.id}">${fieldValue(bean: bundleInstance, field: "isbn")}</g:link> 
				
				
				<td>Revision: ${jobdetails.getCurrentEnversRevision(bundleInstance)}<br>Last updated by: ${userdetail.getLastUpdatedUserNameForBundle(jobdetails.getCurrentEnversRevision(bundleInstance))}</td>
			
				<td>${fieldValue(bean: bundleInstance, field: "title")}</td>
			
				<td>${fieldValue(bean: bundleInstance, field: "duration")}</td>
				<td>${fieldValue(bean: bundleInstance, field: "program")}</td>
									
				
				<g:set var="jobdetail" value="${jobdetails.getPromotionDetails(bundleInstance,1)}" />
						
				<td>	
				<g:if test="${jobdetail[0]!=null && jobdetail[1]!=null && jobdetail[2]!=null && jobdetail[3]!=null}">			
					Job: ${jobdetail[0]}  
				<br>
					Status: ${jobdetail[1]} 
				<br>
					Revision: ${jobdetail[2]} 
				<br>
					User: ${jobdetail[3]} 
				<br>	</g:if>
				<br>
											<g:if test="${devLogFile.exists()}">
												<a href='./download?logFile=<%=devLog%>'>Log File</a>
											</g:if>
											<g:else>
				 
				</g:else>									
				</td>
				
				<g:set var="jobdetailQa" value="${jobdetails.getPromotionDetails(bundleInstance,2)}" />
				
				<td><g:if test="${jobdetailQa[0]!=null && jobdetailQa[1]!=null && jobdetailQa[2]!=null && jobdetailQa[3]!=null}">		
				Job: ${jobdetailQa[0]}
				<br>
				Status: ${jobdetailQa[1]}
				<br>
				Revision: ${jobdetailQa[2]}
				<br>
				User: ${jobdetailQa[3]}
				<br></g:if>
				<br>
											<g:if test="${qaLogFile.exists()}">
												<a href='./download?logFile=<%=qaLog%>'>Log File</a>
											</g:if>
											<g:else>
				 
				</g:else>
				</td>
				
				<g:set var="jobdetailprod" value="${jobdetails.getPromotionDetails(bundleInstance,3)}" />
				
				<td><g:if test="${jobdetailprod[0]!=null && jobdetailprod[1]!=null && jobdetailprod[2]!=null && jobdetailprod[3]!=null}">
				Job: ${jobdetailprod[0]}
				<br>
				Status: ${jobdetailprod[1]}
				<br>
				Revision: ${jobdetailprod[2]}
				<br>
				User: ${jobdetailprod[3]}
				<br></g:if>
				<br>
											<g:if test="${prodLogFile.exists()}">
												<a href='./download?logFile=<%=prodLog%>'>Log File</a>
											</g:if>
											<g:else>
				
				</g:else>
				</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<g:if test="${jobdetails.getUserEnvironmentInformation()==1}">
	<sec:ifAnyGranted roles="ROLE_PM">
	<g:actionSubmit style="color: #ffffff;background-color: #428bca;border-color: #357ebd;margin-right: 10px;margin-top: 10px;margin-bottom: 10px;" value="Deploy" onClick="return deploy()"/>
	</sec:ifAnyGranted>
	</g:if>
	<g:if test="${jobdetails.getUserEnvironmentInformation()==2 || jobdetails.getUserEnvironmentInformation()==3}">
	<sec:ifAnyGranted roles="ROLE_QA, ROLE_PROD">
	<g:actionSubmit style="color: #ffffff;background-color: #428bca;border-color: #357ebd;margin-right: 10px;margin-top: 10px;margin-bottom: 10px;" value="Promote" onClick="return promote()"/>
	</sec:ifAnyGranted>
	</g:if>
	<%-- Required to pass to JavaScript --%>
	<g:hiddenField name="instanceDetail"/>
	<g:hiddenField name="instanceToBePromoted"/>
		
</g:form>
</div>
	<div>
		<bs:paginate total="${bundleInstanceCount}" />
	</div>
</section>

</body>

</html>
