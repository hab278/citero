package edu.nyu.library.citation;


import org.junit.Test;

import edu.nyu.library.citation.Formats;

public class CitationTest {
		
	@Test(expected=IllegalArgumentException.class)
	public void testUnrecognizedFormat() throws IllegalArgumentException {
		Citation.map("").from(Formats.valueOf("none"));
	}
	
	@Test
	public void testUnmatchedFormat(){
		Citation.map("Test").from(Formats.RIS);
	}
	
	@Test
	public void testSizeLimit(){
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDataNotLoaded() throws IllegalArgumentException{
		Citation.map("").from(Formats.BIBTEX).to(Formats.PNX);
	}
}