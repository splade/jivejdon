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
package com.jdon.jivejdon.manager.weibo;

public class WeiboOAuthParamVO {

	public final String CONSUMER_KEY;
	public final String CONSUMER_SECRET;

	private String userId;
	private String passwd;

	public WeiboOAuthParamVO(String cONSUMER_KEY, String cONSUMER_SECRET) {
		super();
		CONSUMER_KEY = cONSUMER_KEY;
		CONSUMER_SECRET = cONSUMER_SECRET;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserId() {
		return userId;
	}

	public String getPasswd() {
		return passwd;
	}

}
