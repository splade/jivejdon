<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>

    <title>java</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon.css"/>" type="text/css">
<style type="text/css">
<!--
.small {font-size: 12px}
-->
</style>
</head>

<%--  
two caller:
no parameter: /query/hotlist.shtml

--%>
<body leftmargin="0" rightmargin="0" topmargin="0">
<table width="155" border="1" cellpadding="0" cellspacing="0" bordercolor="#6692b5">
  <tr>
    <td bgcolor="#c3d9e7"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="25" background="<%=request.getContextPath() %>/images/j1.gif" align="center">
        <font color="#ffffff" ><b> <span class="small">最新讨论</span></b></font></td>
      </tr>
      <tr>
        <td height="3" bgcolor="#6692B5"></td>
      </tr>
      <tr>
        <td >
        
        
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >

<table bgcolor="#ffffff"
         cellpadding="1" cellspacing="0" border="0" width="100%" style='TABLE-LAYOUT: fixed'>
            <tr>
                <td width="100%" style='word-WRAP: break-word'><font size="-1" face="arial"><b>&#149;</b></font> 
                  <span class="small">
                  <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>.html" 
             title="<bean:write name="forumThread" property="name" />" target="_blank">
             <bean:write name="forumThread" property="name" /></a>
             <logic:notEmpty name="forumThread" property="forumThreadState">
               <bean:write name="forumThread" property="forumThreadState.messageCount" /> 回复
             </logic:notEmpty>               
              </span>  </td>
              </tr>
              
            </table>
            
</logic:iterate>
</td>
      </tr>
      <tr>
        <td height="3" bgcolor="#6692B5"></td>
      </tr>
      
      
      
    </table></td>
  </tr>
</table>



</body>
</html>
