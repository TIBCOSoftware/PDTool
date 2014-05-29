/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.ps.common.util;

public class Base64EncodeDecode {

	// The line separator from the OS.
	private static final String lineSep = System.getProperty("line.separator");

	// Map 6-bits to Base64 characters.
	private static char[]    map1 = new char[64];
	   static {
	      int i=0;
	      for (char c='A'; c<='Z'; c++) map1[i++] = c;
	      for (char c='a'; c<='z'; c++) map1[i++] = c;
	      for (char c='0'; c<='9'; c++) map1[i++] = c;
	      map1[i++] = '+'; map1[i++] = '/'; }

	// Map Base64 characters to 6-bits.
	private static byte[]    map2 = new byte[128];
	   static {
	      for (int i=0; i<map2.length; i++) map2[i] = -1;
	      for (int i=0; i<64; i++) map2[map1[i]] = (byte)i; }

	/**
	* Encodes a string into Base64.
	* No blanks or line breaks are inserted.
	* @param s  A String to be encoded.
	* @return   A String containing the Base64 encoded data.
	*/
	public static String encodeString (String s) {
	   return new String(encode(s.getBytes())); }

	/**
	* Encodes a byte array into Base 64 and breaks the output into lines of 76 characters.
	* This method is compatible with sun.misc.BASE64Encoder.encodeBuffer(byte[]).
	* @param in  An array containing the data bytes to be encoded.
	* @return    A String containing the Base64 encoded data, broken into lines.
	*/
	public static String encodeLines (byte[] in) {
	   return encodeLines(in, 0, in.length, 76, lineSep); }

	/**
	* Encodes a byte array into Base 64 format and breaks the output into lines.
	* @param in           	An array containing the data bytes to be encoded.
	* @param offset       	Offset of the first byte in "in" to be processed.
	* @param len          	Number of bytes to be processed in "in", starting at "offset".
	* @param lineLen       	Line length for the output data. Should be a multiple of 4.
	* @param lineSeparator 	The line separator to be used to separate the output lines.
	* @return              	A String containing the Base64 encoded data, broken into lines.
	*/
	public static String encodeLines (byte[] in, int offset, int len, int lineLen, String lineSeparator) {
	   int blockLen = (lineLen*3) / 4;
	   if (blockLen <= 0) throw new IllegalArgumentException();
	   int lines = (len+blockLen-1) / blockLen;
	   int bufLen = ((len+2)/3)*4 + lines*lineSeparator.length();
	   StringBuilder buf = new StringBuilder(bufLen);
	   int curr = 0;
	   while (curr < len) {
	      int l = Math.min(len-curr, blockLen);
	      buf.append (encode(in, offset+curr, l));
	      buf.append (lineSeparator);
	      curr += l; }
	   return buf.toString(); }

	/**
	* Encodes a byte array into Base64.
	* No blanks or line breaks are inserted in the output.
	* @param in  An array containing the data bytes to be encoded.
	* @return    A character array containing the Base64 encoded data.
	*/
	public static char[] encode (byte[] in) {
	   return encode(in, 0, in.length); }

	/**
	* Encodes a byte array into Base64.
	* No blanks or line breaks are inserted in the output.
	* @param in    An array containing the data bytes to be encoded.
	* @param len  Number of bytes to process in "in".
	* @return      A character array containing the Base64 encoded data.
	*/
	public static char[] encode (byte[] in, int len) {
	   return encode(in, 0, len); }

