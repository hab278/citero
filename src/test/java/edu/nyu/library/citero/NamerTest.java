package edu.nyu.library.citero;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.nyu.library.citero.utils.NameFormatter;

public class NamerTest {
	
	@Test
	public void FormattedTest(){
		assertEquals(("Richards, Keith"), NameFormatter.from("Keith  Richards  1943-").toFormatted());
	}
	
	@Test
	public void ebTest(){
	    assertEquals(("Bennion, E. B."), NameFormatter.from("E. B. Bennion (Edmund Baron)").toFormatted());
	}
	
	@Test
    public void eb2Test(){
        assertEquals(("Bennion, E. B."), NameFormatter.from("Bennion,E. B.").toFormatted());
    }

    @Test
    public void ebsTest(){
        assertEquals(("Walton, Geoff"), NameFormatter.from("Geoff Walton (Geoff L.)").toFormatted());
    }
    @Test
    public void ebssTest(){
        assertEquals(("Geoff"), NameFormatter.from("Geoff Walton").firstName());
    }
	@Test
    public void FirstNameTest(){
        assertEquals(("Keith"), NameFormatter.from("Keith  Richards  1943-").firstName());
    }
	
	@Test
    public void LastNameTest(){
        assertEquals(("Richards"), NameFormatter.from("Keith  Richards  1943-").lastName());
    }
	
	@Test
    public void SuffixNameTest(){
        assertEquals((""), NameFormatter.from("Keith  Richards  1943-").suffix());
    }
	
	@Test
    public void MiddleNameTest(){
        assertEquals((""), NameFormatter.from("Keith  Richards  1943-").middleName());
    }
}
