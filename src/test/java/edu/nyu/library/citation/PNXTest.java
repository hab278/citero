package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

public class PNXTest {
	
	@Test
	public void CSFInPNXOutTest(){
		String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
		assertTrue(Citation.map(csf).from(Formats.CSF).to(Formats.PNX).matches(FormatsTest.PNX_REGEX));
	}
	
	@Test
	public void PNXInPNXOutTest(){
		assertEquals(FormatsTest.PNX, Citation.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.PNX));
	}
	
	@Test
	public void OPENURLInPNXOutTest(){
		assertTrue(Citation.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.PNX).matches(FormatsTest.PNX_REGEX));	}
	
	@Test
	public void BIBTEXInPNXOutTest(){
		assertTrue(Citation.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.PNX).matches(FormatsTest.PNX_REGEX));
	}
	
	@Test
	public void RISInPNXOutTest(){
		String ris = "TY  -  JOUR\nA1  -  Shannon,Claude E.\nER  -\n\n";
		assertTrue(Citation.map(ris).from(Formats.RIS).to(Formats.PNX).matches(FormatsTest.PNX_REGEX));	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInPNXOutTest(){
//		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(cit.output(Formats.PNX).matches(FormatsTest.PNX_REGEX));	
	}
}