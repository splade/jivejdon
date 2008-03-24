/*
 * Copyright 2003-2005 the original author or authors.
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

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.ModelIF;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.ForumService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class ThreadListAction extends ModelListAction {
    private final static Logger logger = Logger.getLogger(ThreadListAction.class);
    
    /* (non-Javadoc)
     * @see com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http.HttpServletRequest, int, int)
     */
    public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
    	ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);
        String forumId = request.getParameter("forum");
        if (forumId == null)
        	forumId = request.getParameter("forumId");
        if ((forumId == null) || !UtilValidate.isInteger(forumId)){
            logger.error(" getPageIterator error : forumId is null");
            return new PageIterator(); 
        }
        ResultSort resultSort = new ResultSort();
        if (request.getParameterMap().containsKey("ASC")){//DESC ASC
        	resultSort.setOrder_ASCENDING();
        	request.setAttribute("ASC", "ASC");//for display
        }else{
        	resultSort.setOrder_DESCENDING();
        }
        return forumMessageQueryService.getThreads(new Long(forumId),start, count, resultSort);
    }

    /* (non-Javadoc)
     * @see com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public ModelIF findModelIFByKey(HttpServletRequest request, Object key) {
        ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
        logger.debug(" key calss type = " + key.getClass().getName());
        ForumThread thread = forumMessageService.getThread((Long)key);
        return thread;
    }

    public void customizeListForm(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest request,
            ModelListForm modelListForm) throws Exception {
        ForumService forumService = (ForumService) WebAppUtil.getService("forumService", request);
        String forumId = request.getParameter("forum");
        if (forumId == null)
        	forumId = request.getParameter("forumId");
        if ((forumId == null) || !UtilValidate.isInteger(forumId)){
            logger.error(" customizeListForm error : forumId is null");
            return;
        }
        Forum forum = forumService.getForum(new Long(forumId));
        modelListForm.setOneModel(forum);
    }
}
