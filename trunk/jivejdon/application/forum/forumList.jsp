<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value="软件分析建模与架构设计社区" />
<%@ include file="../common/IncludeTop.jsp" %>

<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>

<table cellpadding="3" cellspacing="3" border="0" width="971" align="center">
<tr>
    <td valign="top" width="98%">
<p class="home_content" >欢迎光临中国知名的软件架构社区! 解惑授道，未来企业架构师孵化基地 
本论坛是基于JdonFramework自主开发的<a href="http://www.jdon.com/jdonframework/jivejdon3/">JiveJdon版本</a><br />

       
</p>
    </td>
</tr>
</table>


<script>


function lastPost(forumId, messageId){

   if (messageId == "" || forumId == "") return;	
   var pars = 'messageId=' +messageId ;
   new Ajax.Updater(forumId, '<%=request.getContextPath()%>/forum/lastPost.shtml', { method: 'get', parameters: pars });
   
}


</script>     

<table cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr><td width="98%" valign="top">

<table  width="100%" border="0" cellpadding="2" cellspacing="1"  align="center" bgcolor="#cccccc">
<tr><td bgcolor="#ffffff">
<table bgcolor="#cccccc" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr><td>
        <table bgcolor="#cccccc"
         cellpadding="6" cellspacing="1" border="0" width="100%">
        <tr bgcolor="#868602">
            <td width="1%"><html:img page="/images/blank.gif" width="1" height="1" border="0" alt=""/></td>
            <td width="97%">
             <table width="100%"><tr><td  width="80"><b><font color="#ffffff">论坛名称</font></b>
             </td><td>

            </td></tr></table>

            </td>
            <td width="1%" nowrap><b><font color="#ffffff">主题 / 消息</font></b></td>
            <td width="1%" nowrap align="center"><b><font color="#ffffff">最后更新</font></b></td>
        </tr>

<logic:iterate indexId="i"   id="forum" name="forumListForm" property="list" >
        <tr bgcolor="#FFFFEC">
            <td width="1%" align="center" valign="top">
                <html:img  page="/images/forum_old.gif" height="12" vspace="2" border="0" alt=""/>
            </td>
            <td width="97%">
                 <a href="<%=request.getContextPath()%>/<bean:write name="forum" property="forumId" />">                
                      <b><bean:write name="forum" property="name" /></b>
                 </a>
                <br>
                <span class="home_content" ><bean:write name="forum" property="description" filter="false"/></span>
            </td>
            <td width="1%" nowrap="nowrap" align="center" valign="top">
               <span class="home_content" > <bean:write name="forum" property="forumState.threadCount" /> / <bean:write name="forum" property="forumState.messageCount" /></span>
            </td>
            <td width="1%" nowrap="nowrap"  valign="top" >
       
            <span id="<bean:write name="forum" property="forumId" />"></span>
            <script>lastPost('<bean:write name="forum" property="forumId" />', '<bean:write name="forum" property="forumState.lastPost.messageId" />')</script>
            </td>
        </tr>

</logic:iterate>


        </table>
    </td></tr>
    </table>
</td></tr></table>


    

    <center>
    	<div id=vlinkgad2 ></div>
    	
    </center>
    </td>
    <td width="1%"><html:img page="/images/blank.gif" width="10" height="1" border="0" alt=""/></td>
    <td width="1%" valign="top">
    
     <table cellpadding="0" cellspacing="0" border="0" width="200">
     <tr><td valign="bottom">
  
     <html:form action="/query/searchAction.shtml"  method="post" styleClass="search"> 
        <input type="text"  name="query" size="15" id="queryId" onfocus="ac(this.id,'<%=request.getContextPath()%>')"/>
         <input type="image"  align="absmiddle" height="16" width="16" name="..." src="<%=request.getContextPath() %>/images/search.gif" onClick="document.messageListForm.submit()"/>
     </html:form>             
     
      <br/><center>
       <div id="hotkeys"></div>
      </center>
         
      </td>
        
      </tr></table>
    
    <div id="hotlist"></div>
    

    </td>
</tr></table>

<script type="text/javascript">
<!--
  function hotkeys(){
        new Ajax.Updater('hotkeys', '<%=request.getContextPath()%>/query/hotKeys.shtml?method=hotkeys', { method: 'get' });
   }
   
  hotkeys();
  
  function popList(){
        new Ajax.Updater('hotlist', '<%=request.getContextPath()%>/query/popularlist.shtml?count=8&length=8&tablewidth=190', { method: 'get' });
   }
   
  popList();

 TooltipManager.init('Users', 
  {url: '<html:rewrite page="/account/accountProfile.shtml?winwidth=250" />', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:260});

 TooltipManager.init('ForumLastPost', 
  {url: '<html:rewrite page="/query/forumLastPostViewAction.shtml" />', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:250});
   
      
//-->
</script>

<%@include file="../common/messageNotfier.jsp"%>


<%@include file="../common/IncludeBottom.jsp"%> 


