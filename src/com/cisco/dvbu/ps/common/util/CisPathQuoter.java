/*******************************************************************************
* Copyright (c) 2014 Cisco Systems
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* PDTool project commiters - initial release
*******************************************************************************/
/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util;

import java.util.Properties;
import java.util.regex.Pattern;

import com.compositesw.ps.utils.repository.CisPathQuoterException;
import com.compositesw.ps.utils.repository.RepoUtilsPropertiesFactory;

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
