<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-html" prefix="html" %>

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
对不起 刷新太快，被拒绝。两种解决方式：
<form action="<%=request.getContextPath() %>/verifySpammer" name="vform">
<input type="hidden" name="fromURL" id="fromURL" value="">
<br>1. 输入验证码通过后立即可访问：<input type="text" name="registerCode" size="10"
				maxlength="50" onfocus="document.getElementById('theImg').src='<%=request.getContextPath()%>/account/registerCodeAction'">
				 <img id="theImg" src="<%=request.getContextPath()%>/images/white_pix.gif" border="0" />
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
    	location.href = 'http://www.jdon.com/my/feed/feedbackAction.do?subject=fromjivejdon3_503_<%=request.getRemoteAddr()%>_'
        	    + location.href;
    }
   </script>
   </body>
</html>