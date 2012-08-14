package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PNXTest {
	@Test
	public void PNXInCSFOutTest(){
		Citation cit = new Citation("", Formats.PNX);
		assertEquals("", cit.output(Formats.CSF));
	}
	
	@Test
	public void CSFInPNXOutTest(){
		Citation cit = new Citation("", Formats.CSF);
		assertEquals("", cit.output(Formats.PNX));
	}
	
	@Test
	public void PNXInPNXOutTest(){
		String pnx = "";
		Citation cit = new Citation(pnx,Formats.PNX);
		assertEquals(pnx, cit.output(Formats.PNX));
	}
}