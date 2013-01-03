package edu.nyu.library.citation;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class CSFTest {
	
	@Test
	public void CSFInCSFOutTest(){
		String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
		assertTrue(FormatsTest.isValidCSF(Citero.map(csf).from(Formats.CSF).to(Formats.CSF)));
	}
	
	@Test
	public void RISInCSFOutTest(){
		assertTrue(FormatsTest.isValidCSF(Citero.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.CSF)));
	}
	
	@Test
	public void OPENURLInCSFOutTest(){
		assertTrue(FormatsTest.isValidCSF(Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.CSF)));
	}
	
	@Test
	public void PNXInCSFOutTest(){
		assertTrue(FormatsTest.isValidCSF(Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.CSF)));
	}
	
	
	@Test
	public void BIBTEXInCSFOutTest(){;
		assertTrue(FormatsTest.isValidCSF(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.CSF)));
	}
	
	@Ignore("Functionality not required yet.")
	@Test
	public void XERXES_XMLInCSFOutTest(){
//		Citero cit = new Citero(FormatsTest.XERXES_XML, Formats.XERXES_XML);
//		assertTrue(FormatsTest.isValidCSF(cit.output(Formats.CSF)));
	}
	

}