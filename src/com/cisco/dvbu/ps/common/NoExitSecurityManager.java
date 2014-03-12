/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common;

/**
 * author: mideange
 */

import java.security.Permission;

public class NoExitSecurityManager extends SecurityManager {

    public static RuntimePermission securityManagerPermission = new RuntimePermission("setSecurityManager");

    public void checkPermission(Permission perm) {
        if (perm.equals(securityManagerPermission)) {
            return;
        }
        super.checkPermission(perm);
    }

    public void checkExit(int status) {
    	if (status != 0)
    		throw new NoExitSecurityExceptionStatusNonZero();
    	else 
    		throw new NoExitSecurityExceptionStatusZero();
    	 
    }
}
