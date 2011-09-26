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
package com.jdon.jivejdon.model.thread;

import java.util.ArrayList;
import java.util.Collection;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ThreadTag;
import com.jdon.jivejdon.model.subscription.subscribed.TagSubscribed;
import com.jdon.util.UtilValidate;

public class ThreadTagsVO {

	private ForumThread forumThread;

	// same as rooMessage 's tags, but this is Collection
	private Collection tags;

	private Collection lasttags;

	private boolean reload;

	public ThreadTagsVO(ForumThread forumThread) {
		super();
		this.forumThread = forumThread;
		this.tags = new ArrayList();
		this.lasttags = new ArrayList();
	}

	public Collection getTags() {
		if (reload)
			reloadChangedTags();
		return tags;
	}

	public String[] getTagTitles() {
		return forumThread.getRootMessage().getMessageVO().getTagTitle();
	}

	public void setTags(Collection tags) {
		this.tags = tags;
		String[] tagTitles = new String[tags.size()];
		int i = 0;
		for (Object o : tags) {
			ThreadTag tag = (ThreadTag) o;
			tagTitles[i] = tag.getTitle();
			i++;
		}
		this.forumThread.getRootMessage().getMessageVO().setTagTitle(tagTitles);
	}

	public void changeTags(String[] tagTitles) {
		if (tagTitles == null || tagTitles.length == 0)
			return;
		Collection newtags = convert(tagTitles);
		this.lasttags = this.tags;
		setTags(newtags);
		forumThread.repositoryRole.changeTags(forumThread);
		reload = true;
	}

	private void reloadChangedTags() {
		reload = false;
		// this event maybe run before last evnet:changeTags
		DomainMessage message = this.forumThread.lazyLoaderRole.loadTags(forumThread.getThreadId());
		this.tags = (Collection) message.getEventResult();
		setTags(this.tags);

		subscriptionNotify();

	}

	public void subscriptionNotify() {
		for (Object o : this.tags) {
			ThreadTag tag = (ThreadTag) o;
			if (!this.lasttags.contains(tag))
				// changeTags will notify subscription
				this.forumThread.subPublisherRole.subscriptionNotify(new TagSubscribed(tag, forumThread));
		}
	}

	private Collection convert(String[] tagTitles) {
		Collection tags = new ArrayList();
		for (int i = 0; i < tagTitles.length; i++) {
			ThreadTag tag = new ThreadTag();
			if (!UtilValidate.isEmpty(tagTitles[i])) {
				tag.setTitle(tagTitles[i]);
				tags.add(tag);
			}

		}
		return tags;
	}

}
