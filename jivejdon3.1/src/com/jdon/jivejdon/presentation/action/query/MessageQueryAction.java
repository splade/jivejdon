package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.ModelIF;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.presentation.form.QueryForm;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListAction;

public class MessageQueryAction extends ModelListAction {
	private final static Logger logger = Logger.getLogger(ThreadQueryAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start,
			int count) {
		logger.debug("enter MessageQueryAction ....");
		QueryForm qForm = (QueryForm) FormBeanUtil.lookupActionForm(request,"queryForm");
		if (qForm == null) {
			logger.error(" ThreadQueryForm is null, please at first call com.jdon.jivejdon.presentation.action.query.MessageQueryAction");
			return new PageIterator();
		}
//		save queryCriteria for html:link multi params	
		request.setAttribute("paramMaps", qForm.getParamMaps());
		
		QueryCriteria queryCriteria = create(qForm, request);
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", request);
		return forumMessageQueryService.getMessages(queryCriteria, start, count);
	}
	

	public ModelIF findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", request);
		logger.debug(" key calss type = " + key.getClass().getName());
		return forumMessageService.getMessage((Long) key);
	}
	
	protected QueryCriteria create(QueryForm qForm, HttpServletRequest request) {
		
//		client: queryView.jsp
		logger.debug("queryType is MultiCriteria");
		MultiCriteria queryCriteria = new MultiCriteria();
		queryCriteria.setForumId(qForm.getForumId());
		queryCriteria.setUsername(qForm.getUsername());
		queryCriteria.setUserID(qForm.getUserID());
		queryCriteria.setFromDate(qForm.getFromDate());
		queryCriteria.setToDate(qForm.getToDate());
		logger.debug("fromDate=" + queryCriteria.getFromDateString());
		logger.debug("toDate=" + queryCriteria.getToDateString());
		return queryCriteria;

	}
}
