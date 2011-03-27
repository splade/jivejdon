<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(0, request, response);
%>
<%@ include file="../common/security.jsp" %>

<logic:notEmpty name="Notification">
          <div align="center">
           <br>
			<bean:write name="Notification" property="content" filter="false" />
			<br>
			<br>
			注:进入如发现无新帖，请按浏览器"刷新"
			<br>
			<br>
			<a href="javascript:void(0);" onclick='window.top.disablePopUPWithID(<bean:write name="Notification" property="id" />,<bean:write name="Notification" property="scopeSeconds" />)'>关闭提示</a>
		  </div>
<script>

function loadWLJS(myfunc){
  if (typeof(window.top.TooltipManager) == 'undefined') {     
     window.top.$LAB
     .script('<%=request.getContextPath()%>/common/js/window_def.js').wait()   
     .wait(function(){
          myfunc();          
     })    
  }else
     myfunc();
}
   
var loadNMJS = function (){
  if (typeof(window.top.popUpNewMessageWithID) == "undefined"){
     window.top.$LAB
     .script('<%=request.getContextPath()%>/forum/js/newMessage.js').wait()
     .wait(function(){
       window.top.popUpNewMessageWithID(<bean:write name="Notification" property="id" />);
     })    
  }else
       window.top.popUpNewMessageWithID(<bean:write name="Notification" property="id" />);
}

loadWLJS(loadNMJS());                   
                
              
            </script>
    
</logic:notEmpty>

<logic:empty name="Notification">
	        <script> 	 
            if (typeof(window.top.clearPopUP) != "undefined")
                window.top.clearPopUP();
            else  if (typeof(clearPopUP) != "undefined")
                clearPopUP();
            </script>
	
</logic:empty>
