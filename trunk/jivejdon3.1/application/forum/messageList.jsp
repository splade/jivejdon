<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-tiles" prefix="tiles" %> 

<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!--  显示一个主题下所有帖子 -->
<bean:size id="messageCount" name="messageListForm" property="list" />
<logic:equal name="messageCount" value="0">
<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
   <table><tr><td>
   无此贴 或已经删除
   </td></tr></table>
</center>   
</logic:equal>


<logic:greaterThan name="messageCount" value="0">

<bean:define id="currentForumThread" name="messageListForm" property="oneModel" />

<bean:define id="forum" name="currentForumThread" property="forum" />

<bean:define id="title" name="currentForumThread" property="name" />
<%@ include file="../common/IncludeTop.jsp" %>
<script language="javascript" src="<html:rewrite page="/common/js/imagetool.js"/>"></script>


<!-- 导航区  -->

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
<html:form action="/query/searchThreadAction.shtml"  method="get" styleClass="search">
    <td valign="top" width="50%">
    <%@include file="nav.jsp"%>
    </td>
     
     <td valign="top"  align="right">   
        <input type="text"  name="query" size="25" />
         <html:submit value="论坛搜索"/>
    </td>
     </html:form>
    
</tr>
</table>
<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="728x90"/>   
 </jsp:include>              

<%--  
<logic:greaterEqual name="messageCount" value="8">
   <center>
        <div id="vgad" ></div>
    </center>
</logic:greaterEqual>
<logic:lessThan name="messageCount" value="8">
--%>
    <table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<%-- </logic:lessThan>--%>


<!--  上下主题 start -->
<%@include file="threadsPrevNext1.jsp"%>
<!--  上下主题 结束  -->
<table  width="100%" border="0" cellpadding="2" cellspacing="1"  align="center" bgcolor="#cccccc">
<tr><td bgcolor="#ffffff">

<table bgcolor="#CFCFA0"
 cellpadding="3" cellspacing="0" border="0" width="100%" align="center">
<tr><td class="smallgray">

    这个主题共有 <b><bean:write name="messageListForm" property="numReplies" /></b> 回复 ／ <b><bean:write name="messageListForm" property="numPages" /></b> 页 [
<MultiPages:pager actionFormName="messageListForm" page="/forum/messageList.shtml" paramId="thread" paramName="currentForumThread" paramProperty="threadId">
<MultiPages:prev name="上一页" />
<MultiPages:index />
<MultiPages:next name="下一页" />
</MultiPages:pager>
     ]
    </td><td >
    </td>
    
    <td align="right" width="250">
       &nbsp;<html:link page="/message/messageAction.shtml" paramId="forum.forumId" paramName="forum" paramProperty="forumId" 
        ><html:img page="/images/newtopic.gif" width="113" height="20" border="0" alt="发表新帖子"/></html:link>
        &nbsp;<a href="#reply"><html:img page="/images/replytopic.gif" width="113" height="20" border="0" alt="回复该主题贴"/></a>
    </td>
</tr>
</table>

<%
   int row = 1;
%>
<logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i">
 <%
 String bgcolor = "#f7f7f7";
 if (row++%2 != 1) {
   bgcolor = "#EAE9EA";
 }
 %>
<a name="<bean:write name="forumMessage" property="messageId"/>"></a>
 
<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr>
 <td>
    <table bgcolor="#cccccc" cellpadding="4" cellspacing="1" border="0" width="100%">

    <tr bgcolor="<%=bgcolor%>">
        <td width="1%" rowspan="2" valign="top">
        <table cellpadding="0" cellspacing="0" border="0" width="120">
        <tr><td>
             <span id="menu_<bean:write name="forumMessage" property="messageId"/>" 
             onmouseover="showMenu(this.id,'<html:rewrite page="/account/accountProfile.shtml?winwidth=160" paramId="userId" paramName="forumMessage" paramProperty="account.userId"/>','URL:200:200')">
             <html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.userId"
            ><b><bean:write name="forumMessage" property="account.username"/></b>
             </html:link>
             </span>
             
            <br><br>
            <html:link page="/query/threadViewQuery.shtml?queryType=userMessageQueryAction" paramId="user" paramName="forumMessage" paramProperty="account.userId" target="_blank">
            发表文章: <bean:write name="forumMessage" property="account.messageCount"/>
            </html:link>
            <br>
            注册时间: <bean:write name="forumMessage" property="account.creationDate"/>         
            </td>
        </tr>
        </table>
        </td>
        <td >
 
  <table width="100%"  cellpadding="1" cellspacing="1"><tr>
        <td width="97%">

        <b><bean:write name="forumMessage" property="filteredSubject"/></b>

  </td>
        <td width="1%" nowrap>
        发表: <bean:write name="forumMessage" property="creationDate"/>
        </td>
         
       <td width="1%" nowrap="nowrap" align="center">
       <logic:equal name="messageListForm" property='<%= "authenticated[" + i + "]" %>' value="true">
           <html:link page="/message/messageAction.shtml?action=edit" paramId="messageId" paramName="forumMessage" paramProperty="messageId"
            >编辑</html:link>/       
       </logic:equal>       
        <a href="<html:rewrite page="/message/messageReplyAction.shtml" paramId="parentMessage.messageId" paramName="forumMessage" paramProperty="messageId"
         />&forum.forumId=<bean:write name="forum" property="forumId"
         />">回复</a>
          </td>
   </tr>
   </table>
         </td>
    </tr>
    <tr bgcolor="<%=bgcolor%>">
        <td width="99%" colspan="4" valign="top">
        <table width="100%" border="0" cellspacing="2" cellpadding="2"  style='TABLE-LAYOUT: fixed'>
          <tr>
            <td style='word-WRAP: break-word'>
            
 <!-- advert -->

