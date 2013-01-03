package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

public class RISTest {
	
	@Test
	public void CSFInRISOutTest(){
		String	csf = "itemType: journalArticle\nauthor: Shannon\\,Claude E.";
		assertTrue(Citation.map(csf).from(Formats.CSF).to(Formats.RIS).matches(FormatsTest.RIS_REGEX));
		
	}
	
	@Test
	public void RISInRISOutTest(){
		String ris = "TY  -  JOUR\nA1  -  Shannon,Claude E.\nER  -\n\n";
		assertEquals(ris, Citation.map(ris).from(Formats.RIS).to(Formats.RIS));
	}
	
	@Test
	public void PNXInRISOutTest(){
		assertTrue(Citation.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
	
	@Test
	public void OPENURLInRISOutTest(){
		assertTrue(Citation.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
	
	@Test
	public void BIBTEXInRISOutTest(){
		assertTrue(Citation.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInRISOutTest(){
//		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(cit.output(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
}