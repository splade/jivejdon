<%@ page contentType="text/css; charset=UTF-8" %>

<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>
table,td,select,input,div{font-family:Verdana;font-size:12px;}body{font-family:Verdana;font-size:12px;height:auto;margin-top:2px;}a img{border:0;}A{text-decoration:none;color:#036;}a:visited{text-decoration:none;color:#102941;}A:hover{text-decoration:underline;color:#eab30d;}form,textarea{font-size:12px;COLOR:#000;}input{BORDER-RIGHT:#668235 1px solid;BORDER-TOP:#668235 1px solid;BORDER-LEFT:#668235 1px solid;BORDER-BOTTOM:#668235 1px solid;BACKGROUND-COLOR:#fafbf5;font-size:12px;}textarea{BORDER-RIGHT:#668235 1px solid;BORDER-TOP:#668235 1px solid;BORDER-LEFT:#668235 1px solid;BORDER-BOTTOM:#668235 1px solid;BACKGROUND-COLOR:#fafbf5;font-size:12px;}select{BORDER-RIGHT:#668235 1px solid;BORDER-TOP:#668235 1px solid;BORDER-LEFT:#668235 1px solid;BORDER-BOTTOM:#668235 1px solid;BACKGROUND-COLOR:#fafbf5;font-size:12px;}ul{FONT-SIZE:14px;COLOR:#000;list-style-type:square;}li{FONT-SIZE:14px;COLOR:#000;}ol{FONT-SIZE:14px;COLOR:#000;list-style-type:decimal;line-height:150%;font-style:normal;font-weight:normal;}.small{font-size:12px;font-family:Tahoma,Verdana;}.smallgray{font-size:12px;COLOR:#B87A7A;font-family:微软雅黑, 宋体;}.blackgray{font-size:12px;COLOR:#bbb;font-family:Tahoma,Verdana;}.small11{font-size:11px;font-family:Arial,Tahoma;}h4{font-size:14px;}h3{font-size:16px;}h2{font-size:20px;}.big16{font-size:16px;}.big18{font-size:18px;}.bige20{font-size:20px;}.body_href{font-size:14px;text-decoration:none;color:#284b78;font-weight:bold;}.white{color:#fff;}.bodystyle{FONT-SIZE:14px;}
A.a03{font-family: "微软雅黑", "宋体"; text-decoration:none;color:#fff;font-size:12px;}
.title{font-size:14px;font-weight:bold;line-height:144%;color:#093;}.article{font-size:14px;line-height:150%;color:#212121;}.tpc_content{font-size:14px;font-weight:normal;color:#222;line-height:150%;}.home_content{font-size:13px;color:#333;line-height:150%;}.gen-2{clear:both;}form{margin:0;}form.search,form.login{margin:8px 8px 8px 8px;}form.search input,form.login input{padding-left:4px;margin:1px 1px 1px 1px;BORDER-RIGHT:#668235 1px solid;BORDER-TOP:#668235 1px solid;BORDER-LEFT:#668235 1px solid;BORDER-BOTTOM:#668235 1px solid;BACKGROUND-COLOR:#fafbf5;}form.search input.focus,form.login input.focus{margin:0;}form.search input{background:#54728b;}form.login input{background:#54728b;}form.search button,form.login button{padding:0;}form.search input,form.login input{color:#fff;border:1px solid black;}pre{white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;}DIV.pagination{padding:3px;margin:3px;font-size:10px;font-weight:bold;font-family:Arial,Times;}DIV.pagination a{padding:2px 5px 2px 5px;margin-right:2px;border:1px solid #9aafe5;text-decoration:none;color:#2e6ab1;}DIV.pagination a:hover,.pagination a:active{border:1px solid #dd6900;color:#000;background-color:lightyellow;text-decoration:none;}DIV.pagination .current{padding:2px 5px 2px 5px;margin-right:2px;border:1px solid navy;font-weight:bold;background-color:#2e6ab1;color:#FFF;}DIV.pagination .disabled{padding:2px 5px 2px 5px;margin-right:2px;border:1px solid #929292;color:#929292;}
DIV.tres{PADDING-RIGHT:3px;PADDING-LEFT:3px;FONT-SIZE:11;PADDING-BOTTOM:3px;MARGIN:3px;PADDING-TOP:3px;FONT-FAMILY:Arial,Helvetica,sans-serif;COLOR:olive;}DIV.tres A{BORDER-RIGHT:#d9d300 2px solid;PADDING-RIGHT:3px;BORDER-TOP:#d9d300 2px solid;PADDING-LEFT:3px;PADDING-BOTTOM:2px;MARGIN:2px;BORDER-LEFT:#d9d300 2px solid;COLOR:#fff;PADDING-TOP:2px;BORDER-BOTTOM:#d9d300 2px solid;BACKGROUND-COLOR:#b08c0f;TEXT-DECORATION:none;}
DIV.tres A:hover{BORDER-RIGHT:#ff0 2px solid;BORDER-TOP:#ff0 2px solid;BORDER-LEFT:#ff0 2px solid;COLOR:#000;BORDER-BOTTOM:#ff0 2px solid;BACKGROUND-COLOR:#ff0;}
DIV.tres A:active{BORDER-RIGHT:#ff0 2px solid;BORDER-TOP:#ff0 2px solid;BORDER-LEFT:#ff0 2px solid;COLOR:#000;BORDER-BOTTOM:#ff0 2px solid;BACKGROUND-COLOR:#ff0;}
DIV.tres SPAN.current{BORDER-RIGHT:#fff 2px solid;PADDING-RIGHT:5px;BORDER-TOP:#fff 2px solid;PADDING-LEFT:5px;FONT-WEIGHT:bold;PADDING-BOTTOM:2px;MARGIN:2px;BORDER-LEFT:#fff 2px solid;COLOR:#000;PADDING-TOP:2px;BORDER-BOTTOM:#fff 2px solid;}DIV.tres SPAN.disabled{DISPLAY:none;}.tooltip_content{FONT-SIZE:14px;overflow:hidden;MARGIN:10px;PADDING-RIGHT:3px;PADDING-LEFT:3px;PADDING-BOTTOM:3px;PADDING-TOP:3px;}.post-tag{color:#3e6d8e;background-color:#e0eaf1;border-bottom:1px solid #3e6d8e;border-right:1px solid #7f9fb6;padding:3px 3px 3px 4px;text-decoration:none;white-space:nowrap;line-height:2;}.post-tag:hover{background-color:#3e6d8e;color:#e0eaf1;border-bottom:1px solid #37607d;border-right:1px solid #37607d;text-decoration:none;}.b_content_line{float:none;clear:none;display:block;overflow:hidden;width:658px;text-align:left;border-top:dashed 1px #e7e8ea;height:10px;margin-top:30px;}.quote_title{PADDING-RIGHT:5px;PADDING-LEFT:0;FONT-WEIGHT:bold;PADDING-BOTTOM:5px;MARGIN:5px 0 0 10px;PADDING-TOP:5px;}.quote_body{background:#f4f5f7 3px 3px no-repeat;border:1px dashed #BBB;padding:8px 12px 8px 36px;margin:5px 0;FONT-WEIGHT:lighter;}.displaycode{margin-top:0;margin-bottom:0;font-family:Andale Mono,Lucida Console,Monaco,fixed,monospace;font-size:11px;}.code-outline{background-color:#fff;border:1px solid #ccc;padding:5px 5px 5px 5px;}div.linkblock:hover{background-color:#fff!important;background-repeat:repeat-x;}div.post-headline h1,div.post-headline h2{margin:0;padding:0;}div.post-footer{clear:both;display:block;}div.post-footer{margin:0;padding:5px;background:#eee;color:#666;line-height:18px;}div.post-footer a:link,div.post-footer a:visited,div.post-footer a:active{color:#333;font-weight:normal;text-decoration:none;}div.post-footer a:hover{color:#333;font-weight:normal;text-decoration:underline;}.overlay_dialog{background-color:#666;filter:alpha(opacity=60);-moz-opacity:.6;opacity:.6;}.overlay___invisible__{background-color:#666;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;}.dialog_nw{width:9px;height:23px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/top_left.gif) no-repeat 0 0;}.dialog_n{background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/top_mid.gif) repeat-x 0 0;height:23px;}.dialog_ne{width:9px;height:23px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/top_right.gif) no-repeat 0 0;}.dialog_e{width:2px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/center_right.gif) repeat-y 0 0;}.dialog_w{width:2px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/center_left.gif) repeat-y 0 0;}.dialog_sw{width:9px;height:19px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/bottom_left.gif) no-repeat 0 0;}.dialog_s{background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/bottom_mid.gif) repeat-x 0 0;height:19px;}.dialog_se{width:9px;height:19px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/bottom_right.gif) no-repeat 0 0;}.dialog_sizer{width:9px;height:19px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/sizer.gif) no-repeat 0 0;cursor:se-resize;}.dialog_close{width:14px;height:14px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/close.gif) no-repeat 0 0;position:absolute;top:5px;left:8px;cursor:pointer;z-index:2000;}.dialog_minimize{width:14px;height:15px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/minimize.gif) no-repeat 0 0;position:absolute;top:5px;left:28px;cursor:pointer;z-index:2000;}.dialog_maximize{width:14px;height:15px;background:transparent url(<%=request.getContextPath()%>/common/js/styles/default/maximize.gif) no-repeat 0 0;position:absolute;top:5px;left:49px;cursor:pointer;z-index:2000;}.dialog_title{float:left;height:14px;font-family:Tahoma,Arial,sans-serif;font-size:12px;text-align:center;width:100%;color:#000;}.dialog_content{overflow:auto;color:#DDD;font-family:Tahoma,Arial,sans-serif;font-size:10px;background-color:#123;}.top_draggable,.bottom_draggable{cursor:move;}.status_bar{font-size:12px;}.status_bar input{font-size:12px;}.wired_frame{display:block;position:absolute;border:1px #000 dashed;}.dialog{display:block;position:absolute;}.dialog table.table_window{border-collapse:collapse;border-spacing:0;width:100%;margin:0;padding:0;}.dialog table.table_window td,.dialog table.table_window th{padding:0;}.dialog .title_window{-moz-user-select:none;}

.userbox{
	background:#888888;
filter:progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#fcfcfc',endColorstr='#D9D9D9');	background:-webkit-gradient(linear,0% 0,0% 100%,from(#fcfcfc),to(#D9D9D9));
	background:-moz-linear-gradient(270deg,#fcfcfc,#D9D9D9);
	border-color:#888888;
	border:1px solid #888888;
	border-bottom-color:#888888;
	-webkit-border-radius:5px;
	-moz-border-radius:5px;
	border-radius:5px;
	-webkit-box-shadow:0 1px 1px rgba(0,0,0,0.2);
	-moz-box-shadow:0 1px 1px rgba(0,0,0,0.2);
	box-shadow:0 1px 1px rgba(0,0,0,0.2);
	height: 45px;
	width: 848px;
}
.userbox a{	
color:#888888;
padding:8px 5px;
}
.site-logo img{float:left;;top:2px;left:0;}
.table-entice{padding:1px 0;text-align:center;color:#666;}
.table-entice p{margin-bottom:0;}
.table-entice img{ 
    float:right;	
	padding:6px 5px;
	 position:static;
  +position:relative;
  top:-20%;left:-1%;
}
.table-entice .tres{
	float:left;
	padding:6px 5px;
	COLOR:#ffffff
}
.table-button{display:inline-block;
height:30px;
width:971px;
color:#ffffff;
text-shadow:-1px -1px 0 rgba(0,0,0,0.25);
font-size:12px;
background:#60A5A8;
filter:progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#D9D9D9',endColorstr='#8C8C8C');background:-webkit-gradient(linear,left top,left bottom,from(#D9D9D9),to(#8C8C8C));
background:-moz-linear-gradient(top,#D9D9D9,#8C8C8C);border:1px solid #B0B0B0;-webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;-webkit-box-shadow:0 1px 4px rgba(0,0,0,0.3);-moz-box-shadow:0 1px 4px rgba(0,0,0,0.3);box-shadow:0 1px 4px rgba(0,0,0,0.3);-webkit-font-smoothing:antialiased;}


DIV.digg-row-left {
	PADDING-RIGHT: 9px; 
	PADDING-LEFT: 9px;  
	PADDING-BOTTOM: 9px; 
	MARGIN: 0px; 
	PADDING-TOP: 0px; 
}
DIV.digg-row-left SPAN {
	PADDING-RIGHT: 10px; 
	PADDING-LEFT: 0px; 
	BACKGROUND: url(<%=request.getContextPath()%>/images/dig.gif) no-repeat; 
	FLOAT: left; 
	PADDING-BOTTOM: 0px; 
	MARGIN: 6px 0px 0px; 
	WIDTH: 54px; 
	PADDING-TOP: 0px; 
	HEIGHT: 60px
}

.diggArea .diggNum {
	MARGIN-TOP: 9px; 
	DISPLAY: block; 
	COLOR: #653636;
	FONT-WEIGHT: lighter; 
	FONT-SIZE: 16px; 
	WIDTH: 54px; 
	FONT-FAMILY: Georgia; 
	HEIGHT: 34px; 
	TEXT-ALIGN: center
}
.diggArea .top8 {
	padding-top:8px;
}

.diggArea .diggLink {
	DISPLAY: block; 
	WIDTH: 54px; 
	COLOR: #999; 
	TEXT-ALIGN: center;
}

<%@ include file="./common/js/styles/default_css.jsp"%>
<%@ include file="./common/js/styles/mac_os_x_css.jsp"%>
<%@ include file="./common/js/styles/alert_css.jsp"%>
<%@ include file="./common/js/styles/autocomplete_css.jsp"%>
