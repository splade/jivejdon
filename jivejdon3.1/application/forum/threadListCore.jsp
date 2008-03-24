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
        <td width="1%" nowrap align="center">
     
        <logic:present name="ASC">
           <html:link page="/forum.jsp" paramId="forum" paramName="forum" paramProperty="forumId"
           ><b><font color="#ffffff">最后回复</font></b>   
           <html:img page="/images/desc_order.png" border="0" width="11" height="11" alt="最新在前排列" title="最新在前排列"/>
           </html:link>   
        </logic:present>
        <logic:notPresent name="ASC">
           <html:link page="/forum.jsp?ASC" paramId="forum" paramName="forum" paramProperty="forumId"
           ><b><font color="#ffffff">最后回复</font></b>
           <html:img page="/images/asc_order.png" border="0" width="11" height="11" alt="最早在前排列" title="最早在前排列"/>
           </html:link>        
        </logic:notPresent>
         </td>          
    </tr>

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
    <tr bgcolor="#f7f7f7">
        <td nowrap>
            <html:img  page="/images/topic_old.gif" width="8" height="8" vspace="4" border="0" />
        </td>
        <td>        
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>.html" 
             title="<bean:write name="forumThread" property="shortRootMessageFilteredBody[100]" />" target="_blank">
             <b><bean:write name="forumThread" property="name" /></b></a>

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
            <br> by: <a href="<html:rewrite page="/forum/messageList.shtml" paramId="thread" paramName="lastPost" paramProperty="forumThread.threadId" 
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


