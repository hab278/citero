package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BIBTEXTest {
	
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
	
	@Test
	public void PNXInBIBTEXOutTest(){}
	
	@Test
	public void OPENURLInBIBTEXOutTest(){}
	
	@Test
	public void RISInBIBTEXOutTest(){}
	
	@Test
	public void XERXES_XMLInBIBTEXOutTest(){}
}