<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-tiles" prefix="tiles" %>
<center>


<script type="text/JavaScript">
function changeAction(theForm){
   
      if (theForm.view[0].checked){
	     theForm.action = '<%=request.getContextPath()%>/query/searchThreadAction.shtml'
	  }else if (theForm.view[1].checked){
    	 theForm.action = '<%=request.getContextPath()%>/query/searchAction.shtml'
	  }
}
</script>

<logic:notPresent name="query">
  <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table><tr><td align="middle">
  <html:form action="/query/searchThreadAction.shtml"  method="get" styleClass="search" onsubmit="changeAction(this);">
        <input type="text"  name="query"  value="<bean:write name="query"/>"  size="40" />
         <html:submit value="论坛搜索"/>
         <input type="radio" name="view" checked="checked"  />以主题排列
         <input type="radio" name="view" />以帖子详细排列
         
         <a href="searchhelp.html" target="_blank">help</a>
         <br><center>热点关键词:<tiles:insert definition=".hotkeys"></tiles:insert></center>         
    </html:form>
    
</td></tr> </table>    
 

</center>