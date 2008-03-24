package com.jdon.jivejdon.dao;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ImageInfo;

/**
 * 
 * @author <a href="mailto:xinying_ge@yahoo.com.cn">GeXinying</a>
 *
 */
public interface ImageInfoDao {

	public PageIterator getImages(int start,int count);
	
	public ImageInfo getImage(String key);
	
	public void deleteImage(String key);
}
