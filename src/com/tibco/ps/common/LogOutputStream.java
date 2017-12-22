/**
 * (c) 2017 TIBCO Software Inc. All rights reserved.
 * 
 * Except as specified below, this software is licensed pursuant to the Eclipse Public License v. 1.0.
 * The details can be found in the file LICENSE.
 * 
 * The following proprietary files are included as a convenience, and may not be used except pursuant
 * to valid license to Composite Information Server or TIBCO(R) Data Virtualization Server:
 * csadmin-XXXX.jar, csarchive-XXXX.jar, csbase-XXXX.jar, csclient-XXXX.jar, cscommon-XXXX.jar,
 * csext-XXXX.jar, csjdbc-XXXX.jar, csserverutil-XXXX.jar, csserver-XXXX.jar, cswebapi-XXXX.jar,
 * and customproc-XXXX.jar (where -XXXX is an optional version number).  Any included third party files
 * are licensed under the terms contained in their own accompanying LICENSE files, generally named .LICENSE.txt.
 * 
 * This software is licensed AS-IS. Support for this software is not covered by standard maintenance agreements with TIBCO.
 * If you would like to obtain assistance with this software, such assistance may be obtained through a separate paid consulting
 * agreement with TIBCO.
 * 
 */
package com.tibco.ps.common;

/**
 * author: mideange
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.*;

import com.tibco.ps.common.util.CommonUtils;

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
