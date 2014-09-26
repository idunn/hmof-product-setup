<%@ page defaultCodec="none" %>
<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="grails.plugin.searchable.internal.lucene.LuceneUtils" %>
<%@ page import="grails.plugin.searchable.internal.util.StringQueryUtils" %>

<html>
  <head>
   
    <meta name="layout" content="kickstart" />
    <title>Product Search</title>
   <link rel="stylesheet"  href="${resource(dir:'css',file:'search.css')}"/>
    <script type="text/javascript">
        var focusQueryInput = function() {
            document.getElementById("q").focus();
        }
    </script>
  </head>
  <body onload="focusQueryInput();">
  

    
  <div id="main">
    <g:set var="haveQuery" value="${params.q?.trim()}" />
    <g:set var="haveResults" value="${searchResult?.results}" />
    <div class="pagination">
      <span>
        <g:if test="${haveQuery && haveResults}">
          Showing <strong>${searchResult.offset + 1}</strong> - <strong>${searchResult.results.size() + searchResult.offset}</strong> of <strong>${searchResult.total}</strong>
         
          <g:if test="${haveResults}">
              Page:
              <g:set var="totalPages" value="${Math.ceil(searchResult.total / searchResult.max)}" />
              <g:if test="${totalPages == 1}"><span class="currentStep">1</span></g:if>
              <g:else><g:paginate controller="search" action="search" params="[q: params.q]" total="${searchResult.total}" prev="&lt; previous&nbsp;" next="&nbsp;next &gt;" /></g:else>
          </g:if>
       
        </g:if>
        <g:else>
        &nbsp;
        </g:else>
      </span>
    </div>

    <g:if test="${haveQuery && !haveResults && !parseException}">
      <p>Nothing matched your query - <strong>${params.q}</strong></p>
    </g:if>

  

    <g:if test="${parseException}">
      <p>Your query - <strong>${params.q}</strong> - is not valid.</p>
      <p>Suggestions:</p>
      <ul>
        <li>Fix the query: see <a href="http://lucene.apache.org/java/docs/queryparsersyntax.html">Lucene query syntax</a> for examples</li>
        <g:if test="${LuceneUtils.queryHasSpecialCharacters(params.q)}">
          <li>Remove special characters like <strong>" - [ ]</strong>, before searching, eg, <em><strong>${LuceneUtils.cleanQuery(params.q)}</strong></em><br />
              <em>Use the Searchable Plugin's <strong>LuceneUtils#cleanQuery</strong> helper method for this: <g:link controller="search" action="search" params="[q: LuceneUtils.cleanQuery(params.q)]">Search again with special characters removed</g:link></em>
          </li>
          <li>Escape special characters like <strong>" - [ ]</strong> with <strong>\</strong>, eg, <em><strong>${LuceneUtils.escapeQuery(params.q)}</strong></em><br />
              <em>Use the Searchable Plugin's <strong>LuceneUtils#escapeQuery</strong> helper method for this: <g:link controller="searchable" action="index" params="[q: LuceneUtils.escapeQuery(params.q)]">Search again with special characters escaped</g:link></em><br />
              <em>Or use the Searchable Plugin's <strong>escape</strong> option: <g:link controller="search" action="search" params="[q: params.q, escape: true]">Search again with the <strong>escape</strong> option enabled</g:link></em>
          </li>
        </g:if>
      </ul>
    </g:if>

    <g:if test="${haveResults}">
      <div class="border: 2px solid;border-radius: 25px;">
        <g:each var="result" in="${searchResult.results}" status="index">
          <div class="result">
            <g:set var="className" value="${ClassUtils.getShortName(result.getClass())}" />
            <g:set var="link" value="${createLink(controller: className[0].toLowerCase() + className[1..-1], action: 'show', id: result.id)}" />
             
          
            
            <div class="stucomment">${className} #${result.id} 
                        
            <a href="${link}">
          
            <g:if test="${className.equalsIgnoreCase("Program")}">
            
            ${result.name}
            </g:if>
            <g:if test="${className.equalsIgnoreCase("Bundle")}">
           ${result.title} 
            </g:if>
            <g:if test="${className.equalsIgnoreCase("CommerceObject")}">
             ${result.objectName} 
            </g:if>
              <g:if test="${className.equalsIgnoreCase("SecureProgram")}">
             ${result.productName} 
            </g:if>
            
            </a></div>  
                                         
                  <g:if test="${className.equalsIgnoreCase("Bundle")}">
                  <g:if test="${result.secureProgram}">
                
