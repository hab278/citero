package edu.nyu.library.citero;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class PNXTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void CSFInPNXOutTest(){
		String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
		assertTrue(Citero.map(csf).from(Formats.CSF).to(Formats.PNX).matches(FormatsTest.PNX_REGEX));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void PNXInPNXOutTest(){
		assertEquals(FormatsTest.PNX, Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.PNX));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void OPENURLInPNXOutTest(){
		assertTrue(Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.PNX).matches(FormatsTest.PNX_REGEX));	}
	
	@Test(expected=IllegalArgumentException.class)
	public void BIBTEXInPNXOutTest(){
		assertTrue(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.PNX).matches(FormatsTest.PNX_REGEX));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void RISInPNXOutTest(){
		assertTrue(Citero.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.PNX).matches(FormatsTest.PNX_REGEX));	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EASYBIBInPNXOutTest(){
		assertTrue(Citero.map(FormatsTest.RIS).from(Formats.EASYBIB).to(Formats.PNX).matches(FormatsTest.PNX_REGEX));	
	}
	@Ignore("Functionality not required yet.")
	@Test(expected=IllegalArgumentException.class)
	public void XERXES_XMLInPNXOutTest(){
//		Citero cit = new Citero(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(cit.output(Formats.PNX).matches(FormatsTest.PNX_REGEX));	
	}
}