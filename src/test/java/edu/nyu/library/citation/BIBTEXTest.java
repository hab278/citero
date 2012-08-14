package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BIBTEXTest {
	@Test
	public void BIBTEXInCSFOutTest(){
		assertEquals("", new Citation("", Formats.BIBTEX).output(Formats.CSF));
	}
	
	@Test
	public void CSFInBIBTEXOutTest(){
		assertEquals("", new Citation("",Formats.CSF).output(Formats.BIBTEX));
	}
	
	@Test
	public void BIBTEXInBIBTEXOutTest(){
		String bibtex = "";
		assertEquals(bibtex, new Citation(bibtex,Formats.BIBTEX).output(Formats.BIBTEX));
	}
}