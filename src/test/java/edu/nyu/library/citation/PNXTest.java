package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PNXTest {
	
	@Test
	public void CSFInPNXOutTest(){
		String csf = "---\nitemType: book\ncreator:\n  ? author\n  : Alexander Dumas\n  ? contributor\n  : D'Artagnan\nfields:\n  ? title\n  : The Three Musketeers";
		Citation cit = new Citation(csf, Formats.CSF);
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><record><display><type>book</type><creator>Alexander Dumas</creator><contributor>D'Artagnan</contributor></display></record>", cit.output(Formats.PNX));
	}
	
	@Test
	public void PNXInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertEquals(FormatsTest.PNX, cit.output(Formats.PNX));
	}
	
	@Test
	public void OPENURLInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		assertEquals(FormatsTest.PNX, cit.output(Formats.PNX));
	}
	
	@Test
	public void BIBTEXInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertEquals(FormatsTest.PNX, cit.output(Formats.PNX));
	}
	
	@Test
	public void RISInPNXOutTest(){
		String ris = "TY  -  JOUR\nA1  -  Shannon,Claude E.\nER  -\n\n";
		Citation cit = new Citation(ris, Formats.RIS);
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><record><display><type>article</type><creator>Shannon,Claude E.</creator></display></record>", cit.output(Formats.PNX));
	}
	
	@Test
	public void XERXES_XMLInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertEquals(FormatsTest.PNX, cit.output(Formats.PNX));
	}
}