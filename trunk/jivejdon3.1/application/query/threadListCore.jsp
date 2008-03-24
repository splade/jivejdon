<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td>
    <table bgcolor="#cccccc"
     cellpadding="3" cellspacing="1" border="0" width="100%">
    <tr bgcolor="#868602" background="<%=request.getContextPath()%>/images/tableheadbg.gif">
        <td width="1%">&nbsp;</td>
        <td width="96%">
        <table width="100%"><tr><td>
          <b><font color="#ffffff">&nbsp; 主题名</font></b>
          </td><td >
           </td></tr>
         </table>
        </td>
        <td width="1%" nowrap><b><font color="#ffffff">&nbsp; 回复 &nbsp;</font></b></td>
        <td width="1%" align="center"><b><font color="#ffffff">&nbsp; 作者 &nbsp;</font></b></td>
        <td width="1%" nowrap align="center"><b><font color="#ffffff">最后回复</font></b></td>
    </tr>

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
    <tr bgcolor="#f7f7f7">
        <td nowrap>
            <html:img  page="/images/topic_old.gif" width="8" height="8" vspace="4" border="0" />
        </td>
        <td>
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>.html" 
              target="_blank">
             <b><bean:write name="forumThread" property="name" /></b></a>
             <br><span class="smallgray"><bean:write name="forumThread" property="shortRootMessageFilteredBody[100]" /></span>
        </td>
        <td align="center">
            <bean:write name="forumThread" property="forumThreadState.messageCount" />            
        </td>
        <td nowrap>
            &nbsp;
            <html:link page="/profile.jsp" paramId="user" paramName="forumThread" paramProperty="rootMessage.account.userId"
            target="_blank" ><b><bean:write name="forumThread" property="rootMessage.account.username" /></b
            ></html:link>

            &nbsp;
        </td>
        <td nowrap>
         
         <logic:notEmpty name="forumThread" property="forumThreadState.lastPost">
            <bean:define id="lastPost" name="forumThread" property="forumThreadState.lastPost"/>
            <bean:write name="lastPost" property="modifiedDate" />
            <br/> by: <a href="<html:rewrite page="/forum/messageList.shtml" paramId="thread" paramName="lastPost" paramProperty="forumThread.threadId" 
                    />&message=<bean:write name="lastPost" property="messageId" 
                    />#<bean:write name="lastPost" property="messageId" />"                                   
                    target="_blank" ><bean:write name="lastPost" property="account.username" /></a>
        </logic:notEmpty>
        </td>
    </tr>
</logic:iterate>


    </table>
    </td>
</tr>
</table>


