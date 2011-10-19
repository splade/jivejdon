package com.jdon.jivejdon.presentation.action.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jdon.cache.LRUCache;
import com.jdon.controller.WebAppUtil;
import com.jdon.controller.cache.Cache;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.strutsutil.ModelListAction;

public class ThreadApprovedNewListAction extends ModelListAction {
	private final static Logger logger = Logger.getLogger(ThreadApprovedNewListAction.class);

	private Cache approvedThreadList = new LRUCache("approvedCache.xml");
	private final ApprovedListSpec approvedListSpec;

	public ThreadApprovedNewListAction() {
		approvedListSpec = new ApprovedListSpec();
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		approvedListSpec.setResultSort(resultSort);
	}

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		if (start >= 300 || start % count != 0)
			return new PageIterator();
		Collection<Long> list = getApprovedThreads(start, approvedListSpec, request);
		PageIterator pageIterator = new PageIterator((start + count) * 2, list.toArray(new Long[0]));
		return pageIterator;
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		try {
			return forumMessageService.getThread((Long) key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Collection<Long> getApprovedThreads(int start, ApprovedListSpec approvedListSpec, HttpServletRequest request) {
		Collection<Long> resultSorteds = null;
		if (approvedThreadList.contain(start)) {
			resultSorteds = (Collection<Long>) approvedThreadList.get(start);
			if (!resultSorteds.isEmpty())
				return resultSorteds;
		}

		logger.debug("not found it in cache, create it");
		int count = approvedListSpec.getNeedCount();
		int i = approvedListSpec.getCurrentStartPage();
		while (i <= start) {
			if (!approvedThreadList.contain(i)) {
				resultSorteds = loadApprovedThreads(approvedListSpec, request);
				if (resultSorteds.size() > 0) {
					approvedThreadList.put(i, resultSorteds);
					logger.debug("resultSorteds() == " + resultSorteds.size());
				} else {
					logger.debug("resultSorteds.size() == 0");
					break;
				}
			}
			i = i + count;
		}
		approvedListSpec.setCurrentStartPage(start);
		return resultSorteds;
	}

	public List<Long> loadApprovedThreads(ApprovedListSpec approvedListSpec, HttpServletRequest request) {
		List<Long> resultSorteds = new ArrayList(approvedListSpec.getNeedCount());
		try {
			int i = 0;
			int start = approvedListSpec.getCurrentStartBlock();
			int count = 100;
			ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);
			AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
			while (i < approvedListSpec.getNeedCount()) {
				PageIterator pi = forumMessageQueryService.getThreads(start, count, approvedListSpec);
				if (!pi.hasNext())
					break;

				while (pi.hasNext()) {
					Long threadId = (Long) pi.next();
					if (approvedListSpec.getCurrentIndicator() > threadId || approvedListSpec.getCurrentIndicator() == 0) {
						ForumThread thread = forumMessageQueryService.getThread(threadId);
						Long userId = thread.getRootMessage().getAccount().getUserIdLong();
						Account account = accountService.getAccount(userId);
						if (approvedListSpec.isApproved(thread, account) && i < approvedListSpec.getNeedCount()) {
							resultSorteds.add(thread.getThreadId());
							i++;
						}
						if (i >= approvedListSpec.getNeedCount()) {
							approvedListSpec.setCurrentIndicator(threadId);
							approvedListSpec.setCurrentStartBlock(start);
							break;
						}
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
