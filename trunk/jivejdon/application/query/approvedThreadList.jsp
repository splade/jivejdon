<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>


<%@ page contentType="text/html; charset=UTF-8" %>
<!-- /query/threadApprovedNewList.shtml -->
<html>
<head>
<title>home</title>

</head>


<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
<div class="linkblock">

	<div class="post-headline">
	<h2>
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><bean:write name="forumThread" property="name" /></b></a>
     </h2>             
   </div> 
   
   <div class="post-byline">   
        作者:<html:link page="/profile.jsp" paramId="user" paramName="forumThread" paramProperty="rootMessage.account.username"
            target="_blank" ><b><bean:write name="forumThread" property="rootMessage.account.username" /></b
            ></html:link>
            <bean:define id="cdate" name="forumThread" property="creationDate" ></bean:define>
            <%String cdateS = (String)pageContext.getAttribute("cdate"); %>
    发表：<%=cdateS.substring(0, 11) %>   
    &nbsp;&nbsp;
    <html:img page="/images/comment_reply.gif" height="16" width="16"/>
    <bean:write name="forumThread" property="state.messageCount" />讨论
    &nbsp;&nbsp;
    <bean:write name="forumThread" property="state.viewCount" />浏览
    &nbsp;&nbsp;
    <bean:write name="forumThread" property="rootMessage.messageDigVo.digCount" />顶
   </div>
    			
     <p>
    <span class="tpc_content">
        <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />..
    </span>            
      </p>                
  

    <div class="post-footer">
    <html:img page="/images/tag_yellow.png" height="16" width="16"/>    
      <logic:iterate id="threadTag" name="forumThread" property="tags" >
         <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />
           </a>
         </span>
             &nbsp;&nbsp;
      </logic:iterate>        
        

    </div>
        
 </div>              	
</logic:iterate>


</body>
</html>
