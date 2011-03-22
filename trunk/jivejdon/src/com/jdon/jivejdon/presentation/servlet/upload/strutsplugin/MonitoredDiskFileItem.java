/* Licence:
*   Use this however/wherever you like, just don't blame me if it breaks anything.
*
* Credit:
*   If you're nice, you'll leave this bit:
*
*   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
*   email : plosson@users.sourceforge.net
*/
/*
*  Changed for Part 2, by Ken Cochrane
*  http://KenCochrane.net , http://CampRate.com , http://PopcornMonsters.com
*/
package com.jdon.jivejdon.presentation.servlet.upload.strutsplugin;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;

/**
 */
public class MonitoredDiskFileItem extends DiskFileItem
{
    Logger log = Logger.getLogger(this.getClass());
    private MonitoredOutputStream mos = null;
    private OutputStreamListener listener;

    public MonitoredDiskFileItem(String fieldName, String contentType, boolean isFormField, String fileName, int sizeThreshold, File repository, OutputStreamListener listener)
    {
        super(fieldName, contentType, isFormField, fileName, sizeThreshold, repository);
         log.debug("inside MonitoredDiskFileItem constructor ");
        this.listener = listener;
    }

    public OutputStream getOutputStream() throws IOException
    {
        log.debug("inside getOutputStream() ");
        if (mos == null)
        {
            mos = new MonitoredOutputStream(super.getOutputStream(), listener);
        }
        log.debug("leaving getOutputStream() ");
        return mos;
    }
}
