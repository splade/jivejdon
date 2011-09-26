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
package com.jdon.jivejdon.model.repository;

import java.util.HashMap;
import java.util.Map;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.model.Send;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.thread.ViewCounter;

/**
 * this is the Role of DCI. and is domain events publisher
 * 
 * @author banq
 * 
 */
@Introduce("message")
public class LazyLoaderRole {

	@Send("subscriptionCounter")
	public DomainMessage loadSubscriptionNumbers(Long subscribeId) {
		return new DomainMessage(subscribeId);
	}

	@Send("accountMessageCounter")
	public DomainMessage loadAccountMessageCount(Long userId) {
		return new DomainMessage(userId);
	}

	@Send("reloadMessageVO")
	public DomainMessage reloadMessageVO(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	@Send("loadMessageProperties")
	public DomainMessage loadMessageProperties(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	@Send("loadUploadEntity")
	public DomainMessage loadUploadEntity(String objectId) {
		return new DomainMessage(objectId);
	}

	@Send("loadUploadFiles")
	public DomainMessage loadUploadFiles(String parentId) {
		return new DomainMessage(parentId);
	}

	@Send("loadTags")
	public DomainMessage loadTags(Long forumThreadId) {
		return new DomainMessage(forumThreadId);
	}

	@Send("loadMessageDigCount")
	public DomainMessage loadMessageDigCount(Long messageId) {
		return new DomainMessage(messageId);
	}

	@Send("threadViewCountManager")
	public DomainMessage refreshThreadCount(ViewCounter viewCounter) {
		return new DomainMessage(viewCounter);
	}

	@Send("shortMessageService")
	public DomainMessage loadNewShortMessageCount(Account account) {
		Map commandReqs = new HashMap();
		commandReqs.put("name", "loadNewShortMessageCount");
		commandReqs.put("value", account);
		return new DomainMessage(commandReqs);
	}

}
