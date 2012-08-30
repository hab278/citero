package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

public class PNXTest {
	
	@Test
	public void CSFInPNXOutTest(){
		String csf = "---\nitemType: book\ncreator:\n  ? author\n  : Alexander Dumas\n  ? contributor\n  : D'Artagnan\nfields:\n  ? title\n  : The Three Musketeers";
		Citation cit = new Citation(csf, Formats.CSF);
		assertTrue(cit.output(Formats.PNX).matches(FormatsTest.PNX_REGEX));
	}
	
	@Test
	public void PNXInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertEquals(FormatsTest.PNX, cit.output(Formats.PNX));
	}
	
	@Test
	public void OPENURLInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		assertTrue(cit.output(Formats.PNX).matches(FormatsTest.PNX_REGEX));	}
	
	@Test
	public void BIBTEXInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertTrue(cit.output(Formats.PNX).matches(FormatsTest.PNX_REGEX));
	}
	
	@Test
	public void RISInPNXOutTest(){
		String ris = "TY  -  JOUR\nA1  -  Shannon,Claude E.\nER  -\n\n";
		Citation cit = new Citation(ris, Formats.RIS);
		assertTrue(cit.output(Formats.PNX).matches(FormatsTest.PNX_REGEX));	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertTrue(cit.output(Formats.PNX).matches(FormatsTest.PNX_REGEX));	}
}