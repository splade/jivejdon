package com.jdon.jivejdon.manager.throttle.hitkey;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.controller.cache.Cache;
import com.jdon.controller.cache.CacheManager;
import com.jdon.jivejdon.manager.block.IPBanListManagerIF;
import com.jdon.jivejdon.manager.throttle.ExpiringCacheEntry;

@Component("customizedThrottle")
public class CustomizedThrottleImp implements CustomizedThrottle {
	private final static Logger log = Logger.getLogger(CustomizedThrottleImp.class);

	private HitConf hitConf;

	private final Cache clientHistoryCache;

	private final IPBanListManagerIF iPBanListManager;

	public CustomizedThrottleImp(HitConf hitConf, CacheManager cacheManager, IPBanListManagerIF iPBanListManager) {
		this.clientHistoryCache = cacheManager.getCache();
		this.iPBanListManager = iPBanListManager;
		this.hitConf = hitConf;
	}

	/*
	 * throttling protection against spammers
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.manager.throttle.ThrottleManagerIF#processHitFilter
	 *      (java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.manager.throttle.img.ImgshowThrottle#processHitFilter
	 *      (com.jdon.jivejdon.manager.throttle.img.ImgHitKey)
	 */
	public boolean processHitFilter(HitKeyIF hitKey) {

		if (iPBanListManager.isBanned(hitKey.getHitIp())) {
			return false;
		}

		// throttling protection against spammers
		if (processHit(hitKey)) {
			log.debug("ABUSIVE " + hitKey.getHitIp());
			iPBanListManager.addBannedIp(hitKey.getHitIp());
			return false;
		}
		return true;
	}

	public void addBanned(String ip) {
		iPBanListManager.addBannedIp(ip);
	}

	public void removeBanned(String ip) {
		iPBanListManager.deleteBannedIp(ip);
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
	public boolean processHit(HitKeyIF hitKey) {

		if (hitKey.isEmpty()) {
			return false;
		}

		// see if we have any info about this client yet
		ClientHitInfo client = fetchClient(hitKey);
		if (client == null) {
			initClient(hitKey);
			return false;
		}

		// if we already know this client then update their hit count and
		// see if they have surpassed the threshold
		if (hitKey.satisfy(client.hitKey))
			client.hits++;

		log.debug("STATUS " + hitKey + " - " + client.hits + " hits since " + client.start);

		// abusive client
		if (client.hits > this.hitConf.getThreshold()) {
			return true;
		} else
			return false;

	}

	private ClientHitInfo fetchClient(HitKeyIF hitKey) {
		ClientHitInfo client = null;
		ExpiringCacheEntry cacheEntry = (ExpiringCacheEntry) clientHistoryCache.get(hitKey.getKey());
		if (cacheEntry != null) {
			log.debug("HIT " + hitKey);
			client = (ClientHitInfo) cacheEntry.getValue();

			// this means entry had expired
			if (client == null) {
				log.debug("EXPIRED " + hitKey);
				clientHistoryCache.remove(hitKey.getKey());
			}
		}
		return client;
	}

	private void initClient(HitKeyIF hitKey) {
		log.debug("NEW " + hitKey);

		// first timer
		ClientHitInfo newClient = new ClientHitInfo();
		newClient.hits = 1;
		newClient.hitKey = hitKey;

		ExpiringCacheEntry newEntry = new ExpiringCacheEntry(newClient, this.hitConf.getInterval());
		clientHistoryCache.put(hitKey.getKey(), newEntry);
	}

	public void clear() {
		clientHistoryCache.clear();
		iPBanListManager.clear();
	}

}
