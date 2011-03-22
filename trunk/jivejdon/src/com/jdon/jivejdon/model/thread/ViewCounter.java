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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.jdon.jivejdon.model.ForumThread;

public class ViewCounter {

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private volatile AtomicInteger viewCount = new AtomicInteger(0);
	private volatile String lastViewIP;
	private final ForumThread thread;

	public ViewCounter(ForumThread thread) {
		this.thread = thread;
	}

	public int getViewCount() {
		lock.readLock().lock();
		try {
			return viewCount.intValue();
		} finally {
			lock.readLock().unlock();
		}
	}

	public void addViewCount() {
		lock.writeLock().lock();
		try {
			viewCount.incrementAndGet();
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void addViewCount(String ip) {
		lock.writeLock().lock();
		try {
			if (!ip.equals(lastViewIP)) {
				viewCount.incrementAndGet();
				lastViewIP = ip;
			} else
				viewCount.intValue();
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void setViewCount(int delta) {
		lock.writeLock().lock();
		try {
			this.viewCount.set(delta);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public ReentrantReadWriteLock getLock() {
		return lock;
	}

	public ForumThread getThread() {
		return thread;
	}

}
