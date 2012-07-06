package edu.nyu.library.citation;

//import static org.junit.Assert.assertTrue;
//import junit.framework.Assert;

import org.junit.Test;

public class CitationTest {

		
	@Test(expected=Exception.class)
	public void testRecognizedFormat() throws Exception {
		Citation cit = new Citation();
		cit.loadData("testing", "test");
	}
	
	@Test
	public void testUnmatchedFormat(){
		
	}
	
	@Test
	public void testSizeLimit(){
		
	}
	
	@Test(expected=Exception.class)
	public void testDataNotLoaded() throws Exception{
		Citation cit = new Citation();
		cit.output("");
	}
}