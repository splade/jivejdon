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

import com.jdon.controller.model.ModelIF;
import com.jdon.jivejdon.model.message.MessageRenderingState;
import com.jdon.jivejdon.repository.EmbedIF;


public class ModelState implements ModelIF, MessageRenderingState, EmbedIF{

	// if has embedded object;
	private boolean embedded;

	//if it is applying the filter ?
	private boolean filtered;
	

	/**
	 * if set false, this Model will not be saved to cache, so 
	 * this model will not be got from the cache.
	 */
	private boolean cacheable = true;

	/**
	 * if set true, this model will not be got from the cache,  
	 * because it must have been saved in the cache.
	 * this function is same as deleting the model from the cache.
	 */
	private boolean modified;
	
	

	/**
	 * in the past version, this method name is isCacheble,
	 * now change it after 1.3 !
	 */
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * in the past version, this method name is setCacheble,
	 * now change it  after 1.3 !
	 */
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public boolean isModified() {
		return modified;
	}

	/**
	 * @return Returns the embedded.
	 */
	public boolean isEmbedded() {
		return embedded;
	}

	/**
	 * @param embedded The embedded to set.
	 */
	public void setEmbedded(boolean embedded) {
		this.embedded = embedded;
	}

	/**
	 * @return Returns the filtered.
	 */
	public boolean isFiltered() {
		return filtered;
	}

	/**
	 * @param filtered The filtered to set.
	 */
	public void setFiltered(boolean filtered) {
		this.filtered = filtered;
	}
	

	public void setModified(boolean modified) {
		if (modified) {
			embedded = false;
			filtered = false;
		}
		this.modified = modified;
	}
}

