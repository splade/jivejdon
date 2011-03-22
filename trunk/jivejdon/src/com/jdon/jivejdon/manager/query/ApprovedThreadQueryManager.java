package com.jdon.jivejdon.manager.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.TagRepository;
import com.jdon.jivejdon.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.service.util.ContainerUtil;

@Component
public class ApprovedThreadQueryManager {
	private final static Logger logger = Logger.getLogger(ApprovedThreadQueryManager.class);

	protected MessageQueryDao messageQueryDao;

	protected TagRepository tagRepository;

	protected AccountFactory accountFactory;

	protected ContainerUtil containerUtil;

	protected ForumFactory forumAbstractFactory;

	public ApprovedThreadQueryManager(MessageQueryDao messageQueryDao, AccountFactory accountFactory, TagRepository tagRepository,
			ForumFactory forumAbstractFactory, ContainerUtil containerUtil) {
		super();
		this.messageQueryDao = messageQueryDao;
		this.accountFactory = accountFactory;
		this.containerUtil = containerUtil;
		this.tagRepository = tagRepository;
		this.forumAbstractFactory = forumAbstractFactory;
	}


	public List<ForumThread> getApprovedThreads(ApprovedListSpec approvedListSpec) {
		String cacheKey = ApprovedThreadQueryManager.class.getName();
		List<ForumThread> resultSorteds = (List) containerUtil.getCacheManager().getCache().get(cacheKey);
		if (resultSorteds == null) {
			logger.debug("not found it in cache, create it");
			resultSorteds = createSorteds(approvedListSpec);
			if (resultSorteds.size() > 0) {
				containerUtil.getCacheManager().getCache().put(cacheKey, resultSorteds);
				logger.debug("resultSorteds() == " + resultSorteds.size());
			} else {
				logger.debug("resultSorteds.size() == 0");
			}
		}
		return resultSorteds;
	}

	private List<ForumThread> createSorteds(ApprovedListSpec approvedListSpec) {
		List<ForumThread> resultSorteds = new ArrayList(approvedListSpec.getNeedCount());
		try {
			int i = 0;
			int start = 0;
			int count = 100;
			while (i < approvedListSpec.getNeedCount()) {
				PageIterator pi = messageQueryDao.getThreads(start, count, approvedListSpec);
				if(!pi.hasNext()) break;
				
				while (pi.hasNext()) {
					Long threadId = (Long) pi.next();
					ForumThread thread = forumAbstractFactory.getThread(threadId);
					if (approvedListSpec.isApproved(thread) && i < approvedListSpec.getNeedCount()) {
						resultSorteds.add(thread);
						i++;
					}
					if (i >= approvedListSpec.getNeedCount()){
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
