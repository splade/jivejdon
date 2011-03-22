/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.manager.listener;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.domain.message.DomainMessage;
import com.jdon.domain.message.MessageListener;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.treepatterns.model.TreeModel;

@Component("addChildZToThreadTree")
public class AddChildZToThreadTree implements MessageListener {
	private final static Logger logger = Logger.getLogger(AddChildZToThreadTree.class);

	public void action(DomainMessage eventMessage) {
		try {
			ForumThread forumThread = (ForumThread) eventMessage.getEventSource();
			ForumMessageReply forumMessage = (ForumMessageReply) forumThread.getState().getLastPost();

			TreeModel treeModel = forumThread.getState().getTreeModel();
			treeModel.addChild(forumMessage.getParentMessage().getMessageId(), forumMessage.getMessageId());
		} catch (Exception e) {
			logger.error(e);
		}

	}

}
