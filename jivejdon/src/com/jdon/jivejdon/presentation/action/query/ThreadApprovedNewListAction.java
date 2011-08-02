package com.jdon.jivejdon.presentation.action.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.cache.LRUCache;
import com.jdon.controller.WebAppUtil;
import com.jdon.controller.cache.Cache;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.presentation.form.ThreadListForm;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.util.ToolsUtil;

public class ThreadApprovedNewListAction extends Action {
	private final static Logger logger = Logger.getLogger(ThreadApprovedNewListAction.class);

	private Cache approvedThreadList = new LRUCache("approvedCache.xml");;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter ThreadApprovedNewListAction");
		ThreadListForm threadListForm = (ThreadListForm) form;
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		ApprovedListSpec approvedListSpec = new ApprovedListSpec();
		approvedListSpec.setResultSort(resultSort);

		if (request.getParameter("count") != null) {
			int needCount = Integer.parseInt(request.getParameter("count"));
			approvedListSpec.setNeedCount(needCount);
		}

		Collection<ForumThread> list = getApprovedThreads(approvedListSpec, request);
		threadListForm.setList(list);
		if (list.size() == 0)
			return mapping.findForward("success");

		ForumThread newThread = (ForumThread) list.toArray()[0];
		int expire = 10 * 60;
		long modelLastModifiedDate = newThread.getState().getLastPost().getModifiedDate2();
		if (!ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
			return null;
		}
		return mapping.findForward("success");
	}

	public Collection<ForumThread> getApprovedThreads(ApprovedListSpec approvedListSpec, HttpServletRequest request) {
		Collection<ForumThread> resultSorteds;
		if (approvedThreadList.contain(ThreadApprovedNewListAction.class)) {
			resultSorteds = (Collection) approvedThreadList.get(ThreadApprovedNewListAction.class);
			if (!resultSorteds.isEmpty())
				return resultSorteds;
		}

		logger.debug("not found it in cache, create it");
		resultSorteds = loadApprovedThreads(approvedListSpec, request);
		if (resultSorteds.size() > 0) {
			approvedThreadList.put(ThreadApprovedNewListAction.class, resultSorteds);
			logger.debug("resultSorteds() == " + resultSorteds.size());
		} else {
			logger.debug("resultSorteds.size() == 0");
		}
		return resultSorteds;
	}

	public List<ForumThread> loadApprovedThreads(ApprovedListSpec approvedListSpec, HttpServletRequest request) {
		List<ForumThread> resultSorteds = new ArrayList(approvedListSpec.getNeedCount());
		try {
			int i = 0;
			int start = 0;
			int count = 100;
			ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);
			AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
			while (i < approvedListSpec.getNeedCount()) {
				PageIterator pi = forumMessageQueryService.getThreads(start, count, approvedListSpec);
				if (!pi.hasNext())
					break;

				while (pi.hasNext()) {
					Long threadId = (Long) pi.next();
					ForumThread thread = forumMessageQueryService.getThread(threadId);
					Long userId = thread.getRootMessage().getAccount().getUserIdLong();
					Account account = accountService.getAccount(userId);
					if (approvedListSpec.isApproved(thread, account) && i < approvedListSpec.getNeedCount()) {
						resultSorteds.add(thread);
						i++;
					}
					if (i >= approvedListSpec.getNeedCount()) {
						break;
					}
				}
				start = start + count;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSorteds;
	}

}
