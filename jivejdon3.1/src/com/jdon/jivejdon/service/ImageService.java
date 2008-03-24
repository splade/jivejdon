package com.jdon.jivejdon.service;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ImageInfo;

/**
 * 
 * @author <a href="mailto:xinying_ge@yahoo.com.cn">GeXinying</a>
 * 
 */
public interface ImageService {

	public void updateImage(EventModel em);

	public void deleteImage(EventModel em);

	public ImageInfo getImage(String imageId);
	
	PageIterator getImages(int start, int count);
}
