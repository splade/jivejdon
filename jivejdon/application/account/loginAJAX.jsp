<%@ page contentType="text/html; charset=UTF-8" %>
<script>
var loggedURL = '<html:rewrite page="/account/protected/logged.jsp"/>';
</script>
<script language="javascript" src="<html:rewrite page="/account/js/login.js"/>"></script>

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

<logic:notPresent name="principal" >
<script>
var username = readCookie("username");
if (username != null){
   var password = readCookie("password"); 
   $('j_username').value = decode64(username);
   $('j_password').value = decode64(password);      
}
</script>
</logic:notPresent>

<%--  
<div id="isNewMessage" style="display:none"></div>

<script>
 <logic:present name="principal" >
     var messageChkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";
     new Ajax.PeriodicalUpdater('isNewMessage', messageChkURL,
      { method: 'get',      
        evalScripts: true});            
</logic:present>
 
<logic:notPresent name="principal" >
  var messageChkURL = "<%=request.getContextPath() %>/forum/checknewmessage.shtml";
     new Ajax.Updater('isNewMessage', messageChkURL,
      { method: 'get',
        evalScripts: true}); 
</logic:notPresent>

</script>
--%>