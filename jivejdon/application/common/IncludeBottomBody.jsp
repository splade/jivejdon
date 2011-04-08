<%@ page contentType="text/html; charset=UTF-8" %>

<table align='center' border="0" cellpadding="0" cellspacing="2" width="971" style="BORDER-LEFT: #333333 1PX DASHED; BORDER-RIGHT: #333333 1PX DASHED; BORDER-TOP: #333333 1PX DASHED; BORDER-BOTTOM: #333333 1PX DASHED">
<tr><td><p align="center">

  <table  border="0" align="center" cellpadding="0" cellspacing="1">
  <tr>
    <td>
    <table align='center'>
      <tr><td align="center">
       
       <table><tr>
        <td><a href="javascript:void(0)" onClick="loadWLJSWithP('<%=request.getContextPath()%>/help.jsp',helpW)">使用帮助</a>
       <script language="javascript">
        var helpW = function(url){         
           var helpW = new Window({className: "mac_os_x", width:550, height:400, title: "  注意  "});
           helpW.setURL(url);
           helpW.showCenter();          
        }
        
        </script>
        </td>
        <td><a href="http://www.jdon.com/jivejdon/thread/37006" target="_blank" title="手机" ><html:img page="/images/phone.gif" width="18" height="18" alt="手机" border="0"/></a>
        </td>
        
       <td>
        <html:link page="/rss" target="_blank">
              <html:img page="/images/rss_button.gif" width="36" height="14" border="0"/>
         </html:link>        
        </td>
   <td>
<!-- Feedsky FEED发布代码开始 -->
<!-- FEED自动发现标记开始 -->
<link title="RSS 2.0" type="application/rss+xml" href="http://feed.feedsky.com/jdon" rel="alternate" />
<!-- FEED自动发现标记结束 -->
<a href="http://feed.feedsky.com/jdon" target="_blank">
<html:img page="/images/cache/icon_sub_c1s16_d.gif" width="50" height="16" alt="RSS" border="0"  vspace="2"  style="margin-bottom:3px" /></a>
<!-- Feedsky FEED发布代码结束 -->
            </td>        
        <td>
        <a href="http://www.google.com/ig/add?feedurl=http://www.jdon.com/jivejdon/rss"><html:img page="/images/add-to-google.gif" width="104" height="17" alt="add to google" border="0"/></a>
         </td>                    
          <td>
        <a href="http://add.my.yahoo.com/rss?url=http://www.jdon.com/jivejdon/rss"><html:img page="/images/add-to-myyahoo.gif" width="91" height="17" alt="add to yahoo" border="0"/></a>
         </td>
        <td>
        <a href="javascript:void(0)" onClick="loadWLJSWithP('<%=request.getContextPath()%>/forum/feed/feedback.jsp',helpW)">联系管理员 </a> 
        </td>
         </tr></table>
         
         
        </td>
      </tr>
      <tr>
        <td align="center">
         <a href="http://www.jdon.com/aboutsite.htm" target="_blank"><span class='small2'><font color="#666666">解惑之道在<strong>J道</strong> ，打造中国最具影响力的的软件架构社区 </font></span>
          </a>
          <a href="http://www.google.com/chrome/index.html?hl=zh-CN" target="_blank"><span class='small2'><font color="#666666">推荐FireFox或Chrome快速浏览本站</font></span></a>
        </td>
      
      </tr>
      <tr>
        <td align="center"  >
		<span class='small2'>OpenSource  <a href="http://www.jdon.com/jdonframework/jivejdon3/index.html" target="_blank"><strong>JIVEJDON</strong> </a> Powered
		by <a href="http://www.jdon.com/jdonframework/" target="_blank"><b>JdonFramework</b></a>  Code &copy; 2002-15 <a href="http://www.jdon.com/" target="_blank"><b>jdon.com</b></a></span>
		</td>
        </tr>
    </table>
    
    </td>
  </tr>
</table>

</td></tr></table>
