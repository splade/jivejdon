<%@ page contentType="text/css; charset=UTF-8" %>

<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>


table,td,select,input,div {font-family: Verdana;font-size:12px;}
body {
	font-family: Verdana;
	font-size:12px;
	height:auto;
	margin-top: 2px;
}
a img {border:0;}

A {
	text-decoration : none;
	color: #003366;
}

a:visited {
	text-decoration: none;
	color: #102941;
}
A:hover {
        text-decoration : underline;
		color: #EAB30D;
}

form,textarea{
	font-size:12px;
	COLOR: #000000;
	
}

input {
   BORDER-RIGHT: #668235 1px solid;
    BORDER-TOP: #668235 1px solid;
    BORDER-LEFT: #668235 1px solid;
    BORDER-BOTTOM: #668235 1px solid;
    BACKGROUND-COLOR: #FAFBF5;
	font-size:12px;

}

textarea{
    BORDER-RIGHT: #668235 1px solid;
    BORDER-TOP: #668235 1px solid;
    BORDER-LEFT: #668235 1px solid;
    BORDER-BOTTOM: #668235 1px solid;
    BACKGROUND-COLOR: #FAFBF5;
	font-size:12px;

}


select {
    BORDER-RIGHT: #668235 1px solid;
    BORDER-TOP: #668235 1px solid;
    BORDER-LEFT: #668235 1px solid;
    BORDER-BOTTOM: #668235 1px solid;
    BACKGROUND-COLOR: #FAFBF5;
	font-size:12px;

}

ul {
         FONT-SIZE: 14px; COLOR: #000000;  
	list-style-type: square;
	
}

li {
         FONT-SIZE: 14px; COLOR: #000000;  
}

ol {
	FONT-SIZE: 14px;
	COLOR: #000000;

	list-style-type: decimal;
	line-height: 150%;
	font-style: normal;
	font-weight: normal;
}

.small {
	font-size: 12px;
	font-family: Tahoma, Verdana; 
}

.smallgray {
	font-size: 12px;
	COLOR: #653636;
	font-family: Tahoma, Verdana; 
}
.blackgray {
	font-size: 12px;
	COLOR: #bbbbbb;
	font-family: Tahoma, Verdana; 
}

.small11{
	font-size: 11px;
	font-family: Arial, Tahoma; 
}



h4 {
	font-size: 14px;
}

h3 {
	font-size: 16px;
}

h2 {
	font-size: 20px;
}

.big16 {
	font-size: 16px;
}


.big18 {
	font-size: 18px;
}

.bige20 {
	font-size: 20px;


}
.body_href{
    font-size:14px;
	text-decoration:none;
	color:#284B78;
	font-weight: bold;
	
}


.white{
color:#FFFFFF
}

.bodystyle {
	FONT-SIZE: 14px;
}

