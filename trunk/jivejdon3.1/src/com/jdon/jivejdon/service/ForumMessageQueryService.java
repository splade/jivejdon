package com.jdon.jivejdon.service;

import java.util.List;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.model.query.ResultSort;

public interface ForumMessageQueryService {
	
	 
    /**
     * a Messages Collection of a ForumThread
     * for batch inquiry
     */
    PageIterator getMessages(Long threadId, int start, int count);
    
   
    
    /**
     * a Messages Collection of their parenMessage
     * for deleteMessage display
     */
    PageIterator getRecursiveMessages(Long messageId, int start, int count);
      
    
    /**
     * all forum's topic collection
     * @param forumId
     * @param start
     * @param count 
     * @return
     */
    PageIterator getThreads(Long forumId, int start, int count, ResultSort resultSort);
    
    /**
     * get the thread collection include prev/current/next threads collection
     * 
     * @param currentThreadId
     * @param start
     * @param count
     * @return ListIterator
     */
    List getThreadsPrevNext( Long currentThreadId);
    
    PageIterator getHotThreads(QueryCriteria messageQueryCriteria, int start, int count);
    
    PageIterator getMessages(QueryCriteria messageQueryCriteria, int start, int count);
    
    PageIterator searchMessages(String query, int start, int count);
    
    PageIterator searchThreads(String query, int start, int count);
    
    PageIterator popularThreads(int popularThreadsWindow, int count);
    
}
