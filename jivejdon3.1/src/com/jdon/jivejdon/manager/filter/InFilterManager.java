package com.jdon.jivejdon.manager.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRendering;

public class InFilterManager {
	private final static Logger logger = Logger.getLogger(InFilterManager.class);

	private Collection inFilters;

	public InFilterManager(String[] inFilterClassNames) {
		initFilters(inFilterClassNames);
	}

	public void initFilters(String[] filters) {
		inFilters = new ArrayList();
		try {
			for (int i = 0; i < filters.length; i++) {
				String className = filters[i];
				inFilters.add((MessageRendering) Class.forName(className).newInstance());
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * before creting a message, we must append the in filter to it such upload images, we will add image display html code to the message. the message.
	 * 
	 * @param forumMessage
	 * @throws Exception
	 */
	public void applyFilters(ForumMessage forumMessage) {
		logger.debug("enter inFilter: ");
		try {
			MessageRendering messageRendering = null;
			Iterator iter = inFilters.iterator();
			while (iter.hasNext()) {
				messageRendering = ((MessageRendering) iter.next())
						.clone(forumMessage);
				logger.debug(messageRendering.getClass().getName());
				forumMessage
						.setSubject(messageRendering.applyFilteredSubject());
				forumMessage.setBody(messageRendering.applyFilteredBody());
				messageRendering.applyFilteredPropertys();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
