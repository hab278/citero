package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CSFTest {
	
	@Test
	public void CSFInCSFOutTest(){
		String csf = "---\nitemType: book\ncreator:\n  ? author\n  : Alexander Dumas\n  ? contributor\n  : D'Artagnan\nfields:\n  ? title\n  : The Three Musketeers";
		assertEquals(csf, new Citation(csf,Formats.CSF).output(Formats.CSF));
	}
	
	@Test
	public void RISInCSFOutTest(){
		assertEquals("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:\nattachments:", new Citation("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n", Formats.RIS).output(Formats.CSF));
	}
	
	@Test
	public void OPENURLInCSFOutTest(){
		assertEquals(FormatsTest.CSF, new Citation(FormatsTest.OPENURL,Formats.OPENURL).output(Formats.CSF));
	}
	
	@Test
	public void PNXInCSFOutTest(){
		assertEquals(FormatsTest.CSF, new Citation(FormatsTest.PNX, Formats.PNX).output(Formats.CSF));
	}
	
	@Test
	public void BIBTEXInCSFOutTest(){
		assertEquals(FormatsTest.CSF, new Citation(FormatsTest.BIBTEX, Formats.BIBTEX).output(Formats.CSF));
	}
	
	@Test
	public void XERXES_XMLInCSFOutTest(){
		assertEquals(FormatsTest.CSF, new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML).output(Formats.CSF));
	}
}