package com.jdon.jivejdon.presentation.action.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.HotKeys;
import com.jdon.jivejdon.service.TagService;

public class HotKeyListAction extends Action {
	private final static Logger logger = Logger.getLogger(TagsListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter hotkeys");
		HotKeys hotKeys = (HotKeys) request.getSession().getServletContext().getAttribute("HOTKEYS");
		if (hotKeys == null) {
			TagService othersService = (TagService) WebAppUtil.getService("othersService", request);
			hotKeys = othersService.getHotKeys();
			request.getSession().getServletContext().setAttribute("HOTKEYS", hotKeys);
		}
		return mapping.findForward("hotkeys");
	}

}
