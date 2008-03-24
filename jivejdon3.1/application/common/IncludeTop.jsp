<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<%@ include file="security.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html><head>
<title>
<bean:write name="title" /> - Powered by Jdon
</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="stylesheet" href="<html:rewrite page="/jivejdon.css"/>" type="text/css">
<script language="javascript">
var contextPath = '<%=request.getContextPath()%>';
</script>
<script language="javascript" src="<html:rewrite page="/common/js/menu.js"/>"></script>
</head>

<body bgcolor="#ffffff"
 link="#000000"
 vlink="#000000"
 alink="#000000"
  marginheight="0" marginwidth="0" topmargin="5" >

<table bgcolor="#707070"
 cellpadding="1" cellspacing="0" border="0" width="100%">
<tr><td>
<table bgcolor="#eeeeee"
 cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
          <td rowspan="2">
          <table cellpadding="0" cellspacing="0">
              <tr>
                <td> <a href="http://www.jdon.com"><html:img page="/images/jdon.gif" width="120" height="60" alt="JiveJdon Community Forums" border="0"/></a>
                
                </td>
              </tr>
            </table></td>
            <td align="right" valign="top">
            
            </tr>
        <tr>
         <td align="right" valign="bottom">  

<table  cellpadding="0" cellspacing="0" border="0">
<tr><td>
<font color="#555555">
<logic:present name="principal" >
欢迎<bean:write name="principal" /> 
</logic:present>
<%--
在线<%=(String)session.getServletContext().getAttribute("userCounter")%>人
--%>
  </font>
</td></tr></table>

<table  cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>

<td align="right">
          
<%
String onlinecount = (String)this.getServletContext().getAttribute((com.jdon.jivejdon.presentation.listener.UserCounterListener.COUNT_KEY));
%>
<span class="smallgray">在线<%=onlinecount%>人</span>
<html:img page="/images/04.gif" width="17" height="17" /><a href="http://www.jdon.com/">J道首页</a>
  | <html:img page="/images/link.gif" width="17" height="17" /><html:link page="/">论坛首页</html:link>
 | <html:img page="/images/watch.gif" width="17" height="17"/><a href="http://www.jdon.com/trainning.htm"  target="_blank">培训咨询</a>
  | <html:img page="/images/05.gif" width="17" height="17"/><a href="http://www.jdon.com/jdonframework/"  target="_blank">开源框架</a>

| <html:img page="/images/prefs.gif" width="17" height="17"/><a href="http://www.jdon.com/communication.htm" target="_blank">精华</a>
| <html:img border="0" page="/images/search.gif"  width="17" height="17"/>
   <html:link page="/query/threadViewQuery.shtml" >查搜</html:link>

<logic:notPresent name="principal" >
 | <html:link page="/account/newAccountForm.shtml">
  <html:img border="0"  page="/images/01.gif"  width="17" height="17" />注册</html:link>
</logic:notPresent>

<logic:notPresent name="principal" >
 | <html:link page="/message/index.jsp">
  <html:img border="0"  page="/images/login.gif"  width="17" height="17"/>登陆</html:link>
</logic:notPresent>
              
<logic:present name="principal" >
     <html:link page="/account/protected/editAccountForm.shtml?action=edit" paramId="username" paramName="principal">
      <html:img border="0"  page="/images/04.gif"  width="17" height="17"/>编辑个人信息</html:link>
</logic:present>
                  
| 
<logic:present name="principal" >
   <html:link page="/jasslogin?logout">
     <html:img page="/images/logout.gif" width="17" height="17" alt="退出" border="0"/>退出
   </html:link>   
</logic:present>       
</td></tr>
</table>

           </td>
        </tr>
</table>
</td>
        </tr>
</table>




<!-- Support for non-traditional but simple message -->
<logic:present name="message">
  <b><font color="BLUE"><bean:write name="message" /></font></b>
</logic:present>

<!-- Support for non-traditional but simpler use of errors... -->
<logic:present name="errors">
  <logic:iterate id="error" name="errors">
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
  </logic:iterate>
</logic:present>

<html:errors/>