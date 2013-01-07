package edu.nyu.library.citero;



import org.junit.Test;

import edu.nyu.library.citero.Citero;
import edu.nyu.library.citero.Formats;

public class CiteroTest {
		
	@Test(expected=IllegalArgumentException.class)
	public void testUnrecognizedFormat() throws IllegalArgumentException {
		Citero.map("itemType: book").from(Formats.valueOf("none"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnmatchedFormat(){
		Citero.map("itemType: book").from(Formats.OPENURL);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMissingSourceFormat() throws IllegalArgumentException{
		Citero.map("itemType: book").from(Formats.CSF);
		Citero.map("itemType: book").to(Formats.CSF);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDataNotLoaded() throws IllegalArgumentException{
		Citero.map("").from(Formats.BIBTEX).to(Formats.PNX);
	}
}