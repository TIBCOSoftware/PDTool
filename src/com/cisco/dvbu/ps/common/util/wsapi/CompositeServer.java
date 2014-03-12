/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
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
	

}
