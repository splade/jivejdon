package com.jdon.jivejdon.manager.listener;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.proptery.ThreadPropertys;
import com.jdon.jivejdon.model.thread.ViewCounter;
import com.jdon.jivejdon.repository.dao.PropertyDao;

/**
 * a cache used for holding view count of ForumThread the data that in the cache
 * will be flushed to database per one hour
 * 
 * @author oojdon banq
 * 
 */

@Component("threadViewCountManager")
public class ThreadViewCountManager implements Startable {
	private final static Logger logger = Logger.getLogger(ThreadViewCountManager.class);

	private ConcurrentMap<Long, ViewCounter> concurrentHashMap = new ConcurrentHashMap<Long, ViewCounter>();
	private static ScheduledExecutorService scheduExec = Executors.newScheduledThreadPool(1);

	private final PropertyDao propertyDao;
	private final ThreadViewCountParameter threadViewCountParameter;

	public ThreadViewCountManager(final PropertyDao propertyDao, final ThreadViewCountParameter threadViewCountParameter) {
		this.propertyDao = propertyDao;
		this.threadViewCountParameter = threadViewCountParameter;
	}

	public void start() {
		Runnable task = new Runnable() {
			public void run() {
				writeDB();
			}
		};
		// flush to db per one hour
		scheduExec.scheduleWithFixedDelay(task, threadViewCountParameter.getInitdelay(), threadViewCountParameter.getDelay(), TimeUnit.SECONDS);
	}

	// when container down or undeploy, active this method.
	public void stop() {
		writeDB();
		scheduExec.shutdown();
		scheduExec = null;
	}

	public void writeDB() {
		Set s = concurrentHashMap.entrySet();
		for (Iterator i = s.iterator(); i.hasNext();) {
			Map.Entry<Long, ViewCounter> m = (Map.Entry) i.next();
			saveItem(m, i);
		}
	}

	private void saveItem(Map.Entry<Long, ViewCounter> m, Iterator i) {
		try {
			ViewCounter viewCounter = (ViewCounter) m.getValue();
			viewCounter.getLock().writeLock().lock();
			try {
				Property property = new Property();
				property.setName(ThreadPropertys.VIEW_COUNT);
				property.setValue(Integer.toString(viewCounter.getViewCount()));
				propertyDao.updateProperty(Constants.THREAD, m.getKey(), property);
			} finally {
				viewCounter.getLock().writeLock().unlock();
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
		}
	}

	// called by thread create factory
	public void initViewCounter(ForumThread thread) {
		Long threadId = thread.getThreadId();
		int count = -1;
		ViewCounter viewCounter = concurrentHashMap.get(threadId);
		if (viewCounter == null) {
			count = getFromDB(threadId);
			viewCounter = new ViewCounter(thread, count);
			concurrentHashMap.put(threadId, viewCounter);
		}
		thread.setViewCounter(viewCounter);
	}

	private int getFromDB(Long threadId) {
		Integer number = null;
		Property p = propertyDao.getThreadProperty(threadId, ThreadPropertys.VIEW_COUNT);
		if (p != null)
			number = Integer.valueOf(p.getValue());
		else
			number = new Integer(0);

		return number;
	}

}
