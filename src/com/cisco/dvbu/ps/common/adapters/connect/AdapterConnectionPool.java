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

package com.cisco.dvbu.ps.common.adapters.connect;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cisco.dvbu.ps.common.adapters.config.ConnectorConfig;

/**
 * @author vmadired, March 2015
 */

public class AdapterConnectionPool {
	private static Log log = LogFactory.getLog(AdapterConnectionPool.class);
	private GenericObjectPool pool = null;
	private ConnectorConfig connConfig = null;

	public AdapterConnectionPool(ConnectorConfig connConfig) {
		this.connConfig = connConfig;
	}
	public synchronized void init(Connector conn) {
		if (pool != null)
			return;
		pool = new GenericObjectPool(
				new AdapterConnectionPoolFactory(conn));
		pool.setTimeBetweenEvictionRunsMillis(1000 * 60 * 5); // 5 minutes
		pool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
		pool.setMaxActive((connConfig!=null) ? connConfig.getMaxClients() : 5);
		pool.setMinIdle((connConfig!=null) ? connConfig.getMinClients() : 3);
		pool.setMaxIdle((connConfig!=null) ? connConfig.getMinClients() : 3);
		pool.setMaxWait(1000 * 60 * 5); // 5 minutes
		pool.setSoftMinEvictableIdleTimeMillis(1000 * 60 * 60);
		pool.setNumTestsPerEvictionRun(2);
	}
	public synchronized void shutdown() throws Exception {
		if (pool != null)
			pool.close();
		pool = null;
	}
	
	public Connector borrowConnector() throws Exception {

		return (Connector) pool.borrowObject();
	}

	public void returnConnector(Connector conn) throws Exception {
		pool.returnObject(conn);
	}

	private class AdapterConnectionPoolFactory extends
			BasePoolableObjectFactory {
		Connector conn = null;

		AdapterConnectionPoolFactory(Connector conn) {
			this.conn = conn;
		}

		public Object makeObject() /* throws Exception */{
			Connector newConn = (Connector) conn.clone();
			
			newConn.init();

			return newConn;
		}

		public void destroyObject(Object object) {
			Connector oldConn = (Connector) object;
			oldConn.cleanup();
			object = null;
		}
	}
}
