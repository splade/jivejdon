<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<cetner>
<logic:iterate id="key" name="HOTKEYS">
  <a href='<bean:write name="key" property="value"/>'  target="_blank"> <bean:write name="key" property="name"/></a>
</logic:iterate>
</center>

