<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="messageList" name="messageListForm" property="list" />

<bean:define id="parentMessage" name="messageListForm" property="oneModel" />
<bean:define id="forum" name="parentMessage" property="forum" />
<bean:define id="title" name="forum" property="name" />
<%@ include file="../header.jsp" %>


<!-- 被删除的帖子 -->	
 
<html:form action="/mobile/message/messageSaveAction.shtml" method="post"  >

<html:hidden property="method" value="delete"/>

<html:hidden name="parentMessage" property="messageId" />
            
作者：<b><bean:write name="parentMessage" property="account.username"/></b>
<br>            
发表时间: <bean:write name="parentMessage" property="creationDate"/>
<br>
<br>       
<b><bean:write name="parentMessage" property="messageVO.subject"/></b>
<br>

<br>
<br> 
<input type="submit" value="确定删除"  >	
<br>
<br> 
<form action="<%=request.getContextPath()%>/admin/user/deleteUserMessages.shtml" method="post" onsubmit="return delConfirm()">
 <input name="method" type="hidden" value="deleteUserMessages"/>
 <input name="username" type="hidden" value="<bean:write name="parentMessage" property="account.username" />"/>
 <input type="submit" value=" 删除该用户所有帖子 "/>
</form>
<br>
<br> 
IP: <bean:write name="parentMessage" property="postip" />
 <form action="<%=request.getContextPath()%>/admin/user/banIPAction.shtml" method="post" >
          <input name="ip" type="hidden" value="<bean:write name="parentMessage" property="postip" />"/>
          <input type="submit" value="屏蔽用户IP"/>
</form>  
</html:form>


<%@include file="../footer.jsp"%>