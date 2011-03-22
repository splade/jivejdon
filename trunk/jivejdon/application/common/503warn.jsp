
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.throttle.hitkey.CustomizedThrottle,
com.jdon.jivejdon.manager.throttle.hitkey.HitKey2,
com.jdon.jivejdon.manager.throttle.hitkey.HitKeyIF"%>

<%
try{
	CustomizedThrottle customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", request);
	HitKeyIF hitKey = new HitKey2(request.getRemoteAddr(), "503");
	if (customizedThrottle.processHit(hitKey)){
		customizedThrottle.addBanned(request.getRemoteAddr());	
	}
}catch(Exception e){
}

%>