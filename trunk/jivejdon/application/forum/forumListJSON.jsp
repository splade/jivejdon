<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="application/json; charset=UTF-8" %>

  
<%          
 com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>

[ <logic:iterate indexId="i"   id="forum" name="forumListForm" property="list" >
<logic:greaterThan name="i" value="0">,</logic:greaterThan>{ "forumId": "<bean:write name="forum"  property="forumId"/>", "name": "<bean:write name="forum"  property="name"/>"}
</logic:iterate>]
