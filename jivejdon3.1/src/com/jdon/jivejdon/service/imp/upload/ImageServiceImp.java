package com.jdon.jivejdon.service.imp.upload;

import org.apache.log4j.Logger;

import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.controller.pool.Poolable;
import com.jdon.jivejdon.auth.ResourceAuthorization;
import com.jdon.jivejdon.dao.ImageInfoDao;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ImageInfo;
import com.jdon.jivejdon.service.ImageService;
import com.jdon.jivejdon.service.UploadService;
import com.jdon.jivejdon.service.imp.message.MessageKernel;
import com.jdon.jivejdon.service.imp.message.MessageRenderingFilter;
import com.jdon.jivejdon.service.util.SessionContextUtil;
/**
 * 
 * @author <a href="mailto:xinying_ge@yahoo.com.cn">GeXinying</a>
 *
 */
public class ImageServiceImp implements ImageService, Poolable{	

	private final static Logger logger = Logger.getLogger(ImageServiceImp.class);
	
	private ImageInfoDao imageInfoDao;
	
	
    protected MessageRenderingFilter messageRenderingFilter;
   
    
	public ImageServiceImp(ImageInfoDao imageInfoDao,MessageRenderingFilter messageRenderingFilter)
	{
		this.imageInfoDao = imageInfoDao;
		this.messageRenderingFilter = messageRenderingFilter;
	}
	
	public void deleteImage(EventModel em) {
		// TODO Auto-generated method stub
		logger.info("delete.....");
		ImageInfo imageInfo = (ImageInfo)em.getModelIF();
		imageInfoDao.deleteImage(imageInfo.getImageId());
	}

	public ImageInfo getImage(String imageId) {
		// TODO Auto-generated method stub
//		messageRenderingFilter.findFilteredMessage(Long.getLong("102"));
		return imageInfoDao.getImage(imageId);
	}

	public void updateImage(EventModel em) {
		// TODO Auto-generated method stub

	}
	
	public PageIterator getImages(int start, int count) {
		// TODO Auto-generated method stub
		PageIterator pageIterator = new PageIterator();
		try {
            pageIterator = imageInfoDao.getImages(start, count);
        } catch (Exception ex) {
            logger.error(ex);
        }
		return pageIterator;
	}
}
