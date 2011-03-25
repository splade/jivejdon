<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="foot">
<%@include file="../../common/IncludeBottomBody.jsp"%>
</div> 
 
 
<logic:present name="principal" >
   <%@include file="../../forum/messageNotfier.jsp"%>
</logic:present> 

<script>
function openShortmessageWindow(name, url){
    if (typeof(Dialog) == 'undefined') 
       loadWLJS(nof);
    
     if (!isLogin){//login defined in .common/security.jsp        
        Dialog.alert("请先登陆", 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});
        return false;
    }
   openPopUpWindow(name, url);
}
 
</script>
</body> 
</html> 