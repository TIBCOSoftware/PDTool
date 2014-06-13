package com.cisco.dvbu.ps.common.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A sample DOM application.  This application reads an XML document, 
 * builds a DOM tree, then sorts the nodes in the DOM tree. 
 */

public class XMLDomSorter
{
  public void parseAndSortLines(String uri)
  {
    Document doc = null;

    try
    {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setIgnoringElementContentWhitespace(true);
      DocumentBuilder db = dbf.newDocumentBuilder();
      doc = db.parse(uri);
      if (doc != null)
      {
        NodeList theLines = doc.getDocumentElement().
                            getElementsByTagName("line");
        sortLines(theLines);
//        XMLUtils.getPrettyXml(el);
      }
    }
    catch (Exception e)
    {
      System.err.println("Sorry, an error occurred: " + e);
    }
  }

  public String getTextFromLine(Node lineElement)
  {
    StringBuffer returnString = new StringBuffer();

    if (lineElement.getNodeName().equals("line"))
    {
      NodeList kids = lineElement.getChildNodes();
      if (kids != null)
        if (kids.item(0).getNodeType() == Node.TEXT_NODE)
          returnString.append(kids.item(0).getNodeValue());
    }
    else
      returnString.setLength(0);

    return new String(returnString);
  }

  /** Sorts the <line> elements in the file.
      It uses a bubble sort algorithm, since a 
      sonnet only has 14 lines.                **/
  public void sortLines(NodeList theLines)
  {
    if (theLines != null)
    {
      int len = theLines.getLength();
      for (int i = 0; i < len; i++)
        for (int j = 0; j < (len - 1 - i); j++)
          if (getTextFromLine(theLines.item(j)).
              compareTo(getTextFromLine(theLines.item(j+1)))
              > 0)
            theLines.item(j).getParentNode().
              insertBefore(theLines.item(j+1),
                           theLines.item(j));
    }
  }

  /** Main program entry point. */
  public static void main(String argv[]) 
  {
    if (argv.length == 0 ||
        (argv.length == 1 && argv[0].equals("-help")))
    {
      System.out.println("\nUsage:  java DomSorter uri");
      System.out.println("   where uri is the URI of the XML " + 
                         "document you want to sort.");
      System.out.println("   Sample:  java DomSorter sonnet.xml");
      System.out.println("   Note: Your XML document must " + 
                         "use the sonnet DTD.");
      System.out.println("\nSorts the lines of an XML sonnet, then " + 
                         "writes the DOM tree to the console.");
      System.exit(1);
    }

    XMLDomSorter ds = new XMLDomSorter();
    ds.parseAndSortLines(argv[0]);
  }
}

