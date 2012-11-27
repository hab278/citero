package edu.nyu.library.citation;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class EASYBIBTest {
	
	@Test
	public void CSFInEASYBIBOutTest(){
		String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
		assertTrue(FormatsTest.isValidJson(Citation.map(csf).from(Formats.CSF).to(Formats.EASYBIB)));
	}
	
	@Test
	public void RISInEASYBIBOutTest(){
		assertTrue(FormatsTest.isValidJson(Citation.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.EASYBIB)));
	}
	
	@Test
	public void OPENURLInEASYBIBOutTest(){
		assertTrue(FormatsTest.isValidJson(Citation.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.EASYBIB)));
	}
	
	@Test
	public void PNXInEASYBIBOutTest(){
		assertTrue(FormatsTest.isValidJson(Citation.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.EASYBIB)));
	}
	
	
	@Test
	public void BIBTEXInEASYBIBOutTest(){;
		assertTrue(FormatsTest.isValidJson(Citation.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.EASYBIB)));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInEASYBIBOutTest(){
//		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.EASYBIB)));
	}
	

}