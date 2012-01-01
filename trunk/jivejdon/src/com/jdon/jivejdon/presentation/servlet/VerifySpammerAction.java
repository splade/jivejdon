package com.jdon.jivejdon.presentation.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.manager.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.presentation.form.SkinUtils;

public class VerifySpammerAction extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(VerifySpammerAction.class);

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("enter VerifySpammerAction");

		String registerCode = request.getParameter("registerCode");
		if (registerCode == null || registerCode.length() == 0) {
			response.sendError(404);
		}

		if (SkinUtils.verifyRegisterCode(registerCode, request)) {
			CustomizedThrottle customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", request);
			customizedThrottle.removeBanned(request.getRemoteAddr());
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.sendRedirect(request.getContextPath());
			return;
		}
		response.sendError(404);

	}
}
