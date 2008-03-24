package com.jdon.jivejdon.presentation.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.presentation.form.MessageForm;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.util.Debug;


/**
 * the filter for Message edit Action, 
 * 
 * @author banq
 *
 */
public class MessageEditAuthFilter extends Action {
	private final static String module = MessageEditAuthFilter.class.getName();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Debug.logVerbose("enter MessageEditAuthFilter");
		MessageForm messageForm = (MessageForm)form;	
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		com.jdon.jivejdon.model.Account account = accountService.getloginAccount();
		if (account == null){
			messageForm.setAuthenticated(false);//only logined user can post
			return mapping.findForward("success");
		}
	    if ((messageForm.getAction() != null) && 
	    		(!messageForm.getAction().equals(MessageForm.CREATE_STR))){
		    ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		    ForumMessage forumMessage = forumMessageService.getMessage(messageForm.getMessageId());
		    boolean result = forumMessageService.isAuthenticated(forumMessage);
	    	messageForm.setAuthenticated(result);
	    }
	       
		return mapping.findForward("success");
	}

}
