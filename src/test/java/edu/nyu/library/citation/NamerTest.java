package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Test;

public class NamerTest {
	
	@Test
	public void FirstNameTest(){
		assertEquals(("Richards, Keith"), Namer.from("Keith  Richards  1943-").toFormatted());
	}
}
