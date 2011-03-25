<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>


<bean:parameter  id="noheaderfooter" name="noheaderfooter" value=""/>
<logic:empty name="noheaderfooter">

<bean:define id="forumThread" name="messageListForm" property="oneModel" />
<bean:define id="forum" name="forumThread" property="forum" />
<bean:define id="title" name="forumThread" property="name" />
<bean:define id="pagestart" name="messageListForm" property="start" />
<bean:define id="pagecount" name="messageListForm" property="count" />
<%
int pagestartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
int currentPageNo = 1;
if (pagecountInt > 0) {
	currentPageNo = (pagestartInt / pagecountInt) + 1;
}
String titleStr = (String)pageContext.getAttribute("title");
if (currentPageNo > 1){
	titleStr = titleStr + "  - 第"+ currentPageNo + "页";
}
pageContext.setAttribute("title", titleStr);
%>
<%@ include file="header.jsp" %>
<meta name="keywords" content='
<logic:iterate id="threadTag" name="forumThread" property="tags" >
  <bean:write name="threadTag" property="title" />,
</logic:iterate>'/>


<!-- 导航区  -->
<%@include file="nav.jsp"%>
 
<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="728x90"/>   
 </jsp:include>              

<table cellpadding="0" cellspacing="0" width="971" align="center"><tr><td>

<!--  上下主题 start -->
<%@include file="threadsPrevNext1.jsp"%>
<!--  上下主题 结束  -->

</td></tr></table>
</logic:empty>

<logic:notEmpty name="noheaderfooter">
<%
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(0, request, response);
%>
</logic:notEmpty>
<div id="messageListBody" >

<logic:empty name="forumThread">
   <bean:define id="forumThread" name="messageListForm" property="oneModel" />
   <bean:define id="forum" name="forumThread" property="forum" />
</logic:empty>


<table  width="971" border="0" cellpadding="2" cellspacing="1"  align="center" bgcolor="#cccccc">
<tr><td bgcolor="#ffffff">

<table bgcolor="#CFCFA0"
 cellpadding="3" cellspacing="0" border="0" width="100%" align="center">
<tr><td >

<div class="tres" onmouseover="loadWLJS(nof)">         
<MultiPagesREST:pager actionFormName="messageListForm" page="/thread" paramId="thread" paramName="forumThread" paramProperty="threadId">
<MultiPagesREST:prev name="&#9668;" />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name="&#9658;" />
</MultiPagesREST:pager>
<a href="JavaScript:void(0);" class="tooltip html_tooltip_content_go"><span class="pageGo">Go</span></a>
共有 <b><bean:write name="messageListForm" property="numReplies" /></b> 回复(<b><bean:write name="messageListForm" property="numPages" /></b>页) 
阅读<bean:write name="forumThread" property="state.viewCount" />次 
<logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
     <bean:write name="forumThread" property="state.subscriptionCount"/>人关注
</logic:greaterThan>

<div id="tooltip_content_go"  style="display:none">
<div class="tooltip_content">
  <div class="title">前往下页:</div>
	<div class="form">
		<input type="text" style="width: 50px;" id="pageToGo">
		<input type="button" value=" Go "
		 onClick="goToAnotherPageREST('/thread/<bean:write name="forumThread" property="threadId"/>',
		 <bean:write name="messageListForm" property="count" />);" />				
	</div>
  </div>  
</div>  
</div>
    </td><td >
    </td>
    
    <td align="right" width="250">
       &nbsp;<html:link page="/message/messageAction.shtml" paramId="forum.forumId" paramName="forum" paramProperty="forumId">
       <html:img page="/images/newtopic.gif" width="113" height="20" border="0" alt="发表新帖子"/>
       </html:link>
        &nbsp;<a href="javascript:void(0);" onclick="openReplyWindow('<bean:write name="forumThread" property="rootMessage.messageId" />');">
        <html:img page="/images/replytopic.gif" width="113" height="20" border="0" alt="回复该主题贴"/></a>
    </td>
</tr>
</table>

   
<%
   int row = 1;
%>
<logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i">
 <%
 String bgcolor = "#ffffff";
 if (row++%2 != 1) {
   bgcolor = "#f8f8f8";
 }
 %>
<%@include file="messageListBody.jsp"%>

</logic:iterate>

  
<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="971" align="center">
 <tr bgcolor="#FFFFCC">
    <td >
    <table cellpadding="3" cellspacing="0" border="0" width="100%" height="30"  >
     <tr><td  align="center">
     <span  onmouseover="loadWLJS(initTagsW)">
        <html:link page="/query/tagsList.shtml?count=150" target="_blank" title="标签"><html:img page="/images/tag_yellow.png" width="16" height="16" alt="标签" border="0"/></html:link>
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
         <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />(<bean:write name="threadTag" property="assonum" />)
             </a></span>
             &nbsp;&nbsp;&nbsp;&nbsp;
        </logic:iterate>
      </span>
       </td></tr></table>
    </td>
