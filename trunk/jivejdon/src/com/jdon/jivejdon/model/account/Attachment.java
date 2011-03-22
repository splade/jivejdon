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
package com.jdon.jivejdon.model.account;

import java.util.Collection;
import java.util.Iterator;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.attachment.UploadFile;

public class Attachment {

	// for upload files lazyload
	private UploadFile uploadFile;

	private DomainMessage eventMessage;

	private Account account;

	private boolean reload;

	public Attachment(Account account) {
		super();
		this.account = account;
	}

	public UploadFile getUploadFile() {
		try {
			if ((uploadFile == null && eventMessage == null)) {
				eventMessage = account.getDomainEvent().loadUploadFiles(account.getUserIdLong());
				loadUploadFile();

			}
			if (reload) {
				loadUploadFile();
				reload = false;
			}
		} catch (Exception e) {
		}
		return uploadFile;
	}

	private void loadUploadFile() {
		Object result = eventMessage.getEventResult();
		if (result != null) {
			Collection uploads = (Collection) result;
			Iterator iter = uploads.iterator();
			if (iter.hasNext()) {
				uploadFile = (UploadFile) iter.next();
			}
		}

	}

	public void updateUploadFile() {
		eventMessage = account.getDomainEvent().loadUploadFiles(account.getUserIdLong());
		reload = true;
	}

}
