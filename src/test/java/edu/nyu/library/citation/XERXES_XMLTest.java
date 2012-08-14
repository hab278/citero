package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XERXES_XMLTest {
	@Test
	public void XERXES_XMLInCSFOutTest(){
		assertEquals("", new Citation("", Formats.XERXES_XML).output(Formats.CSF));
	}
	
	@Test
	public void CSFInXERXES_XMLOutTest(){
		assertEquals("", new Citation("",Formats.CSF).output(Formats.XERXES_XML));
	}
	
	@Test
	public void XERXES_XMLInXERXES_XMLOutTest(){
		String xerxes_xml = "";
		assertEquals(xerxes_xml, new Citation(xerxes_xml,Formats.XERXES_XML).output(Formats.XERXES_XML));
	}
}