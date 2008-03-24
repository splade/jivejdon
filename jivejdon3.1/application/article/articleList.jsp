<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!--  显示一个主题下所有帖子 -->
<bean:define id="messageList" name="messageListForm" property="list" />

<logic:empty name="messageListForm"  property="oneModel" >
   无此贴
</logic:empty>

<logic:notEmpty name="messageListForm"  property="oneModel" >
<bean:define id="currentForumThread" name="messageListForm" property="oneModel" />

<bean:define id="forum" name="currentForumThread" property="forum" />

<bean:define id="title" name="currentForumThread" property="name" />
<%@ include file="header.jsp" %>

<!--  get the first message, regard it as article -->
<logic:iterate id="forumMessage" name="messageListForm" property="list" length="1">
<table  cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr><td>
  
     <h3 align="center"><bean:write name="forumMessage" property="filteredSubject"/></h3>
         <p align="center">
            作者：<html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.userId"
            ><b><bean:write name="forumMessage" property="account.username"/></b>
             </html:link>
            发表时间：<bean:write name="forumMessage" property="creationDate"/>
            <a href="<html:rewrite page="/message/messageReplyAction.shtml" paramId="parentMessage.messageId" paramName="forumMessage" paramProperty="messageId"
             />&forum.forumId=<bean:write name="forum" property="forumId"
             />"><img src="images/reply.gif" width="17" height="17" alt="回复此消息" hspace="3" border="0"
              >回复</a>
            <br/>
<center>
原贴网址：<a href="<%=request.getContextPath()%>/thread/<bean:write name="currentForumThread" property="threadId"/>.html">
http://www.jdon.com<%=request.getContextPath()%>/thread/<bean:write name="currentForumThread" property="threadId"/>.html</a>
</center>   
           </p>
         
  <!-- advert -->
  <%
java.util.Calendar cal = java.util.Calendar.getInstance();		
int m = cal.get(java.util.Calendar.MONTH);
int randomi = m % 2;
String align = "right";
if (randomi == 1){
	   align = "left";
}         
%>
         <table width="1%" border="0" cellpadding="0" cellspacing="5" align="<%=align%>"> <tr> <td>
         <div style="margin-top:0px;margin-left:5px;" id="vgad300x250">
                <jsp:include page="../common/advert.jsp" flush="true">   
                  <jsp:param name="fmt" value="336x280"/>   
                </jsp:include>  
           </div>
          </td></tr>
            </table>
                    
          <p class="article"><bean:write name="forumMessage" property="filteredBody" filter="false"/></p>    
                
</td></tr></table>
</logic:iterate>

<%
   int row = 1;
%>
<logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i" offset="1">
 <%
 String bgcolor = "#f7f7f7";
 if (row++%2 != 1) {
   bgcolor = "#EAE9EA";
 }
 %>
<a name="<bean:write name="forumMessage" property="messageId"/>"></a>
 
<table bgcolor="#cccccc" cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr>
	<td>
    <table bgcolor="#cccccc" cellpadding="4" cellspacing="1" border="0" width="100%">
    
    <tr bgcolor="<%=bgcolor%>">
        <td >
		<table width="100%"  cellpadding="1" cellspacing="1"><tr>
        <td width="97%">

        <b><bean:write name="forumMessage" property="filteredSubject"/></b>

		</td>
        <td width="1%" nowrap>
        发表: <bean:write name="forumMessage" property="creationDate"/>
        </td>
         
       <td width="1%" nowrap="nowrap" align="center">
        <a href="<html:rewrite page="/message/messageReplyAction.shtml" paramId="parentMessage.messageId" paramName="forumMessage" paramProperty="messageId"
         />&forum.forumId=<bean:write name="forum" property="forumId"
         />">回复</a>
          </td>
		 </tr>
		 </table>
         </td>
    </tr>
     <tr bgcolor="<%=bgcolor%>">
      <td>
      <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr><td>
             <html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.userId"
            ><b><bean:write name="forumMessage" property="account.username"/></b>
             </html:link>
           <html:link page="/query/threadViewQuery.shtml?queryType=userMessageQueryAction" paramId="user" paramName="forumMessage" paramProperty="account.userId" target="_blank">
            发表文章: <bean:write name="forumMessage" property="account.messageCount"/>
            </html:link>/
            注册时间: <bean:write name="forumMessage" property="account.creationDate"/>
            </td>
        </tr>
       </table>
     </td>
    </tr>
    <tr bgcolor="<%=bgcolor%>">
        <td width="99%" colspan="4" valign="top">
        <table width="100%" border="0" cellspacing="2" cellpadding="2"  style='TABLE-LAYOUT: fixed'>
          <tr>
            <td  style='word-WRAP: break-word'>
        <span class="tpc_content">
     	<bean:write name="forumMessage" property="filteredBody" filter="false"/>
    	</span>
		<p>
        </td> </tr>
        </table>
        </td>
    </tr>
    </table>

