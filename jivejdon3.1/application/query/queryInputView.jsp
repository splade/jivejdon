<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/common/js/calendar-win2k-cold-1.css" title="win2k-cold-1" />
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/calendar-zh.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/calendar-setup.js"></script>

<logic:notPresent name="query">
  <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<br>
<center>

<form method="get" action="http://www.google.com/custom" target="_blank" class="search">
 
      <input type=text name=q size="40" maxlength="255" value="<bean:write name="query"/>" >
      <input type="hidden" name="domains" value="jdon.com">
      <input type=submit name=sa value="Google本站内搜索" class="small">     
      <input type="hidden" name="client" value="pub-2190557680964036">
      <input type="hidden" name="forid" value="1">
      <input type="hidden" name="ie" value="utf-8">
      <input type="hidden" name="oe" value="utf-8">
      <input type="hidden" name="cof" value="GALT:#008000;GL:1;DIV:#FFFFFF;VLC:663399;AH:center;BGC:FFFFFF;LBGC:FFFFFF;ALC:0000FF;LC:0000FF;T:000000;GFNT:0000FF;GIMP:0000FF;LH:60;LW:120;L:http://www.jdon.com/images/jdon.gif;S:http://www.jdon.com/;LP:1;FORID:1;">
      <input type="hidden" name="hl" value="zh-CN">
      <input type="hidden" name="sitesearch" value="jdon.com" checked="checked">
       </form>

<html:form action="/query/threadViewQuery.shtml" method="post">
<html:hidden  name="queryForm" property="queryType" value="HOT2"/>
  <table cellspacing="1" cellpadding="0" width="90%" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>            
              <td  align="middle" >              
              在
     <html:select name="queryForm" property="forumId">
       <html:option value="">所有论坛</html:option>
       <html:optionsCollection name="queryForm" property="forums" value="forumId" label="name"/>
     </html:select>查询
      <br/>
                发布时间：
              从<html:text styleId="begin_date_b" name="queryForm" property='fromDate' size="20" maxlength="20" 
              /><img width="22" height="21" src="<%=request.getContextPath()%>/images/show-calendar.gif" onclick="return showCalendar('begin_date_b', 'y-m-d');">
              到<html:text styleId="end_date_b" name="queryForm" property='toDate' size="20" maxlength="20" 
               /><img width="22" height="21" src="<%=request.getContextPath()%>/images/show-calendar.gif" onclick="return showCalendar('end_date_b', 'y-m-d');">               
           <br>
              回复数不少于<input type="text" name="messageReplyCountWindow"  size="4"/>
              <html:submit value=" 查询热门主题" property="btnsearch" />
            </td>
          </tr>

        </table>
      </td>
    </tr>
  </table>
</html:form> 
<br>
<html:form action="/query/threadViewQuery.shtml"  method="post">
<html:hidden  name="queryForm" property="queryType" value="messageQueryAction"/>
  <table cellspacing="1" cellpadding="0" width="90%" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>
              <td  align="middle" >在
            
   <html:select name="queryForm" property="forumId">
       <option value="">所有论坛</option>
       <html:optionsCollection name="queryForm" property="forums" value="forumId" label="name"/>
  </html:select>           
          中，查询用户名为<html:text name="queryForm" property="username"/>发布的所有帖子(非主题贴)
       </td>             
            </tr>
            <tr>
              <td  align="middle">
              发布时间
                         从<html:text styleId="begin_date_b2" name="queryForm" property='fromDate' size="20" maxlength="20" 
              /><img width="22" height="21" src="<%=request.getContextPath()%>/images/show-calendar.gif" onclick="return showCalendar('begin_date_b2', 'y-m-d');">
              到<html:text styleId="end_date_b2" name="queryForm" property='toDate' size="20" maxlength="20" 
               /><img width="22" height="21" src="<%=request.getContextPath()%>/images/show-calendar.gif" onclick="return showCalendar('end_date_b2', 'y-m-d');">               

            <br>
              <html:submit value=" 查询 " property="btnsearch"  style="width:60"/>
            </td>
          </tr>

        </table>
      </td>
    </tr>
  </table>
</html:form>

</center>