A.a03:link {text-decoration:none;color:#ffffff;font-size: 12px;}
A.a03:visited {text-decoration:none;color:#ffffff;font-size: 12px;}
A.a03:active {text-decoration:none;color:#ff0000;font-size: 12px;}
A.a03:hover {text-decoration:none;color:#ff0000;font-size: 12px;}

.title { font-size: 14px; font-weight: bold ; line-height: 144%; color: #009933;}
.article { font-size: 14px; line-height: 150%; color: #212121;}
.tpc_content { font-size: 14px; font-weight: normal ;color: #222222; line-height: 150%;}
.home_content { font-size: 13px; color: #333333; line-height: 150%;}


.gen-2 {
	clear: both;
}


form {
	margin: 0px 0px 0px 0px;
}

form.search, form.login {
	margin: 8px 8px 8px 8px;
}

form.search input, form.login input {
	padding-left: 4px;
	margin: 1px 1px 1px 1px;
	 BORDER-RIGHT: #668235 1px solid;
    BORDER-TOP: #668235 1px solid;
    BORDER-LEFT: #668235 1px solid;
    BORDER-BOTTOM: #668235 1px solid;
    BACKGROUND-COLOR: #FAFBF5;
}

form.search input.focus, form.login input.focus {
	margin: 0px 0px 0px 0px;
}

form.search input {
   background:#54728B;
	
}

form.login input{
    background:#54728B;
	
}

form.login input.focus {
}

form.search button, form.login button {
	padding: 0px 0px 0px 0px;
}

form.search input, form.login input {
	color: #fff;
	border: 1px solid black;
	
}

pre {
white-space: pre-wrap; /* css-3 */
white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
white-space: -pre-wrap; /* Opera 4-6 */
white-space: -o-pre-wrap; /* Opera 7 */
word-wrap: break-word; /* Internet Explorer 5.5+ */
 }
 
 
 /*CSS default style pagination*/
DIV.pagination {
	padding:3px;
	margin:3px;
	font-size: 10px;
	font-weight: bold;
	font-family: Arial, Times;
}

DIV.pagination a {
	padding: 2px 5px 2px 5px;
	margin-right: 2px;
	border: 1px solid #9aafe5;
	text-decoration: none; 
	color: #2e6ab1;
}
DIV.pagination a:hover, .pagination a:active {
	border: 1px solid #dd6900;
	color: #000;
	background-color: lightyellow;
	text-decoration: none;
}
DIV.pagination .current {
	padding: 2px 5px 2px 5px;
	margin-right: 2px;
	border: 1px solid navy;
	font-weight: bold;
	background-color: #2e6ab1;
	color: #FFF;
}
DIV.pagination .disabled {
	padding: 2px 5px 2px 5px;
	margin-right: 2px;
	border: 1px solid #929292;
	color: #929292;
}


/*CSS tres style pagination*/

DIV.tres {
	PADDING-RIGHT: 3px; PADDING-LEFT: 3px; FONT-WEIGHT: bold; FONT-SIZE: 12; PADDING-BOTTOM: 3px; MARGIN: 3px; PADDING-TOP: 3px; FONT-FAMILY: Arial, Helvetica, sans-serif;COLOR:olive; 
}
DIV.tres A {
	BORDER-RIGHT: #d9d300 2px solid; PADDING-RIGHT: 3px; BORDER-TOP: #d9d300 2px solid; PADDING-LEFT: 3px; PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #d9d300 2px solid; COLOR: #fff; PADDING-TOP: 2px; BORDER-BOTTOM: #d9d300 2px solid; BACKGROUND-COLOR: #B08C0F; TEXT-DECORATION: none
}
DIV.tres A:hover {
	BORDER-RIGHT: #ff0 2px solid; BORDER-TOP: #ff0 2px solid; BORDER-LEFT: #ff0 2px solid; COLOR: #000; BORDER-BOTTOM: #ff0 2px solid; BACKGROUND-COLOR: #ff0
}
DIV.tres A:active {
	BORDER-RIGHT: #ff0 2px solid; BORDER-TOP: #ff0 2px solid; BORDER-LEFT: #ff0 2px solid; COLOR: #000; BORDER-BOTTOM: #ff0 2px solid; BACKGROUND-COLOR: #ff0
}
DIV.tres SPAN.current {
	BORDER-RIGHT: #fff 2px solid; PADDING-RIGHT: 5px; BORDER-TOP: #fff 2px solid; PADDING-LEFT: 5px; FONT-WEIGHT: bold; PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #fff 2px solid; COLOR: #000; PADDING-TOP: 2px; BORDER-BOTTOM: #fff 2px solid
}
DIV.tres SPAN.disabled {
	DISPLAY: none
}



.tooltip_content{
    	FONT-SIZE: 14px ; 
    	overflow:hidden;
    	MARGIN: 10px;     	      
    	PADDING-RIGHT: 3px; 
    	PADDING-LEFT: 3px; 
      PADDING-BOTTOM: 3px; 
      PADDING-TOP: 3px; 

	  }
.post-tag
{
	color: #3E6D8E;
	background-color: #E0EAF1;
	border-bottom: 1px solid #3E6D8E;
	border-right: 1px solid #7F9FB6;
	padding: 3px 3px 3px 4px;
	text-decoration: none;
	white-space:nowrap;
	line-height: 2;
}

.post-tag:hover
{
	background-color: #3E6D8E;
	color: #E0EAF1;
	border-bottom: 1px solid #37607D;
	border-right: 1px solid #37607D;
	text-decoration: none;
}	  

.b_content_line {float:none;clear:none;display:block;overflow:hidden;width:658px;text-align:left;border-top:dashed 1px #E7E8EA;height:10px;margin-top:30px;}


.quote_title {
	PADDING-RIGHT: 5px; 
	PADDING-LEFT: 0px; 
	FONT-WEIGHT: bold; 
	PADDING-BOTTOM: 5px; 
	MARGIN: 5px 0px 0px 10px; 
	PADDING-TOP: 5px
}
.quote_body{
	background:#F4F5F7  3px 3px no-repeat;
	border:1px dashed #BBB;
	padding:8px 12px 8px 36px;
	margin:5px 0;
	FONT-WEIGHT: lighter;
}


.displaycode {margin-top: 0; margin-bottom: 0; font-family:  Andale Mono, Lucida Console, Monaco, fixed, monospace; font-size: 11px}

.code-outline {background-color:#fff; border: 1px solid #ccc; padding: 5px 5px 5px 5px}


	
/*-------------------- POST HEADLINE ----------------------*/
div.linkblock:hover {background-color: #fff !important;background-repeat: repeat-x;}
div.post-headline {
	/* more  ... */
	}

div.post-headline h1,
div.post-headline h2 {
    margin: 0;
    padding: 0;
    /* more  ... */
    }

div.post-headline h1 a:link, 
div.post-headline h1 a:visited, 
div.post-headline h1 a:active,
div.post-headline h2 a:link, 
div.post-headline h2 a:visited, 
div.post-headline h2 a:active {
    /* more  ... */
    }

div.post-headline h1 a:hover,
div.post-headline h2 a:hover {
    /* more  ... */
    }

	
/*-------------------- POST BYLINE ------------------------*/
	
div.post-byline {
	/* more  ... */
	}

div.post-byline a:link, 
div.post-byline a:visited, 
div.post-byline a:active {
	/* more  ... */
	}

div.post-byline a:hover {
	/* more  ... */
	}		
		
/*-------------------- POST FOOTER ------------------------*/
	
div.post-footer {
	clear:both; 
	display: block;
	/* more  ... */
	}

div.post-footer a:link, 
div.post-footer a:visited, 
div.post-footer a:active {
	/* more  ... */
	}	

div.post-footer a:hover {
	/* more  ... */
	}

div.post-footer {
	margin: 0;
padding: 5px;
background: #eeeeee;
color: #666;
line-height: 18px;	}
 
div.post-footer a:link, 
div.post-footer a:visited, 
div.post-footer a:active {
	color: #333;
font-weight: normal;
text-decoration: none;	}	
 
div.post-footer a:hover {
	color: #333;
font-weight: normal;
text-decoration: underline;	}	


<%@ include file="./common/js/styles/default_css.jsp"%>
<%@ include file="./common/js/styles/mac_os_x_css.jsp"%>
<%@ include file="./common/js/styles/alert_css.jsp"%>
<%@ include file="./common/js/styles/autocomplete_css.jsp"%>
