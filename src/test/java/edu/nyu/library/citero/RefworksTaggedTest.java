package edu.nyu.library.citero;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RefworksTaggedTest {

    @Test
    public void CSFInRefworksTaggedOutTest(){
        String  csf = "itemType: journalArticle\nauthor: Shannon\\,Claude E.";
        assertTrue(Citero.map(csf).from(Formats.CSF).to(Formats.REFWORKS_TAGGED).matches(FormatsTest.REFWORKS_TAGGED_REGEX));
        
    }
    
    @Test
    public void RISInRefworksTaggedOutTest(){
        String ris = "TY  -  JOUR\nA1  -  Shannon, Claude E.\nER  -\n\n";
        String refworks = "RT Journal Article\nA1 Shannon, Claude E.\nER \n \n";
        assertEquals(refworks, Citero.map(ris).from(Formats.RIS).to(Formats.REFWORKS_TAGGED));
    }
    
    @Test
    public void PNXInRefworksTaggedOutTest(){
        assertTrue(Citero.map(FormatsTest.PNX).from(Formats.PNX).to(Formats.REFWORKS_TAGGED).matches(FormatsTest.REFWORKS_TAGGED_REGEX));
    }
    
    @Test
    public void Xerxes_XMLInRefworksTaggedOutTest(){
        assertTrue(Citero.map(FormatsTest.XERXES_XML).from(Formats.XERXES_XML).to(Formats.REFWORKS_TAGGED).matches(FormatsTest.REFWORKS_TAGGED_REGEX));
    }
    
    @Test
    public void OPENURLInRefworksTaggedOutTest(){
        assertTrue(Citero.map(FormatsTest.OPENURL).from(Formats.OPENURL).to(Formats.REFWORKS_TAGGED).matches(FormatsTest.REFWORKS_TAGGED_REGEX));
    }
    
    @Test
    public void BIBTEXInRefworksTaggedOutTest(){
        assertTrue(Citero.map(FormatsTest.BIBTEX).from(Formats.BIBTEX).to(Formats.REFWORKS_TAGGED).matches(FormatsTest.REFWORKS_TAGGED_REGEX));
    }

}
