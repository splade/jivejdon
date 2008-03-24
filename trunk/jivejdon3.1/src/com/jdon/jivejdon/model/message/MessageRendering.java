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
package com.jdon.jivejdon.model.message;

import com.jdon.jivejdon.model.ForumMessage;


/**
 * The decorator of the ForumMessage.
 * this decorator is used by message filter display.
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public abstract  class MessageRendering {
    

    /**
     * The underlying message the filter is applied to.
     */
    protected ForumMessage message = null;

    /**
     * Clones a new filter that will have the same properties and that
     * will wrap around the specified message.
     *
     * @param message the ForumMessage to wrap the new filter around.
     */
    public abstract MessageRendering clone(ForumMessage message);

    
    /**
     * @return Returns the body.
     */
    public abstract String applyFilteredBody();
    
        
    /**
     * @return Returns the body.
     */
    public  String applyFilteredSubject(){
    	return message.getSubject();
    }
    
    
    public  void applyFilteredPropertys(){
    }
   
    /**
     * @return Returns the message.
     */
    public ForumMessage getMessage() {
        return message;
    }


	public void setMessage(ForumMessage message) {
		this.message = message;
	}
    
    
    
   
    
}
