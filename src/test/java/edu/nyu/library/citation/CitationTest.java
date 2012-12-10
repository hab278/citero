package edu.nyu.library.citation;



import org.junit.Test;

import edu.nyu.library.citation.Formats;

public class CitationTest {
		
	@Test(expected=IllegalArgumentException.class)
	public void testUnrecognizedFormat() throws IllegalArgumentException {
		Citation.map("itemType: book").from(Formats.valueOf("none"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnmatchedFormat(){
		Citation.map("itemType: book").from(Formats.OPENURL);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMissingSourceFormat() throws IllegalArgumentException{
		Citation.map("itemType: book").from(Formats.CSF);
		Citation.map("itemType: book").to(Formats.CSF);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDataNotLoaded() throws IllegalArgumentException{
		Citation.map("").from(Formats.BIBTEX).to(Formats.PNX);
	}
}