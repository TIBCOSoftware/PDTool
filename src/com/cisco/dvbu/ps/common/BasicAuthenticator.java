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
package com.cisco.dvbu.ps.common;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import com.cisco.dvbu.ps.common.util.wsapi.CompositeServer;

public class BasicAuthenticator extends Authenticator {
	
	private CompositeServer server;

	public BasicAuthenticator(CompositeServer server) {
		this.server = server;
	}	
	
    public PasswordAuthentication getPasswordAuthentication() { 
        return (new PasswordAuthentication(server.getUser()+'@'+server.getDomain(), server.getPassword().toCharArray())); 
    } 
}
