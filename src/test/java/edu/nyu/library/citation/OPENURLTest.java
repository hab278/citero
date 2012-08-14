package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OPENURLTest {
	@Test
	public void OPENURLInCSFOutTest(){
		assertEquals("", new Citation("", Formats.OPENURL).output(Formats.CSF));
	}
	
	@Test
	public void CSFInOPENURLOutTest(){
		assertEquals("", new Citation("",Formats.CSF).output(Formats.OPENURL));
	}
	
	@Test
	public void OPENURLInOPENURLOutTest(){
		String openurl = "";
		assertEquals(openurl, new Citation(openurl,Formats.OPENURL).output(Formats.OPENURL));
	}
}