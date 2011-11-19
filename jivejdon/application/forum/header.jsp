<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty> - Thinking In Jdon
</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="shortcut icon" href="<html:rewrite page="/images/favicon.ico"/>" />
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
<link href="<html:rewrite page="/forum/css/messageList.css"/>" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<html:rewrite page="/common/js/LAB.js"/>"></script>
<script>
 if(top !== self) top.location = self.location;
 window.google_analytics_uacct = "UA-257352-2";
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-257352-2']);
  _gaq.push(['_trackPageview']);

</script>

<%@ include file="../common/security.jsp" %>
<%@ include file="../common/loginAccount.jsp" %>

</head>
<%@ include file="../common/body_header.jsp" %>
<%-- loaded in LAB.js   
<%@ include file="../account/loginAJAX.jsp" %>
--%>
<script>
var loggedURL = '<html:rewrite page="/account/protected/logged.jsp"/>';
</script>

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
<div id="loginAJAX" style="display:none" align="center">
 <div class="tooltip_content">
  <span id='login_error_msg' class="login_error" style="display:none">&nbsp;</span>
      <table border="0" cellpadding="0" cellspacing="2">
  <tr>
    <td> 用户 </td>
    <td width="10">&nbsp;</td>
    <td><input type="text" name="j_username" size="25" tabindex="1" id="j_username" value="">
    </td>
    <td width="10">&nbsp;</td>
    <td><table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>自动登陆 </td>
        <td align="right"><input type="checkbox" name="rememberMe"  id="rememberMe" checked="checked">
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>密码 </td>
    <td width="10">&nbsp;</td>
    <td><input type="password" name="j_password" size="25" tabindex="2" id="j_password" value="">
    </td>
    <td width="10">&nbsp;</td>
    <td> </td>
  </tr>
  <tr>
    <td align="center" colspan="5">
	<a href="<%=request.getContextPath()%>/account/newAccountForm.shtml"  target="_blank" >
                          新用户注册
                    </a>
                    <a href="<%=request.getContextPath()%>/account/forgetPasswd.jsp" target="_blank">
                          忘记密码?
                    </a>
	     </td></tr>
      </table>    
</div>      
  </div> 


 <%@ include file="../common/header_errors.jsp" %>
 