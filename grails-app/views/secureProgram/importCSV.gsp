<%@ page import="hmof.SecureProgram"%>
<%@ page defaultCodec="none"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="kickstart">
<g:set var="entityName"
	value="${message(code: 'importCSV.label', default: 'ImportCSV')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'search.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
<script>
	function myFunction() {
		var fileName = document.getElementById("CSVfiledata").value;
		document.getElementById('file_info').value = fileName
	}
</script>
</head>
<body>
	<div class="row">
		<div class="widget stacked ">
			<div class="widget-content">
			<g:set var="counter" value="${0}"/>
				<g:if test="${parseFileAndPersistData}">
			
				
					<div class="alert alert-danger">

						<g:each in="${parseFileAndPersistData}" var="item">
						
				<g:if test="${(item).contains("Object Name :")}">
				
					<g:set var="counter" value="${counter + 1}"/>
				</g:if>
							${item}	</br>

						</g:each>
<div>
				<u><b>Total Falied Secure Programs : ${counter }</b></u>
				</div>
					</div>
					
				</g:if>
				
				
				<div class="span12">
					<section>

						<g:uploadForm action="importFile">

							<div class="col-lg-8 col-sm-6 col-12">
								<h4>Import Secure Programs CSV file</h4>
								<div class="input-group">
									<span class="input-group-btn"> <span
										class="btn btn-primary btn-file"> Browse&hellip; <input
											type="file" id="CSVfiledata" name="CSVfiledata"
											onchange="myFunction();"/>
									</span>
									</span> <input type="text" class="form-control" id="file_info" />

								</div>
								<span class="help-block">
									<dl>
  										<ul> 
											  <li>Database Export:
												    <ul>
												    <li>Export the Secure Programs from the HMOF Production DB</li>
												    <li>Export to CSV without a header row</li>
												    </ul>
											  </li>											
											 <li>Before Import:
												    <ul>    
												    <li>Manually Convert Copyright symbols '©' to its HTML Entity Number: &amp;#169;</li>
												    </ul>
											  </li>
											
											 <li>After Import:
												    <ul>
												    <li>Set the Platform Commerce Objects, e.g. turn off Dashboard for legacy products</li>
												    <li>If product is Knewton then enable the Knewton checkbox and enter the non-production id fields</li>
												    </ul>
											  </li>										  
										</ul>
								</span> <span> <g:submitButton name="importFile"
										class="btn btn-primary" value="Upload" />
								</span>
							</div>

						</g:uploadForm>

					</section>
				</div>
			</div>
		</div>
	</div>

</body>
</html>

