<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>

    <title>Jdon</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon.css"/>" type="text/css">
<style type="text/css">
<!--
.small {font-size: 12px}
-->
</style>
</head>

<%--  
two caller:
no parameter: /query/hotlist.shtml

parameters:
<IFRAME  MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 WIDTH="400" HEIGHT="200"  scrolling="no" 
SRC="<%=request.getContextPath()%>/query/hotlist.shtml?dateRange=180&tablewidth=400"></iframe>


--%>
<body leftmargin="0" rightmargin="0" topmargin="0">

<bean:parameter id="tablewidth" name="tablewidth" value="160"/>


<table cellpadding="0" cellspacing="0" border="0" width='<bean:write name="tablewidth"/>'>
  <tr>
    <td valign="top">  
   
       <table bgcolor="#cccccc"
     cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
          <td> <table bgcolor="#eeeeee"
         cellpadding="2" cellspacing="0" border="0" width="100%">
              <tr>
              <td align="center" bgcolor="#cccccc" width="80%"><font color="#000000" ><span class="small">热点讨论</span></font></td>
                <td align="right" bgcolor="#cccccc">
                <html:link page="/query/threadViewQuery.shtml?queryType=HOT1&dateRange=180" target="_blank">
                <span class="smallgray">more</span></html:link>
                </td>
                         </tr>
            </table></td>
        </tr>
        <tr>
          <td>


<bean:parameter id="length" name="length" value="8"/>
<bean:size id="hotcount" name="threadListForm" property="list"/>

<%
String coutlength = (String)pageContext.getAttribute("length");
String randomoffset = "0";

String noRandom = request.getParameter("noRandom");
if (noRandom == null){
	int hotcounti = ((Integer)pageContext.getAttribute("hotcount")).intValue(); 
	int coutlengthi = Integer.parseInt(coutlength);
	if (hotcounti > coutlengthi){
		hotcounti = hotcounti - coutlengthi;
	}
	int randomoffsetI = (int) (Math.random() * hotcounti);
	randomoffset = Integer.toString(randomoffsetI);	
}


%>

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%= coutlength%>' offset='<%= randomoffset %>'>

<table bgcolor="#ffffff"
         cellpadding="1" cellspacing="0" border="0" width="100%" style='TABLE-LAYOUT: fixed'>
            <tr>
                <td width="100%" style='word-WRAP: break-word'><font size="-1" face="arial"><b>&#149;</b></font> 
                  <span class="small">
                  
                  <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>.html" 
             title="<bean:write name="forumThread" property="name" />" target="_blank">
             <bean:write name="forumThread" property="name" /></a>
             <logic:notEmpty name="forumThread" property="forumThreadState">
                  <bean:write name="forumThread" property="forumThreadState.messageCount" /> 回复 
             </logic:notEmpty>
                  </span>  </td>
              </tr>
              
            </table>
            
</logic:iterate>
              
              
          </td>
              </tr>
              <tr>
                <td></td>
              </tr>
            </table></td>
        </tr>
      </table>



</body>
</html>
