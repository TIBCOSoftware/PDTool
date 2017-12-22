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

package com.tibco.ps.common.adapters.connect;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tibco.ps.common.adapters.config.ConnectorConfig;

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
