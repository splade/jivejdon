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
if (errorBlocker.checkCount(request.getRemoteAddr(), 5)){
	response.sendError(404);
    return;
}
%>

<html>
<head>
<title>503 错误</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />

</head>
<body>
对不起 刷新太快,或同时打开窗口太多。两种解决方式：
<form action="<%=request.getContextPath() %>/verifySpammer" name="vform">
<br>1. 输入验证码(如无验证码再刷新一次)：<input type="text" name="registerCode" size="10"
				maxlength="10" >
				 <img id="theImg" src="<%=request.getContextPath()%>/account/registerCodeAction" border="0"  width="60" height="20"/>
        <input type="submit" value="验证" >			
        <br>如果无法验证，请转<a href="<%=request.getContextPath()%>">论坛首页</a>再次验证。	 
<p>
<br>2.人工帮助(处理有时间延迟)：<a href='<%=request.getContextPath()%>/forum/feed/feedback.jsp?subject=fromjivejdon3_503&body=<%=request.getRemoteAddr()%>_<%=request.getHeader("Referer")%>'>按这里报告管理员</a>
</form>

   </body>
</html>