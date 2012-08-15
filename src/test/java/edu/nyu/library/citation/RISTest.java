package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RISTest {
	
	@Test
	public void CSFInRISOutTest(){
		assertEquals("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n", new Citation("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:", Formats.CSF).output(Formats.RIS));
	}
	
	@Test
	public void RISInRISOutTest(){
		String ris = "TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n";
		assertEquals(ris, new Citation(ris,Formats.RIS).output(Formats.RIS));
	}
	
	@Test
	public void PNXInRISOutTest(){
		assertEquals(FormatsTest.RIS, new Citation(FormatsTest.PNX, Formats.PNX).output(Formats.RIS));
	}
	
	@Test
	public void OPENURLInRISOutTest(){
		assertEquals(FormatsTest.RIS, new Citation(FormatsTest.OPENURL, Formats.OPENURL).output(Formats.RIS));
	}
	
	@Test
	public void BIBTEXInRISOutTest(){
		assertEquals(FormatsTest.RIS, new Citation(FormatsTest.BIBTEX, Formats.BIBTEX).output(Formats.RIS));
	}
	
	@Test
	public void XERXES_XMLInRISOutTest(){
		assertEquals(FormatsTest.RIS, new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML).output(Formats.RIS));
	}
}