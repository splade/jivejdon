package com.jdon.jivejdon.dao.search;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.compass.annotations.config.CompassAnnotationsConfiguration;
import org.compass.core.Compass;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.core.CompassTransaction;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.core.config.ConfigurationException;
import org.compass.core.engine.SearchEngineException;

import com.jdon.jivejdon.dao.filter.MessageAssociationDao;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.query.MessageSearchSpec;

public class MessageSearchProxy {
    private final static Logger logger = Logger.getLogger(MessageAssociationDao.class);
    private Compass compass;

	private CompassTemplate compassTemplate;
    
    public MessageSearchProxy() {
    	init();
    }    
    
    public MessageSearchProxy(boolean rebuild) {
    	if (rebuild){
    		reInit();
    	}else{
    		init();	
    	}
    	
    }    
    
    public void init()  {
		try {
			logger.debug("compass init");
			CompassConfiguration config = 
				new CompassAnnotationsConfiguration();
			//.configure("/com/jdon/jivejdon/search/compass/compass.cfg.xml");
			//in j2ee env, the configure seem can not be found
			//error :org.compass.core.config.ConfigurationException: Failed to open config resource
			//com/jdon/jivejdon/search/compass/compass.cfg.xml
			config.setSetting(CompassEnvironment.CONNECTION, "target/testindex");
			config.setSetting("compass.engine.highlighter.default.formatter.simple.pre", "<font color=CC0033>");
			config.setSetting("compass.engine.highlighter.default.formatter.simple.post", "</font>");
			
			
			/**
			config.setSetting("compass.engine.connection", "jdbc://java:/JiveJdonDS");
			config.setSetting("compass.jndi.enable", "true");
			config.setSetting("compass.engine.store.jdbc.connection.provider.class", "org.compass.core.lucene.engine.store.jdbc.ExternalDataSourceProvider"); 
			ExternalDataSourceProvider.setDataSource(ds); 
			

			TableToResourceMapping folderMapping = new TableToResourceMapping("frmfolders", "frmfolders"); 
			TableToResourceMapping templateMapping = new TableToResourceMapping("frmtemplates", "frmtemplates"); 
			TableToResourceMapping variantMapping = new TableToResourceMapping("frmvariants", "frmvariants"); 

			config.addMappingResover(new ResultSetResourceMappingResolver(folderMapping, ds)); 
			config.addMappingResover(new ResultSetResourceMappingResolver(templateMapping, ds)); 
			config.addMappingResover(new ResultSetResourceMappingResolver(variantMapping, ds)); 
			
**/
			config.addClass(com.jdon.jivejdon.model.ForumMessage.class);
			config.addClass(com.jdon.jivejdon.model.Forum.class);
			compass = config.buildCompass();
			compassTemplate = new CompassTemplate(compass);
		} catch (ConfigurationException e) {
            logger.error(e);
			e.printStackTrace();
		} catch (SearchEngineException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (CompassException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
    
    public void reInit()  {
		try {
			logger.debug("compass init");
			CompassConfiguration config = 
				new CompassAnnotationsConfiguration();
			config.setSetting(CompassEnvironment.CONNECTION, "target/testindex");
		
			config.addClass(com.jdon.jivejdon.model.ForumMessage.class);
			config.addClass(com.jdon.jivejdon.model.Forum.class);
			compass = config.buildCompass();
			//compass.getSearchEngineIndexManager().deleteIndex();
			//compass.getSearchEngineIndexManager().createIndex();
			compassTemplate = new CompassTemplate(compass);
		} catch (ConfigurationException e) {
            logger.error(e);
			e.printStackTrace();
		} catch (SearchEngineException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (CompassException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

        
    public void createMessage(ForumMessage  forumMessage) throws Exception{
        logger.debug("MessageSearchProxy.createMessage");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
		    tx = session.beginTransaction();
			session.save(forumMessage);
			tx.commit();
		} catch (Exception ce) {
		    if (tx != null) tx.rollback();
		    logger.error(ce);
		    throw new Exception(" createMessage error " + ce );
		} finally {
		    session.close();
		}
    }
    
    public void createMessageReply(ForumMessageReply  forumMessageReply) throws Exception{
        logger.debug("MessageSearchProxy.createMessageReply");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
		    tx = session.beginTransaction();
			session.save(forumMessageReply);
			tx.commit();
		} catch (Exception ce) {
		    if (tx != null) tx.rollback();
		    logger.error(ce);
		    throw new Exception(" createMessageReply error " + ce );
		} finally {
		    session.close();
		}
    }
    
    public void updateMessage(ForumMessage  forumMessage) throws Exception{
        logger.debug("MessageSearchProxy.updateMessage");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
		    tx = session.beginTransaction();
		    ForumMessage messageS = (ForumMessage) session.load(ForumMessage.class,
		    		forumMessage.getMessageId());
		    messageS.setSubject(forumMessage.getSubject());
		    messageS.setBody(forumMessage.getBody());
		    session.save(messageS);
			tx.commit();
		} catch (Exception ce) {
		    if (tx != null) tx.rollback();
		    logger.error(ce);
		    throw new Exception(" updateMessage error " + ce );
		} finally {
		    session.close();
		}

    }
    
    public void deleteMessage(Long  forumMessageId) throws Exception{
        logger.debug("MessageSearchProxy.deleteMessage");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
		    tx = session.beginTransaction();
		    ForumMessage messageS = (ForumMessage) session.load(ForumMessage.class,
		    		forumMessageId);
			session.delete(messageS);
			tx.commit();
		} catch (Exception ce) {
		    if (tx != null) tx.rollback();
		    logger.error(ce);
		    throw new Exception(" deleteMessage error " + ce );
		} finally {
		    session.close();
		}
    }
    
    /**
     * 
     * @param iquery
     * @return MessageSearchSpec Collection include highlight subject and body
     * @throws Exception
     */
    public Collection find(String query, int start, int count)  {
		logger.debug("MessageSearchProxy.find");
		Collection result = new ArrayList();
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		MessageSearchSpec messageSearchSpec = null;
		try {
			tx = session.beginTransaction();
			CompassHits hits = session.find(query);
			logger.debug("Found [" + hits.getLength() + "] hits for [" + query
					+ "] query");
			int end = start + count;
			if (end >=  hits.getLength())
				end = hits.getLength();
				
			for (int i = start; i < end; i++) {
				logger.debug("create  messageSearchSpec collection");
				ForumMessage smessage = (ForumMessage) hits.data(i);
				messageSearchSpec = new MessageSearchSpec();
				messageSearchSpec.setMessageId(smessage.getMessageId());
				String body = hits.highlighter(i).fragment("body");
				messageSearchSpec.setBody(body);
				messageSearchSpec.setResultAllCount(hits.getLength());
				result.add(messageSearchSpec);
			}
			hits.close();
			tx.commit();
		} catch (Exception ce) {
		    if (tx != null) tx.rollback();
		    logger.error(ce);
		} finally {
		    session.close();
		}
		return result;
	}
    
    
}
