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

import java.io.Serializable;

import com.jdon.jivejdon.repository.EmbedIF;


public class ForumModel implements EmbedIF, Serializable{
	private static final long serialVersionUID = 1L;
	
	// if has embedded object;
	private volatile  boolean embedded;

	/**
	 * @return Returns the embedded.
	 */
	public boolean isEmbedded() {
		return embedded;
	}

	/**
	 * when embedded is false, this model are not completely ready,
	 * it has not embedded other association sub object.
	 * so the model can not be used by View client.
	 *  
	 * @param embedded The embedded to set.
	 */
	public void setEmbedded(boolean embedded) {
		this.embedded = embedded;
	}

	
	
}

