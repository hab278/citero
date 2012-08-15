package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BIBTEXTest {
	
	@Test
	public void CSFInBIBTEXOutTest(){
		assertEquals(FormatsTest.BIBTEX, new Citation(FormatsTest.CSF, Formats.CSF).output(Formats.BIBTEX));
	}
	
	@Test
	public void BIBTEXInBIBTEXOutTest(){
		System.out.println(FormatsTest.BIBTEX.isEmpty());
		assertEquals(FormatsTest.BIBTEX, new Citation(FormatsTest.BIBTEX,Formats.BIBTEX).output(Formats.BIBTEX));
	}
	
	@Test
	public void PNXInBIBTEXOutTest(){
		assertEquals(FormatsTest.BIBTEX, new Citation(FormatsTest.PNX, Formats.PNX).output(Formats.BIBTEX));
	}
	
	@Test
	public void OPENURLInBIBTEXOutTest(){
		assertEquals(FormatsTest.BIBTEX, new Citation(FormatsTest.OPENURL, Formats.OPENURL).output(Formats.BIBTEX));
	}
	
	@Test
	public void RISInBIBTEXOutTest(){
		assertEquals(FormatsTest.BIBTEX, new Citation(FormatsTest.RIS, Formats.RIS).output(Formats.BIBTEX));
	}
	
	@Test
	public void XERXES_XMLInBIBTEXOutTest(){
		assertEquals(FormatsTest.BIBTEX, new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML).output(Formats.BIBTEX));
	}
}