<logic:equal name="i" value="0"><!--  first message -->
<%
java.util.Calendar cal = java.util.Calendar.getInstance();		
int m = cal.get(java.util.Calendar.MONTH);
int randomi = (m + 1)% 2;
String align = "right";
if (randomi == 1){
	   align = "left";
}         
%>
    <table width="1%" border="0" cellpadding="0" cellspacing="5" align="<%=align%>">
          <tr><td>
            <div style="margin-top:0px;margin-left:5px;" id="vgad300x250">
                 <jsp:include page="../common/advert.jsp" flush="true">   
                     <jsp:param name="fmt" value="336x280_2"/>   
                </jsp:include>  
            </div> 
          </td></tr>
    </table>                    
</logic:equal>
  
<span class="tpc_content">
      <bean:write name="forumMessage" property="filteredBody" filter="false"/>
</span>

        
        </td></tr>
        </table>
        </td>
    </tr>
    </table>

</td></tr>
</table>

</logic:iterate>

<%-- 
<table cellspacing="1" cellpadding="0" width="100%" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>            
              <td  align="middle" >          
              <jsp:include page="../common/advert.jsp" flush="true">   
                 <jsp:param name="fmt" value="728x90"/>   
               </jsp:include>  
              </td>
          </tr>
         
        </table>
      </td>
    </tr>
  </table>
--%>
<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="100%" align="center">
<tr bgcolor="#eeeeee">
  <td>

    </td>
    <td align="right">
<table cellpadding="3" cellspacing="0" border="0" width="100%" >
<tr><td class="smallgray" align="right">
 这个主题有 <b><bean:write name="messageListForm" property="numReplies" /></b> 回复 ／ <b><bean:write name="messageListForm" property="numPages" /></b> 页 [
<MultiPages:pager actionFormName="messageListForm" page="/forum/messageList.shtml" paramId="thread" paramName="currentForumThread" paramProperty="threadId">
<MultiPages:prev name="上一页" />
<MultiPages:index />
<MultiPages:next name="下一页" />
</MultiPages:pager>
     ]

    </td>
  
</tr>
</table>
    </td>
</tr>
</table>

</td></tr></table>

<!--  上下主题 start -->
<%@include file="threadsPrevNext2.jsp"%>
<!--  上下主题 结束  -->


<center>
<table><tr><td>
<IFRAME  MARGINHEIGHT="0" MARGINWIDTH="0" FRAMEBORDER="0" WIDTH="500" HEIGHT="290"  scrolling="no" 
SRC="<%=request.getContextPath()%>/query/hotlist.shtml?dateRange=365&messageReplyCountWindow=10&tablewidth=500&length=15&count=200"></iframe>
</td><td>

<jsp:include page="../common/advert.jsp" flush="true">   
  <jsp:param name="fmt" value="200x200"/>   
</jsp:include>  
</td></tr></table>
</center>

<center>
热点TAG:<tiles:insert definition=".hotkeys"></tiles:insert>
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
              查询本论坛内
                        <html:select name="queryForm" property="dateRange" >
                <html:optionsCollection name="queryForm" property="dateRanges" value="value" label="name"/>
           </html:select>
           回复超过<input type="text" name="messageReplyCountWindow"  size="4" value="10"/>的热门帖子                           
              <html:submit value=" 查询 " property="btnsearch"  style="width:60"/>
            </td>
          </tr>
         
        </table>
      </td>
    </tr>
  </table>
</html:form> 
<center>
 <jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="728x15"/>   
 </jsp:include>  
</center>
<a name="reply"></a>
<html:form action="/message/messageReplySaveAction.sthml" method="post"  onsubmit="return checkPost(this);" >
<html:hidden property="action" value="create"/>
<input type="hidden" name="parentMessage.messageId" value="<bean:write name="currentForumThread" property="rootMessage.messageId" />" />
<html:hidden property="messageId" />

<!-- create another name "messageForm", so in messageFormBody.jsp it can be used -->
<bean:define id="messageForm" name="messageReplyForm" />
快速发表回复

<%@ include file="../message/messageFormBody.jsp" %>

 <script>
document.messageReplyForm.subject.value='re:<bean:write name="currentForumThread" property="rootMessage.subject"/>';

</script>
</html:form>


</td>

</tr>
</table>

</logic:greaterThan>

 <jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="bottom"/>   
 </jsp:include>  
 
<%@include file="../common/IncludeBottom.jsp"%> 


