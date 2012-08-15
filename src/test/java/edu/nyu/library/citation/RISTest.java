package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RISTest {
	
	@Test
	public void CSFInRISOutTest(){
		Citation cit = new Citation("---\nitemType: journalArticle\ncreator:\n  ? author\n  : Shannon,Claude E.\nfields:", Formats.CSF);
		assertEquals("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n", cit.output(Formats.RIS));
	}
	
	@Test
	public void RISInRISOutTest(){
		String ris = "TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n";
		Citation cit = new Citation(ris,Formats.RIS);
		assertEquals(ris, cit.output(Formats.RIS));
	}
	
	@Test
	public void PNXInRISOutTest(){}
	
	@Test
	public void OPENURLInRISOutTest(){}
	
	@Test
	public void BIBTEXInRISOutTest(){}
	
	@Test
	public void XERXES_XMLInRISOutTest(){}
}