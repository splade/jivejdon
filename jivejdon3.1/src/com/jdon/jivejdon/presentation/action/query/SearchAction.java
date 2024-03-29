package com.jdon.jivejdon.presentation.action.query;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.ModelIF;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

public class SearchAction extends ModelListAction {

	private final static Logger logger = Logger.getLogger(ThreadQueryAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {

		String query = request.getParameter("query");
		if ((query == null) || (UtilValidate.isEmpty(query))) {
			logger.error(" getPageIterator error : query is null");
			return new PageIterator();
     	}

		String useGBK = request.getParameter("useGBK");
		if (useGBK != null){
			try {
				query = ToolsUtil.getParameterFromQueryString(request.getQueryString(), "query");
				query = new String((query).getBytes("ISO-8859-1"),"GBK");
				query = ToolsUtil.gbToUtf8(query);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("query", query);
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);
		return forumMessageQueryService.searchMessages(query, start, count);
	}

	public ModelIF findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		logger.debug(" key calss type = " + key.getClass().getName());
		return forumMessageService.getMessage((Long) key);
	}

}
