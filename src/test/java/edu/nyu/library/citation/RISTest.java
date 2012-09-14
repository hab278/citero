package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

public class RISTest {
	
	@Test
	public void CSFInRISOutTest(){
		Citation cit = new Citation("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon\\,Claude E.\nfields:\n  ? \n", Formats.CSF);
		System.out.println(cit.output(Formats.RIS));
		assertEquals(cit.output(Formats.RIS), (FormatsTest.RIS_REGEX));
		
	}
	
	@Test
	public void RISInRISOutTest(){
		String ris = "TY  -  JOUR\nA1  -  Shannon,Claude E.\nER  -\n\n";
		assertEquals(ris, new Citation(ris,Formats.RIS).output(Formats.RIS));
	}
	
	@Test
	public void PNXInRISOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertTrue(cit.output(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
	
	@Test
	public void OPENURLInRISOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		assertTrue(cit.output(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
	
	@Test
	public void BIBTEXInRISOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertTrue(cit.output(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInRISOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertTrue(cit.output(Formats.RIS).matches(FormatsTest.RIS_REGEX));
	}
}