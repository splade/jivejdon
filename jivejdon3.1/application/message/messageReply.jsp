<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="forum" name="messageReplyForm" property="forum"  />

<bean:define id="title" name="forum" property="name" />
<%@ include file="messageHeader.jsp" %>

<bean:define id="parentMessage" name="messageReplyForm" property="parentMessage"  />
<bean:define id="forumThread" name="parentMessage" property="forumThread"  />

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td><html:img page="/images/blank.gif" width="1" height="10" border="0" alt=""/></td></tr>
</table>

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
    <td valign="top" width="98%">

      <%@include file="nav.jsp"%>    
    
    <p>
    在
    <html:link page="/forum.jsp"  paramId="forumId" paramName="forum" paramProperty="forumId" target="_blank">
            "<bean:write name="forum" property="name" />"
    </html:link>
    论坛中的主题    
    <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>.html"
     target="_blank">
             "<bean:write name="forumThread" property="name" />"</a>
    中回应原始消息
    <a href="#parent">"<bean:write name="parentMessage" property="subject" />"</a>
    
    </td>
    <td valign="top" width="1%" align="center">
    
    </td>
</tr>
</table>

<html:form action="/message/messageReplySaveAction.sthml" method="post"  onsubmit="return checkPost(this);" >
<html:hidden property="action" />
<html:hidden property="parentMessage.messageId" />
<html:hidden property="messageId" />

<!-- create another name "messageForm", so in messageFormBody.jsp it can be used -->
<bean:define id="messageForm" name="messageReplyForm" />

<%@ include file="messageFormBody.jsp" %>

</html:form>


<p>
<a name="parent"></a>
原始信息


<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr>
	<td>
    <table bgcolor="#cccccc" cellpadding="4" cellspacing="1" border="0" width="100%">
   
    <tr bgcolor="#E3E7F0">
        <td width="1%" rowspan="2" valign="top">
        <table cellpadding="0" cellspacing="0" border="0" width="140">
        <tr><td>
            
               <b><bean:write name="parentMessage" property="account.username"/></b>
            			
            <br><br>
            
            发表文章: <bean:write name="parentMessage" property="account.messageCount"/>
            
            <br>            
            注册时间: <bean:write name="parentMessage" property="account.creationDate"/>
            <br><br>

            </td>
        </tr>
        </table>
        </td>
        <td >
       
        <b><bean:write name="parentMessage" property="filteredSubject"/></b>
    
		</td>
        <td width="1%" nowrap>
        发表: <bean:write name="parentMessage" property="creationDate"/>
        </td>

        <td width="1%" nowrap align="center">
         </td>        
    </tr>
    <tr bgcolor="#E3E7F0">
        <td width="99%" colspan="4" valign="top">
        <span class="tpc_content">
        <bean:write name="parentMessage" property="filteredBody" filter="false"/>
    	</span>
		<p>
        <div id=vgad2 ></div>
        </td>
    </tr>
    </table>

</td></tr>
</table>


<%@include file="../common/IncludeBottom.jsp"%> 


