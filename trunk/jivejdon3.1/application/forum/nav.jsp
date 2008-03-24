<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>


    <b>
    <a href="http://www.jdon.com/"
    >首页</a>
    &raquo;
    <html:link page="/index.jsp" title="返回论坛列表"
    >论坛</html:link>
    &raquo;
    <logic:present name="forum" >
    <html:link page="/forum.jsp"  paramId="forumId" paramName="forum" paramProperty="forumId">
            <bean:write name="forum" property="name" />
    </html:link>
    </logic:present>
    </b>
