<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../../../common/security.jsp" %>
<%@ include file="../../../common/loginAccount.jsp" %>
<logic:present name="loginAccount" >
  <%
  com.jdon.jivejdon.model.Account account = (com.jdon.jivejdon.model.Account)request.getAttribute("loginAccount");
  String userId = request.getParameter("userId");
  if ( account.getUserId().equals(userId)){
	  request.setAttribute("isOwner", "true"); 
  }
  %>
  
</logic:present>

<form action="" method="POST" name="listForm" >
<input type="hidden" name="subscribeType" value="1">

<table class="contacts" width="600" cellpadding=3 cellspacing=0 border=1  align="center">
  <tr  bgcolor="#C3C3C3">
			<td class="contactDept" align="center">
				主题标题
			</td>
			<td class="contactDept" align="center">
				回复
			</td>
			<td class="contactDept" align="center">
				作者
			</td>
			<td class="contactDept" align="center" width="150">
				最后回复
			</td>
			<td class="contactDept" align="center">
				消息
			</td>
			<td class="contactDept" align="center" >
				邮件
			</td>
			<td class="contactDept" align="center" width="2">
			</td>
			
		</tr>
		<logic:iterate id="subscription" name="subscriptionListForm" property="list"
			indexId="i">
			<bean:define id="subscribed" name="subscription" property="subscribed"></bean:define>
			<bean:define id="forumThread" name="subscribed" property="forumThread"></bean:define>
			<tr bgcolor="#ffffff">
				<td class="contact" align="center" >
					<a href="<%=request.getContextPath()%>/thread/<bean:write name="subscribed" property="subscribeId"/>" 
              target="_blank">
						<bean:write name="subscribed" property="name" />
					</a>
				</td>
				<td class="contact" align="center" >
					<bean:write name="forumThread" property="state.messageCount" /> 
				</td>
				<td class="contact" align="center" >
					<bean:write name="forumThread" property="rootMessage.account.username" />
				</td>
					<td class="contact" align="center" >
				    
		<logic:notEmpty name="forumThread" property="state.lastPost">
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>
            <span  class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
            <bean:write name="lastPost" property="modifiedDate" />
            </span>
            <br> by:
            <logic:equal name="lastPost" property="root" value="true">
                   <a href='<%=request.getContextPath()%>/thread/<bean:write name="lastPost" property="forumThread.threadId" />' target="_blank" >                    
            </logic:equal>
            <logic:equal name="lastPost" property="root" value="false">
                   <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />' target="_blank"  rel="nofollow"> 
            </logic:equal>
                    <span class='Users ajax_userId=<bean:write name="lastPost" property="account.userId"/>' >
                    <bean:write name="lastPost" property="account.username" /></span></a>
        </logic:notEmpty>
        
				</td>
				<td class="contact" align="center" >
				    <html:checkbox name="subscription" property="actionType(com.jdon.jivejdon.manager.subscription.action.ShortMsgAction)" disabled="true"/>
				</td>
				<td class="contact" align="center" >
				   <html:checkbox name="subscription" property="actionType(com.jdon.jivejdon.manager.subscription.action.EmailAction)" disabled="true"/>
				</td>
				<td class="contact" align="center" >
				<logic:present name="isOwner" >
				 <input type="radio" name="subscriptionId" value="<bean:write name="subscription" property="subscriptionId" />" >
				 </logic:present>
				</td>
			</tr>
		</logic:iterate>
	</table>
	<table cellpadding="4" cellspacing="0" border="0" width="580px">
		<tr>
			<td width="562px">
				<MultiPages:pager actionFormName="subscriptionListForm"
					page="/account/protected/sub/subThreadList.shtml">
					<MultiPages:prev name="[Prev ]" />
					<MultiPages:index />
					<MultiPages:next name="[Next ]" />
				</MultiPages:pager>
			</td>
			
		</tr>
	</table>
	<center>
	<logic:present name="isOwner" >
	 <input type="submit" name="edit" value="编辑选择的关注" onclick="return editAction('listForm','subscriptionId');" >
      <input type="submit" name="delete" value="删除选择的关注" onclick="return delAction('listForm','subscriptionId');" >
 
     <br></br>
           加入新关注方法：点按任意帖子的右上面图标<html:img page="/images/user_add.gif" width="18" height="18" alt="关注本主题 有新回复自动通知我" border="0" />就可加入。   
      
      </logic:present>
   </center>
</form>   
