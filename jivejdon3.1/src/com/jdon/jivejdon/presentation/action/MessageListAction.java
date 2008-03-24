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
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class MessageListAction extends ModelListAction {
    private final static Logger logger = Logger.getLogger(MessageListAction.class);
    
    /* (non-Javadoc)
     * @see com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http.HttpServletRequest, int, int)
     */
    public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
        logger.debug("enter getPageIterator");
        String threadId = request.getParameter("thread");
        if ((threadId == null) || (!UtilValidate.isInteger(threadId))){
            logger.error(" getPageIterator error : threadId is null");  
            return new PageIterator();
        }
        String messageId = request.getParameter("message");
        return getMessages(new Long(threadId), start, count, messageId, request);    
    }
    
    /**
     *   // Determine if we need to adjust the start index of the thread iterator.
    // If we're passed a message ID, we need to show the thread page that
    // messageID is contained on.
     * this method has not good performance. not frequency call it
     */
    private PageIterator getMessages(Long threadId, int start, int count, String messageId, HttpServletRequest request){
    	logger.debug("enter getMessages2 messageId=" + messageId);
    	ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);         
		if ((threadId == null) || (threadId.longValue() == 0))
			return new PageIterator();
		if (UtilValidate.isEmpty(messageId))
			return forumMessageQueryService.getMessages(threadId, start, count);
		
		Long messageIdL = Long.parseLong(messageId);
	    start = 0;
		boolean found = false;
    	PageIterator pi = forumMessageQueryService.getMessages(threadId, start, count);
    	int allCount = pi.getAllCount();
     	while((start < allCount) && (!found)){//loop all 
     		while(pi.hasNext()){
    			Long messageIdT = (Long)pi.next();
    			if (messageIdT.longValue() == messageIdL.longValue()){
   				   found = true;   				   
   				   break;
   			   }
    		}
     		if (found) break;
     		start = start + count ;
    		logger.debug("start = " + start + " count = " + count);
    		pi = forumMessageQueryService.getMessages(threadId, start, count);     			     		
    	}
     	
     	if (found){
     		MessageListForm messageListForm = (MessageListForm)FormBeanUtil.lookupActionForm(request, "messageListForm"); //same as struts-config-message.xml
     		if (messageListForm != null) messageListForm.setStart(start);//diaplay
     		return forumMessageQueryService.getMessages(threadId, start, count);
     	}else{
     		logger.debug("not found the messageId =" + messageId);
     		return new PageIterator();
     	}
    }

    /* (non-Javadoc)
     * @see com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public ModelIF findModelIFByKey(HttpServletRequest request, Object key) {
        logger.debug("enter findModelByKey");
        ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
        return forumMessageService.findFilteredMessage((Long)key);
    }
    
    protected boolean isEnableCache() {
        return false;
      }
    
    public void customizeListForm(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest request,
            ModelListForm modelListForm) throws Exception {
        ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
        String threadId = request.getParameter("thread");
        if ((threadId == null) || (!UtilValidate.isInteger(threadId))){
            logger.error("customizeListForm error : threadId is null");  
            return;
        }
        ForumThread forumThread = forumMessageService.getThread(new Long(threadId));        
        modelListForm.setOneModel(forumThread);
    }
    

}
