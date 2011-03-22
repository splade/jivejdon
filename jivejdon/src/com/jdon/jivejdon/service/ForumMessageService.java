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
package com.jdon.jivejdon.service;

import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.output.RenderingFilterManager;

/**
 * Message operations interface. if modify this interface, remmeber modify
 * com.jdon.jivejdon.model.jivejdon_permission.xml
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public interface ForumMessageService {

	ForumMessage initMessage(EventModel em);

	ForumMessage initReplyMessage(EventModel em);

	/**
	 * has Authorization ; no cache Intercept it Called by using message's
	 * modify or deletion at first accessing this method must be checked. it is
	 * configured in jdonframework.xml
	 * 
	 * <getMethod name="findMessage"/>
	 */
	EventModel findMessage(Long messageId);

	/**
	 * no Authorization ; cache Intercept it Called by using message List, not
	 * for modify or deletion now MessageListAction or
	 * MessageRecursiveListAction.java call this method
	 */
	ForumMessage getMessage(Long messageId);

	/**
	 * no Authorization ; no cache Intercept equals getMessage, has full
	 * propperties
	 * 
	 * @param messageId
	 * @return
	 */
	ForumMessage findMessageWithPropterty(Long messageId);

	/**
	 * create a topic message, it is a root message
	 * 
	 * @param em
	 */
	void createTopicMessage(EventModel em) throws Exception;

	/**
	 * create a reply message.
	 * 
	 * @param em
	 */
	void createReplyMessage(EventModel em) throws Exception;

	void updateMessage(EventModel em) throws Exception;

	void deleteMessage(EventModel em) throws Exception;

	void deleteUserMessages(String username) throws Exception;

	/**
	 * for batch inquiry
	 */
	ForumThread getThread(Long id) throws Exception;

	RenderingFilterManager getFilterManager();

	/**
	 * check if forumMessage is Authenticated by current login user.
	 * 
	 * @param forumMessage
	 * @return
	 */
	boolean checkIsAuthenticated(ForumMessage forumMessage);

	// for /message/messageMaskAction.shtml
	void maskMessage(EventModel em) throws Exception;

}
