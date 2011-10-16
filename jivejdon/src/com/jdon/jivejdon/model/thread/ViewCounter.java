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

import java.util.concurrent.atomic.AtomicLong;

import com.jdon.jivejdon.model.ForumThread;

public class ViewCounter {

	private final AtomicLong viewCount;
	private volatile String lastViewIP;
	private final ForumThread thread;

	public ViewCounter(ForumThread thread, long count) {
		this.thread = thread;
		this.viewCount = new AtomicLong(count);
	}

	public int getViewCount() {
		return viewCount.intValue();
	}

	public void addViewCount() {
		viewCount.incrementAndGet();
	}

	public void addViewCount(String ip) {
		if (!ip.equals(lastViewIP)) {
			viewCount.incrementAndGet();
			lastViewIP = ip;
		} else
			viewCount.intValue();
	}

	public void setViewCount(int delta) {
		this.viewCount.set(delta);
	}

	public ForumThread getThread() {
		return thread;
	}

}
