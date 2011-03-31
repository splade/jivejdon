<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>



<%--  com.jdon.jivejdon.presentation.action.ThreadEtagFilter  --%>
 
<logic:notEmpty name="NEWLASMESSAGE" >
<div id="isNewLastMessage" style="display:none">
<center>
<h4>当前有了新的更新</h4>
<br>
<a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="NEWLASMESSAGE" 
        property="forumThread.threadId" />/<bean:write name="NEWLASMESSAGE" property="messageId"/>#<bean:write name="NEWLASMESSAGE" property="messageId" />'>按这里</a>
        
</center>        
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
   

var popNowNewLast = function(){
 var nowNewLast = new Window({className: "mac_os_x", width:250, height:150, title: " Have a Message "}); 
       nowNewLast.setContent("isNewLastMessage",false, false);                  
       nowNewLast.showCenter();
}

loadWLJS(popNowNewLast);                   
                


</script>
</logic:notEmpty>

