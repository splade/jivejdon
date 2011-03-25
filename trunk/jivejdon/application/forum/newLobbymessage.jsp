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
			<a href="javascript:void(0);" onclick='window.top.disablePopUPWithID(<bean:write name="Notification" property="id" />,<bean:write name="Notification" property="scopeSeconds" />)'>关闭提示</a>
		  </div>

<script type="text/javascript" src="<html:rewrite page="/common/js/LAB.js"/>"></script>		  
<script> 	 


function loadWLJS(myfunc){
  if (typeof(TooltipManager) == 'undefined') {     
     $LAB
     .script('<%=request.getContextPath()%>/common/js/window_def.js').wait()   
     .wait(function(){
          myfunc();          
     })    
  }else
     myfunc();
}
   
var loadNMJS = function (){
  if (typeof(popUpNewMessageWithID) == "undefined"){
     $LAB
     .script('<%=request.getContextPath()%>/forum/js/newMessage.js').wait()
     .wait(function(){
       popUpNewMessageWithID(<bean:write name="Notification" property="id" />);
     })    
  }else
       popUpNewMessageWithID(<bean:write name="Notification" property="id" />);
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
