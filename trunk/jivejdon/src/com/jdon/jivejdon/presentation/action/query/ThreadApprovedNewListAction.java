package com.jdon.jivejdon.presentation.action.query;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.presentation.form.ThreadListForm;
import com.jdon.jivejdon.service.ForumMessageQueryService;

public class ThreadApprovedNewListAction extends Action {
	private final static Logger logger = Logger.getLogger(ThreadQueryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter ThreadApprovedNewListAction");
		ThreadListForm threadListForm = (ThreadListForm) form;
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);

		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		ApprovedListSpec approvedListSpec = new ApprovedListSpec();
		approvedListSpec.setResultSort(resultSort);
		
		if (request.getParameter("count")!= null){
			int needCount = Integer.parseInt(request.getParameter("count"));
			approvedListSpec.setNeedCount(needCount);
		}
		
		Collection<ForumThread> list = forumMessageQueryService.getApprovedThreads(approvedListSpec);
		threadListForm.setList(list);
		return mapping.findForward("success");
	}

}
