package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OPENURLTest {
	
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
	
	@Test
	public void PNXInOPENURLOutTest(){}
	
	@Test
	public void BIBTEXInOPENURLOutTest(){}
	
	@Test
	public void RISInOPENURLOutTest(){}
	
	@Test
	public void XERXES_XMLInOPENURLOutTest(){}
}