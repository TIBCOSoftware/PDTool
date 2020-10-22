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
package com.tibco.ps.common.util;

import java.util.Properties;
import java.util.regex.Pattern;

import com.tibco.ps.utils.repository.CisPathQuoterException;
import com.tibco.ps.utils.repository.RepoUtilsPropertiesFactory;

public class CisPathQuoter {

    // determines if the input word is a CIS reserved word. Quoted strings are not considered reserved.
    //
    public static boolean isReservedWord (String inStr) throws CisPathQuoterException {
        Properties p;
        String rwRegex;
        boolean specialFound = false;
        
        inStr = inStr.trim();
        
        if (inStr.startsWith("\"") && inStr.endsWith("\"")) {
            return false;
        }
        
        try {
            p = RepoUtilsPropertiesFactory.getProperties();
        } catch (Exception e) {
            throw new CisPathQuoterException (e);
        }
        
        if (p.getProperty ("cis.reserved_words_re") == null)
            throw new CisPathQuoterException ("Unable to locate property \"cis.path_quoting_rules\".");
        
        rwRegex = "(?i)^(?:" + p.getProperty ("cis.reserved_words_re") + ")$";

        // check against regular expressions in the reserved word list (includes leading numeric/underscore and special character searches too.)
        //
        if (Pattern.compile (rwRegex).matcher (inStr).find()) {
            specialFound = true;
        }

        return specialFound;      
    }

    // uses CIS quoting rules to determine if the input word would need to be quoted to be used in a path
    //
    public static boolean isQuotableWord (String inStr) throws CisPathQuoterException {
        Properties p;
        String[] regexList;
        boolean specialFound = false;
        
        inStr = inStr.trim();

        if (inStr.startsWith("\"") && inStr.endsWith("\"")) {
            return false;
        }
        
        try {
            p = RepoUtilsPropertiesFactory.getProperties();
        } catch (Exception e) {
            throw new CisPathQuoterException (e);
        }
        
        if (p.getProperty ("cis.path_quoting_rules") == null)
            throw new CisPathQuoterException ("Unable to locate property \"cis.path_quoting_rules\".");
        
        regexList = p.getProperty ("cis.path_quoting_rules").split(",", 0);

        // check against regular expressions in the reserved word list (includes leading numeric/underscore and special character searches too.)
        //
        regexLoop:
        for (int r = 0; r < regexList.length; r++) {
            if (Pattern.compile (regexList[r]).matcher (inStr).find()) {
                specialFound = true;
                break regexLoop;
            }
        }

        return specialFound;      
    }

    public static String quoteWord (String inStr) throws CisPathQuoterException {
        String result;
        
        if (isQuotableWord (inStr))
            result = "\"" + inStr + "\"";
        else
            result = inStr;
        
        return result;
    }
    
    public static String quotePath (String inStr) throws CisPathQuoterException {
        String result = "";
        String[] pathList;
        String tmpName;
        
        pathList = inStr.replace ("\"", "").split ("/", 0);

        pathLoop:
        for (int p = 0; p < pathList.length; p++) {
            tmpName = pathList[p];
            
            if (p > 0 && tmpName.equals (""))
                continue pathLoop;
            
            tmpName = CisPathQuoter.quoteWord (tmpName);
            
            if (p > 0)
                result += "/";
            
            result += tmpName;
        }

        return result;
    }
}
