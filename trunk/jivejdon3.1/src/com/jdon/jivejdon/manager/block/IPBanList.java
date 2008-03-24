/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.manager.block;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.dao.SetupDao;
import com.jdon.jivejdon.util.StringSortComparator;
import com.jdon.util.task.TaskEngine;


/**
 * From Apache Roller
 * 
 * Represents a list of banned ip addresses.
 *
 * This base implementation gets its list from a file on the filesystem.  We
 * are also aware of when the file changes via some outside source and we will
 * automatically re-read the file and update the list when that happens.
 * 
 *
 */
public class IPBanList implements Runnable{
	private final static Logger log = Logger.getLogger(IPBanList.class);
	
	private  String PERSISTENCE_NAME = "IPBANLIST";
	
	  // set of ips that are banned, use a set to ensure uniqueness
    private Set bannedIps = null;
    
    private boolean myLastModified = false;
    
    // file listing the ips that are banned
    private SetupDao setupDao;
    
    public IPBanList(SetupDao setupDao) {
		this.setupDao = setupDao;
		log.debug("INIT");
		this.loadBannedIps();
	}
      
    
    public boolean isBanned(String ip) {
        
        // update the banned ips list if needed
        this.loadBannedIpsIfNeeded(false);
        
        if(ip != null) {
            return this.bannedIps.contains(ip);
        } else {
            return false;
        }
    }
    
    
    public void addBannedIp(String ip) {        
        if(ip == null) {
            return;
        }
        
        // update the banned ips list if needed
        this.loadBannedIpsIfNeeded(false);
        
        if(!this.bannedIps.contains(ip)) {
            try {
            	   // add to Set
                this.bannedIps.add(ip);
                
                TaskEngine.addTask(this);
                log.debug("ADDED "+ip);
            } catch(Exception e) {
                log.error("Error adding banned ip ", e);
            }
        }
    }
    
    public void deleteBannedIp(String ip) {        
        if(ip == null) {
            return;
        }        
        // update the banned ips list if needed
        this.loadBannedIpsIfNeeded(false);        
        if(this.bannedIps.contains(ip)) {
            try {
            	   // remove
                this.bannedIps.remove(ip);
                
                TaskEngine.addTask(this);
                log.debug("DEL "+ip);
            } catch(Exception e) {
                log.error("Error delete banned ip ", e);
            }
        }
    }
    
    public void run() {	
    	saveBanIpList();
		log.info("save ip finished!");
	}
    
    public void saveBanIpList(){
    	try {
			StringBuffer sb = new StringBuffer();
			Iterator iter = bannedIps.iterator();
			while(iter.hasNext()){
				String ip = (String)iter.next();
				sb.append(ip).append("\n");
			}
		    setupDao.saveSetupValue(PERSISTENCE_NAME, sb.toString());   
		    myLastModified = true;
		} finally {		
		}
    }
    
    
    /**
     * Check if the banned ips file has changed and needs to be reloaded.
     */
    private void loadBannedIpsIfNeeded(boolean forceLoad) {
        
        if(myLastModified || forceLoad){
            // need to reload
            this.loadBannedIps();
            myLastModified = false;
        }
    }
    
    
    /**
     * Load the list of banned ips from a file.  This clears the old list and
     * loads exactly what is in the file.
     */
    private synchronized void loadBannedIps() {

		try {
			Set newBannedIpList = new TreeSet(new StringSortComparator());

			String ipListText = setupDao.getSetupValue(PERSISTENCE_NAME);
			BufferedReader in = new BufferedReader(new StringReader(ipListText));

			String ip = null;
			while ((ip = in.readLine()) != null) {
				newBannedIpList.add(ip);
				log.debug("READED " + ip);
			}

			in.close();

			// list updated, reset modified file
			this.bannedIps = newBannedIpList;

			log.info(this.bannedIps.size() + " banned ips loaded");
		} catch (Exception ex) {
			log.error("Error loading banned ips from file", ex);
		}

	}


	public Set getBannedIps() {
		return bannedIps;
	}
    
  
    

}
