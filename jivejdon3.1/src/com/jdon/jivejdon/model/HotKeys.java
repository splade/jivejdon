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
package com.jdon.jivejdon.model;

import java.util.Collection;

public class HotKeys {
	
	private int id = 44444444;
	
	private Collection props;
	
	public int getId() {
		return id;
	}
	/**
	public void setId(int id) {
		this.id = id;
	}
	 */
	public Collection getProps() {
		return props;
	}
	public void setProps(Collection props) {
		this.props = props;
	}
	
}
