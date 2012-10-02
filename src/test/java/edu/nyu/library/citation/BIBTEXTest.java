package edu.nyu.library.citation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class BIBTEXTest {
	
	@Test
	public void CSFInBIBTEXOutTest(){
		assertTrue( Citation.map(FormatsTest.CSF).from(Formats.CSF).to(Formats.BIBTEX).matches(FormatsTest.BIBTEX_REGEX));
	}
	
	@Test
	public void BIBTEXInBIBTEXOutTest(){
		assertEquals(FormatsTest.BIBTEX, Citation.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.BIBTEX));
	}
	
	@Test
	public void PNXInBIBTEXOutTest(){
		assertTrue( Citation.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.BIBTEX).matches(FormatsTest.BIBTEX_REGEX));
	}
	
	@Test
	public void OPENURLInBIBTEXOutTest(){
		assertTrue( Citation.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.BIBTEX).matches(FormatsTest.BIBTEX_REGEX));
	}
	
	@Test
	public void RISInBIBTEXOutTest(){
		assertTrue( Citation.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.BIBTEX).matches(FormatsTest.BIBTEX_REGEX));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInBIBTEXOutTest(){
//		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertEquals("", cit.output(Formats.BIBTEX));
	}
}