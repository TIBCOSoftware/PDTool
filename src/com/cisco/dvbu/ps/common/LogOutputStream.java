/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 * 
 * This software is released under the Eclipse Public License. The details can be found in the file LICENSE. 
 * Any dependent libraries supplied by third parties are provided under their own open source licenses as 
 * described in their own LICENSE files, generally named .LICENSE.txt. The libraries supplied by Cisco as 
 * part of the Composite Information Server/Cisco Data Virtualization Server, particularly csadmin-XXXX.jar, 
 * csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar, csext-XXXX.jar, csjdbc-XXXX.jar, 
 * csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar, and customproc-XXXX.jar (where -XXXX is an 
 * optional version number) are provided as a convenience, but are covered under the licensing for the 
 * Composite Information Server/Cisco Data Virtualization Server. They cannot be used in any way except 
 * through a valid license for that product.
 * 
 * This software is released AS-IS!. Support for this software is not covered by standard maintenance agreements with Cisco. 
 * Any support for this software by Cisco would be covered by paid consulting agreements, and would be billable work.
 * 
 */
package com.cisco.dvbu.ps.common;

/**
 * author: mideange
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.*;

import com.cisco.dvbu.ps.common.util.CommonUtils;

public class LogOutputStream extends ByteArrayOutputStream {
    private Logger logger;
    private Level level;

    public LogOutputStream(Logger logger, Level level) {
        super();
        this.logger = logger;
        this.level = level;
    }

    public void write(byte[] b, int off, int len)
    {
    	// Suppress the extra line that gets printed out with System.out.println()
    	//   The logger catches the print and injects a carriage return linefeed anyway.
    	if (len == 2) {
    		// If not carriage return/line feed go ahead and print it out.
    		if (b[0] != 13 && b[1] != 10)
    	        super.write(b, off, len);
    	} else {
    		super.write(b, off, len);
    	}
        try {
            flush();
        }
        catch (IOException ioe) {
        }
    }

    public void flush()
        throws IOException
    {
        if (size() > 0) {
        	// Mask any passwords in the string
    		String buf = CommonUtils.maskCommand(toString());
        	if (level.equals(Level.ERROR)) 
        		logger.log(level, CommonConstants.applicationErrorPrependMessage+buf);
        	else {
        		logger.log(level, buf);
        	}
        	reset();    
        }
    }

    public void close()
        throws IOException
    {
        try {
            flush();
        }
        finally {
            super.close();
        }
    }
}
