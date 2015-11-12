<%@ page import="hmof.programxml.ProgramXML" %>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.multiselect.css')}"
	type="text/css">

<script src="${resource(dir:'js',file:'jquery.localisation-min.js')}"></script>
	<script src="${resource(dir:'js',file:'jquery.scrollTo-min.js')}"></script>
	<script src="${resource(dir:'js',file:'ui.multiselect.js')}"></script>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'tcps.css')}"
	type="text/css">
<script type="text/javascript">

 function updateBUID(){

  var title = document.getElementById("title").value;
  var str = title.toUpperCase(); 
  while (str.indexOf(' ') !== -1)
  {
	  str = str.replace(' ', '_');
  }
	
	 document.getElementById("buid").value=str;
	 var buid=document.getElementById("buid").value;
	 document.getElementById("filename").value="hmof_program_"+buid+".xml";
	 
 }
</script>
<div class="span7">
	<section>
<div class="control-group fieldcontain ${hasErrors(bean: programXMLInstance, field: 'title', 'error')} required">
	<label for="title" class="control-label col-sw-1">
		<g:message code="programXML.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
	<g:textField class="form-control" name="title" required="" value="${programXMLInstance?.title}"  onchange="return updateBUID();"/>
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: programXMLInstance, field: 'buid', 'error')} required">
	<label for="buid" class="control-label col-sw-1">
		<g:message code="programXML.buid.label" default="Buid" />
		<span class="required-indicator">*</span>
	</label><div class="controls">
	<g:textField class="form-control" name="buid" required="" value="${programXMLInstance?.buid}"  pattern="${programXMLInstance.constraints.buid.matches}"/>
</div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: programXMLInstance, field: 'language', 'error')} ">
	<label for="language" class="control-label col-sw-1">
		<g:message code="programXML.language.label" default="Language" />
		
	</label><div class="controls">
	<g:select class="form-control"  name="language" from="${programXMLInstance.constraints.language.inList}" value="${programXMLInstance?.language}" valueMessagePrefix="programXML.language" />

</div></div>

<div class="control-group fieldcontain ${hasErrors(bean: programXMLInstance, field: 'filename', 'error')} required">
	<label for="filename" class="control-label col-sw-1">
		<g:message code="programXML.filename.label" default="Filename" />
		<span class="required-indicator">*</span>
	</label><div class="controls">
	<g:textField class="form-control" name="filename" required="" value="${programXMLInstance?.filename}"  pattern="${programXMLInstance.constraints.filename.matches}"/>
</div>
</div>

<div
			class="control-group fieldcontain ${hasErrors(bean: programXMLInstance, field: 'secureProgram', 'error-field')} ">
			<label for="secureProgram" class="control-label col-sw-1"><g:message
					code="programXML.secureProgram.label" default="Secure Program" /></label>
					<br>
			<div class="controls">
				<g:select class="multiselect" style="width:80%;" name="secureProgram"  
					from="${securePrograms.list().sort()}" 
					multiple="multiple" optionKey="id" size="10"
					value="${programXMLInstance?.secureProgram*.id}" data-toggle="tooltip" data-placement="right" data-container="body" title="A programXMLInstance can only be deployed when it is associated with a Secure Program"   />
				
			</div>
		</div>
</section>
</div>
<script type="text/javascript">
   $(function () { $("[data-toggle='tooltip']").tooltip(); });
      
 $("#secureProgram").multiselect().multiselectfilter();

</script>
