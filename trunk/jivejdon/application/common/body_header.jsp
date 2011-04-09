<%@ page contentType="text/html; charset=UTF-8" %>
 <%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<body id="mainbody">

<table bgcolor="#707070"
 cellpadding="1" cellspacing="0" border="0" width="971" align="center">
<tr><td>
<table bgcolor="#FFFFCC"
 cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
          <td rowspan="2">
          <table cellpadding="0" cellspacing="0">
              <tr>
                <td> 
                <a href="http://www.jdon.com">
                 <img src='<html:rewrite page="/images/jdon.gif" />' width="120" height="60" alt="JiveJdon Community Forums" border="0" />
                  </a>                
                </td>
              </tr>
            </table></td>           
            </tr>
        <tr>
         <td align="right" valign="bottom">  

<table  cellpadding="0" cellspacing="0" border="0">
<tr><td>

<font color="#555555">
<logic:present name="principal" >
   欢迎<bean:write name="principal" />   
</logic:present>


  </font>
</td></tr></table>


<table  cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>

<td align="right">
          
<%
com.jdon.jivejdon.presentation.listener.UserCounterListener onlinecount = (com.jdon.jivejdon.presentation.listener.UserCounterListener)this.getServletContext().getAttribute((com.jdon.jivejdon.presentation.listener.UserCounterListener.COUNT_KEY));
String oc = Integer.toString(onlinecount.getActiveSessions());
%>
  <html:img page="/images/user_groups.gif" width="17" height="17" align="absmiddle" />
  <span class="smallgray">
  <a href="javascript:void(0);" target="_blank" onmouseover="loadWLJS(onlinesInf)" >在线<%=oc%>人</a> 
  </span>
  &nbsp;&nbsp;<html:img page="/images/world.gif" width="17" height="17" align="absmiddle" /><a href="/">首页</a>
  &nbsp;&nbsp;<html:img page="/images/folder_folder.gif" width="17" height="17" align="absmiddle"/><html:link page="/thread">主题表</html:link>
  &nbsp;&nbsp;<html:img page="/images/users.gif" width="17" height="17" align="absmiddle"/><a href="http://www.jdon.com/trainning.htm"  target="_blank">培训咨询</a>  
  &nbsp;&nbsp;<html:img page="/images/flag.gif" width="17" height="17" align="absmiddle"/><html:link page="/tags">标签</html:link>
  &nbsp;&nbsp;<html:img border="0" page="/images/search.gif"  width="17" height="17" align="absmiddle"/><html:link page="/query/threadViewQuery.shtml" >查搜</html:link>

<logic:notPresent name="principal" >
  &nbsp;&nbsp;<html:link page="/account/newAccountForm.shtml">
  <html:img border="0"  page="/images/user_add.gif"  width="17" height="17" align="absmiddle"/>注册</html:link>
</logic:notPresent>

<logic:notPresent name="principal" >
  
  &nbsp;&nbsp;<a href="javascript:void(0);" onclick='loadWLJS(loginW)'>
  <html:img border="0"  page="/images/user_right.gif"  width="17" height="17" align="absmiddle"/>登陆
  </a>  
  
</logic:notPresent>
              
<logic:present name="principal" >

  &nbsp;&nbsp;<a href="<%=request.getContextPath()%>/blog/<bean:write name="principal"/>" >
      <html:img border="0"  page="/images/user_folder.gif"  width="17" height="17" align="absmiddle"/>个人博客</a>
</logic:present>
                  
<logic:present name="principal" >
   &nbsp;&nbsp;<html:link page="/jasslogin?logout">
     <html:img page="/images/user_up.gif" width="17" height="17" alt="退出" border="0" align="absmiddle"/>退出
   </html:link>   
</logic:present>       

<!-- Feedsky FEED发布代码开始 -->
<!-- FEED自动发现标记开始 -->
<link title="RSS 2.0" type="application/rss+xml" href="http://feed.feedsky.com/jdon" rel="alternate" />
<!-- FEED自动发现标记结束 -->
<a href="http://feed.feedsky.com/jdon" target="_blank"><html:img page="/images/feed-icon-12x12-orange.gif" width="12" height="12" alt="RSS" border="0" align="absmiddle"/></a>
<!-- Feedsky FEED发布代码结束 -->
</td></tr>
</table>

           </td>
        </tr>
</table>
</td>
        </tr>
</table>