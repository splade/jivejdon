<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>

var checkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";

<logic:present name="principal" >
   checkNewMessage(checkURL);
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
      checkNewMessage(checkURL);
   
<% }%>
</logic:notPresent>