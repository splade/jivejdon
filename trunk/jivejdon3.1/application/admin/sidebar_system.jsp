<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<head>
<title>论坛管理工具</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon.css"/>" type="text/css">
</head>

<body background="images/sidebar_back.gif" text="#000000" link="#0000ff" vlink="#0000ff" alink="#6699cc"
onload="parent.frames['header'].location.href='tabs.jsp?method=system';">

<html:img page="/images/blank.gif" width="50" height="5" border="0" alt=""/><br>
 <table cellpadding="2" cellspacing="0" border="0" width="100%">
    <tr><td rowspan="99" width="1%">&nbsp;</td>
        <td colspan="3" width="99%"><font size="-1" color="#333333"><b>系统设置</b></font></td>
    </tr>
    <%--
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><font size="-1"><a href="clickstream/clickstreams.jsp" target="main">点按状况</a></font></td>
    </tr>--%>
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><html:link page="/admin/filters/filtersAction.shtml?method=display" target="main">全局过虑</html:link></td>
    </tr>
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><html:link page="/admin/search/setup.jsp"  target="main">搜索设置</html:link></td>
    </tr>
    <tr><td width="1%">&nbsp;</td>
        <td width="1%">&#149;</td>
        <td width="97%">缓存设置</td>
    </tr>    
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td>基本统计</td>
    </tr>
    <!--
    <tr><td colspan="3">&nbsp;</td>
    </tr>
    <tr><td colspan="3"><font size="-1" color="#333333"><b>监控</b></font></td>
    </tr>
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><font size="-1"><a href="editWatches.jsp" target="main">监控设置</a></font></td>
    </tr>
    <tr><td colspan="3">&nbsp;</td>
    </tr>
    <tr><td colspan="3"><font size="-1" color="#333333"><b>奖赏</b></font></td>
    </tr>
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><font size="-1"><a href="manageRewards.jsp" target="main">点数管理</a></font></td>
    </tr>
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><font size="-1"><a href="editRewards.jsp" target="main">点数设置</a></font></td>
    </tr>
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><font size="-1"><a href="rewardReports.jsp" target="main">报告</a></font></td>
    </tr>
    -->
    </table>

</body>
</html>