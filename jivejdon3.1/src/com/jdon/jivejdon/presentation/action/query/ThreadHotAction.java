/*
 * Copyright 2003-2006 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
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
import com.jdon.util.UtilValidate;

/**
 * difference with ThreadPopularAction
 * ThreadPopularAction is simple, only for one page , no multi pages.
 * ThreadPopularAction is no messageReplyCountWindow, donot need sorted by message replies
 * ThreadHotAction is simple than ThreadQueryAction.
 * @author banq(http://www.jdon.com)
 *
 */
public class ThreadHotAction extends ModelListAction {

	private final static Logger logger = Logger.getLogger(ThreadQueryAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start,
			int count) {
				
		HoThreadCriteria  queryCriteria = new HoThreadCriteria();

		String dateRange = "1";
		if (request.getParameter("dateRange") != null)
		    dateRange = request.getParameter("dateRange");
		queryCriteria.setDateRange(dateRange);
		logger.debug("ThreadHotAction dateRange=" + dateRange + " count=" + count);
		
		String messageReplyCountWindowS = request.getParameter("messageReplyCountWindow");
		int messageReplyCountWindow = 10; //if reply num is greate than 10, it is hot thread
		if (!UtilValidate.isEmpty(messageReplyCountWindowS)){
			messageReplyCountWindow = Integer.parseInt(messageReplyCountWindowS);
		}
		queryCriteria.setMessageReplyCountWindow(messageReplyCountWindow);
		
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", request);
		return forumMessageQueryService.getHotThreads(queryCriteria, start, count);
	}
	

	public ModelIF findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", request);
		logger.debug(" key calss type = " + key.getClass().getName());
		return forumMessageService.getThread((Long) key);
	}

}
