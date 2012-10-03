package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

public class OPENURLTest {
	
	@Test
	public void CSFInOPENURLOutTest(){
		assertTrue(Citation.map(FormatsTest.CSF).from(Formats.CSF).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void OPENURLInOPENURLOutTest(){
		assertTrue(Citation.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void PNXInOPENURLOutTest(){
		assertTrue(Citation.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void BIBTEXInOPENURLOutTest(){
		assertTrue(Citation.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void RISInOPENURLOutTest(){
		assertTrue(Citation.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInOPENURLOutTest(){
//		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(cit.output(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
}