package edu.nyu.library.citero;

import static org.junit.Assert.assertTrue;

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
    public void Xerxes_XMLInCSFOutTest(){
        assertTrue(FormatsTest.isValidCSF(Citero.map(FormatsTest.XERXES_XML).from(Formats.Xerxes_XML).to(Formats.CSF)));
    }
	
	@Test
	public void BIBTEXInCSFOutTest(){
		assertTrue(FormatsTest.isValidCSF(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.CSF)));
	}
	
	@Test
    public void EASYBIBInCSFOutTest(){
	    EASYBIB ebib = new EASYBIB("itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers");
        assertTrue(FormatsTest.isValidCSF(ebib.toCSF().doExport()));
    }
	
	@Test
    public void FormatToEASYBIBInCSFOutTest(){
        Format format = new EASYBIB("itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers");
        assertTrue(FormatsTest.isValidCSF(((CSF)(format.toCSF())).doExport()));
    }

}