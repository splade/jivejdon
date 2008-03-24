<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<table cellpadding="0" cellspacing="2" border="0" width="100%" align="center">
<tr><td colspan="3"><html:img page="/images/blank.gif" width="1" height="5" border="0"/></td></tr>
<tr>
    <td width="1%" nowrap>


<logic:empty name="ThreadsPrevNext" >
   ERROR:  ThreadsPrevNext is null! check your ThreadPrevNexListAction!
</logic:empty>

<%
java.util.ListIterator iter = (java.util.ListIterator)request.getAttribute("ThreadsPrevNext");
if (iter.hasPrevious()){
   com.jdon.jivejdon.model.ForumThread forumThreadPrev = (com.jdon.jivejdon.model.ForumThread)iter.previous();
   request.setAttribute("forumThreadPrev", forumThreadPrev);
  // advance the iterator pointer back to the original index   
   iter.next();
  %>
   <logic:notEmpty name="forumThreadPrev" >
   
     <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThreadPrev" property="threadId"/>.html" 
      title="<bean:write name="forumThreadPrev" property="name"/>" class="forum">
    <html:img page="/images/prev.gif" width="10" height="10" hspace="2" altKey="forumThreadPrev.name" />
     上一主题
    </a>
   </logic:notEmpty>
<%} else {
 %>&nbsp;<%
} %>

    </td>
    <td width="98%" align="center">

<table cellpadding="0" cellspacing="2" border="0">
<tr>
	<td><html:link page="/forum.jsp"  paramId="forumId" paramName="forum" paramProperty="forumId" title="返回主题列表">
        <html:img page="/images/back_to.gif" width="12" height="12" alt="Go back to the topic listing" border="0"/>
        </html:link>
    </td>
	<td nowrap>
        &nbsp;
        <html:link page="/forum.jsp"  paramId="forumId" paramName="forum" paramProperty="forumId" title="返回主题列表">
                      返回主题列表
        </html:link>
    </td>
</tr>
</table>

    </td>
    <td width="1%" nowrap>
<%
if (iter.hasNext())
   iter.next();
if (iter.hasNext()){
   com.jdon.jivejdon.model.ForumThread forumThreadNext = (com.jdon.jivejdon.model.ForumThread)iter.next();
   request.setAttribute("forumThreadNext", forumThreadNext);
  %>
    <logic:notEmpty name="forumThreadNext" >
    
     <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThreadNext" property="threadId"/>.html"
      title="<bean:write name="forumThreadNext" property="name"/>" class="forum">
    <html:img page="/images/next.gif" width="10" height="10" hspace="2" altKey="forumThreadNext.name"/>
     下一主题
    </a>
   </logic:notEmpty>
<%  } else { %>
    &nbsp;
<%  } %>    

    </td>
</tr>
</table>