package com.jdon.jivejdon.manager.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRendering;
import com.jdon.jivejdon.model.message.output.RenderingFilterManager;

public class OutFilterManager {
	private final static Logger logger = Logger.getLogger(OutFilterManager.class);

	private Collection outFilters;
	
	private RenderingFilterManager renderingFilterManager;

	public OutFilterManager(RenderingFilterManager renderingFilterManager) {
		initFilters(renderingFilterManager.getFilters());
		this.renderingFilterManager = renderingFilterManager;
		this.renderingFilterManager.setOutFilterManager(this);
	}

	public void initFilters(MessageRendering[] filters) {
		outFilters = new ArrayList();
		for (int i = 0; i < filters.length; i++) {
			outFilters.add(filters[i]);
		}
	}

	/**
	   // Loop through replacing filters and apply them
        //these filters will not add new content to message, just replace something.
        //they are different from appending filters.
	 */
	public void applyFilters(ForumMessage forumMessage) {
		logger.debug("enter outFilter: ");
		try {
			MessageRendering messageRendering = null;
			Iterator iter = outFilters.iterator();
			while(iter.hasNext()){				
				messageRendering = ((MessageRendering)iter.next()).clone(forumMessage);
				logger.debug(messageRendering.getClass().getName());
			    forumMessage.setFilteredSubject(messageRendering.applyFilteredSubject());
                forumMessage.setFilteredBody(messageRendering.applyFilteredBody());
                messageRendering.applyFilteredPropertys();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public RenderingFilterManager getRenderingFilterManager() {
		return renderingFilterManager;
	}
	
	

}
