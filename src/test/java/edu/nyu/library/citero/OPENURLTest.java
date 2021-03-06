package edu.nyu.library.citero;

import static org.junit.Assert.*;

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
    public void Xerxes_XMLInOPENURLOutTest(){
        assertTrue(Citero.map(FormatsTest.XERXES_XML).from(Formats.XERXES_XML).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
    }
	
	@Test
	public void BIBTEXInOPENURLOutTest(){
		assertTrue(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}
	
	@Test
	public void RISInOPENURLOutTest(){
		assertTrue(Citero.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.OPENURL).matches(FormatsTest.OPENURL_REGEX));
	}

}