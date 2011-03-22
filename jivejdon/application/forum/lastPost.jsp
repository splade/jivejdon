<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%     
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(2 * 60, request, response);
%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta   http-equiv="pragma"   content="no-cache">
</head>
<body>


  <logic:notEmpty name="messageForm" property="creationDate">
                    <bean:define id="forum" name="messageForm" property="forum"/>
                          <span class="home_content" >
                          <span  class='ForumLastPost ajax_forumId=<bean:write name="forum" property="forumId"/>' >
                          <bean:write name="messageForm" property="modifiedDate" /></span></span> 
  
                           <br>
                    <span class="home_content" >作者:</span>
                    <logic:equal name="messageForm" property="root" value="true">
                       <a href='<%=request.getContextPath()%>/thread/<bean:write name="messageForm" property="forumThread.threadId" />' >                    
                    </logic:equal>
                    <logic:equal name="messageForm" property="root" value="false">
                       <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="messageForm" property="forumThread.threadId" />/<bean:write name="messageForm" property="messageId" />#<bean:write name="messageForm" property="messageId" />'  rel="nofollow"> 
                    </logic:equal>
                    
                    <span  class='Users ajax_userId=<bean:write name="messageForm" property="account.userId"/>' >
                    <bean:write name="messageForm" property="account.username" /></span></a>                       
                        
            </logic:notEmpty>
            
            </body>
</html>