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
package com.jdon.jivejdon.manager.subscription;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.manager.subscription.action.ShortMsgAction;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ThreadSubscribed;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.SubscriptionRepository;

@Consumer("createSubscription")
public class SubscriptionCreator implements DomainEventHandler {

	private AccountFactory accountFactory;
	protected SubscriptionRepository subscriptionRepository;

	public SubscriptionCreator(AccountFactory accountFactory, SubscriptionRepository subscriptionRepository) {
		super();
		this.accountFactory = accountFactory;
		this.subscriptionRepository = subscriptionRepository;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {

		ForumMessage newforumMessage = (ForumMessage) event.getDomainMessage().getEventSource();
		Subscription sub = new Subscription();
		sub.setAccount(accountFactory.getFullAccount(newforumMessage.getAccount()));
		sub.addAction(new ShortMsgAction());
		Subscribed subscribed = SubscribedFactory.createTransient(ThreadSubscribed.TYPE, newforumMessage.getForumThread().getThreadId());
		sub.setSubscribed(subscribed);
		subscriptionRepository.createSubscription(sub);
	}
}
