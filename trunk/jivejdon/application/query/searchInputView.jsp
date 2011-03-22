<%@ page contentType="text/html; charset=UTF-8" %>
<center>


<script type="text/JavaScript">
function changeAction(theForm){
   
      if (theForm.view[0].checked){
	     theForm.action = '<%=request.getContextPath()%>/query/searchAction.shtml'
	  }else if (theForm.view[1].checked){
    	 theForm.action = '<%=request.getContextPath()%>/query/searchThreadAction.shtml'
	  }
}
 var options = {
		      script:'<%=request.getContextPath()%>/query/tags.shtml?method=tags&',
		      varname:'q',
		      json:true,
		      shownoresults:true,
		      maxresults:16,
		      callback: function (obj) { 
		      }
     		};
		function ac(id){
	    	new AutoComplete(id,options);
		}
		
</script>


<logic:notPresent name="query">
  <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table><tr><td align="middle">
  <html:form action="/query/searchThreadAction.shtml"  method="post" styleClass="search" onsubmit="changeAction(this);">
        <input type="text"  name="query"  value="<bean:write name="query"/>" id="queryId" onfocus="javascript:ac(this.id)" size="40" />
         <html:submit value="论坛搜索"/>         
         <input type="radio" name="view" checked="checked" />查询每个帖子
         <input type="radio" name="view" />查询主题贴(不包括回贴)
         
         <a href="searchhelp.html" target="_blank">help</a>
         <br><center>热点关键词:<div id="hotkeys"></div></center>         
    </html:form>
    <script type="text/javascript">
    function hotkeys(){
        new Ajax.Updater('hotkeys', '<%=request.getContextPath()%>/query/hotKeys.shtml?method=hotkeys', { method: 'get' });
      }
   
     hotkeys();
    </script>
</td></tr> </table>    
 

</center>