
<%@page import="com.jdon.jivejdon.service.AccountService,
              com.jdon.jivejdon.model.Account,
              com.jdon.controller.WebAppUtil" %>
           
<%
//need at first include security.jsp
//this jsp can save logined account all datas into request, if you inlude this jsp, you can get them. 
if (request.getAttribute("principal") != null){
	AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
	Account account = accountService.getloginAccount();
	if (account != null) {
	   request.setAttribute("loginAccount", account);
	}
}
%>

