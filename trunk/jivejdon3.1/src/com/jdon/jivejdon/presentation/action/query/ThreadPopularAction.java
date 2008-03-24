package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.ModelIF;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.query.HoThreadCriteria;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.strutsutil.ModelListAction;

/**
 * difference with ThreadHotAction
 * ThreadPopularAction is simple, only for one page , no multi pages.
 * ThreadPopularAction is no messageReplyCountWindow, donot need sorted by message replies
 * call from /query/popularlist.shtml only in fourmList.jsp
 * 
 * ThreadPopularAction is like ThreadHotAction, but another way in SQL query.
 * @author banq
 *
 */
public class ThreadPopularAction extends ModelListAction {

	private final static Logger logger = Logger.getLogger(ThreadQueryAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start,
			int count) {
				
		HoThreadCriteria  queryCriteria = new HoThreadCriteria();

		String dateRange = "1";
		if (request.getParameter("dateRange") != null)
		    dateRange = request.getParameter("dateRange");
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", request);
		logger.debug("ThreadPopularAction dateRange=" + dateRange + " count=" + count);
		return forumMessageQueryService.popularThreads(Integer.parseInt(dateRange), count);
	}
	

	public ModelIF findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", request);
		return forumMessageService.getThread((Long) key);
	}
}
