package edu.nyu.library.citation;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XMLStringParser {
	
	private Document doc;
	private XPath xpath;
	
	public XMLStringParser(String xml) 
	{
		Reader reader = new CharArrayReader(xml.toCharArray());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(new InputSource(reader));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XPathFactory xPathfactory = XPathFactory.newInstance();
		xpath = xPathfactory.newXPath();
	}
	
	public String xpath(String expression){
		try {
			return xpath.compile(expression).evaluate(doc);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			System.err.println("No such expression");
			return "";
		}
	}

}
