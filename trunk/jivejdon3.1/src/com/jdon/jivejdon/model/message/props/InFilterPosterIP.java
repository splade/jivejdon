/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.model.message.props;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRendering;
import com.jdon.jivejdon.util.ToolsUtil;

/**
 * Manage the properties of a ForumMessage
 * 
 * @author banq(http://www.jdon.com)
 * @see com.jdon.jivejdon.model.message.props.PropertyFilterManager
 */
public class InFilterPosterIP extends MessageRendering{
    private final static Logger logger = Logger.getLogger(InFilterPosterIP.class);
    private DateFormat dateTime_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
    public MessageRendering clone(ForumMessage message) {
    	InFilterPosterIP filter = new InFilterPosterIP();
        filter.message = message;
        return filter;
      }
    
    public String applyFilteredBody() {
    	Account modifier = message.getOperator();
    	if ((modifier == null) || (!message.isModified())){
    		logger.debug("poster is null or message is  not modified state" );
    		return message.getBody();
    	}    	
    	 StringBuffer buffer = new StringBuffer(message.getBody());
    	 buffer.append("\n");
    	 long now = System.currentTimeMillis();
         String displayDateTime = dateTime_formatter.format(new Date(now));
        
         buffer.append("[该贴被").append(modifier.getUsername());
         buffer.append("于").append(displayDateTime).append("修改过]");
        return buffer.toString();
    }
   
    
    public  void applyFilteredPropertys(){
    	logger.debug(" enter : applyFilteredPropertys");
    	com.jdon.jivejdon.model.Account poster = message.getOperator();
    	if (poster == null){
    		logger.error("poster is null" );
    		return;
    	}
        logger.debug("addPostIp IP=" + poster.getPostIP());
        message.addProperty(ForumMessage.PROPERTY_IP, poster.getPostIP());
    }

	public DateFormat getDateTime_formatter() {
		return dateTime_formatter;
	}

	public void setDateTime_formatter(DateFormat dateTime_formatter) {
		this.dateTime_formatter = dateTime_formatter;
	}
  
}
