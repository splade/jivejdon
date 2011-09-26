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
package com.jdon.jivejdon.model.dci.action;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.realtime.LobbyPublisherRoleIF;
import com.jdon.jivejdon.model.realtime.Notification;
import com.jdon.jivejdon.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.model.subscription.subscribed.AccountSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.repository.ForumFactory;

@Consumer("aftercreateAction")
public class AfterCreateAction implements DomainEventHandler {

	private LobbyPublisherRoleIF lobbyPublisherRole;
	private SubPublisherRoleIF subPublisherRole;
	private ForumFactory forumFactory;

	public AfterCreateAction(LobbyPublisherRoleIF lobbyPublisherRole, SubPublisherRoleIF subPublisherRole, ForumFactory forumFactory) {
		super();
		this.lobbyPublisherRole = lobbyPublisherRole;
		this.subPublisherRole = subPublisherRole;
		this.forumFactory = forumFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		// setup long time waiting for last step ,ensure new message has
		// inserted into db successfully.
		event.setWaitTimeforeturnResult(6);
		DomainMessage lastStepMessage = (DomainMessage) event.getDomainMessage().getEventSource();
		Object lastStepOk = lastStepMessage.getEventResult();
		if (lastStepOk != null) {
			ForumMessage message = (ForumMessage) lastStepOk;
			threadAction(message);
		}

	}

	public void threadAction(ForumMessage forumMessage) throws Exception {
		try {
			boolean isReplyNotifyForAuthor = forumMessage.isReplyNotify();
			forumMessage = forumFactory.getMessage(forumMessage.getMessageId());
			forumMessage.getForum().addNewThread(forumMessage);

			Notification notification = new Notification();
			notification.setSource(forumMessage);
			lobbyPublisherRole.notifyLobby(notification);

			Subscribed accountSubscribed = new AccountSubscribed(forumMessage.getAccount());
			accountSubscribed.addSubscribed(forumMessage);
			// notify the author's fans
			subPublisherRole.subscriptionNotify(accountSubscribed);

			// notify the tag's fans
			forumMessage.getForumThread().tagsSubscriptionNotify();

			// if enable reply notify, so the author is the thread's fans
			if (isReplyNotifyForAuthor) {
				subPublisherRole.createSubscription(forumMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
