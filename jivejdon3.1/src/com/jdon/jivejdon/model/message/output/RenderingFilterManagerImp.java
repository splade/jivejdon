/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.model.message.output;

import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.jdon.jivejdon.dao.SetupDao;
import com.jdon.jivejdon.manager.filter.OutFilterManager;
import com.jdon.jivejdon.model.message.MessageRendering;
import com.jdon.jivejdon.util.BeanUtils;
import com.jdon.jivejdon.util.XMLProperties;

/**
 * setup all filter into Database
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class RenderingFilterManagerImp implements RenderingFilterManager {
    private final static Logger logger = Logger.getLogger(RenderingFilterManagerImp.class);

    private SetupDao setupDao;
    
    public static final String[] DEFAULT_FILTER_CLASSES = new String[] { 
            "com.jdon.jivejdon.model.message.output.html.TextStyle",
            "com.jdon.jivejdon.model.message.output.html.Newline",
           };
    
    private RenderingAvailableFilters renderingAvailableFilters;

    private static XMLProperties properties = null;

    private String context;

    private static MessageRendering[] availableFilters = null;

    private MessageRendering[] filters;
    
    private OutFilterManager outFilterManager;

    /**
     * @param filters
     */
    public RenderingFilterManagerImp(SetupDao setupDao, RenderingAvailableFilters renderingAvailableFilters) {
        this.setupDao = setupDao;
        this.renderingAvailableFilters = renderingAvailableFilters;
        initProperties();
    }

    public MessageRendering[] getAvailableFilters() {
        if (availableFilters == null) {
            logger.debug("enter getAvailableFilters.. availableFilters not null");
            // Load filter classes
            String[] classNames = properties.getChildrenProperties("filterClasses");
            List filterList = new ArrayList();
            for (int i = 0; i < classNames.length; i++) {
                try {
                    String className = properties.getProperty("filterClasses." + classNames[i]);
                    Class filterClass = Class.forName(className);
                    // Attempt a cast. If it fails, we'll skip this class.
                    MessageRendering filter = (MessageRendering) filterClass.newInstance();
                    filterList.add(filter);
                } catch (Exception e) {
                }
            }
            availableFilters = new MessageRendering[filterList.size()];
            for (int i = 0; i < availableFilters.length; i++) {
                availableFilters[i] = (MessageRendering) filterList.get(i);
            }
        }
        return availableFilters;
    }

    public MessageRendering[] getFilters() {
        return filters;
    }

    

    public MessageRendering getFilter(int index) throws Exception {
        
        if (index < 0 || index > filters.length - 1) {
            throw new IllegalArgumentException("Index " + index + " is not valid.");
        }
        return filters[index];
    }

    public int getFilterCount() throws Exception {
        return filters.length;
    }

    public void addFilter(MessageRendering filter) throws Exception {
        addFilter(filter, filters.length);
    }

    public void addFilter(MessageRendering filter, int index) throws Exception {
        logger.debug("enter addFilter, index=" + index);
        ArrayList newFilters = new ArrayList(filters.length + 1);
        for (int i = 0; i < filters.length; i++) {
            newFilters.add(filters[i]);
        }
        newFilters.add(index, filter);
        MessageRendering[] newArray = new MessageRendering[newFilters.size()];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = (MessageRendering) newFilters.get(i);
        }

        // Finally, overwrite filters with the new array
        this.filters = newArray;
        this.outFilterManager.initFilters(this.filters);
        saveFilters();
    }

   
    public void removeFilter(int index) throws Exception {
        logger.debug("enter removeFilter, index=" + index);
        ArrayList newFilters = new ArrayList(filters.length);
        for (int i = 0; i < filters.length; i++) {
            newFilters.add(filters[i]);
        }
        newFilters.remove(index);
        MessageRendering[] newArray = new MessageRendering[newFilters.size()];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = (MessageRendering) newFilters.get(i);
        }
        // Finally, overwrite filters with the new array
        filters = newArray;
        this.outFilterManager.initFilters(this.filters);
        saveFilters();
    }

    public void saveFilters() throws Exception {
        logger.debug("enter saveFilters" + filters.length);
        // Delete all filters from context.
        properties.deleteProperty(context.substring(0, context.length() - 1));
        if (filters.length > 0) {
            properties.setProperty(context + "filterCount", Integer.toString(filters.length));
        }
        // Now write them out again.
        for (int i = 0; i < filters.length; i++) {
            String filterContext = context + "filter" + i + ".";
            // Write out class name.
            properties.setProperty(filterContext + "className", filters[i].getClass().getName());
            // Write out all properties.
            Map filterProps = BeanUtils.getProperties(filters[i]);
            for (Iterator iter = filterProps.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String value = (String) filterProps.get(name);
                properties.setProperty(filterContext + "properties." + name, value);
            }
        }
        saveToDb();
        
    }
    
    public synchronized void addFilterClass(String className) throws ClassNotFoundException, IllegalArgumentException {
        logger.debug("enter addFilterClass, className=" + className);
        // Try to load class. If found in classpath and is a filter, add it to
        // the list
        Class newClass = Class.forName(className);
        try {
            Object newFilter = newClass.newInstance();
            if (!(newFilter instanceof MessageRendering)) {
                throw new IllegalArgumentException("Class is not a ForumMessageFilter");
            }
            MessageRendering[] newFilters = new MessageRendering[availableFilters.length + 1];
            for (int i = 0; i < newFilters.length - 1; i++) {
                newFilters[i] = availableFilters[i];
            }
            newFilters[newFilters.length - 1] = (MessageRendering) newFilter;
            availableFilters = newFilters;
            // Write out new classes names.
            properties.deleteProperty("filterClasses");
            for (int i = 0; i < availableFilters.length; i++) {
                String cName = availableFilters[i].getClass().getName();
                properties.setProperty("filterClasses.filter" + i, cName);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (InstantiationException e2) {
            throw new IllegalArgumentException(e2.getMessage());
        }
        saveToDb();
    }

    
    private void saveToDb(){
        logger.debug("save the value to database:");
        StringWriter writer = new StringWriter();
        properties.savePropertiesToStream(writer);
        String xmlValue = writer.toString();        
        logger.debug(xmlValue);
        setupDao.saveSetupValue(PERSISTENCE_NAME, xmlValue);
    }

    private void initProperties() {
        logger.debug("enter initProperties" );
        String name = "global";
        //  name = "forum" + forumID;
        // Make sure properties are loaded.
        loadProperties();

        // Now load up filters for this manager.
        context = name + ".";
        // See if a record for this context exists yet. If not, create one.
        String fCount = properties.getProperty(context + "filterCount");
        if (fCount == null) {
            fCount = "0";
        }
        int filterCount = 0;
        try {
            filterCount = Integer.parseInt(fCount);
        } catch (NumberFormatException nfe) {
        }
        
        logger.debug("got the filterCount=" +  filterCount);

        // Load up all filters
        filters = new MessageRendering[filterCount];
        for (int i = 0; i < filterCount; i++) {
            try {
                String filterContext = context + "filter" + i + ".";
                String className = properties.getProperty(filterContext + "className");
                filters[i] = (MessageRendering) Class.forName(className).newInstance();

                // Load filter properties.
                String[] propNames = properties.getChildrenProperties(filterContext + "properties");
                Map filterProps = new HashMap();
                for (int j = 0; j < propNames.length; j++) {
                    // Get the bean property name, which is everything after
                    // the last '.' in the xml property name.
                    filterProps.put(propNames[j], properties.getProperty(filterContext + "properties." + propNames[j]));
                }
                // Set properties on the bean
                logger.debug("setProperties: i=" + i + " filters[i]=" + filters[i] );
                BeanUtils.setProperties(filters[i], filterProps);
            } catch (Exception e) {
            	logger.error("Error loading filter " + i + " for context " + context);
            }
        }
    }

    private void loadProperties() {
        if (properties != null)
            return;
        try {
            logger.debug("enter loadProperties, properties is null ");
            String xmlValue = this.setupDao.getSetupValue(PERSISTENCE_NAME);
            if ((xmlValue == null) || (xmlValue.equals(""))) {
                xmlValue = createProperties();
                logger.debug("save the value to database:");
                logger.debug(xmlValue);
                setupDao.saveSetupValue(PERSISTENCE_NAME, xmlValue);
            }
            Reader sr = new StringReader(xmlValue);
            logger.debug("create a XMLProperties");
            properties = new XMLProperties(sr);     
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    private String createProperties() {
        StringWriter writer = new StringWriter();
        try {
            logger.debug("enter createProperties ....");
            Document doc = new Document(new Element("jiveFilters"));
            Element filterClasses = new Element("filterClasses");
            doc.getRootElement().addContent(filterClasses);
            // Add in default list of available filter classes.
            for (int i = 0; i < renderingAvailableFilters.getAvailableFilterClassNames().length; i++) {
                Element newClass = new Element("filter" + i);
                newClass.setText(renderingAvailableFilters.getAvailableFilterClassNames()[i]);
                filterClasses.addContent(newClass);
            }
            
            // Set default global filters -- HTML and Newline
            Element defaultFilters = new Element("global");
            doc.getRootElement().addContent(defaultFilters);
            defaultFilters.addContent(new Element("filterCount").addContent(Integer.toString(DEFAULT_FILTER_CLASSES.length)));
            for (int i = 0; i < DEFAULT_FILTER_CLASSES.length; i++) {
                Element filter = new Element("filter" + i);
                filter.addContent(new Element("className").addContent(DEFAULT_FILTER_CLASSES[i]));
                defaultFilters.addContent(filter);
            }
            
            // Now, write out to the file.
            OutputStream out = null;
            try {
                // Use JDOM's XMLOutputter to do the writing and formatting.
                XMLOutputter outputter = new XMLOutputter();
                outputter.output(doc, writer);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        } catch (RuntimeException e) {
            logger.error(e);
        }
        return writer.toString();
    }

	public OutFilterManager getOutFilterManager() {
		return outFilterManager;
	}

	public void setOutFilterManager(OutFilterManager outFilterManager) {
		this.outFilterManager = outFilterManager;
	}
    
    

}
