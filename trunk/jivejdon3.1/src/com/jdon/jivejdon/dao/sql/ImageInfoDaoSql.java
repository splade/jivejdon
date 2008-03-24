package com.jdon.jivejdon.dao.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.dao.ImageInfoDao;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ImageInfo;
import com.jdon.jivejdon.service.util.ContainerUtil;
import com.jdon.model.query.PageIteratorSolver;
/**
 * 
 * @author <a href="mailto:xinying_ge@yahoo.com.cn">GeXinying</a>
 *
 */
public class ImageInfoDaoSql implements ImageInfoDao {

	

	private final static Logger logger = Logger
			.getLogger(ImageInfoDaoSql.class);

	private PageIteratorSolver pageIteratorSolver;	
	
	public ImageInfo getImage(String key) {
		logger.debug("enter getForum for id:" + key);
        String LOAD_FORUM =
            "SELECT objectId, name, description FROM upload WHERE objectId=?";
        List queryParams = new ArrayList();
        queryParams.add(key);         
        
        ImageInfo ret = new ImageInfo();
        try {
            List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams,
                    LOAD_FORUM);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                Map map = (Map) iter.next();
                ret.setImageId((String) map.get("objectId"));
                ret.setName((String) map.get("name"));                
                ret.setDescription((String) map.get("description"));
                //<img src="/jivejdon/imageShow.jsp?type=images/jpeg&id=106" border='0' >
                String bodyStr= "<img src=\"/jivejdon/imageShow.jsp?type=images/jpeg&id=#IMAGEID#\" border='0' >";
                bodyStr = bodyStr.replaceAll("#IMAGEID#", (String) map.get("objectId"));
                ret.setBody(bodyStr);
                
            }           
        } catch (Exception se) {
            logger.error(se);
        }
        return ret;
	}

	public void deleteImage(String key) {
		String DELETE_IMAGE =
            "DELETE FROM upload WHERE objectId=?";
        List queryParams = new ArrayList();
        queryParams.add(key);
        try {
            jdbcTempSource.getJdbcTemp().operate(queryParams, DELETE_IMAGE);    
            clearCache();
        } catch (Exception e) {
            logger.error("imageId="+ key + " happend " + e);
        }
	}
	private JdbcTempSource jdbcTempSource;

	private PropertyDaoSql propertyDaoSql;

	private Constants constants;

	public ImageInfoDaoSql(JdbcTempSource jdbcTempSource,
			ContainerUtil containerUtil, Constants constants) {
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource
				.getDataSource(), containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
		this.propertyDaoSql = new PropertyDaoSql(jdbcTempSource);
		this.constants = constants;
	}

	public PageIterator getImages(int start, int count) {
		logger.debug("enter getImages ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) from upload ";

		String GET_ALL_ITEMS = "select objectId from upload ";

		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT,
				GET_ALL_ITEMS, "", start, count);
	}
	public void clearCache() {
        pageIteratorSolver.clearCache();
    }
}
