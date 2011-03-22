<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty  name="TITLE">
  <bean:define id="title" name="TITLE"  />
</logic:notEmpty>
<%@ include file="../common/IncludeTop.jsp" %>


<bean:parameter name="queryType" id="queryType" value=""/>
<bean:parameter name="tagID" id="tagID" value=""/>


<!-- first query result -->
<logic:present name="threadListForm">
<logic:greaterThan name="threadListForm" property="allCount" value="0">


<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>

<center>
<logic:notEmpty  name="TITLE">
  <h3><bean:write  name="TITLE"  /></h3>
</logic:notEmpty>

  <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="tagID" /> " target="_blank"  rel="nofollow">
         <html:img page="/images/user_add.gif" width="18" height="18" alt="关注本标签 有新回复自动通知我" border="0" />         
                  关注订阅本标签</a>
<span id='count_<bean:write name="tagID" />'></span>

<script type="text/javascript" src="<html:rewrite page="/common/js/tags.js"/>"></script>
<script>
  getTagSubCount('<%=request.getContextPath()%>', -1, '<bean:write name="tagID" />');
</script>

&nbsp;&nbsp;
<html:link page="/tags">查看其他所有标签</html:link>
</center>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray" align="center">
<div class="tres">        
        
     符合查询主题贴共有<b><bean:write name="threadListForm" property="allCount"/></b>贴 
<MultiPages:pager actionFormName="threadListForm" page="/query/taggedThreadList.shtml"  paramId="tagID" paramName="tagID"  >
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>
<a href="JavaScript:void(0);" class="tooltip html_tooltip_content_go">Go</a>

<div id="tooltip_content_go"  style="display:none">
<div class="tooltip_content">
			<div class="title">前往下页:</div>
			<div class="form">
				<input type="text" style="width: 50px;" id="pageToGo">
				<input type="button" value=" Go " onClick="goToAnotherPage('<html:rewrite page="/query/taggedThreadList.shtml"  paramId="tagID" paramName="tagID"/>',
				<bean:write name="threadListForm" property="count" />);" />
				
			</div>
 </div>
</div> 
</div>      
    </td>
</tr>
</table>
<div  style="width:80%;background-color:#FFFFFF;margin-left: auto;margin-right: auto;">
 
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
   <bean:define id="rootMessage" name="forumThread" property="rootMessage"></bean:define>
   <logic:notEmpty name="rootMessage"  property="account">     
        作者:  <html:link page="/profile.jsp" paramId="user" paramName="rootMessage" paramProperty="account.username"
            target="_blank" >
                <b><bean:write name="rootMessage" property="account.username" /></b>
            </html:link>
   </logic:notEmpty>
               
    发表于：<bean:write name="forumThread" property="creationDate" />            
   </div>
   <p>			
   <span class="tpc_content">
        <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[200]" />[...]
    </span>
   </p>                
    

    <div class="post-footer">标签分类:
      <logic:iterate id="threadTag" name="forumThread" property="tags" >
         <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />
           </a>
         </span>
             &nbsp;&nbsp;&nbsp;&nbsp;
      </logic:iterate>
        | 讨论(<bean:write name="forumThread" property="state.messageCount" />)
        |  <a href="<html:rewrite page="/message/messageReplyAction.shtml" paramId="parentMessage.messageId" paramName="forumThread" paramProperty="rootMessage.messageId"
             />&forum.forumId=<bean:write name="forumThread" property="forum.forumId"
             />"  rel="nofollow">回复</a>

    </div>
              	
    <div class="b_content_line"> </div>
  </div>
</logic:iterate>

</div>
<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray" align="center">
<div class="tres">        
     符合查询主题共有<b><bean:write name="threadListForm" property="allCount"/></b>贴  
<MultiPages:pager actionFormName="threadListForm" page="/query/taggedThreadList.shtml"  paramId="tagID" paramName="tagID"  >
<a href="JavaScript:void(0);" class="tooltip html_tooltip_content_go">Go</a>
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>     
      </div>
    </td>
</tr>
</table>

</logic:greaterThan>
</logic:present>


<script>
if (typeof(TooltipManager) != "undefined")
TooltipManager.init('Tags', 
		  {url: '<html:rewrite page="/query/tt.shtml?tablewidth=400&count=10" />', 
		   options: {method: 'get'}},
		   {className:"mac_os_x", width:400});  
		   

</script>

<%@ include file="searchInputView.jsp" %>

<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp"%>


