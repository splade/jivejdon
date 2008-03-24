package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.presentation.form.QueryForm;

public class MessageQueryUserAction extends MessageQueryAction {
	private final static Logger logger = Logger.getLogger(MessageQueryUserAction.class);

	protected QueryCriteria create(QueryForm qForm, HttpServletRequest request) {
		MultiCriteria queryCriteria = new MultiCriteria();
		queryCriteria.setForumId(qForm.getForumId());
		String userId = request.getParameter("userID");
		if (userId == null){
			userId = request.getParameter("user"); // for old version
		}
		if (userId == null){
			userId = request.getParameter("userId"); // for old version
		}
		qForm.setUserID(userId);
		queryCriteria.setUserID(userId);
		qForm.setFromDate("2000-01-01");
		queryCriteria.setFromDate(qForm.getFromDate());
		logger.debug("fromDate=" + queryCriteria.getFromDateString());
		logger.debug("toDate=" + queryCriteria.getToDateString());
		return queryCriteria;

	}

}
