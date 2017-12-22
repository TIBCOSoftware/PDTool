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
package com.tibco.ps.common.util.jdbcapi;
/**
 * Composite Software, Inc.
 * All Rights Reserved
 */

import java.sql.*;

class JdbcApp
{
    public static void main(String args[]) throws Exception 
    {
    	/*
 		 * arg[0]=<datasource_name> - Name of the Composite JDBC source with published resources (views or procedures)
 		 * arg[1]=<hostname> - The fully qualifed name of the machine where Composite is installed
 		 * arg[2]=<port> - The JDBC port for Composite (e.g. 9401)
 		 * arg[3]=<user> - The name of the user to connect Composite with
 		 * arg[4]=<password> - The password for the user
 		 * arg[5]=<domain_name> - The name of the Compsoite domain (e.g. composite)
 		 * arg[6]="<sql_statement>" - The sql statement in which to execute
 		 * arg[7]=[encrypt] - An option constant "encrypt" indicating whether to use the JDBC SSL port or not.  leave blank if not using.
 		 */
    	if (args != null && args.length > 0) {
        	if (args.length == 7) {
        		String result = invokeJdbc(args[0], args[1], args[2], args[3], args[4], args[5], args[6], null);
        		if (result != null) {
        			throw new Exception("invokeJdbc("+args[0]+", "+args[1]+", "+args[2]+", "+args[3]+", "+args[4]+", "+args[5]+", "+args[6]+") error response: " + result);
        		}      		
        	} else if (args.length == 8) {
            		String result = invokeJdbc(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            		if (result != null) {
            			throw new Exception("invokeJdbc("+args[0]+", "+args[1]+", "+args[2]+", "+args[3]+", "+args[4]+", "+args[5]+", "+args[6]+", "+args[7]+") error response: " + result);
            		}      		
        	} else {
        		String argList="";
        		int i=0;
        		for (String a : args) {
            		System.err.println(a);
            		argList += "arg["+i+"]="+a+"  ";
            		i++;
        		}
        		throw new Exception("Invalid usage: invalid parameters provided ("+ argList +")  Usage: <datasource_name> <hostname> <port> <csw_user> <csw_password> <domain_name> \"<sql_statement>\" [encrypt]");
        	}
    	} else {
    		throw new Exception("Invalid usage: no parameters provided.  Usage: <datasource_name> <hostname> <port> <user> <password> <domain_name> \"<sql_statement>\" [encrypt]");
       }
	}
        
    private static String invokeJdbc(
    		String datasource, String hostname, String port, String userName, String password, String domain, String sqlStatement, String encrypt) 
    		throws Exception {
        
        // port of Composite Server dbapi service
        int portnum = 0;
        try {
            portnum = Integer.parseInt(port);
        } catch (Exception e) {
            if (encrypt != null) {
            	if (encrypt.equalsIgnoreCase("true")){
            		// encrypt=true chosen, use default JDBC SSL port
            		portnum = 9403;
            	}
            } else {
                // use default JDBC port
                portnum = 9401; 
            }
        }

        String result = null;
        String resulttmp = "";
        String url = null;
        Connection conn = null;
        Statement stmt = null;  
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        try {
            Class.forName("cs.jdbc.driver.CompositeDriver");

            url = "jdbc:compositesw:dbapi@" + hostname + ":" + portnum + "?domain=" + domain + "&dataSource=" + datasource;
            if (encrypt != null) {
            	if (encrypt.equalsIgnoreCase("true")){
            		// encrypt=true chosen, use default JDBC SSL port
            		url += "&encrypt=true";
            	}
            }
            conn = DriverManager.getConnection(url, userName, password);
            stmt = conn.createStatement();
            boolean isNotUpdate = stmt.execute(sqlStatement);
            int rows = 0;

            // return type is a result set
            if (isNotUpdate == true) {
                rs = stmt.getResultSet();

                if (rs == null) {
                    //throw new SQLException("sql=`"+sqlStatement+"` did not generate a result set");
                }
                rsmd = rs.getMetaData();
          
                int columns = rsmd.getColumnCount();
                //System.out.println("column count = " + columns);

                rows = 1;
                int type = 0;
              
                while (rs.next()) {
                    //System.out.print("row = `" + rows + "`  ");
                    if (rows > 1) 
                    	result += "\n";
                    
                   for (int i=1; i <= columns; i++) {
                        type = rsmd.getColumnType(i);
                        if (i > 1) 
                        	result += ",  ";

                        switch (type) {
                            case Types.INTEGER:
                            	resulttmp = Integer.toString(rs.getInt(i));
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.SMALLINT:
                            	resulttmp = Integer.toString(rs.getShort(i));
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.TINYINT:
                            	resulttmp = Byte.toString(rs.getByte(i));
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.BIGINT:
                            	resulttmp = Long.toString(rs.getLong(i));
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.FLOAT:
                            	resulttmp = Float.toString(rs.getFloat(i));
                            	//System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.REAL:
                            	resulttmp = Float.toString(rs.getFloat(i));
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.DECIMAL:
                            	resulttmp = Float.toString(rs.getFloat(i));
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.DOUBLE:
                            	resulttmp = Double.toString(rs.getDouble(i));
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.NUMERIC:
                            	resulttmp = Float.toString(rs.getFloat(i));
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.CHAR:
                            	resulttmp = rs.getString(i);
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.VARCHAR:
                            	resulttmp = rs.getString(i);
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.LONGVARCHAR:
                            	resulttmp = rs.getString(i);
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.DATE:
                            	resulttmp = rs.getDate(i).toString();
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.TIME:
                            	resulttmp = rs.getTime(i).toString();
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.TIMESTAMP:
                            	resulttmp = rs.getTimestamp(i).toString();
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            case Types.BOOLEAN:
                            	resulttmp = Boolean.toString(rs.getBoolean(i));
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;

                            default:
                            	resulttmp = rs.getString(i);
                                //System.out.print(" col[" + i + "]=`" + result + "` ");
                                break;
                        }
                        if (result == null)
                        	result = resulttmp;
                        else
                        	result += resulttmp;
                    }
                    //System.out.println("\n");
                    rows++;
                }
                rs.close();
            } else {
              // return type is not a result set
              rows = stmt.getUpdateCount();
              result = "Return type is not a result set for sql=`"+sqlStatement+"` affected " + rows + " row(s)";
              //System.out.println(result);
            }
      
            stmt.close();
            conn.close();
        } catch (Exception e) {
        	result = e.getLocalizedMessage();
            e.printStackTrace();
            if (rs != null)   {
                try {
                    rs.close(); 
                } catch (SQLException ignore) { }
            }
            if (stmt != null) {
                try {
                    stmt.close(); 
                } catch (SQLException ignore) { }
            }
            if (conn != null) {
                try {
                    conn.close(); 
                } catch (SQLException ignore) { }
            }
            //throw e;
        } finally {
            rs = null;
            stmt = null;
            conn = null;
        }
        if(result==null || result.equals(""))
        {
        	//result = url;
        }
        return result;
    }
}
