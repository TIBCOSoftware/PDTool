/*******************************************************************************
* Copyright (c) 2014 Cisco Systems
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* PDTool project commiters - initial release
*******************************************************************************/
/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
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
