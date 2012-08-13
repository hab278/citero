package edu.nyu.library.citation;

//import static org.junit.Assert.assertTrue;
//import junit.framework.Assert;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.nyu.library.citation.Formats;

public class CitationTest {
		
	@Test(expected=IllegalArgumentException.class)
	public void testUnrecognizedFormat() throws IllegalArgumentException {
		new Citation("testing", Formats.PNX);
	}
	
	@Test
	public void testUnmatchedFormat(){
		
	}
	
	@Test
	public void testSizeLimit(){
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDataNotLoaded() throws IllegalArgumentException{
		Citation cit = new Citation("testing", Formats.BIBTEX);
		cit.output(Formats.PNX);
	}
	
	


}