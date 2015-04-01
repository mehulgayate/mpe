<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored ="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WSM</title>
<meta name="keywords" content="secured theme, free template, templatemo, red layout" />
<meta name="description" content="Secured Theme is provided by templatemo.com" />
<link href="/mpe/static/templatemo_style.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="/mpe/static/style.css" />
<script type="text/javascript" src="/mpe/static/js/jquery.min.js" ></script>
<script type="text/javascript" src="/mpe/static/js/jquery-ui.min.js" ></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#featured > ul").tabs({fx:{opacity: "toggle"}}).tabs("rotate", 5000, true);
	});
</script>

<link rel="stylesheet" type="text/css" href="/mpe/static/css/ddsmoothmenu.css" />

<script type="text/javascript" src="/mpe/static/js/ddsmoothmenu.js">

/***********************************************
* Smooth Navigational Menu- (c) Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

</script>

<script type="text/javascript">

ddsmoothmenu.init({
	mainmenuid: "templatemo_menu", //menu DIV id
	orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'ddsmoothmenu', //class added to menu's outer DIV
	//customtheme: ["#1c5a80", "#18374a"],
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})

function clearText(field)
{
    if (field.defaultValue == field.value) field.value = '';
    else if (field.value == '') field.value = field.defaultValue;
}

</script>

<link rel="stylesheet" href="/mpe/static/css/slimbox2.css" type="text/css" media="screen" /> 
<script type="text/JavaScript" src="/mpe/static/js/slimbox2.js"></script> 


</head>
<body>

<div id="templatemo_wrapper">
	<div id="templatemo_header">
    	<div id="site_title"><a rel="nofollow" href="">WSM (XML Mining)</a></div>
        
    </div> <!-- END of header -->


         <div id="templatemo_menu" class="ddsmoothmenu">
            <ul>
                <li><a href="/mpe">Home</a></li>
                <li><a href="" class="selected">Upload</a>
                    <ul>
                        <li><a href="/mpe/upload-new">Upload New file</a></li>                                                                                             
                  </ul>
                </li>   
               <li><a href="/mpe/user-files">User Uploads</a></li>                            
            </ul>
            <br style="clear: left" />
        </div> <!-- end of templatemo_menu -->
        
    <div id="templatemo_main">
   
    	<div class="content_wrapper content_mb_30">
    	 <form action="/mpe/edit-file" method="post">
        	File Type : ${fileType }
        	<input type="hidden" value="${fileType}" name="type"/>
        	<br/>
        	<br/>
        	<br/>
        	<hr/>
        	<br/>
        	<br/>
        	Input File
        	<br/>
        	<strong>
        	<textarea rows="30" cols="70" name="code">${code}</textarea>
       		<input type="submit" value="Process"/>
       		
        	</strong>
        	</form> 
        	<br/>
        	<hr/>
        	<br/>
        	Compile Output Is
        	<br/>
        	<strong>
        	${compileOutput}
        	</strong>
        	<br/>
        	<hr/>
        	<br/>
        	Run Output Is
        	<br/>
        	<strong>
        	${runOutput}
        	</strong> 
        	
        	<br/>
        	<hr/>
        	<br/>
        	<a href="/mpe/download-file?type=${download}">Download Compiled File</a>
        </div>
    	<div class="content_wrapper">
            
        </div>
        <div class="clear"></div>
    </div>

	<div id="templatemo_footer">
    	
        <div class="clear"></div>
    </div> <!-- END of templatemo_footer -->
</div> <!-- END of templatemo_wrapper -->
<div id="templatemo_copyright_wrapper">
    <div id="templatemo_copyright">
        Developed By <a href="#"></a> 
    </div>
</div>
</body>
</html>