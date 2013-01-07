package edu.nyu.library.citero;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class EASYBIBTest {
	
	@Test
	public void CSFInEASYBIBOutTest(){
		String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
		assertTrue(FormatsTest.isValidJson(Citero.map(csf).from(Formats.CSF).to(Formats.EASYBIB)));
	}
	
	@Test
	public void RISInEASYBIBOutTest(){
		assertTrue(FormatsTest.isValidJson(Citero.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.EASYBIB)));
	}
	
	@Test
	public void OPENURLInEASYBIBOutTest(){
		assertTrue(FormatsTest.isValidJson(Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.EASYBIB)));
	}
	
	@Test
	public void PNXInEASYBIBOutTest(){
		assertTrue(FormatsTest.isValidJson(Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.EASYBIB)));
	}
	
	
	@Test
	public void BIBTEXInEASYBIBOutTest(){;
		assertTrue(FormatsTest.isValidJson(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.EASYBIB)));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInEASYBIBOutTest(){
//		Citero cit = new Citero(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.EASYBIB)));
	}
	

}