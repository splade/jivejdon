<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<div id="isNewMessage" style="display:none"></div>

<script>
  function checkmsg(url){
   if(typeof(Ajax) != "undefined")
      checkNewMessage(url);
  }

<logic:present name="principal" >
     checkmsg("<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml");      
</logic:present>
<logic:notPresent name="principal" >
<%@page import="com.jdon.security.web.CookieUtil"%>
<%
  String uname = CookieUtil.getUsername(request);
  String passwd = CookieUtil.getPassword(request);
  if ((uname != null && uname.length()!= 0) &&
     (passwd != null && passwd.length()!= 0))
  {
%>
     checkmsg("<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml");      

<% }else{%>
<!--  anonymous -->
      checkmsg("<%=request.getContextPath() %>/forum/checknewmessage.shtml");

<% }%>
</logic:notPresent>

</script>