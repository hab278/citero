package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PNXTest {
	
	@Test
	public void CSFInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.CSF, Formats.CSF);
		assertEquals(FormatsTest.PNX, cit.output(Formats.PNX));
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
		Citation cit = new Citation(FormatsTest.RIS, Formats.RIS);
		assertEquals(FormatsTest.PNX, cit.output(Formats.PNX));
	}
	
	@Test
	public void XERXES_XMLInPNXOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertEquals(FormatsTest.PNX, cit.output(Formats.PNX));
	}
}