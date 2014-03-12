/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common;

/**
 * author: mideange
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.*;

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
            logger.log(level, toString());
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
