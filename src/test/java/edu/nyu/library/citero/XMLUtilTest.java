package edu.nyu.library.citero;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.nyu.library.citero.utils.XMLUtil;;

public class XMLUtilTest {
    
    @Test
    public void FormattedTest(){
        XMLUtil builder = new XMLUtil();
        builder.build("This", "That");
        builder.build("that", "This");
        builder.build("This", "That");
        builder.build("This//That", "Tho");
        assertEquals(("<?xml version=\"1.0\" encoding=\"UTF-8\"?><record><This>That ; That<That>Tho</That></This><that>This</that></record>"), builder.out());
    }
}
