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
package com.jdon.jivejdon.manager.throttle.post;

import org.apache.log4j.Logger;

import com.jdon.controller.cache.Cache;
import com.jdon.controller.cache.CacheManager;
import com.jdon.jivejdon.manager.block.IPBanListManagerIF;
import com.jdon.jivejdon.manager.throttle.ClientInfo;
import com.jdon.jivejdon.manager.throttle.ExpiringCacheEntry;

/**
 * * From Apache Roller org.apache.roller.util.GenericThrottle
 * 
 * A tool used to provide throttling support.
 * 
 * The basic idea is that if the # of hits from a client within a certain
 * interval of time is greater than the threshold value then the client is
 * considered to be abusive.
 * 
 */
public class ThrottleManager {

	private final static Logger log = Logger.getLogger(ThrottleManager.class);

	private final Cache clientHistoryCache;

	private final IPBanListManagerIF iPBanListManager;

	private int threshold = 25;

	private int interval = 60000; // milliseconds

	public ThrottleManager(CacheManager cacheManager, IPBanListManagerIF iPBanListManager) {
		this.clientHistoryCache = cacheManager.getCache();
		this.iPBanListManager = iPBanListManager;
	}

	public boolean checkValidate(String ip) {
		if (iPBanListManager.isBanned(ip))
			return false;

		if (processHit(ip)) {
			iPBanListManager.addBannedIp(ip);
			return false;
		}

		return true;
	}

	public void blockIP(String ip) {
		iPBanListManager.addBannedIp(ip);
	}

	/**
	 * Process a new hit from the client.
	 * 
	 * Each call to this method increments the hit count for the client and then
	 * returns a boolean value indicating if the hit has pushed the client over
	 * the threshold.
	 * 
	 * @retuns true if client is abusive, false otherwise
	 */
	private boolean processHit(String clientId) {

		if (clientId == null) {
			return false;
		}

		// see if we have any info about this client yet
		ClientInfo client = null;
		ExpiringCacheEntry cacheEntry = (ExpiringCacheEntry) clientHistoryCache.get(clientId);
		if (cacheEntry != null) {
			log.debug("HIT " + clientId);
			client = (ClientInfo) cacheEntry.getValue();

			// this means entry had expired
			if (client == null) {
				log.debug("EXPIRED " + clientId);
				clientHistoryCache.remove(clientId);
			}
		}

		// if we already know this client then update their hit count and
		// see if they have surpassed the threshold
		if (client != null) {

			log.debug("STATUS " + clientId + " - " + client.hits + " hits since " + client.start);

			// abusive client
			if (client.hits > this.threshold) {
				return true;
			}
			client.hits++;
		} else {
			log.debug("NEW " + clientId);

			// first timer
			ClientInfo newClient = new ClientInfo();
			newClient.hits = 1;
			newClient.id = clientId;

			ExpiringCacheEntry newEntry = new ExpiringCacheEntry(newClient, this.interval);
			clientHistoryCache.put(clientId, newEntry);
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.manager.throttle.ThrottleManagerIF#isAbusive(java.lang
	 *      .String)
	 */
	public boolean isAbusive(String clientId) {
		if (clientId == null) {
			return false;
		}
		if (iPBanListManager.isBanned(clientId)) {
			return true;
		}
		return false;

	}

	public void clear() {
		clientHistoryCache.clear();
		iPBanListManager.clear();
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
