/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.presentation.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.util.StringUtil;
import com.jdon.util.UtilValidate;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class RSSGenServlet extends HttpServlet {
	
	private String channel_title = "";
	private String channel_link = "";
	private String channel_des = "";
	private String item_link = "";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String title = config.getInitParameter("title");
		if (!UtilValidate.isEmpty(title)) {
			channel_title = title;
		}
		String link = config.getInitParameter("link");
		if (!UtilValidate.isEmpty(link)) {
			channel_link = link;
		}

		String description = config.getInitParameter("description");
		if (!UtilValidate.isEmpty(description)) {
			channel_des = description;
		}
		
		String item_linkS = config.getInitParameter("item.link");
		if (!UtilValidate.isEmpty(item_linkS)) {
			item_link = item_linkS;
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SyndFeed feed = new SyndFeedImpl();
        //rss_0.90, rss_0.91, rss_0.92, rss_0.93, rss_0.94, rss_1.0 rss_2.0 or atom_0.3
        String rssType = request.getParameter("rssType");
        if (UtilValidate.isEmpty(rssType)){
        	rssType = "rss_2.0";
        }
        feed.setFeedType(rssType);
        
        String startS = request.getParameter("start");
        if (UtilValidate.isEmpty(startS)){
        	startS = "0";
        }
        int start = Integer.parseInt(startS);
        
        String countS = request.getParameter("count");
        if (UtilValidate.isEmpty(countS)){
        	countS = "30";
        }
        int count = Integer.parseInt(countS);
        
        feed.setTitle(channel_title);
        feed.setLink(channel_link);
        feed.setDescription(channel_des);
        feed.setEncoding("UTF-8");

        List entries = new ArrayList();    
        MultiCriteria queryCriteria = new MultiCriteria();
		queryCriteria.setUserID(request.getParameter("userId"));
		queryCriteria.setFromDate("2000-01-01");//from begin
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil
				.getService("forumMessageQueryService", request);
		PageIterator pi = forumMessageQueryService.getMessages(queryCriteria, start, count);
		
		while(pi.hasNext()){
			Long messageId = (Long)pi.next();
			ForumMessage message = getForumMessage(request, messageId);
			addMessage(entries, message, request);
		}
    
        feed.setEntries(entries);
        try {
        	response.setCharacterEncoding("UTF-8");
			Writer writer = response.getWriter();
			SyndFeedOutput output = new SyndFeedOutput();
			output.output(feed,writer);
			writer.close();
		} catch (FeedException e) {
			e.printStackTrace();
		}
	}
	
	public ForumMessage getForumMessage(HttpServletRequest request, Long key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", request);
		return forumMessageService.getMessage(key);
	}


	private void addMessage(List entries, ForumMessage message, HttpServletRequest request) {
		try {
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(message.getSubject());
			String relativeLink = StringUtil.replace(item_link, "#(threadId)#", message.getForumThread().getThreadId().toString());
			relativeLink = StringUtil.replace(relativeLink, "#(messageId)#", message.getMessageId().toString());
			entry.setLink(request.getContextPath() + relativeLink + "#" + message.getMessageId());
			
			Date publishedDate = message.getDateTime_formatter().parse(message.getCreationDate());
			entry.setPublishedDate(publishedDate);
			entry.setAuthor(message.getAccount().getUsername());
			Date updateDate = message.getDateTime_formatter().parse(message.getCreationDate());
			entry.setUpdatedDate(updateDate);

			SyndContent description = new SyndContentImpl();
			description.setType("text/html");
			description.setValue(message.getFilteredBody());
			entry.setDescription(description);
			entries.add(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		super.destroy();
	}
}