package com.jdon.jivejdon.model;


import com.jdon.annotation.Model;
/**
 * temporary ForumThread  that not need saved into cache.
 * @author banq 
 *
 */
@Model(isCacheable=false, isModified=false) //not cache the no full object
public class ForumThreadTemp extends ForumThread {
	
	public ForumThreadTemp() {
		super();
	}

}
