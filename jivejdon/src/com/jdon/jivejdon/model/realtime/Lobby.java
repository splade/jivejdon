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
package com.jdon.jivejdon.model.realtime;

import com.jdon.annotation.Component;
import com.jdon.annotation.model.OnEvent;
import com.jdon.jivejdon.model.realtime.notification.ContentFormatConverter;

@Component("lobby")
public class Lobby {

	private final NotificationLocator notificationLocator;

	public Lobby(ContentFormatConverter contentFormatConverter) {
		notificationLocator = new NotificationLocator(contentFormatConverter);
	}

	@OnEvent("lobbyNotify")
	public void addNotification(Notification notification) {
		notificationLocator.addNotification(notification);
	}

	public Notification checkNotification(String username) {
		return notificationLocator.checkNotification(username);
	}

	// remove by com.jdon.jivejdon.presentation.listener.UserCounterListener
	public void removeUser(String username) {
		notificationLocator.removeUser(username);

	}

	public void clear() {
		notificationLocator.clear();
	}

}
