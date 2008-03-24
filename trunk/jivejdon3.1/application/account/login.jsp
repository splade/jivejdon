<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="title"  value=" 用户登录" />
<%@ include file="../common/IncludeTop.jsp" %>
<link rel="stylesheet" href="<html:rewrite page="/portlet_css.jsp"/>" type="text/css">

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
    <td valign="top" width="99%">
    <%@include file="../forum/nav.jsp"%>
    <br>
    </td>
    <td valign="top"  align="center">
    </td>
</tr>
</table>


<table border="0" cellpadding="0" cellspacing="0" width="500" align='center'>
<tr>
<td>
<div class="portlet-container">
<div class="portlet-header-bar">
<div class="portlet-title">
<div style="position: relative; font-size: smaller; padding-top: 5px;"><b>&nbsp;用户登陆&nbsp;</b></div>
</div>
<div class="portlet-small-icon-bar">
</div>
</div><!-- end portlet-header-bar -->
<div class="portlet-top-decoration"><div><div></div></div></div>
<div class="portlet-box">
<div class="portlet-minimum-height">
<div id="p_p_body_2" >
<div id="p_p_content_2_" style="margin-top: 0; margin-bottom: 0;">

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
<td align="center">


 <form method="POST" action="<%=request.getContextPath()%>/jasslogin" onsubmit="return Juge(this);">
           <table border="0" cellpadding="0" cellspacing="2">
  <tr>
    <td> 用户 </td>
    <td width="10">&nbsp;</td>
    <td><input type="text" name="j_username" size="25" tabindex="1">
    </td>
    <td width="10">&nbsp;</td>
    <td><table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>自动登陆 </td>
        <td align="right"><input type="checkbox" name="rememberMe"  >
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>密码 </td>
    <td width="10">&nbsp;</td>
    <td><input type="password" name="j_password" size="25" tabindex="2">
    </td>
    <td width="10">&nbsp;</td>
    <td><input type="submit" value=" 登陆 " tabindex="3" >
    </td>
  </tr>
  <tr>
    <td align="center" colspan="5">
	<a href="<%=request.getContextPath()%>/account/newAccountForm.shtml"  target="_blank" >
                          新用户注册
                    </a>
                    <a href="forgetPasswd.jsp" target="_blank">
                          忘记密码?
                    </a>
	</td>
  </tr>
</table>
      </form>



</td>
</tr>
</table>
</div>
</div>
</div>
</div><!-- end portlet-box -->
<div class="portlet-bottom-decoration-2"><div><div></div></div></div>
</div><!-- End portlet-container -->
</td></tr></table>


<script>


function Juge(theForm)
{  
 if (theForm.j_username.value == "")
  {
     alert("请输入用户名！");
     theForm.j_username.focus();
     return (false);
  }
  if (theForm.j_password.value == "")
  {
     alert("请输入密码！");
     theForm.j_password.focus();
     return (false);
  }
  
}


</script>    

<%@include file="../common/IncludeBottom.jsp"%>