<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="title"  value=" 注册资料成功" />
<%@ include file="../common/IncludeTop.jsp" %>

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



<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" align="center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#FFFFCC">
 <td>
 <font class=p3 
     color="#000000">
 <b>注册资料操作</b>
 </font>
 </td>
</tr>
<tr bgcolor="#ffffff">
 <td align="center">
 
<center>
<br>
<br>
<br>
<logic:messagesNotPresent>
    注册资料操作成功  
</logic:messagesNotPresent>

<html:link page="/index.jsp"><b>按这里回首页登录</b></html:link>
</center>
<br>
<br>
<br>
 </td>
</tr></table>
</td></tr>
</table>



<%@include file="../common/IncludeBottom.jsp"%>

