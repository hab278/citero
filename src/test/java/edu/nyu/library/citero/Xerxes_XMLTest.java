package edu.nyu.library.citero;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class Xerxes_XMLTest {
    
    @Test(expected=IllegalArgumentException.class)
    public void CSFInPNXOutTest(){
        String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
        assertTrue(Citero.map(csf).from(Formats.CSF).to(Formats.Xerxes_XML).matches(FormatsTest.PNX_REGEX));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void PNXInPNXOutTest(){
        assertEquals(FormatsTest.PNX, Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.Xerxes_XML));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void OPENURLInPNXOutTest(){
        assertTrue(Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.Xerxes_XML).matches(FormatsTest.PNX_REGEX));   }
    
    @Test(expected=IllegalArgumentException.class)
    public void BIBTEXInPNXOutTest(){
        assertTrue(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.Xerxes_XML).matches(FormatsTest.PNX_REGEX));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void RISInPNXOutTest(){
        assertTrue(Citero.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.Xerxes_XML).matches(FormatsTest.PNX_REGEX));   
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void EASYBIBInPNXOutTest(){
        assertTrue(Citero.map(FormatsTest.RIS).from(Formats.EASYBIB).to(Formats.Xerxes_XML).matches(FormatsTest.PNX_REGEX));   
    }

}