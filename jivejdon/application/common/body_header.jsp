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

<font color="#555555" id="loggedInfo">
<logic:present name="principal" >
   欢迎<bean:write name="principal" />   
</logic:present>
 </font>
</td></tr></table>

<table  cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>

<td align="right">
            
  <span class="smallgray">
  <a href="javascript:void(0);" target="_blank" onmouseover="loadWLJS(onlinesInf)" >在线</a> 
  </span>
  &nbsp;&nbsp;<a href="/">首页</a>
  &nbsp;&nbsp;<html:link page="/thread">主题表</html:link>
  &nbsp;&nbsp;<html:link page="/tags">标签</html:link>
  &nbsp;&nbsp;<html:link page="/query/threadViewQuery.shtml" >查搜</html:link>

<logic:notPresent name="principal" >
  &nbsp;&nbsp;<html:link page="/account/newAccountForm.shtml">注册</html:link>
</logic:notPresent>

<logic:notPresent name="principal" >  
  &nbsp;&nbsp;<a href="javascript:void(0);" onclick='loadWLJS(loginW)'>登陆 </a>    
</logic:notPresent>
              
<logic:present name="principal" >
  &nbsp;&nbsp;<a href="<%=request.getContextPath()%>/blog/<bean:write name="principal"/>" >个人博客</a>
</logic:present>
                  
<logic:present name="principal" >
   &nbsp;&nbsp;<html:link page="/jasslogin?logout">退出 </html:link>   
</logic:present>       

&nbsp;&nbsp;<a href="javascript:void(0);"  onclick="window.location.reload()">刷新</a>

<a href="http://feed.feedsky.com/jdon" target="_blank"><html:img page="/images/feed-icon-12x12-orange.gif" width="12" height="12" alt="RSS" border="0" align="absmiddle"/></a>

  <a href="http://weibo.com/ijdon" target="_blank"><html:img page="/images/sina.png" width="12" height="12" border="0"/></a>     
</td></tr>
</table>

           </td>
        </tr>
</table>
</td>
        </tr>
</table>