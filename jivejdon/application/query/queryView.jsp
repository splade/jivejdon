<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 论坛查询" />
<%@ include file="../common/IncludeTop.jsp" %>

<%@ include file="searchInputView.jsp" %>

<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp"%>

