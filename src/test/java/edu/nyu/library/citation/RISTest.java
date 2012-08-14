package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RISTest {
	@Test
	public void RISInCSFOutTest(){
		Citation cit = new Citation("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -", Formats.RIS);
		assertEquals("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:", cit.output(Formats.CSF));
	}
	
	@Test
	public void CSFInRISOutTest(){
		Citation cit = new Citation("", Formats.CSF);
		assertEquals("", cit.output(Formats.RIS));
	}
	
	@Test
	public void RISInRISOutTest(){
		String ris = "";
		Citation cit = new Citation(ris,Formats.RIS);
		assertEquals(ris, cit.output(Formats.RIS));
	}
}