package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XERXES_XMLTest {
	
	@Test
	public void CSFInXERXES_XMLOutTest(){
		Citation cit = new Citation("", Formats.CSF);
		assertEquals("", cit.output(Formats.XERXES_XML));
	}
	
	@Test
	public void XERXES_XMLInXERXES_XMLOutTest(){
		String xerxes_xml = "";
		Citation cit = new Citation(xerxes_xml,Formats.XERXES_XML);
		assertEquals(xerxes_xml, cit.output(Formats.XERXES_XML));
	}
	
	@Test
	public void OPENURLInXERXES_XMLOutTest(){}
	
	@Test
	public void BIBTEXInXERXES_XMLOutTest(){}
	
	@Test
	public void RISInXERXES_XMLOutTest(){}
	
	@Test
	public void PNXInXERXES_XMLOutTest(){}
}