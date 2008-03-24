package com.jdon.jivejdon.presentation.action.dispatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.HotKeys;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.OthersService;

public class MethodDispatchAction extends DispatchAction {
	private final static Logger logger = Logger.getLogger(MethodDispatchAction.class);

	 public ActionForward deleteUserMessages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 logger.debug("enter userMessageListDelete");
		 String username = request.getParameter("username");
		 ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		 forumMessageService.deleteUserMessages(username);
		 return mapping.findForward("deleteUserMessages");
	 }
	 
	 public ActionForward hotkeys(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 logger.debug("enter hotkeys");
		 OthersService othersService = (OthersService) WebAppUtil.getService("othersService", request);
		 HotKeys hotKeys = othersService.getHotKeys();	
		 request.setAttribute("HOTKEYS", hotKeys.getProps());
		 return mapping.findForward("hotkeys");
	 }
	
}
