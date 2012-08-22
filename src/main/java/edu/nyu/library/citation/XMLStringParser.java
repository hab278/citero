package edu.nyu.library.citation;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.base.Splitter;


public class XMLStringParser {
	
	private Document doc;
	private XPath xpath;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private DocumentFragment docFrag;
	
	public XMLStringParser(){
		 dbFactory = DocumentBuilderFactory.newInstance();
		 try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 doc = dBuilder.newDocument();
		 XPathFactory xPathfactory = XPathFactory.newInstance();
		 xpath = xPathfactory.newXPath();
		 
	}
	
	public XMLStringParser(String xml) 
	{
		this();
		Reader reader = new CharArrayReader(xml.toCharArray());
		try {
			doc = dBuilder.parse(new InputSource(reader));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void build(String expression, String value){

		
		docFrag = doc.createDocumentFragment();
		Element element = null;
		Element prevElement = null;
		for(String str: Splitter.on("/").omitEmptyStrings().trimResults().split(expression))
		{
			if(doc.getElementsByTagName(str).getLength() == 0)
			{
				element = doc.createElement(str);
				if(prevElement == null)
					docFrag.appendChild(element);
				else
					prevElement.appendChild(element);
			}
			else
			{
				element = (Element) doc.getElementsByTagName(str).item(0);
			}
			prevElement = element;
		}
		prevElement.appendChild(doc.createTextNode(value));
		doc.appendChild(docFrag);
		
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