</tr>
<tr bgcolor="#CFCFA0">
    <td >
<table cellpadding="3" cellspacing="0" border="0" width="100%" >
<tr>
<td>
<!-- JiaThis Button BEGIN -->
<div id="ckepop">
<a href="http://www.jiathis.com/share/" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank">分享到：</a>
<a class="jiathis_button_tools_1"></a>
<a class="jiathis_button_tools_2"></a>
<a class="jiathis_button_tools_3"></a>
<a class="jiathis_button_tools_4"></a>
</div>
<!-- JiaThis Button END -->
</td>
<td  align="right">
<div class="tres">
共有 <b><bean:write name="messageListForm" property="numReplies" /></b> 回复(<b><bean:write name="messageListForm" property="numPages" /></b>页) 
<a href="JavaScript:void(0);" class="tooltip html_tooltip_content_go">Go</a>   
<MultiPagesREST:pager actionFormName="messageListForm" page="/thread" paramId="thread" paramName="forumThread" paramProperty="threadId">
<MultiPagesREST:prev name="&#9668;" />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name="&#9658;" />
</MultiPagesREST:pager>
</div>     

    </td>
  
</tr>
</table>
    </td>
</tr>


</table>

</td></tr></table>
</div>
<logic:empty name="noheaderfooter">
<!--  上下主题 start -->
<%@include file="threadsPrevNext2.jsp"%>
<!--  上下主题 结束  -->

<center>
<!-- advert -->
<div id="v728902" ></div>
<div id="hotkeys"></div>
</center>

<table cellpadding="15" cellspacing="15" border="0" width="728" align="center">
<tr><td valign="top" >
<div id="approved"></div>
</td></tr></table>


<table align="center"><tr><td valign="top" >
<div id="vgad336x280"></div>

</td><td  valign="top">
<div id=hotList>正在读取，请等待...</div>

</td></tr></table>



<html:form action="/query/threadViewQuery.shtml" method="post">
<html:hidden  name="queryForm" property="queryType" value="HOT1"/>
<input type="hidden"  name="forumId"  value="<bean:write name="forum" property="forumId"/>"/> 
  <table cellspacing="1" cellpadding="0" width="971" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>            
              <td  align="center" >              
              查询本论坛内
                        <html:select name="queryForm" property="dateRange" >
                <html:optionsCollection name="queryForm" property="dateRanges" value="value" label="name"/>
           </html:select>
           回复超过<input type="text" name="messageReplyCountWindow"  size="4" value="10"/>的热门帖子                           
              <html:submit value=" 查询 " property="btnsearch"  style="width:60"/>
              
     
            </td>
          </tr>
         
        </table>
      </td>
    </tr>
  </table>
</html:form> 



<center>
<a name="reply"></a>
  
<jsp:include page="messagePostReply.jsp" flush="true">   
   <jsp:param name="parentMessageId">
      <jsp:attribute name="value" >
        <bean:write name="forumThread" property="rootMessage.messageId" />
     </jsp:attribute>
   </jsp:param>   
</jsp:include>

<script>       
document.messageReplyForm.subject.value='<bean:write name="forumThread" property="rootMessage.messageVO.subject"/>';
</script>
</center>

<div id="isNewMessage" style="display:none"></div>


<script language="javascript" >
   $LAB
   .script('<html:rewrite page="/common/js/prototype.js"/>').wait()
   .script('<html:rewrite page="/forum/js/messageList.js"/>')
   .wait(function(){
   
      hotList();
      hotkeys();
      approveList();

      var pageURL = '<%=request.getContextPath() %>/thread/<bean:write name="forumThread" property="threadId"/>';
      var start = <bean:write name="messageListForm" property="start" />;
      var count = <bean:write name="messageListForm" property="count" />;
      var allCount = <bean:write name="messageListForm" property="allCount" />
      document.onkeydown=leftRightgoPageREST;
   
    })   
   .wait(function(){
      $$('.loadUsersJS').each(function(e){
        Event.observe(e, 'mouseover', function(event){ 
    	  loadWLJS(initUsersW, "<%=request.getContextPath()%>");
    	  });
       });
       
      $$('.loadTagsJS').each(function(e){
        Event.observe(e, 'mouseover', function(event){ 
    	  loadWLJS(initTagsW, "<%=request.getContextPath()%>");
    	  });
       });
       
       <%@ include file="./js/messageNotfierJS.jsp" %>
    
    })
 
</script>


<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="468x60"/>   
</jsp:include>  

<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="728x90x2_loader"/>   
</jsp:include>  

<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="336x280_2_b"/>   
</jsp:include>  

<!-- JiaThis Button BEGIN -->
<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
<!-- JiaThis Button END -->

<%@include file="footer.jsp"%> 


   
</logic:empty> 