<div class="divborder-search">
                         <div class="divul-search">Associated Secure Programs</div>  
                       <ul style="list-style-type:square;">                       
                     
                        	<g:each in="${result.secureProgram}" status="i" var="b">
						 			 <li>
										<g:link controller="secureProgram" action="show"
												id="${b.id}">
											<g:if test="${raw(searchResult.highlights[index])}">
            <span style="color:red">${raw(searchResult.highlights[index])}</span>
            </g:if> 
            <g:else>
            <b>${b.registrationIsbn}</b> 
            </g:else>: ${b?.encodeAsHTML()}
											</g:link>
																 
										<br/>
										</li>
							<g:if test="${b.commerceObjects}">
						      <div class="divli-search">Associated Commerce Objects</div>	
						      <ul>						   					    
						      <g:each in="${b.commerceObjects}" status="i1" var="c">

									<li><g:link controller="CommerceObject" action="show"
												id="${c.id}">
											<g:if test="${raw(searchResult.highlights[index])}">
             <span style="color:red">${raw(searchResult.highlights[index])}</span>
            </g:if> 
            <g:else>
             <b>${c.isbn}</b>
            </g:else> : ${c?.encodeAsHTML()}
											</g:link></li>									
								
								</g:each>
                             </ul>
						     </g:if>
						    </g:each>
						   						     
                         </ul>	
                         </div>
						 </g:if> 
						 </g:if>
					
				<g:if test="${className.equalsIgnoreCase("SecureProgram")}">
                                                  
							<g:if test="${result.commerceObjects}">
							<div  class="divborder-search">
						     <div class="divli-search">Associated Commerce Objects</div>
						      <ul>						   					    
						      <g:each in="${result.commerceObjects}" status="i1" var="c">

									<li><g:link controller="CommerceObject" action="show"
												id="${c.id}">
						<g:if test="${raw(searchResult.highlights[index])}">
            <span style="color:red">${raw(searchResult.highlights[index])}</span>
            </g:if> 
            <g:else>
             <b>${c.isbn}</b>
            </g:else> : ${c?.encodeAsHTML()}
											</g:link></li>									
								
								</g:each>
                             </ul>
                             </div>
					</g:if>
					</g:if>
					
					
				<g:if test="${className.equalsIgnoreCase("Program")}">				
                  <g:if test="${result.bundles}">
                  <div class="divborder-search">
                   <div class="divli-search"> Bundles</div>
                       <ul>
                     
                        	<g:each in="${result.bundles}" status="i" var="b">
						 			 <li>
										<g:link controller="Bundle" action="show"
												id="${b.id}">
												<g:if test="${raw(searchResult.highlights[index])}">
             <span style="color:red">${raw(searchResult.highlights[index])}</span>
            </g:if> 
            <g:else>
             ${b?.encodeAsHTML()}
            </g:else>
											
											</g:link>
																 
										<br/>
										</li>
							
						    </g:each>
						   						     
                         </ul>	
                         </div>
						 </g:if> 
					</g:if>
					
					
					
					<%--
						   
						 
						 
					 <g:if test="${className.equalsIgnoreCase("SecureProgram")}">
                         <g:if test="${result.commerceObjects}">
                  			<table  style="width:400px;" align="center" class="table table-bordered margin-top-medium">
							<thead>
								<tr>
									<th>
										Commerce Objects
									</th>
									
								</tr>
							</thead>
							<tbody>
								<g:each in="${result.commerceObjects}" status="i1" var="c">

									<tr class="${(i1 % 2) == 0 ? 'odd' : 'even'}">
										<td><g:link controller="CommerceObject" action="show"
												id="${c.id}">
											${c.isbn} : ${c?.encodeAsHTML()}
											</g:link></td>										
									</tr>
								</g:each>

							</tbody>
						</table> 
						 </g:if> 
						 </g:if>
						 
						 
						 
						 
						 --%><%--
                  
             <g:if test="${className.equalsIgnoreCase("Bundle")}">
                 
                <g:if test="${result.secureProgram}">
                    <div class="stucomment">secureProgram</div>
                    <div class="comments">
                       <g:each var="result1" in="${result.secureProgram}"  >
           
           
           <g:set var="link1" value="${createLink(controller: 'SecureProgram', action: 'show', id: result1.id)}" />
            <div class="contentname"><a href="${link1}">${result1.productName}</a></div>
             <g:if test="${raw(searchResult.highlights[index])}">
             <div class="displayLink">${raw(searchResult.highlights[index])}</div>
            </g:if> 
            <g:else>
            <div class="displayLink">${result1.registrationIsbn}</div>
            </g:else>
            
              </g:each>
               </div>
           </g:if> 
           </g:if> 
           
           
          --%></div>
        </g:each>
      </div>

    </g:if>
    
  </div>
  </body>
</html>