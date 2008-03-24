<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value="Java/JavaEE/J2EE/JEE/JSP论坛 软件设计社区" />
<%@ include file="../common/IncludeTop.jsp" %>

<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
    <td valign="top" width="98%">
<p>欢迎光临中国知名的最专业的Java及OO设计论坛! 倡导理性专业思维，请勿发表非专业言论。 
本论坛是基于JdonFramework自主开发的JiveJdon3.0，<a href="http://www.jdon.com/jdonframework/jivejdon3/">按这里免费下载JiveJdon3源码</a>
    </td>
</tr>
</table>


<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td width="98%" valign="top">

<table  width="100%" border="0" cellpadding="2" cellspacing="1"  align="center" bgcolor="#cccccc">
<tr><td bgcolor="#ffffff">
<table bgcolor="#cccccc" cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr><td>
        <table bgcolor="#cccccc"
         cellpadding="4" cellspacing="1" border="0" width="100%">
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
        <tr bgcolor="#ECEEF4">
            <td width="1%" align="center" valign="top">
                <html:img  page="/images/forum_old.gif" height="12" vspace="2" border="0" alt=""/>
            </td>
            <td width="97%">
                 <html:link page="/forum.jsp"  paramId="forum" paramName="forum" paramProperty="forumId">
                      <b><bean:write name="forum" property="name" /></b>
                 </html:link>
                <br>
                <bean:write name="forum" property="description" filter="false"/>
            </td>
            <td width="1%" nowrap align="center" valign="top">
                <bean:write name="forum" property="forumState.threadCount" /> / <bean:write name="forum" property="forumState.messageCount" />
            </td>
            <td width="1%" nowrap valign="top" >
            <logic:notEmpty name="forum" property="forumState.lastPost">
                    <bean:define id="lastPost" name="forum" property="forumState.lastPost"/>
                    <logic:notEmpty name="lastPost">
                          <bean:write name="lastPost" property="modifiedDate" /> 
  
                           <br>
                    by:<span id="menu_<bean:write name="lastPost" property="messageId"/>" 
             onmouseover="showMenu(this.id,'<html:rewrite page="/account/accountProfile.shtml?winwidth=160" paramId="userId" paramName="lastPost" paramProperty="account.userId"/>','URL:200:200')"> 
                    <a href="<html:rewrite page="/forum/messageList.shtml" paramId="thread" paramName="lastPost" paramProperty="forumThread.threadId" 
                    />&message=<bean:write name="lastPost" property="messageId" 
                    />#<bean:write name="lastPost" property="messageId" />"                      
                         ><bean:write name="lastPost" property="account.username" /></a>
                   </span>
                   </logic:notEmpty>
            </logic:notEmpty>
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
    <td width="1%" nowrap valign="top">
     <table cellpadding="1" cellspacing="0" border="0" width="180">
         
    <tr><td>
     <html:form action="/query/searchAction.shtml"  method="get" styleClass="search"> 
        <input type="text"  name="query" size="15" />
         <html:submit value="搜索"/>
         <br/><center><tiles:insert definition=".hotkeys"></tiles:insert></center>
         </html:form>    
      </td>
        
      </tr></table>

    
     <IFRAME  id="hotlist" MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 
     WIDTH="160" HEIGHT="280"  scrolling="no"
     SRC="<%=request.getContextPath()%>/query/popularlist.shtml?count=8&length=8"></iframe>
    

    </td>
</tr></table>

<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="bottom"/>   
 </jsp:include>  
 

<%@include file="../common/IncludeBottom.jsp"%> 


