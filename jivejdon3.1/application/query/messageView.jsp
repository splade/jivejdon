<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 论坛查询" />
<%@ include file="../common/IncludeTop.jsp" %>


<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>

<!-- second query result -->
<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="allCount" value="0">

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
     符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴 [ 
<MultiPages:pager actionFormName="messageListForm" page="/query/threadViewQuery.shtml" name="paramMaps" >
<MultiPages:prev name="上页"/>
<MultiPages:index />
<MultiPages:next name="下页"/>
</MultiPages:pager>     
      ]
    </td>
</tr>
</table>


<table bgcolor="#999999" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td> 
<table bgcolor="#999999" cellpadding="3" cellspacing="1" border="0" width="100%">
<tr bgcolor="#eeeeee">
    <td align="center" nowrap="nowrap"><b>帖子标题</b></td>
    <td align="center" nowrap="nowrap"><b>作者</b></td>
    <td align="center" nowrap="nowrap"><b>最后修改时间</b></td>
</tr>

<logic:iterate indexId="i"   id="forumMessage" name="messageListForm" property="list" >
 
<bean:define id="forumThread" name="forumMessage" property="forumThread"/>

<tr bgcolor="#ffffff">
    <td width="20%">
      <a href="<html:rewrite page="/forum/messageList.shtml" paramId="thread" paramName="forumThread" 
      paramProperty="threadId" />&message=<bean:write name="forumMessage" property="messageId"/>#<bean:write name="forumMessage" property="messageId"/>"
        title="<bean:write name="forumMessage" property="body"/>"
        target="_blank" >
        <bean:write name="forumMessage" property="subject"/></a>
    </td>
    <td align="center" width="4%"
        ><bean:write name="forumMessage" property="account.username"/> </td>
    <td align="center" width="4%"
        ><bean:write name="forumMessage" property="modifiedDate" /></td>
     </tr>

</logic:iterate>
</table>

</td></tr>
</table>


<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
     符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴 [ 
<MultiPages:pager actionFormName="messageListForm" page="/query/threadViewQuery.shtml" name="paramMaps">
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


<%@ include file="searchInputView.jsp" %>


<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp"%>


