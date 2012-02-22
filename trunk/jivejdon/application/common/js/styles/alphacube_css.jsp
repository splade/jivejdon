<%
response.setContentType("text/css");
%>
.overlay_alphacube {
	background-color: #85BBEF;
	filter:alpha(opacity=60);
	-moz-opacity: 0.6;
	opacity: 0.6;
}

.alphacube_nw {
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/left-top.gif) no-repeat 0 0;			
  width:10px;
  height:25px;
}

.alphacube_n {
  background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/top-middle.gif) repeat-x 0 0;			
  height:25px;
}

.alphacube_ne {
  background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/right-top.gif) no-repeat 0 0;			
  width:10px;	  
  height:25px;
}

.alphacube_w {
  background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/frame-left.gif) repeat-y top left;			
  width:7px;
}

.alphacube_e {
  background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/frame-right.gif) repeat-y top right;			
  width:7px;	  
}

.alphacube_sw {
  background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/bottom-left-c.gif) no-repeat 0 0;			
  width:7px;
  height:7px;
}

.alphacube_s {
  background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/bottom-middle.gif) repeat-x 0 0;			
  height:7px;
}

.alphacube_se, .alphacube_sizer  {
  background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/bottom-right-c.gif) no-repeat 0 0;			
  width:7px;
  height:7px;
}

.alphacube_sizer {
	cursor:se-resize;	
}

.alphacube_close {
	width: 23px;
	height: 23px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/button-close-focus.gif) no-repeat 0 0;			
	position:absolute;
	top:0px;
	right:11px;
	cursor:pointer;
	z-index:1000;
}

.alphacube_minimize {
	width: 23px;
	height: 23px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/button-min-focus.gif) no-repeat 0 0;			
	position:absolute;
	top:0px;
	right:55px;
	cursor:pointer;
	z-index:1000;
}

.alphacube_maximize {
	width: 23px;
	height: 23px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alphacube/button-max-focus.gif) no-repeat 0 0;			
	position:absolute;
	top:0px;
	right:33px;
	cursor:pointer;
	z-index:1000;
}

.alphacube_title {
	float:left;
	height:14px;
	font-size:14px;
	text-align:center;
	margin-top:2px;
	width:100%;
	color:#123456;
}

.alphacube_content {
	overflow:auto;
	color: #000;
	font-family: Tahoma, Arial, sans-serif;
  font: 12px arial;
	background:#FDFDFD;
}

/* For alert/confirm dialog */
.alphacube_window {
	border:1px solid #F00;	
	background: #FFF;
	padding:20px;
	margin-left:auto;
	margin-right:auto;
	width:400px;
}

.alphacube_message {
  font: 12px arial;
	text-align:center;
	width:100%;
	padding-bottom:10px;
}

.alphacube_buttons {
	text-align:center;
	width:100%;
}

.alphacube_buttons input {
	width:20%;
	margin:10px;
}

.alphacube_progress {
	float:left;
	margin:auto;
	text-align:center;
	width:100%;
	height:16px;
	background: #FFF url('<%=request.getContextPath()%>/common/js/styles/alert/progress.gif') no-repeat center center
}

.alphacube_wired_frame {
	background: #FFF;
	filter:alpha(opacity=60);
	-moz-opacity: 0.6;
	opacity: 0.6;	
}


