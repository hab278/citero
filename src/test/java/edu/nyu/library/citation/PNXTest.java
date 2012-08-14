package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PNXTest {
	@Test
	public void PNXInCSFOutTest(){
		assertEquals("", new Citation("", Formats.PNX).output(Formats.CSF));
	}
	
	@Test
	public void CSFInPNXOutTest(){
		assertEquals("", new Citation("",Formats.CSF).output(Formats.PNX));
	}
	
	@Test
	public void PNXInPNXOutTest(){
		String pnx = "";
		assertEquals(pnx, new Citation(pnx,Formats.PNX).output(Formats.PNX));
	}
}