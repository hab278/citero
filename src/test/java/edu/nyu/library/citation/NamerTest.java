package edu.nyu.library.citation;

import static org.junit.Assert.*;
import org.junit.Test;

public class NamerTest {
	
	@Test
	public void FirstNameTest(){
		Namer n = new Namer("Eddie Murphy 1961-");
		assertEquals(("Murphy, Eddie"), n.goodName());
	}
}
