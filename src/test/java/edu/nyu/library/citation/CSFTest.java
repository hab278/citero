package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CSFTest {
	
	@Test
	public void CSFInCSFOutTest(){
		String csf = "";
		Citation cit = new Citation(csf,Formats.CSF);
		assertEquals(csf, cit.output(Formats.CSF));
	}
}