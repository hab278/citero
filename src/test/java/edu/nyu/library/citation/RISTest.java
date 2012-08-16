package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RISTest {
	
	@Test
	public void CSFInRISOutTest(){
		Citation cit = new Citation("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:", Formats.CSF);
		assertEquals("TY  -  JOUR\nA1  -  Shannon,Claude E.\nER  -\n\n", cit.output(Formats.RIS));
	}
	
	@Test
	public void RISInRISOutTest(){
		String ris = "TY  -  JOUR\nA1  -  Shannon,Claude E.\nER  -\n\n";
		assertEquals(ris, new Citation(ris,Formats.RIS).output(Formats.RIS));
	}
	
	@Test
	public void PNXInRISOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertEquals(FormatsTest.RIS,  cit.output(Formats.RIS));
	}
	
	@Test
	public void OPENURLInRISOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		assertEquals(FormatsTest.RIS, cit.output(Formats.RIS));
	}
	
	@Test
	public void BIBTEXInRISOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertEquals(FormatsTest.RIS, cit.output(Formats.RIS));
	}
	
	@Test
	public void XERXES_XMLInRISOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertEquals(FormatsTest.RIS, cit.output(Formats.RIS));
	}
}