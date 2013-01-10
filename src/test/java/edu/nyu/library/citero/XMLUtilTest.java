package edu.nyu.library.citero;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.nyu.library.citero.utils.XMLUtil;;

public class XMLUtilTest {
    
    @Test
    public void FormattedTest(){
        XMLUtil builder = new XMLUtil();
        builder.build("This", "That");
        assertEquals(("<?xml version=\"1.0\" encoding=\"UTF-8\"?><record><This>That</This></record>"), builder.out());
    }
    

}
