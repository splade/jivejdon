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
package com.jdon.jivejdon.model.subscription.subscribed;

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumThread;

public class ForumSubscribed implements Subscribed {

	public final static int TYPE = 0;

	private Forum forum;

	public ForumSubscribed(Forum forum) {
		super();
		this.forum = forum;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	@Override
	public Long getSubscribeId() {
		return forum.getForumId();
	}

	@Override
	public String getName() {
		return forum.getName();
	}

	@Override
	public int getSubscribeType() {
		return this.TYPE;
	}

	@Override
	public Object[] getSubscribed() {
		return new Object[] { forum };
	}

	@Override
	public void addSubscribed(Object o) {
		if (o != null && o instanceof ForumThread)
			this.forum = (Forum) o;

	}

	@Override
	public void updateSubscriptionCount(int count) {
		forum.getForumState().updateSubscriptionCount(count);

	}

}
