package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Test;

public class NamerTest {
	
	@Test
	public void FirstNameTest(){
		assertEquals(("Murphy, Eddie"), Namer.from("Eddie Murphy 1961-").toFormatted());
	}
}
