<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-tiles" prefix="tiles" %>
<script language="javascript">
var contextPath = '<%=request.getContextPath()%>';
</script>
<html>
<head>

<title><bean:write name="title" /></title>  

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon.css"/>" type="text/css">
<link rel="shortcut icon" href="http://www.jdon.com/favicon.ico">
<script language="javascript" src="<html:rewrite page="/common/js/imagetool.js"/>"></script>
<meta http-equiv="refresh" content="900">
</head>
<body bgcolor="#54728b" text="#000000" leftMargin=0 topMargin=2 marginwidth="0">
 <table width="778" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr> 
            <td width="129"  rowspan="3" align="center" valign="bottom"   bgcolor="#FFFFFF"> 
            
                <table width="100%"  border="0" cellpadding="0" cellspacing="0"  >
                  <tr>
                    <td align="center"  >
                    <img src="http://www.jdon.com/images/jdon.gif" width="120" height="60"></td>
                  </tr>
				  <tr>
				    <td height="9"></td>
				  </tr>
                </table>
                
            </td>
            <td  valign="bottom" >
			<table  width="100%" cellpadding="0" cellspacing="0" >
              <tr bgcolor="#CAC9BB" >
                <td width="43%"  align="left" valign="bottom" bgcolor="#CABFA8"><img src="http://www.jdon.com/images/00.gif" alt="jdon" width="107" height="55"></td>
                <td width="57%" height="55"  align="right" valign="bottom" bgcolor="#CABFA8" class="smallgray"><em>解惑授道，企业信息化解决之道 </em></td>
              </tr>
            </table></td>
          </tr>
          <tr> 
		    <td>
			<table cellpadding="0" cellspacing="1" bgcolor="#CCCCCC" >
                <tr>
			            <td height="26" bgcolor="#707070" width="222">&nbsp;</td>
            <td height="26" width="70" bgcolor="#707070" class="unnamed2"> <div align="center"><a href="http://www.jdon.com/index.html" target="_blank" class="a03">首&nbsp;&nbsp;&nbsp;&nbsp;页</a></div></td>
            <td height="26" width="70" bgcolor="#707070" class="unnamed2"> <div align="center"><a href="http://www.jdon.com/design.htm" target="_blank" class="a03">设计研究</a></div></td>
            <td height="26" width="70" bgcolor="#707070" class="unnamed2"> <div align="center"><a href="http://www.jdon.com/product.htm" target="_blank" class="a03">构件产品</a></div></td>			
            <td height="26" width="70" bgcolor="#707070" class="unnamed2"> <div align="center"><a href="http://www.jdon.com/trainning.htm" target="_blank" class="a03">培训咨询</a></div></td>
            <td height="26" width="70" bgcolor="#707070"> <div align="center" class="unnamed2"><a href="http://www.jdon.com/jdonframework/" target="_blank" class="a03">开源框架</a></div></td>
            <td height="26" width="70" bgcolor="#707070"> <div align="center" class="unnamed2"><a href="http://www.jdon.com/jive/index.jsp"  target="_blank" class="a03">论坛</a></div></td>

			</tr></table>
			</td>
          </tr>
</table>

<table width="778" border="0" cellspacing="0" cellpadding="0" align="center">

  <tr> 
    <td height="11" colspan="7" bgcolor="#FFFFFF" class="bianxian"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0" height="1" bordercolor="#000000">
        <tr> 
          <td> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" height="2" bgcolor="#000000">
              <tr> 
                <td></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    
    <td bgcolor="#FFFFFF"  valign="top">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
        <tr> 
          <td width="122" valign="top"  bgcolor="#FFFFD7">
          	

   <table width="100%" border="0" cellspacing="0" cellpadding="3" height="1000" align="center">
            <tr>
              <td align="left" valign="top"></td>
            </tr>
            <tr>
              <td align="right" valign="top">
<html:form action="/query/searchAction.shtml"  method="get" >
<input type="text"  name="query"  size="13" />
<html:submit value=" 搜索 "/>
<br>热点Tag:<tiles:insert definition=".hotkeys"></tiles:insert>

</html:form>
              
 <p><a href="http://www.jdon.com/jdonframework/app.htm" target="_blank"><font color="#FF3333"><strong>Jdon框架演示</strong></font></a></p>
 <p><a href="http://www.jdon.com/jdonframework/jivejdon3/index.html"  target="_blank"><font color="#FF3333"><strong>JiveJdon3.0<br>
   源码下载</strong></font></a>
     </p>
 <p><a href="http://www.jdon.com/designpatterns/index.htm" target="_blank"><font color="#FF3333"><strong>GoF设计模式</strong></font></a></p>
 <p><a href="http://www.jdon.com/trainning/jiaocheng.htm" target="_blank"><font color="#FF3333"><strong>在线教程</strong></font></a></p>
 <p><a href="http://www.jdon.com/communication.htm" target="_blank"><strong><font color="#FF3333">社区精彩讨论</font></strong></a></p>
            


<p>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
</p>
   
				  

              </td>
            </tr>
            <tr><td valign="bottom">
            
<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="120x600"/>   
 </jsp:include>              

            	</td></tr>
          </table>            
          </td>
		  <td width="1"  height="100%" >
<table height="100%" cellpadding="0" cellspacing="0" background="../images/blackpoint.gif" >
              <tr><td> <img name="" src="" width="1" height="1" alt="" ></td></tr></table>
		  </td > 
          <td valign="top" > 
            <table width="100%" border="0" cellspacing="8" cellpadding="3">
              <tr>
                <td>
                	
	