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

package com.tibco.ps.common.adapters.common;

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
