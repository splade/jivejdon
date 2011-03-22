<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 帖子保存结果" />
<%@ include file="messageHeader.jsp" %>

<logic:notEmpty name="messageForm">
<logic:notEmpty name="messageForm" property="messageId">

   <bean:define id="messageId" name="messageForm" property="messageId"  />
   <logic:notEmpty name="messageForm" property="forumThread" >
       <bean:define id="forumThread" name="messageForm" property="forumThread"  />
    </logic:notEmpty>
    <bean:define id="action" name="messageForm" property="action"  />
</logic:notEmpty>
</logic:notEmpty>

<logic:notEmpty name="messageReplyForm">
<logic:notEmpty name="messageReplyForm" property="messageId">
<bean:define id="messageId" name="messageReplyForm" property="messageId"  />
<logic:notEmpty name="messageReplyForm" property="forumThread" >
   <bean:define id="forumThread" name="messageReplyForm" property="forumThread"  />
</logic:notEmpty>
<bean:define id="action" name="messageReplyForm" property="action"  />
</logic:notEmpty>
</logic:notEmpty>


<!-- 导航区  -->
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
    <td valign="top" width="99%">
    
<logic:notEmpty name="forumThread" >
  <logic:notEmpty name="forumThread" property="forum" >
      <bean:define id="forum" name="forumThread" property="forum" />   
       <%@include file="nav.jsp"%>    
    </logic:notEmpty>
</logic:notEmpty>
    </td>
    <td valign="top"  align="center">

    </td>
</tr>
</table>

<table cellpadding="0" cellspacing="0" border="0"  align="center">
<tr>
    <td valign="top" > 
    
    <p>
    <html:errors />

   <logic:messagesNotPresent>
    <logic:empty name="errors">
     <br><br>     
      帖子操作成功！
       <logic:notEmpty name="forumThread">
       <logic:notEmpty name="forumThread" property="forum" >
       <logic:notEqual name="action" value="delete">
          
        <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" 
        property="threadId" />/<bean:write name="messageId" />#<bean:write name="messageId" />'
        >按这里返回所发帖子</a>
        
       <script>      

       var fmainurl = '<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" 
        property="threadId" />/<bean:write name="messageId" />';
       var fmainPars = ''
       var anchor = '<bean:write name="messageId" />';

      </script>

        </logic:notEqual>
        </logic:notEmpty>    
        </logic:notEmpty>     
    </logic:empty>
  </logic:messagesNotPresent>
    </td>
</tr>
<tr>
    <td valign="top" > 
    <br><br><br><p><html:link page="/index.jsp">按这里返回首页</html:link>
    </td>
</tr>
</table>

<logic:messagesNotPresent>
<logic:empty name="errors">

<script>
  if (window.top.setDiagInfo)
      window.top.setDiagInfo(" <center><br><h4>\ 帖子保存成功 </h4> <br>由于缓存过几分钟可能才会生效  <br>请勿频繁发帖 ");
  if (window.top.forwardNewPage){
      setTimeout("window.top.forwardNewPage(fmainurl, fmainPars, anchor)",6000);      
  }      
 
</script>
    </logic:empty>
  </logic:messagesNotPresent>

<logic:messagesPresent>
   <span id="errors">
     <logic:present name="errors">
           <logic:iterate id="error" name="errors">
                <BR><bean:write name="error" />
           </logic:iterate>
    </logic:present>
    </span>

   <script>
    if (window.top.setDiagInfo){        
        window.top.setDiagInfo('<p align="center"> <html:errors />  ' + $('errors').innerHTML + "</p>");
    }
   </script>         

</logic:messagesPresent>
 
<br><br><br><br><br><br><br><br>
<%@include file="../common/IncludeBottom.jsp"%> 


