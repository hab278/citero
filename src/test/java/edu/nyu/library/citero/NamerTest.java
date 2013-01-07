package edu.nyu.library.citero;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.nyu.library.citero.utils.NameFormatter;

public class NamerTest {
	
	@Test
	public void FirstNameTest(){
		assertEquals(("Richards, Keith"), NameFormatter.from("Keith  Richards  1943-").toFormatted());
	}
}
