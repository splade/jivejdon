<%@ page contentType="text/html; charset=UTF-8" %>

<%
String username = "";
String password = "";
if (com.jdon.security.web.CookieUtil.getUsername(request) != null){
	username = com.jdon.security.web.CookieUtil.getUsername(request);	
	password = com.jdon.security.web.CookieUtil.getPassword(request);
}
%>
<span id="contextPath" style="display:none" class="<%=request.getContextPath() %>"></span>
<div id="loginAJAX" style="display:none" align="center">
 <div class="tooltip_content">
  <span id='login_error_msg' class="login_error" style="display:none">&nbsp;</span>
      <table border="0" cellpadding="0" cellspacing="2">
  <tr>
    <td> 用户 </td>
    <td width="10">&nbsp;</td>
    <td><input type="text" name="j_username" size="25" tabindex="1" id="j_username" value="<%=username%>">
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
    <td><input type="password" name="j_password" size="25" tabindex="2" id="j_password" value="<%=password%>">
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
<script>
var loggedURL = '<html:rewrite page="/account/protected/logged.jsp"/>';
</script>
<script language="javascript" src="<html:rewrite page="/account/js/login.js"/>"></script>
