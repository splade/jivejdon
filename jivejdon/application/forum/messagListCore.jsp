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


<table bgcolor="#CFCFA0"
 cellpadding="3" cellspacing="0" border="0" width="971" align="center">
<tr><td align="left">

<div class="tres">         
<MultiPagesREST:pager actionFormName="messageListForm" page="/thread" paramId="thread" paramName="forumThread" paramProperty="threadId">
<MultiPagesREST:prev name="&#9668;" />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name="&#9658;" />
</MultiPagesREST:pager>
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go"><span class="pageGo">Go</span></a>
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
        &nbsp;<a href="javascript:void(0);" onclick="loadWLJSWithP('<bean:write name="forumThread" property="rootMessage.messageId" />',loadWPostjs)">
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


<div id="post_titleright" style="display: none;">
  <a title="关注本主题" href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=1&subscribeId=<bean:write name="forumThread" property="threadId" />"  rel="nofollow">
       <html:img page="/images/user_add.gif" width="18" height="18" alt="关注本主题" border="0" /></a>


       <logic:equal name="forumMessage" property="root" value="true">
             <a title="到本帖网址" href='<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId" />'>                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
             <a title="到本帖网址" href='<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />'  rel="nofollow"> 
       </logic:equal>                          
       <html:img page="/images/arrow_down.gif" width="18" height="18" alt="到本帖网址" border="0" /></a>
          
       <a title="加入本帖到收藏夹" href="JavaScript:void(0);" onclick="addfavorite('<bean:write name="forumMessage" property="messageVO.subject"/>')" >
       <html:img page="/images/album_add.gif" width="18" height="18" alt="加入本帖到收藏夹" border="0" /></a>
       
       <a title="网上收藏本主题" href="JavaScript:void(0);" onclick="loadWLJS(mark)" >
       <html:img page="/images/user_up.gif" width="18" height="18" alt="网上收藏本主题" border="0" /></a>
       
       
       <a title="手机条码扫描浏览本页" href="JavaScript:void(0);" onclick='loadWLJS(qtCode)'  >
       <html:img page="/images/phone.gif" width="18" height="18" alt="手机条码扫描浏览本页" border="0" /></a>
       
         <a title="请用鼠标选择需要回复的文字再点按本回复键" href="javascript:void(0);" onclick="loadWLJSWithP('<bean:write name="forumMessage" property="messageId"/>',loadQPostjs)">
        <html:img page="/images/document_comment.gif" width="18" height="18" border="0" alt="请用鼠标选择需要回复的文字再点按本回复键"/></a>
       
         <a title="回复该主题" href="javascript:void(0);" onclick="loadWLJSWithP('<bean:write name="forumMessage" property="messageId"/>',loadWPostjs)">
         <html:img page="/images/comment_reply.gif" width="18" height="18" border="0" alt="回复该主题"/></a>
                 
</div>

  
<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="971" align="center">
 <tr bgcolor="#FFFFCC">
    <td >
    <table cellpadding="3" cellspacing="0" border="0" width="100%" height="30"  >
     <tr><td  align="center">
        <html:link page="/query/tagsList.shtml?count=150" target="_blank" title="标签"><html:img page="/images/tag_yellow.png" width="16" height="16" alt="标签" border="0"/></html:link>
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
         <span onmouseover="loadWLJSWithP(this, initTagsW)" class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />(<bean:write name="threadTag" property="assonum" />)
             </a></span>
             &nbsp;&nbsp;&nbsp;&nbsp;
        </logic:iterate>
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
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>   
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

</div>

<logic:empty name="noheaderfooter">
<!--  上下主题 start -->
<%@include file="threadsPrevNext2.jsp"%>
<!--  上下主题 结束  -->

<center>
<div id="hotkeys"></div>
</center>

<table align="center"><tr><td valign="top" >
<div id=hotList>正在读取，请等待...</div>
</td><td  valign="top">
<div id="vgad336x280">
<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="336x280_2_b"/>   
</jsp:include>  
</div>
</td></tr></table>


<div id="approved" class="approved" ></div>


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


<script type="text/javascript" src="<html:rewrite page="/common/js/prototype.js"/>"></script>
<script language="javascript" >

var f = $$('.post_titleright');
for(var i=0; i<f.length; i++){	  
   f[i].innerHTML += $("post_titleright").innerHTML ;
}

hotList();
//hotkeys();



if (isDisplayNeedLoad('approved')){
    approveList();
}else{ 	 
   Event.observe(window, 'scroll', function() {
		setTimeout(function(){
		 if (isDisplayNeedLoad('approved')){	
					    approveList();
         }			
		},1500);
   });
}
      

      var pageURL = '<%=request.getContextPath() %>/thread/<bean:write name="forumThread" property="threadId"/>';
      var start = <bean:write name="messageListForm" property="start" />;
      var count = <bean:write name="messageListForm" property="count" />;
      var allCount = <bean:write name="messageListForm" property="allCount" />
      document.onkeydown=leftRightgoPageREST;
          
</script>

 <%@ include file="./messageNotfier.jsp" %>



<!-- JiaThis Button BEGIN -->
<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
<!-- JiaThis Button END -->


<%@include file="footer.jsp"%> 


   
</logic:empty> 