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
package com.tibco.ps.common.util.wsapi;

public class CompositeServer {
	
	private String id;
	private String hostname;	
	private String port;
	private String user;
	private String password;
	private String domain;
	private String usage;
	private String cishome;
	private String clustername;
	private String site;
	private Boolean useHttps;
	private Boolean allowVariables;

	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return Integer.parseInt(port);
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the cishome
	 */
	public String getCishome() {
		return cishome;
	}
	/**
	 * @param cishome the cishome to set
	 */
	public void setCishome(String cishome) {
		this.cishome = cishome;
	}
	/**
	 * @return the clustername
	 */
	public String getClustername() {
		return clustername;
	}
	/**
	 * @param clustername the clustername to set
	 */
	public void setClustername(String clustername) {
		this.clustername = clustername;
	}
	/**
	 * @return the site
	 */
	public String getSite() {
		return site;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}
	public boolean isUseHttps() {
		return useHttps;
	}
	public void setUseHttps(boolean useHttps) {
		this.useHttps = useHttps;
	}
	
	/** 
	 * @param allowVariables allows the use of variables in parameters
	 */
	public boolean isAllowVariables() {
		return allowVariables;
	}
	public void setAllowVariables(boolean allowVariables) {
		this.allowVariables = allowVariables;
	}


}
