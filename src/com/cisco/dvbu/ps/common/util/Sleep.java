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
package com.cisco.dvbu.ps.common.util;

public class Sleep {
	/**
	* @param args
	*/
	public static void main(String[] args) {
		sleep(true, 5000);
	}
	
	public static void sleep(boolean debug, int sleepMillis) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		try {
			Thread.sleep(sleepMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (debug)
			System.out.println("Sleep for "+elapsedTime(start, System.currentTimeMillis()));
	}
	
	public static String elapsedTime(long start, long end) {
		String auxRet = "";
		long aux = end - start;
		long days = 0, hours = 0, minutes = 0, seconds = 0, milliseconds = 0;
		// days
		if (aux > 24 * 60 * 60 * 1000) {
		days = aux / (24 * 60 * 60 * 1000);
		}
		aux = aux % (24 * 60 * 60 * 1000);
		// hours
		if (aux > 60 * 60 * 1000) {
		hours = aux / (60 * 60 * 1000);
		}
		aux = aux % (60 * 60 * 1000);
		// minutes
		if (aux > 60 * 1000) {
		minutes = aux / (60 * 1000);
		}
		aux = aux % (60 * 1000);
		// seconds
		if (aux > 1000) {
		seconds = aux / (1000);
		}
		milliseconds = aux % 1000;
		if (days > 0) {
		auxRet = days + " days ";
		}
		if (days != 0 || hours > 0) {
		auxRet += hours + " hours ";
		}
		if (days != 0 || hours != 0 || minutes > 0) {
		auxRet += minutes + " minutes ";
		}
		if (days != 0 || hours != 0 || minutes != 0 || seconds > 0) {
		auxRet += seconds + " seconds ";
		}
		auxRet += milliseconds + " milliseconds ";
		return auxRet;
	}

}
