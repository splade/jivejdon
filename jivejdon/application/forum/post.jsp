<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!--  deirectly call this :/forum/post.jsp -->
<%@ include file="../common/IncludeTop.jsp" %>
<a name="post"></a>
<jsp:include page="threadPost.jsp" flush="true">   
   <jsp:param name="forumId">
      <jsp:attribute name="value" >
     </jsp:attribute>
   </jsp:param>   
</jsp:include>


<%@include file="../common/IncludeBottom.jsp"%>