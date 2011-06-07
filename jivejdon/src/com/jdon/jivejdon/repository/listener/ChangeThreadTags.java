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
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.repository.TagRepository;

@Component("changeTags")
public class ChangeThreadTags implements MessageListener {

	private final TagRepository tagRepository;

	public ChangeThreadTags(TagRepository tagRepository) {
		super();
		this.tagRepository = tagRepository;
	}

	public void action(DomainMessage eventMessage) {
		ForumThread forumThread = (ForumThread) eventMessage.getEventSource();
		try {
			tagRepository.mergeTagTitle(forumThread.getThreadId(), forumThread.getTagTitles());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}