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
package com.jdon.jivejdon.model;

import com.jdon.jivejdon.model.subscription.SubscribedState;
import com.jdon.jivejdon.model.subscription.subscribed.ForumSubscribed;

/**
 * Forum State ValueObject this is a embeded class in Forum.
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class ForumState {
	private int threadCount = 0;

	/**
	 * the number of messages in the thread. This includes the root message. So,
	 * to find the number of replies to the root message, subtract one from the
	 * answer of this method.
	 */
	private int messageCount = 0;

	private volatile ForumMessage lastPost;

	private Forum forum;

	private SubscribedState subscribedState;

	public ForumState(Forum forum) {
		super();
		this.forum = forum;
		lastPost = new ForumMessage();
		this.subscribedState = new SubscribedState(new ForumSubscribed(forum));
	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		return messageCount;
	}

	/**
	 * @param messageCount
	 *            The messageCount to set.
	 */
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	public void addMessageCount() {
		this.messageCount = messageCount + 1;
	}

	/**
	 * @return Returns the threadCount.
	 */
	public int getThreadCount() {
		return threadCount;
	}

	public void addThreadCount() {
		this.threadCount = threadCount + 1;
	}

	/**
	 * @param threadCount
	 *            The threadCount to set.
	 */
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public ForumMessage getLastPost() {
		return lastPost;
	}

	public void setLastPost(ForumMessage lastPost) {
		this.lastPost = lastPost;
	}

	public Forum getForum() {
		return forum;
	}

	public void updateSubscriptionCount(int count) {
		subscribedState.update(count);
	}

	public int getSubscriptionCount() {
		return subscribedState.getSubscriptionCount(this.forum.lazyLoaderRole);
	}

}
