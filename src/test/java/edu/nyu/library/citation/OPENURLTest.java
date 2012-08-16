package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OPENURLTest {
	
	@Test
	public void CSFInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.CSF, Formats.CSF);
		assertEquals(FormatsTest.OPENURL, cit.output(Formats.OPENURL));
	}
	
	@Test
	public void OPENURLInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		assertEquals(FormatsTest.OPENURL, cit.output(Formats.OPENURL));
	}
	
	@Test
	public void PNXInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertEquals(FormatsTest.OPENURL, cit.output(Formats.OPENURL));
	}
	
	@Test
	public void BIBTEXInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertEquals(FormatsTest.OPENURL, cit.output(Formats.OPENURL));
	}
	
	@Test
	public void RISInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.RIS, Formats.RIS);
		assertEquals(FormatsTest.OPENURL, cit.output(Formats.OPENURL));
	}
	
	@Test
	public void XERXES_XMLInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertEquals(FormatsTest.OPENURL, cit.output(Formats.OPENURL));
	}
}