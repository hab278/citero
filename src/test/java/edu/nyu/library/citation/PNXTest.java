package edu.nyu.library.citation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PNXTest {
	
	@Test
	public void CSFInPNXOutTest(){
		assertEquals(FormatsTest.PNX, new Citation(FormatsTest.CSF, Formats.CSF).output(Formats.PNX));
	}
	
	@Test
	public void PNXInPNXOutTest(){
		System.out.println(FormatsTest.PNX.isEmpty());
		assertEquals(FormatsTest.PNX, new Citation(FormatsTest.PNX,Formats.PNX).output(Formats.PNX));
	}
	
	@Test
	public void OPENURLInPNXOutTest(){
		assertEquals(FormatsTest.PNX, new Citation(FormatsTest.OPENURL, Formats.OPENURL).output(Formats.PNX));
	}
	
	@Test
	public void BIBTEXInPNXOutTest(){
		assertEquals(FormatsTest.PNX, new Citation(FormatsTest.BIBTEX, Formats.BIBTEX).output(Formats.PNX));
	}
	
	@Test
	public void RISInPNXOutTest(){
		assertEquals(FormatsTest.PNX, new Citation(FormatsTest.RIS, Formats.RIS).output(Formats.PNX));
	}
	
	@Test
	public void XERXES_XMLInPNXOutTest(){
		assertEquals(FormatsTest.PNX, new Citation(FormatsTest.XERXES_XML, Formats.XERXES_XML).output(Formats.PNX));
	}
}