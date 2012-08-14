package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RISTest {
	@Test
	public void RISInCSFOutTest(){
		assertEquals("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:", new Citation("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -", Formats.RIS).output(Formats.CSF));
	}
	
	@Test
	public void CSFInRISOutTest(){
		assertEquals("", new Citation("",Formats.CSF).output(Formats.RIS));
	}
	
	@Test
	public void RISInRISOutTest(){
		String ris = "";
		assertEquals(ris, new Citation(ris,Formats.RIS).output(Formats.RIS));
	}
}