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
