<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<script>
  var prevThreadUrl;
  var nextThreadUrl;
</script>
<table cellpadding="0" cellspacing="2" border="0" width="971" align="center">
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
     <script>
        prevThreadUrl = '<%=request.getContextPath()%>/thread/<bean:write name="forumThreadPrev" property="threadId"/>';
     </script>
     <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThreadPrev" property="threadId"/>" 
      title="<bean:write name="forumThreadPrev" property="name"/>" class="forum">
    <html:img page="/images/prev.gif" width="10" height="10" hspace="2" altKey="forumThreadPrev.name" />
    <span class="tooltip html_tooltip_content_<bean:write name="forumThreadPrev" property="threadId"/>">
     上一主题
     </span>
    </a>
    <!-- for prototype window TooltipManager.init -->
             <div id="tooltip_content_<bean:write name="forumThreadPrev" property="threadId"/>" style="display:none">
               <div class="tooltip_content">
                <span class="tpc_content">
                 <bean:write name="forumThreadPrev" property="rootMessage.messageVO.shortBody[100]" />
                 </span>
               </div>
             </div>
   </logic:notEmpty>
<%} else {
 %>&nbsp;<%
} %>

    </td>
    <td width="98%" align="center">

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
     <script>
        nextThreadUrl = '<%=request.getContextPath()%>/thread/<bean:write name="forumThreadNext" property="threadId"/>';
     </script>
    
     <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThreadNext" property="threadId"/>"
      title="<bean:write name="forumThreadNext" property="name"/>" class="forum">
    <html:img page="/images/next.gif" width="10" height="10" hspace="2" altKey="forumThreadNext.name"/>
    <span class="tooltip html_tooltip_content_<bean:write name="forumThreadNext" property="threadId"/>">
     下一主题
     </span>
    </a>
      <!-- for prototype window TooltipManager.init -->
             <div id="tooltip_content_<bean:write name="forumThreadNext" property="threadId"/>" style="display:none">
               <div class="tooltip_content">
                <span class="tpc_content">
                 <bean:write name="forumThreadNext" property="rootMessage.messageVO.shortBody[100]" />
                 </span>
               </div>
             </div>
   </logic:notEmpty>
<%  } else { %>
    &nbsp;
<%  } %>    

    </td>
</tr>
</table>
