package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class BIBTEXTest {
	
	@Test
	public void CSFInBIBTEXOutTest(){
		Citation cit = new Citation(FormatsTest.CSF, Formats.CSF);
		assertEquals("", cit.output(Formats.BIBTEX));
	}
	
	@Test
	public void BIBTEXInBIBTEXOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertEquals("", cit.output(Formats.BIBTEX));
	}
	
	@Test
	public void PNXInBIBTEXOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertEquals("", cit.output(Formats.BIBTEX));
	}
	
	@Test
	public void OPENURLInBIBTEXOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		assertEquals("", cit.output(Formats.BIBTEX));
	}
	
	@Test
	public void RISInBIBTEXOutTest(){
		Citation cit = new Citation(FormatsTest.RIS, Formats.RIS);
		assertEquals("", cit.output(Formats.BIBTEX));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInBIBTEXOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertEquals("", cit.output(Formats.BIBTEX));
	}
}