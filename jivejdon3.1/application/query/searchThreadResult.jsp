<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>


<%
String title = "搜索 ";
if (request.getAttribute("query") != null){
	title += (String)request.getAttribute("query");
}
pageContext.setAttribute("title", title);
%>
<%@ include file="../common/IncludeTop.jsp" %>


<center>

<%@ include file="searchInputView.jsp" %>

</center>
<!-- second query result -->
<logic:present name="threadListForm">
<logic:greaterThan name="threadListForm" property="allCount" value="0">

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
     符合查询结果共有<b><bean:write name="threadListForm" property="allCount"/></b>贴 [ 
   
     
<MultiPages:pager actionFormName="threadListForm" page="/query/searchThreadAction.shtml" paramId="query" paramName="query">
<MultiPages:prev name="上页"/>
<MultiPages:index />
<MultiPages:next name="下页"/>
</MultiPages:pager>     
      ]
    </td>
</tr>
</table>


<center>
<table><tr><td align="center">
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</td></tr></table>
</center>

<%@ include file="threadListCore.jsp" %>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
     符合查询结果共有<b><bean:write name="threadListForm" property="allCount"/></b>贴 [ 
<MultiPages:pager actionFormName="threadListForm" page="/query/searchThreadAction.shtml" paramId="query" paramName="query" >
<MultiPages:prev name="上页"/>
<MultiPages:index />
<MultiPages:next name="下页"/>
</MultiPages:pager>     
      ]
    </td>
</tr>
</table>

</logic:greaterThan>
</logic:present>


<center>
<table><tr><td align="center">
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="336x280"/>   
</jsp:include>
</td><td  align="center">
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="336x280"/>   
</jsp:include>
</td></tr></table>
</center>


<%@ include file="queryInputView.jsp" %>

 <jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="bottom"/>   
 </jsp:include> 

<%@include file="../common/IncludeBottom.jsp"%>

