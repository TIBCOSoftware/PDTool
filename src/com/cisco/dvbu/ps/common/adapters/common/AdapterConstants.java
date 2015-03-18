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

package com.cisco.dvbu.ps.common.adapters.common;

/**
 * @author vmadired, March 2015
 */

public class AdapterConstants {
	/* All Adapter configuration constants */
	public static final String ADAPTER_HOST				= "HOST";
	public static final String ADAPTER_PORT				= "PORT";
	public static final String ADAPTER_USER				= "USER";
	public static final String ADAPTER_PSWD				= "PASSWORD";
	public static final String ADAPTER_DOMAIN			= "DOMAIN";
	public static final String ADAPTER_USE_HTTPS		= "USEHTTPS";
	public static final String ADAPTER_USE_PROXY		= "USEPROXY";
	public static final String ADAPTER_PROXY_HOST		= "PROXYHOST";
	public static final String ADAPTER_PROXY_PORT		= "PROXYPORT";
	public static final String ADAPTER_PROXY_USER		= "PROXYUSER";
	public static final String ADAPTER_PROXY_PSWD		= "PROXYPASSWORD";
	
	/* All connector configuration constants */
	public static final String CONNECTOR_TYPE_SOAPHTTP	= "soaphttp";
	public static final String CONNECTOR_SH_ENDPOINT	= "endpoint";
	public static final String CONNECTOR_SH_SOAPACTION	= "soapaction";
	public static final String CONNECTOR_MAX_CLIENTS	= "maxclients";
	public static final String CONNECTOR_MIN_CLIENTS	= "minclients";
	public static final String CONNECTOR_RETRY_ATTMPTS	= "retryattempts";
	public static final String CONNECTOR_CALLBACK		= "callback";
	public static final String CONNECTOR_SIMPLE_ELEM	= "simpleelement";
	public static final String CONNECTOR_SE_NAME		= "name";
	public static final String CONNECTOR_TYPE_UNKNOWN	= "unknown";
	public static final String CONNECTOR_EP_SEPARATOR	= "$$$";

	/* All XPaths constants */
	public static final String XPATH_ROOT				= "/adapterconfiguration";
	public static final String XPATH_CONFIG_VERSION		= "/adapterconfiguration/@version";
	public static final String XPATH_NAME				= "/adapterconfiguration/name";
	public static final String XPATH_DESC				= "/adapterconfiguration/description";
	public static final String XPATH_CIS_VERSION		= "/adapterconfiguration/cisversion";
	public static final String XPATH_PROPERTIES			= "/adapterconfiguration/properties";
	public static final String XPATH_CALLBACKS			= "/adapterconfiguration/callbacks";
	public static final String XPATH_CONN_RETRYATTMPTS	= "/adapterconfiguration/retryattempts";
	public static final String XPATH_CONN_MAXCLIENTS	= "/adapterconfiguration/maxclients";
	public static final String XPATH_CONN_MINCLIENTS	= "/adapterconfiguration/minclients";
	public static final String XPATH_CONN_ENDPOINTS		= "/adapterconfiguration/endpoints";
	public static final String XPATH_CONN_ENDPOINT		= "./endpoint";
	public static final String XPATH_CONN_CB_SOAPACTION	= "./soapaction";
	public static final String XPATH_CONN_CB_RQ_BODY	= "./request/body";
	public static final String XPATH_CONN_CB_RS_BODY	= "./response/body";

	/* All Message constants */
	public static final String ADAPTER_EM_WSFAULT		= "Service error %s (%s)";
	public static final String ADAPTER_EM_CONNECTION	= "Failed to connect to %s";
}
