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
package com.jdon.jivejdon.repository;

import java.util.Collection;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ThreadTag;
import com.jdon.jivejdon.model.query.specification.TaggedThreadListSpec;

public interface TagRepository {

	public abstract Collection<Long> searchTitle(String s);

	public abstract PageIterator getTaggedThread(TaggedThreadListSpec taggedThreadListSpec, int start, int count);

	public abstract PageIterator getThreadTags(int start, int count);

	public abstract ThreadTag getThreadTag(Long tagID);

	public abstract void addTagTitle(Long threadId, String[] tagTitle) throws Exception;

	// 进行比较 使用hibernate merge就一句OK
	public abstract void mergeTagTitle(Long threadId, String[] tagTitles) throws Exception;

	public abstract void deleteTagTitle(Long threadId) throws Exception;

	public abstract Collection getThreadTags(ForumThread forumThread);

	public abstract Collection getThreadTags(Long forumThreadId);

	public abstract void updateThreadTag(ThreadTag threadTag) throws Exception;

	public abstract void deleteThreadTag(ThreadTag threadTag) throws Exception;

}