<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<table bgcolor="#cccccc" 
 cellpadding="0" cellspacing="0" border="0" width="100%"> 
<tr><td>
    <table bgcolor="#cccccc" id="ejiaA1"
     cellpadding="6" cellspacing="1" border="0" width="100%">
     <tbody >
      <tr bgcolor="#868602" background="<%=request.getContextPath()%>/images/tableheadbg.gif" height="30" id="ejiaA1_title_tr">     
        <td width="5%"   align="center" nowrap><font color="#ffffff">标签</font></td>
        <td width="80%"  align="center" nowrap>
          <b><font color="#ffffff">&nbsp; 主题名</font></b>
        </td>
        <td width="5%"  align="center" nowrap><b><font color="#ffffff">&nbsp; 阅读 &nbsp;</font></b></td>
        <td width="5%"  align="center" nowrap><b><font color="#ffffff">&nbsp; 回复 &nbsp;</font></b></td>
        <td width="5%" align="center" nowrap><b><font color="#ffffff">&nbsp; 作者 &nbsp;</font></b></td>
        <td width="5%" align="center" nowrap>
     
        <logic:present name="ASC">
           <a href="<%=request.getContextPath()%>/<bean:write name="forum" property="forumId" />">
           <b><font color="#ffffff">最近更新</font></b>   
           <html:img page="/images/desc_order.png" border="0" width="11" height="11" alt="最新在前排列" title="最新在前排列"/>
           </a>   
        </logic:present>
        <logic:notPresent name="ASC">
           <html:link page="/forum.jsp?ASC" paramId="forum" paramName="forum" paramProperty="forumId"
           ><b><font color="#ffffff">最近更新</font></b>
           <html:img page="/images/asc_order.png" border="0" width="11" height="11" alt="最早在前排列" title="最早在前排列"/>
           </html:link>        
        </logic:notPresent>
         </td>          
    </tr>


<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
    <tr bgcolor="#FFFFEC" id="tr_<bean:write name="forumThread" property="threadId" />">
        <td nowrap="nowrap">
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
             <bean:write name="threadTag" property="title" /></span>
             </a>
             <br>             
        </logic:iterate>
        </td>
        <td>        
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
              >
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <bean:write name="forumThread" property="name" />                          
             </span></b></a>
             
             <logic:greaterEqual  name="forumThread" property="state.messageCount" value="15">
             <script>             
               document.write(generateLinkString("<%=request.getContextPath()%>", '<bean:write name="forumThread" property="state.messageCount" />', "<bean:write name="forumThread" property="threadId"/>", "15"));
             </script>
             </logic:greaterEqual>
             
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 (<bean:write name="forumThread" property="state.subscriptionCount"/>人关注)
              </logic:greaterThan>
              
            <!-- for prototype window TooltipManager.init -->
             <div id="tooltip_content_<bean:write name="forumThread" property="threadId"/>" style="display:none">
               <div class="tooltip_content">
                <span class="tpc_content">
                 <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />
                 </span>
               </div>
             </div>

        </td>
        <td align="center">
            <bean:write name="forumThread" property="state.viewCount" />            
        </td>
        <td align="center">
            <bean:write name="forumThread" property="state.messageCount" />            
        </td>
        <td nowrap="nowrap">
            &nbsp;
            <bean:define id="rootMessage" name="forumThread" property="rootMessage"></bean:define>
            <logic:notEmpty name="rootMessage"  property="account">            
            <html:link page="/profile.jsp" paramId="user" paramName="rootMessage" paramProperty="account.username"
            target="_blank" >
            <span class='Users ajax_userId=<bean:write name="rootMessage" property="account.userId"/>' >
                <b><bean:write name="rootMessage" property="account.username" /></b>            
            </span>
            </html:link>
             </logic:notEmpty>

            &nbsp;
        </td>
        <td nowrap="nowrap">
           <logic:notEmpty name="forumThread" property="state.lastPost">
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>
            <span  class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
            <bean:write name="lastPost" property="modifiedDate" />
            </span>
            <br> by:
            <logic:equal name="lastPost" property="root" value="true">
                   <a href='<%=request.getContextPath()%>/thread/<bean:write name="lastPost" property="forumThread.threadId" />' target="_blank" >                    
            </logic:equal>
            <logic:equal name="lastPost" property="root" value="false">
                   <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />'  rel="nofollow"> 
            </logic:equal>
                    <span class='Users ajax_userId=<bean:write name="lastPost" property="account.userId"/>' >
                    <bean:write name="lastPost" property="account.username" /></span></a>
        </logic:notEmpty>
        <%-- 
           <span id="<bean:write name="forumThread" property="threadId" />"></span>
            <script>lastPost('<bean:write name="forumThread" property="threadId" />', '<bean:write name="forumThread" property="state.lastPost.messageId" />')</script>
            --%>
           
        </td>
    </tr>
</logic:iterate>

      </tbody>
    </table>
    </td></tr>
</table>

<script language="javascript">
addStickyList('ejiaA1_title_tr','<bean:write name="forum" property="forumId"/>');
ejiaA1("ejiaA1","#fff","#F5F5F5","#FFFFCC","#FFFF84");
</script>


<script>
if (typeof(TooltipManager) != "undefined"){

 TooltipManager.init('Users', 
  {url: '<html:rewrite page="/account/accountProfile.shtml?winwidth=250" />', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:260});  

 TooltipManager.init('ThreadLastPost', 
  {url: '<html:rewrite page="/query/threadLastPostViewAction.shtml" />', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:150});
   
    TooltipManager.init('Tags', 
  {url: '<html:rewrite page="/query/tt.shtml?tablewidth=400&count=10" />', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:400});  
}
</script>
