<%@ taglib uri="struts-logic" prefix="logic" %>
<%@page import="com.jdon.security.web.CookieUtil"%>
<%
response.setContentType("text/javascript; charset=utf-8");
%>
<logic:present name="principal" >
     var messageChkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";
     new Ajax.PeriodicalUpdater('isNewMessage', messageChkURL,
      { method: 'get',
        frequency: 60, 
        decay: 60,
        evalScripts: true}); 
           
</logic:present>
<logic:notPresent name="principal" >

<%
  String uname = CookieUtil.getUsername(request);
  String passwd = CookieUtil.getPassword(request);
  if ((uname != null && uname.length()!= 0) &&
     (passwd != null && passwd.length()!= 0))
  {
%>
  var messageChkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";
     new Ajax.PeriodicalUpdater('isNewMessage', messageChkURL,
      { method: 'get',
        frequency: 60, 
        decay: 60,
        evalScripts: true}); 
      

<% }else{%>
<!--  anonymous -->
  var messageChkURL = "<%=request.getContextPath() %>/forum/checknewmessage.shtml";
     new Ajax.PeriodicalUpdater('isNewMessage', messageChkURL,
      { method: 'get',
        frequency: 60, 
        decay: 60,
        evalScripts: true}); 

<% }%>
</logic:notPresent>
