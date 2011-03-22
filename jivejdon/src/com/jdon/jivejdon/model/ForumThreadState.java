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

import java.util.Date;
import java.util.concurrent.FutureTask;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.subscription.SubscribedState;
import com.jdon.jivejdon.model.subscription.subscribed.ThreadSubscribed;
import com.jdon.jivejdon.model.thread.ViewCounter;
import com.jdon.treepatterns.model.TreeModel;

/**
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class ForumThreadState {
	private static final long serialVersionUID = 1L;

	/**
	 * the number of messages in the thread. This includes the root message. So,
	 * to find the number of replies to the root message, subtract one from the
	 * answer of this method.
	 */
	private volatile int messageCount = 0;

	private volatile ViewCounter viewCounter;

	private volatile ForumMessage lastPost;

	// used to comupte weight task such as TreeModel construct
	protected FutureTask futureTask;

	/**
	 * 
	 * move from ForumThread to here, it is a state property when has a
	 * lastPost, TreeModel changed!
	 */
	private volatile TreeModel treeModel;

	/**
	 * 
	 * move from ForumThread to here,it is a state property when has a lastPost,
	 * modifiedDate is modified it is equals lastPost.modifiedDate so no used
	 */
	private volatile String modifiedDate;

	private ForumThread forumThread;

	private SubscribedState subscribedState;

	public ForumThreadState(ForumThread forumThread) {
		super();
		this.forumThread = forumThread;
		this.subscribedState = new SubscribedState(new ThreadSubscribed(forumThread));
		this.viewCounter = new ViewCounter(forumThread);
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
		if (messageCount >= 1)
			this.messageCount = messageCount - 1;
		else
			this.messageCount = messageCount;
	}

	public void addMessageCount() {
		this.messageCount = messageCount + 1;
	}

	// http://forums.sun.com/thread.jspa?threadID=666377
	public void addViewCount(String ip) {
		viewCounter.addViewCount(ip);
		this.forumThread.getDomainEvent().refreshThreadCount(viewCounter);
	}

	public void setViewCount(int count) {
		viewCounter.setViewCount(count);
	}

	public ForumMessage getLastPost() {
		return lastPost;
	}

	public void setLastPost(ForumMessage lastPost) {
		this.lastPost = lastPost;
	}

	public ForumThread getForumThread() {
		return forumThread;
	}

	/**
	 * @return Returns the treeModel.
	 */
	public TreeModel getTreeModel() {
		try {
			if (futureTask != null) {
				// ThreadBuilder#buildTreeModel
				TreeModel treeModel = (TreeModel) this.futureTask.get();
				this.treeModel = treeModel;
				this.futureTask = null;
			}
			return treeModel;
		} catch (Exception e) {
			System.err.print(e);
			return null;
		} finally {
		}
	}

	public void setTreeModel(TreeModel treeModel) {
		this.treeModel = treeModel;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public long getModifiedDate2() {
		Date mdate = Constants.parseDateTime(modifiedDate);
		return mdate.getTime();
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getSubscriptionCount() {
		return subscribedState.getSubscriptionCount(this.forumThread.getDomainEvent());
	}

	public void updateSubscriptionCount(int count) {
		subscribedState.update(count);
	}

	public FutureTask getFutureTask() {
		return futureTask;
	}

	public void setFutureTask(FutureTask futureTask) {
		this.futureTask = futureTask;
	}

	public int getViewCount() {
		return viewCounter.getViewCount();
	}

	public ViewCounter getViewCounter() {
		return viewCounter;
	}

}
