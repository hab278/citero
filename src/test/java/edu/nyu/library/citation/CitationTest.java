package edu.nyu.library.citation;

//import static org.junit.Assert.assertTrue;
//import junit.framework.Assert;


import org.junit.Test;

import edu.nyu.library.citation.Formats;

public class CitationTest {
		
	@Test(expected=IllegalArgumentException.class)
	public void testUnrecognizedFormat() throws IllegalArgumentException {
		new Citation("testing", Formats.valueOf("none"));
	}
	
	@Test
	public void testUnmatchedFormat(){
		new Citation("Test", Formats.RIS);
	}
	
	@Test
	public void testSizeLimit(){
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDataNotLoaded() throws IllegalArgumentException{
		new Citation("", Formats.BIBTEX).output(Formats.PNX);
	}
}