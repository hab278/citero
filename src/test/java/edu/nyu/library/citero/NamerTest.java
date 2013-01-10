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
