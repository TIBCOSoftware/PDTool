package com.cisco.dvbu.ps.common.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;

import com.cisco.dvbu.ps.common.exception.CompositeException;

public class ReplaceText 
{
	static String getName = "ReplaceText";

	
	public static void replaceAllText(String fromFilePath, String toFilePath, String searchText, String replacementText) throws CompositeException
	{
		boolean append = false;
		
		if (fromFilePath == null || fromFilePath.length() == 0) 
			throw new CompositeException("The parameter \"fromFilePath\" may not be null or empty.");
		if (searchText == null || searchText.length() == 0) 
			throw new CompositeException("The parameter \"searchText\" may not be null or empty.");
		if (replacementText == null) 
			throw new CompositeException("The parameter \"replacementText\" may not be null.");
		if (toFilePath == null || toFilePath.length() == 0)
			toFilePath = fromFilePath;
		
		StringBuilder fileContent = getFileContentsAscii(fromFilePath, searchText, replacementText, 0);

		createFileAscii(toFilePath, append, fileContent.toString());
	}
	
	public static void replaceOccurrenceText(String fromFilePath, String toFilePath, String searchText, String replacementText, String occurrenceStr) throws CompositeException
	{
		boolean append = false;
		int occurrence = Integer.valueOf(occurrenceStr);
		
		if (fromFilePath == null || fromFilePath.length() == 0) 
			throw new CompositeException("The parameter \"fromFilePath\" may not be null or empty.");
		if (searchText == null || searchText.length() == 0) 
			throw new CompositeException("The parameter \"searchText\" may not be null or empty.");
		if (replacementText == null) 
			throw new CompositeException("The parameter \"replacementText\" may not be null.");
		if (toFilePath == null || toFilePath.length() == 0)
			toFilePath = fromFilePath;
		
		StringBuilder fileContent = getFileContentsAscii(fromFilePath, searchText, replacementText, occurrence);

		createFileAscii(toFilePath, append, fileContent.toString());
	}

	private static StringBuilder getFileContentsAscii(String filePath, String searchText, String replacementText, int occurrence) throws CompositeException {
		
		StringBuilder fileContent = new StringBuilder();
		
		fileContent.setLength(0);
    	try
    	{
    		FileReader fr = new FileReader(filePath);
    		BufferedReader br = new BufferedReader(fr);
    		String s;
    		
    		int counter = 0;
    		while((s = br.readLine()) != null) {
    			if (s.contains(searchText)) {
    				// Perform this operation if all occurrences (occurrences=0) should be replaced or a specific occurrence (occurrence=i) should be replaced.
					if (occurrence == 0 || counter < occurrence) {
						//counter = counter + numOccurrences(s, searchText, counter, occurrence);
						int fromIndex = 0;
						while (s.indexOf(searchText, fromIndex) > 0) {
							fromIndex = s.indexOf(searchText, fromIndex);
							counter++;
							// Do the replacement if occurrence matches counter of occurrence within the string line
							if (occurrence == 0 || occurrence == counter) {
									// Extract the string parts for the found search text.  This is required since the replace function doesn't work properly when the replacement text contains an asterisk. 
									String first = s.substring(0, fromIndex);
									String middle = s.substring(fromIndex, fromIndex+searchText.length());
									String last = s.substring(fromIndex+searchText.length(), s.length());
									s = first + middle.replaceFirst(Matcher.quoteReplacement(searchText), Matcher.quoteReplacement(replacementText)) + last;
								}
							fromIndex++;
						}
					}
				}
    			fileContent.append(s + "\n");
    		}
    		fr.close();   
    		return fileContent;
    	}
    	catch(IOException ex)
    	{
    		throw new CompositeException("Error in "+getName+": " + ex.toString());
    	}	        
	}

	private static void createFileAscii(String fileName, boolean append, String fileContent) throws CompositeException {

		try {
			File file = new File(fileName);
			// if its not there create it
			if(!file.isFile()){
			file.createNewFile();
		}				
		
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName, append));
		if (append) {
			out.newLine();
		}
		out.write(fileContent);
		out.close();
		}
		catch(IOException ex)
		{
			throw new CompositeException("Error in "+getName+": " + ex.toString());
		}	        
	}

	private static void logger(String message) {
		System.out.println(message);
	}
	
	
	public static void main(String[] args) {
		
		if(args == null || args.length <=1)
		{
			throw new CompositeException("Invalid Arguments");
		}
	
		try {
			boolean validMethod = false;
			Class<ReplaceText> ReplaceTextClass = ReplaceText.class;
			Method[] methods = ReplaceTextClass.getMethods();
			for (int i = 0; i < methods.length; i++) {
				if(methods[i].getName().equals(args[0])){
					validMethod = true;
					try {
						String[] methodArgs = new String[args.length - 1];
						String methodArgsPrint = "";
						for (int j = 0; j < methodArgs.length; j++) {
							methodArgs[j] = args[j+1];
							methodArgsPrint = methodArgsPrint + "\"" + args[j+1] + "\" ";
						}
						logger(" Invoking method "+args[0]+" With args: "+methodArgsPrint);

						//replaceAllText(args[1], args[2], args[3], args[4]);
						Object returnValue = methods[i].invoke(ReplaceTextClass, (Object[])methodArgs);
						logger(" Successfully executed "+args[0]);

					} catch (IllegalArgumentException e) {
						logger("Error while invoking method "+args[0]);
						throw new CompositeException(e.getMessage(),e);
					} catch (Throwable e) {
						throw new CompositeException(e.getMessage(),e);
					}
				}
			}
			
			if(!validMethod){
				logger("Passed in method "+args[0]+" does not exist");
				throw new CompositeException("Passed in method  "+args[0]+" does not exist ");
			}
			
		} catch (CompositeException e) {
			logger("Error occured while executing ");
			throw e;
		}
	}

	
} // end of class
 

