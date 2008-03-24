<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%
String title = "搜索 ";
if (request.getAttribute("query") != null){
	title += (String)request.getAttribute("query");
}
pageContext.setAttribute("title", title);
%>
<%@ include file="../common/IncludeTop.jsp" %>


<center>

<%@ include file="searchInputView.jsp" %>

</center>
<!-- second query result -->
<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="allCount" value="0">

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
     符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴 [ 
   
     
<MultiPages:pager actionFormName="messageListForm" page="/query/searchAction.shtml" paramId="query" paramName="query">
<MultiPages:prev name="上页"/>
<MultiPages:index />
<MultiPages:next name="下页"/>
</MultiPages:pager>     
      ]
    </td>
</tr>
</table>


<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>

<logic:iterate indexId="i" id="messageSearchSpec" name="messageListForm" property="list" >
 
<bean:define id="forumMessage" name="messageSearchSpec" property="message"/>
<bean:define id="forumThread" name="forumMessage" property="forumThread"/>


<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="90%" align="center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#eeeeee">
   <td width="50%">
      <a href="<html:rewrite page="/forum/messageList.shtml" paramId="thread" paramName="forumThread" 
      paramProperty="threadId" />&message=<bean:write name="forumMessage" property="messageId"/>#<bean:write name="forumMessage" property="messageId"/>"        
       title="<bean:write name="forumMessage" property="filteredBody" />"   target="_blank" >
      <bean:write name="forumMessage" property="subject" /></a>
        <br>[ 作者: <html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.userId"
            ><bean:write name="forumMessage" property="account.username"/></html:link>]-
        [ 发表时间: <bean:write name="forumMessage" property="modifiedDate" />]  
        
        </td>
    <td>
    所属主题: <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>.html" 
             title="<bean:write name="forumThread" property="shortRootMessageFilteredBody[100]" />"  target="_blank">
         <bean:write name="forumMessage" property="forumThread.rootMessage.subject" />
         </a>
    </td>
</tr>
<tr bgcolor="#ffffff" >
 <td colspan="2">
 <bean:write name="messageSearchSpec" property="body" filter="false"/>
 </td>
</tr></table>
</td></tr>
</table>
<br>
</logic:iterate>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
     符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴 [ 
<MultiPages:pager actionFormName="messageListForm" page="/query/searchAction.shtml" paramId="query" paramName="query" >
<MultiPages:prev name="上页"/>
<MultiPages:index />
<MultiPages:next name="下页"/>
</MultiPages:pager>     
      ]
    </td>
</tr>
</table>

</logic:greaterThan>
</logic:present>


<center>
<table><tr><td align="center">
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="336x280"/>   
</jsp:include>
</td><td  align="center">
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="336x280"/>   
</jsp:include>
</td></tr></table>
</center>

<%@ include file="queryInputView.jsp" %>



 <jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="bottom"/>   
 </jsp:include> 

<%@include file="../common/IncludeBottom.jsp"%>

