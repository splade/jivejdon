package com.jdon.jivejdon.repository;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.SequenceDao;
import com.jdon.jivejdon.dao.UploadFileDao;
import com.jdon.jivejdon.model.UploadFile;

public class UploadRepository {
	private final static Logger logger = Logger.getLogger(UploadRepository.class);
	private SequenceDao sequenceDao;

	private UploadFileDao uploadFileDao;
	
	public UploadRepository(SequenceDao sequenceDao, UploadFileDao uploadFileDao) {
		this.sequenceDao = sequenceDao;
		this.uploadFileDao = uploadFileDao;
	}
	
	public Collection getUploadFiles(Long messageId) {
		return uploadFileDao.getAdjunct(messageId);
	}

	public void removeUploadFile(UploadFile uploadFile) {
		String id = uploadFile.getId();
		uploadFileDao.deleteUploadFile(id);
	}

	public UploadFile getUploadFile(String objectId) {
		return uploadFileDao.getUploadFile(objectId);
	}

	public void saveAllUploadFiles(String parentId, Collection uploads) {
		logger.debug(" prepareForSaveAllUploadFiles parentId=" + parentId);
		try {
			logger.debug(" uploads size=" + uploads.size());
			Iterator iter = uploads.iterator();
			while (iter.hasNext()) {
				UploadFile uploadFile = (UploadFile) iter.next();
				uploadFile.setParentId(parentId);
				Long mIDInt = sequenceDao.getNextId(Constants.OTHERS);
				uploadFile.setId(mIDInt.toString());
				uploadFileDao.createUploadFile(uploadFile);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
