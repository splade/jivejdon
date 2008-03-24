package com.jdon.jivejdon.presentation.action.admin;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.ModelIF;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

public class UserMessageListAction extends ModelListAction {
	private final static Logger logger = Logger.getLogger(UserMessageListAction.class);
	
	public PageIterator getPageIterator(HttpServletRequest request, int start,
			int count) {
		logger.debug("enter UserMessageListAction ....");
		QueryCriteria queryCriteria = new MultiCriteria();
		String username = request.getParameter("username");
		if (UtilValidate.isEmpty(username)) {		
			return new PageIterator();
		}

		logger.debug("queryType is MultiCriteria");
		queryCriteria = new MultiCriteria();
		((MultiCriteria) queryCriteria).setUsername(username);
		queryCriteria.setFromDate("1970", "01", "01"); //all date
		logger.debug("fromDate=" + queryCriteria.getFromDateString());
		logger.debug("toDate=" + queryCriteria.getToDateString());

		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", request);
		return forumMessageQueryService.getMessages(queryCriteria, start, count);
	}

	
	public ModelIF findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", request);
		logger.debug(" key calss type = " + key.getClass().getName());
		return forumMessageService.getMessageWithPropterty((Long) key);
	}
	
	

	
}
