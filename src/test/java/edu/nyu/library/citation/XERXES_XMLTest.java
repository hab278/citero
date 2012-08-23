package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class XERXES_XMLTest {
	
	@Ignore("Functionality not required yet.")
	@Test
	public void CSFInXERXES_XMLOutTest(){
		Citation cit = new Citation(FormatsTest.CSF, Formats.PNX);
		assertEquals(FormatsTest.XERXES_XML, cit.output(Formats.XERXES_XML));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInXERXES_XMLOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertEquals(FormatsTest.XERXES_XML, cit.output(Formats.XERXES_XML));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void PNXInXERXES_XMLOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertEquals(FormatsTest.XERXES_XML, cit.output(Formats.XERXES_XML));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void BIBTEXInXERXES_XMLOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertEquals(FormatsTest.XERXES_XML, cit.output(Formats.XERXES_XML));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void RISInXERXES_XMLOutTest(){
		Citation cit = new Citation(FormatsTest.RIS, Formats.RIS);
		assertEquals(FormatsTest.XERXES_XML, cit.output(Formats.XERXES_XML));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void OPENURLInXERXES_XMLOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		assertEquals(FormatsTest.XERXES_XML, cit.output(Formats.XERXES_XML));
	}
}