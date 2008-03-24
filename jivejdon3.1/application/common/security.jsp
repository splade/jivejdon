<%
java.security.Principal principal= request.getUserPrincipal();
if (principal != null){
    request.getSession().setAttribute("principal", principal);
}else{
	request.getSession().removeAttribute("principal");
}
%>