package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class OPENURLTest {
	
	@Test
	public void CSFInOPENURLOutTest(){
		assertTrue(Citero.map(FormatsTest.CSF).from(Formats.CSF).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void OPENURLInOPENURLOutTest(){
		assertTrue(Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void PNXInOPENURLOutTest(){
		assertTrue(Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void BIBTEXInOPENURLOutTest(){
		assertTrue(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void RISInOPENURLOutTest(){
		assertTrue(Citero.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInOPENURLOutTest(){
//		Citero cit = new Citero(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(cit.output(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
}