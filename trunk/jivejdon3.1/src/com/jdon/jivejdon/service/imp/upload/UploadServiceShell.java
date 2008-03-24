package com.jdon.jivejdon.service.imp.upload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jdon.container.visitor.data.SessionContext;
import com.jdon.container.visitor.data.SessionContextAcceptable;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.pool.Poolable;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.UploadFile;
import com.jdon.jivejdon.repository.UploadRepository;
import com.jdon.util.UtilValidate;

public class UploadServiceShell extends UploadServiceImp implements
		SessionContextAcceptable, Poolable {
	private final static Logger logger = Logger.getLogger(UploadServiceShell.class);
	
	private final static String UPLOAD_NAME = "UPLOAD";
	  
	protected SessionContext sessionContext;

	public UploadServiceShell(UploadRepository uploadRepository) {
		super(uploadRepository);

	}
	
    /* 
     * save the uploadfile to session becuase the messageId has not produce,
     * when the message create operation is active, will call saveUploadFileToDB method
     */
    public void saveUploadFile(EventModel em) {
        UploadFile uploadFile = (UploadFile)em.getModelIF();
        logger.debug("UploadFile size =" + uploadFile.getSize());
        
        Long messageId = null;  
        if (!UtilValidate.isEmpty(uploadFile.getParentId()))
        	messageId = Long.parseLong(uploadFile.getParentId());
        
        List uploads  = getInitSession(messageId);   
        if(uploads.size() < 3){//@todo  modify it by this user's grade
        	logger.debug("add the upload ");
            uploads.add(uploadFile);
        }else if(uploads.size() >= 3){
            logger.warn("max count is three upload files" );
            em.setErrors(Constants.EXCEED_MAX_UPLOADS);
        }
    }
    
    public Collection getUploadFiles(Long messageId){
    	return getInitSession(messageId);
    }
    
    private List getInitSession(Long messageId){
    	 logger.debug("getUploadFilesFromSession, messageId=" + messageId );
    	List uploads  = (List)sessionContext.getArrtibute(UPLOAD_NAME);
        if (uploads == null){
     	    uploads = new ArrayList();
            sessionContext.setArrtibute(UPLOAD_NAME, uploads);
            logger.debug("first time init ");
            if ((messageId != null) && (messageId.longValue() != 0)){
                Collection dbList = super.getUploadFiles(messageId);                
                Iterator iter = dbList.iterator();
                while(iter.hasNext()){
                	UploadFile uploadFile = (UploadFile)iter.next();
                	uploadFile.setNew(false); //it is not new it read from persistence
                	uploads.add(uploadFile);
                }
            }
        }
        return uploads;
    }
    
  
    /** 
     * get all UploadFiles include session but not exclude the old
     */
    public Collection loadNewUploadFiles(SessionContext sessionContext){
        logger.debug(" loadUploadFiles ");
        Collection newuploads = new ArrayList();
        try {
        	List uploads  = (List)sessionContext.getArrtibute(UPLOAD_NAME);
            sessionContext.remove(UPLOAD_NAME);
            if (uploads == null)
            	return  new ArrayList();
            logger.debug(" uploads size="+ uploads.size());
            Iterator iter = uploads.iterator();
            while(iter.hasNext()){
            	UploadFile uploadFile = (UploadFile)iter.next();
            	if (uploadFile.isNew()){
            		newuploads.add(uploadFile);
            	}
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return newuploads;
    }
          
    
    public void removeUploadFile(EventModel em){
        logger.debug(" uploadService.removeUploadFile ");
        UploadFile uploadFile = (UploadFile)em.getModelIF();
        String tempId = uploadFile.getTempId();
        String id = uploadFile.getId();
        List uploads  = (List)sessionContext.getArrtibute(UPLOAD_NAME);
        if (!UtilValidate.isEmpty(id)){ //if there is id, so it must be existed in db
            //delete the db
            super.removeUploadFile(em);
        }
        if (!UtilValidate.isEmpty(tempId)){
            //delete the session
            uploads.remove(Integer.parseInt(tempId));
        }        
    }
	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext The sessionContext to set.
	 */
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

}
