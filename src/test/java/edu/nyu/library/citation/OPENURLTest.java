package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OPENURLTest {
	
	@Test
	public void CSFInOPENURLOutTest(){
		assertEquals(FormatsTest.OPENURL, new Citation(FormatsTest.CSF, Formats.CSF).output(Formats.OPENURL));
	}
	
	@Test
	public void OPENURLInOPENURLOutTest(){
		assertEquals(FormatsTest.OPENURL, new Citation(FormatsTest.OPENURL,Formats.OPENURL).output(Formats.OPENURL));
	}
	
	@Test
	public void PNXInOPENURLOutTest(){
		assertEquals(FormatsTest.OPENURL, new Citation(FormatsTest.PNX, Formats.PNX).output(Formats.OPENURL));
	}
	
	@Test
	public void BIBTEXInOPENURLOutTest(){
		assertEquals(FormatsTest.OPENURL, new Citation(FormatsTest.BIBTEX, Formats.BIBTEX).output(Formats.OPENURL));
	}
	
	@Test
	public void RISInOPENURLOutTest(){
		assertEquals(FormatsTest.OPENURL, new Citation(FormatsTest.RIS, Formats.RIS).output(Formats.OPENURL));
	}
	
	@Test
	public void XERXES_XMLInOPENURLOutTest(){
		assertEquals(FormatsTest.OPENURL, new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML).output(Formats.OPENURL));
	}
}