	/**
	* Encodes a byte array into Base64.
	* No blanks or line breaks are inserted in the output.
	* @param in    	An array containing the data bytes to be encoded.
	* @param offset	Offset of the first byte in "in" to be processed.
	* @param len  	Number of bytes to process in "in", starting at "offset".
	* @return      	A character array containing the Base64 encoded data.
	*/
	public static char[] encode (byte[] in, int offset, int len) {
	   int outDataLen = (len*4+2)/3;       // output length without padding
	   int outLen = ((len+2)/3)*4;         // output length including padding
	   char[] out = new char[outLen];
	   int curr = offset;
	   int end = offset + len;
	   int op = 0;
	   while (curr < end) {
	      int i0 = in[curr++] & 0xff;
	      int i1 = curr < end ? in[curr++] & 0xff : 0;
	      int i2 = curr < end ? in[curr++] & 0xff : 0;
	      int o0 = i0 >>> 2;
	      int o1 = ((i0 &   3) << 4) | (i1 >>> 4);
	      int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
	      int o3 = i2 & 0x3F;
	      out[op++] = map1[o0];
	      out[op++] = map1[o1];
	      out[op] = op < outDataLen ? map1[o2] : '='; op++;
	      out[op] = op < outDataLen ? map1[o3] : '='; op++; }
	   return out; }

	/**
	* Decodes a string from Base64.
	* No blanks or line breaks are allowed within the Base64 encoded input data.
	* @param s  A Base64 String to be decoded.
	* @return   A String containing the decoded data.
	* @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
	*/
	public static String decodeString (String s) {
	   return new String(decode(s)); }

	/**
	* Decodes a byte array from Base64 and ignores line separators, tabs and blanks.
	* CR, LF, Tab and Space characters are ignored in the input data.
	* This method is compatible with "sun.misc.BASE64Decoder.decodeBuffer(String)".
	* @param s  A Base64 String to be decoded.
	* @return   An array containing the decoded data bytes.
	* @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
	*/
	public static byte[] decodeLines (String s) {
	   char[] buf = new char[s.length()];
	   int p = 0;
	   for (int curr = 0; curr < s.length(); curr++) {
	      char c = s.charAt(curr);
	      if (c != ' ' && c != '\r' && c != '\n' && c != '\t')
	         buf[p++] = c; }
	   return decode(buf, 0, p); }

	/**
	* Decodes a byte array from Base64.
	* No blanks or line breaks are allowed within the Base64 encoded input data.
	* @param s  A Base64 String to be decoded.
	* @return   An array containing the decoded data bytes.
	* @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
	*/
	public static byte[] decode (String s) {
	   return decode(s.toCharArray()); }

	/**
	* Decodes a byte array from Base64.
	* No blanks or line breaks are allowed within the Base64 encoded input data.
	* @param in  A character array containing the Base64 encoded data.
	* @return    An array containing the decoded data bytes.
	* @throws    IllegalArgumentException If the input is not valid Base64 encoded data.
	*/
	public static byte[] decode (char[] in) {
	   return decode(in, 0, in.length); }

	/**
	* Decodes a byte array from Base64.
	* No blanks or line breaks are allowed within the Base64 encoded input data.
	* @param in    	A character array containing the Base64 encoded data.
	* @param offset Offset of the first character in "in" to be processed.
	* @param len  	Number of characters to process in "in", starting at "offset".
	* @return      An array containing the decoded data bytes.
	* @throws      IllegalArgumentException If the input is not valid Base64 encoded data.
	*/
	public static byte[] decode (char[] in, int offset, int len) {
	   if (len%4 != 0) throw new IllegalArgumentException ("Length of Base64 encoded input string is not a multiple of 4.");
	   while (len > 0 && in[offset+len-1] == '=') len--;
	   int outLen = (len*3) / 4;
	   byte[] out = new byte[outLen];
	   int curr = offset;
	   int end = offset + len;
	   int op = 0;
	   while (curr < end) {
	      int i0 = in[curr++];
	      int i1 = in[curr++];
	      int i2 = curr < end ? in[curr++] : 'A';
	      int i3 = curr < end ? in[curr++] : 'A';
	      if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
	         throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
	      int b0 = map2[i0];
	      int b1 = map2[i1];
	      int b2 = map2[i2];
	      int b3 = map2[i3];
	      if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
	         throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
	      int o0 = ( b0       <<2) | (b1>>>4);
	      int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
	      int o2 = ((b2 &   3)<<6) |  b3;
	      out[op++] = (byte)o0;
	      if (op<outLen) out[op++] = (byte)o1;
	      if (op<outLen) out[op++] = (byte)o2; }
	   return out; }

}
