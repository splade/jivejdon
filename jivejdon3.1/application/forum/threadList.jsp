<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-tiles" prefix="tiles" %> 
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="threadList" name="threadListForm" property="list" />

<bean:define id="forum" name="threadListForm" property="oneModel" />
<bean:define id="title" name="forum" property="name" />
<%@ include file="../common/IncludeTop.jsp" %>

<!-- 导航区  -->
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
  <html:form action="/query/searchThreadAction.shtml"  method="get" styleClass="search" >
    <td valign="top" width="50%">
    <%@include file="nav.jsp"%>        
    </td>    
    <td valign="top"  align="right">
        <input type="text"  name="query"  size="25"  />
        <html:submit value="搜索"/>    
    </td>

</tr>
</table>
   </html:form>

<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td  class="smallgray">
     本论坛共有<b><bean:write name="threadListForm" property="allCount"/></b>贴 [ 
<MultiPages:pager actionFormName="threadListForm" page="/forum.jsp" paramId="forum" paramName="forum" paramProperty="forumId">
<MultiPages:prev>上一页<html:img  page="/images/prev.gif" width="10" height="10" hspace="2" border="0" alt="上页"/></MultiPages:prev>
<MultiPages:index />
<MultiPages:next><html:img  page="/images/next.gif" width="10" height="10" hspace="2" border="0" alt="下页"/>下一页</MultiPages:next>
</MultiPages:pager>     
      ]
    </td>
    <td align="right">
        &nbsp;<a href="#post" ><html:img page="/images/newtopic.gif" width="113" height="20" border="0"/></a>
    </td>
</tr>
</table>

<table  width="100%" border="0" cellpadding="2" cellspacing="1"  align="center" bgcolor="#cccccc">
<tr><td bgcolor="#ffffff">

<%@ include file="threadListCore.jsp" %>


<table bgcolor="#cccccc" cellpadding="1" cellspacing="0" border="0" width="100%">
<tr>
    <td>
<table bgcolor="#eeeeee" cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
 <td>
   
    </td>
    <td align="right"  class="smallgray">
     本论坛共有<b><bean:write name="threadListForm" property="allCount"/></b>贴 [ 
<MultiPages:pager actionFormName="threadListForm" page="/forum.jsp" paramId="forum" paramName="forum" paramProperty="forumId">
<MultiPages:prev>上一页<html:img  page="/images/prev.gif" width="10" height="10" hspace="2" border="0" alt="上页"/></MultiPages:prev>
<MultiPages:index />
<MultiPages:next><html:img  page="/images/next.gif" width="10" height="10" hspace="2" border="0" alt="下页"/>下一页</MultiPages:next>
</MultiPages:pager>     
      ]</td>
   
</tr>
</table>
    </td>
</tr>
</table>

</td></tr></table>

<center>
 <jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="728x15"/>   
 </jsp:include>  
</center>

<center>
<table><tr><td>
<IFRAME  MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 WIDTH="500" HEIGHT="290"  scrolling="no" 
SRC="<%=request.getContextPath()%>/query/hotlist.shtml?dateRange=3650&messageReplyCountWindow=30&tablewidth=500&length=15&count=200"></iframe>
</td><td>

<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="200x200"/>   
</jsp:include>
</td></tr></table>
</center>

<center>
热点TAG:<tiles:insert definition=".hotkeys"></tiles:insert>
</center>

<html:form action="/query/threadViewQuery.shtml" method="post">
<html:hidden  name="queryForm" property="queryType" value="HOT1"/>
<input type="hidden"  name="forumId"  value="<bean:write name="forum" property="forumId"/>"/> 
  <table cellspacing="1" cellpadding="0" width="90%" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>            
              <td  align="middle" >              
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

<a name="post"></a>
<html:form action="/message/messageSaveAction.sthml" method="post"  onsubmit="return checkPost(this);" >
<input type="hidden" name="forum.forumId" value="<bean:write name="forum" property="forumId"/>"/>
<html:hidden property="messageId" />
<html:hidden property="action" value="create"/>
快速发帖
<%@ include file="../message/messageFormBody.jsp" %>

</html:form>

<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="bottom"/>   
</jsp:include>  
 
<%@include file="../common/IncludeBottom.jsp"%>


