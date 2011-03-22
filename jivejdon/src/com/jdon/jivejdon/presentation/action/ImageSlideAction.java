package com.jdon.jivejdon.presentation.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.service.UploadInfoVOService;
import com.jdon.strutsutil.ModelListForm;

public class ImageSlideAction extends Action {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		UploadInfoVOService uploadInfoVOService = (UploadInfoVOService) WebAppUtil.getService("uploadInfoVOService", request);
		int count = 0;
		if (request.getParameter("count") != null) {
			count = Integer.parseInt(request.getParameter("count"));
		}
		ModelListForm imageListForm = (ModelListForm) actionForm;
		Collection images = uploadInfoVOService.getUploadInfoVOsOfMessage(count);
		imageListForm.setList(images);

		return actionMapping.findForward("success");
	}

}
