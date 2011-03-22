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
package com.jdon.jivejdon.manager.throttle.post;

import org.apache.log4j.Logger;

public class NewUserThrottleConf {
	private final static Logger log = Logger.getLogger(ThrottleConf.class);

	// threshold and interval to determine who is abusive
	private int threshold = 2;

	private int interval = 60000; // milliseconds

	private int newUserThreshold = 86400; // 1 day 1x24x60x60 seconds

	// see manager.xml config.parameter
	public NewUserThrottleConf(String threshold, String interval, String newUserThresholds) {
		// threshold can't be negative, that would mean everyone is abusive
		int thresh = 2;
		try {
			thresh = Integer.parseInt(threshold);
		} catch (Exception e) {
			log.warn("bad input for config property comment.throttle.threshold");
		}
		if (thresh > -1) {
			this.threshold = thresh;
		}

		// interval must be a positive value
		int inter = 60000;
		try {
			inter = Integer.parseInt(interval);
		} catch (NumberFormatException e) {
			log.warn("bad input for config property comment.throttle.interval");
		}
		if (inter > 0) {
			this.interval = inter * 1000;// convert from seconds to
			// milliseconds
		}

		newUserThreshold = Integer.parseInt(newUserThresholds);

	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int getNewUserThreshold() {
		return newUserThreshold;
	}

	public void setNewUserThreshold(int newUserThreshold) {
		this.newUserThreshold = newUserThreshold;
	}

}
