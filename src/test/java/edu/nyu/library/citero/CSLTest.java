package edu.nyu.library.citero;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class CSLTest {
    
    @Test
    public void CSFInCSLOutTest(){
        String csf = "itemType: book\nauthor: Alexander Dumas\ncontributor: D'Artagnan\ntitle: The Three Musketeers";
        assertTrue(FormatsTest.isValidJson(Citero.map(csf).from(Formats.CSF).to(Formats.CSL)));
    }
    
    @Test
    public void RISInCSLOutTest(){
        assertTrue(FormatsTest.isValidJson(Citero.map(FormatsTest.RIS).from(Formats.RIS).to(Formats.CSL)));
    }
    
    @Test
    public void OPENURLInCSLOutTest(){
        assertTrue(FormatsTest.isValidJson(Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.CSL)));
    }
    
    @Test
    public void PNXInCSLOutTest(){
        assertTrue(FormatsTest.isValidJson(Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.CSL)));
    }
    
    @Test
    public void Xerxes_XMLInCSLOutTest(){
        assertTrue(FormatsTest.isValidJson(Citero.map(FormatsTest.XERXES_XML).from(Formats.XERXES_XML).to(Formats.CSL)));
    }

    @Test
    public void BIBTEXInCSLOutTest(){;
        assertTrue(FormatsTest.isValidJson(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.CSL)));
    }
}