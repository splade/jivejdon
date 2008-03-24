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

import org.apache.log4j.Logger;

import com.jdon.controller.cache.Cache;
import com.jdon.controller.cache.CacheManager;
import com.jdon.jivejdon.manager.block.IPBanList;

/**
 *  * From Apache Roller org.apache.roller.util.GenericThrottle
 * 
 * A tool used to provide throttling support.
 *
 * The basic idea is that if the # of hits from a client within a certain
 * interval of time is greater than the threshold value then the client is
 * considered to be abusive.
 * 
 */
public class ThrottleManager implements ThrottleManagerIF {

	private final static Logger log = Logger.getLogger(ThrottleManager.class);

	private ThrottleConf throttleConf;

    private ThrottleHolderIF throttleHolder;

	public ThrottleManager(ThrottleConf throttleConf, ThrottleHolderIF throttleHolder) {
		this.throttleConf = throttleConf;
		this.throttleHolder = throttleHolder;
	}
	
	/*
	 * throttling protection against spammers
	 */
	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.manager.throttle.ThrottleManagerIF#processHitFilter(java.lang.String)
	 */
	public void processHitFilter(String ip) throws Exception{
		
		if (throttleHolder.getIPBanList().isBanned(ip)){
			throw new Exception("BANNED "+ ip);
		}
		 // throttling protection against spammers
        if(processHit(ip)){
            log.debug("ABUSIVE "+ ip);
            throttleHolder.getIPBanList().addBannedIp(ip);
            throw new Exception("ABUSIVE "+ ip);
        }
	}

	
    public boolean isValidate(String ip){
		if (throttleHolder.getIPBanList().isBanned(ip)){
			return false;
		}
		 // throttling protection against spammers
        if(processHit(ip)){
            log.debug("ABUSIVE "+ ip);
            throttleHolder.getIPBanList().addBannedIp(ip);
            return false;
        }
        return true;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.manager.throttle.ThrottleManagerIF#processHit(java.lang.String)
	 */
	public boolean processHit(String clientId) {

		if (clientId == null) {
			return false;
		}

		// see if we have any info about this client yet
		ClientInfo client = null;
		ExpiringCacheEntry cacheEntry = (ExpiringCacheEntry)throttleHolder.getClientHistoryCache().get(clientId);
		if (cacheEntry != null) {
			log.debug("HIT " + clientId);
			client = (ClientInfo) cacheEntry.getValue();

			// this means entry had expired
			if (client == null) {
				log.debug("EXPIRED " + clientId);
				throttleHolder.getClientHistoryCache().remove(clientId);
			}
		}

		// if we already know this client then update their hit count and 
		// see if they have surpassed the threshold
		if (client != null) {
			client.hits++;

			log.debug("STATUS " + clientId + " - " + client.hits + " hits since " + client.start);

			// abusive client
			if (client.hits > this.throttleConf.getThreshold()) {
				return true;
			}

		} else {
			log.debug("NEW " + clientId);

			// first timer
			ClientInfo newClient = new ClientInfo();
			newClient.hits = 1;
			newClient.id = clientId;

			ExpiringCacheEntry newEntry = new ExpiringCacheEntry(newClient, this.throttleConf.getInterval());
			throttleHolder.getClientHistoryCache().put(clientId, newEntry);
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see com.jdon.jivejdon.manager.throttle.ThrottleManagerIF#isAbusive(java.lang.String)
	 */
	public boolean isAbusive(String clientId) {

		if (clientId == null) {
			return false;
		}

		// see if we have any info about this client
		ClientInfo client = null;
		ExpiringCacheEntry cacheEntry = (ExpiringCacheEntry) throttleHolder.getClientHistoryCache().get(clientId);
		if (cacheEntry != null) {
			log.debug("HIT " + clientId);
			client = (ClientInfo) cacheEntry.getValue();

			// this means entry had expired
			if (client == null) {
				log.debug("EXPIRED " + clientId);
				throttleHolder.getClientHistoryCache().remove(clientId);
			}
		}

		if (client != null) {
			return (client.hits > this.throttleConf.getThreshold());
		} else {
			return false;
		}
	}

	
}
