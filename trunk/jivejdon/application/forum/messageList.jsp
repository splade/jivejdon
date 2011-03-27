 <%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>


<%@ page contentType="text/html; charset=UTF-8" %>

<logic:present name="messageListForm">
<bean:size id="messageCount" name="messageListForm" property="list" />
<logic:equal name="messageCount" value="0">
<% response.sendError(404); %>
</logic:equal>

<logic:greaterThan name="messageCount" value="0">
<logic:empty name="messageListForm" property="oneModel" >
  <% 
  response.sendError(404);
  %>
</logic:empty>
<bean:define id="checkthread" name="messageListForm" property="oneModel" />
<bean:define id="lastModifiedDate" name="checkthread" property="state.modifiedDate2"/>
<%
long expire = 10 * 60;
if (request.getParameter("nocache") !=null){ // for just modified and view it
	expire =0;
}
long modelLastModifiedDate =((Long)pageContext.getAttribute("lastModifiedDate"));
com.jdon.jivejdon.util.ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response);	
%>


<%@include file="./messagListCore.jsp"%>
</logic:greaterThan>
</logic:present>