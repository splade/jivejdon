<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>JiveJdon论坛管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>


<frameset rows="90,*" bordercolor="#0099cc" border="0" frameborder="0" framespacing="0" style="background-color:#0099cc">
	<frame src="tabs.jsp" name="header" scrolling="no" marginheight="0" marginwidth="0" noresize>
	<frameset cols="130,*" bordercolor="#0099cc" border="0" frameborder="0" style="background-color:#0099cc">
		<frame src="<html:rewrite page="/admin/sidebar.shtml?method=system"/>" name="sidebar" scrolling="auto" marginheight="0" marginwidth="0" noresize>
		<frameset rows="*" bordercolor="#0099cc" border="0" frameborder="0" style="background-color:#0099cc">
			<frame src="main.jsp" name="main" scrolling="auto" noresize>
		</frameset>
	</frameset>
</frameset>

</html>
