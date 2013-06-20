package edu.nyu.library.citero;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class XerxesXMLTest {
    
    @Test(expected=IllegalArgumentException.class)
    public void CSFInXerxesXMLOutTest(){
        String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
        assertTrue(Citero.map(csf).from(Formats.CSF).to(Formats.XERXES_XML).matches(FormatsTest.XERXES_XML));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void PNXInXerxesXMLOutTest(){
        assertEquals(FormatsTest.PNX, Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.XERXES_XML));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void OPENURLInXerxesXMLOutTest(){
        assertTrue(Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.XERXES_XML).matches(FormatsTest.XERXES_XML));   }
    
    @Test(expected=IllegalArgumentException.class)
    public void BIBTEXInXerxesXMLOutTest(){
        assertTrue(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.XERXES_XML).matches(FormatsTest.XERXES_XML));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void RISInXerxesXMLOutTest(){
        assertTrue(Citero.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.XERXES_XML).matches(FormatsTest.XERXES_XML));   
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void EASYBIBInXerxesXMLOutTest(){
        assertTrue(Citero.map(FormatsTest.RIS).from(Formats.EASYBIB).to(Formats.XERXES_XML).matches(FormatsTest.XERXES_XML));   
    }

}