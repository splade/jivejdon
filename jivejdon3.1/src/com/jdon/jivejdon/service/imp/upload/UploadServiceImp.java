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
package com.jdon.jivejdon.service.imp.upload;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.model.UploadFile;
import com.jdon.jivejdon.repository.UploadRepository;
import com.jdon.jivejdon.service.UploadService;

/**
 * CRUD for upload files in Session.
 * 
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public abstract class UploadServiceImp implements UploadService {
	private final static Logger logger = Logger
			.getLogger(UploadServiceImp.class);

	private UploadRepository uploadRepository;

	public UploadServiceImp(UploadRepository uploadRepository) {
		this.uploadRepository = uploadRepository;

	}

	public Collection getUploadFiles(Long messageId) {
		return uploadRepository.getUploadFiles(messageId);
	}

	public void removeUploadFile(EventModel em) {
		UploadFile uploadFile = (UploadFile) em.getModelIF();
		uploadRepository.removeUploadFile(uploadFile);

	}

	public UploadFile getUploadFile(String objectId) {
		logger.debug("getUploadFile for id=" + objectId);
		return uploadRepository.getUploadFile(objectId);
	}


}
