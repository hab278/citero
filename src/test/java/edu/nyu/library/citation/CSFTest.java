package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CSFTest {
	
	@Test
	public void CSFInCSFOutTest(){
		String csf = "---\nitemType: book\ncreator:\n  ? author\n  : Alexander Dumas\n  ? contributor\n  : D'Artagnan\nfields:\n  ? title\n  : The Three Musketeers";
		Citation cit = new Citation(FormatsTest.CSF, Formats.CSF);
		assertEquals(csf, cit.output(Formats.CSF));
	}
	
	@Test
	public void RISInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.RIS, Formats.RIS);
		assertEquals("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:\nattachments:", cit.output(Formats.CSF));
	}
	
	@Test
	public void OPENURLInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		assertEquals(FormatsTest.CSF, cit.output(Formats.CSF));
	}
	
	@Test
	public void PNXInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertEquals(FormatsTest.CSF, cit.output(Formats.CSF));
	}
	
	@Test
	public void BIBTEXInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertEquals(FormatsTest.CSF, cit.output(Formats.CSF));
	}
	
	@Test
	public void XERXES_XMLInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertEquals(FormatsTest.CSF, cit.output(Formats.CSF));
	}
}