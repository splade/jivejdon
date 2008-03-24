package com.jdon.jivejdon.presentation.filter;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.util.Debug;

/**
 * the filter for MessageListAction, 
 * 
 * @author banq
 *
 */
public class MessageListAuthFilter extends Action {
	private final static String module = MessageListAuthFilter.class.getName();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Debug.logVerbose("enter MessageListAuthFilter " + form.getClass().getName());
		MessageListForm messageListForm = (MessageListForm)form;
		boolean[] authenticateds = new boolean[messageListForm.getList().size()];
		messageListForm.setAuthenticateds(authenticateds);
		
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		com.jdon.jivejdon.model.Account account = accountService.getloginAccount();
		if (account == null){//not login
			return mapping.findForward("success");
		}
	    ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
	    Iterator iter = messageListForm.getList().iterator();
	    int i = 0;
	    while(iter.hasNext()){
	    	ForumMessage forumMessage = (ForumMessage)iter.next();
	    	boolean result = forumMessageService.isAuthenticated(forumMessage);
	    	authenticateds[i] = result;
	    	i++;
	    }
		return mapping.findForward("success");
	}

}
