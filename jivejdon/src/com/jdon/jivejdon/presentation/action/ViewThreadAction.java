package com.jdon.jivejdon.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.util.UtilValidate;

public class ViewThreadAction extends Action {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String threadId = request.getParameter("thread");
		if ((threadId == null) || (!UtilValidate.isInteger(threadId))) {
			((HttpServletResponse) response).sendError(404);
			return actionMapping.findForward("error");
		}
		try {
			ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);

			ForumThread thread = forumMessageService.getThread(new Long(threadId));
			
			if (thread == null) {
				((HttpServletResponse) response).sendError(404);
				return actionMapping.findForward("error");
			}

			thread.viewCountAction(request.getRemoteAddr());
		} catch (Exception e) {
			return actionMapping.findForward("error");
		}
		return actionMapping.findForward("success");
	}

}
