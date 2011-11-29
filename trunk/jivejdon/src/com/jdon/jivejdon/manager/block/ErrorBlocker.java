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
package com.jdon.jivejdon.manager.block;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.manager.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.manager.throttle.hitkey.HitKey3;
import com.jdon.jivejdon.manager.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

@Component("errorBlocker")
public class ErrorBlocker implements Startable, ErrorBlockerIF {

	private Map<String, Integer> bannedIPs;

	private final ScheduledExecutorUtil scheduledExecutorUtil;

	private final CustomizedThrottle customizedThrottle;

	public ErrorBlocker(CustomizedThrottle customizedThrottle, ScheduledExecutorUtil scheduledExecutorUtil) {
		super();
		bannedIPs = new ConcurrentHashMap();
		this.customizedThrottle = customizedThrottle;
		this.scheduledExecutorUtil = scheduledExecutorUtil;
	}

	public void start() {
		Runnable task = new Runnable() {
			public void run() {
				clearBlock();
			}
		};
		// flush to db per one hour
		scheduledExecutorUtil.getScheduExec().scheduleAtFixedRate(task, 60, 60 * 60, TimeUnit.SECONDS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.manager.block.ErrorBlockerIF#doErrorIP(java.lang.String
	 * , int)
	 */
	public boolean checkRate(String ip, int callcount) {
		try {
			HitKeyIF hitKey = new HitKey3(ip, "error");
			if (customizedThrottle.processHit(hitKey)) {
				checkCount(ip, callcount);
			}
		} catch (Exception e) {
		}
		return true;

	}

	public void checkCount(String ip, int callcount) {
		if (bannedIPs.containsKey(ip)) {
			int count = bannedIPs.get(ip);
			if (count >= callcount) {
				customizedThrottle.addBanned(ip);
				bannedIPs.remove(ip);
				// BanIPUtils.addIPTables(ip);//high level
			} else {
				System.err.print("ip=" + ip + " has " + count + " times checkErrorIP " + callcount);
				bannedIPs.put(ip, count + 1);
			}
		} else
			bannedIPs.put(ip, 1);

	}

	// when container down or undeploy, active this method.
	public void stop() {
		clearBlock();
	}

	private void clearBlock() {
		bannedIPs.clear();
	}

}
