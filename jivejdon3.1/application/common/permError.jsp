<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<bean:define id="title"  value=" 权限错误" />
<%@ include file="../common/IncludeTop.jsp" %>

<bean:parameter id="error" name="error" value="" />
<center>
    <H3>Something happened...</H3>
    <B>对不起，没有权限操作，例如：有跟帖的帖子不能修改删除。
    <br>或者发生系统错误<bean:write name="error" />，<a href='http://www.jdon.com/my/feed/feedbackAction.do?subject=fromjivejdon3_<bean:write name="error" />'>请联系管理员</a></B>
      <br><br><br><p>返回<html:link page="/index.jsp" > 首页</html:link>
</center>    

<%@include file="../common/IncludeBottom.jsp"%>