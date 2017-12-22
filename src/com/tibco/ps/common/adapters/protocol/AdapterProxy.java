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

package com.tibco.ps.common.adapters.protocol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tibco.ps.common.adapters.common.AdapterConstants;
import com.tibco.ps.common.adapters.common.AdapterException;
import com.tibco.ps.common.adapters.config.AdapterConfig;

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
