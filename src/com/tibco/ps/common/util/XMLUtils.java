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


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import com.tibco.ps.common.CommonConstants;
import com.tibco.ps.common.exception.ApplicationException;
import com.tibco.ps.common.exception.CompositeException;
import com.tibco.ps.deploytool.modules.ObjectFactory;

public class XMLUtils {

	// -- returns root element of XML document
	public static Element getDocumentFromString(String xmlString) throws CompositeException {
		
		Element retval=null;
		String parseStr = xmlString;		
		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(parseStr));
		Document document = null;
		try {
			document = (new SAXBuilder()).build(inStream);
	        retval = document.getRootElement();
		} catch(JDOMException e) {
			throw new CompositeException(e);
		} catch (IOException e) {
			throw new CompositeException(e);
		}
		
		return retval;
	}
	
	// -- returns an XML string from an XML document object
	public static String getStringFromDocument(Object object) throws CompositeException {
		
		String retval = null;
		
		try {
			// Create JAXBContext
			ObjectFactory objectFactory = new ObjectFactory();
			JAXBContext jaxbContext = JAXBContext.newInstance(objectFactory.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);

			StringWriter sw = new StringWriter();
			marshaller.marshal(object,sw);
			retval = sw.toString();
		} catch (Exception e) {
			throw new CompositeException(e);
		}
		
		return retval;
	}

	// -- pretty prints an XML element
	public static String getPrettyXml(Element el) {
		XMLOutputter ser = new XMLOutputter(Format.getPrettyFormat());
		return ser.outputString(el);
	}	
	
	public static void createXMLFromModuleType(Object object, String xmlFilePath) {
		try {

			// Create JAXBContext
			ObjectFactory objectFactory = new ObjectFactory();
			JAXBContext jaxbContext = JAXBContext.newInstance(objectFactory.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);

			StringWriter sw = new StringWriter();
			marshaller.marshal(object,sw);

			FileWriter fw = new FileWriter(xmlFilePath);
			fw.write(sw.toString());
			fw.flush();
			fw.close();
		} catch (Throwable t) {
			CompositeLogger.logException(t, t.getMessage());
			throw new CompositeException(t);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getModuleTypeFromXML(String xmlFilePath) {
		
		
		// Added code to convert any XML namespaces from old path to new path
		String filecontent = null;
		filecontent = CommonUtils.getFileAsString(xmlFilePath);
		if (filecontent.contains("dvbu.cisco")) {
			filecontent = filecontent.replaceAll("dvbu.cisco", "tibco");
			CommonUtils.createFileWithContent(xmlFilePath, filecontent);
		}

		Object object = null;
		try {
			FileInputStream fisXML = new FileInputStream(new File(xmlFilePath));
			try {
	
				// Create JAXBContext
				ObjectFactory objectFactory = new ObjectFactory();
				JAXBContext jaxbContext = JAXBContext.newInstance(objectFactory.getClass());
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	
				/* 
				 * Open the deployToolModules.xsd Schema to read and set the schema for the validation by the unmarshaller
				 * 
				 * Valid XML Constants used when defining a new schema
				 * 		javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI ("http://www.w3.org/2001/XMLSchema") W3C XML Schema 1.0 
				 * 		javax.xml.XMLConstants.RELAXNG_NS_URI ("http://relaxng.org/ns/structure/1.0")  
				 * 
				 * Get the schemaLocation value from the property file.
				 */
				Schema schema = null;
				String defaultPropertyFile = CommonConstants.propertyFile;
				/*  Determine the property file name for this environment
				 *    1. Start with default file CommonConstants.propertyFile
				 *    2. Get Java Environment variables
				 *    3. Get OS System Environment variables
				 */
		        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
		        // Set a default value if not found
		        if (propertyFile == null || propertyFile.trim().length() == 0) {
		        	propertyFile = defaultPropertyFile;
		        }
				String schemaLocation = CommonUtils.extractVariable("XMLUtils", PropertyManager.getInstance().getProperty(propertyFile,"SCHEMA_LOCATION"),propertyFile, true);
				if (schemaLocation != null && schemaLocation.length() > 0) {
					try {
						FileInputStream fisSchema = new FileInputStream(schemaLocation); 
						schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new Source[] { new StreamSource(fisSchema) } );			
						unmarshaller.setSchema(schema);
					} catch (FileNotFoundException e) {
						CompositeLogger.logException(e, e.getMessage());
						throw new CompositeException(e);
					}
				} else {
					throw new ApplicationException("The schemaLocation property is not set properly for /resources/config/deploy.properties.");
				}
	
				// Now unmarshall the xml	
				Object xmlObject = unmarshaller.unmarshal(fisXML);
				if(xmlObject instanceof JAXBElement){
					object = (Object) ((JAXBElement) xmlObject).getValue();
				}else{
					object = xmlObject;
				}
				
			} catch (JAXBException e) {
				String message = parseException(null, xmlFilePath, e, null);
				CompositeLogger.logException(e, message);
				throw new CompositeException(e);
				
			} catch (SAXParseException e) {		
				String message = parseException(null, xmlFilePath, null, e);
				CompositeLogger.logException(e, message);
				throw new CompositeException(e);
				
			} catch (Throwable e) {
				CompositeLogger.logException(e, e.getMessage());
				throw new CompositeException(e);
			}
		} catch (FileNotFoundException e) {
			CompositeLogger.logException(e, e.getMessage());
			throw new CompositeException(e);
		}

		return object;
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getJavaObjectFromXML(String xmlDoc) {
		Object object = null;
		try {
			// Make sure the document contains lines for proper parsing by parseException if an error occurs.
			xmlDoc = XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(xmlDoc));
			
			// Create an input stream from the XML document string
			InputStream is = new ByteArrayInputStream(xmlDoc.getBytes());
			 			
			try {
	
				// Create JAXBContext
				ObjectFactory objectFactory = new ObjectFactory();
				JAXBContext jaxbContext = JAXBContext.newInstance(objectFactory.getClass());
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	
				/* 
				 * Open the deployToolModules.xsd Schema to read and set the schema for the validation by the unmarshaller
				 * 
				 * Valid XML Constants used when defining a new schema
				 * 		javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI ("http://www.w3.org/2001/XMLSchema") W3C XML Schema 1.0 
				 * 		javax.xml.XMLConstants.RELAXNG_NS_URI ("http://relaxng.org/ns/structure/1.0")  
				 * 
				 * Get the schemaLocation value from the property file.
				 */
				Schema schema = null;
				String defaultPropertyFile = CommonConstants.propertyFile;
				//  Determine the property file name for this environment
				//    1. Start with default file CommonConstants.propertyFile
				//    2. Get Java Environment variables
				//    3. Get OS System Environment variables
				//
		        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
		        // Set a default value if not found
		        if (propertyFile == null || propertyFile.trim().length() == 0) {
		        	propertyFile = defaultPropertyFile;
		        }
				String schemaLocation = CommonUtils.extractVariable("XMLUtils", PropertyManager.getInstance().getProperty(propertyFile,"SCHEMA_LOCATION"),propertyFile, true);
				if (schemaLocation != null && schemaLocation.length() > 0) {
					try {
						FileInputStream fisSchema = new FileInputStream(schemaLocation); 
						schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new Source[] { new StreamSource(fisSchema) } );			
						unmarshaller.setSchema(schema);
					} catch (FileNotFoundException e) {
						CompositeLogger.logException(e, e.getMessage());
						throw new CompositeException(e);
					}
				} else {
					throw new ApplicationException("The schemaLocation property is not set properly for /resources/config/deploy.properties.");
				}
				
				// Now unmarshall the xml	
				Object xmlObject = unmarshaller.unmarshal(is);
				if(xmlObject instanceof JAXBElement){
					object = (Object) ((JAXBElement) xmlObject).getValue();
				}else{
					object = xmlObject;
				}
				
			} catch (JAXBException e) {
				String message = parseException(xmlDoc, null, e, null);
				CompositeLogger.logException(e, message);
				throw new CompositeException(e);
				
			} catch (SAXParseException e) {		
				String message = parseException(xmlDoc, null, null, e);
				CompositeLogger.logException(e, message);
				throw new CompositeException(e);
			} catch (Throwable e) {
				CompositeLogger.logException(e, e.getMessage());
				throw new CompositeException(e);
			}
		} catch (Exception e) {
			CompositeLogger.logException(e, e.getMessage());
			throw new CompositeException(e);
		}

		return object;
	}

	/* xmlDoc - the xml document to convert to an object
	 * schemaPropertyName - the deploy.properties variable name such as "ADMIN_API_LOCATION"
	 */
	@SuppressWarnings("rawtypes")
	public static Object getJavaObjectFromXMLOtherSchema(String xmlDoc, String schemaPropertyName) {
		Object object = null;
		try {
			// Make sure the document contains lines for proper parsing by parseException if an error occurs.
			xmlDoc = XMLUtils.getPrettyXml(XMLUtils.getDocumentFromString(xmlDoc));
			
			// Create an input stream from the XML document string
			InputStream is = new ByteArrayInputStream(xmlDoc.getBytes());
			 			
			try {
	
				// Create JAXBContext
				ObjectFactory objectFactory = new ObjectFactory();
				JAXBContext jaxbContext = JAXBContext.newInstance(objectFactory.getClass());
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	
				/* 
				 * Open the deployToolModules.xsd Schema to read and set the schema for the validation by the unmarshaller
				 * 
				 * Valid XML Constants used when defining a new schema
				 * 		javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI ("http://www.w3.org/2001/XMLSchema") W3C XML Schema 1.0 
				 * 		javax.xml.XMLConstants.RELAXNG_NS_URI ("http://relaxng.org/ns/structure/1.0")  
				 * 
				 * Get the schemaLocation value from the property file.
				 */
				
				Schema schema = null;
				String defaultPropertyFile = CommonConstants.propertyFile;
				//  Determine the property file name for this environment
				//    1. Start with default file CommonConstants.propertyFile
				//    2. Get Java Environment variables
				//    3. Get OS System Environment variables
				//
		        String propertyFile = CommonUtils.getFileOrSystemPropertyValue(defaultPropertyFile, "CONFIG_PROPERTY_FILE");
		        // Set a default value if not found
		        if (propertyFile == null || propertyFile.trim().length() == 0) {
		        	propertyFile = defaultPropertyFile;
		        }
				String schemaLocation = CommonUtils.extractVariable("XMLUtils", PropertyManager.getInstance().getProperty(propertyFile,schemaPropertyName),propertyFile, true);
				if (schemaLocation != null && schemaLocation.length() > 0) {
					try {
						FileInputStream fisSchema = new FileInputStream(schemaLocation); 
						schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new Source[] { new StreamSource(fisSchema) } );			
						unmarshaller.setSchema(schema);
					} catch (FileNotFoundException e) {
						CompositeLogger.logException(e, e.getMessage());
						throw new CompositeException(e);
					}
				} else {
					throw new ApplicationException("The schemaLocation property is not set properly for /resources/config/deploy.properties.");
				}
				
				// Now unmarshall the xml	
				Object xmlObject = unmarshaller.unmarshal(is);
				if(xmlObject instanceof JAXBElement){
					object = (Object) ((JAXBElement) xmlObject).getValue();
				}else{
					object = xmlObject;
				}
				
			} catch (JAXBException e) {
				String message = parseException(xmlDoc, null, e, null);
				CompositeLogger.logException(e, message);
				throw new CompositeException(e);
				
			} catch (SAXParseException e) {		
				String message = parseException(xmlDoc, null, null, e);
				CompositeLogger.logException(e, message);
				throw new CompositeException(e);
			
			} catch (Throwable e) {
				CompositeLogger.logException(e, e.getMessage());
				throw new CompositeException(e);
			}
		} catch (Exception e) {
			CompositeLogger.logException(e, e.getMessage());
			throw new CompositeException(e);
		}

		return object;
	}

	private static String parseException(String xmlDoc, String xmlFilePath, JAXBException je, SAXParseException se) throws CompositeException {
		String message = "";
		try {
			// Determine whether the input is from an XML string or an XML file.
			DataInputStream in = null;
			if (xmlFilePath != null) {
				FileInputStream fisError = new FileInputStream(new File(xmlFilePath));
				in = new DataInputStream(fisError);
			}
			if (xmlDoc != null) {
				InputStream isError = new ByteArrayInputStream(xmlDoc.getBytes());
				in = new DataInputStream(isError);
			}

			int line = 0;
			try {
				// Cast the Linked Exception to SASParseException
				SAXParseException s = null;
				if (je != null) {
					s = (SAXParseException) je.getLinkedException();
				}
				if (se != null ) {
					s = se;
				}
				line = s.getLineNumber();
				String lineText = null;
				// If the line number greater than 0 then read the XML Property File that was passed in and get the line which caused the exception
				if (line > 0) {
					try {
						// Get the object of DataInputStream
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						String strLine;
						int i=0;
						//Read File Line By Line
						while ((strLine = br.readLine()) != null)   {
							if (++i == line) {
								// Found the line number provided by SASParseException
								lineText = strLine;
								break;
							}
						}
						//Close the input stream
						in.close();				
					} catch (Exception re){//Catch exception if any
						CompositeLogger.logException(re, re.getMessage());
						throw new CompositeException(re);
					}
				}
				if (lineText != null) {
					message = "Error in file=["+xmlFilePath+"]  Line Number=["+line+"]  Line Text=["+lineText.trim()+"]  Message=["+s.getMessage()+"]";
				} else {
					message = "Error in file=["+xmlFilePath+"]  Message=["+s.getMessage()+"]";						
				}
	
			} catch (Exception pe) {
				CompositeLogger.logException(pe, pe.getMessage());
				throw new CompositeException(pe);
			}
		} catch (FileNotFoundException fe) {
			CompositeLogger.logException(fe, fe.getMessage());
			throw new CompositeException(fe);
		}
		return message;
	}


	@SuppressWarnings("unchecked")
	public static void encryptOrDecryptPasswordsInInputFile(String xmlFilePath, boolean encrypt, String bypassStr) {

		String xmlString = CommonUtils.getFileAsString(xmlFilePath);
		Element rootElement = getDocumentFromString(xmlString);

		if(rootElement != null){
			
			List<Element> nodes = rootElement.getChildren();
			processChildNodes(nodes, encrypt, bypassStr);
		
			Document doc = rootElement.getDocument();
		    XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		    FileWriter writer = null;
			
		    try {

				writer = new FileWriter(xmlFilePath);
			    outputter.output(doc, writer);

			} catch (IOException t) {
				CompositeLogger.logException(t, t.getMessage());
				throw new CompositeException(t);
			
			}finally{
			    try {
					writer.close();
				} catch (IOException e) {

				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void processChildNodes(List<Element> childNodes, boolean encrypt, String bypassStr){

		boolean allowVariables = false;
		// Look ahead to see how allowVariables is set in order to know how to encrypt the password field.
		for (Element element : childNodes) {
			if(element.getChildren() == null || element.getChildren().isEmpty()){
				// Determine if the allowVariables is set to true or false
				if( (element.getName().equalsIgnoreCase("allowVariables") && element.getValue() != null)){
					allowVariables = Boolean.valueOf(element.getValue());
				}

			}else{
				processChildNodes(element.getChildren(), encrypt, bypassStr);
			}
		}

		for (Element element : childNodes) {
			if(element.getChildren() == null || element.getChildren().isEmpty()){
				
				// Look for any name in the encrypted password list:
				//  "VCS_PASSWORD encryptedPassword PASSWORD_STRING SVN_VCS_PASSWORD, P4_VCS_PASSWORD CVS_VCS_PASSWORD TFS_VCS_PASSWORD GIT_VCS_PASSWORD";
				if( (CommonUtils.existsEncryptPropertyList(element.getName()) && element.getValue() != null)){
					// encrypt the password if directed to and the allowVariables=true
					if(encrypt && !allowVariables){
						// Get the password value
						String password = XMLUtils.convertXMLEscapeChar(element.getValue());
						
						// Encrypt the password value
						if (!CommonUtils.doBypassEncryption(password, bypassStr))
							element.setText(CommonUtils.encrypt(password));
					}
				}
				// Look for PASSWORD_STRING
				if(element.getValue() != null && CommonUtils.existsEncryptPropertyList(element.getValue())){
					processChildNodesSiblings(childNodes, encrypt, allowVariables, bypassStr);
				}

			}else{
				processChildNodes(element.getChildren(), encrypt, bypassStr);
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	private static void processChildNodesSiblings(List<Element> childNodes, boolean encrypt, boolean allowVariables, String bypassStr){
		for (Element element : childNodes) {
			if(element.getChildren() == null || element.getChildren().isEmpty()){
				
				if((element.getName().equalsIgnoreCase("value") || element.getName().equalsIgnoreCase("defaultValue") ) && element.getValue() != null){
					if(encrypt && !allowVariables){
						// Get the password value
						String password = XMLUtils.convertXMLEscapeChar(element.getValue());
						// Encrypt the password value
						if (!CommonUtils.doBypassEncryption(password, bypassStr))
							element.setText(CommonUtils.encrypt(password));
					}
				}
			}
		}

	}
	
	private static String convertXMLEscapeChar(String str) {
		/* 
		 * direction
		 * 	0 = convert XML escape to original character
		 *  1 = convert regular characters to XML escape
		 * Modify any XML escaped values: 
			"   &quot;
			'   &apos;
			<   &lt;
			>   &gt;
			&   &amp;
		*/
		if (str.contains("&quot;"))
			str = str.replaceAll(Matcher.quoteReplacement("&quot;"), Matcher.quoteReplacement("\""));
		if (str.contains("&apos;"))
			str = str.replaceAll(Matcher.quoteReplacement("&apos;"), Matcher.quoteReplacement("'"));
		if (str.contains("&lt;"))
			str = str.replaceAll(Matcher.quoteReplacement("&lt;"), Matcher.quoteReplacement("<"));
		if (str.contains("&gt;"))
			str = str.replaceAll(Matcher.quoteReplacement("&gt;"), Matcher.quoteReplacement(">"));
		if (str.contains("&amp;"))
			str = str.replaceAll(Matcher.quoteReplacement("&amp;"), Matcher.quoteReplacement("&"));
		return str;
	}

	
	/**
	 * Return a flat name value pairs string constructed based on passed in xml content and separators 
	 * "=","|" are defaulted as separators 1 and 2 if the values are not passed. Here is the flattened servers.xml
	 * hostname=localhost|port=9400|usage=DEV|user=admin|encryptedpassword=7F6324FFD300BE8F|domain=composite|
	 * hostname=localhost|port=9460|usage=DEV|user=admin|encryptedpassword=7F6324FFD300BE8F|domain=composite
	 * @param xmlString xml string
	 * @param seperator1 Separator of name and value
	 * @param seperator2 Separator for name value pair
	 * @param nodeName String return name values for passed in node name and node value
	 * @param nodeValue String return name values for passed in node name and node value
	 * @param options additional options to return the node name or attributes such as "-noid -noname -noattributes"
	 * @return flattened xml 
	 */
	public static String getNameValuePairsFromXMLString(String xmlString, String seperator1, String seperator2, String nodeName, String nodeValue, String options){

		StringBuffer sb = new StringBuffer();
	
		if(seperator1 == null || seperator1.isEmpty() || seperator1.length() == 0){
			seperator1 = "=";				
		}

		if(seperator2 == null || seperator2.isEmpty() || seperator2.length() == 0){
			seperator2 = "|";				
		}
			
		Element	el = getDocumentFromString(xmlString);
		extractChildElements(el, sb, seperator1, seperator2,nodeName,nodeValue,options);
		
		return sb.toString();			
	}

	/**
	 * Return a flat name value pairs string constructed based on passed in xml content and separators 
	 * "=","|" are defaulted as separators 1 and 2 if the values are not passed. Here is the flattened servers.xml
	 * hostname=localhost|port=9400|usage=DEV|user=admin|encryptedpassword=7F6324FFD300BE8F|domain=composite|
	 * hostname=localhost|port=9460|usage=DEV|user=admin|encryptedpassword=7F6324FFD300BE8F|domain=composite
	 * @param xmlFilePath xml File Path
	 * @param seperator1 Separator of name and value
	 * @param seperator2 Separator for name value pair
	 * @param nodeName String return name values for passed in node name and node value
	 * @param nodeValue String return name values for passed in node name and node value
	 * @param options additional options to return the node name or attributes such as "-noid -noname -noattributes"
	 * @return flattened xml 
	 */
	public static String getNameValuePairsFromXML(String xmlFilePath, String seperator1, String seperator2, String nodeName, String nodeValue, String options){

		StringBuffer sb = new StringBuffer();
	
		if(seperator1 == null || seperator1.isEmpty() || seperator1.length() == 0){
			seperator1 = "=";				
		}

		if(seperator2 == null || seperator2.isEmpty() || seperator2.length() == 0){
			seperator2 = "|";				
		}
		
		String xml = CommonUtils.getFileAsString(xmlFilePath);
		
		Element	el = getDocumentFromString(xml);
		extractChildElements(el, sb, seperator1, seperator2,nodeName,nodeValue,options);
		
		return sb.toString();			
	}

	@SuppressWarnings("unchecked")
	public static void extractChildElements(Element el, StringBuffer sb, String seperator1, String seperator2, String nodeName, String nodeValue, String options){
		
		List<Attribute> attributes = el.getAttributes();

		//handle attributes here
		if(attributes != null && !attributes.isEmpty() && options != null && !options.contains("-noattributes")){
			for (Attribute attribute : attributes) {
				extractElementData(attribute.getName(),attribute.getValue(), sb,seperator1, seperator2,nodeName,nodeValue,options);
			}
		}

		//handle nodes here
		List<Element> childNodes = el.getChildren();
		extractChildElementValues(childNodes,sb, seperator1, seperator2,nodeName,nodeValue,options);
	}


	@SuppressWarnings("unchecked")
	private static void extractChildElementValues(List<Element> childNodes,StringBuffer sb, String seperator1, String seperator2, String nodeName, String nodeValue, String options) {
		boolean nodeFound = false;
		for (Element element : childNodes) {	
			if(element.getChildren() == null || element.getChildren().isEmpty()){
				
				// handle nodes here	

				if(nodeName != null && nodeValue != null){
					
					if(((element.getName().equalsIgnoreCase(nodeName)) || (nodeName.equals(PropertyUtil.getAllResourcesIndicatorString()))) && ((element.getValue().equalsIgnoreCase(nodeValue)) || (nodeValue.equals(PropertyUtil.getAllResourcesIndicatorString())))){
						
						nodeFound = true;						
					}
					
				}else{

					extractElementData(element.getName(),element.getValue(), sb,seperator1, seperator2,nodeName,nodeValue,options);
					List<Attribute> attributes = element.getAttributes();

					//handle attributes here
					if(attributes != null && !attributes.isEmpty() && (options == null || !options.contains("-noattributes"))){
						for (Attribute attribute : attributes) {
							extractElementData(attribute.getName(),attribute.getValue(), sb,seperator1, seperator2,nodeName,nodeValue,options);
						}
					}

				}
			}else{
				extractChildElements(element, sb, seperator1, seperator2,nodeName,nodeValue,options);
			}
			
		}

		if(nodeFound){
			
			extractAllChildElementValues(childNodes, sb, seperator1, seperator2, nodeName, nodeValue, options);
		}

	}

	private static void extractAllChildElementValues(List<Element> childNodes,StringBuffer sb, String seperator1, String seperator2, String nodeName, String nodeValue, String options) {

		for (Element element : childNodes) {

			if(element.getChildren() == null || element.getChildren().isEmpty()){

				extractElementData(element.getName(), element.getValue(), sb,seperator1, seperator2, nodeName, nodeValue, options);
	
				@SuppressWarnings("unchecked")
				List<Attribute> attributes = element.getAttributes();
	
				// handle attributes here
				if (attributes != null && !attributes.isEmpty() && (options == null || !options.contains("-noattributes"))) {
					for (Attribute attribute : attributes) {
						extractElementData(attribute.getName(),attribute.getValue(), sb, seperator1, seperator2,nodeName, nodeValue, options);
					}
				}
			}
		}
						
	}

	private static void extractElementData(String name, String value, StringBuffer sb, String seperator1,String seperator2, String nodeName, String nodeValue, String options) {
		if (name != null && name.trim().length() > 0) {

			if ((options != null && options.contains("-noid")) && nodeName != null && nodeValue != null
				&& (name.equalsIgnoreCase(nodeName) || nodeName.equals(PropertyUtil.getAllResourcesIndicatorString())) && ((value.equalsIgnoreCase(nodeValue)) || nodeValue.equals(PropertyUtil.getAllResourcesIndicatorString()))){
				//skip

			} else {

				if (sb.length() > 0) {
					sb.append(seperator2);
				}
				if (options != null && !options.contains("-noname")){
					sb.append(name);
					sb.append(seperator1);
				}
				sb.append(value);
			}
		}
	}

}