</td></tr>
</table>

</logic:iterate>


<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="100%" align="center">
<tr>
    <td>
<table bgcolor="#eeeeee"
 cellpadding="3" cellspacing="0" border="0" width="100%" align="center">
<tr><td>
 
<jsp:include page="../common/advert.jsp" flush="true">   
  <jsp:param name="fmt" value="article_end"/>   
</jsp:include>  

    <td><td >
    </td>
</tr>
</table>
    </td>
</tr>
</table>       

<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="100%" align="center">
<tr>
    <td>
<table bgcolor="#eeeeee"
 cellpadding="3" cellspacing="0" border="0" width="100%" align="center">
<tr><td class="smallgray">
 这个主题共有 <b><bean:write name="messageListForm" property="numReplies" /></b> 回复 ／ <b><bean:write name="messageListForm" property="numPages" /></b> 页 [
<MultiPages:pager actionFormName="messageListForm" page="/forum/messageList.shtml" paramId="thread" paramName="currentForumThread" paramProperty="threadId" target="_blank">
<MultiPages:prev name="上一页" />
<MultiPages:index />
<MultiPages:next name="下一页" />
</MultiPages:pager>
     ]
    <td><td >

    </td>
</tr>
</table>
    </td>
</tr>
</table>
<!--  上下主题 start -->
<%@include file="../forum/threadsPrevNext3.jsp"%>
<!--  上下主题 结束  -->

</logic:notEmpty>


<center>
<IFRAME  MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 WIDTH="500" HEIGHT="290"  scrolling="no" 
SRC="<%=request.getContextPath()%>/query/hotlist.shtml?dateRange=180&tablewidth=500&length=15&count=200"></iframe>
</center>

<html:form action="/query/threadViewQuery.shtml" method="post">
<html:hidden  name="queryForm" property="queryType" value="HOT1"/>
<input type="hidden"  name="forumId"  value="<bean:write name="forum" property="forumId"/>"/> 
  <table cellspacing="1" cellpadding="0" width="90%" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>            
              <td  align="middle" >              
              查询本论坛
                        <html:select name="queryForm" property="dateRange" >
                <html:optionsCollection name="queryForm" property="dateRanges" value="value" label="name"/>
           </html:select>
           最热门帖子                           
              <html:submit value=" 查询 " property="btnsearch"  style="width:60"/>
            </td>
          </tr>         
        </table>
      </td>
    </tr>
  </table>
</html:form> 

<a name="reply"></a>
<html:form action="/message/messageReplySaveAction.sthml" method="post"  onsubmit="return checkPost(this);" >
<html:hidden property="action" value="create"/>
<input type="hidden" name="parentMessage.messageId" value="<bean:write name="currentForumThread" property="rootMessage.messageId" />" />
<html:hidden property="messageId" />
快速发表回复:
<!-- create another name "messageForm", so in messageFormBody.jsp it can be used -->
<bean:define id="messageForm" name="messageReplyForm" />

<%@ include file="../message/messageFormBody.jsp" %>

 <script>
document.messageReplyForm.subject.value='re:<bean:write name="currentForumThread" property="rootMessage.subject"/>';

</script>
</html:form>

 <center>
    <jsp:include page="../common/advert.jsp" flush="true">   
         <jsp:param name="fmt" value="728x15"/>   
    </jsp:include>
   </center>

<%@include file="footer.jsp"%> 


