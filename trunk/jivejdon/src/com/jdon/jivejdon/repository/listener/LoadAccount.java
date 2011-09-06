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
package com.jdon.jivejdon.repository.listener;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.repository.AccountFactory;

@Consumer("loadAccount")
public class LoadAccount implements DomainEventHandler {

	private final AccountFactory accountFactory;

	public LoadAccount(AccountFactory accountFactory) {
		super();
		this.accountFactory = accountFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		ForumMessage forumMessage = (ForumMessage) event.getDomainMessage().getEventSource();
		Account account = accountFactory.getFullAccount(forumMessage.getAccount());
		event.getDomainMessage().setEventResult(account);
		forumMessage.setAccount(account);

	}

}
