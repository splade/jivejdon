/*
 * Copyright 2003-2006 the original author or authors.
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
package com.jdon.jivejdon.presentation.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * UserCounterListener class used to count the current number
 * of active users for the applications.  Does this by counting
 * how many user objects are stuffed into the session.  It Also grabs
 * these users and exposes them in the servlet context.
 *
 * @web.listener
 */
public class UserCounterListener implements ServletContextListener, HttpSessionListener {
	public static final String COUNT_KEY = "userCounter";

	private transient ServletContext servletContext;

	private Set sessionIds;

	public synchronized void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		servletContext.setAttribute((COUNT_KEY), Integer.toString(0));
		sessionIds = new HashSet();
	}

	public synchronized void contextDestroyed(ServletContextEvent event) {
		servletContext = null;
		sessionIds = null;
	}

	private void addSession(Object sessionId) {
		sessionIds.add(sessionId);
		servletContext.setAttribute(COUNT_KEY, Integer.toString(sessionIds.size()));
	}

	private void removeSession(Object sessionId) {
		if (sessionIds != null) {
			sessionIds.remove(sessionId);
			servletContext.setAttribute(COUNT_KEY, Integer.toString(sessionIds.size()));
		}
	}

	public void sessionCreated(HttpSessionEvent se) {
		addSession(se.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		removeSession(se.getSession().getId());
	}

}