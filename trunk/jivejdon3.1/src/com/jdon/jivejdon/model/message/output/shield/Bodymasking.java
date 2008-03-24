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
package com.jdon.jivejdon.model.message.output.shield;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageRendering;

/**
 * when first running, must config this class in managers.xml
 * @author banq
 *
 */
public class Bodymasking extends MessageRendering {

	private String maskLocalization = "THIS MESSAGE HAS BEEN MASKED";

	public MessageRendering clone(ForumMessage message) {
		Bodymasking filter = new Bodymasking();
		filter.message = message;
		filter.maskLocalization = maskLocalization;
		return filter;
	}

	public String applyFilteredBody() {
		if (message.isMasked()){
			return maskLocalization;
		}else
		    return message.getFilteredBody();
	}

	public String getMaskLocalization() {
		return maskLocalization;
	}

	public void setMaskLocalization(String maskLocalization) {
		this.maskLocalization = maskLocalization;
	}
	
	
}
