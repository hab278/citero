package edu.nyu.library.citero;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class BIBTEXTest {
	
	@Test
	public void CSFInBIBTEXOutTest(){
		assertTrue( Citero.map(FormatsTest.CSF).from(Formats.CSF).to(Formats.BIBTEX).matches(FormatsTest.BIBTEX_REGEX));
	}
	
	@Test
	public void BIBTEXInBIBTEXOutTest(){
		assertEquals(FormatsTest.BIBTEX, Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.BIBTEX));
	}
	
	@Test
	public void PNXInBIBTEXOutTest(){
		assertTrue( Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.BIBTEX).matches(FormatsTest.BIBTEX_REGEX));
	}
    
    @Test
    public void Xerxes_XMLInBIBTEXOutTest(){
        assertTrue(Citero.map(FormatsTest.XERXES_XML).from(Formats.XERXES_XML).to(Formats.BIBTEX).matches(FormatsTest.BIBTEX_REGEX));
    }
	
	@Test
	public void OPENURLInBIBTEXOutTest(){
		assertTrue( Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.BIBTEX).matches(FormatsTest.BIBTEX_REGEX));
	}
	
	@Test
	public void RISInBIBTEXOutTest(){
		assertTrue( Citero.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.BIBTEX).matches(FormatsTest.BIBTEX_REGEX));
	}
	
}