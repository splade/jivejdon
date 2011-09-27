<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty> - Thinking In Jdon
</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="shortcut icon" href="<html:rewrite page="/images/favicon.ico"/>" />
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
<link href="<html:rewrite page="/forum/css/messageList.css"/>" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<html:rewrite page="/forum/js/messageList.js"/>"></script>
<script>
 if(top !== self) top.location = self.location;
</script>


<%@ include file="../common/security.jsp" %>
<%@ include file="../common/loginAccount.jsp" %>

</head>
<%@ include file="../common/body_header.jsp" %>
<%@ include file="../account/loginAJAX.jsp" %>
 <%@ include file="../common/header_errors.jsp" %>
 