/**
 * (c) 2015 Cisco and/or its affiliates. All rights reserved.
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

package com.cisco.dvbu.ps.common.adapters.protocol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.adapters.common.AdapterConstants;
import com.cisco.dvbu.ps.common.adapters.common.AdapterException;
import com.cisco.dvbu.ps.common.adapters.config.AdapterConfig;

/**
 * @author vmadired, March 2015
 */

public class AdapterProxy {
	private static Log log	 									= LogFactory.getLog(AdapterProxy.class);
	private boolean need_proxy;
	private boolean auth_proxy;
	private String proxyHost;
	private int proxyPort;
	private String proxyUser;
	private String proxyPswd;
	
	public AdapterProxy(AdapterConfig props) throws AdapterException {
		String str = props.getProperty(AdapterConstants.ADAPTER_USE_PROXY);
		if (str!=null && "true".equalsIgnoreCase(str)) {
			need_proxy = true;
			if ((proxyHost = props.getProperty(AdapterConstants.ADAPTER_PROXY_HOST))==null || proxyHost.isEmpty()) {
				log.error("1201: Proxy host not configured!");
				throw new AdapterException(1201, "Proxy host not configured!", null);
			}
			str = props.getProperty(AdapterConstants.ADAPTER_PROXY_PORT);
			if (str==null || Integer.parseInt(str)<1) {
				log.error("1202: Proxy port missing or invalid!");
				throw new AdapterException(1202, "Proxy port missing or invalid!", null);
				
			}
			proxyPort = Integer.parseInt(str);
			str = props.getProperty(AdapterConstants.ADAPTER_PROXY_USER);
			if (str!=null && !str.isEmpty()) {
				proxyUser = str;
				auth_proxy = true;
				if ((str = props.getProperty(AdapterConstants.ADAPTER_PROXY_PSWD))==null || str.isEmpty()) {
					log.error("1203: Proxy user password missing!");
					throw new AdapterException(1203, "Proxy user password missing!", null);
					
				}
			} else {
				auth_proxy = false;
			}
		} else {
			need_proxy = false;
		}
	}
	
	private AdapterProxy() {}
	
	public boolean useProxy() {
		return need_proxy;
	}
	
	public boolean useCredentials() {
		return auth_proxy;
	}
	
	public String getHost() {
		return proxyHost;
	}
	
	public int getPort() {
		return proxyPort;
	}
	
	public String getUser() {
		return proxyUser;
	}
	
	public String getPassword() {
		return proxyPswd;
	}
}
