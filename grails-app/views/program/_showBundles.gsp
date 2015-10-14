<div class="list">
   <table class="table table-bordered margin-top-medium">
        <thead>
        <tr>

           <th class="widget-header2" style="color:#bbb">
										${'List'}
									</th>
									
									<th class="widget-header2" style="color:#bbb">
										${'Edit'}
									</th>
									<th class="widget-header2" style="color:#bbb">
										${'Preview'}
									</th>
									<th class="widget-header2" style="color:#bbb">
										SAP<br>Status
									</th>
									<th class="widget-header2" style="color:#bbb" >
										SAP<br>Internal Description
									</th><th class="widget-header2" style="color:#bbb">
										SAP<br>Material Group
									</th><th class="widget-header2" style="color:#bbb">
										SAP<br>eGoods Indicator
									</th>
        </tr>
        </thead>
        <tbody>
        <g:if test="${bundleInstance}">
       <g:each in="${bundleInstance}" status="i" var="b">
							       
									<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
										<td><g:link controller="bundle" action="show"
												id="${b.id}">
												${b?.encodeAsHTML()}
											</g:link> </td>
									
										<td><g:link controller="bundle" action="edit"
												id="${b.id}">
												${"Edit"}
											</g:link></td>									
												<td><g:if test="${jobdetails.isDashboardSecureProgram(b.id)==true}">	
				
<a href="#"  class="previewlink"  onclick="return teacherPreview(${b.id},'${b.title}');"  >Teacher</a>|&nbsp;<a href="#"  class="previewlink"  onclick="return studentPreview(${b.id},'${b.title}');"  >Student</a>
				
				</g:if></td>						
											<g:each in="${sapResultsList}" status="j" var="c">										
											<g:if test="${c.isbn!=null &&  (c.isbn).equals(b.isbn) }"> 											
											<td>${c.status}</td><td>${c.internalDescription}</td><td><% def materialGroup = c.materialGroup

											def subtitle =""
											if(materialGroup!=null)
											 subtitle =  materialGroup.split('MATERIAL_GROUP_')[-1].replace("ON_LINE","Online")?:materialGroup
 %> ${subtitle}</td><td>${c.eGoodsIndicator}</td>
										    </g:if>
										   
											</g:each>											
											
											 
											
											
									</tr>
									
								</g:each>
        </g:if>
								<g:else>
								<tr>
										<td colspan="4" align="center" >
<div class="alert alert-danger">No Bundles Associated</div>

                                   </td>
										</tr>
								
								</g:else>
        </tbody>
    </table>
</div>

<div class="paginateButtons">


            
            <bs:paginate  total="${bundleCount}" id="${programInstance.id }"/>
</div>
