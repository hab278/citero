package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CSFTest {
	
	@Test
	public void CSFInCSFOutTest(){
		String csf = "---\nitemType: book\ncreator:\n  ? author\n  : Alexander Dumas\n  ? contributor\n  : D'Artagnan\nfields:\n  ? title\n  : The Three Musketeers";;
		Citation cit = new Citation(csf,Formats.CSF);
		assertEquals(csf, cit.output(Formats.CSF));
	}
	
	@Test
	public void RISInCSFOutTest(){
		Citation cit = new Citation("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n", Formats.RIS);
		assertEquals("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:", cit.output(Formats.CSF));
	}
	
	@Test
	public void BIBTEXInCSFOutTest(){
		Citation cit = new Citation("", Formats.BIBTEX);
		assertEquals("", cit.output(Formats.CSF));
	}
	
	@Test
	public void OPENURLInCSFOutTest(){
		Citation cit = new Citation("", Formats.OPENURL);
		assertEquals("", cit.output(Formats.CSF));
	}
	
	@Test
	public void PNXInCSFOutTest(){
		Citation cit = new Citation("", Formats.PNX);
		assertEquals("", cit.output(Formats.CSF));
	}
	
	@Test
	public void XERXES_XMLInCSFOutTest(){
		Citation cit = new Citation("", Formats.XERXES_XML);
		assertEquals("", cit.output(Formats.CSF));
	}
}