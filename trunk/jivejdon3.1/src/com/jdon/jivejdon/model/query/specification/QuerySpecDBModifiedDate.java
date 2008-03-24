package com.jdon.jivejdon.model.query.specification;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.model.query.QueryCriteria;
import com.jdon.jivejdon.model.query.QuerySpecification;
import com.jdon.jivejdon.util.ToolsUtil;

public class QuerySpecDBModifiedDate implements QuerySpecification {
	 private final static Logger logger = Logger.getLogger(QuerySpecDBModifiedDate.class);
	   
	
	private QueryCriteria qc;
	private Collection params;
	private String whereSQL;

	public QuerySpecDBModifiedDate(QueryCriteria qc) {
		this.qc = qc;
		this.params = new ArrayList(5);
	}

	public void parse() {
		logger.debug("enter parse");
		StringBuffer where = new StringBuffer( " WHERE modifiedDate >= ? and modifiedDate <= ? ");
		
		String fromDate = ToolsUtil.dateToMillis(qc.getFromDate().getTime());
		logger.debug("fromDate="+ qc.getFromDate() + " sql formate=" + fromDate );
		String toDate = ToolsUtil.dateToMillis(qc.getToDate().getTime());
		logger.debug("toDate="+ qc.getToDate() + " sql formate=" + toDate );
		params.add(fromDate);
		params.add(toDate);
		
		if (qc.getForumId() != null){
			where.append(" and forumID = ? ");
			params.add(new Long(qc.getForumId()));
		}
		
		if (qc instanceof MultiCriteria){
			MultiCriteria mmqc = (MultiCriteria)qc;
			if (mmqc.getUserID() != null){
				where.append(" and userID = ?");
				params.add(mmqc.getUserID());    		
			}					
		}		
		this.whereSQL = where.toString();
	}

	public Collection getParams() {
		return params;
	}

	public String getWhereSQL() {
		return whereSQL;
	}
	
	public String getResultSortSQL(){
	    if (qc.getResultSort().isASCENDING())
	        return " ORDER BY modifiedDate ASC ";
	    else
	    	return " ORDER BY modifiedDate DESC ";
	}
	

}
