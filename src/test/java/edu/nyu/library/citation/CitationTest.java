package edu.nyu.library.citation;

//import static org.junit.Assert.assertTrue;
//import junit.framework.Assert;

import org.junit.Test;

public class CitationTest {

		
	@Test(expected=IllegalArgumentException.class)
	public void testRecognizedFormat() throws IllegalArgumentException {
		Citation cit = new Citation();
		cit.loadData("testing", "test");
	}
	
	@Test
	public void testUnmatchedFormat(){
		
	}
	
	@Test
	public void testSizeLimit(){
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDataNotLoaded() throws IllegalArgumentException{
		Citation cit = new Citation();
		cit.output("");
	}
}