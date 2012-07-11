package edu.nyu.library.citation;

//import static org.junit.Assert.assertTrue;
//import junit.framework.Assert;

import org.junit.Test;

import edu.nyu.library.citation.Citation.Format;

public class CitationTest {

		
	@Test(expected=IllegalArgumentException.class)
	public void testRecognizedFormat() throws IllegalArgumentException {
		Citation cit = new Citation("testing", Format.BIBTEX);
	}
	
	@Test
	public void testUnmatchedFormat(){
		
	}
	
	@Test
	public void testSizeLimit(){
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDataNotLoaded() throws IllegalArgumentException{
		Citation cit = new Citation("testing", Format.BIBTEX);
		cit.output(Format.PNX);
	}
//test jenkins
}