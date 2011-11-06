<%@ page contentType="text/html; charset=UTF-8" %>


<table align='center' border="0" cellpadding="0" cellspacing="2" width="971" >
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
        </td><td>
        <a href="http://weibo.com/ijdon" target="_blank"><html:img page="/images/sina.png" width="30" height="27" border="0"/></a>        
        </td><td>
<!-- Feedsky FEED发布代码开始 -->
<!-- FEED自动发现标记开始 -->
<link title="RSS 2.0" type="application/rss+xml" href="http://feed.feedsky.com/jdon" rel="alternate" />
<!-- FEED自动发现标记结束 -->
<a href="http://feed.feedsky.com/jdon" target="_blank">
<html:img page="/images/cache/icon_sub_c1s16_d.gif" width="50" height="16" alt="RSS" border="0"  vspace="2"  style="margin-bottom:3px" /></a>
<!-- Feedsky FEED发布代码结束 -->
            </td>              
        <td>
        <a href="javascript:void(0)" onClick="loadWLJSWithP('<%=request.getContextPath()%>/forum/feed/feedback.jsp',helpW)">联系管理员 </a> 
        </td>
          <td>
        探索 分享 交流 解惑 授道 
         </td>
         </tr></table>                  
        </td> </tr>      
      <tr> <td align="center"  >
		<span class='small2'>OpenSource  <a href="http://www.jdon.com/jdonframework/jivejdon3/index.html" target="_blank"><strong>JIVEJDON</strong> </a> Powered
		by <a href="http://www.jdon.com/jdonframework/" target="_blank"><b>JdonFramework</b></a>  Code &copy; 2002-15 <a href="http://www.jdon.com/" target="_blank"><b>jdon.com</b></a></span>
		</td>  </tr>
    </table>
    
    </td>
  </tr>
</table>

</td></tr></table>

