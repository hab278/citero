package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class RISTest {
	
	@Test
	public void CSFInRISOutTest(){
		String	csf = "itemType: journalArticle\nauthor: Shannon\\,Claude E.";
		assertTrue(Citero.map(csf).from(Formats.CSF).to(Formats.RIS).matches(FormatsTest.RIS_REGEX));
		
	}
	
	@Test
	public void RISInRISOutTest(){
		String ris = "TY  -  JOUR\nA1  -  Shannon,Claude E.\nER  -\n\n";
		assertEquals(ris, Citero.map(ris).from(Formats.RIS).to(Formats.RIS));
	}
	
	@Test
	public void PNXInRISOutTest(){
		assertTrue(Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
	
	@Test
	public void OPENURLInRISOutTest(){
		assertTrue(Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
	
	@Test
	public void BIBTEXInRISOutTest(){
		assertTrue(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInRISOutTest(){
//		Citero cit = new Citero(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(cit.output(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
}