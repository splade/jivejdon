<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 论坛查询" />
<%@ include file="../common/IncludeTop.jsp" %>

<bean:parameter name="queryType" id="queryType"/>


<!-- first query result -->
<logic:present name="threadListForm">
<logic:greaterThan name="threadListForm" property="allCount" value="0">

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
    <%-- request.setAttribute("paramMaps", qForm.getParamMaps());  in ThreadQueryAction --%>    
     符合查询结果共有<b><bean:write name="threadListForm" property="allCount"/></b>主题贴 [
<MultiPages:pager actionFormName="threadListForm" page="/query/threadViewQuery.shtml"  name="paramMaps">
<MultiPages:prev name="上页"/>
<MultiPages:index />
<MultiPages:next name="下页"/>
</MultiPages:pager>     
      ]
    </td>
</tr>
</table>


<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>


<%@ include file="threadListCore.jsp" %>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
     符合查询结果共有<b><bean:write name="threadListForm" property="allCount"/></b>主题贴 [ 
<MultiPages:pager actionFormName="threadListForm" page="/query/threadViewQuery.shtml"  name="paramMaps">
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

<%@ include file="searchInputView.jsp" %>



<%@ include file="queryInputView.jsp" %>

 <jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="bottom"/>   
 </jsp:include> 
 
<%@include file="../common/IncludeBottom.jsp"%>


