package edu.nyu.library.citation;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;




public class PNX extends Format{
	
	private CSF item;
	private String input;

	public PNX(String input) {
		super(input);
		item = new CSF();
		doImport();
	}
	
	public void doImport(){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Reader reader = new CharArrayReader(input.toCharArray());
			Document doc = docBuilder.parse(new InputSource(reader));
			
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile("//display/type");
			
			String itemType = expr.evaluate(doc);
			System.out.println(expr.evaluate(doc));
			
			if(itemType.equals("book") || item.equals("Books"))
				;
			else if (itemType.equals("audio"))
				;
			else if (itemType.equals("video"))
				;
			else if (itemType.equals("report"))
				;
			else if (itemType.equals("webpage"))
				;
			else if (itemType.equals("article"))
				;
			else if (itemType.equals("thesis"))
				;
			else if (itemType.equals("map"))
				;
			else
				;
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PNX(CSF item) {
		super(item);
	}

	@Override
	public String toCSF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public edu.nyu.library.citation.CSF CSF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String export() {
		// TODO Auto-generated method stub
		return null;
	}
}
