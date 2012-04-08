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
package com.jdon.jivejdon.presentation.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jdon.util.UtilValidate;

/**
 * Ban clients that not refer from owner domain, or other spammer that not in
 * web..xml
 * 
 * this Ban action is in Interval such as 30 minutes.
 * 
 * @author banq
 * 
 */
public class SpamFilterRefer implements Filter {
	private final static Logger log = Logger.getLogger(SpamFilterRefer.class);

	protected Pattern domainPattern;

	public static String DP = "domainPattern";

	public void init(FilterConfig config) throws ServletException {

		String domainPatternStr = config.getInitParameter("referrer.domain.namePattern");
		if (!UtilValidate.isEmpty(domainPatternStr)) {
			try {
				domainPattern = Pattern.compile(domainPatternStr);
				if (domainPattern != null)
					config.getServletContext().setAttribute(SpamFilterRefer.DP, domainPattern);
			} catch (Exception e) {
				log.error("Error parsingreferrer.domain.namePattern value '" + domainPattern, e);
			}
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (!isPermittedReferer(httpRequest)) {
			log.error("spammer, not permitted referer :" + httpRequest.getRequestURI() + " refer:" + httpRequest.getHeader("Referer") + " remote:"
					+ httpRequest.getRemoteAddr());
			disableSessionOnlines(httpRequest);
			if (!response.isCommitted())
				response.reset();
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(503);
			return;
		}

		chain.doFilter(request, response);
		return;
	}

	private boolean isPermittedReferer(HttpServletRequest request) {
		String referrerUrl = request.getHeader("Referer");
		if (referrerUrl == null) {
			if (request.getRemoteAddr().equalsIgnoreCase("127.0.0.1") || request.getRemoteAddr().equalsIgnoreCase("0:0:0:0:0:0:0:1")
					|| request.getRemoteAddr().equalsIgnoreCase("localhost")) {
				return true;
			}
		} else if (referrerUrl != null && referrerUrl.length() > 0 && domainPattern != null) {
			if (domainPattern.matcher(referrerUrl.toLowerCase()).matches()) {
				return true;
			}
		}
		return false;
	}

	private void disableSessionOnlines(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		if (session != null)
			session.invalidate();
	}

	public void destroy() {

	}

}
