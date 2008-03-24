<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
  <head>
    <title>上传文件</title>
    <link href="<html:rewrite page="/jivejdon.css"/>" rel="stylesheet" type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
      <html:base/>
    </head>
    <body onmouseover=cal()>
<html:errors />

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
  </logic:iterate>
</logic:present>


<logic:equal name="upLoadFileForm" property="authenticated" value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="upLoadFileForm" property="authenticated" value="true">

<div style="font-size:12px; padding:5px;padding-left:20px;line-height:20px">
1.附件在互联网上,直接输入url网址后,按"确定"按钮。
<br />  
2.附件在自己硬盘上,请用"浏览"选中硬盘上的附件后,按"上传"按钮。每个帖子最多传三个附件。 
<br /> 
3.上传附件有效类型:
 <logic:iterate id="fileType" name="upLoadFileForm" property="fileTypes" >
  <bean:write name="fileType"/>
</logic:iterate> 

4.上传附件最大:100 (K)<!-- 见struts-config-upload.xml中配置  --> 
</div>
<html:form action="/message/upload/saveUploadAction.shtml" enctype="multipart/form-data" onsubmit="return uploadValid(document.upLoadFileForm.theFile.value)">

<html:hidden property="action" />
<html:hidden property="id" />
<html:hidden property="tempId" />
<html:hidden property="parentId" />
<html:hidden property="parentName" />
<input type="hidden" name="path"  value="<%=request.getContextPath()%>"/>
附件:<html:file property="theFile" size="30" /> 
<html:submit property="submit" value="上传"/>

<br>
说明:<html:text property="description" size="30" />


<html:form action="/message/upload/saveUploadAction.shtml" method="post" >
        <input type="hidden" name="action"  value="delete">
        <input type="hidden" name="forward" id="forward" value="read">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#dddddd">
          <tr class="list" height="24">
            <td width="20">&nbsp;</td>
            <td width="350">文件</td>
            <td width="50">大小</td>
            <td width="52">&nbsp;</td>
          </tr>
     <% int allSize=0;  %>
     <logic:iterate id="upLoadFile" name="upLoadFileListForm" property="list" indexId="tempId">
          
                <tr><td height="1" colspan="4" bgcolor="#cccccc"></td></tr>
                    <tr class="list">
            <td width="20" bgcolor="#EFECEC">
            </td>
            <td width="350" bgcolor="#EFECEC"><bean:write name="upLoadFile"  property="name"/></td>
            <td width="50"  bgcolor="#EFECEC"><bean:write name="upLoadFile"  property="size"/>K</td>
            <td width="52" bgcolor="#EFECEC"> 
            <bean:parameter id="parentId" name="parentId" value=""/>
            <a href="<%=request.getContextPath()%>/message/upload/saveUploadAction.shtml?action=delete&tempId=<bean:write name="tempId" />&id=<bean:write name="upLoadFile" property="id" />&parentId=<bean:write name="parentId"/>"
            >
            <html:img page="/images/button_delete.gif" alt="删除"/> 
            </a> </td>
          </tr>
          <bean:define id="count" name="upLoadFile"  property="size" type="java.lang.Integer"/>
          
          <%allSize = allSize+ count.intValue();%>
     </logic:iterate>
          
          <tr><td height="1" colspan="4" bgcolor="#dddddd"></td></tr>
                    <tr class="list" height="25">
            <td width="20">&nbsp;</td>
            <td width="350">总　　计</td>
            <td width="50"><%=allSize%> k</td>
            <SCRIPT language = "Javascript">
              if(window.opener.document.all.attachsize!=null){	
                window.opener.document.all.attachsize.value = "附件大小总共<%=allSize%> k";
              }
            </script>
            <td width="52">&nbsp;</td>
          </tr>
        </table>
        
</html:form>

<SCRIPT language = "Javascript">
<!--

function getFullImageTag(ImageFileUrl){
    var fullTage = "[img]" + ImageFileUrl + "[/img]";
    return fullTage;
}

function getFullFileTag(FileUrl){             
    var fullTage = "[url=" + FileUrl + "]" + theFile + "[/url]";
    return fullTage;
}


function urlAction(theFile){

     if ((theFile == null) || (theFile == "")) {
        window.close();
        return;
     }
   	 if ((theFile.toLowerCase().indexOf("c:") > -1) ||
	    (theFile.toLowerCase().indexOf("d:") > -1) ||
		(theFile.toLowerCase().indexOf("e:") > -1) ||
		(theFile.toLowerCase().indexOf("f:") > -1))
	  {
        alert("请按上传！");
         return false;
      }
      
      if(window.opener.document.all['body']!=null){	
	     if (isImage(theFile)){
    	     imgtext="[img]"+theFile+"[/img]";
             window.opener.document.all['body'].value += imgtext;
             window.close();
	     
	     }else{
             filetext="[url="+theFile+"]"+theFile+"[/url]";
             window.opener.document.all['body'].value += filetext;
             window.close();
         }
      }
}

function uploadValid(field){
	if (field.toLowerCase().indexOf("http://") > -1){
        alert("必须提供你硬盘上文件上传");
         return false;
	}
    if (isAuth(field)){
       return true;
    }else{
       alert("对不起，上传附件文件的类型不在允许的类型之中");
       return false;
   }
}

function isAuth(field){
  <logic:iterate id="fileType" name="upLoadFileForm" property="fileTypes" >
     if (field.toLowerCase().indexOf(".<bean:write name="fileType"/>") > -1){
         return true;
     }
     
   </logic:iterate> 
   return false;
}

function isImage(field){
    if ((field == null) || (field == "")) {
        return false;  
     }
     <logic:iterate id="imageType" name="upLoadFileForm" property="imagesTypes" >
     if (field.toLowerCase().indexOf(".<bean:write name="imageType"/>") > -1){
         return true;
     }
     
   </logic:iterate> 
     return false;  
}

-->
//-->
</script>

<!-- 自动显示图片 -->
<tr><td  align=center colspan=2>
<html:img imageName="preview" page="/images/white_pix.gif"/>
<input type="hidden" name="oldwidth" >
<input type="hidden" name="oldheight" >


<script>
<!--
function cal()
{
var maxwidth=300;
var maxheight=300;
var x;
var y;
try
{
  
   if (isImage(document.all.theFile.value)){
      document.all.preview.src=document.all.theFile.value;
   }


  if(document.preview.width >maxwidth)
    {

        x=document.preview.width;
        y=document.preview.height;

        nx=maxwidth;
        ny=maxwidth*y/x;
        document.preview.width=nx;
        document.preview.height=ny;
    }
     if(document.preview.height >maxheight)
    {
        x=document.preview.width;
        y=document.preview.height;
        ny=maxheight;
        nx=maxheight*x/y;
        document.preview.width=nx;
        document.preview.height=ny;
    }
    }
    catch(e)
    {
    }

}

-->
</script>
</td></tr>
</table>


<table width="100%" >
<tr><td align="center"  height="5"></td></tr>
<tr ><td align="center"  width="60%" height="1" bgcolor="#cccccc"></td></tr>
 
</table>
*删除注意：如果是编辑帖子是删除上传文件，还必须将帖子内容中图片代码清除。
<p>
<input type=button value="确定关闭本窗口" class=Button onClick="urlAction(document.upLoadFileForm.theFile.value)">
</html:form>

</logic:equal>   

</body>
</html>