package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BIBTEXTest {
	@Test
	public void BIBTEXInCSFOutTest(){
		Citation cit = new Citation("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -", Formats.BIBTEX);
		assertEquals("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:", cit.output(Formats.CSF));
	}
	
	@Test
	public void CSFInBIBTEXOutTest(){
		Citation cit = new Citation("", Formats.CSF);
		assertEquals("", cit.output(Formats.BIBTEX));
	}
	
	@Test
	public void BIBTEXInBIBTEXOutTest(){
		String bibtex = "";
		Citation cit = new Citation(bibtex,Formats.BIBTEX);
		assertEquals(bibtex, cit.output(Formats.BIBTEX));
	}
}