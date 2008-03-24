/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.manager.throttle;

import com.jdon.controller.cache.Cache;
import com.jdon.controller.cache.CacheManager;
import com.jdon.jivejdon.manager.block.IPBanList;

public class ThrottleHolder implements ThrottleHolderIF {
	
    private Cache clientHistoryCache;
	
	private IPBanList iPBanList;
	
	public ThrottleHolder(CacheManager cacheManager,
			IPBanList iPBanList) {
		this.clientHistoryCache = cacheManager.getCache();
		this.iPBanList = iPBanList;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.manager.throttle.ThrottleHolderIF#getClientHistoryCache()
	 */
	public Cache getClientHistoryCache() {
		return clientHistoryCache;
	}

	public void setClientHistoryCache(Cache clientHistoryCache) {
		this.clientHistoryCache = clientHistoryCache;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.manager.throttle.ThrottleHolderIF#getIPBanList()
	 */
	public IPBanList getIPBanList() {
		return iPBanList;
	}

	public void setIPBanList(IPBanList banList) {
		iPBanList = banList;
	}
	
	

}
