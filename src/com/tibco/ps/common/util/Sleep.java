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
package com.tibco.ps.common.util;

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
