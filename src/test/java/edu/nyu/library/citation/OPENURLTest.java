package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

public class OPENURLTest {
	
	@Test
	public void CSFInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.CSF, Formats.CSF);
		assertTrue(cit.output(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void OPENURLInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		assertTrue(cit.output(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void PNXInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertTrue(cit.output(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void BIBTEXInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertTrue(cit.output(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void RISInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.RIS, Formats.RIS);
		assertEquals(cit.output(Formats.OPENURL),(FormatsTest.OPENURL_REGEX));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInOPENURLOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertTrue(cit.output(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
}