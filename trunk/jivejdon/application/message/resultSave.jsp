<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="messageForm">
<logic:notEmpty name="messageForm" property="messageId">
   <bean:define id="messageId" name="messageForm" property="messageId"  />
    <bean:define id="action" name="messageForm" property="action"  />
</logic:notEmpty>
</logic:notEmpty>

<logic:notEmpty name="messageReplyForm">
<logic:notEmpty name="messageReplyForm" property="messageId">
<bean:define id="messageId" name="messageReplyForm" property="messageId"  />
<bean:define id="action" name="messageReplyForm" property="action"  />
</logic:notEmpty>
</logic:notEmpty>

    
<html:errors />

<logic:messagesNotPresent>
<logic:empty name="errors">
    <logic:notEqual name="action" value="delete">          
            帖子保存成功，<a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="messageId" />/<bean:write name="messageId" />#<bean:write name="messageId" />'
        >按这里返回所发帖子</a>
                
       <script>      
       ///thread/nav/forumThreadId/messageId   if there is not forumThreadId, it can be skip.
       var fmainurl = '<%=request.getContextPath()%>/thread/nav/<bean:write name="messageId" />/<bean:write name="messageId" />';
       var fmainPars = ''
       var anchor = '<bean:write name="messageId" />';
       if (window.top.setDiagInfo)
           window.top.setDiagInfo(" <center><br><h4>\ 帖子保存成功 </h4> <br>由于缓存过几分钟可能才会生效  <br>请勿频繁发帖 ");
       if (window.top.forwardNewPage){
           setTimeout("window.top.forwardNewPage(fmainurl, fmainPars, anchor)",6000);      
       }            
     </script>
                    
       
     </logic:notEqual>
      <logic:equal name="action" value="delete">      
          <bean:define id="title"  value=" 帖子删除成功" />
             <%@ include file="messageHeader.jsp" %>
             <table cellpadding="0" cellspacing="0" border="0"  align="center">
                <tr><td valign="top" > 
               <br><br>帖子删除成功 ， 由于您自己的缓存过10分钟后可能才会生效。
              <br><br><br><p><html:link page="/">按这里返回首页</html:link>
              </p>
              </td></tr></table>
          <%@include file="../common/IncludeBottom.jsp"%>   
      </logic:equal>         
     
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


