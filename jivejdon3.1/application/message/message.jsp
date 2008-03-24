<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="forum" name="messageForm" property="forum"  />
<bean:define id="title" name="forum" property="name" />
<%@ include file="messageHeader.jsp" %>


<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td><html:img page="/images/blank.gif" width="1" height="10" border="0" alt=""/></td></tr>
</table>

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
    <td valign="top" width="98%">

      <%@include file="nav.jsp"%>    
    
    </td>
    <td valign="top" width="1%" align="center">
    
    </td>
</tr>
</table>


<html:form action="/message/messageSaveAction.sthml" method="post"  onsubmit="return checkPost(this);" >
<html:hidden property="action" />
<html:hidden property="messageId" />
<html:hidden property="forumThread.threadId" />

<table cellpadding="4" cellspacing="0" border="0">

<tr>
	<td align="right" width="10"> </td>
	<td> 在
     <html:select name="messageForm" property="forum.forumId">
       <html:option value="">请选择</html:option>
       <html:optionsCollection name="forumListForm" property="list" value="forumId" label="name"/>       
     </html:select>
    中操作帖.</td>
</tr>
</table>

<logic:equal name="messageForm" property="authenticated" value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="messageForm" property="authenticated" value="true">
  
<%@ include file="messageFormBody.jsp" %>

<table cellpadding="4" cellspacing="0" border="0" width="400">
<tr>
	<td width="20">&nbsp;</td>
  <logic:equal name="messageForm" property="action" value="edit">
	<td>
       <html:link page="/message/messageDeleAction.shtml?" paramId="messageId" paramName="messageForm" paramProperty="messageId">
       删除本贴(只有无跟贴才可删除)
       </html:link>	
	</td>
	 <logic:equal name="messageForm" property="masked" value="true">
      <td > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=false" paramId="messageId" paramName="messageForm" paramProperty="messageId" >
        取消屏蔽
        </html:link></td>   
     </logic:equal>           
        
    <logic:notEqual name="messageForm" property="masked" value="true">
      <td > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=true" paramId="messageId" paramName="messageForm" paramProperty="messageId" >
       屏蔽该贴
        </html:link></td>    
     </logic:notEqual>        
     
   </logic:equal>  
</tr>
</table>

</logic:equal>   



</html:form>

<p><%@include file="../common/IncludeBottom.jsp"%></p>


