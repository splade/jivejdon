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

import com.jdon.annotation.Component;
import com.jdon.domain.message.DomainMessage;
import com.jdon.domain.message.MessageListener;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.repository.AccountFactory;

@Component("loadAccount")
public class LoadAccount implements MessageListener {

	private final AccountFactory accountFactory;

	public LoadAccount(AccountFactory accountFactory) {
		super();
		this.accountFactory = accountFactory;
	}

	public void action(DomainMessage eventMessage) {
		ForumMessage forumMessage = (ForumMessage) eventMessage.getEventSource();
		Account account = accountFactory.getFullAccount(forumMessage.getAccount());
		eventMessage.setEventResult(account);
		forumMessage.setAccount(account);

	}

}
