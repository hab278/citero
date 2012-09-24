package edu.nyu.library.citation;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class CSFTest {
	
	@Test
	public void CSFInCSFOutTest(){
		String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
		assertTrue(FormatsTest.isValidCSF(Citation.map(csf).from(Formats.CSF).to(Formats.CSF)));
	}
	
	@Test
	public void RISInCSFOutTest(){
		String ris = "TY  -  JOUR\nAU  -  Shannon,Claude E.\nER  -\n\n";
		assertTrue(FormatsTest.isValidCSF(Citation.map(ris).from(Formats.RIS).to(Formats.CSF)));
	}
	
	@Test
	public void OPENURLInCSFOutTest(){
		assertTrue(FormatsTest.isValidCSF(Citation.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.CSF)));
	}
	
	@Test
	public void PNXInCSFOutTest(){
		assertTrue(FormatsTest.isValidCSF(Citation.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.CSF)));
	}
	
	
	@Test
	public void BIBTEXInCSFOutTest(){;
		assertTrue(FormatsTest.isValidCSF(Citation.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.CSF)));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInCSFOutTest(){
//		Citation cit = new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.CSF)));
	}
	

}