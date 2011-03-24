<%@ page contentType="text/html; charset=UTF-8" %>


<a name="<bean:write name="forumMessage" property="messageId"/>"></a>

<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr>
 <td>
    <table bgcolor="#cccccc" cellpadding="6" cellspacing="1" border="0" width="100%">

    <tr bgcolor="<%=bgcolor%>">
        <td width="1%" rowspan="2" valign="top">
        <table cellpadding="0" cellspacing="0" border="0" width="160">
        <tr><td>
          <logic:notEmpty name="forumMessage" property="account">
          
          <html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.username">
            <span  class='Users ajax_userId=<bean:write name="forumMessage" property="account.userId"/>' >                      
              <b> <span  id='author_<bean:write name="forumMessage" property="messageId"/>'><bean:write name="forumMessage" property="account.username" /></span></b>                       
          <br><br/>
           <logic:notEmpty name="forumMessage" property="account.uploadFile">
            <img  src="<%=request.getContextPath() %>/img/uploadShowAction.shtml?oid=<bean:write name="forumMessage" property="account.userId"/>&id=<bean:write name="forumMessage" property="account.uploadFile.id"/>"  border='0' />
           </logic:notEmpty>
           </span>           
		  <br/>个人详介按这里<br/>
          </html:link>	
          	  
           <html:link page="/query/threadViewQuery.shtml?queryType=userMessageQueryAction" paramId="user" paramName="forumMessage" paramProperty="account.userId" target="_blank">
           <span class="smallgray"> 发表文章:
              <logic:greaterThan name="forumMessage" property="account.messageCount" value="0">
                 <bean:write name="forumMessage" property="account.messageCount"/>
              </logic:greaterThan>
           </html:link>
           </span>
           <%-- </html:link> --%>          
            <br>
           <span class="smallgray"> 注册时间: <bean:write name="forumMessage" property="account.creationDate"/></span>
           <br>
         <logic:notEmpty name="forumMessage" property="account.username">
             <bean:define id="messageTo" name="forumMessage" property="account.username" />
             <a href="javascript:void(0);" onclick="openShortmessageWindow('<html:rewrite page="/account/protected/shortmessageAction.shtml" paramId="messageTo"  paramName="messageTo" />');">          
             <html:img page="/images/user_comment.gif" width="18" height="18" border="0" align="absmiddle"/><span class="smallgray">悄悄话</span>
            </a>     
            <br>
            <html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.username"
            ><html:img page="/images/digg.gif" width="16" height="16" border="0" align="absmiddle"/><span class="smallgray">个人博客</span>
            </html:link>
               
            <br><html:img page="/images/users.gif" width="18" height="18" alt="在线?" border="0" align="absmiddle"/>            
            <bean:define id="username" name="forumMessage" property="account.username" />           
             <jsp:include page="./onlineCheck.jsp" flush="true">   
                  <jsp:param name="username" value="<%=username%>"/>   
             </jsp:include>  
            
         </logic:notEmpty> 
              
        	<span id="messageOwnerOnline_<bean:write name="forumMessage" property="messageId"/>"></span>  
          
           <br>
            <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=3&subscribeId=<bean:write name="forumMessage" property="account.userId"/>"
                target="_blank" title="我要关注该作者发言"  rel="nofollow">            
            <html:img page="/images/user_add.gif" width="18" height="18" alt="我要关注该作者发言" border="0" align="absmiddle"/></a>
             <logic:greaterThan name="forumMessage" property="account.subscriptionCount" value="0">
                 <bean:write name="forumMessage" property="account.subscriptionCount"/>人关注
              </logic:greaterThan>
            
            
             </logic:notEmpty>           
            </td>
        </tr>       
        </table>
        </td>
        <td >
 
  <table width="100%"  cellpadding="1" cellspacing="1">
    <tr>
        <td width="97%">
        <span id='subject_<bean:write name="forumMessage" property="messageId"/>'><bean:write name="forumMessage" property="messageVO.subject"/></span>
        </td>
        <td width="1%" nowrap="nowrap">
        <span class="smallgray" id='creationDate_<bean:write name="forumMessage" property="messageId"/>'><bean:write name="forumMessage" property="creationDate" /></span>
        </td>
       <td width="1%" nowrap="nowrap" align="center">
       
        <a title="收藏关注本主题" href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=1&subscribeId=<bean:write name="forumThread" property="threadId" />"  rel="nofollow">
       <html:img page="/images/user_add.gif" width="18" height="18" alt="收藏关注本主题" border="0" /></a>


       <logic:equal name="forumMessage" property="root" value="true">
             <a title="到本帖网址" href='<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId" />'>                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
             <a title="到本帖网址" href='<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />'  rel="nofollow"> 
       </logic:equal>                          
       <html:img page="/images/arrow_down.gif" width="18" height="18" alt="到本帖网址" border="0" /></a>
          
       <a title="加入本帖到收藏夹" href="JavaScript:void(0);" onclick="addfavorite('<bean:write name="forumMessage" property="messageVO.subject"/>')" >
       <html:img page="/images/album_add.gif" width="18" height="18" alt="加入本帖到收藏夹" border="0" /></a>
       
       <a title="网上收藏本主题" href="JavaScript:void(0);" onclick="mark('<%=request.getContextPath()%>')" >
       <html:img page="/images/user_up.gif" width="18" height="18" alt="网上收藏本主题" border="0" /></a>
       
       
       <a title="手机条码扫描浏览本页" href="JavaScript:void(0);" onclick='qtCode()'  >
       <html:img page="/images/phone.gif" width="18" height="18" alt="手机条码扫描浏览本页" border="0" /></a>
       
         <a title="请用鼠标选择需要回复的文字再点按本回复键" href="javascript:void(0);" onclick="loadQPostjs('<bean:write name="forumMessage" property="messageId"/>')">
        <html:img page="/images/document_comment.gif" width="18" height="18" border="0" alt="请用鼠标选择需要回复的文字再点按本回复键"/></a>
       
         <a title="回复该主题" href="javascript:void(0);" onclick="loadWPostjs('<bean:write name="forumMessage" property="messageId"/>')">
         <html:img page="/images/comment_reply.gif" width="18" height="18" border="0" alt="回复该主题"/></a>
                 
       <logic:equal name="messageListForm" property='<%= "authenticated[" + i + "]" %>' value="true">
           <html:link page="/message/messageAction.shtml?action=edit" paramId="messageId" paramName="forumMessage" paramProperty="messageId"
            >编辑</html:link>       
       </logic:equal>
       <logic:equal name="i" value="0">
       <logic:notEmpty name="principal">  
       <logic:equal name="loginAccount" property="roleName" value="Admin">
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','announce_all','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">全区公告</a>
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','sticky_all','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">全区置顶</a>
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','sticky','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">置顶</a>
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','announce','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">公告</a>  
	      
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','announce_all','delete','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">取消该贴为置顶或者公告</a>  
	      
	     </logic:equal>  
	     </logic:notEmpty>
       </logic:equal>  
          </td>
    </tr>
    <logic:equal name="i" value="0"> 
     <tr bgcolor="<%=bgcolor%>"> 
        <td colspan="3">
        <html:link page="/query/tagsList.shtml?count=150" target="_blank" title="标签"><html:img page="/images/tag_yellow.png" width="16" height="16" alt="标签" border="0"/></html:link>
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
           <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />
           </a>
              </span>
             &nbsp;&nbsp;&nbsp;&nbsp;
        </logic:iterate>
         
       </td> 
    </tr> 
    </logic:equal>
   </table>
   
         </td>
    </tr>
    <tr bgcolor="<%=bgcolor%>">
        <td width="99%" colspan="4" valign="top">
        <table width="100%" border="0" cellspacing="2" cellpadding="2"  style='TABLE-LAYOUT: fixed'>
          <tr>
            <td style='word-WRAP: break-word'>

<DIV class=digg-row>
<SPAN class=diggArea>	
  <DIV class=diggNum id="digNumber_<bean:write name="forumMessage" property="messageId"/>">
  <logic:notEqual name="forumMessage" property="digCount" value="0">
    <bean:write name="forumMessage" property="digCount"/>
  </logic:notEqual>
  </DIV>
  <DIV class="diggLink top8" id="textArea_<bean:write name="forumMessage" property="messageId"/>"><a href="javascript:digMessage('<bean:write name="forumMessage" property="messageId"/>')">顶一下</a></DIV> 	
</SPAN>
</DIV>           
<span class="tpc_content" id='body_<bean:write name="forumMessage" property="messageId"/>'><bean:write name="forumMessage" property="messageVO.body" filter="false"/></span>
<logic:equal name="i" value="0"> 
    <table width="1%" border="0" cellpadding="0" cellspacing="5" > 
    <tr> <td> <!-- advert -->   
      <div id="advert_468x60"></div>
    </td></tr></table> 
             
</logic:equal>

       
        </td></tr>
        </table>
        </td>
    </tr>
    </table>

</td></tr>
</table>

             
