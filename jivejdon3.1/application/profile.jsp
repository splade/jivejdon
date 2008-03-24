<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<bean:parameter name="user" id="user"/>
<logic:redirect forward="profile"
  paramId="userId" paramName="user" />