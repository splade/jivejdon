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
package com.jdon.jivejdon.presentation.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.manager.block.IPBanListManagerIF;
import com.jdon.util.UtilValidate;

/**
 * if referrerUrl inlucde if referrerUrl is empty, it is a spam if referrerUrl
 * is not starting with "http://" , it is spam
 * 
 * see configuration in web.xml
 * 
 */
public class SpamFilter implements Filter {
	private final static Logger log = Logger.getLogger(SpamFilter.class);

	protected Pattern robotPattern;

	protected Pattern domainPattern;

	protected ServletContext servletContext;

	protected IPBanListManagerIF iPBanListManagerIF;

	public void init(FilterConfig config) throws ServletException {
		// check for possible robot pattern
		String robotPatternStr = config.getInitParameter("referrer.robotCheck.userAgentPattern");
		if (!UtilValidate.isEmpty(robotPatternStr)) {
			// Parse the pattern, and store the compiled form.
			try {
				robotPattern = Pattern.compile(robotPatternStr);
			} catch (Exception e) {
				// Most likely a PatternSyntaxException; log and continue as if
				// it is not set.
				log.error("Error parsing referrer.robotCheck.userAgentPattern value '" + robotPatternStr + "'.  Robots will not be filtered. ", e);
			}
		}

		String domainPatternStr = config.getInitParameter("referrer.domain.namePattern");
		if (!UtilValidate.isEmpty(domainPatternStr)) {
			try {
				domainPattern = Pattern.compile(domainPatternStr);
			} catch (Exception e) {
				log.error("Error parsingreferrer.domain.namePattern value '" + domainPattern, e);
			}
		}

		String thresholdStr = config.getInitParameter("throttle.conf.threshold");
		String intervalStr = config.getInitParameter("throttle.conf.interval");
		if (!UtilValidate.isEmpty(thresholdStr) && !UtilValidate.isEmpty(intervalStr)) {
			// throttleConf = new ThrottleConf(thresholdStr, intervalStr);
		}

		servletContext = config.getServletContext();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		/**
		 * if (isPermitted(httpRequest)) { chain.doFilter(request, response);
		 * return; }
		 * 
		 * if (isPermittedRobot(httpRequest)) { chain.doFilter(request,
		 * response); disableSessionOnlines(httpRequest); return; }
		 */
		if (isSpam(httpRequest)) {
			log.debug("spammer, giving 'em a 503");
			disableSessionOnlines(httpRequest);
			if (!response.isCommitted())
				response.reset();
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(503);
			return;
		}

		chain.doFilter(request, response);
	}

	private void disableSessionOnlines(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		if (session != null)
			session.invalidate();
	}

	private boolean isPermittedRobot(HttpServletRequest request) {
		// if refer is null, 1. browser 2. google 3. otherspam
		String userAgent = request.getHeader("User-Agent");
		if (robotPattern != null) {
			if (userAgent != null && userAgent.length() > 0 && robotPattern.matcher(userAgent.toLowerCase()).matches()) {
				disableSessionOnlines(request);// although permitted, but
				// disable session.
				return true;
			}
		}
		return false;
	}

	private boolean isPermitted(HttpServletRequest request) {
		String referrerUrl = request.getHeader("Referer");
		if (domainPattern != null) {
			if (referrerUrl != null && referrerUrl.length() > 0 && domainPattern.matcher(referrerUrl.toLowerCase()).matches()) {
				return true;
			}
		}

		// String clinetIp = request.getRemoteAddr();
		// if ((clinetIp !=null) && (clinetIp.indexOf("127.0.0.1") != -1))
		// return true; //if localhost debug, skip;
		return false;
	}

	/**
	 * Process the incoming request to extract referrer info and pass it on to
	 * the referrer processing queue for tracking.
	 * 
	 * @returns true if referrer was spam, false otherwise
	 */
	protected boolean isSpam(HttpServletRequest request) {
		return isSpamForThrottle2(request);
	}

	protected boolean isSpamForThrottle2(HttpServletRequest request) {
		if (iPBanListManagerIF == null)
			iPBanListManagerIF = (IPBanListManagerIF) WebAppUtil.getComponentInstance("iPBanListManager", request);
		if (iPBanListManagerIF.isBanned(request.getRemoteAddr())) {
			String userAgent = request.getHeader("User-Agent");
			String referrerUrl = request.getHeader("Referer");
			log.error("it is spam : processing referrer for " + request.getRequestURI() + " referrerUrl=" + referrerUrl + " userAgent=" + userAgent
					+ " ip=" + request.getRemoteAddr());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Unused.
	 */
	public void destroy() {
	}
}
