package edu.nyu.library.citation;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.base.Splitter;
/**
 * The XMLStringParser is a useful tool to parse an XML document or document fragment and to build an new one
 * using xPath.
 * @author hab278
 *
 */

public class XMLStringParser {
	
	private final Log logger = LogFactory.getLog(BIBTEX.class);
	/** doc variable is the XML Document object that will be built or parsed. */
	private Document doc;
	/** xpath variable is the xPath object that will be used to evalute the Document */
	private XPath xpath;
	/** dbFactory variable is used to get the Document Builder */
	private DocumentBuilderFactory dbFactory;
	/** dBuilder variable is used to build the Document */
	private DocumentBuilder dBuilder;
	/** docFrag variable is a Document fragment used to build Documents */
	private DocumentFragment docFrag;
	
	/**
	 * The default constructor. This builds a Document object and an xPath object.
	 */
	public XMLStringParser(){
		logger.info("XML STRING PARSER");
		dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		doc = dBuilder.newDocument();
		doc.appendChild(doc.createElement("record"));
		XPathFactory xPathfactory = XPathFactory.newInstance();
		xpath = xPathfactory.newXPath();
	}
	
	/**
	 * Converts an XML document into a Document.
	 * @param xml A String representation of the XML.
	 */
	public XMLStringParser(String xml) 
	{
		this();
		Reader reader = new CharArrayReader(xml.toCharArray());
		try {
			doc = dBuilder.parse(new InputSource(reader));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses the Document object for the xPath.
	 * @param expression A String representing the xPath.
	 * @return The evaluated xPath, whatever value that belongs to the xPath, in String format. Returns an empty String if nothing was found.
	 */
	public String xpath(String expression){
		try {
			return xpath.compile(expression).evaluate(doc);
		} catch (XPathExpressionException e) {
			logger.error("No such expression", e);
			return "";
		}
	}
	
	/**
	 * Builds an XML Document object given a key-value pair.
	 * @param key The key in the key-value pairs used to build the document.
	 * @param value The value in the key-value pairs used to build the document.
	 */
	public void build(String key, String value){

		
		docFrag = doc.createDocumentFragment();
		Element element = null;
		Element prevElement = null;
		boolean exists = false;
		//appends the XML tags to the previous element, or the root element, the docfrag
		for(String str: Splitter.on("/").omitEmptyStrings().trimResults().split(key))
		{
			if(doc.getElementsByTagName(str).getLength() == 0)
			{
				element = doc.createElement(str);
				if(prevElement == null)
					docFrag.appendChild(element);
				else
					prevElement.appendChild(element);
				exists = false;
			}
			else
			{
				element = (Element) doc.getElementsByTagName(str).item(0);
				exists = true;
			}
			prevElement = element;
		}
		if(exists)
			prevElement.appendChild(doc.createTextNode(" ; "+value));
		else
			prevElement.appendChild(doc.createTextNode(value));
		//appends docfrag to the doc.
		doc.getFirstChild().appendChild(docFrag);
	}

	/**
	 * Converts the Document object into a String.
	 * @return A String representation of the Document object.
	 */
	public String out(){
		//Transforms XML document to string
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new StringWriter());
		try {
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return ((StringWriter) result.getWriter()).getBuffer().toString();
	}
}
