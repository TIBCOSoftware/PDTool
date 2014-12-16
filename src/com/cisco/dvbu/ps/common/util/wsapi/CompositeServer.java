/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
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
package com.cisco.dvbu.ps.common.util.wsapi;

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
