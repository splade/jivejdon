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

import com.jdon.jivejdon.manager.block.IPBanList;

public interface ThrottleManagerIF {
	
	/*
	 * throttling protection against spammers
	 */
	public abstract void processHitFilter(String ip) throws Exception;
	
	
	public boolean isValidate(String ip);

	/**
	 * Process a new hit from the client.
	 *
	 * Each call to this method increments the hit count for the client and
	 * then returns a boolean value indicating if the hit has pushed the client
	 * over the threshold.
	 *
	 * @retuns true if client is abusive, false otherwise
	 */
	public abstract boolean processHit(String clientId);

	/**
	 * Check the current status of a client.
	 *
	 * A client is considered abusive if the number of hits from the client
	 * within the configured interval is greater than the set threshold.
	 *
	 * @returns true if client is abusive, false otherwise.
	 */
	public abstract boolean isAbusive(String clientId);
	

}