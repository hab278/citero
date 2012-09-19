package edu.nyu.library.citation;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class CSFTest {
	
	@Test
	public void CSFInCSFOutTest(){
		String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
		Citation cit = new Citation(csf, Formats.CSF);
		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.CSF)));
	}
	
	@Test
	public void RISInCSFOutTest(){
		Citation cit = new Citation("TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n", Formats.RIS);
		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.CSF)));
	}
	
	@Test
	public void OPENURLInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.OPENURL, Formats.OPENURL);
		System.out.println(cit.output(Formats.CSF).matches(FormatsTest.CSF_REGEX));
		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.CSF)));
	}
	
	@Test
	public void PNXInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.PNX, Formats.PNX);
		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.CSF)));
	}
	
	
	@Test
	public void BIBTEXInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.BIBTEX, Formats.BIBTEX);
		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.CSF)));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInCSFOutTest(){
		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.CSF)));
	}
	

}