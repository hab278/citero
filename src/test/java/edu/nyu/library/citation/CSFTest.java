package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

public class CSFTest {
	
	@Test
	public void CSFInCSFOutTest(){
		String csf = "---\nitemType: book\ncreator:\n  ? author\n  : Alexander Dumas\n  ? contributor\n  : D'Artagnan\nfields:\n  ? title\n  : The Three Musketeers";
		Citation cit = new Citation(csf, Formats.CSF);
		assertTrue(cit.output(Formats.CSF).matches(FormatsTest.CSF_REGEX));
	}
	
	@Test
	public void RISInCSFOutTest(){
		Citation cit = new Citation("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n", Formats.RIS);
		assertEquals(cit.output(Formats.CSF), (FormatsTest.CSF_REGEX));
	}
	
	@Test
	public void OPENURLInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		System.out.println(cit.output(Formats.CSF).matches(FormatsTest.CSF_REGEX));
		assertTrue(cit.output(Formats.CSF).matches(FormatsTest.CSF_REGEX));
	}
	
	@Test
	public void PNXInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertTrue(cit.output(Formats.CSF).matches(FormatsTest.CSF_REGEX));
	}
	
	
	@Test
	public void BIBTEXInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertTrue(cit.output(Formats.CSF).matches(FormatsTest.CSF_REGEX));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertTrue(cit.output(Formats.CSF).matches(FormatsTest.CSF_REGEX));
	}
}