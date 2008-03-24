<%-- 
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<script language="JavaScript" type="text/javascript">
<!--
function tag(theTag) {
    var el = document.forms[0].body;
    if (el == null)
        el = document.forms[1].body;
    if (el == null)
        el = document.forms[2].body;
        
    if (theTag == 'b') {
        el.value += "[b][/b]";
    } else if (theTag == 'i') {
        el.value += "[i][/i]";
    } else if (theTag == 'u') {
        el.value += "[u][/u]";
    } else if (theTag == 'code') {
        el.value += "\n[code]\n// 在此输入java代码\n[/code]";
    } else if (theTag == 'image') {
        var url = prompt("请输入一个图片的URL","http://");
        if (url != null) {
            el.value += "[img]" + url + "[/img]";
        }
    } else if (theTag == 'url') {
        var url = prompt("请输入链接的URL","http://");
        var text = prompt("请输入链接文本");
        if (url != null) {
            if (text != null) {
                el.value += "[url=" + url + "]" + text + "[/url]";
            } else {
                el.value += "[url]" + url + "[/url]";
            }
        }
    }
}
function MM_openBrWindow(theURL,features) { //v2.0
   window.open(theURL,"adwin",features);

}

//-->
</script>


<script language="JavaScript" type="text/javascript">
<!-- 

function checkPost(theForm) {
     var check = false;
      if ((theForm.body.value  != "")
          && (theForm.subject.value  != "")){
          check = true;
          return check;
      }else{
          alert("请输入发言标题和发言内容！");
          return check;
      }
      if ((theForm.body.value.length  < <bean:write name="messageForm" property="bodyMaxLength"/>)){
          check = true;
          return check;
      }else{
          alert("请发言内容长度必须小于" + <bean:write name="messageForm" property="bodyMaxLength"/>);
          return check;          
      }
}
//-->
</script>

<table cellpadding="2" cellspacing="0" border="0">

<tr>
	<td align="right">标题</td>
	<td><html:text  property="subject" size="40" maxlength="75" tabindex="1"/></td>
</tr>


<tr>
    <td>&nbsp;</td>
	<td>
    <table cellpadding="0" cellspacing="0" border="0">
    <tr><td>
    	<a href="#" onclick="tag('b');return false;"
            ><html:img page="/images/b.gif" width="23" height="22" alt="粗体" border="0" 
            /></a>
        </td>
        <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
        <td>
    	<a href="#" onclick="tag('i');return false;"
            ><html:img page="/images/i.gif" width="23" height="22" alt="斜体" border="0"
            /></a>
        </td>
        <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
        <td>
    	<a href="#" onclick="tag('u');return false;"
            ><html:img page="/images/u.gif" width="23" height="22" alt="下划线" border="0"
            /></a>
        </td>
		 <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
        <td>
    	<a href="#" onclick="MM_openBrWindow('<html:rewrite page="/message/upload/uploadAction.shtml" paramId="parentId" paramName="messageForm" paramProperty="messageId"/>','width=450,height=400,scrollbars=yes')"
            ><html:img page="/images/image.gif" width="23" height="22" alt="插入图片" border="0" /></a>
        </td>
		 <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
      
        <td>
    	<a href="#" onclick="tag('code');return false;"
            ><html:img page="/images/code.gif" width="23" height="22" alt="插入代码" border="0"
            /></a>
        </td>
		 <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
        <td>
    	<a href="#" onclick="tag('url');return false;"
            ><html:img page="/images/link.gif" width="23" height="22" alt="插入url链接" border="0"/></a>
        </td>        
		 <td><ihtml:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
       <td>
        <a href="#" onclick="MM_openBrWindow('<html:rewrite page="/message/upload/uploadAction.shtml" paramId="parentId" paramName="messageForm" paramProperty="messageId"/>','width=450,height=400,scrollbars=yes')"
            ><html:img page="/images/file.gif" width="23" height="22" alt="插入附件" border="0" /></a>
            <input readonly size="10" name="attachsize" id="attachsize" value="" style="width:120px; border:0px solid Black; background:transparent;"> 
        </td>
       <td><html:img page="/images/blank.gif" width="5" height="1" border="0"/></td>
    </tr>
    </table>
	</td>
</tr>
<tr>
	<td valign="top" align="right">
	内容
	</td>

	<td>
	<html:textarea property="body" cols="65" rows="15" tabindex="2"></html:textarea>	
	</td>
</tr>

</table>

<table cellpadding="4" cellspacing="0" border="0">
<tr>
 <td width="20">&nbsp;</td>
 <td>
    <input type="submit" value="发 表" name="formButton" tabindex="3"> 
 </td>
</tr>
</table>