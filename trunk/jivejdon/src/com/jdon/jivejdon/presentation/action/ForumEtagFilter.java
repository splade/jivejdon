/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.presentation.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

public abstract class ForumEtagFilter extends ModelListAction {
	public final static String NEWLASMESSAGE = "NEWLASMESSAGE";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumService forumService = (ForumService) WebAppUtil.getService("forumService", request);
		String forumId = request.getParameter("forum");
		if (forumId == null)
			forumId = request.getParameter("forumId");

		ForumMessage lastpost = null;
		if ((forumId == null) || !UtilValidate.isInteger(forumId)) {
			lastpost = getForumsLastModifiedDate(request);
			if (lastpost == null)
				return super.execute(actionMapping, actionForm, request, response);
			return super.execute(actionMapping, actionForm, request, response);

		} else {
			Forum forum = forumService.getForum(new Long(forumId));
			if (forum == null)
				return super.execute(actionMapping, actionForm, request, response);
			lastpost = forum.getForumState().getLastPost();
		}
		long expire = 10 * 60;
		long modelLastModifiedDate = lastpost.getModifiedDate2();
		String previousToken = request.getHeader("If-None-Match");

		if (!ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
			return null;
		} else { // newLastMessageNotfier.jsp
			if (previousToken != null && Long.parseLong(previousToken) < modelLastModifiedDate) {
				request.setAttribute(NEWLASMESSAGE, lastpost);
				response.setStatus(HttpServletResponse.SC_OK);
			}
		}
		// request.setAttribute(NEWLASMESSAGE, lastpost);
		// expireFilter not effects jivejdon/thread/xxxx

		return super.execute(actionMapping, actionForm, request, response);

	}

	private ForumMessage getForumsLastModifiedDate(HttpServletRequest request) {
		Collection<Long> listF = new ArrayList();
		Map<Long, ForumMessage> maps = new HashMap();
		try {
			ForumService forumService = (ForumService) WebAppUtil.getService("forumService", request);
			PageIterator pageIterator = forumService.getForums(0, 200);
			while (pageIterator.hasNext()) {
				Forum forum = forumService.getForum((Long) pageIterator.next());
				listF.add(forum.getForumState().getLastPost().getMessageId());
				maps.put(forum.getForumState().getLastPost().getMessageId(), forum.getForumState().getLastPost());
			}
			return maps.get(Collections.max(listF));
		} catch (Exception ex) {
		}
		return null;

	}

}
