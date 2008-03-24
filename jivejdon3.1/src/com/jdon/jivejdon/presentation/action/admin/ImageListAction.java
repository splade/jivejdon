package com.jdon.jivejdon.presentation.action.admin;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.Model;
import com.jdon.controller.model.ModelIF;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.service.ImageService;
import com.jdon.strutsutil.ModelListAction;
/**
 * 
 * @author <a href="mailto:xinying_ge@yahoo.com.cn">GeXinying</a>
 *
 */
public class ImageListAction extends ModelListAction {

	/*
	 * (non-Javadoc)
	 * @see com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http.HttpServletRequest, int, int)
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int start,
			int count) {
		
		ImageService imageService = (ImageService) WebAppUtil.getService("imageService", request);
		return imageService.getImages(start, count);
	}

	/*
	 * (non-Javadoc)
	 * @see com.jdon.strutsutil.ModelListAction#findModelIFByKey(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	public ModelIF findModelIFByKey(HttpServletRequest request, Object key) {
		
		ImageService imageService = (ImageService) WebAppUtil.getService("imageService", request);
		return imageService.getImage((String)key);
	}

}
