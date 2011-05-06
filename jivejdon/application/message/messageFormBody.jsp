<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:parameter id="reply" name="reply"  value="true"/>

<logic:notEmpty name="messageForm">     
<logic:notEmpty name="messageForm" property="messageId">  
  <logic:present  name="messageForm" property="root">   
     <logic:equal  name="messageForm" property="root" value="true">
         <bean:define id="reply" value="false"></bean:define>
     </logic:equal>
     <logic:equal  name="messageForm" property="root" value="false">
         <bean:define id="reply" value="true"></bean:define>
     </logic:equal>
  </logic:present>
</logic:notEmpty>
</logic:notEmpty>

<logic:notEmpty name="messageReplyForm">
   <bean:define id="messageForm" name="messageReplyForm" scope="request" />
   <bean:define id="reply" value="true"></bean:define>
</logic:notEmpty>


<script type="text/javascript">
<!--
var loadformjs = function(){
  if (typeof(checkPost) == 'undefined') {
   $LAB
   .script('<html:rewrite page="/forum/js/form.js"/>').wait()
   .wait(function(){
      loadPostjs();
   })     
  }else
    loadPostjs();
  
}



function openUploadWindowStart(url){
    <logic:notPresent name="principal" > 
       myalert("只有登录后才能打开上传页面");
       return;
    </logic:notPresent>     
    <logic:present name="principal" >       
      loadWLJSWithP(url, openUploadWindow);       
    </logic:present>     
    
 }
var bodyMaxLength = <bean:write name="messageForm" property="bodyMaxLength"/>; 
//-->
</script>

<table cellpadding="2" cellspacing="0" border="0" width="971" align="center">

<tr>
	<td width="50" align="right">标题</td>
	<td align="left"> <html:text  property="subject" styleId="replySubject" size="40" maxlength="90" tabindex="5" /></td>
</tr>


<tr>
    <td>&nbsp;</td>
	<td  align="left">
	<div onmouseover="loadWLJS(loadformjs)">
    <table cellpadding="0" cellspacing="0" border="0">
    <tr><td>
    	<a href="#" onclick="tag('b');return false;" title="粗体: [b]文本[/b]"
            ><html:img page="/images/b.gif" width="23" height="22" alt="粗体: [b]文本[/b]" border="0" 
            /></a>
        </td>
        <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
        <td>
    	<a href="#" onclick="tag('i');return false;"  title="斜体: [i]文本[/i]"
            ><html:img page="/images/i.gif" width="23" height="22" alt="斜体: [i]文本[/i]" border="0"
            /></a>
        </td>
        <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
        <td>
    	<a href="#" onclick="tag('u');return false;"  title="下划线 [u]文本[/u]"
            ><html:img page="/images/u.gif" width="23" height="22" alt="下划线 [u]文本[/u] " border="0"
            /></a>
        </td>
		 <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
        <td>
         <a href="#" onclick="tag('image');return false;" 
         title="插入网上图片"><html:img page="/images/image.gif" width="23" height="22" alt="插入网上的图片 [img]http://wwww.xxxx.com/img.ext[/img]" border="0" 
            /></a>
        </td>
		 <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
      
        <td>
    	<a href="#" onclick="tag('code');return false;"  title="插入代码 [code]程序代码[/code]"
            ><html:img page="/images/code.gif" width="23" height="22" alt="插入代码 [code]程序代码[/code]  " border="0"
            /></a>
        </td>
		 <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
        <td>
    	<a href="#" onclick="tag('url');return false;"  title="插入url链接 [url]http://url[/url] / [url=http://url]URL加下滑线[/url] "
            ><html:img page="/images/link.gif" width="23" height="22" alt="插入url链接 [url]http://url[/url] / [url=http://url]URL加下滑线[/url] " border="0"/></a>
        </td>        
		 <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
       <td>
        <a title="上传图片 word文档 Txt等附件" href="javascript:openUploadWindowStart('<html:rewrite page="/message/upload/uploadAction.shtml" paramId="parentId" paramName="messageForm" paramProperty="messageId"/>')"
            ><html:img page="/images/file.gif" width="23" height="22" alt="上传图片 word文档 Txt等附件" border="0" 
            /></a><span id="attachsize" ></span>                         	
	    <logic:equal name="messageForm" property="attached" value="true">
	    <bean:size name="messageForm" property="uploadFiles" id="attachedSize"/>	    
	    <SCRIPT language = "Javascript">
              document.getElementById("attachsize").innerHTML = "有<bean:write name="attachedSize"/>个附件";
        </script>
	</logic:equal>
        </td>
       <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
    </tr>
    </table>
    </div>
	</td>
</tr>
<tr>
	<td valign="top" align="right">
	内容
	</td>

	<td align="left">
	<html:textarea property="body" cols="100" rows="40" styleClass="tpc_content" styleId="formBody" tabindex="6" onkeydown="releaseKeyboard()" onfocus="loadWLJS(loadformjs)"  ></html:textarea>	<%-- onclick="startCopy(300000)" --%>
	</td>
</tr>

</table>

<table cellpadding="4" cellspacing="0" border="0" width="971" align="center">
<logic:equal name="reply" value="false">
<tr>
	<td  width="50" align="right">标签</td>
	<td align="left">
	<script>
	
function loadAcJS(thisId){
  if (typeof(ac) == 'undefined') {
     $LAB
     .script('<%=request.getContextPath()%>/common/js/autocomplete.js')
     .wait(function(){
          ac(thisId,'<%=request.getContextPath()%>');
     })     
  }else
      ac(thisId,'<%=request.getContextPath()%>');
}
		
		
	</script>
	<input  type="text" name="tagTitle" size="15" maxlength="25" id="searchV_0" onfocus="javascript:loadAcJS(this.id)" value='' />
	<input  type="text" name="tagTitle" size="15" maxlength="25" id="searchV_1" onfocus="javascript:loadAcJS(this.id)"   value=''/>	
    <input  type="text" name="tagTitle" size="15" maxlength="25" id="searchV_2" onfocus="javascript:loadAcJS(this.id)"  value=''/>
    <input  type="text" name="tagTitle" size="15" maxlength="25" id="searchV_3" onfocus="javascript:loadAcJS(this.id)"  value=''/>
    <logic:notEmpty name="messageForm" property="forumThread.tags">
         <logic:iterate id="threadTag" name="messageForm" property="forumThread.tags" indexId="i">
             <script>
             $('searchV_<bean:write name="i"/>').value ='<bean:write name="threadTag" property="title" />'             
             </script>
        </logic:iterate>
     </logic:notEmpty>        
		<span id='json_info'></span>
	</td>
</tr>
</logic:equal>
<tr>
 <td width="50">&nbsp;</td>
 <td>
    提交时自动拷贝以上内容到剪贴板 Ctrl+V可取出；提问题前先查询<html:link page="/query/tagsList.shtml?count=200" target="_blank">标签列表</html:link>
    <br>
    <input type="submit" value=" 发 表 Ctrl+Enter " name="formButton" id="formSubmitButton" tabindex="3"> 
    <logic:equal name="reply" value="false">
       如有回复通知我<input type="checkbox" name="replyNotify" checked="checked">
    </logic:equal>
    
		
 </td>
</tr>
</table>
<div id="insertImage" style='display: none'>
<html:img page="/images/logout.gif" width="23" height="22" border="0" />
</div>
