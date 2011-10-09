<%@ page contentType="text/html; charset=UTF-8"%>

<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>

<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.block.ErrorBlockerIF"%>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", request);
if (!errorBlocker.checkRate(request.getRemoteAddr(), 10))
    return;
%>

<html>
<head>
<title>503 错误</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />

</head>
<body>
对不起 刷新太快,或同时打开窗口太多。两种解决方式：
<form action="<%=request.getContextPath() %>/verifySpammer" name="vform">
<input type="hidden" name="fromURL" id="fromURL" value="">
<br>1. 输入验证码(输入时出现)通过后立即可访问：<input type="text" name="registerCode" size="10"
				maxlength="10" >
				 <img id="theImg" src="<%=request.getContextPath()%>/account/registerCodeAction" border="0" />
        <input type="button" value="验证" onclick="verify()">				 
<p>
<br>2.人工帮助(处理有时间延迟)：<a href='javascript:contactAdmin()'>按这里报告管理员</a>
</form>

<script>
    function verify(){
         document.getElementById('fromURL').value = location.href;
         document.vform.submit();
    
    }

    function contactAdmin(){
    	location.href = '<%=request.getContextPath()%>/forum/feed/feedback.jsp?subject=fromjivejdon3_503&body=<%=request.getRemoteAddr()%> '
        	    + location.href;
    }
   </script>
   </body>
</html>