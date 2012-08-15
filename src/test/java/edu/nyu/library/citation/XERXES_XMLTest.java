package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XERXES_XMLTest {
	
	@Test
	public void CSFInXERXES_XMLOutTest(){
		assertEquals(FormatsTest.XERXES_XML, new Citation(FormatsTest.CSF, Formats.CSF).output(Formats.XERXES_XML));
	}
	
	@Test
	public void XERXES_XMLInXERXES_XMLOutTest(){
		System.out.println(FormatsTest.XERXES_XML);
		System.out.println(FormatsTest.XERXES_XML.isEmpty());
		assertEquals(FormatsTest.XERXES_XML, new Citation(FormatsTest.XERXES_XML,Formats.XERXES_XML).output(Formats.XERXES_XML));
	}
	
	@Test
	public void PNXInXERXES_XMLOutTest(){
		assertEquals(FormatsTest.XERXES_XML, new Citation(FormatsTest.PNX, Formats.PNX).output(Formats.XERXES_XML));
	}
	
	@Test
	public void BIBTEXInXERXES_XMLOutTest(){
		assertEquals(FormatsTest.XERXES_XML, new Citation(FormatsTest.BIBTEX, Formats.BIBTEX).output(Formats.XERXES_XML));
	}
	
	@Test
	public void RISInXERXES_XMLOutTest(){
		assertEquals(FormatsTest.XERXES_XML, new Citation(FormatsTest.RIS, Formats.RIS).output(Formats.XERXES_XML));
	}
	
	@Test
	public void OPENURLInXERXES_XMLOutTest(){
		assertEquals(FormatsTest.XERXES_XML, new Citation(FormatsTest.OPENURL, Formats.OPENURL).output(Formats.XERXES_XML));
	}
}