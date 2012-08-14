package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OPENURLTest {
	@Test
	public void OPENURLInCSFOutTest(){
		Citation cit = new Citation("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -", Formats.OPENURL);
		assertEquals("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:", cit.output(Formats.CSF));
	}
	
	@Test
	public void CSFInOPENURLOutTest(){
		Citation cit = new Citation("", Formats.CSF);
		assertEquals("", cit.output(Formats.OPENURL));
	}
	
	@Test
	public void OPENURLInOPENURLOutTest(){
		String openurl = "";
		Citation cit = new Citation(openurl,Formats.OPENURL);
		assertEquals(openurl, cit.output(Formats.OPENURL));
	}
}